package gol

/** Representation of encoding the rules in a particular way
 * Created by aknauss on 12/21/16.
 */
interface RuleFormat {

    var birthNeighbors: CharArray
    var deathNeighbors: CharArray

    fun serialize(): String
}