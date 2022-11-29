package com.example.petfriends.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var uId: String,
    var name: String,
    var email: String,
    var password: String
): Parcelable
