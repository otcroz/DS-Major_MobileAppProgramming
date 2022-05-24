package com.example.pratice_mobile_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pratice_mobile_app.databinding.ActivityMyBinding

class MyActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data1 = intent.getStringExtra("ysy1")
        val data2 = intent.getStringExtra("ysy2")

        binding.textInvert.text = data1 + data2

        binding.addActivityGo.setOnClickListener{
            val intent = Intent()
            intent.action = "ACTION_EDIT"
            startActivity(intent)
        }


    }
}