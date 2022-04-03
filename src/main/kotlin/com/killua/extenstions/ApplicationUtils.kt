@file:OptIn(InternalAPI::class)

package com.killua.extenstions

import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.model.UserType

import de.nycode.bcrypt.hash
import de.nycode.bcrypt.verify
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.util.*
import java.util.*


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

data class DefaultResponse(
    var status: HttpStatusCode,
    var payload: Payload?,
    val message: String?,
    val call: ApplicationCall?,
)

interface Payload


@OptIn(InternalAPI::class)
fun String.encoded(): String {
    return hash(this).encodeBase64()
}

@OptIn(InternalAPI::class)
fun String.verify(s: String): Boolean {
    return verify(s, this.decodeBase64Bytes())
}