package gol.board.format

import gol.board.Board

/**
 * Created by aknauss on 12/27/16.
 */
interface Format<out T> {

    fun serialize(): String
    fun deserialize(repr: String): T

}