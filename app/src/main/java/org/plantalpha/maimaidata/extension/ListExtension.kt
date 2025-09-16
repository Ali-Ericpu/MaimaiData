package org.plantalpha.maimaidata.extension

fun <T> MutableList<T>.replace(other : List<T>) = apply {
    clear()
    addAll(other)
}