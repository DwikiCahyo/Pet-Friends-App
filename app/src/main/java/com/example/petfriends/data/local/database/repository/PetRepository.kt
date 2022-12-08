package com.example.petfriends.data.local.database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.petfriends.data.local.database.PetRoomDatabase
import com.example.petfriends.data.local.database.dao.PetDao
import com.example.petfriends.data.local.database.dao.PetFoodDao
import com.example.petfriends.data.local.database.entity.Pet
import com.example.petfriends.data.local.database.entity.PetFood
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PetRepository(
    private val mPetDao: PetDao,
    private val mPetFoodDao: PetFoodDao) {

    companion object {
        @Volatile
        private var instance: PetRepository? = null

        fun getInstance(context: Context): PetRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = PetRoomDatabase.getDatabase(context)
                    instance = PetRepository(
                        database.petDao(),
                        database.petFoodDao())
                }
                return instance as PetRepository
            }

        }
    }

//   Pet
    fun getPet(): LiveData<List<Pet>> = mPetDao.getPet()

    fun insertPet(pet: Pet) {
        return mPetDao.insert(pet)
    }

    fun deletePet(pet: Pet) {
        return mPetDao.delete(pet)
    }

    fun updatePet(pet: Pet) {
        mPetDao.update(pet)
    }

//    PetFoods
    fun getPetFoods(): LiveData<List<PetFood>> = mPetFoodDao.getFoods()

    fun insertPetFoods(petFood: PetFood) {
        mPetFoodDao.insert(petFood)
    }

    fun deletePetFoods(petFood: PetFood) {
        mPetFoodDao.delete(petFood)
    }

    fun updatePetFoods(petFood: PetFood) {
        mPetFoodDao.update(petFood)
    }



}