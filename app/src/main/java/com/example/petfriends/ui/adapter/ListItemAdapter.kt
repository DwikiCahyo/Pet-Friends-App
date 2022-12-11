package com.example.petfriends.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petfriends.data.local.model.ItemList
import com.example.petfriends.databinding.ItemFoodRowActivityBinding

class ListItemAdapter(private val itemList: ArrayList<ItemList>) : RecyclerView.Adapter<ListItemAdapter.MyViewHolder> () {
    class MyViewHolder(private var binding: ItemFoodRowActivityBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemList: ItemList) {
            binding.apply {
                tvFoodName.text = itemList.name
                tvFoodWeight.text = itemList.weight
                tvFoodDate.text = itemList.createdAt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFoodRowActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}