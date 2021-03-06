package com.killua.features.user.data.dao

import com.killua.config.Names.COMPANY_COLUMN
import com.killua.config.Names.CREATED_BY_COLUMN
import com.killua.config.Names.CREATED_DATE_COLUMN
import com.killua.config.Names.DELETED_BY_COLUMN
import com.killua.config.Names.DELETED_DATE_COLUMN
import com.killua.config.Names.EMAIL_ADDRESS_COLUMN
import com.killua.config.Names.PASSWORD_COLUMN
import com.killua.config.Names.UPDATED_BY_COLUMN
import com.killua.config.Names.UPDATED_DATE_COLUMN
import com.killua.config.Names.USER_INFO_COLUMN
import com.killua.config.Names.USER_TABLE
import com.killua.config.Names.USER_TYPE_COLUMN
import com.killua.extenstions.toUuid
import com.killua.features.company.data.dao.CompanyEntity
import com.killua.features.company.data.dao.CompanyTable
import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.image.data.dao.ImagesTable
import com.killua.features.user.domain.model.UserType
import io.ktor.server.auth.*
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime
import java.util.*
import java.util.concurrent.TimeUnit

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id), Principal {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    val picture by ImageEntity optionalReferrersOn ImagesTable.user
    var userType by UserTable.userType
    var email by UserTable.email
    var password by UserTable.password

    /*    val myCurrentCar by CarEntity optionalReferrersOn  CarTable.currentDriver
        val drivenCar by CarEntity referrersOn UserCarsTable.car
        val accidents by AccidentEntity referrersOn UsersAccidentsTable.accident*/
    var userInfo by UserInfoEntity optionalReferencedOn UserTable.userInfo
    var company by CompanyEntity optionalReferencedOn UserTable.company
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
            val diffInMillisecond: Long = DateTime.now().millis - it.millis
            val res = TimeUnit.MILLISECONDS.toDays(diffInMillisecond)
            println("${id.value} $res")
            if (res > 0)
                delete()
            return@let 1
        } ?: 0
    }

    fun softDelete(user: UserEntity) {
        deletedBy = user
        deletedDate = DateTime.now()
    }

    fun create(userID: String) {
        createdBy = UserEntity.findById(userID.toUuid())!!
        createdDate = DateTime.now()
    }

    fun create(user: UserEntity) {
        createdBy = user
        createdDate = DateTime.now()
    }

    fun update(userID: String) {
        updatedBy = UserEntity.findById(userID.toUuid())!!
        updatedDate = DateTime.now()
    }

    fun update(user: UserEntity) {
        updatedBy = user
        updatedDate = DateTime.now()
    }

}


object UserTable : UUIDTable(USER_TABLE) {
    val email = text(EMAIL_ADDRESS_COLUMN).default("")
    val password = text(PASSWORD_COLUMN).default("")
    val userInfo = reference(
        USER_INFO_COLUMN,
        UserInfosTable,
        onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.RESTRICT
    ).nullable()
    val company = reference(
        COMPANY_COLUMN, CompanyTable, onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.NO_ACTION
    ).nullable()
    val userType = enumerationByName(USER_TYPE_COLUMN, 8, UserType::class)
    val createdBy = reference(
        CREATED_BY_COLUMN, UserTable, onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.NO_ACTION
    )
    val createdDate = datetime(CREATED_DATE_COLUMN).defaultExpression(CurrentDateTime())
    val updatedBy = reference(
        UPDATED_BY_COLUMN, UserTable, onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.NO_ACTION
    ).nullable()
    val updatedDate = datetime(UPDATED_DATE_COLUMN).nullable()
    val deletedBy = reference(
        DELETED_BY_COLUMN, UserTable, onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.NO_ACTION
    ).nullable()
    val deletedDate = datetime(DELETED_DATE_COLUMN).nullable()
}
