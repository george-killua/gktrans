package com.killua.features.user.data

import com.killua.TestStaticNames.User.E_MAIL
import com.killua.TestStaticNames.User.TEST_PASSWORD
import com.killua.di.FeaturesModule
import com.killua.extenstions.*
import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserInfoEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.user.domain.mapper.toUserDto
import com.killua.features.user.domain.mapper.toUserInfo
import com.killua.features.user.domain.model.UserDto
import com.killua.features.user.domain.model.UserInfoDto
import com.killua.features.user.domain.model.UserType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    private val password = TEST_PASSWORD
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
                    currentUser = listOf(
                        { runBlocking { userLdsImpl.getUserLoginCredential(email, password) } },
                        { userLdsImpl.addUserTest(userDto) }
                    ).checkResult(UserNotFoundException())
                }
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

                userLdsImpl.updateUserEmail(currentUser.id.value, E_MAIL, currentUser).checkResult()
                userLdsImpl.updateUserPassword(currentUser.id.value, TEST_PASSWORD, currentUser).checkResult()
                userLdsImpl.updateUserType(currentUser.id.value, UserType.GEORGE, currentUser).checkResult()

            }
            stopKoin()

            DatabaseExt.disconnect()
            Dispatchers.resetMain()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
    @Test
    fun getAllUsers() = runTest {
        // Given

        measureTime {
            (0..4).forEach { i ->
                usersList.add(UserDto(email = "$i$emailTesting", password = password))
            }
            var dbUser = emptyList<UserDto>()
            dbTransaction(testDispatcher) {
                userDto = currentUser.toUserDto()
                usersList.forEach {
                    userLdsImpl.addUser(it, currentUser)
                }

                // When

                dbUser = userLdsImpl.getAllUsers().checkResult(UserNotFoundException())
                    .filter { it.email.contains("testing.com") }
                    .map { it.toUserDto() }
                println("im at the test")
            }
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
            var createdUser: UserDto? = null
            //When
            dbTransaction(testDispatcher) {
                createdUser = userLdsImpl.addUser(newUser, currentUser).checkResult(UserNotFoundException()).toUserDto()

                println("User id= ${createdUser!!.id}")
                newUser =
                    userLdsImpl.getUser(createdUser!!.id?.toUuid()!!).checkResult(UserNotFoundException()).toUserDto()
            }

            //Then

            newUser `should equal` createdUser!!
        }.also { println(it) }
    }

    @Test
    fun addUserTest() {

    }

    @Test
    fun addUserInfo() = runTest {
        //Given
        val userInfoDto = UserInfoDto(
            firstname = "george",
            lastname = "kassih",
            phoneNumber = "09786343",
            nationality = "syrien",
            sex = "male",
            street = "hjkj",
            streetNr = "fhjb",
            city = "vjhbjkn",
            areaCode = "hjbvjh",
            additionalInfo = "",
        )
        var userInfo: UserInfoDto? = null
        //When
        dbTransaction(testDispatcher) {
            userLdsImpl.addUserInfo(currentUser.id.value, userInfoDto, currentUser).checkResult(UserInfoExption())

            userInfo =
                userLdsImpl.getUser(currentUser.id.value).checkResult(UserNotFoundException()).userInfo?.toUserInfo()
        }
        //then
        val a = "${userInfoDto.firstname} ${userInfoDto.lastname} ${userInfoDto.phoneNumber}"
        val b = "${userInfo?.firstname} ${userInfo?.lastname} ${userInfo?.phoneNumber}"
        println(a)
        println(b)
        a `should equal` b

    }

    @Test
    fun updateUserEmail() = runTest {
        //Given
        val newEmail = E_MAIL + "ge"

        //when
        val newUserEmail = dbTransaction(testDispatcher) {
            userLdsImpl.updateUserEmail(currentUser.id.value, newEmail, currentUser).checkResult()
        }?.email

        val emailNewer = dbTransaction(testDispatcher) {
            userLdsImpl.getUser(currentUser.id.value).checkResult()
        }?.email
        //then
        emailNewer!! `should equal` newUserEmail!!

    }

    @Test
    fun updateUserPassword() = runTest {
        //Given
        val newPassword = TEST_PASSWORD + "ge"

        //when
        val newUserPassword = dbTransaction(testDispatcher) {
            userLdsImpl.updateUserPassword(currentUser.id.value, newPassword, currentUser).checkResult()
            userLdsImpl.getUser(currentUser.id.value).checkResult()
        }?.password

        //then
        newPassword.encoded()!! `should equal` newUserPassword!!

    }

    @Test
    fun updateUserType() = runTest {
        //Given
        val newType = UserType.values().random()

        //when
        val newUserType = dbTransaction(testDispatcher) {
            userLdsImpl.updateUserType(currentUser.id.value, newType, currentUser).checkResult()
            userLdsImpl.getUser(currentUser.id.value).checkResult()
        }?.userType
        //then
        newType `should equal` newUserType!!

    }

    @Test
    fun ownTester() = runTest {
        dbTransaction(testDispatcher) {
            val user = UserInfoDto(
                firstname = "gen",
                lastname = "kbbbh",
                phoneNumber = "09786343",
                nationality = "syrien"
            )
            UserInfoEntity.findById("52245a9d-4528-4825-ad7e-31317a8a31c3".toUuid())?.run {
                firstname = user.firstname
                println(firstname)
                lastname = lastname checkUpdatable user.lastname
                phoneNumber = phoneNumber checkUpdatable user.phoneNumber
                nationality = nationality checkUpdatable user.nationality
                sex = sex checkUpdatable user.sex
                street = street checkUpdatable user.street
                streetNr = streetNr checkUpdatable user.streetNr
                city = city checkUpdatable user.city
                areaCode = areaCode checkUpdatable user.areaCode
                additionalInfo = additionalInfo checkUpdatable user.additionalInfo
                update(currentUser)
            }
        }
    }

    @Test
    fun updateUserInfo() = runTest {
        //Given
        val userInfoDto = UserInfoDto(
            firstname = "geo",
            lastname = "khhhhhh",
            phoneNumber = "09786343",
        )
        val ge = dbTransaction(testDispatcher) {
            userLdsImpl.updateUserInfo(currentUser.id.value, userInfoDto, currentUser)
                .checkResult(UserInfoExption())
        }
        println(ge?.firstname)
        val userInfo: UserInfoDto? = dbTransaction(testDispatcher) {

            userLdsImpl.getUser(currentUser.id.value).checkResult(UserNotFoundException()).userInfo?.toUserInfo()
        }//then
        val a = "${userInfo?.firstname} ${userInfo?.lastname} ${userInfo?.phoneNumber}"
        val b = "${userInfoDto.firstname} ${userInfoDto.lastname} ${userInfoDto.phoneNumber}"
        println(a)
        println(b)
        a `should equal` b
    }


    @Test
    fun deleteUser() {
    }

    @Test
    fun deleteUserInfo() = runTest {
        //Given
        val userId = currentUser.id.value
        //When

        dbTransaction(testDispatcher) {
            userLdsImpl.deleteUserInfo(userId, currentUser)
            val userInfoEntity = userLdsImpl.getUserInfo(userId).checkResult()
            //then
            userInfoEntity.deletedBy?.id?.value?.`should equal`(userId)
        }
    }

    @Test
    fun cleanUsersTable() {
    }

    @Test
    fun cleanUserInfoTable() = runTest {
        val expected = 1L
        val result = dbTransaction(testDispatcher) {
            userLdsImpl.cleanUserInfoTable()
            UserInfoEntity.all().count()
        }.checkNullability().checkResult()
        result.`should equal`(expected)


    }
}