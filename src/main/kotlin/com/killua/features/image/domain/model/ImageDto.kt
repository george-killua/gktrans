package com.killua.features.image.domain.model

import com.killua.features.user.domain.model.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(val id: String? = null, val tookByUserDto: ?, val image: ByteArray?, val takenDate: Long? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageDto

        if (tookByUserDto != other.tookByUserDto) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tookByUserDto.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}