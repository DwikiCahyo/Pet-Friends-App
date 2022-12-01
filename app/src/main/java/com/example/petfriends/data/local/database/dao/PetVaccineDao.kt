package com.example.petfriends.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.petfriends.data.local.database.entity.PetAndVaccine
import com.example.petfriends.data.local.database.entity.PetVaccine

@Dao
interface PetVaccineDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(petVaccine: PetVaccine)

    @Update
    fun update(petVaccine: PetVaccine)

    @Delete
    fun delete(petVaccine: PetVaccine)

    @Query("SELECT * FROM PetVaccine")
    fun getVaccine(): LiveData<List<PetVaccine>>

    @Transaction
    @Query("SELECT * FROM pet")
    fun getPetAndVaccine(): LiveData<List<PetAndVaccine>>
}