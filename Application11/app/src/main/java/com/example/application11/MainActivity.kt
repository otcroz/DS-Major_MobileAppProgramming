package com.example.application11

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView // 임포트 부분 확인 필요: androidx
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.application11.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() { //안드로이드 X를 사용하면서 AppCompatActivity을 상속받는 형태가 됨, 자동으로 추가되어 사용

    class MyFragmentAdapter(activity:FragmentActivity) : FragmentStateAdapter(activity){
        // 프레그먼트들의 대한 배열
        val fragments: List<Fragment>
        init {
            fragments = listOf(Fragment1(), Fragment2(), Fragment3()) // 3개의 프레그먼트 등록
        }

        override fun getItemCount(): Int { // 등록된 프레그먼트의 개수
            //TODO("Not yet implemented")
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment { // 전달받은(position 번째에 해당하는는) 프레그먼트 리턴
           //TODO("Not yet implemented")
            return fragments[position]
        }
    }

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)} //초기화를 늦추어 다른 function에서도 사용할 수 있도록
    lateinit var toggle : ActionBarDrawerToggle // 토글 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 툴바 적용하기
        setSupportActionBar(binding.toolbar) // **툴바를 액션 바 형태로 적용
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_open, R.string.drawer_close) // 액티비티, xml, string
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 액션바 추가
        toggle.syncState() // 토글 동기화
        
        //**Fragment 적용하기
        //val fragmentManager : FragmentManager = supportFragmentManager
        //val transaction : FragmentTransaction = fragmentManager.beginTransaction() // 프레그먼트 트랜잭션 추가
        
        //프레그먼트 추가

        //var fragment = Fragment1()
        //transaction.add(R.id.fragment_content, fragment) // activtity_main에 해당하는 레이아웃(LinearLayout의 fragment에 추가한다.)
        //transaction.commit() // 트랜젝션 실행(프레그먼트 실행)

        // 뷰 페이저 추가
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL //가로 방향으로 프레그먼트 넘기기
        binding.viewpager.adapter = MyFragmentAdapter(this)

        // 탭 레이아웃: TabLayoutMediator) 뷰페이저와 탭바 연동하기
        TabLayoutMediator(binding.tab1, binding.viewpager){
            tab, position -> tab.text = "TAB ${position+1}"
        }.attach()

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
                //binding.tv1.text = p0
                    return true
            }
        }


        )
        return super.onCreateOptionsMenu(menu) // menu를 전달하여 적용하도록
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // 메뉴의 아이템이 선택되었을 때 이벤트 처리
        // 토글에 대한 확인: 아이템 선택에 대한 확인
        if (toggle.onOptionsItemSelected(item)) return true

        when(item.itemId){
            R.id.menu1 -> {
                //binding.tv1.setTextColor(Color.BLUE)
                true //리턴값에 대한 값을 가져야 한다. -> true로 설정
            }
            R.id.menu2 -> {
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}