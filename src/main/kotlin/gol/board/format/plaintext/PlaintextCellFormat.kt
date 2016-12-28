package gol.board.format.plaintext

import gol.board.Board
import gol.board.format.Format
import gol.cell.Cell
import gol.cell.CellMortality

/**
 * Created by aknauss on 12/27/16.
 */
class PlaintextCellFormat(val cell: Cell) : Format<PlaintextCellFormat> {

    override fun serialize(): String {
        return when {
            cell.mortality == CellMortality.Alive -> "O"
            cell.mortality == CellMortality.Dead -> "."
            else -> "?"
        }
    }

    override fun toString(): String {
        return serialize()
    }
}