package com.killua.extenstions

import io.ktor.server.plugins.*

sealed class DbResult<out T : Any> {

    data class FoundThings<out T : Any>(val result: T) : DbResult<T>()
    object NothingsFound : DbResult<Nothing>()
}

infix fun <T : Any> DbResult<T>.checkResult(
    exception: Exception?
): T {

    return when {
        this is DbResult.FoundThings -> this.result
        exception != null -> throw exception
        else -> {
            throw NotFoundException()
        }
    }
}


@Throws(Exception::class)
fun <T : Any> Collection<() -> DbResult<T>>.checkResult(exception: Exception): T =
    run ge@{
        var result: T? = null
        this.forEachIndexed { _, newResult ->
            try {
                newResult().checkResult(exception).let {
                    result = it
                }
                if (result != null) return@ge result!!
            } catch (_: Exception) {
            }
        }
        throw exception
    }


inline fun <reified T : Any> T?.checkNullability(): DbResult<T> {
    return when (this) {
        null -> DbResult.NothingsFound
        else -> DbResult.FoundThings(this)
    }
}
