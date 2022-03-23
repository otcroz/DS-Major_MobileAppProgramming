package com.example.ch08_events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Toast
import com.example.ch08_events.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var pauseTime = 0L
    var initTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime()  + pauseTime // 기준 시간
            binding.chronometer.start()
            binding.buttonStart.isEnabled = false
            binding.buttonStop.isEnabled = true
            binding.buttonReset.isEnabled = true
        }
        binding.buttonStop.setOnClickListener {
            // stop 버튼을 눌렀을 때의 시간(음수)
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.buttonStart.isEnabled = true
            binding.buttonStop.isEnabled = false
            binding.buttonReset.isEnabled = true

        }
        binding.buttonReset.setOnClickListener {
            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.buttonStart.isEnabled = true
            binding.buttonStop.isEnabled = true
            binding.buttonReset.isEnabled = false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis()-initTime > 3000){ // 클릭 후 3초 내에 또 클릭했을 때
                Toast.makeText(this, "종료하려면 한 번 더 누르세요", Toast.LENGTH_LONG).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}