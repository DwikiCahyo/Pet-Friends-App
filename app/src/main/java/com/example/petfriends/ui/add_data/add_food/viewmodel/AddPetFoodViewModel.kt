package com.example.petfriends.ui.add_data.add_food.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.petfriends.data.local.database.entity.PetFood
import com.example.petfriends.data.local.database.repository.PetRepository

class AddPetFoodViewModel(private val petRepository: PetRepository) : ViewModel() {

    fun getPetFoods(): LiveData<List<PetFood>> = petRepository.getPetFoods()

    fun insertPetFoods(petFood: PetFood) {
        petRepository.insertPetFoods(petFood)
    }

    fun updatePetFoods(petFood: PetFood) {
        petRepository.updatePetFoods(petFood)
    }

    fun deletePetFoods(petFood: PetFood) {
        petRepository.deletePetFoods(petFood)
    }
}