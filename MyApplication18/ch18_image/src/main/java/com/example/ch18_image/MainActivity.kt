package com.example.ch18_image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_image.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            var call: Call<PageListModel> = MyApplication.networkService.getList(
                //요청 내용 전달
                1,
                10,
                "json",
                "CDNRFWzcqVNIQ++7vj9QCBoCKvsk5fAEh/nT6XXO+49SR7SN2qEWcX9vTorvWC1Zsgn1VGftwEZslejzAUs/ww=="
            )
            call?.enqueue(object: Callback<PageListModel> {
                override fun onResponse(
                    call: Call<PageListModel>,
                    response: Response<PageListModel>
                ) {
                    if(response.isSuccessful){ // 올바르게 응답이 왔을 때
                        Log.d("mpbildApp", "${response.body()}")
                        binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.retrofitRecyclerView.adapter = MyAdapter(this@MainActivity, response.body()?.data)

                    }
                }

                override fun onFailure(call: Call<PageListModel>, t: Throwable) {
                    Log.d("mobileApp", "onFailure...")
                }
            })
        }

    }
}