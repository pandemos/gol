package gol

/**
 * Created by aknauss on 12/21/16.
 */
fun main(args: Array<String>) {
    val board = Board(100, 100)
    board.rules.whenApplied { Board ->
        println(board.iteration)
    }
    for (i in 0..100000) {
        board.step()
    }
}

