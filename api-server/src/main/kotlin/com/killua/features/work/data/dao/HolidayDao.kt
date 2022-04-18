package com.killua.features.work.data.dao

import com.killua.features.user.data.dao.UserEntity
import com.killua.features.user.data.dao.UserTable
import com.killua.features.commondao.CommonEntity
import com.killua.features.commondao.CommonTable
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.minus
import org.jetbrains.exposed.sql.jodatime.date
import org.jetbrains.exposed.sql.jodatime.day
import java.util.*

object HolidayTable : CommonTable("holidays") {
    val user = reference("user", UserTable)
    val holidayStartDate = date("holiday_start_date")
    val holidayEndDate = date("holiday_end_date")
    val daysOfHoliday = integer("days_of_holiday").nullable()
        .default(holidayEndDate.day().minus(holidayStartDate.day()).toString().toIntOrNull())
}

class HolidayEntity(id: EntityID<UUID>) : CommonEntity(id, HolidayTable) {
    companion object : UUIDEntityClass<HolidayEntity>(HolidayTable)

    val user by UserEntity referencedOn HolidayTable.user
    val holidayStart by HolidayTable.holidayStartDate
    val holidayEndDate by HolidayTable.holidayEndDate
    val daysOfHoliday by HolidayTable.daysOfHoliday


}
/*
abstract class ExtendedIntIdTable(name: String ) : IntIdTable(name) {

    val createdAt = datetime("createdAt").default(DateTime.now())
    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()

}

abstract class ExtendedIntEntity(id: EntityID<Int>, table: ExtendedIntIdTable) : IntEntity(id) {
    val createdAt by table.createdAt
    var updatedAt by table.updatedAt
    var deletedAt by table.deletedAt

    override fun delete() {
        deletedAt = DateTime.now()
    }
}

abstract class ExtendedIntEntityClass<E:ExtendedIntEntity>(table: ExtendedIntIdTable) : IntEntityClass<E>(table) {

    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated)
                action.toEntity(this)?.updatedAt = DateTime.now()
        }
    }
}

object FooTable : ExtendedIntIdTable("Foo") {
    val bar = varchar("bar", 255).nullable()
}

class FooEntity(id: EntityID<Int>) : ExtendedIntEntity(id, FooTable) {

    val bar by FooTable.bar

    companion object : ExtendedIntEntityClass<FooEntity>(FooTable)
}
 */