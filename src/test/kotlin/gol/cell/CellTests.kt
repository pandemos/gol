package gol.cell

import gol.cell.Cell
import gol.cell.CellMortality
import org.junit.Assert.assertEquals
import org.junit.Test

/** A single cell
 * Created by aknauss on 12/21/16.
 */
class CellTests {

    /**
     * Can be alive
     */
    @Test
    fun canBeAlive() {
        val cell = LiveCell()
        assertEquals(CellMortality.Alive, cell.mortality)
    }

    /**
     * Can be dead
     */
    @Test
    fun canBeDead() {
        val cell = DeadCell()
        assertEquals(CellMortality.Dead, cell.mortality)
    }

}
