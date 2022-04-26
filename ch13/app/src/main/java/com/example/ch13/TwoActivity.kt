package com.example.ch13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ch13.databinding.ActivityTwoBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlin.system.measureTimeMillis

class TwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_two)

        val binding = ActivityTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ANR 문제를 확인하는 실습
        binding.button6.setOnClickListener {
            /*
            var sum = 0L
            var time = measureTimeMillis {
                for ( i in 1..4000000000){
                    sum += i
                }
            } //실행시간을 측정하여 저장
            Log.d("mobileApp", "걸린 시간: $time")
            binding.tv3.text = "합계: $sum"

             */
            // 스코프 생성
            val channel = Channel<Long>() // 채널을 통해 값 전달
            var  bgScope = CoroutineScope(Dispatchers.Default + Job())
            bgScope.launch {
                var sum = 0L
                var time = measureTimeMillis {
                    for ( i in 1..4000000000){
                        sum += i
                    }
                } //실행시간을 측정하여 저장
                Log.d("mobileApp", "걸린 시간: $time")
                channel.send(sum)
            }
            // 메인 스레드에서 작동할
            val mainScope = GlobalScope.launch(Dispatchers.Main) {
                // 채널을 통해 전달된 값을 출력하기 위해 호출
                channel.consumeEach{
                    binding.tv3.text = "$it"
                }

            }
        }
    }
}