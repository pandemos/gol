package gol.board

import gol.board.Board
import gol.cell.Cell
import gol.cell.CellMortality
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlin.test.*

/** Represents a grid
 * Created by aknauss on 12/21/16.
 */
class BoardTests {

    @Test
    fun hasWidthAndHeight() {
        val board = Board(width = 10, height = 10)
        assertEquals(10, board.width)
        assertEquals(10, board.height)
    }

    /**
     * Any cell can be retrieved from the board by specifying an x and y coordinate
     */
    @Test
    fun canRetrieveCellByCoordinate() {
        val cell = Cell(CellMortality.Alive)
        val board = Board(width = 10, height = 10)

        // Living cell
        board.setCellAt(x = 3, y = 3, cell = cell)
        assertEquals(cell, board.cellAt(3, 3))

        // Dead cell
        assertEquals(CellMortality.Dead, board.cellAt(7, 7).mortality)
    }

    /**
     * Only store living cells, others are assumed dead
     */
    @Test
    fun onlyStoreLivingCells() {
        val board = Board(width = 3, height = 3)
        val cell00 = Cell(CellMortality.Dead)
        val cell10 = Cell(CellMortality.Dead)
        val cell20 = Cell(CellMortality.Dead)
        val cell01 = Cell(CellMortality.Dead)
        val cell11 = Cell(CellMortality.Alive)
        val cell21 = Cell(CellMortality.Dead)
        val cell02 = Cell(CellMortality.Dead)
        val cell12 = Cell(CellMortality.Dead)
        val cell22 = Cell(CellMortality.Dead)

        board.setCellAt(0, 0, cell00)
        board.setCellAt(1, 0, cell10)
        board.setCellAt(2, 0, cell20)
        board.setCellAt(0, 1, cell01)
        board.setCellAt(1, 1, cell11)
        board.setCellAt(2, 1, cell21)
        board.setCellAt(0, 2, cell02)
        board.setCellAt(1, 2, cell12)
        board.setCellAt(2, 2, cell22)

        assertFalse(cell00 === board.cellAt(0, 0))
        assertFalse(cell10 === board.cellAt(1, 0))
        assertFalse(cell20 === board.cellAt(2, 0))
        assertFalse(cell01 === board.cellAt(0, 1))
        assertTrue(cell11 === board.cellAt(1, 1))
        assertFalse(cell21 === board.cellAt(2, 1))
        assertFalse(cell02 === board.cellAt(0, 2))
        assertFalse(cell12 === board.cellAt(1, 2))
        assertFalse(cell22 === board.cellAt(2, 2))


    }

    /**
     * iteration count starts at 0
     */
    @Test
    fun iterationCountStartsAt0() {
        val board = Board(10, 10)
        assertEquals(0, board.iteration)
    }
    /**
     * has an iteration count
     */
    @Test
    fun hasIterationCount() {
        val board = Board(10, 10, 4)
        assertEquals(4, board.iteration)
    }

    /**
     * Iteration count can be incremented
     */
    @Test
    fun iterationCountCanBeIncremented() {
        val board = Board(10, 10)
        assertEquals(0, board.iteration)
        board.step()
        assertEquals(1, board.iteration)
        board.step()
        assertEquals(2, board.iteration)
    }

    /**
     * When the iteration count is incremented, rules are applied to all cells at cone
     */
    @Test
    fun incrementingIterationCountAppliesRulesToAllCells() {
        val board = Board(10, 10)
        val rules = board.rules
        var wasApplied = false
        rules.whenApplied { board: Board -> wasApplied = true }
        board.step()
        assertTrue(wasApplied)
    }

    /**
     * Iteration count can be reset
     */
    @Test
    fun iterationCountCanBeRest() {
        val board = Board(10, 10, 4)
        board.reset()
        assertEquals(0, board.iteration)
    }

    /**
     * Attempting to access a cell outside the board throws an exception
     */
    @Test
    fun outOfRangeCell() {
        val board = Board(10, 10)

        assertFailsWith(IndexOutOfBoundsException::class) {
            board.setCellAt(-1, -1, Cell(CellMortality.Alive))
        }

        assertFailsWith(IndexOutOfBoundsException::class) {
            board.setCellAt(board.width, board.height, Cell(CellMortality.Alive))
        }

        assertFailsWith(IndexOutOfBoundsException::class) {
            board.cellAt(-1, -1)
        }

        assertFailsWith(IndexOutOfBoundsException::class) {
            board.cellAt(board.width, board.height)
        }
    }

    /**
     * for any cell, determine the number of living neighbors when no neighbors are living
     */
    @Test
    fun determineNumberOfLiving0Neighbors() {

        val board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        assertEquals(0, board.livingNeighbors(1, 1))
    }
/*
    /**
     * for any cell, determine the number of living neighbors
     */
    @Test
    fun determineNumberOfLiving1NeighborEdge() {

        var board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(1, 0, Cell(CellMortality.Alive))
        assertEquals(1, board.livingNeighbors(1, 1))

        board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(0, 1, Cell(CellMortality.Alive))
        assertEquals(1, board.livingNeighbors(1, 1))

        board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(2, 1, Cell(CellMortality.Alive))
        assertEquals(1, board.livingNeighbors(1, 1))

        board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(1, 2, Cell(CellMortality.Alive))
        assertEquals(1, board.livingNeighbors(1, 1))

    }

    /**
     * for any cell, determine the number of living neighbors
     */
    @Test
    fun determineNumberOfLiving2NeighborEdge() {

        var board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(1, 0, Cell(CellMortality.Alive))
        board.setCellAt(0, 1, Cell(CellMortality.Alive))
        assertEquals(2, board.livingNeighbors(1, 1))

        board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(2, 1, Cell(CellMortality.Alive))
        board.setCellAt(1, 2, Cell(CellMortality.Alive))
        assertEquals(2, board.livingNeighbors(1, 1))

    }
    /**
     * for any cell, determine the number of living neighbors
     */
    @Test
    fun determineNumberOfLiving1NeighborCorner() {

        var board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(0, 0, Cell(CellMortality.Alive))
        assertEquals(1, board.livingNeighbors(1, 1))

        board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(0, 2, Cell(CellMortality.Alive))
        assertEquals(1, board.livingNeighbors(1, 1))

        board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(2, 0, Cell(CellMortality.Alive))
        assertEquals(1, board.livingNeighbors(1, 1))

        board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(2, 2, Cell(CellMortality.Alive))
        assertEquals(1, board.livingNeighbors(1, 1))

    }

    /**
     * for any cell, determine the number of living neighbors
     */
    @Test
    fun determineNumberOfLiving2NeighborCorner() {

        var board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(0, 0, Cell(CellMortality.Alive))
        board.setCellAt(2, 2, Cell(CellMortality.Alive))
        assertEquals(2, board.livingNeighbors(1, 1))

        board = Board(3, 3)
        board.setCellAt(1, 1, Cell(CellMortality.Alive))
        board.setCellAt(2, 0, Cell(CellMortality.Alive))
        board.setCellAt(0, 2, Cell(CellMortality.Alive))
        assertEquals(2, board.livingNeighbors(1, 1))

    }
    */
}
