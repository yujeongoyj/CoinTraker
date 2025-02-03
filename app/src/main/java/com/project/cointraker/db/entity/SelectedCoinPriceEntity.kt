package com.project.cointraker.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "selected_coin_price_table")
data class SelectedCoinPriceEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val coinName: String,
    val transaction_date: String,
    val type: String,
    val units_traded: String,
    val price: String,
    val total: String,
    val timeStamp : Date // 이런 타입은 DB에 바로 안들어 가므로 converter를 생성
)

class DateConverters {
     @TypeConverter
     fun fromTimeStamp(value : Long) : Date {
        return Date(value)
     }

    @TypeConverter
    fun dateToTimeStamp(date : Date) : Long {
        return date.time
    }
}
