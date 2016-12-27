package gol.board.format.rle

import gol.board.format.Format
import gol.cell.Cell
import gol.cell.CellMortality

/**
 * Created by aknauss on 12/27/16.
 */
class RleCellFormat(val cell: Cell) : Format<RleCellFormat> {
    override fun serialize(): String {
        return when {
            CellMortality.Alive == cell.mortality -> "o"
            CellMortality.Dead == cell.mortality -> "b"
            else -> "?"
        }
    }

    override fun deserialize(repr: String): RleCellFormat {
        throw UnsupportedOperationException("not implemented")
    }
}
