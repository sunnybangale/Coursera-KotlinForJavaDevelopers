/**
 * This code is written for the programming assignment of the Kotlin Coursera Assignment -  Week 5
 * It has functions create a Game of Fifteen game and process the necessary actions involved in it.
 * 
 * The functions implemented are:
 * 1. Initialize
 * 2. CanMove
 * 3. HasWon
 * 4. ProcessMove
 * 5. Get
 *
 * It is written in Kotlin.
 *
 * @author Sunny Bangale
 *
*/

package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game

/**
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 *
 * Class to implement overriden methods for GameOfFifteen
 */
class GameOfFifteen(val initializer: GameOfFifteenInitializer): Game
{
    val board = createGameBoard<Int?>(4)

    /**
    * Function to initialize the game of fifteen board. 
    * It creates a 4x4 board of cells and inserts numbers from 1 to 15 randomly.
    */
    override fun initialize() {
        val permutation = initializer.initialPermutation

        var permuteIterator = 0
        for(row in 1..board.width){
            for(column in 1..board.width){
                board[board.getCell(row,column)] = if (permuteIterator < permutation.size) permutation[permuteIterator++] else null
            }
        }
    }

    /**
    * Function to determine if a move can be done the game of fifteen board. 
    */
    override fun canMove(): Boolean = true

    /**
    * Function to determine if a player has won the game of fifteen board. 
    */
    override fun hasWon(): Boolean {
        var won = true
        var iterator = 1

        for (row in 1..board.width) {
            for (column in 1..board.width) {
                if (get(row, column) != iterator && iterator != board.width * board.width) {
                    won = false
                    break
                }
                iterator++
            }
        }

        return won && get(board.width, board.width) == null
    }

    /**
    * Function to process a move can be done the game of fifteen board. 
    * A cell is moved to an adjacent empty spot when clicked by the user. 
    * It can move only in 4 directions: Up, Down, Right and Left.
    */
    override fun processMove(direction: Direction) {
        val emptyCell = board.find { it == null }!!
        val neighbor: Cell

        board.apply { neighbor = emptyCell.getNeighbour(direction.reversed())!! }
        board[emptyCell] = board[neighbor]
        board[neighbor] = null
    }

    /**
    * Function to get a cell in the game of fifteen board. 
    */
    override fun get(i: Int, j: Int): Int? = board[board.getCell(i,j)]

}

/**
* Function to randomly initilialize the cells in the game of fifteen board. 
*/    
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = GameOfFifteen(initializer)
