package com.example.mydjango

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydjango.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDjango.setOnClickListener {
            getList()
        }

        // 호스트가 데이터를 입력할 수 있는 View를 보이도록 한다.
        binding.btnPost.setOnClickListener {
            binding.layout1.visibility = View.VISIBLE
            binding.layout2.visibility = View.VISIBLE
            binding.layout3.visibility = View.VISIBLE
            binding.btnPost2.visibility = View.VISIBLE

        }

        binding.btnPost2.setOnClickListener { // 데이터 추가하기 및 불러오기
            val name = binding.edtName.text.toString()
            val phone = binding.edtPhone.text.toString()
            val addr = binding.edtAddr.text.toString()

            if(name != "" && phone !="" && addr !=""){
                val data = hInfoModel(name, phone, addr)
                // apiService가 data를 가지고 url에 접속하여 서버에 데이터 전달 및 추가
                var call:Call<hInfoModel> = MyApplication.apiService.postData(data)
                call?.enqueue(object:Callback<hInfoModel>{
                    override fun onResponse(
                        call: Call<hInfoModel>,
                        response: Response<hInfoModel>
                    ) {
                        getList()
                    }

                    override fun onFailure(call: Call<hInfoModel>, t: Throwable) {
                        Log.d("mobileApp", "오류 발생")
                    }

                })

            }
            else{
                Toast.makeText(this, "데이터를 모두 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            binding.edtName.text.clear()
            binding.edtPhone.text.clear()
            binding.edtAddr.text.clear()
            binding.layout1.visibility = View.GONE
            binding.layout2.visibility = View.GONE
            binding.layout3.visibility = View.GONE
            binding.btnPost2.visibility = View.GONE
        }

    }

    private fun getList(){ // 응답이 잘 이루어졌을 때 recyclerView 생성
        var call: Call<MutableList<hInfoModel>> = MyApplication.apiService.getList("json")
        call?.enqueue(object: Callback<MutableList<hInfoModel>>{
            override fun onResponse(
                call: Call<MutableList<hInfoModel>>,
                response: Response<MutableList<hInfoModel>>
            ) {
                if(response.isSuccessful){
                    binding.recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.recycler.adapter = HinfoAdapter(this@MainActivity, response.body()?.toMutableList<hInfoModel>())
                }
            }

            override fun onFailure(call: Call<MutableList<hInfoModel>>, t: Throwable) {
                Log.d("mobileApp", "오류 발생")
            }

        })
    }
}