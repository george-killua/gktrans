package com.killua.extenstions

import com.killua.config.AppConfig
import com.killua.config.DatabaseConfig
import com.killua.features.image.data.dao.ImagesTable
import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.accident.data.dao.AccidentTable
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import com.killua.features.vehiclemanager.car.info.dao.CarInsuranceInfosTable
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
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseExt {
    fun initializeConnection(appConfig: AppConfig) {
        val databaseConfig = configInitializer(appConfig.databaseConfig)
        Database.connect(databaseConfig)
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.createMissingTablesAndColumns(
                tables =
                arrayOf(
                    HolidayTable,
                    WorkDetailTable,
                    HolidayTable,
                    WorkPlanTable,
                    WorkStaticTable,
                    ImagesTable,
                    UserTable,
                    CarTable,
                    CarsAccidentsTable,
                    CarInsuranceInfosTable,
                    UsedHistoryTable,
                    UserCarsTable,
                    UsersAccidentsTable,
                    AccidentTable
                ), withLogs = true
            )

        }
    }


    private fun configInitializer(databaseConfig: DatabaseConfig): HikariDataSource {

        val config = HikariConfig()

        config.jdbcUrl = databaseConfig.url
        config.username = databaseConfig.dbUsername
        config.password = databaseConfig.dbPassword
        config.driverClassName = databaseConfig.driver
        config.isAutoCommit = false
        config.maximumPoolSize = 10
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"

        config.validate()
        return HikariDataSource(config)
    }


    suspend fun <T> dbTransaction(block: suspend () -> T): T =
        suspendedTransactionAsync(Dispatchers.IO) { block() }.await()
}


