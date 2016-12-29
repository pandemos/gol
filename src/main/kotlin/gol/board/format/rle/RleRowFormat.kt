package gol.board.format.rle

import gol.board.Board
import gol.board.format.FormatBase
import gol.cell.Cell
import gol.cell.DeadCell
import gol.cell.LiveCell

/**
 * Created by aknauss on 12/27/16.
 */
class RleRowFormat(val board: Board, val y: Int, val last: Boolean = false) : FormatBase<RleRowFormat>() {

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
                        sb.append("${runSize}${RleCellFormat(thisCell)}")
                    }
                    else {
                        sb.append("${RleCellFormat(thisCell)}")
                    }
                }
                thisCell.mortality == nextCell.mortality -> {
                    runSize += 1
                    null
                }
                else -> {
                    if (runSize > 1) {
                        sb.append("${runSize}${RleCellFormat(thisCell)}")
                        runSize = 1
                    }
                    else {
                        sb.append(RleCellFormat(thisCell))
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

    @Suppress("IMPLICIT_CAST_TO_ANY")
    companion object {

        /**
         * Given a board, create a row format for each of its rows
         */
        fun fromBoard(board: Board): List<RleRowFormat> {
            return (0 until board.height).map { y ->
                RleRowFormat(board, y, y == board.height-1)
            }
        }

        fun deserialize(repr: String, index: Int, board: Board): RleRowFormat {
            var cells = mutableListOf<RleCellFormat>()
            var parts = mutableListOf<String>()
            var countString = ""
            repr.toCharArray().map { c ->
                when {
                    c == 'b' && countString == "" -> parts.add(c.toString())
                    c == 'b' && countString != "" -> {
                        parts.add("${countString.toInt()}$c")
                        countString = ""
                    }
                    c == 'o' && countString == "" -> parts.add(c.toString())
                    c == 'o' && countString != "" -> {
                        parts.add("${countString.toInt()}$c")
                        countString = ""
                    }
                    else -> countString += c.toString()
                }
            }

            parts.map { s ->
                 when {
                     s == "b" -> cells.add(RleCellFormat(DeadCell()))
                     s == "o" -> cells.add(RleCellFormat(LiveCell()))
                     s.last() == 'b' -> {
                         (0 until s.substring(0, s.length-1).toInt()).map {
                             cells.add(RleCellFormat(DeadCell()))
                         }
                     }
                     s.last() == 'o' -> {
                         (0 until s.substring(0, s.length-1).toInt()).map {
                             cells.add(RleCellFormat(LiveCell()))
                         }
                     }
                     else -> null
                 }
            }

            (0 until cells.size).map { x ->
                board.setCellAt(x, index, cells[x].cell)
            }

            return RleRowFormat(board, index)
        }

    }
}
