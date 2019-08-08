package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {

    val permutationArray = permutation.toMutableList()

    var position = 0
    for (current in 1..permutationArray.size - 1) {

        var previous = current - 1
        val value = permutationArray[current]

        while (previous >= 0 && permutationArray[previous] > value) {
            permutationArray[previous + 1] = permutationArray[previous]
            position++
            previous--
        }

        permutationArray[previous + 1] = value
    }

    return position % 2 == 0

}