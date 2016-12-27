package gol.board.format.rle

import gol.board.Board
import gol.board.format.Format
import gol.cell.Cell
import gol.cell.CellMortality

/**
 * Created by aknauss on 12/27/16.
 */
class RleRowFormat(val board: Board, val y: Int, val last: Boolean = false) : Format<RleRowFormat> {

    override fun serialize(): String {

        var sb = StringBuilder()

        var runSize = 1
        (0 until board.width).map { x ->
            val thisCell = board.cellAt(x, y)
            var nextCell: Cell? = null
            if (x < board.width-1) {
                nextCell = board.cellAt(x + 1, y)
            }
            when {
                nextCell == null -> {
                    if (runSize > 1) {
                        sb.append("${runSize}${RleCellFormat(thisCell).serialize()}")
                    }
                    else {
                        sb.append("${RleCellFormat(thisCell).serialize()}")
                    }
                }
                thisCell.mortality == nextCell.mortality -> {
                    runSize += 1
                    null
                }
                else -> {
                    if (runSize > 1) {
                        sb.append("${runSize}${RleCellFormat(thisCell).serialize()}")
                        runSize = 1
                    }
                    else {
                        sb.append("${RleCellFormat(thisCell).serialize()}")
                    }
                }
            }

        }

        while (sb.last() == 'b' || (sb.last() >= '0' && sb.last() <= '9')) {
            sb.deleteCharAt(sb.length-1)
        }

        sb.append(when {
            last == true -> "!"
            else -> "$"
        })

        return sb.toString()
    }

    override fun deserialize(repr: String): RleRowFormat {
        throw UnsupportedOperationException("not implemented")
    }

    companion object {

        /**
         * Given a board, create a row format for each of its rows
         */
        fun fromBoard(board: Board): List<RleRowFormat> {
            return (0 until board.height).map { y ->
                RleRowFormat(board, y, y == board.height-1)
            }
        }

    }
}
