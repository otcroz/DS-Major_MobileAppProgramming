package com.example.application11

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerView.layoutManager = layoutManager // 기본: vertical로 설정되어 있음
        binding.recyclerView.adapter = MyAdapter(datas)

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