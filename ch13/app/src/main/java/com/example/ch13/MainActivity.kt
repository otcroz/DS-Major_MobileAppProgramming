package com.example.ch13

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
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

        //ActivityResultLauncher 사용하기
        val requestLauncher:ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            //ActivityResultLauncher를 사용하면 인텐트가 되돌아올 때 여기(콜백 함수)로 되돌아옴
            val d3 = it.data!!.getStringExtra("result")
            Log.d("mobileApp", d3!!) // !! 널이 아닌 것을 명시
        }

        binding.fab.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java) // 인텐트 생성
            intent.putExtra("data1", "mobile") // 매개변수: 전달되는 값의 이름, 전달하려는 값...
            intent.putExtra("data2", "app")
            //startActivity(intent) // 인텐트 호출
            //startActivityForResult(intent, 10) // 매개변수: 인텐트, 호출값
            requestLauncher.launch(intent) // ActivityResultLauncher 요청
        }
    }

    // 이 메서드를 통해 인텐트가 돌아옴
    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode== RESULT_OK){

        }
    }*/
}