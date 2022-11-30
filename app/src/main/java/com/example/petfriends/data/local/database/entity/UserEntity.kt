package com.example.petfriends.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity](
    @PrimaryKey
    val uId: String,
    val photoUrl: String? = null,
    val name: String,
    )





