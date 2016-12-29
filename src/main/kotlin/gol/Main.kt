package gol

import gol.board.Board
import gol.board.format.plaintext.PlaintextBoardFormat
import gol.board.format.rle.RleBoardFormat
import gol.cell.LiveCell

/**
 * Created by aknauss on 12/21/16.
 */
fun main(args: Array<String>) {
    val width = 100
    val height = 100
    val nCells = 750
    val nSteps = 100000
    val board = Board.random(width, height, nCells)
    board.rules.whenApplied { Board ->
        println(board.iteration)
    }
    for (i in 0..nSteps) {
        board.step()
    }
    println(PlaintextBoardFormat(board, "$width x $height with $nCells random living cells after $nSteps steps"))
    println(RleBoardFormat(board))
    println()
    println()

    val board2 = Board(3, 3)
    board2.setCellAt(1, 0, LiveCell())
    board2.setCellAt(2, 1, LiveCell())
    board2.setCellAt(0, 2, LiveCell())
    board2.setCellAt(1, 2, LiveCell())
    board2.setCellAt(2, 2, LiveCell())

    println(PlaintextBoardFormat(board2, "Glider"))
    println(RleBoardFormat(board2))
}

