package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */

class GameOfFifteen(val initializer: GameOfFifteenInitializer): Game
{
    val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val permutation = initializer.initialPermutation

        var permuteIterator = 0
        for(row in 1..board.width){
            for(column in 1..board.width){
                board[board.getCell(row,column)] = if (permuteIterator < permutation.size) permutation[permuteIterator++] else null
            }
        }
    }

    override fun canMove(): Boolean = true

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

    override fun processMove(direction: Direction) {
        val emptyCell = board.find { it == null }!!
        val neighbor: Cell

        board.apply { neighbor = emptyCell.getNeighbour(direction.reversed())!! }
        board[emptyCell] = board[neighbor]
        board[neighbor] = null
    }

    override fun get(i: Int, j: Int): Int? = board[board.getCell(i,j)]

}


fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = GameOfFifteen(initializer)