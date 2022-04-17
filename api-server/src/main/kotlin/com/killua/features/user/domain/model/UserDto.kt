package com.killua.features.user.domain.model

import com.killua.features.company.domain.model.CompanyMentionDto

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
    var company: CompanyMentionDto? = null,
)

