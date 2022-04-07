package com.killua.features.company.domain.mapper

import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.domain.model.CompanyDto
import com.killua.features.company.domain.model.CompanyMentionDto
import com.killua.features.user.domain.mapper.toUserDto

fun CompanyEntity.toCompanyDto(): CompanyDto {
    return CompanyDto(
        id = id.value.toString(),
        name = name,
        createdBy = createdBy.toUserDto(),
        createdDate = createdDate.millis,
        updatedBy = updatedBy?.toUserDto(),
        updatedDate = updatedDate?.millis,
        deletedBy = deletedBy?.toUserDto(),
        deletedDate = updatedDate?.millis
    )
}

fun CompanyEntity.toCompanyMentionDto(): CompanyMentionDto {
    return CompanyMentionDto(id = id.value.toString(), name = name)
}




