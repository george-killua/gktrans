package com.killua.features.image.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val id: String? = null,
    val imageUrl: String?,
    val createdAt: Long = 0L,
    val createdBy: String = "",
    val updatedAt: Long? = null,
    val updatedBy: String? = null,
    val deletedAt: Long? = null,
    val deletedBy: String? = null,
) {

}