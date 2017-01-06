package gol.rule

import gol.board.Board
import gol.cell.DeadCell
import gol.cell.LiveCell
import gol.rule.format.RleRuleFormat
import gol.rule.format.RuleFormat

/** Representation of automaton rules
 * Created by aknauss on 12/21/16.
 */
abstract class Rules(val ruleString: String) {

    private val ruleFormat: RuleFormat = RleRuleFormat(ruleString)

    private var onAppliedCallbacks = mutableListOf<(Board) -> Unit>()

    /**
     * Apply the rules to a given board
     */
    fun applyTo(board: Board) {
        val newBoard = Board(board.width, board.height, board.iteration, board.rules)

        board.forEach { x, y, cell ->
            // Apply birth rules
            for (rule in ruleFormat.birthNeighbors) {
                val cell = board.cellAt(x, y)
                if (cell.isDead() && board.livingNeighbors(x, y) == rule.toString().toInt()) {
                    newBoard.setCellAt(x, y, LiveCell())
                }
            }

            // Apply sustain rules
            val cell = board.cellAt(x, y)
            val nNeighbors = board.livingNeighbors(x, y)
            val matchesRule = ruleFormat.sustainNeighbors.map { n -> n.toString().toInt() }.contains(nNeighbors)
            if (cell.isAlive() && matchesRule) {
                newBoard.setCellAt(x, y, LiveCell())
            }
        }

        board.copyFrom(newBoard)
        doOnApplied(newBoard)
    }

    /**
     * Add a callback to be run whenever these rules are applied
     */
    fun whenApplied(callback: (Board) -> Unit) {
        onAppliedCallbacks.add(callback)
    }

    /**
     * Run configured onApplied callbacks
     */
    private fun doOnApplied(board: Board) {
        for (callback in onAppliedCallbacks) {
            callback(board)
        }
    }

}

/**
 * Classic rules for Conway's Game of Life
 *
 * - For a cell that is ALIVE:
 *    - Each cell with one or no living neighbors dies, as if by solitude.
 *    - Each cell with four or more living neighbors dies, as if by overpopulation.
 *    - Any cell that touches one or more board edges dies
 *  - For a cell that is DEAD:
 *     - Each cell with exactly three living neighbors becomes populated.
 */
class ConwayRules() : Rules("B3/S23")

/**
 * Specify arbitrary rules based on a rulestring
 */
class GenericRules(val rule: String) : Rules(rule)