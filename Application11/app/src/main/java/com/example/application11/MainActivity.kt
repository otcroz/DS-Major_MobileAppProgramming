package com.example.application11

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView // 임포트 부분 확인 필요: androidx
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.application11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() { //안드로이드 X를 사용하면서 AppCompatActivity을 상속받는 형태가 됨, 자동으로 추가되어 사용
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)} //초기화를 늦추어 다른 function에서도 사용할 수 있도록

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar) // **툴바를 액션 바 형태로 적용
        
        //**Fragment 적용하기
        val fragmentManeger : FragmentManager = supportFragmentManager
        val transection : FragmentTransaction = fragmentManeger.beginTransaction() // 프레그먼트 트랜젝션 추가
        
        //프레그먼트 추가
        var fragment = Fragment_1()
        transection.add(R.id.fragment_content, fragment) // activtity_main에 해당하는 레이아웃(LinearLayout의 fragment에 추가한다.)
        transection.commit() // 트랜젝션 실행(프레그먼트 실행)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // 옵션 메뉴 추가
        //val menuItem1 : MenuItem? = menu?.add(0,0,0, "메뉴1") // 2번째 매개변수: 식별자, 4번째 매개변수: 메뉴에 노출되는 문자
        //val menuItem2 : MenuItem? = menu?.add(0,1,0, "메뉴2")

        //리소스로 만든 메뉴 적용
        menuInflater.inflate(R.menu.menu_main, menu)

        //검색 메뉴에 대한 코드
        val menuSearch = menu?.findItem(R.id.menu_search)
        val searchView = menuSearch?.actionView as SearchView //메뉴 아이템이 연결된 액션 뷰(searchView를 가지고 온다)
        searchView.setOnQueryTextListener( object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean { // 텍스트가 바뀔 때마다
                //TODO("Not yet implemented")

                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean { // 텍스트를 검색할 때마다
                //TODO("Not yet implemented")
                binding.tv1.text = p0
                    return true
            }
        }


        )
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