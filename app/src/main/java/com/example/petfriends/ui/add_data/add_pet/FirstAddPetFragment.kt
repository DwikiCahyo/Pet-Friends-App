package com.example.petfriends.ui.add_data.add_pet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.petfriends.R
import com.example.petfriends.data.local.model.PetModel
import com.example.petfriends.data.local.preference.PetPreferences
import com.example.petfriends.databinding.FragmentFirstAddPetBinding
import com.example.petfriends.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class FirstAddPetFragment : Fragment(){

    private var _binding: FragmentFirstAddPetBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth:FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstAddPetBinding.inflate(inflater, container, false)
        val view: View = binding.root
        return view




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        //get user name
        auth = Firebase.auth
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            binding.edtWelcome.text = getString(R.string.name_string,name)
        }

        binding.btnSelectCat.setOnClickListener{
            val petCat = "Cat"
            selectPet(petCat)
            Navigation.createNavigateOnClickListener(R.id.action_firstAddPetFragment_to_secondAddPetFragment)

        }

        binding.btnSelectDog.setOnClickListener{
                val petDog = "Dog"
                selectPet(petDog)
                Navigation.createNavigateOnClickListener(R.id.action_firstAddPetFragment_to_secondAddPetFragment)
        }



    }

    private fun selectPet(pet:String) {
        val pref = PetPreferences(requireContext())
            val petModel = PetModel()
            petModel.typePet  = pet
            pref.setPet(petModel)
            Toast.makeText(requireContext(),"${petModel.typePet} select",Toast.LENGTH_SHORT).show()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}