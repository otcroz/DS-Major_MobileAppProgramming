package com.example.ch18_network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ch18_network.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RetrofitFragment()
        val xmlfragment = XmlFragment()
        val bundle = Bundle()
        binding.searchBtn.setOnClickListener {
            when(binding.rGroup.checkedRadioButtonId){
                R.id.json -> bundle.putString("returnType", "json")
                R.id.xml -> bundle.putString("returnType", "xml")
                else -> bundle.putString("returnType", "json")
            }
            if(binding.rGroup.checkedRadioButtonId == R.id.json){
                Log.d("testFragment", "R.id.json")
                fragment.arguments = bundle // 프래그먼트에 값 전달
                supportFragmentManager.beginTransaction() // 액티비티에 프래그먼트 보이게
                    .replace(R.id.activity_content, fragment)
                    .commit()
            } else if(binding.rGroup.checkedRadioButtonId == R.id.xml){
                Log.d("testFragment", "R.id.xml")
                xmlfragment.arguments = bundle // 프래그먼트에 값 전달
                supportFragmentManager.beginTransaction() // 액티비티에 프래그먼트 보이게
                    .replace(R.id.activity_content, xmlfragment)
                    .commit()
            }

        }
    }
}