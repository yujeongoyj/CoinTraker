package com.project.cointraker.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.project.cointraker.db.entity.SelectedCoinPriceEntity
import com.project.cointraker.network.model.RecentCoinPriceList
import com.project.cointraker.repository.DBRepository
import com.project.cointraker.repository.NetWorkRepository
import kotlinx.coroutines.channels.ticker
import timber.log.Timber
import java.util.Calendar
import java.util.Date

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

        val timeStamp = Calendar.getInstance().time

        for (coinData in selectedCoinList) {
            Timber.d(coinData.toString())


            val recentCoinPriceList = netWorkRepository.getInterestCoinPriceData(coinData.coin_name)

            Timber.d(recentCoinPriceList.toString())

            saveSelectedCoinPrice(
                coinData.coin_name,
                recentCoinPriceList,
                timeStamp
            )
        }

    }

    fun saveSelectedCoinPrice(
        coinName: String,
        recentCoinPriceList: RecentCoinPriceList,
        timeStamp : Date
    ) {
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStamp
        )

        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)
    }

}