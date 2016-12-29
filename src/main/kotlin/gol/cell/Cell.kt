package gol.cell

/** Representation of a single cell
 * Created by aknauss on 12/21/16.
 */
interface Cell {
    val mortality: CellMortality
}

class DeadCell : Cell {
    override val mortality = CellMortality.Dead
}

class LiveCell : Cell {
    override val mortality: CellMortality = CellMortality.Alive
}
