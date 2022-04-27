package com.example.ch14

import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ch14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //리시버 호출
        /*
        val receiver = MyReceiver()
        val filter = IntentFilter("ACTION_RECEIVER").apply{
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_OKAY)
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        */
        // 리시버를 호출하지 않고 처리하기
        registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))!!.apply{
            // 배터리 공급 여부
            val status = getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            if(status == BatteryManager.BATTERY_STATUS_CHARGING){
                val chargePlug = getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
                when(chargePlug){
                    BatteryManager.BATTERY_PLUGGED_AC -> {
                        Log.d("mobileApp", "AC CHARGED")
                        binding.chargingResult.text = "AC CHARGED"
                        // 이벤트에 따른 이미지 적용setImageBitmap(BitmapFactory.decodeResource(a,b)
                        binding.chargingTV.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.ac))
                    }
                    BatteryManager.BATTERY_PLUGGED_USB ->{
                        Log.d("mobileApp", "USB CHARGED")
                        binding.chargingResult.text = "USB CHARGED"
                        //이벤트에 따른 이미지 적용setImageBitmap(BitmapFactory.decodeResource(a,b)
                        binding.chargingTV.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.usb))
                    }

                }
            }
            else{
                Log.d("mobileApp", "DISCHARGING")
                binding.chargingResult.text= "DISCHARGING"
            }
            val level = getIntExtra(BatteryManager.EXTRA_LEVEL, -1) // 배터리 정보 가져오기
            val scale = getIntExtra(BatteryManager.EXTRA_SCALE,-1)
            val battPerc = level / scale.toFloat() * 100
            binding.percentResult.text = "$battPerc"
        }

        binding.button.setOnClickListener {
            val intent = Intent(this, MyReceiver::class.java)
            // 브로드캐스트인 경우
            sendBroadcast(intent)
        }

    }
}