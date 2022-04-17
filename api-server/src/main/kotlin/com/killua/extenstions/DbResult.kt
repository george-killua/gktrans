package com.killua.extenstions

sealed class DbResult<out T : Any> {

    data class FoundThings<out T : Any>(val result: T) : DbResult<T>()
    object NothingsFound : DbResult<Nothing>()
}

@Throws(Exception::class)
fun <T : Any> DbResult<T>.checkResult(exception: Exception): T {
    /* if (this == DbResult.NothingsFound())
         throw exception
     else
         return (this as DbResult.FoundThings).result*/
    return when (val x = this) {
        is DbResult.FoundThings -> x.result
        is DbResult.NothingsFound -> throw exception
    }
}

@Throws(Exception::class)
fun <T : Any> Collection<DbResult<T>>.checkResult(exception: Exception): T {
    lateinit var georgeResult: T
    this.forEachIndexed { index, newResult ->
        georgeResult = when (newResult) {
            is DbResult.FoundThings -> newResult.result
            DbResult.NothingsFound -> if (this.size - index > 0)
                this.toMutableList()
                    .takeLast(this.size - index)
                    .checkResult(exception)
            else throw exception
        }
    }
    return georgeResult
}

inline fun <reified T : Any> T?.checkNullability(): DbResult<T> {
    return when (this) {
        null -> DbResult.NothingsFound
        else -> DbResult.FoundThings(this)
    }
}
