package gol.board.format.rle

import gol.board.format.FormatBase
import gol.cell.Cell
import gol.cell.CellMortality
import gol.cell.DeadCell
import gol.cell.LiveCell

/**
 * Created by aknauss on 12/27/16.
 */
class RleCellFormat(val cell: Cell) : FormatBase<RleCellFormat>() {

    override fun serialize(): String {
        return when {
            CellMortality.Alive == cell.mortality -> "o"
            CellMortality.Dead == cell.mortality -> "b"
            else -> "?"
        }
    }

    companion object {
        fun deserialize(repr: String): RleCellFormat {
            return when {
                repr == "b" -> RleCellFormat(DeadCell())
                repr == "o" -> RleCellFormat(LiveCell())
                else -> RleCellFormat(LiveCell()) // This is what the spec requires
            }
        }
    }
}
