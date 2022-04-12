package com.killua.features.user.domain.model

@kotlinx.serialization.Serializable

data class UserInfoDto(
    var id: String? = "",
    val firstname: String? = null,
    val lastname: String? = null,
    val phoneNumber: String? = null,
    val nationality: String? = null,
    val sex: String? = null,
    val street: String? = null,
    val streetNr: String? = null,
    val city: String? = null,
    val areaCode: String? = null,
    val additionalInfo: String? = null,
    val createdBy: String = "default",
    val createdDate: Long? = null,
    val updatedBy: String? = "",
    val updatedDate: Long? = null,
    val deletedBy: String? = "",
    val deletedDate: Long? = null,

    )
