package com.killua.features.company.validation

import com.killua.di.applicationModule
import org.amshove.kluent.`should equal`
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

internal class InputValidatorImplTest : KoinTest {

    private val inputValidator: InputValidator by inject()

    @BeforeEach
    fun setUp() {
        startKoin {
            modules(applicationModule)
        }
        inputValidator.newInstance
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getNewInstance() {
        inputValidator.newInstance.validate() shouldBe  ValidationResult.Valid
    }
    @Test
    fun getNewInstanceAfterError() {
        // Given
        val testPassword = "GEORGE"
        val errorMessage = "password should contain at least 1 lower case letter"
        // When adding a new task
        val resultHolder: ValidationResult = inputValidator.isPasswordValid(testPassword).validate()
        // Then the new task event is triggered
        errorMessage `should equal` (resultHolder as ValidationResult.Invalid).errorMessage
        inputValidator.newInstance.validate() shouldBe  ValidationResult.Valid
    }

    @Test
    fun passwordMustLowerLetter() {
        // Given
        val testPassword = "GEORGE"
        val validationResult = ValidationResult.Invalid("password should contain at least 1 lower case letter")


        // When adding a new task
        val resultHolder: ValidationResult = inputValidator.isPasswordValid(testPassword).validate()

        // Then the new task event is triggered
        validationResult.errorMessage `should equal` (resultHolder as ValidationResult.Invalid).errorMessage
    }

    @Test
    fun passwordMustUpperLetter() {
        // Given
        val testPassword = "ge23"
        val validationResult = ValidationResult.Invalid("password should contain at least 1 upper case letter")
        val resultHolder = inputValidator.isPasswordValid(testPassword).validate()

        // Then the new task event is triggered
        validationResult.errorMessage `should equal` (resultHolder as ValidationResult.Invalid).errorMessage
    }

    @Test
    fun passwordMustDigit() {
        // Given
        val testPassword = "geGEro"
        val validationResult = ValidationResult.Invalid("password should contain at least 1 digit")
        val resultHolder = inputValidator.isPasswordValid(testPassword).validate()

        // Then the new task event is triggered
        validationResult.errorMessage `should equal` (resultHolder as ValidationResult.Invalid).errorMessage
    }

    @Test
    fun passwordMustNoWhiteSpaces() {
        // Given
        val testPassword = "geGE3 4ro"
        val validationResult = ValidationResult.Invalid("password shouldn't contain white spaces")
        val resultHolder = inputValidator.isPasswordValid(testPassword).validate()

        // Then the new task event is triggered
        validationResult.errorMessage `should equal` (resultHolder as ValidationResult.Invalid).errorMessage
    }

    @Test
    fun passwordMustMore6Length() {
        // Given
        val testPassword = "gE2o"
        val validationResult = ValidationResult.Invalid("password should contain at least 8 characters")
        val resultHolder = inputValidator.isPasswordValid(testPassword).validate()

        // Then the new task event is triggered
        validationResult.errorMessage `should equal` (resultHolder as ValidationResult.Invalid).errorMessage
    }

    @Test
    fun testPasswordCorrect() {
        // Given
        val testPassword = "gEF2vjbso"
        val validationResult = ValidationResult.Valid
        val resultHolder = inputValidator.isPasswordValid(testPassword).validate()

        // Then the new task event is triggered
        validationResult `should equal` (resultHolder as ValidationResult.Valid)
    }

    @Test
    fun isStringLengthNotValid() {
        // Given
        val testPassword = "kfdkd"
        val name = "testPassword"
        val output = "the $name length is too long"
        val resultHolder = inputValidator.isStringLengthValid(testPassword, "testPassword", 4).validate()

        // Then the new task event is triggered
        output `should equal` (resultHolder as ValidationResult.Invalid).errorMessage

    }

    @Test
    fun isStringLengthValid() {
        // Given
        val testPassword = "kfdkd"
        val name = "testPassword"
        val output = ValidationResult.Valid
        val resultHolder = inputValidator.isStringLengthValid(testPassword, name, 8).validate()

        // Then the new task event is triggered
        output `should equal` resultHolder as ValidationResult.Valid

    }

    @Test
    fun validNotUUID() {
        // Given
        val uuid = "kldsflksdlk "
        val messageOutput = "$uuid is NOT UUID"
        val resultHolder: ValidationResult =
            // When
            inputValidator.isUUIDValid(uuid).validate()

        // Then
        messageOutput `should equal` (resultHolder as ValidationResult.Invalid).errorMessage
    }

    @Test
    fun validUUID() {
        // Given
        val uuid = "71817d26-c0b0-4654-9793-182f74115570"

        // When
        val resultHolder: ValidationResult = inputValidator.isUUIDValid(uuid).validate()

        // Then
        ValidationResult.Valid `should equal` resultHolder
    }

    @Test
    fun inputValidatorHaveTwoErrors() {

        // Given
        val testPassword1 = "gege"
        val testPassword2 = "GEGE"
        val uuid = "deaee7c5-d178d-4de6-9f20-49ee8e830c1d"
        val messageOutput = StringBuilder()
            .append("password should contain at least 1 upper case letter\n")
            .append("password should contain at least 1 lower case letter\n")
            .append("deaee7c5-d178d-4de6-9f20-49ee8e830c1d is NOT UUID")

        val resultHolder: ValidationResult =
            //When
            inputValidator.isPasswordValid(testPassword1).isPasswordValid(testPassword2)
                .isUUIDValid(uuid).validate()

        //then
        (resultHolder as ValidationResult.Invalid).errorMessage `should equal` messageOutput.toString()
    }
}