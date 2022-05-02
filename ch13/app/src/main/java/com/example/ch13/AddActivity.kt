package com.example.ch13

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
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

        // 자기 자신의 액티비티 호출하기
        binding.button5.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent()
            // 암시적으로 인텐트 불러옴
            intent.action = "ACTION_EDIT" // ACTION_EDIT의 액션을 취하는 액티비티를 불러옴
            intent.data = Uri.parse("http://www.google.com")
            startActivity(intent)
        }

        // 소프트 키보드 사용: Input 메서드 매니저 호출
        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.button3.setOnClickListener{
            binding.addEditView.requestFocus()
            manager.showSoftInput(binding.addEditView, InputMethodManager.SHOW_IMPLICIT)
        }
        binding.button4.setOnClickListener {
            manager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
        
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    //옵션 메뉴 사용하기
    // 옵션에 대한 오버라이딩 함수 호출
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu) // 메뉴 사용 선언
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_save){ // 아이템이 선택되었을 때
            val inputData = binding.addEditView.text.toString() // 변수에 데이터 저장

            // DB에 데이터 저장
            val db = DBHelper(this).writableDatabase // 데이터를 가져옴
            db.execSQL("insert into todo_tb (todo) values (?)", arrayOf<String>(inputData)) // ? 사용자가 입력해온 값을 넣는 것(따옴표 안에는 고정된 값을 넣기 때문)
            db.close()

            intent.putExtra("result", inputData) // 텍스트를 string 값으로 얻어온다.
            // 되돌아가기
            setResult(RESULT_OK, intent) // 1번째 매개변수로 주로 RESULT_OK 사용
            finish() // 액티비티 종료

            return true
        }
        return false
    }
}