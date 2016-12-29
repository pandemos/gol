package gol.rule.format

import gol.rule.format.RleRuleFormat
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertTrue

/** Tests for representation of automaton rules in RLE
 * See http://conwaylife.com/wiki/Rulestring#Rules
 * Created by aknauss on 12/21/16.
 */
class RleRuleFormatTests {

    /**
     * Can get the rules serialized as a string
     */
    @Test
    fun canBeSerializedAsString() {
        val ruleFormat = RleRuleFormat("B3/S23")
        assertEquals("B3/S23", ruleFormat.serialize())
    }

    /**
     * Must have a list of number of living neighbors to cause birth
     */
    @Test
    fun listBirthNeighbors() {
        var ruleFormat = RleRuleFormat("B3/S23")
        assertTrue(ruleFormat.valid)
        assertEquals('3', ruleFormat.birthNeighbors[0])

        ruleFormat = RleRuleFormat("B137/S2459")
        assertTrue(ruleFormat.valid)
        assertEquals('1', ruleFormat.birthNeighbors[0])
        assertEquals('3', ruleFormat.birthNeighbors[1])
        assertEquals('7', ruleFormat.birthNeighbors[2])
    }

    /**
     * Must have a list of number of living neighbors to cause death
     */
    @Test
    fun listSustainNeighbors() {
        var ruleFormat = RleRuleFormat("B3/S23")
        assertTrue(ruleFormat.valid)
        assertEquals('2', ruleFormat.sustainNeighbors[0])
        assertEquals('3', ruleFormat.sustainNeighbors[1])

        ruleFormat = RleRuleFormat("B137/S2459")
        assertTrue(ruleFormat.valid)
        assertEquals('2', ruleFormat.sustainNeighbors[0])
        assertEquals('4', ruleFormat.sustainNeighbors[1])
        assertEquals('5', ruleFormat.sustainNeighbors[2])
        assertEquals('9', ruleFormat.sustainNeighbors[3])
    }

}
