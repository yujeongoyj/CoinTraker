package com.project.cointraker.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.project.cointraker.repository.DBRepository
import com.project.cointraker.repository.NetWorkRepository
import timber.log.Timber

// 최근 거래된 코인 가격 내역을 가져오는 WorkManager

/* 1. 관심 있어하는 코인 리스트를 가져오기
 2. 코인 각각의 가격 변동 정보를 가져오기
 3. DB에 저장*/

// WorkManager는 주로 반복적인 작업, 앱이 종료되거나 재부팅 후에도 지속적으로
// 실행해야 하는 작업을 처리할 때 사용
class GetCoinPriceRecentContractedWorkManager(
    val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val dbRepository = DBRepository()
    private val netWorkRepository = NetWorkRepository()

    override suspend fun doWork(): Result {

        Timber.d("doWork")

        getAllInterestSelectedCoinData()

        return Result.success()
    }

    // 1. 관심 있어하는 코인 리스트를 가져오기
    suspend fun getAllInterestSelectedCoinData() {

        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        for (coinData in selectedCoinList) {
            Timber.d(coinData.toString())


            val recentCoinPriceList = netWorkRepository.getInterestCoinPriceData(coinData.coin_name)

            Timber.d(recentCoinPriceList.toString())
        }

    }

}