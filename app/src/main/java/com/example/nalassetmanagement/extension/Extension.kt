package com.example.nalassetmanagement.extension

fun <T, O> List<T>.mapEntityToData(transform: (T) -> O): List<O> {
    val result = mutableListOf<O>()
    for (item in this) {
        result.add(transform(item))
    }
    return result
}
fun <T,O> List<T>.mapDataToEntity(transform: (T) -> O): List<O> {
    val result = mutableListOf<O>()
    for (item in this) {
        result.add(transform(item))
    }
    return result
}