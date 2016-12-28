package gol.board

import gol.cell.Cell
import gol.cell.CellMortality
import gol.rule.Rules
import java.security.SecureRandom
import java.util.*

/** Board containing the grid of cells
 * Created by aknauss on 12/21/16.
 */
class Board(val width: Int,
            val height: Int,
            var iteration: Int = 0,
            val rules: Rules = Rules()) {
    private var cells = HashMap<String, Cell>()

    /**
     * Set the cell at the specified coordinate to the specified cell instance
     */
    fun setCellAt(x: Int, y: Int, cell: Cell) {
        if (cell.mortality == CellMortality.Dead) {
            return
        }
        checkCellRange(x, y)
        val cellKey = makeCellKey(x, y)
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
        return Cell(CellMortality.Dead)
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
        return 0
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
                board.setCellAt(x, y, Cell(CellMortality.Alive))
            }
            return board
        }

    }
}

