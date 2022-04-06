package com.example.application11

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application11.databinding.Fragment1Binding
import com.example.application11.databinding.ItemRecyclerviewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */

class MyViewHolder(val binding : ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) //viewHolder에 대한 클래스
class MyAdapter(val datas : MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() { // datas에 대한 타입 선언(onCreate 참고)
    // override: 3개의 함수 추가
    override fun getItemCount(): Int {
        Log.d("test", "getItemCount")
        //TODO("Not yet implemented")
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("test", "onCreateViewHolder")
        //TODO("Not yet implemented")
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) // layoutInflator는 MainActivity에서만 적용 => 함수를 통해 만들어준다.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) { // 해당되는 목록에 데이터를 넣어줌
        Log.d("test", "onBindViewHolder")
        //TODO("Not yet implemented")
        val binding = (holder as MyViewHolder).binding
        binding.itemTv.text = datas[position]
    }
}

// 데커레이션 클래스
class MyDecoration(val context: Context) : RecyclerView.ItemDecoration(){
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 리사이클러 항목이 그려지기 전 호출되는 함수 (배경이미지 -> 아이템)
        super.onDraw(c, parent, state) // c: 캔버스
        //c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.stadium),0f,0f,null ) // 이미지, 위치
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 리사이클에 대한 모든 것들이 호출된 후에 호출되는 함수
        super.onDrawOver(c, parent, state)
        // 이미지 정 중앙에 배치하기: 스마트폰의 크기 /2 - 이미지 크기 /2
        // 1. 메인 액티비티의 크기 구하기
        val width = parent.width
        val height = parent.height

        // 2. 이미지 액티비티의 크기 구하기
        val dr: Drawable? = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val d_width = dr?.intrinsicWidth
        val d_height = dr?.intrinsicHeight

        val left = width/2 - d_width?.div(2) as Int // 계산 수 Int로
        val top = height/2 - d_height?.div(2) as Int

        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),left.toFloat(),top.toFloat(),null ) // 이미지, 위치

    }

    override fun getItemOffsets( // 아이템을 하나하나 꾸며주는 함수
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(10, 10, 10, 0)
        view.setBackgroundColor(Color.parseColor("#49c1ff")) // 사용자 지정 색상 설정
        ViewCompat.setElevation(view, 20.0f) // 3차원적으로 그림을 그려줌(살짝 올라가있는 효과)
    }
}

class Fragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("test", "onCreateView1")
        // Inflate the layout for this fragment
        val datas = mutableListOf<String>() //9 개의 문자열을 갖는 datas 생성
        for(i in 1..9){
            datas.add("item $i")
        }
        // 바인딩 설정
        Log.d("test", "onCreateView1_binding")
        val binding = Fragment1Binding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(activity)
        //layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        //val layoutManager = GridLayoutManager(activity, 2) //액티비티, 칼럼의 개수
        binding.recyclerView.layoutManager = layoutManager // 기본: vertical로 설정되어 있음
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context)) // 데코레이션 추가:

        return binding.root
       }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}