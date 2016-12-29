package gol.board.format.rle

import gol.board.Board
import gol.board.format.FormatBase
import gol.rule.Rules

/**
 * Created by aknauss on 12/27/16.
 */
class RleBoardFormat(val board: Board) : FormatBase<RleBoardFormat>() {
    override fun serialize(): String {
        return StringBuilder()
                .append(RleHeaderFormat(board.width, board.height, board.rules.ruleString))
                .append(RleRowFormat.fromBoard(board).map { row -> row }.joinToString(separator=""))
                .toString()
    }

    companion object {

        fun deserialize(repr: String): RleBoardFormat {

            val parts = repr.split("\n")
            var cellParts = parts.last().split("$")
            var header = RleHeaderFormat.deserialize(parts.first())

            val board = Board(header.width, header.height, rules = Rules(header.rule))

            (0 until cellParts.size).map { i ->
                RleRowFormat.deserialize(cellParts[i], i, board)
            }

            return RleBoardFormat(board)
        }
    }
}
