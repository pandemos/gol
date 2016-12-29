package gol.board.format.rle

import gol.board.format.FormatBase
import java.security.InvalidKeyException

/**
 * Created by aknauss on 12/27/16.
 */
class RleHeaderFormat(val width: Int, val height: Int, val rule: String) : FormatBase<RleHeaderFormat>() {

    override fun serialize(): String {
        return "$X = $width, $Y = $height, $RULE = $rule\n"

    }

    companion object {

        val X = "x"
        val Y = "y"
        val RULE = "rule"

        fun deserialize(repr: String): RleHeaderFormat {
            val keyValuePairs = repr.split(',').map { entry ->
                entry.split('=')
            }.map { kvp ->
                Pair(kvp[0].trim(), kvp[1].trim())
            }.toMap()
            return RleHeaderFormat(
                    keyValuePairs.getOrElse(X, { throw InvalidKeyException(X)}).toInt(),
                    keyValuePairs.getOrElse(Y, { throw InvalidKeyException(Y)}).toInt(),
                    keyValuePairs.getOrElse(RULE, { throw InvalidKeyException(RULE)}))
        }
    }
}