package com.example.petfriends.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var name: String,
    var email: String,
    var password: String
): Parcelable
