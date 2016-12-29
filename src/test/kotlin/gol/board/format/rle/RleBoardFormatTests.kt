package gol.board.format.rle

import gol.board.Board
import gol.board.format.plaintext.PlaintextBoardFormat
import gol.cell.Cell
import gol.cell.CellMortality
import gol.board.format.plaintext.PlaintextCellFormat
import gol.board.format.plaintext.PlaintextHeaderFormat
import gol.board.format.rle.RleBoardFormat
import gol.board.format.rle.RleCellFormat
import gol.board.format.rle.RleHeaderFormat
import gol.board.format.rle.RleRowFormat
import gol.cell.DeadCell
import gol.cell.LiveCell
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by aknauss on 12/27/16.
 */

/** Tests for representation of automaton rules in RLE
 * See http://conwaylife.com/wiki/Rulestring#Rules
 * Created by aknauss on 12/21/16.
 */
class RleBoardFormatTests {

    /**
     * A header is serialized as `!Name: "name"` followed by a line containing only `!`
     */
    @Test
    fun headerSerialization() {
        assertEquals("x = 3, y = 4, rule = B3/S23\n", RleHeaderFormat(width=3, height=4, rule="B3/S23"))
    }

    /**
     * A living cell is serialized as `O` (capital letter 'o')
     */
    @Test
    fun livingCellSerialization() {
        assertEquals("o", RleCellFormat(LiveCell()))
    }

    /**
     * A dead cell is serialized as a `.`
     */
    @Test
    fun deadCellSerialization() {
        assertEquals("b", RleCellFormat(DeadCell()))
    }

    /**
     * A row is serialized by serializing each cell
     */
    @Test
    fun rowSerializationDirect() {
        val board: Board = Board(3, 1)
        board.setCellAt(0, 0, LiveCell())
        board.setCellAt(2, 0, LiveCell())
        assertEquals("obo$", RleRowFormat(board, 0))
    }

    /**
     * The last row ends with `!` instead of `$`
     */
    @Test
    fun lastRowSerialization() {
        val board: Board = Board(3, 1)
        board.setCellAt(0, 0, LiveCell())
        board.setCellAt(2, 0, LiveCell())
        assertEquals("obo!", RleRowFormat(board, 0, last=true))
    }

    /**
     * A row is serialized by serializing each cell,
     * collapsing runs of additional cells
     */
    @Test
    fun rowSerializationCollapsed() {
        val board: Board = Board(3, 1)
        board.setCellAt(1, 0, LiveCell())
        board.setCellAt(2, 0, LiveCell())
        assertEquals("b2o$", RleRowFormat(board, 0))

        board.setCellAt(0, 0, LiveCell())
        board.setCellAt(1, 0, LiveCell())
        board.setCellAt(2, 0, LiveCell())
        assertEquals("3o$", RleRowFormat(board, 0))
    }

    /**
     * A row is serialized by serializing each cell,
     * dropping trailing dead cells
     */
    @Test
    fun rowSerializationDropsTrailingDeadCells() {
        val board: Board = Board(3, 1)
        board.setCellAt(0, 0, LiveCell())
        assertEquals("o$", RleRowFormat(board, 0))
    }

    /**
     * A board is serialized as a header followed by visually-representative cells
     */
    @Test
    fun boardSerialization() {
        val board: Board = Board(3, 3)
        board.setCellAt(1, 0, LiveCell())

        board.setCellAt(2, 1, LiveCell())

        board.setCellAt(0, 2, LiveCell())
        board.setCellAt(1, 2, LiveCell())
        board.setCellAt(2, 2, LiveCell())
        assertEquals("x = 3, y = 3, rule = B3/S23\nbo$2bo$3o!", RleBoardFormat(board))
    }

    /**
     * Header can be deserialized from string
     */
    @Test
    fun headerDeserialization() {
        assertEquals(3, RleHeaderFormat.deserialize("x = 3, y = 4, rule = B3/S23\n").width)
        assertEquals(4, RleHeaderFormat.deserialize("x = 3, y = 4, rule = B3/S23\n").height)
        assertEquals("B3/S23", RleHeaderFormat.deserialize("x = 3, y = 4, rule = B3/S23\n").rule)
    }

    /**
     * Cells can be deserialized from string
     */
    @Test
    fun cellDeserialization() {
        assertEquals(CellMortality.Alive, RleCellFormat.deserialize("o").cell.mortality)
        assertEquals(CellMortality.Dead, RleCellFormat.deserialize("b").cell.mortality)
    }

    /**
     * Rows of cells can be deserialized from string
     */
    @Test
    fun rowDeserialization() {
        var board = Board(3, 1)
        assertEquals(CellMortality.Alive, RleRowFormat.deserialize("ooo$", 0, board).board.cellAt(0, 0).mortality)
        assertEquals(CellMortality.Alive, RleRowFormat.deserialize("ooo$", 0, board).board.cellAt(1, 0).mortality)
        assertEquals(CellMortality.Alive, RleRowFormat.deserialize("ooo$", 0, board).board.cellAt(2, 0).mortality)

        board = Board(3, 1)
        assertEquals(CellMortality.Alive, RleRowFormat.deserialize("obo$", 0, board).board.cellAt(0, 0).mortality)
        assertEquals(CellMortality.Dead, RleRowFormat.deserialize("obo$", 0, board).board.cellAt(1, 0).mortality)
        assertEquals(CellMortality.Alive, RleRowFormat.deserialize("obo$", 0, board).board.cellAt(2, 0).mortality)
    }

    /**
     * Board can be deserialized from string
     */
    @Test
    fun boardDeserializationPartialRow() {
        var deserializedBoard = RleBoardFormat.deserialize("x = 3, y = 1, rule = B3/S23\nbo!").board
        assertEquals(CellMortality.Dead, deserializedBoard.cellAt(0, 0).mortality)
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(1, 0).mortality)
        assertEquals(CellMortality.Dead, deserializedBoard.cellAt(2, 0).mortality)
    }
    /**
     * Board can be deserialized from string
     */
    @Test
    fun boardDeserializationMergedRow() {
        var deserializedBoard = RleBoardFormat.deserialize("x = 3, y = 1, rule = B3/S23\n2bo!").board
        assertEquals(CellMortality.Dead, deserializedBoard.cellAt(0, 0).mortality)
        assertEquals(CellMortality.Dead, deserializedBoard.cellAt(1, 0).mortality)
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(2, 0).mortality)
    }
    /**
     * Board can be deserialized from string
     */
    @Test
    fun boardDeserializationFullRow() {
        var deserializedBoard = RleBoardFormat.deserialize("x = 3, y = 3, rule = B3/S23\n3o!").board
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(0, 0).mortality)
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(1, 0).mortality)
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(2, 0).mortality)
    }
    /**
     * Board can be deserialized from string
     */
    @Test
    fun boardDeserialization() {
        var deserializedBoard = RleBoardFormat.deserialize("x = 3, y = 3, rule = B3/S23\nbo$2bo$3o!").board
        assertEquals(CellMortality.Dead, deserializedBoard.cellAt(0, 0).mortality)
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(1, 0).mortality)
        assertEquals(CellMortality.Dead, deserializedBoard.cellAt(2, 0).mortality)
        assertEquals(CellMortality.Dead, deserializedBoard.cellAt(0, 1).mortality)
        assertEquals(CellMortality.Dead, deserializedBoard.cellAt(1, 1).mortality)
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(2, 1).mortality)
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(0, 2).mortality)
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(1, 2).mortality)
        assertEquals(CellMortality.Alive, deserializedBoard.cellAt(2, 2).mortality)

    }
}