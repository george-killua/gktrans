package com.killua.features.work.data.dao

import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.jodatime.date
import java.util.*
object WorkDetailTable : CommonTable("work_details") {
    val company = varchar("company", 16)
    val holiday = reference("holiday", HolidayTable)
    val user = reference("user", UserTable)
    val contractStartedAt = date("contract_started_at")
    val contractEndedAt = date("contract_ended_at")
    val workStatics = reference("work_statics", WorkStaticTable).nullable()
    val salary = float("salary")
}

class WorkDetailEntity(id: EntityID<UUID>) : CommonEntity(id,WorkDetailTable) {
    companion object : UUIDEntityClass<WorkDetailEntity>(WorkDetailTable)

    val company by CompanyEntity referencedOn WorkDetailTable.company
    val user by UserEntity referencedOn WorkDetailTable.user
    val holiday by HolidayEntity referencedOn WorkDetailTable.holiday
    val workStatics by WorkStaticEntity via WorkStaticTable
    val contractStartedAt by WorkDetailTable.contractStartedAt
    val contractEndedAt by WorkDetailTable.contractEndedAt
    val workStaticsEntity by WorkStaticEntity optionalReferencedOn WorkDetailTable.workStatics
    val salary by WorkDetailTable.salary
}