package com.example.petfriends.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.petfriends.data.local.database.entity.PetAndShower
import com.example.petfriends.data.local.database.entity.PetShower

@Dao
interface PetShowerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(petShower: PetShower)

    @Update
    fun update(petShower: PetShower)

    @Delete
    fun delete(petShower: PetShower)

    @Query("SELECT * FROM PetShower")
    fun getShower(): LiveData<List<PetShower>>

    @Transaction
    @Query("SELECT * FROM Pet")
    fun getPetAndShower(): LiveData<List<PetAndShower>>

}