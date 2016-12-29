package gol.board.format.plaintext

import gol.board.Board
import gol.board.format.FormatBase

/**
 * Created by aknauss on 12/27/16.
 */
class PlaintextBoardFormat(val board: Board, val name: String = "") : FormatBase<PlaintextBoardFormat>() {

    override fun serialize(): String {
        var sb = StringBuilder()
                .append(PlaintextHeaderFormat(name))

        for (y in 0..board.height-1) {
            for (x in 0..board.width-1) {
                sb.append(PlaintextCellFormat(board.cellAt(x, y)))

            }
            sb.append("\n")
        }
        return sb.toString()
    }
}
