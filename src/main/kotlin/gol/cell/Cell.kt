package gol.cell

/** Representation of a single cell
 * Created by aknauss on 12/21/16.
 */
interface Cell {
    val mortality: CellMortality

    fun isAlive(): Boolean
    fun isDead(): Boolean
}

abstract class CellBase : Cell {

    override fun isAlive(): Boolean {
        return mortality == CellMortality.Alive
    }

    override fun isDead(): Boolean {
        return mortality == CellMortality.Dead
    }
}

class DeadCell : CellBase() {
    override val mortality = CellMortality.Dead
}

class LiveCell : CellBase() {
    override val mortality: CellMortality = CellMortality.Alive
}
