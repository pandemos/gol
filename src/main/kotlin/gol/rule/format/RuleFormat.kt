package gol.rule.format

/** Representation of encoding the rules in a particular way
 * Created by aknauss on 12/21/16.
 */
interface RuleFormat {

    var birthNeighbors: CharArray
    var sustainNeighbors: CharArray

    fun serialize(): String
}