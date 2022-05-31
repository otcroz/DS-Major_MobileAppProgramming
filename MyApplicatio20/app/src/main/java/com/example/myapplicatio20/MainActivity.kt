package com.example.myapplicatio20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicatio20.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 해시키 구하기
        //val keyHash = Utility.getKeyHash(this)
        //Log.d("mobileApp", keyHash)

        myCheckPermission(this) // 퍼미션을 획득하는 과정
        // FloatingButton을 눌렀을 때
        binding.addFab.setOnClickListener{
            if(MyApplication.checkAuth()){
                startActivity(Intent(this, AddActivity::class.java))
            }
            else{
                Toast.makeText(this,"인증 진행 해주세요..",  Toast.LENGTH_SHORT).show()
           }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java) // 액티비티 호출로 로그인
            // 로그인, 로그아웃, 회원가입 상태 파악하기
            if(binding.btnLogin.text.equals("로그인"))
                intent.putExtra("data", "logout")
            else if(binding.btnLogin.text.equals("로그아웃"))
                intent.putExtra("data", "login")

            startActivity(intent)
        }
    }

    override fun onStart() { // mainActivity에서 다른 activity로 이동하여 다른 작업 후 다시 돌아올 때 실행하는 메서드
        super.onStart()
        if(MyApplication.checkAuth() || MyApplication.email != null){ // 검증된 이메일인지 확인
            binding.btnLogin.text = "로그아웃"
            binding.authTv.text = "${MyApplication.email}님 반갑습니다."
            binding.authTv.textSize = 16F
            binding.mainRecyclerView.visibility = View.VISIBLE

            makeRecyclerView()
        } else{
            binding.btnLogin.text = "로그인"
            binding.authTv.text = "덕성 모바일"
            binding.authTv.textSize = 24F
            binding.mainRecyclerView.visibility = View.GONE
        }
    }

    private fun makeRecyclerView(){ // 파이어스토어에 저장된 정보를 가져온다.
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemData>()
                for(document in result) { // for 문으로 data를 가져옴, itemList에 내용 저장
                    val item = document.toObject(ItemData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.mainRecyclerView.adapter = MyAdapter(this, itemList)
            }
            .addOnFailureListener{
                Toast.makeText(this,"서버 데이터 획득 실패",  Toast.LENGTH_SHORT).show()
            }
    }
}