package com.example.petfriends.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.petfriends.data.local.database.entity.PetAndMedicine
import com.example.petfriends.data.local.database.entity.PetMedicine

@Dao
interface PetMedicineDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(petMedicine: PetMedicine)

    @Update
    fun update(petMedicine: PetMedicine)

    @Delete
    fun delete(petMedicine: PetMedicine)

    @Query("SELECT * FROM petmedicine")
    fun getMedicine(): LiveData<List<PetMedicine>>

    @Transaction
    @Query("SELECT * FROM pet")
    fun getPetAndMedicine(): LiveData<List<PetAndMedicine>>
}