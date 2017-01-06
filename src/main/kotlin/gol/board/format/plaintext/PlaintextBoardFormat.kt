package gol.board.format.plaintext

import gol.board.Board
import gol.board.format.FormatBase

/**
 * Created by aknauss on 12/27/16.
 */
class PlaintextBoardFormat(val board: Board, val name: String = "") : FormatBase<PlaintextBoardFormat>() {

    override fun serialize(): String {
        var sb = StringBuilder()
                .append(PlaintextHeaderFormat(name).serialize())

        board.mapRows { y ->
            board.mapColumns { x ->
                sb.append(PlaintextCellFormat(board.cellAt(x, y)).serialize())
            }
            sb.append("\n")
        }

        return sb.toString()
    }
}
