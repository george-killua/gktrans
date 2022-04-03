package com.killua.features.user.domain.model

@kotlinx.serialization.Serializable
data class UserInfoDto(
    val id: String,
    val firstname: String,
    val lastname: String,
    val phoneNumber: String,
    val nationality: String,
    val sex: String,
    val street: String,
    val streetNr: String,
    val city: String,
    val areaCode: String,
    val additionalInfo: String,
    val createdBy: String = "default",
    val createdDate: Long? = null,
    val updatedBy: String? = "",
    val updatedDate: Long? = null,
    val deletedBy: String? = "",
    val deletedDate: Long? = null,
){
}
