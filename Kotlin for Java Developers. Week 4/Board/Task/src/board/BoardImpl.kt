/**
 * This code is written for the programming assignment of the Kotlin Coursera Assignment -  Week 4.
 * It implements two Interfaces called SquareBoard and GameBoard.
 * There are several functions to access the components of the boards called cells.
 *
 * The functions for square board are:
 * 1. getCellOrNull
 * 2. getAllCells
 * 3. getColumn
 * 4. getRow
 * 5. getAllNeighbors
 * 6. getCell
 *
 * The functions for game board are:
 * 1. get
 * 2. set
 * 3. filter
 * 4. find
 * 5. any
 * 6. all
 *
 * It is written in Kotlin.
 * @author Sunny Bangale
 *
 */


package board


open class SquareBoardImplementation ( override val width: Int ) : SquareBoard
{
    var cells: Array<Array<Cell>> = arrayOf(arrayOf())

    /**
     * Overriden function to get a cell from the squared board. It will return the cell based on the coordinates and null, otherwise
     */
    override fun getCellOrNull(i: Int, j: Int): Cell? {
        when {
            i <= width && j <= width && i > 0 && j > 0 && isSafe(cells, i-1, j-1) -> return getCell(i, j)
            else -> return null
        }
    }

    /**
     * Overriden function to get a cell from the squared board. It will return the cell based on the coordinates
     */
    override fun getCell(i: Int, j: Int): Cell {
            return cells[i-1][j-1]
    }

    /**
     * Overriden function to get all the cells from the squared board. It will return all the cell based on the coordinates
     */
    override fun getAllCells(): Collection<Cell> = IntRange(1, width).flatMap { i -> IntRange(1, width).map { j -> getCell(i,j) } }

    /**
     * Overriden function to get a row of cells from the squared board. It will return the row of cells based on the row number
     */
    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        when{
            jRange.last > width -> return IntRange(jRange.first, width).map { j -> getCell(i , j) }
            else -> return jRange.map { j -> getCell(i , j) }
        }
    }

    /**
     * Overriden function to get a column of cells from the squared board. It will return the column of cells based on the column number
     */
    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        when{
            iRange.last > width -> return IntRange(iRange.first, width).map { i -> getCell(i , j) }
            else -> return iRange.map { i -> getCell(i , j) }
        }
    }

    /**
     * Overriden function to get all the neighbor of a cell from the squared board. It will return the neighbor of the cell based on the coordinates
     */
    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when(direction){
            Direction.UP -> getCellOrNull(i - 1, j)
            Direction.DOWN -> getCellOrNull(i + 1, j)
            Direction.LEFT -> getCellOrNull(i, j - 1)
            Direction.RIGHT -> getCellOrNull(i , j + 1)
        }
}

/**
 * Function to check if the coordinates lie within the board.
 */
fun isSafe(cells: Array<Array<Cell>>, i: Int, j: Int): Boolean =  i >= 0 && i < cells.size && j >= 0 && j < cells.get(0).size

/**
 * Funtion to create a squared board with cells based on the width provided
 */
fun createSquareBoard(width: Int): SquareBoard
{
    val squareBoard = SquareBoardImplementation(width)
    squareBoard.cells = createEmptySGBoard(width)

    return squareBoard
}

/**
 * Helper function to create an empty sqaureBoard for the create SquareBoard method
 */
fun createEmptySGBoard(width: Int): Array<Array<Cell>> {
    var board = arrayOf<Array<Cell>>()
    for (i in 1..width) {
        var internalBoard = arrayOf<Cell>()

        for (j in 1..width) {
            internalBoard = internalBoard + Cell(i, j)
        }
        board = board + internalBoard
    }

    return board
}

/**
 * This class Extends SquareBoardImplementation class and implements GameBoard Interface
 */
class GameBoardImplementation<T> (width: Int) : SquareBoardImplementation(width), GameBoard <T> {

    //Map of cell and the values associated to it
    val cellValues = mutableMapOf<Cell, T?>()

    /**
     * This function is a getter method for the value associated with a cell
     */
    override fun get(cell: Cell): T? = cellValues.get(cell)

    /**
     * This function is a setter method for the value associated with a cell
     */
    override fun set(cell: Cell, value: T?) {
        cellValues += cell to value
    }

    /**
     * This function filters cells based on the predicate provided by the user
     */
    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
            cellValues.filter { predicate(it.value) }.map { it.key }.toList()

    /**
     * This function finds cells based on the predicate provided by the user
     */
    override fun find(predicate: (T?) -> Boolean): Cell? =
            cellValues.entries.find { predicate(it.value)}?.key

    /**
     * This function returns true if it finds any cells based on the predicate provided by the user and false otherwise
     */
    override fun any(predicate: (T?) -> Boolean): Boolean =
        cellValues.any { predicate(it.value) }

    /**
     * This function returns true if it finds that all the cells satisfy a condition based on the predicate provided by the user and false otherwise
     */
    override fun all(predicate: (T?) -> Boolean): Boolean =
        cellValues.all { predicate(it.value) }


}


fun <T> createGameBoard(width: Int): GameBoard<T>
{
    val gameBoard = GameBoardImplementation<T>(width)
    gameBoard.cells = createEmptySGBoard(width)

    gameBoard.cells.forEach { it.forEach { cell: Cell -> gameBoard.cellValues += cell to null }}

    return gameBoard
}
