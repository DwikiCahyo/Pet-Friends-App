package com.example.petfriends.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petfriends.data.local.model.ItemList
import com.example.petfriends.databinding.ItemRowActivityBinding

class ListItemAdapter(private val itemList: ArrayList<ItemList>) : RecyclerView.Adapter<ListItemAdapter.MyViewHolder> () {
    class MyViewHolder(private var binding: ItemRowActivityBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemList: ItemList) {
            binding.apply {
                tvCategoriesName.text = itemList.name
                tvCategoriesInformation.text = itemList.weight
                tvHourPet.text = itemList.createdAt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}