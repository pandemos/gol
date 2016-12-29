package gol

import gol.board.Board
import gol.board.format.plaintext.PlaintextBoardFormat
import gol.cell.Cell
import gol.cell.CellMortality
import gol.cell.LiveCell

/**
 * Created by aknauss on 12/21/16.
 */
fun main(args: Array<String>) {
    val board = Board.random(100, 100, 750)
    board.rules.whenApplied { Board ->
        println(board.iteration)
    }
    for (i in 0..100000) {
        board.step()
    }
    println(PlaintextBoardFormat(board, "100 x 100 random walk"))

    val board2 = Board(3, 3)
    board2.setCellAt(1, 0, LiveCell())
    board2.setCellAt(2, 1, LiveCell())
    board2.setCellAt(0, 2, LiveCell())
    board2.setCellAt(1, 2, LiveCell())
    board2.setCellAt(2, 2, LiveCell())

    println(PlaintextBoardFormat(board2, "Glider"))
}

