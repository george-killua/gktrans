package com.killua.features.user.domain.model

@kotlinx.serialization.Serializable
data class UserMentionDto(
    val id: String? = null,
    val email: String,
    val pictureUrl: String? = "",
)