package com.example.petfriends.data.local.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.petfriends.data.local.database.entity.PetAndFoods
import com.example.petfriends.data.local.database.entity.PetFood

@Dao
interface PetFoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(petFood: PetFood)

    @Update
    fun update(petFood: PetFood)

    @Delete
    fun delete(petFood: PetFood)

    @Query("SELECT * FROM PetFood")
    fun getFoods(): LiveData<List<PetFood>>

    @Transaction
    @Query("SELECT * FROM Pet")
    fun getPetAndFoods(): LiveData<List<PetAndFoods>>

}