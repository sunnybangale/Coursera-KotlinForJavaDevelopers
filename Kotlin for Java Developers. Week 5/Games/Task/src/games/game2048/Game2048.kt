/**
 * This code is written for the programming assignment of the Kotlin Coursera Assignment -  Week 5
 * It has functions create a Game of 2048 and process the necessary actions involved in it.
 * 
 * The functions implemented are:
 * 1. Initialize
 * 2. CanMove
 * 3. HasWon
 * 4. ProcessMove
 * 5. Get
 * 6. Move ValuesInARowOrColumn
 * 7. MoveValues
 *
 * It is written in Kotlin.
 *
 * @author Sunny Bangale
 *
*/


package games.game2048

import board.Cell
import board.Direction.*
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
        Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {

    val randomCell : Pair<Cell, Int>? = initializer.nextValue(this)
    this[this.getCell(randomCell!!.first.i, randomCell!!.first.j)] = randomCell.second
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {

    val mergedList = rowOrColumn.map { this[it] }.moveAndMergeEqual { it -> it * 2 }
    if(mergedList.isEmpty()) return false

    rowOrColumn.forEachIndexed { index, cell -> this[cell] = if (index < mergedList.size) mergedList[index] else null }

    return mergedList.size < rowOrColumn.size
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {

    val boardRange = 1..width
    val directionRange = if (direction in listOf(UP, LEFT)) boardRange else boardRange.reversed()
    var isValueMoved = false

    when (direction) {
        UP, DOWN -> {
            for (i in boardRange) {
                val moved = moveValuesInRowOrColumn(getColumn(directionRange, i))
                isValueMoved = isValueMoved || moved
            }
        }
        LEFT, RIGHT -> {
            for (i in boardRange) {
                val moved = moveValuesInRowOrColumn(getRow(i, directionRange))
                isValueMoved = isValueMoved || moved
            }
        }
    }

    return isValueMoved

}
