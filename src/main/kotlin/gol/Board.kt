package gol

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
        val cellKey = makeCellKey(x, y)
        cells.set(cellKey, cell)
    }

    /**
     * Get the cell at the specified coordinates
     */
    fun cellAt(x: Int, y: Int): Cell {
        val cellKey = makeCellKey(x, y)
        val cell = cells[cellKey]
        if (cell != null) {
            return cell
        }
        setCellAt(x, y, Cell(CellMortality.Dead))
        return cellAt(x, y)
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

    private fun makeCellKey(x: Int, y: Int): String {
        return "{$x},{$y}"
    }
}