package com.example.petfriends.data.local.preference

import android.content.Context
import com.example.petfriends.data.local.model.PetModel
//
//internal class PetPreferences(context: Context) {
//    companion object {
//        private const val PREF_NAME = "user_pref"
//        private const val TYPE = "type"
//    }
//
//    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//
//    fun setPet(value: PetModel){
//        val editor = preferences.edit()
//        editor.putString(TYPE,value.typePet)
//        editor.apply()
//    }
//
//    fun getPet():PetModel{
//        val model = PetModel()
//        model.typePet = preferences.getString(TYPE,"")
//        return model
//    }
//}