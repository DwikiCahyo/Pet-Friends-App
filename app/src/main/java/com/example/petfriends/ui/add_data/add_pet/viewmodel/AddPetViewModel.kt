package com.example.petfriends.ui.add_data.add_pet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petfriends.data.local.database.entity.Pet
import com.example.petfriends.data.local.database.repository.PetRepository
import kotlinx.coroutines.launch

class AddPetViewModel(private val petRepository: PetRepository) : ViewModel() {
    fun getPet(): LiveData<List<Pet>> = petRepository.getPet()

    fun insert(pet: Pet) {
        viewModelScope.launch {
            petRepository.insertPet(pet)
        }
    }

    fun update(pet: Pet) {
        viewModelScope.launch {
            petRepository.updatePet(pet)
        }
    }

    fun delete(pet: Pet) {
        viewModelScope.launch {
            petRepository.deletePet(pet)
        }
    }

}