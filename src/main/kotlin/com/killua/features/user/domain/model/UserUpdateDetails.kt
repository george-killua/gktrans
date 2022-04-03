package com.killua.features.user.domain.model

@kotlinx.serialization.Serializable
data class UserUpdateDetails(
    val id: String,
    val firstname: String? = null,
    val lastname: String? = null,
    val phoneNr: String? = null,
)
