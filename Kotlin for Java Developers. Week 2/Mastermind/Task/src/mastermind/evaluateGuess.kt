/**
 * This code is written for the programming assignment of Coursera assignment.
 * It has functions to validate if a particular guess made by a user is correct based on the original string.
 * The game compares two strings and tells how many letters are on the right position and wrong positions.
 * The player has to iteratively guess the exact string based on the intermediate statistics of the position of colors.
 *
 * It is written in Kotlin.
 *
 * @author Sunny Bangale
 *
 */
package mastermind

import mastermind.Evaluation

/**
* This class is to define the evaluation parameters for the secret and the guessed string
*/
data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

/**
* This function evaluates a guess based on the secret string and the guessed string by the player.
* It returns an iterative evaluation of the right and wrong positions of certain color codes in the string.
*/
fun evaluateGuess(secret: String, guess: String): Evaluation {
    
    var secretMap : MutableMap<Char, HashSet<Int>> = mutableMapOf()
    var countMap : MutableMap<Char, Int> = mutableMapOf()

    for (i in 0 until secret.length) {
       if (secret[i] in secretMap) {
            secretMap[secret[i]]!!.add(i)
            countMap[secret[i]] = countMap[secret[i]]!! + 1
        }
        else {
            var tempSet: HashSet<Int> =  hashSetOf()
            tempSet.add(i)
            secretMap[secret[i]] = tempSet
            countMap[secret[i]] = 1
        }
    }

    var rightPosition = 0
    var commonColor = 0

    for (i in 0 until guess.length) {
        if (guess[i] in secretMap && secretMap[guess[i]]!!.contains(i)) {
            rightPosition++
        }

        if(guess[i] in countMap && countMap[guess[i]]!! > 0) {
            commonColor++
            countMap[guess[i]] = countMap[guess[i]]!! - 1
        }
    }
    var wrongPosition = commonColor - rightPosition

    return Evaluation(rightPosition, wrongPosition)

}
