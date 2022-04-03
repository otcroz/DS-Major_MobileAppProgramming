package com.example.application11

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.application11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() { //안드로이드 X를 사용하면서 AppCompatActivity을 상속받는 형태가 됨, 자동으로 추가되어 사용
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)} //초기화를 늦추어 다른 function에서도 사용할 수 있도록

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // 옵션 메뉴 추가
        //val menuItem1 : MenuItem? = menu?.add(0,0,0, "메뉴1") // 2번째 매개변수: 식별자, 4번째 매개변수: 메뉴에 노출되는 문자
        //val menuItem2 : MenuItem? = menu?.add(0,1,0, "메뉴2")

        //리소스로 만든 메뉴 적용
        menuInflater.inflate(R.menu.menu_main, menu) 
        return super.onCreateOptionsMenu(menu) // menu를 전달하여 적용하도록
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // 메뉴의 아이템이 선택되었을 때 이벤트 처리
        when(item.itemId){
            R.id.menu1 -> {
                binding.tv1.setTextColor(Color.BLUE)
                true //리턴값에 대한 값을 가져야 한다. -> true로 설정
            }
            R.id.menu2 -> {
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}