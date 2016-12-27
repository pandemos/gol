package gol

import gol.board.Board
import gol.board.format.plaintext.PlaintextBoardFormat
import gol.cell.Cell
import gol.cell.CellMortality
import gol.board.format.plaintext.PlaintextCellFormat
import gol.board.format.plaintext.PlaintextHeaderFormat
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by aknauss on 12/27/16.
 */

/** Tests for representation of automaton rules in RLE
 * See http://conwaylife.com/wiki/Rulestring#Rules
 * Created by aknauss on 12/21/16.
 */
class PlaintextBoardFormatTests {

    /**
     * A header is serialized as `!Name: "name"` followed by a line containing only `!`
     */
    @Test
    fun headerSerialization() {
        assertEquals("!Name: Example\n!\n", PlaintextHeaderFormat(name="Example").serialize())
    }

    /**
     * A living cell is serialized as `O` (capital letter 'o')
     */
    @Test
    fun livingCellSerialization() {
        assertEquals("O", PlaintextCellFormat(Cell(CellMortality.Alive)).serialize())
    }

    /**
     * A dead cell is serialized as a `.`
     */
    @Test
    fun deadCellSerialization() {
        assertEquals(".", PlaintextCellFormat(Cell(CellMortality.Dead)).serialize())
    }

    /**
     * A board is serialized as a header followed by visually-representative cells
     */
    @Test
    fun boardSerialization() {
        val board: Board = Board(3, 3)
        board.setCellAt(1, 0, Cell(CellMortality.Alive))

        board.setCellAt(2, 1, Cell(CellMortality.Alive))

        board.setCellAt(0, 2, Cell(CellMortality.Alive))
        board.setCellAt(1, 2, Cell(CellMortality.Alive))
        board.setCellAt(2, 2, Cell(CellMortality.Alive))
        assertEquals("!Name: name\n!\n.O.\n..O\nOOO\n", PlaintextBoardFormat(board, "name").serialize())
    }

}