package com.example.pratice_mobile_app

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pratice_mobile_app.databinding.Fragment3Binding
import com.example.pratice_mobile_app.databinding.RecyclerItemBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */

class MyViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)
class MyAdapter(val datas:MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int { // 아이템 개수 리턴
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder { // 뷰 홀더 준비
        // layoutInflator는 Activity에서만 적용 => Fragment에서는 함수를 통해 만들어준다: LayoutInflater.from(parent.context)
        return MyViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) { // 뷰 홀더 뷰에 데이터 출력: 바인딩
        val binding = (holder as MyViewHolder).binding
        binding.itemTv.text = datas[position]
    }
}

class MyDecoration(val context: Context) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
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

class Fragment3 : Fragment() {
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
        // Inflate the layout for this fragment
        val datas = mutableListOf<String>()
        for(i in 1..32){
            datas.add("item $i")
        }

        //리사이클러 뷰 적용하기
        val binding = Fragment3Binding.inflate(inflater, container, false)
        //val layoutManager = LinearLayoutManager(activity)
        //layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val layoutManager = GridLayoutManager(activity, 3) //액티비티, 칼럼의 개수
        binding.recyclerView.layoutManager = layoutManager // 기본: vertical로 설정되어 있음
        binding.recyclerView.adapter = MyAdapter(datas)
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context)) // 데코레이션

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}