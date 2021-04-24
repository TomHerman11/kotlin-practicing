package nicestring

// based on: https://github.com/esanchezros/nicestring-kotlin-coursera
// A string is nice if at least two of the following conditions are satisfied:
// - It doesn't contain substrings bu, ba or be;
// - It contains at least three vowels (vowels are a, e, i, o and u);
// - It contains a double letter (at least two similar letters following one another), like b in "abba".

fun String.containsBuBaBe(): Boolean {
    return this.contains(Regex("b[aue]"))
}

fun String.containsAtLeastThreeVowels(): Boolean {
    return this.count {
        it == 'a' ||
        it == 'e' ||
        it == 'i' ||
        it == 'o' ||
        it == 'u'
    } >= 3
}

fun String.containsDoubleLetter(): Boolean {
    return this.contains(Regex("(.)\\1"))
}

fun String.isNiceString(): Boolean {
    val results = listOf(
            !this.containsBuBaBe(),
            this.containsAtLeastThreeVowels(),
            this.containsDoubleLetter()
    )

    return results.count { it } >= 2
}

fun main() {
    println("bac".isNiceString())
    println("aza".isNiceString())
    println("abaca".isNiceString())
    println("baaa".isNiceString())
    println("aaab".isNiceString())
}