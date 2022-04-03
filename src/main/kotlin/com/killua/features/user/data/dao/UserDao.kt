package com.killua.features.user.data.dao

import com.killua.extenstions.toUuid
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.data.dao.CompanyTable
import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.image.data.dao.ImagesTable
import com.killua.features.user.domain.model.UserType
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import com.killua.features.vehiclemanager.commondao.UserCarsTable
import com.killua.features.vehiclemanager.commondao.UsersAccidentsTable
import io.ktor.auth.*
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime
import java.util.*

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id),Principal {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    var picture by ImageEntity optionalReferencedOn ImagesTable.user
    var userType by UserTable.userType
    var email by UserTable.email
    var password by UserTable.password
    val myCurrentCar by CarEntity optionalReferencedOn CarTable.currentDriver
    val drivenCar by CarEntity referrersOn UserCarsTable.car
    val accidents by AccidentEntity referrersOn UsersAccidentsTable.accident
    var userInfo by UserInfoEntity optionalReferencedOn UserTable.userInfo
    var company by CompanyEntity optionalReferencedOn UserTable.company
    fun fullName() = "${userInfo?.firstname} ${userInfo?.lastname}".ifBlank { id.value.toString() }
    var createdBy by UserEntity referencedOn UserTable.createdBy
    var createdDate by UserTable.createdDate
    var updatedBy by UserEntity optionalReferencedOn UserTable.updatedBy
    var updatedDate by UserTable.updatedDate
    var deletedBy by UserEntity optionalReferencedOn UserTable.deletedBy
    var deletedDate by UserTable.deletedDate
    fun softDelete(userID: String) {
        deletedBy = UserEntity.findById(userID.toUuid())
        deletedDate = DateTime.now()
        this.flush()
    }


    fun cleanSomeOfIt(): Int {
        return deletedDate?.let {
            if (it.minusDays(15).dayOfMonth > 0)
                delete()
            return@let 1
        } ?: 0
    }

    fun softDelete(user: UserEntity) {
        deletedBy = user
        deletedDate = DateTime.now()
        this.flush()
    }

    fun create(userID: String) {
        createdBy = UserEntity.findById(userID.toUuid())!!
        createdDate = DateTime.now()
        this.flush()
    }

    fun create(user: UserEntity) {
        createdBy = user
        createdDate = DateTime.now()
        this.flush()
    }

    fun update(userID: String) {
        updatedBy = UserEntity.findById(userID.toUuid())!!
        updatedDate = DateTime.now()
        this.flush()
    }

    fun update(user: UserEntity) {
        updatedBy = user
        updatedDate = DateTime.now()
        this.flush()
    }

}


object UserTable : UUIDTable("users") {
    val userInfo = reference("user_info", UserInfosTable).nullable()
    val userType = enumerationByName("user_type", 8, UserType::class)
    val password = text("password").default("")
    val email = text("email").default("")
    val company = reference("company", CompanyTable).nullable()
    val createdBy = reference("created_by", UserTable)
    val createdDate = datetime("created_date").defaultExpression(CurrentDateTime())
    val updatedBy = reference("updated_by", UserTable).nullable()
    val updatedDate = datetime("updated_date").nullable()
    val deletedBy = reference("deleted_by", UserTable).nullable()
    val deletedDate = datetime("deleted_date").nullable()
}
