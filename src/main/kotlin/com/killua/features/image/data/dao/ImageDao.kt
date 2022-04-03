package com.killua.features.image.data.dao

import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.accident.data.dao.AccidentEntity
import com.killua.features.vehiclemanager.accident.data.dao.AccidentTable
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.data.dao.CarTable
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

object ImagesTable : CommonTable("images") {
    val imageUri = text("image_uri").nullable()
    val accident = reference("accident", AccidentTable).nullable().default(null)
    val user = reference("user_image", UserTable).nullable().default(null)
    val car = reference("car_image", CarTable).nullable().default(null)
    val usedHistory = reference("used_history", UsedHistoryTable).nullable().default(null)
}


