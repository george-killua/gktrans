package com.killua.features.company.validation

import java.util.*
import java.util.function.IntPredicate

class InputValidatorImpl : InputValidator {
    private var validateTmp = mutableListOf<ValidationResult>()

    override fun validate(): ValidationResult {
        val stringErrors = StringBuilder()
        validateTmp.mapIndexedNotNull { index, value ->
            when (value) {
                is ValidationResult.Invalid ->
                    if (index != validateTmp.lastIndex)
                        value.errorMessage + "\n"
                    else
                        value.errorMessage
                ValidationResult.Valid -> null
            }
        }.ifEmpty {
            return ValidationResult.Valid
        }.forEach {
            stringErrors.append(it)
        }
        return ValidationResult.Invalid(stringErrors.toString())

    }

    override val newInstance: InputValidator
        get() {
            validateTmp = mutableListOf()
            return this
        }

    override fun isPasswordValid(string: String): InputValidator {

        when {
            !containsLowerCase(string) ->
                validateTmp
                    .add(
                        ValidationResult
                            .Invalid("password should contain at least 1 lower case letter")
                    )
            !containsUpperCase(string) ->
                validateTmp
                    .add(
                        ValidationResult
                            .Invalid("password should contain at least 1 upper case letter")
                    )
            !containsNumber(string) ->
                validateTmp
                    .add(
                        ValidationResult
                            .Invalid("password should contain at least 1 digit")
                    )
            containsWhitespace(string) ->
                validateTmp
                    .add(
                        ValidationResult
                            .Invalid("password shouldn't contain white spaces")
                    )
            string.length < 8 ->
                validateTmp
                    .add(
                        ValidationResult
                            .Invalid("password should contain at least 8 characters")
                    )
            else -> validateTmp
                .add(ValidationResult.Valid)
        }
        //      "(?=.*[@#$%^&+=])" +    //at least 1 special character

        return this
    }

    private fun containsLowerCase(value: String): Boolean {
        return contains(value) { i -> Character.isLetter(i) && Character.isLowerCase(i) }
    }

    private fun containsUpperCase(value: String): Boolean {
        return contains(value) { i -> Character.isLetter(i) && Character.isUpperCase(i) }
    }

    private fun containsNumber(value: String): Boolean {
        return contains(value, Character::isDigit)
    }

    private fun containsWhitespace(value: String): Boolean {
        return contains(value, Character::isWhitespace)
    }

    private fun contains(value: String, predicate: IntPredicate): Boolean {
        return value.chars().anyMatch(predicate)
    }

    override fun isStringLengthValid(string: String, name: String, length: Int): InputValidator {
        val stringParser = StringParser(string)
        val response: String = with(stringParser) {
            this.removeHTMLTags()
            this.removeSpecialCharacters()
            this.removeLeadingAndTrailingSpaces()
        }
        if (response.length > length) validateTmp
            .add(
                ValidationResult
                    .Invalid("the $name length is too long")
            )
        else validateTmp
            .add(ValidationResult.Valid)
        return this
    }


    override fun isUUIDValid(string: String?): InputValidator {
        runCatching { UUID.fromString(string) }
            .onSuccess {
                validateTmp
                    .add(ValidationResult.Valid)
            }
            .onFailure {
                validateTmp.add(ValidationResult.Invalid("$string is NOT UUID"))

            }

        return this
    }
}

class StringParser(var string: String) {
    fun removeHTMLTags(): String {
        string = Regex("<[^>]*>").replace(string, "")

        return string
    }

    fun removeSpecialCharacters(): String {
        string = Regex("[^A-Za-z0-9 ]").replace(string, "")
        return string
    }

    fun removeLeadingAndTrailingSpaces(): String {
        return string.trim()
    }
}