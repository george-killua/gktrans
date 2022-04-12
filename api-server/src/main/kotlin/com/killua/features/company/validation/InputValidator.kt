package com.killua.features.company.validation

@kotlinx.serialization.Serializable
sealed class ValidationResult {
    @kotlinx.serialization.Serializable
    object Valid : ValidationResult()

    @kotlinx.serialization.Serializable
    class Invalid(val errorMessage: String) : ValidationResult()
}

interface InputValidator {
    fun validate(): ValidationResult
    val newInstance: InputValidator
    fun isPasswordValid(string: String): InputValidator
    fun isStringLengthValid(string: String, name: String, length: Int): InputValidator
    fun isUUIDValid(string: String?): InputValidator
}
