package com.killua.features.user.data

import com.killua.TestStaticNames
import com.killua.di.FeaturesModule
import com.killua.extenstions.DatabaseExt
import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.extenstions.UserNotFoundException
import com.killua.extenstions.checkResult
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.model.UserDto
import com.killua.inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
class GetUserTester : KoinTest {
    private val userLdsImpl: UserLds by inject()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher(name = "foresees")

    //currentUserSetup
    private val email = TestStaticNames.User.E_MAIL
    private val password = TestStaticNames.User.PASSWORD
    private var userDto = UserDto(email = email, password = password)
    private lateinit var currentUser: UserEntity

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        println("im at before test")
        DatabaseExt.connectTesting()
        startKoin {
            modules(FeaturesModule.userModule, FeaturesModule.companyModule)
        }

    }

    @AfterEach
    fun tearDown() {
        println("im at after test")

        stopKoin()

        DatabaseExt.disconnect()
        Dispatchers.resetMain()
    }

    @Test
    fun getUser() = runTest {

        dbTransaction(testDispatcher) {
            currentUser = listOf(
                { runBlocking { userLdsImpl.getUserLoginCredential(email, password) } },
                { userLdsImpl.addUserTest(userDto) }
            ).checkResult(UserNotFoundException())
        }
        println(currentUser.email)
    }
}