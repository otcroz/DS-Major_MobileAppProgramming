package com.example.ch08

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                Log.d("mobileApp", "Action DOWN : ${event.x}, ${event.y}")
            }
            MotionEvent.ACTION_UP ->{
                Log.d("mobileApp", "Action UP ${event.rawX}, ${event.rawY}")
            }
            MotionEvent.ACTION_MOVE ->{
                Log.d("mobileApp", "Action MOVE")
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("mobileApp", "onKeyDown")
        when(keyCode){
            KeyEvent.KEYCODE_0 -> {Log.d("mobileApp", "0 Key Down")}
            KeyEvent.KEYCODE_B -> {Log.d("mobileApp", "B Key Down")}
            KeyEvent.KEYCODE_BACK -> {Log.d("mobileApp", "뒤로가기 Key Down")}
            KeyEvent.KEYCODE_VOLUME_UP -> {Log.d("mobileApp", "VOLUNE_UP")}
            KeyEvent.KEYCODE_VOLUME_DOWN -> {Log.d("mobileApp", "VOLUME_DOWN")}
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("mobileApp", "onKeyUp")
        return super.onKeyUp(keyCode, event)
    }

    override fun onBackPressed() {
       Log.d("mobileApp", "뒤로가기 Key Down")
    }
}