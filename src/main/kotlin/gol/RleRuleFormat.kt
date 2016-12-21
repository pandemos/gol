package gol

/** Implementation of rules formatted using RLE
 * See http://conwaylife.com/wiki/Rulestring#Rules
 * Created by aknauss on 12/21/16.
 */
class RleRuleFormat(val ruleString: String,
                    var valid: Boolean = false,
                    override var birthNeighbors: CharArray = CharArray(0),
                    override var deathNeighbors: CharArray = CharArray(0)) : RuleFormat {

    private val pattern = "B([0-9]+)/S([0-9]+)".toRegex()

    init {
        val ruleValues = pattern.matchEntire(ruleString)?.groupValues
        if (ruleValues != null) {
            birthNeighbors = ruleValues.get(1).toCharArray()
            deathNeighbors = ruleValues.get(2).toCharArray()
            valid = true
        }
    }

    /**
     * Serialize these rules to a string
     */
    override fun serialize(): String {
        return ruleString
    }
}