@file:OptIn(InternalAPI::class)

package com.killua.extenstions

import com.killua.features.socket.UserSession
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserType
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


fun ApplicationCall.getAuthorizationTokenWithoutBearer(): String? {
    return this.request.headers["Authorization"]?.substring(7)
}

fun String.toUuid(): UUID = UUID.fromString(this)
val ApplicationCall.user get() = authentication.principal<UserEntity>()
val ApplicationCall.type get() = user?.userType
suspend fun <T : Any?> ApplicationCall.withRole(
    vararg userTypes: UserType,
    suspendBlock: suspend () -> DefaultResponse,
) {

    return if (user?.userType in userTypes)
        respond(suspendBlock())
    else respond("you are not Allowed")


}

fun ApplicationCall.getSessionId(): String? {
    return sessions.get<UserSession>()?.Userid
}

data class DefaultResponse(
    var status: HttpStatusCode,
    var payload: Payload?,
    val message: String?,
    val call: ApplicationCall?,
)

interface Payload

private const val ALGO = "AES"
private val keyValue = "0123456789abcdef".toByteArray()

@OptIn(InternalAPI::class)
fun String.encoded(): String? {
    val encodedPwd: String?
    try {
        val key: Key = generateKey()
        val c = Cipher.getInstance(ALGO)
        c.init(Cipher.ENCRYPT_MODE, key)
        val encVal = c.doFinal(this.toByteArray())
        encodedPwd = Base64.getEncoder().encodeToString(encVal)
        return encodedPwd
    } catch (e: Exception) {
        e.printStackTrace()

    }
    return null
}


private fun generateKey(): Key {
    return SecretKeySpec(keyValue, ALGO)
}

@OptIn(InternalAPI::class)
fun String.verify(s: String): Boolean {
    val decodedPWD =
        try {
            val key: Key = generateKey()
            val c = Cipher.getInstance(ALGO)
            c.init(Cipher.DECRYPT_MODE, key)
            val decodedValue = Base64.getDecoder().decode(this)
            val decValue = c.doFinal(decodedValue)
            String(decValue)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    return decodedPWD == s
}

private suspend inline fun PipelineContext<*, ApplicationCall>.requireUser(block: (userId: UserDto) -> Unit) {
    val userId = call.user
    if (userId == null) {
        call.respond(HttpStatusCode.Unauthorized, "User id is null")
        return
    }

    block(userId.toUserDto())
}
