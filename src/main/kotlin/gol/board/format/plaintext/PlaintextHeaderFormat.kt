package gol.board.format.plaintext

import gol.board.format.FormatBase

/**
 * Created by aknauss on 12/27/16.
 */
class PlaintextHeaderFormat(val name: String) : FormatBase<PlaintextHeaderFormat>() {

    override fun serialize(): String {
        return "!Name: $name\n!\n"
    }
}