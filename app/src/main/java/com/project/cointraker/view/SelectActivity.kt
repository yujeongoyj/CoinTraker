package com.project.cointraker.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.project.cointraker.view.main.MainActivity
import com.project.cointraker.view.adapter.SelectRVAdapter
import com.project.cointraker.R
import com.project.cointraker.background.GetCoinPriceRecentContractedWorkManager
import com.project.cointraker.databinding.ActivitySelectBinding
import java.util.concurrent.TimeUnit

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding

    private val viewModel: SelectViewModel by viewModels()

    private lateinit var selectRVAdapter: SelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getCurrentCoinList()
        // this(라이브 객체)에 Observer를 설정해 liveData가 데이터를 방출할 때마다 자동 호출
        viewModel.currentPriceResult.observe(this, Observer {
            selectRVAdapter = SelectRVAdapter(this, it)
            binding.coinListRV.adapter = selectRVAdapter
            binding.coinListRV.layoutManager = LinearLayoutManager(this)

        })



        binding.laterTextArea.setOnClickListener {

            viewModel.setUpFirstFlag()
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)

        }

        viewModel.save.observe(this, Observer {
            if (it.equals("done")) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // 가장 처음으로 저장된 코인 정보가 시작되는 시점
                saveInterestCoinDataPeriodic()

            }
        })

    }

    private fun saveInterestCoinDataPeriodic() {
        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentContractedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        // 계속 실행하면 여러개가 돌아갈텐데 그걸 방지하고 유니크하게 하나만(?) 돌아가도록
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentContractedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }
}