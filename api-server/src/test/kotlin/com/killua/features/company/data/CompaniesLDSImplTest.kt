package com.killua.features.company.data


import com.killua.TestStaticNames.CompanySettings.COMPANY_ID
import com.killua.TestStaticNames.User.E_MAIL
import com.killua.TestStaticNames.User.PASSWORD
import com.killua.di.FeaturesModule
import com.killua.extenstions.*
import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.domain.model.CompanyDto
import com.killua.features.user.data.UserLds
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.domain.model.UserDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject


@ExperimentalCoroutinesApi
class CompaniesLDSImplTest() : KoinTest {


    private lateinit var userAsynchronously: Deferred<UserEntity>

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: CoroutineDispatcher = StandardTestDispatcher()
    private val userLDS: UserLds by inject()
    private val companiesLDS: CompaniesLDS by inject()

    @BeforeEach
    fun setUp() {
        startKoin {
            modules(FeaturesModule.companyModule, FeaturesModule.userModule)
        }
        DatabaseExt.connectTesting()
        runTest {
             userAsynchronously = dbTransaction(Dispatchers.Unconfined) {
                val email = E_MAIL
                val password = PASSWORD
                return@dbTransaction when (val x = userLDS.getUserLoginCredential(email, password)) {
                    is DbResult.FoundThings -> x.result
                    is DbResult.NothingsFound -> userLDS.addUserTest(UserDto(email = email, password = password))
                        .checkResult(UserNotFoundException())
                }
            }

        }
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
        DatabaseExt.disconnect()
    }

    @Test
    fun getCompany() {
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addCompany() = runTest {
        //Given
        val companyName = "lewdness"


        println("im Here between ${userAsynchronously.isActive}")

        val company: CompanyEntity = userAsynchronously.await().run {
            return@run dbTransaction(Dispatchers.IO) {
                return@dbTransaction companiesLDS.addCompanye(
                    CompanyDto(name = companyName), this
                )
            }.await().apply {
                println(this.toString())
                println("im Here")
            }
        }
        // Then
        companyName `should equal` company.name
    }


    @Test
    fun getUsersWithCompanyId() = runTest {
        val users = dbTransaction(testDispatcher) {
            companiesLDS.getUsers(COMPANY_ID.toUuid())
        }
    }

    @Test
    fun getCars() {
    }

    @Test
    fun getCompanyCarAccidents() {
    }

    @Test
    fun updateCompany() {
    }

    @Test
    fun removeCompany() {
    }

    @Test
    fun undoRemoveCompany() {
    }

    @Test
    fun cleanCompanyTable() {
    }
}


