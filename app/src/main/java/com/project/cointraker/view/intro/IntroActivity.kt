package com.project.cointraker.view.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.project.cointraker.MainActivity
import com.project.cointraker.R
import com.project.cointraker.databinding.ActivityIntroBinding
import timber.log.Timber


// https://developer.android.com/develop/ui/views/launch/splash-screen?hl=ko

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private val viewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // onCreate 메서드는 액티비티가 시작될 때 호출되므로, 테마가 이미 설정된 후에
        // 스플래시를 호출하면 AppCompat 에러가 나지 않게 함
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Timber.d("onCreate")

        viewModel.checkFirstFlag()
        viewModel.first.observe(this, Observer {

            if (it) {
                // 처음 접속하는 유저가 아님
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                binding.animationView.visibility = View.INVISIBLE
                binding.fragmentContainerView.visibility = View.VISIBLE


            }
        })
    }
}