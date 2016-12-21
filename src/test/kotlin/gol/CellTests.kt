package gol

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
        val cell = Cell(CellMortality.Alive)
        assertEquals(CellMortality.Alive, cell.mortality)
    }

    /**
     * Can be dead
     */
    @Test
    fun canBeDead() {
        val cell = Cell(CellMortality.Dead)
        assertEquals(CellMortality.Dead, cell.mortality)
    }

}
