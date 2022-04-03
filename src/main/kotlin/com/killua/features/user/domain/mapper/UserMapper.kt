package com.killua.features.user.domain.mapper

import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserInfoEntity
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto

fun UserEntity.toUser(): UserDto {
    return UserDto(
        id = id.value.toString(),
        password = password,
        email = email,
        userType = userType,
        pictureUrl = picture?.imageUri,
        company=company?.name?:"",
        createdBy = createdBy.fullName(),
        createdDate = createdDate.millis,
        updatedBy = updatedBy?.fullName(),
        updatedDate = updatedDate?.millis,
        deletedBy = deletedBy?.fullName(),
        deletedDate = deletedDate?.millis
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
    createdBy = createdBy.fullName(),
    createdDate = createdDate.millis,
    updatedBy = updatedBy?.fullName(),
    updatedDate = updatedDate?.millis,
    deletedBy = deletedBy?.fullName(),
    deletedDate = deletedDate?.millis
)
