package com.killua.extenstions

import com.killua.config.AppConfig
import com.killua.config.DatabaseConfig
import com.killua.features.company.data.dao.CompanyTable
import com.killua.features.image.data.dao.ImagesTable
import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.accident.data.dao.AccidentTable
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import com.killua.features.vehiclemanager.car.info.dao.data.CarInsuranceTable
import com.killua.features.vehiclemanager.commondao.CarsAccidentsTable
import com.killua.features.vehiclemanager.commondao.UserCarsTable
import com.killua.features.vehiclemanager.commondao.UsersAccidentsTable
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryTable
import com.killua.features.work.data.dao.HolidayTable
import com.killua.features.work.data.dao.WorkDetailTable
import com.killua.features.work.data.dao.WorkPlanTable
import com.killua.features.work.data.dao.WorkStaticTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseExt {
    lateinit var dataSource: HikariDataSource
    fun initializeConnection(appConfig: AppConfig) {
        dataSource = configInitializer(appConfig.databaseConfig)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(
                tables =
                arrayOf(
                    UserTable,
                    CompanyTable,
                    HolidayTable,
                    WorkDetailTable,
                    WorkPlanTable,
                    WorkStaticTable,
                    ImagesTable,
                    CarTable,
                    CarsAccidentsTable,
                    CarInsuranceTable,
                    UsedHistoryTable,
                    UserCarsTable,
                    UsersAccidentsTable,
                    AccidentTable
                )
            )

        }
    }

    fun connectTesting() {

        dataSource =
            configInitializer(
                url = "jdbc:postgresql://localhost:5432/testers",
                driver = "org.postgresql.Driver"
            )
        Database.connect(dataSource)
        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                tables =
                arrayOf(
                    UserTable,
                    CompanyTable,
                    HolidayTable,
                    WorkDetailTable,
                    WorkPlanTable,
                    WorkStaticTable,
                    ImagesTable,
                    CarTable,
                    CarsAccidentsTable,
                    CarInsuranceTable,
                    UsedHistoryTable,
                    UserCarsTable,
                    UsersAccidentsTable,
                    AccidentTable
                )
            )

        }
    }

    fun disconnect() {
        dataSource.close()
    }

    private fun configInitializer(databaseConfig: DatabaseConfig): HikariDataSource {

        val config = HikariConfig()

        config.jdbcUrl = databaseConfig.url
        config.username = databaseConfig.dbUsername
        config.password = databaseConfig.dbPassword
        config.driverClassName = databaseConfig.driver
        config.isAutoCommit = true
        config.maximumPoolSize = 10
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        config.validate()
        return HikariDataSource(config)
    }

    private fun configInitializer(url: String, driver: String): HikariDataSource {

        val config = HikariConfig()

        config.jdbcUrl = url
        config.driverClassName = driver
        config.isAutoCommit = true
        config.maximumPoolSize = 10
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        config.validate()
        return HikariDataSource(config)
    }


    suspend fun <T> dbTransaction(dispatcher: CoroutineDispatcher,block: suspend () -> T): Deferred<T> {
        return suspendedTransactionAsync(dispatcher) {
            block()
        }

    }


}







