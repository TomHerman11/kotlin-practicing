package nicestring

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