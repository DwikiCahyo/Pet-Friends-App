package com.example.petfriends.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

//class CalendarAdapter(private val days: ArrayList<LocalDate>,
//                      private val context: Context,
//                      private val onClickListener: (View) -> Unit) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
////
//    class ViewHolder() {
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        final LocalDate date = days.get(position);
//        if(date == null)
//            holder.dayOfMonth.setText("");
//        else
//        {
//            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
//            if(date.equals(CalendarUtils.selectedDate))
//                holder.parentView.setBackgroundColor(Color.LTGRAY);
//        }
//    }
//
//    override fun getItemCount(): Int = days.size
//}