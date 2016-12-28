package gol.board.format.plaintext

import gol.board.format.Format

/**
 * Created by aknauss on 12/27/16.
 */
class PlaintextHeaderFormat(val name: String) : Format<PlaintextHeaderFormat> {

    override fun serialize(): String {
        return "!Name: $name\n!\n"
    }

    override fun toString(): String {
        return serialize()
    }
}