package com.project.cointraker.view.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.cointraker.R
import com.project.cointraker.databinding.ActivitySettingBinding
import com.project.cointraker.service.PriceForegroundService

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val isTiramisuOrHigher = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        val notificationPermission = Manifest.permission.POST_NOTIFICATIONS

        var hasNotificationPermission =
            if (isTiramisuOrHigher)
                ContextCompat.checkSelfPermission(
                    this,
                    notificationPermission
                ) == PackageManager.PERMISSION_GRANTED
            else true

        val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            hasNotificationPermission = it
            if (it) {
                val intent = Intent(this, PriceForegroundService::class.java)
                intent.action = "START"
                startService(intent)
            }
        }

        binding.startForeground.setOnClickListener {

            Toast.makeText(this, "start", Toast.LENGTH_LONG).show()

            if (hasNotificationPermission) {
                val intent = Intent(this, PriceForegroundService::class.java)
                intent.action = "START"
                startService(intent)
            } else if (isTiramisuOrHigher) {
                launcher.launch(notificationPermission)
            }


        }

        binding.stopForeground.setOnClickListener {

            Toast.makeText(this, "stop", Toast.LENGTH_LONG).show()

            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "STOP"
            startService(intent)


        }


    }
}