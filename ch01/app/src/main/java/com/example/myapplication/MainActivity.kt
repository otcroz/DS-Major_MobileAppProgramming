package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var button1 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // 부모가 가지고 있는 oncreate 함수를 그대로 사용

        setContentView(R.layout.activity_main) // xml 파일을 보여줌

        Log.d("myCheck", "안드로이드 시작 - 로그 출력")
    }
}