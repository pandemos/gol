package gol.board

import gol.cell.Cell
import gol.cell.CellMortality
import gol.cell.DeadCell
import gol.cell.LiveCell
import gol.rule.ConwayRules
import gol.rule.Rules
import java.security.SecureRandom
import java.util.*

/** Board containing the grid of cells
 * Created by aknauss on 12/21/16.
 */
class Board(val width: Int,
            val height: Int,
            var iteration: Int = 0,
            val rules: Rules = ConwayRules()) {
    private var cells = HashMap<String, Cell>()

    /**
     * Set the cell at the specified coordinate to the specified cell instance
     */
    fun setCellAt(x: Int, y: Int, cell: Cell) {
        checkCellRange(x, y)
        val cellKey = makeCellKey(x, y)
        if (cell.isDead()) {
            cells.remove(key = cellKey)
            return
        }
        cells.set(cellKey, cell)
    }

    /**
     * Get the cell at the specified coordinates
     */
    fun cellAt(x: Int, y: Int): Cell {
        checkCellRange(x, y)
        val cellKey = makeCellKey(x, y)
        val cell = cells[cellKey]
        if (cell != null) {
            return cell
        }
        return DeadCell()
    }

    /**
     * Advance the simulation to the next step
     */
    fun step() {
        rules.applyTo(this)
        iteration += 1
    }

    /**
     * Reset to iteration 0
     */
    fun reset() {
        iteration = 0
    }

    /**
     * Determine the number of living neighbors
     */
    fun livingNeighbors(x: Int, y: Int): Int {
        return listOf(
                neighborAbove(x, y),
                neighborBelow(x, y),
                neighborLeft(x, y),
                neighborRight(x, y),
                neighborUL(x, y),
                neighborUR(x, y),
                neighborLL(x, y),
                neighborLR(x, y)
        ).filter { m ->
            m == CellMortality.Alive
        }.size
    }

    /**
     * Determine mortality of neighbor above
     */
    fun neighborAbove(x: Int, y: Int): CellMortality {
        if (y == 0) {
            return CellMortality.Dead
        }
        return cellAt(x, y-1).mortality
    }

    /**
     * Determine mortality of neighbor above
     */
    fun neighborBelow(x: Int, y: Int): CellMortality {
        if (y == height-1) {
            return CellMortality.Dead
        }
        return cellAt(x, y+1).mortality
    }

    /**
     * Determine mortality of neighbor above
     */
    fun neighborLeft(x: Int, y: Int): CellMortality {
        if (x == 0) {
            return CellMortality.Dead
        }
        return cellAt(x-1, y).mortality
    }

    /**
     * Determine mortality of neighbor above
     */
    fun neighborRight(x: Int, y: Int): CellMortality {
        if (x == width-1) {
            return CellMortality.Dead
        }
        return cellAt(x+1, y).mortality
    }

    /**
     * Determine mortality of neighbor above left
     */
    fun neighborUL(x: Int, y: Int): CellMortality {
        if (x == 0 || y == 0) {
            return CellMortality.Dead
        }
        return cellAt(x-1, y-1).mortality
    }

    /**
     * Determine mortality of neighbor above right
     */
    fun neighborUR(x: Int, y: Int): CellMortality {
        if (x == width-1 || y == 0) {
            return CellMortality.Dead
        }
        return cellAt(x+1, y-1).mortality
    }

    /**
     * Determine mortality of neighbor below left
     */
    fun neighborLL(x: Int, y: Int): CellMortality {
        if (x == 0 || y == height-1) {
            return CellMortality.Dead
        }
        return cellAt(x-1, y+1).mortality
    }

    /**
     * Determine mortality of neighbor below right
     */
    fun neighborLR(x: Int, y: Int): CellMortality {
        if (x == width-1 || y == height-1) {
            return CellMortality.Dead
        }
        return cellAt(x+1, y+1).mortality
    }

    /**
     * Copy the cells of one board to another
     */
    fun copyFrom(other: Board) {
        assert(width == other.width && height == other.height)
        for (y in (0 until other.height)) {
            for (x in (0 until other.width)) {
                setCellAt(x, y, other.cellAt(x, y))
            }
        }
    }

    private fun makeCellKey(x: Int, y: Int): String {
        return "{$x},{$y}"
    }

    private fun checkCellRange(x: Int, y: Int) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw IndexOutOfBoundsException("Requested index is outside of board: $x, $y")
        }
    }

    companion object {

        private val randomGenerator: SecureRandom = SecureRandom()

        /**
         * Create a new board at iteration 0 and initialize it with up to n living cells
         */
        fun random(width: Int, height: Int, nInitialCells: Int): Board {
            val board = Board(width, height)
            for (i in 0..nInitialCells) {
                val x = randomGenerator.nextInt(width)
                val y = randomGenerator.nextInt(height)
                board.setCellAt(x, y, LiveCell())
            }
            return board
        }
    }
}

