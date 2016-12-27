package gol.board.format.rle

import gol.board.format.Format

/**
 * Created by aknauss on 12/27/16.
 */
class RleHeaderFormat(val width: Int, val height: Int, val rule: String) : Format<RleHeaderFormat> {

    override fun serialize(): String {
        return "x = $width, y = $height, rule = $rule\n"

    }

    override fun deserialize(repr: String): RleHeaderFormat {
        throw UnsupportedOperationException("not implemented")
    }
}