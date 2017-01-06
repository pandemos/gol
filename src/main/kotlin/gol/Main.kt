package gol

import gol.board.Board
import gol.board.format.plaintext.PlaintextBoardFormat
import gol.board.format.rle.RleBoardFormat
import gol.cell.LiveCell

/**
 * Created by aknauss on 12/21/16.
 */
fun main(args: Array<String>) {
    val width = 300
    val height = 300
    val nCells = 7900
    val nSteps = 100
    var board = Board.random(width, height, nCells)
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


    board = Board(3, 3)
    board.setCellAt(0, 1, LiveCell())
    board.setCellAt(1, 1, LiveCell())
    board.setCellAt(2, 1, LiveCell())
    println(PlaintextBoardFormat(board, "Blinker"))
    board.step()
    println(PlaintextBoardFormat(board, "Blinker"))
    board.step()
    println(PlaintextBoardFormat(board, "Blinker"))

    board = Board(5, 5)
    board.setCellAt(1, 0, LiveCell())
    board.setCellAt(2, 1, LiveCell())
    board.setCellAt(0, 2, LiveCell())
    board.setCellAt(1, 2, LiveCell())
    board.setCellAt(2, 2, LiveCell())

    println(PlaintextBoardFormat(board, "Glider"))
    board.step()
    println(PlaintextBoardFormat(board, "Glider"))
    board.step()
    println(PlaintextBoardFormat(board, "Glider"))
    board.step()
    println(PlaintextBoardFormat(board, "Glider"))
    board.step()
    println(PlaintextBoardFormat(board, "Glider"))
    //println(RleBoardFormat(board))
}

