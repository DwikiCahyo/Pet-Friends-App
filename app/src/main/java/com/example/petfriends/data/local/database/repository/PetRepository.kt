package com.example.petfriends.data.local.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.petfriends.data.local.database.PetRoomDatabase
import com.example.petfriends.data.local.database.dao.PetDao
import com.example.petfriends.data.local.database.entity.Pet
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PetRepository(application: Application) {
    private val mPetDao: PetDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = PetRoomDatabase.getDatabase(application)
        mPetDao = db.petDao()
    }

    fun getPet(): LiveData<List<Pet>> = mPetDao.getPet()

    fun insert(pet: Pet){
        executorService.execute{mPetDao.insert(pet)}
    }

    fun delete(pet: Pet){
        executorService.execute{mPetDao.delete(pet)}
    }

    fun update(pet: Pet){
        executorService.execute{mPetDao.update(pet)}
    }


}