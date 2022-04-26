package com.example.ch13

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 전역 변수로 선언, var로 변경
    var datas:MutableList<String>? = null
    lateinit var adapter : MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 전체 화면 설정 (SDK 버전 고려)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {// >=30
            window.setDecorFitsSystemWindows(false) // 전체화면으로 설정
            val controller = window.insetsController
            if(controller != null){
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }else{
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        //ActivityResultLauncher 사용하기
        val requestLauncher:ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            //ActivityResultLauncher를 사용하면 인텐트가 되돌아올 때 여기(콜백 함수)로 되돌아옴
            val d3 = it.data!!.getStringExtra("result")?.let{
                datas?.add(it) // datas에 데이터 추가
                adapter.notifyDataSetChanged() // 리사이클러 뷰의 변경을 명시
            }
            //Log.d("mobileApp", d3!!) // !! 널이 아닌 것을 명시
        }

        binding.fab.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java) // 인텐트 생성
            intent.putExtra("data1", "mobile") // 매개변수: 전달되는 값의 이름, 전달하려는 값...(데이터 더 전달 가능)
            intent.putExtra("data2", "app")
            //startActivity(intent) // 인텐트 호출
            //startActivityForResult(intent, 10) // 매개변수: 인텐트, 호출값
            requestLauncher.launch(intent) // ActivityResultLauncher 요청
        }

        //액티비티가 비활성 -> 활성되었을 때 Bundle에 저장되었던 datas의 값을 받는다.
        datas = savedInstanceState?.let{
            it.getStringArrayList("mydatas")?.toMutableList() // key 값을 통해 값을 얻어옴
        } ?: let{ // null일 때
            mutableListOf<String>() // 리스트 생성 및 선언
        }

        // 데이터를 가지고 온 이후(savedInstanceState) 리사이클러 뷰의 어댑터 설정
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(datas)
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
    }

    // datas의 값을 저장해두는 함수
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putStringArrayList("mydatas", ArrayList(datas)) // Bundle에 ArrayList 값을 저장
    }

    // 이 메서드를 통해 인텐트가 돌아옴
    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode== RESULT_OK){

        }
    }*/
}