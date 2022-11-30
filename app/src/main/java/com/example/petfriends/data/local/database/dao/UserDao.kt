package com.example.petfriends.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.petfriends.data.local.database.entity.UserEntity


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("SELECT * FROM userentity ORDER BY uId ASC")
    fun getUser(): LiveData<List<(UserEntity)>>
}