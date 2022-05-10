package com.example.ch18_network

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ch18_network.databinding.ItemMainBinding

class XmlViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root) // 뷰 홀더에 대한 생성
class XmlAdapter(val context: Context, val datas:MutableList<myItem>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]
        binding.name.text = model.yadmNm
        binding.tel.text = model.telno
        binding.addr.text = model.sidoNm + " " + model.sgguNm
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }
}