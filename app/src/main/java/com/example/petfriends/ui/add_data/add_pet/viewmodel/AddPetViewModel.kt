package com.example.petfriends.ui.add_data.add_pet.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.petfriends.data.local.database.entity.Pet
import com.example.petfriends.data.local.database.repository.PetRepository

class AddPetViewModel(application: Application) : ViewModel() {
    private val mPetRepository: PetRepository = PetRepository(application)

    fun insert(pet: Pet){
        mPetRepository.insert(pet)
    }

    fun update(pet: Pet){
        mPetRepository.update(pet)
    }

    fun delete(pet: Pet){
        mPetRepository.delete(pet)
    }

}