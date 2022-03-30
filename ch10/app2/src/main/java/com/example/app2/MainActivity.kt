package com.example.app2

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            val nofication : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val rington = RingtoneManager.getRingtone(applicationContext, nofication) // Rington 객체를 구함
            rington.play()
        }

        binding.button2.setOnClickListener {
            val player :MediaPlayer = MediaPlayer.create(this, R.raw.funny_voices)
            player.start()
        }

        binding.button3.setOnClickListener {
            // Vibrator 객체 획득
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                val vibratorManager = this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            }
            else{
                getSystemService(VIBRATOR_SERVICE) as Vibrator
            }
            // Vibrator을 이용한 진동
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else{
                vibrator.vibrate(500)
            }

        }
    }
}