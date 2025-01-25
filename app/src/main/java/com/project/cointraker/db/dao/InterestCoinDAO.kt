package com.project.cointraker.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.cointraker.db.entity.InterestCoinEntity
import com.project.cointraker.view.SelectActivity
import kotlinx.coroutines.flow.Flow


@Dao
interface InterestCoinDAO {
    // getAllData
    // flow는 데이터의 변경 사항을 감지하기 좋다
    @Query("SELECT * FROM interested_coin_table")
    fun getAllData() : Flow<List<InterestCoinDAO>>


    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(insertCoinEntity: InterestCoinEntity)

    // Update
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)


    // getSelectedCoinList 관심있는 코인 리스트
    @Query("SELECT * FROM interested_coin_table WHERE selected = :selected")
    fun getSelectedData(selected : Boolean = true) : List<InterestCoinEntity>
}