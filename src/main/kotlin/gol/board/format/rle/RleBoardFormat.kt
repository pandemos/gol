package gol.board.format.rle

import gol.board.Board
import gol.board.format.Format

/**
 * Created by aknauss on 12/27/16.
 */
class RleBoardFormat(val board: Board) : Format<RleBoardFormat> {
    override fun serialize(): String {
        return StringBuilder()
                .append(RleHeaderFormat(board.width, board.height, board.rules.ruleString).serialize())
                .append(RleRowFormat.fromBoard(board).map { row -> row.serialize() }.joinToString(separator=""))
                .toString()
    }

    override fun deserialize(repr: String): RleBoardFormat {
        throw UnsupportedOperationException("not implemented")
    }
}
