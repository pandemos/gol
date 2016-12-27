package gol

import gol.board.Board
import gol.board.format.plaintext.PlaintextBoardFormat
import gol.cell.Cell
import gol.cell.CellMortality

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
    board2.setCellAt(1, 0, Cell(CellMortality.Alive))
    board2.setCellAt(2, 1, Cell(CellMortality.Alive))
    board2.setCellAt(0, 2, Cell(CellMortality.Alive))
    board2.setCellAt(1, 2, Cell(CellMortality.Alive))
    board2.setCellAt(2, 2, Cell(CellMortality.Alive))

    println(PlaintextBoardFormat(board2, "Glider"))
}

