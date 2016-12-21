package gol

/** Representation of automaton rules
 * Created by aknauss on 12/21/16.
 */
class Rules(val ruleString: String = "B3/S23") {

    private val ruleFormat: RuleFormat = RleRuleFormat(ruleString)

    private var onAppliedCallbacks = mutableListOf<(Board) -> Unit>()

    /**
     * Apply the rules to a given board
     */
    fun applyTo(board: Board) {
        doOnApplied(board)
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
