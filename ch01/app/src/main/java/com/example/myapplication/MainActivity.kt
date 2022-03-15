package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var button1 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // 부모가 가지고 있는 oncreate 함수를 그대로 사용

        /*
        setContentView(R.layout.activity_main) // xml 파일을 보여줌

        // val tv1 = findViewById(R.id.textView)
        val tv1 : TextView = findViewById(R.id.textView)
        val rbar: RatingBar = findViewById(R.id.ratingBar)
        val btn : Button = findViewById(R.id.button)
        val chb : CheckBox = findViewById(R.id.checkBox)
        val rdo : RadioButton = findViewById(R.id.radioButton)
        //findViewById를 너무 많이 사용함 => 뷰 바인딩 사용
        tv1.visibility = View.INVISIBLE

        */

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView.visibility = View.INVISIBLE
        binding.button.visibility = View.INVISIBLE

        Log.d("myCheck", "안드로이드 시작 - 로그 출력")
    }
}