package com.example.ch13

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datas:MutableList<String>? = null
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.adapter = MyAdapter(datas)

        binding.fab.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java) // 인텐트 생성
            intent.putExtra("data1", "mobile") // 매개변수: 전달되는 값의 이름, 전달하려는 값...
            intent.putExtra("data2", "app")
            //startActivity(intent) // 인텐트 호출
            startActivityForResult(intent, 10) // 매개변수: 인텐트, 호출값
        }
    }

    // 이 메서드를 통해 인텐트가 돌아옴
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode== RESULT_OK){
            val d3 = data?.getStringExtra("test")
            Log.d("mobileApp", d3!!) // !! 널이 아닌 것을 명시
        }
    }
}