package com.killua.features.vehiclemanager.usedhistory.data.dao

import com.killua.features.vehiclemanager.commondao.CommonEntity
import com.killua.features.vehiclemanager.commondao.CommonTable
import com.killua.features.image.data.dao.ImageEntity
import com.killua.features.image.data.dao.ImagesTable
import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.vehiclemanager.car.data.dao.CarEntity
import com.killua.features.vehiclemanager.car.data.dao.CarTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.joda.time.DateTime
import java.util.*


class UsedHistoryEntity(id: EntityID<UUID>) : CommonEntity(id, UsedHistoryTable) {
    companion object : UUIDEntityClass<UsedHistoryEntity>(UsedHistoryTable)

    var user by UserEntity referencedOn UsedHistoryTable.user
    var car by CarEntity referencedOn UsedHistoryTable.car
    var givingDate by UsedHistoryTable.givingDate
    val images by ImageEntity optionalReferrersOn ImagesTable.driverHistory
    val returnDate by UsedHistoryTable.returnDate

}

object UsedHistoryTable : CommonTable("used_histories") {
    var user = reference("user", UserTable)
    var car = reference("car", CarTable)
    val givingDate = long("giving_date").default(DateTime.now().millis)
    val returnDate = long("return_date").nullable().default(null)
}
