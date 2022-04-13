package com.example.ch13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.ch13.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    //binding을 전역변수로 선언, lateinit로 선언
    lateinit var binding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val d1 = intent.getStringExtra("data1") // 매개변수: 전달되는 값의 이름
        val d2 = intent.getStringExtra("data2")

        binding.tv.text = (d1+d2)

        // 버튼 클릭 이벤트: 돌아가기
        /*binding.button1.setOnClickListener {
            intent.putExtra("test", "world")
            // 되돌아가기
            setResult(RESULT_OK, intent) // 1번째 매개변수로 주로 RESULT_OK 사용
            finish() // 액티비티 종료
        }*/

        //옵션 메뉴 사용하기
        
    }

    // 옵션에 대한 오버라이딩 함수 호출
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu) // 메뉴 사용 선언
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_save){ // 아이템이 선택되었을 때
            intent.putExtra("result", binding.addEditView.text.toString()) // 텍스트를 string 값으로 얻어온다.
            // 되돌아가기
            setResult(RESULT_OK, intent) // 1번째 매개변수로 주로 RESULT_OK 사용
            finish() // 액티비티 종료

            return true
        }
        return false
    }
}