package com.killua.features.image.data.dao

import com.killua.config.Names.ACCIDENT_COLUMN
import com.killua.config.Names.CAR_COLUMN
import com.killua.config.Names.IMAGE_TABLE
import com.killua.config.Names.IMAGE_URI_COLUMN
import com.killua.config.Names.USED_HISTORY_COLUMN
import com.killua.config.Names.USER_COLUMN
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentTable
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import com.killua.features.commondao.CommonEntity
import com.killua.features.commondao.CommonTable
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryEntity
import com.killua.features.vehiclemanager.usedhistory.data.dao.UsedHistoryTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ImageEntity(id: EntityID<UUID>) : CommonEntity(id, ImagesTable) {
    companion object : UUIDEntityClass<ImageEntity>(ImagesTable)

    var imageUri by ImagesTable.imageUri
    var accident by AccidentEntity optionalReferencedOn ImagesTable.accident
    var user by UserEntity optionalReferencedOn ImagesTable.user
    var car by CarEntity optionalReferencedOn ImagesTable.car
    var usedHistory by UsedHistoryEntity optionalReferencedOn ImagesTable.usedHistory
}

object ImagesTable : CommonTable(IMAGE_TABLE) {
    val imageUri = text(IMAGE_URI_COLUMN).nullable()
    val accident = reference(ACCIDENT_COLUMN, AccidentTable).nullable().default(null)
    val user = reference(USER_COLUMN, UserTable).nullable().default(null)
    val car = reference(CAR_COLUMN, CarTable).nullable().default(null)
    val usedHistory = reference(USED_HISTORY_COLUMN, UsedHistoryTable).nullable().default(null)
}


