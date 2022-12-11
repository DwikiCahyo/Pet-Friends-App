package com.example.petfriends.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//data class PetModel(
//    var typePet:String? = null
//)

@Parcelize
data class PetModel(
    val uId: String,
    val petPhotoUrl: String,
    val petName: String,
//    val petAge: Int,
    val petJenis: String,
    val petGender: String,
    val petBirthday: String,
    val createdAt: String,
    val petCreated: Boolean = true
) : Parcelable

@Parcelize
data class PetFood(
//    var petFoodId: String,
    val uId: String,
//    val petId: String,
    var urlPhoto: String,
    var name: String,
    var weight: String,
    val hours: String,
    val day: String,
    val date: String,
    var createdAt: String
) : Parcelable


data class ItemList(
    val createdAt: String? = null,
    val name: String? = null,
    val uId: String? = null,
    val urlPhoto: String? = null,
    val weight: String? = null
)