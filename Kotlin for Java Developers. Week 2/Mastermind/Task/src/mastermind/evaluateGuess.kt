package mastermind

import mastermind.Evaluation

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    println("Secret is $secret")
    println("Guess is $guess")

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

    //println("Secret Map is $secretMap")
    //println("Count Map is $countMap")

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

    //println("RP $rightPosition")
    //println("WP $wrongPosition")
    return Evaluation(rightPosition, wrongPosition)

}
