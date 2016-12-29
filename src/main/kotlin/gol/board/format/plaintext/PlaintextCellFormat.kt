package gol.board.format.plaintext

import gol.board.format.FormatBase
import gol.cell.Cell

/**
 * Created by aknauss on 12/27/16.
 */
class PlaintextCellFormat(val cell: Cell) : FormatBase<PlaintextCellFormat>() {

    override fun serialize(): String {
        return when {
            cell.isAlive() -> "O"
            cell.isDead() -> "."
            else -> "?"
        }
    }
}