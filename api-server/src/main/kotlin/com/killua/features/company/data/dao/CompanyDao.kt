package com.killua.features.company.data.dao

import com.killua.config.Names.COMPANY_NAME_COLUMN
import com.killua.config.Names.COMPANY_NAME_LENGTH
import com.killua.config.Names.COMPANY_TABLE
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentTable
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import com.killua.features.commondao.CommonEntity
import com.killua.features.commondao.CommonTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

object CompanyTable : CommonTable(COMPANY_TABLE) {
    val companyName = varchar(COMPANY_NAME_COLUMN, COMPANY_NAME_LENGTH)

}

class CompanyEntity(id: EntityID<UUID>) : CommonEntity(id, CompanyTable) {
    companion object : UUIDEntityClass<CompanyEntity>(CompanyTable)

    var name by CompanyTable.companyName
    val users by UserEntity optionalReferrersOn UserTable.company
    val cars by CarEntity optionalReferrersOn CarTable.company
    val accidents by AccidentEntity referrersOn AccidentTable.company
}
