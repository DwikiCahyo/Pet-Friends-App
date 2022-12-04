package com.example.petfriends.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.petfriends.data.local.database.entity.Pet
import com.example.petfriends.data.local.database.entity.UserAndPet

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(pet: Pet)

    @Update
    fun update(pet: Pet)

    @Delete
    fun delete(pet: Pet)

    @Query("SELECT * FROM Pet")
    fun getPet(): LiveData<List<Pet>>

    @Transaction
    @Query("SELECT * FROM UserEntity")
    fun getUserAndPet(): LiveData<List<UserAndPet>>
}