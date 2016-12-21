package gol

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

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
}
