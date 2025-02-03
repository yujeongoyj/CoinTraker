package com.project.cointraker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.cointraker.db.entity.SelectedCoinPriceEntity


@Dao
interface SelectedCoinPriceDAO {

    // getAllData
    @Query("select * from selected_coin_price_table")
    fun getAllData() : List<SelectedCoinPriceEntity>

    // insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)

    // 하나의 코인에 대해서 저장된 정보를 가져오도록
    // BTC 15 30 45분전 등 -> List<BTC> -> 현재 가격, 15 30, 45가 어떻게 변했는지 디비값과 비교
    @Query("select * from selected_coin_price_table where coinName = :coinName")
    fun getOneCoinData(coinName: String) : List<SelectedCoinPriceEntity>

}