package com.example.petfriends.ui.add_data.add_pet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.petfriends.R
import com.example.petfriends.data.local.model.PetModel
import com.example.petfriends.databinding.FragmentSecondAddPetBinding
import com.example.petfriends.ui.MainActivity
import com.example.petfriends.utils.DateHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SecondAddPetFragment : Fragment() {

    private var _binding: FragmentSecondAddPetBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var jenisPet: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondAddPetBinding.inflate(inflater, container, false)
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = Firebase.auth

//        jenisPet = arguments?.getString(FirstAddPetFragment.EXTRA_PET).toString()
        jenisPet = SecondAddPetFragmentArgs.fromBundle(arguments as Bundle).jenis



        binding.tvJenisPet.text = jenisPet

        setAddAction()
    }

    private fun setAddAction() {
        binding.apply {
            binding.btnAddPet.setOnClickListener {
                val petId = "01"
                val petName = edNamePet.text.toString()
                val petPhoto = edPhotoPet.text.toString()
                val date = edDatePet.text.toString()
                val gender = edGenderPet.text.toString()
                val uId = mAuth.currentUser?.uid.toString()
                val createdAt = DateHelper.getCurrentDate()

                when {
                    petName.isEmpty() -> {
                        edNamePet.error = "Error"
                    }
                    petPhoto.isEmpty() -> {
                        edPhotoPet.error = "Error"
                    }
                    date.isEmpty() -> {
                        edDatePet.error = "Error"
                    }
                    gender.isEmpty() -> {
                        edGenderPet.error = "Error"
                    }
                    else -> {
                        val petModel = PetModel(
                            uId,
                            petPhoto,
                            petName,
                            jenisPet,
                            gender,
                            date,
                            createdAt
                        )

                        database = FirebaseDatabase.getInstance().getReference("Pets")
                        database.child(uId).setValue(petModel).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.w(TAG, "success")
                                Toast.makeText(context, getString(R.string.success), Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else {
                                Log.w(TAG, "failure", task.exception)
                                Toast.makeText(context, getString(R.string.failed), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "SecondAddPetFragment"
    }

}