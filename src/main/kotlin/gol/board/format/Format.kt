package gol.board.format

import gol.board.Board

/**
 * Created by aknauss on 12/27/16.
 */
interface Format<out T> {
    fun serialize(): String
}

abstract class FormatBase<out T> : Format<T> {

    override fun toString(): String {
        return serialize()
    }

}