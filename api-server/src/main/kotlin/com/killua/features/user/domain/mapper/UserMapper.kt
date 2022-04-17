package com.killua.features.user.domain.mapper

import com.killua.features.company.domain.mapper.toCompanyMentionDto
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserInfoEntity
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserMentionDto
import io.ktor.util.*
import org.jetbrains.exposed.sql.exists

fun UserEntity.toUserDto(): UserDto {
    val urled =if(picture!=null)picture.firstOrNull()?.imageUri else ""
    return UserDto(
        id = id.value.toString(),
        password = password,
        email = email,
        userType = userType,
        pictureUrl = urled  ,
        company = company?.toCompanyMentionDto(),
        createdBy = createdBy.id.value.toString(),
        createdDate = createdDate.millis,
        updatedBy = updatedBy?.id?.value.toString(),
        updatedDate = updatedDate?.millis,
        deletedBy = deletedBy?.id?.value.toString(),
        deletedDate = deletedDate?.millis
    )
}

fun UserEntity.toUserMentionDto(): UserMentionDto {
    return UserMentionDto(
        id = id.value.toString(),
        email = email,
        pictureUrl = picture.firstOrNull()?.imageUri,
    )
}

fun UserInfoEntity.toUserInfo() = UserInfoDto(
    id = id.value.toString(),
    firstname = firstname ?: "",
    lastname = lastname ?: "",
    phoneNumber = phoneNumber ?: "",
    nationality = nationality ?: "",
    sex = sex ?: "",
    street = street ?: "",
    streetNr = streetNr ?: "",
    city = city ?: "",
    areaCode = areaCode ?: "",
    additionalInfo = additionalInfo ?: "",
    createdBy = createdBy.id.value.toString(),
    createdDate = createdDate.millis,
    updatedBy = updatedBy?.id?.value.toString(),
    updatedDate = updatedDate?.millis,
    deletedBy = deletedBy?.id?.value.toString(),
    deletedDate = deletedDate?.millis
)
