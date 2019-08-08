/**
 * This code is written for the programming assignment of Coursera assignment.
 * It has functions to validate if a string is Nice based on 3 rules.
 * 1. It doesn't contain substrings bu, ba or be.
 * 2. It contains at least three vowels (vowels are a, e, i, o and u).
 * 3. It contains a double letter (at least two similar letters following one another), like b in "abba".
 * If atleast 2 rules are satisfied, then the string is nice and not nice, otherwise.
 *
 * It is written in Kotlin.
 *
 * @author Sunny Bangale
 *
 */

package nicestring

/**
 * This class validates the string against rule 1. Returns true/ false based on the validation.
 * @param Input String
 * @return True or False
 */
fun checkRule1(input: String): Boolean
{
    val neighborList = input.zipWithNext().map { it.first.toString() + it.second }
    val inputList = listOf("ba", "bu", "be")

    return neighborList.intersect(inputList).isEmpty()
}


/**
 * This function validates the string against rule 2. Returns true/ false based on the validation.
 * @param Input String
 * @return True or False
 */
fun checkRule2(input: String): Boolean
{
    var vowelCount = 0
    input.toList().forEach(){t -> if(checkVowel(t)) vowelCount++}

    return vowelCount >= 3
}

/**
 * This function validates the string against rule 3. Returns true/false based on the validation.
 * @param Input String
 * @return True or False
 */
fun checkRule3(input: String): Boolean
{
    var doubleLetterCount = 0
    input.zipWithNext().map { if (it.first == it.second) doubleLetterCount++ }

    return doubleLetterCount >= 1
}

/**
 * This function checks a string for vowels. Returns true/false based on its presence in a list
 * @param Input Character
 * @return True or False
 */
fun checkVowel(vowel: Char): Boolean = vowel in listOf('a','e','i','o','u')

/**
 * This function validates the string against all rules. Returns true/ false based on the validation.
 * @param Input Rules
 * @return True or False
 */
fun validateRules(checkRule1: Boolean, checkRule2: Boolean,checkRule3: Boolean) = (checkRule1 && checkRule2) || (checkRule2 && checkRule3) || (checkRule1 && checkRule3)

/**
 * This function validates if a string is nice based on 3 rules. Returns true/ false based on the validation.
 * @param Input String
 * @return True or False
 */
fun String.isNice(): Boolean {

    //println("Input String $this")
    var checkRule1 = checkRule1(this)
    //println("Rule 1: $checkRule1")
    var checkRule2 = checkRule2(this)
    //println("Rule 2: $checkRule2")
    var checkRule3 = checkRule3(this)
    //println("Rule 3: $checkRule3")

    return validateRules(checkRule1,checkRule2, checkRule3)
}
