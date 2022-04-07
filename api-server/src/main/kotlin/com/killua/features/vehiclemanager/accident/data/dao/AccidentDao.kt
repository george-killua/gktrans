package com.killua.features.vehiclemanager.accident.data.dao

import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.data.dao.CompanyTable
import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.image.data.dao.ImagesTable
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.commondao.CarsAccidentsTable
import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import com.killua.features.vehiclemanager.commondao.UsersAccidentsTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AccidentEntity(id: EntityID<UUID>) : CommonEntity(id, AccidentTable) {
    companion object : UUIDEntityClass<AccidentEntity>(AccidentTable)

    var drivers by UserEntity via UsersAccidentsTable
    val cars by CarEntity via CarsAccidentsTable
    val accidentImage by ImageEntity optionalReferrersOn ImagesTable.accident
    var accidentDescription by AccidentTable.accidentDescription
    var company by CompanyEntity referencedOn AccidentTable.company
}

object AccidentTable : CommonTable("accidents") {
    val accidentDescription = text("description").nullable()
    val company = reference("company", CompanyTable)
}

