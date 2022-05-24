package com.example.pratice_mobile_app

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.pratice_mobile_app.databinding.ActivityMainBinding
import com.example.pratice_mobile_app.databinding.DialogItemBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle // 토글 선언

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        // 툴바를 액션바 형태로 적용하기
        setSupportActionBar(binding.toolbarHaha)

        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        // 내 마음대로 토스트 적용하기
        var toast : Toast
        binding.listButton.setOnClickListener {
            toast = Toast.makeText(this,"클릭하면 뜨는 토스트~", Toast.LENGTH_SHORT)
            toast.setText("헤이~ ~!")
            toast.setGravity(Gravity.TOP, 20,100) // 이건 대체 무슨 설정이랴?
            toast.show()
            toast.addCallback(
                @RequiresApi(Build.VERSION_CODES.R)
                object: Toast.Callback() {
                    override fun onToastHidden() {
                        Log.d("mobileApp","토스트가 사라집니다.")
                        super.onToastHidden()
                    }

                    override fun onToastShown() {
                        Log.d("mobileApp","토스트가 나타납니다.")
                        super.onToastShown()
                    }
                }
            )
        }


        // 롱클릭 리스너에는 Boolean 값을 반환해줘야 한다.
        binding.listButton.setOnLongClickListener {
            toast = Toast.makeText(this, "길게 클릭하셨나요?", Toast.LENGTH_LONG)
            toast.show()
            true
        }


        // 내 마음대로 달력이랑 시간 적용하기
        binding.calButton!!.setOnClickListener {
            DatePickerDialog(this,
                object:DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        Log.d("mobileApp", "$p1 년, ${p2 + 1} 월, $p3 일")
                    }
                },2022,3,30).show()
        }

        binding.timeButton!!.setOnClickListener {
            TimePickerDialog(this,
                object: TimePickerDialog.OnTimeSetListener{
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                        Log.d("mobileApp", "$p1 시 $p2 분")
                    }
                }
                ,13,0,true).show()
        }
        val diglogBinding = DialogItemBinding.inflate(layoutInflater)
        // 다이얼로그를 위한 이벤트 핸들러 만들기: onClick
        val dialogEvent = object:DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE){
                    diglogBinding.inputView.text = "확인을 누르셨네~"
                    Log.d("mobileApp", "${diglogBinding.inputView.text}")

                } else if(p1 == DialogInterface.BUTTON_NEGATIVE){
                    diglogBinding.inputView.text = "닫기를 누르셨네~"
                    Log.d("mobileApp", "${diglogBinding.inputView.text}")

                }
            }
        }

        // 내 마음대로 다이얼로그 적용하기 : 하지만 이건 xml로 만드는건 아닌

        val items = arrayOf<String>("사과", "딸기", "수박" ,"토마토")
        binding.myDialog2!!.setOnClickListener {
            AlertDialog.Builder(this).run{
                //체크박스 적용하기
                setMultiChoiceItems(items, booleanArrayOf(false, true, false, false),
                object : DialogInterface.OnMultiChoiceClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                        Log.d("mobileApp", "${items[p1]} ${if(p2) "선택" else "해제"}")
                    }
                })
                setPositiveButton("확인", dialogEvent)
                setNegativeButton("취소", dialogEvent)
                show()
            }.setCanceledOnTouchOutside(false)
        }


        val alert = AlertDialog.Builder(this)
            .setTitle("이것은 라디오버튼 테스트")
            .setSingleChoiceItems(items, 1,
                object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Log.d("mobileApp", "${items[p1]}")
                    }
                })
            .setPositiveButton("안녕",dialogEvent)
            .setNegativeButton("바이", dialogEvent)
            .create()

        // 내 마음대로 xml 만들어서 다이얼로그 적용하기 => 바인딩 후 setView()로 설정
        //val alert = AlertDialog.Builder(this) // 여기서 만들 것이다.
        //    .setTitle("입력창 제목")
        //    .setView(diglogBinding.root)
        //    .setPositiveButton("확인", dialogEvent)
        //    .setNegativeButton("닫기", dialogEvent)
        //    .create()


        binding.myDialog!!.setOnClickListener {
            alert.show()
            alert.setCanceledOnTouchOutside(true) //show 다음에 호출해야하는듯
        }

        /// 프래그먼트 연습하기
        val fragmentManager : FragmentManager = supportFragmentManager
        val transaction : FragmentTransaction = fragmentManager.beginTransaction()

        val fragment = Fragment1()
        //transaction.add(R.id.fragment_start, fragment) // activity_main > fragmentView 자리에 fragment 추가
        //transaction.commit()

        // 프래그먼트 리스트로 만들기
        class MyFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
            // 프레그먼트들의 대한 배열
            val fragments: List<Fragment>
            init {
                fragments = listOf(Fragment1(), Fragment2(), Fragment3()) // 3개의 프레그먼트 등록
            }

            override fun getItemCount(): Int { // 등록된 프레그먼트의 개수
                //TODO("Not yet implemented")
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment { // 전달받은(position 번째에 해당하는) 프레그먼트 리턴
                //TODO("Not yet implemented")
                return fragments[position]
            }
        }

        // 플로팅 버튼 이벤트 적용하기
        binding.fab!!.setOnClickListener{
            when(binding.fab!!.isExtended) { // 플로팅 버튼의 확대 여부
                true -> binding.fab!!.shrink() // 버튼의 크기를 줄임
                false -> binding.fab!!.extend() // 버튼의 크기를 확장
            }
        }

        // 뷰페이저 적용하기
        binding.viewpager!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL //가로 방향으로 프레그먼트 넘기기
        binding.viewpager!!.adapter = MyFragmentAdapter(this)


        // 탭바 적용하기
        binding.tab1?.let {
            TabLayoutMediator(it, binding.viewpager){ tab, position -> tab.text = "TAB ${position+1}"
            }.attach()
        }
        
        // 여기로 돌아옴
        val requestLauncher : ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            Log.d("test_intent", "ActivityResultLauncher")
        }

        // 인텐트 적용하기
        binding.activityGo!!.setOnClickListener {
            val intent = Intent(this, MyActivity::class.java)
            intent.putExtra("ysy1", "안녕하세요!")
            intent.putExtra("ysy2", " 저는 유수연입니다~!")
            //startActivity(intent)
            requestLauncher.launch(intent)
        }

        // 토글 적용하기
        toggle = ActionBarDrawerToggle(this, binding.drawer as DrawerLayout?, R.string.app_name, R.string.content_1) // 액티비티, xml, string
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 액션바 추가
        toggle.syncState() // 토글 동기화

        //드로어 레이아웃에서의 메뉴 이벤트
        binding.mainDrawView!!.setNavigationItemSelectedListener {
            Log.d("mobileApp", "Navigation selected...${it.title}") // it: 셀렉터가 전달받은 파라미터
            true
        }

    }

    // 액션바 생성하기 -> 툴바 사용하기(이걸 생성 후에 onCreate에서 툴바를 적용해야 한다. )
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 하드코딩으로 메뉴 생성
        //val menuItem1 : MenuItem? = menu?.add(0,0,0, "메뉴1")
        //val menuItem2 : MenuItem? = menu?.add(0,1,0, "메뉴2")

        menuInflater.inflate(R.menu.main_menu, menu)

        //2. 액션 뷰: 검색 메뉴에 대한 코드
        val menuSearch = menu?.findItem(R.id.menu_search) // 메뉴 바인딩
        val searchView = menuSearch?.actionView as SearchView // 액션 뷰 가져오기

        searchView.setOnQueryTextListener (object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어 변경 이벤트
                Log.d("test_app", "검색어 변경~!")

                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 키보드 검색 버튼을 클릭한 순간의 이벤트
                Log.d("test_app", "버튼 클릭")
                return true
            }
        } )

        return super.onCreateOptionsMenu(menu)
    }
    
    // 메뉴가 선택되었을 때 처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true

        val binding = ActivityMainBinding.inflate(layoutInflater)
        
        // 메뉴 아이템 처리하기
        when(item.itemId){
            R.id.menu1 -> {
                Log.d("text_color", "테스트BLUE")
                binding.content.setTextColor(Color.BLUE) // 색깔 설정이 적용되지 않는 오류
                true //리턴값에 대한 값을 가져야 한다. -> true로 설정
            }
            R.id.menu2 -> {
                Log.d("text_color", "테스트MAGENTA")
                binding.content.setTextColor(Color.MAGENTA)
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}