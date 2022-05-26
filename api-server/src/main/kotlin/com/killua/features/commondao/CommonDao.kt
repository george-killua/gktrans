package com.killua.features.commondao

import com.killua.extenstions.toUuid
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime
import java.util.*
import java.util.concurrent.TimeUnit


abstract class CommonTable(name: String) : UUIDTable(name) {
    val createdBy = reference(
        "created_by", UserTable, onDelete = ReferenceOption.NO_ACTION,
        onUpdate = ReferenceOption.NO_ACTION
    )
    val createdDate = datetime("created_date").defaultExpression(CurrentDateTime())
    val updatedBy = reference(
        "updated_by", UserTable, onDelete = ReferenceOption.NO_ACTION,
        onUpdate = ReferenceOption.NO_ACTION
    ).nullable()
    val updatedDate = datetime("updated_date").nullable()
    val deletedBy = reference(
        "deleted_by", UserTable, onDelete = ReferenceOption.NO_ACTION,
        onUpdate = ReferenceOption.NO_ACTION
    ).nullable()
    val deletedDate = datetime("deleted_date").nullable()
}

abstract class CommonEntity(id: EntityID<UUID>, commonTable: CommonTable) : UUIDEntity(id) {
    var createdBy by UserEntity referencedOn commonTable.createdBy
    var createdDate by commonTable.createdDate
    var updatedBy by UserEntity optionalReferencedOn commonTable.updatedBy
    var updatedDate by commonTable.updatedDate
    var deletedBy by UserEntity optionalReferencedOn commonTable.deletedBy
    var deletedDate by commonTable.deletedDate
    fun softDelete(userID: String) {
        deletedBy = UserEntity.findById(userID.toUuid())
        deletedDate = DateTime.now()
    }


    fun cleanSomeOfIt(): Int {

        return deletedDate?.let {
            val diffInMillisecond: Long = DateTime.now().millis - it.millis
            val res = TimeUnit.MILLISECONDS.toDays(diffInMillisecond)
            if (res > 0) {
                delete()
            }
            return@let 1
        } ?: 0
    }

    open fun softDelete(user: UserEntity) {
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

abstract class CommonEntityClass<E : CommonEntity>(table: CommonTable) : UUIDEntityClass<E>(table) {

    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated)
                action.toEntity(this)?.deletedDate = DateTime.now()
        }
    }

}