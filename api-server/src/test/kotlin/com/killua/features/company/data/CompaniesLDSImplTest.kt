package com.killua.features.company.data


import com.killua.di.applicationModule
import com.killua.di.featuresModule
import com.killua.extenstions.DatabaseExt
import com.killua.extenstions.DatabaseExt.dbTransaction
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.domain.model.CompanyDto
import com.killua.features.user.data.UserLocalDataSource
import com.killua.features.user.data.UserLocalDataSourceImpl
import com.killua.features.user.domain.model.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject


class CompaniesLDSImplTest() : KoinTest {


    private val email = "george@kass.com"
    private val password = "ForgeRock"
    private val currentUser = UserDto(email = email, password = password)
    private val userLDS: UserLocalDataSource = UserLocalDataSourceImpl()

    private val companiesLDS: CompaniesLDS by inject()

    @BeforeEach
    fun setUp() {
        startKoin {
            modules(featuresModule, applicationModule)
        }
        DatabaseExt.connectTesting()
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

        val userAsynchronously = dbTransaction(Dispatchers.Unconfined) {
            (userLDS.getUserLoginCredentiale(email, password)
                ?: userLDS.addUserTest(currentUser))
        }
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
    fun getUsers() {
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
        runBlockingTest(context = Dispatchers.IO) {}
    }
}


