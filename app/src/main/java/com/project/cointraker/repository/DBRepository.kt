package com.project.cointraker.repository

import com.project.cointraker.App
import com.project.cointraker.db.CoinPriceDatabase
import com.project.cointraker.db.entity.InterestCoinEntity
import com.project.cointraker.db.entity.SelectedCoinPriceEntity

// DB - DAO  - ViewModel - Activity 원래는 이렇게만 접근해도 상관이 없지만 관리가 불편하므로 DB Repo를 사용
// DB - DAO - Repository - ViewModel - Activity
class DBRepository {

    val context = App.context()
    val db = CoinPriceDatabase.getDatabase(context)

    fun getAllInterestCoinData() = db.interestCoinDAO().getAllData()

    fun insertInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().insert(interestCoinEntity)

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().update(interestCoinEntity)

    fun getAllInterestSelectedCoinData() = db.interestCoinDAO().getSelectedData()

    // CoinPrice

    fun getAllCoinPriceData() = db.selectedCoinDAO().getAllData()

    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) = db.selectedCoinDAO().insert(selectedCoinPriceEntity)
    // 하나의 코인 정보
    fun getOneSelectedCoinData(coinName : String) = db.selectedCoinDAO().getOneCoinData(coinName)



}










