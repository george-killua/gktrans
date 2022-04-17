package com.killua.features.user.data

import com.killua.TestStaticNames.User.E_MAIL
import com.killua.TestStaticNames.User.PASSWORD
import com.killua.di.FeaturesModule
import com.killua.extenstions.DatabaseExt
import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.extenstions.UserNotFoundException
import com.killua.extenstions.checkResult
import com.killua.extenstions.toUuid
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.user.domain.model.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalCoroutinesApi
internal class UserLdsImplTest : KoinTest {
    private val userLdsImpl: UserLds by inject()
    private var usersList = arrayListOf<UserDto>()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher(name = "foregoes")

    //currentUserSetup
    private val email = E_MAIL
    private val emailTesting = "gepgrpege@testing.com"
    private val password = PASSWORD
    private var userDto = UserDto(email = email, password = password)
    private lateinit var currentUser: UserEntity

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        println("im at before test")




        startKoin {
            modules(FeaturesModule.userModule, FeaturesModule.companyModule)
        }
        DatabaseExt.connectTesting()

        runTest(testDispatcher) {
            this.launch {
                dbTransaction(testDispatcher) {
                    val x = userLdsImpl.addUserTest(userDto)
                    val y = userLdsImpl.getUserLoginCredential(email, password)
                    currentUser = arrayListOf(x, y).checkResult(UserNotFoundException())
                }.await()
            }.join()
        }
    }


    @AfterEach
    fun tearDown() {
        usersList = arrayListOf()
        println("im at after test")

        runTest(testDispatcher) {
            dbTransaction(testDispatcher) {
                UserEntity.find { UserTable.email like "%@testing.com" }.forEach {
                    it.delete()
                }
            }.await()
        }
        stopKoin()

        DatabaseExt.disconnect()
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
    @Test
    fun getAllUsers() = runTest {
        // Given

        measureTime {
            (0..4).forEach { i ->
                usersList.add(UserDto(email = "$i$emailTesting", password = password))
            }
            dbTransaction(testDispatcher) {
                userDto = currentUser.toUserDto()
                usersList.forEach {
                    userLdsImpl.addUser(it, currentUser)
                }
            }.await()
            var dbUser = emptyList<UserDto>()
            // When
            dbTransaction(testDispatcher) {
                dbUser = userLdsImpl.getAllUsers().checkResult(UserNotFoundException())
                    .filter { it.email.contains("testing.com") }
                    .map { it.toUserDto() }
                println("im at the test")
            }.await()
            println("usersList ${usersList.count()}")
            println("usersListDb ${dbUser.count()}")
            //Then
            dbUser.count() `should equal` usersList.count()
        }.apply {
            println("Took until finish this operation $this")
        }

    }

    @Test
    fun testGetAllUsers() {
    }

    @Test
    fun getUser() {
    }

    @Test
    fun getUserInfo() {
    }

    @Test
    fun getUserLoginCredential() {
    }


    @OptIn(ExperimentalTime::class)
    @Test
    fun addUser() = runTest {
        measureTime {
            //Given
            var newUser = UserDto(email = emailTesting, password = password)
            val createdUser: UserDto
            //When
            dbTransaction(testDispatcher) {
                userLdsImpl.addUser(newUser, currentUser).checkResult(UserNotFoundException()).toUserDto()
            }.also {
                createdUser = it.await()
            }.await()
            println("User id= ${createdUser.id}")
            newUser = dbTransaction(testDispatcher) {
                userLdsImpl.getUser(createdUser.id?.toUuid()!!).checkResult(UserNotFoundException()).toUserDto()
            }.await()

            //Then
            newUser `should equal` createdUser
            dbTransaction(testDispatcher) {
                UserEntity.findById(id = createdUser.id?.toUuid()!!)?.delete()
            }.join()
        }.also { println(it) }
    }

    @Test
    fun addUserTest() {

    }

    @Test
    fun addUserInfo() {
    }

    @Test
    fun updateUserEmail() {
    }

    @Test
    fun updateUserPassword() {
    }

    @Test
    fun updateUserType() {
    }

    @Test
    fun updateUserInfo() {
    }

    @Test
    fun deleteUser() {
    }

    @Test
    fun deleteUserInfo() {
    }

    @Test
    fun cleanUsersTable() {
    }

    @Test
    fun cleanUserInfoTable() {
    }
}