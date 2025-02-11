package com.project.cointraker.service


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.project.cointraker.R
import com.project.cointraker.dataModel.CurrentPrice
import com.project.cointraker.dataModel.CurrentPriceResult
import com.project.cointraker.repository.NetWorkRepository
import com.project.cointraker.view.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Random

class PriceForegroundService : Service() {

    private val netWorkRepository = NetWorkRepository()

    private val NOTIFICATION_ID = 10000

    lateinit var job: Job

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // intent에 들어온 값이 start일 때
        when (intent?.action) {

            "START" -> {
                job = CoroutineScope(Dispatchers.Default).launch {

                    while(true) {
                        Timber.d("START")
                        startForeground(NOTIFICATION_ID, makeNotification())

                        delay(3000)
                    }

                }
             }

            "STOP" -> {
                Timber.d("STOP")

                try {
                    job.cancel()
                    stopForeground(true)
                    stopSelf()
                } catch (e : java.lang.Exception) {

                }

            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    suspend fun makeNotification(): Notification {

        val result = getAllCoinList()
        val randomNum = Random().nextInt(result.size)
        val title = result[randomNum].coinName
        val content = result[randomNum].coinInfo.fluctate_24H


        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
            .setContentTitle("코인 이름 : $title")
            .setContentText("변동 가격 : $content")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "name"
            val descriptionText = "descriptionTest"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        return builder.build()


    }

    suspend fun getAllCoinList(): ArrayList<CurrentPriceResult> {

        val result = netWorkRepository.getCurrentCoinList()
        val currentPriceResultList = ArrayList<CurrentPriceResult>()

        for (coin in result.data) {

            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)
                currentPriceResultList.add(currentPriceResult)

            } catch (e: java.lang.Exception) {
                Timber.d(e.toString())
            }

        }
        return currentPriceResultList

    }


}
