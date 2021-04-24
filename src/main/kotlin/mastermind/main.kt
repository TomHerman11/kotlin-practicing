// Background:
// When comparing two isogram* strings, we would like to return the "bulls" and "cows":
// "bulls": matching characters in the same positions.
// "cows": matching characters in different positions.

// Isogram - A string consisted of characters which appear only once in the string.

class BullsAndCows(val originalString: String, val guessString: String) {
    val bulls = ArrayList<Char>()
    val cows = ArrayList<Char>()

    fun addBull(bull: Char) = this.bulls.add(bull)
    fun addCow(cow: Char) = this.cows.add(cow)

    override fun toString(): String {
        return """
            Original: ${this.originalString}
            Guess: ${this.guessString}
            Bulls: ${this.bulls}
            Cows: ${this.cows}
        """.trimIndent()
    }
}

fun String.isIsogram(): Boolean {
    val s = mutableSetOf<Char>()
    for (c in this) {
        if (s.contains(c)) return false
        s.add(c)
    }
    return true
}

fun String.guessString(guess: String): BullsAndCows? {
    if (this.length != guess.length || !this.isIsogram() || !guess.isIsogram()) return null

    val ans = BullsAndCows(this, guess)
    val potentialCows: Set<Char> = this.toSet()

    // 'this' and 'guess' has the same length:
    for (i in 0 until this.length) {
        // find bulls:
        if (this[i] == guess[i]) {
            ans.addBull(this[i])
        } else if (potentialCows.contains(guess[i])) {
            ans.addCow(guess[i])
        }
    }

    return ans
}

fun main() {
    println("helowrd".guessString("tomabcd"))
    println()
    println("helowrd".guessString("wrldeom"))
    println()
    println("helowrd".guessString("helowrd"))
}
