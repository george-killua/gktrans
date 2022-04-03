package com.killua.features.user.domain.model

import com.killua.features.company.data.dao.CompanyEntity

@kotlinx.serialization.Serializable
data class UserDto(
    val id: String? = null,
    val email: String,
    var password: String,
    val userType: UserType = UserType.DEFAULT,
    val pictureUrl: String? = "",
    val createdBy: String = "default",
    val createdDate: Long? = null,
    val updatedBy: String? = "",
    val updatedDate: Long? = null,
    val deletedBy: String? = "",
    val deletedDate: Long? = null,
    val company:String?=null
) {
}
