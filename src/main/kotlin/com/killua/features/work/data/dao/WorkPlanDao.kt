package com.killua.features.work.data.dao

import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.data.dao.CompanyTable
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.jodatime.date
import java.util.*

object WorkPlanTable : CommonTable("work_plan") {
    val user = reference("user", UserTable)
    val company = reference("company", CompanyTable)
    val dateOfWork = date("date_of_work")
    val beganAt = date("began_at")
    val endedAt = date("ended_at")
    val area = varchar("area", 16).nullable()
    val packagesCount = integer("packages_count")
}

class WorkPlanEntity(id: EntityID<UUID>) : CommonEntity(id,WorkPlanTable) {
    companion object : UUIDEntityClass<WorkPlanEntity>(WorkPlanTable)

    val user by UserEntity referencedOn WorkPlanTable.user
    val company by CompanyEntity referencedOn WorkPlanTable.company
    val dateOfWork by WorkPlanTable.dateOfWork
    val beganAt by WorkPlanTable.beganAt
    val endedAt by WorkPlanTable.endedAt
    val area by WorkPlanTable.area
    val packagesCount by WorkPlanTable.packagesCount
}