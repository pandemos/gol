package gol.rule

import gol.board.Board
import gol.cell.CellMortality
import gol.cell.LiveCell
import gol.rule.Rules
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** Tests of Rules
 * Created by aknauss on 12/21/16.
 */

class RulesTests {

    /**
     * Rules can be applied
     */
    @Test
    fun canBeApplied() {
        val rules = ConwayRules()
        var wasApplied = false
        rules.whenApplied { board: Board -> wasApplied = true }
        rules.applyTo(Board(0, 0))
        assertTrue(wasApplied)
    }

    /**
     * A callback can be run when rules are applied
     */
    @Test
    fun callbackCanBeRunWhenRulesAreApplied() {
        val rules = ConwayRules()
        var wasApplied = false
        rules.whenApplied { board: Board -> wasApplied = true }
        rules.applyTo(Board(0, 0))
        assertTrue(wasApplied)
    }

    /**
     * Rules are configured via rulestring
     */
    @Test
    fun configuredViaRulestring() {
        val ruleString = "B3/S23"
        val rules = GenericRules(rule = ruleString)
        assertEquals(ruleString, rules.ruleString)
    }

}

class ConwayRuleTests {

    /**
     * A living cell dies if it has 0 living neighbors
     */
    @Test
    fun livingCellDiesWith0Neighbors() {
        val board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())
    }

    /**
     * A living cell dies if it has 1 living neighbors
     */
    @Test
    fun livingCellDiesWith1neighbors() {
        var board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(2, 1, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())

        board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(1, 1, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())

        board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(1, 2, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())

        board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(1, 3, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())

        board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(2, 3, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())

        board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(3, 1, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())

        board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(3, 2, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())

        board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(3, 3, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())
    }

    /**
     * A living cell dies if it has 4 living neighbors
     */
    @Test
    fun livingCellDiesWith4Neighbors() {
        var board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(2, 1, LiveCell())
        board.setCellAt(2, 3, LiveCell())
        board.setCellAt(1, 2, LiveCell())
        board.setCellAt(3, 2, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())

        board = Board(5, 5)
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(1, 1, LiveCell())
        board.setCellAt(1, 3, LiveCell())
        board.setCellAt(3, 1, LiveCell())
        board.setCellAt(3, 3, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isDead())
    }

    /**
     * A cell is bord if it has exactly 3 living neighbors
     */
    @Test
    fun cellisBornWithExactly3Neighbors() {
        var board = Board(5, 5)
        board.setCellAt(2, 1, LiveCell())
        board.setCellAt(2, 3, LiveCell())
        board.setCellAt(1, 2, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isAlive())

        board = Board(5, 5)
        board.setCellAt(1, 1, LiveCell())
        board.setCellAt(1, 3, LiveCell())
        board.setCellAt(3, 3, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isAlive())
    }

    /**
     * Spinner
     */
    @Test
    fun spinner() {
        var board = Board(5, 5)
        board.setCellAt(1, 2, LiveCell())
        board.setCellAt(2, 2, LiveCell())
        board.setCellAt(3, 2, LiveCell())
        board.step()
        assertTrue(board.cellAt(2, 2).isAlive())
        assertTrue(board.cellAt(2, 1).isAlive())
        assertTrue(board.cellAt(2, 3).isAlive())
        assertTrue(board.cellAt(1, 2).isDead())
        assertTrue(board.cellAt(3, 2).isDead())
        board.step()
        assertTrue(board.cellAt(2, 2).isAlive())
        assertTrue(board.cellAt(2, 1).isDead())
        assertTrue(board.cellAt(2, 3).isDead())
        assertTrue(board.cellAt(1, 2).isAlive())
        assertTrue(board.cellAt(3, 2).isAlive())

    }

}
