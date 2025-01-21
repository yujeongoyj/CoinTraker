package com.project.cointraker.repository

import com.project.cointraker.Api
import com.project.cointraker.network.RetrofitInstance

// Api 호출을 관리하는 repository
// Api- repository - viewModel 단계를 거치도록
class NetWorkRepository {
    private val client = RetrofitInstance.getInstance().create(Api::class.java)

    suspend fun getCurrentCoinList() = client.getCurrentConList()


}