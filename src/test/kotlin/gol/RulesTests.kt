package gol

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

/** Tests of Rules
 * Created by aknauss on 12/21/16.
 */

class RulesTests {

    /**
     * Rules can be applied
     */
    fun canBeApplied() {
        val rules = Rules()
        var wasApplied = false
        rules.whenApplied { board: Board -> wasApplied = true }
        rules.applyTo(Board(0, 0))
        assertTrue(wasApplied)
    }

    /**
     * A callback can be run when rules are applied
     */
    fun callbackCanBeRunWhenRulesAreApplied() {
        val rules = Rules()
        var wasApplied = false
        rules.whenApplied { board: Board -> wasApplied = true }
        rules.applyTo(Board(0, 0))
        assertTrue(wasApplied)
    }

    /**
     * Rules are configured via rulestring
     */
    fun configuredViaRulestring() {
        val ruleString = "B3/S23"
        val rules = Rules(ruleString = ruleString)
        assertEquals(ruleString, rules.ruleString)
    }

}
