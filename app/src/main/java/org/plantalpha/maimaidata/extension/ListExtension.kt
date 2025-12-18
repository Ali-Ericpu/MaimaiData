package org.plantalpha.maimaidata.extension

fun <T> MutableList<T>.replace(other : T) = apply {
    clear()
    add(other)
}

fun <T> MutableList<T>.replace(other : List<T>) = apply {
    clear()
    addAll(other)
}