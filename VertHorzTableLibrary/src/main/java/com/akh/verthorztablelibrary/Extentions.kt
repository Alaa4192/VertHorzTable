package com.akh.verthorztablelibrary

fun String?.string(): String {
    return this ?: ""
}

fun Int?.string(): String {
    return this.toString()
}

fun <E> MutableList<E>.addIf(e: E, shouldAdd: Boolean = false) {
    if (shouldAdd) {
        this.add(e)
    }
}