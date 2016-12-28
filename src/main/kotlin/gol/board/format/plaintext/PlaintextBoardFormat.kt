package gol.board.format.plaintext

import gol.board.format.Format
import gol.board.Board

/**
 * Created by aknauss on 12/27/16.
 */
class PlaintextBoardFormat(val board: Board, val name: String = "") : Format<PlaintextBoardFormat> {

    override fun serialize(): String {
        var sb = StringBuilder()
                .append(PlaintextHeaderFormat(name).serialize())

        for (y in 0..board.height-1) {
            for (x in 0..board.width-1) {
                sb.append(PlaintextCellFormat(board.cellAt(x, y)).serialize())

            }
            sb.append("\n")
        }
        return sb.toString()
    }

    override fun toString(): String {
        return serialize()
    }
}
