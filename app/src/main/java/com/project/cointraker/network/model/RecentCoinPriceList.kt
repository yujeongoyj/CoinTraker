package com.project.cointraker.network.model

import com.project.cointraker.dataModel.RecentPriceData

// "전체" 데이터를 받는 형식
data class RecentCoinPriceList (
    val status : String,
    val data : List<RecentPriceData>

)

