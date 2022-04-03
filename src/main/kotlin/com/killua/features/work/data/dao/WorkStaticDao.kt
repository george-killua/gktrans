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

object WorkStaticTable : CommonTable("work_statics") {
    val user = reference("user", UserTable)
    val company = reference("company", CompanyTable)
    val dateOfStatic = date("date_of_static")
    val packagesCount = integer("packages_count")
    val hoursCount = float("hours_count")
}
class WorkStaticEntity(id: EntityID<UUID>) : CommonEntity(id,WorkStaticTable) {
    companion object : UUIDEntityClass<WorkStaticEntity>(WorkStaticTable)
    val user by UserEntity referencedOn WorkStaticTable.user
    val company by CompanyEntity referencedOn WorkStaticTable.company
    val packagesCount by WorkStaticTable.packagesCount
    val hoursCount by WorkStaticTable.hoursCount
    var dateOfStatic by WorkStaticTable.dateOfStatic
}
