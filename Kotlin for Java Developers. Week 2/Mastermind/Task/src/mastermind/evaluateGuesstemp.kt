package mastermind.changes

import mastermind.Evaluation

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    println("Secret is $secret")
    println("Guess is $guess")
    val (secretMap: MutableMap<Char, MutableSet<Int>>, countMap: MutableMap<Char, Int>) = buildModel(secret)
    var rightPosition = 0
    var commonColor = 0

    for (i in 0 until guess.length) {
        if (characterExist(guess, i, secretMap) && secretMap[guess[i]]!!.contains(i)) {
            rightPosition++
        }

        if(guess[i] in countMap && countMap[guess[i]]!! > 0) {
            commonColor++
            countMap[guess[i]] = countMap[guess[i]]!! - 1
        }
    }

    val wrongPosition = commonColor - rightPosition
    //println("RP $rightPosition")
    //println("WP $wrongPosition")
    return Evaluation(rightPosition, wrongPosition)

}

private fun buildModel(secret: String): Pair<MutableMap<Char, MutableSet<Int>>, MutableMap<Char, Int>> {
    val secretMap: MutableMap<Char, MutableSet<Int>> = mutableMapOf()
    val countMap: MutableMap<Char, Int> = mutableMapOf()
    processCharacters(secret, secretMap, countMap)
    return Pair(secretMap, countMap)
}

private fun processCharacters(secret: String, secretMap: MutableMap<Char, MutableSet<Int>>, countMap: MutableMap<Char, Int>) {
    for (position in 0 until secret.length) {
        processCharacter(secret, position, secretMap, countMap)
    }
}

private fun processCharacter(secret: String, position: Int, secretMap: MutableMap<Char, MutableSet<Int>>, countMap: MutableMap<Char, Int>) {
    if (characterExist(secret, position, secretMap)) {
        addPositionOfExistingCharacter(secretMap, secret, position)
        incrementCharacterQuantity(countMap, secret, position)
    } else {
        registerPositionOfCharacter(position, secretMap, secret)
        registerFirstCountOfTheLetterYouCount(countMap, secret, position)
    }
}

private fun incrementCharacterQuantity(countMap: MutableMap<Char, Int>, secret: String, position: Int) {
    countMap[secret[position]] = countMap[secret[position]]!! + 1
}

private fun addPositionOfExistingCharacter(secretMap: MutableMap<Char, MutableSet<Int>>, secret: String, position: Int) {
    secretMap[secret[position]]!!.add(position)
}

private fun registerFirstCountOfTheLetterYouCount(countMap: MutableMap<Char, Int>, secret: String, position: Int) {
    countMap[secret[position]] = 1
}

private fun registerPositionOfCharacter(position: Int, secretMap: MutableMap<Char, MutableSet<Int>>, secret: String) {
    secretMap[secret[position]] = mutableSetOf<Int>().apply { add(position) }
}

private fun characterExist(secret: String, position: Int, secretMap: MutableMap<Char, MutableSet<Int>>) = secret[position] in secretMap
