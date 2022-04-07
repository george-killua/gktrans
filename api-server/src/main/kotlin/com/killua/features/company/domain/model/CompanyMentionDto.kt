package com.killua.features.company.domain.model

@kotlinx.serialization.Serializable
data class CompanyMentionDto(
    val id: String,
    val name: String,
)