package gol.board.format.rle

import gol.board.format.FormatBase

/**
 * Created by aknauss on 12/27/16.
 */
class RleHeaderFormat(val width: Int, val height: Int, val rule: String) : FormatBase<RleHeaderFormat>() {

    override fun serialize(): String {
        return "x = $width, y = $height, rule = $rule\n"

    }

    companion object {

        fun deserialize(repr: String): RleHeaderFormat {
            val keyValuePairs = repr.split(',').map { entry ->
                entry.split('=')
            }.map { kvp ->
                listOf(kvp[0].trim(), kvp[1].trim())
            }
            return RleHeaderFormat(keyValuePairs[0][1].toInt(), keyValuePairs[1][1].toInt(), keyValuePairs[2][1])
        }
    }
}