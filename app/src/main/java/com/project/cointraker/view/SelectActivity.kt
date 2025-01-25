package com.project.cointraker.view

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.cointraker.MainActivity
import com.project.cointraker.R
import com.project.cointraker.databinding.ActivitySelectBinding
import com.project.cointraker.view.adapter.SelectRVAdapter
import timber.log.Timber

class SelectActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectBinding

    private val viewModel : SelectViewModel by viewModels()

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

        viewModel.setUpFirstFlag()

        binding.laterTextArea.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}