package com.killua.features.company.data.dao

import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentTable
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*
object CompanyTable : CommonTable("companies") {
    val companyName = varchar("company_name", 16)

}
class CompanyEntity(id: EntityID<UUID>) : CommonEntity(id, CompanyTable) {
    companion object : UUIDEntityClass<CompanyEntity>(CompanyTable)
    var name by CompanyTable.companyName
    val users by UserEntity optionalReferrersOn UserTable.company
    val cars by CarEntity optionalReferrersOn CarTable.company
    val accidents by AccidentEntity referrersOn  AccidentTable.company
}
