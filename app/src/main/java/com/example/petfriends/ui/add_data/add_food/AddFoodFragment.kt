package com.example.petfriends.ui.add_data.add_food

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.petfriends.R
import com.example.petfriends.data.local.model.PetFood
import com.example.petfriends.databinding.FragmentAddFoodBinding
import com.example.petfriends.utils.DateHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AddFoodFragment : Fragment() {
    private var _binding: FragmentAddFoodBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth : FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFoodBinding.inflate(inflater, container, false)
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = Firebase.auth

        addAction()
    }

    private fun addAction() {
        binding.apply {
            tvHours.text = DateHelper.getCurrentDate()
            btnFoodNext.setOnClickListener {
                val petFoodId = "asdasdas1"
                val urlPhoto = "asdasdasd"
                val foodName = edFoodName.text.toString()
                val foodWeight = edFoodWeight.text.toString()
                val uId = mAuth.currentUser?.uid.toString()
                val createdAt = DateHelper.getCurrentDate()

                when {
                    foodName.isEmpty() -> {
                        edFoodName.error = getString(R.string.error_food_name)
                    }
                    foodWeight.isEmpty() -> {
                        edFoodWeight.error = getString(R.string.error_weight)
                    }
                    else -> {
                        val petFood = PetFood(
                            urlPhoto,
                            foodName,
                            foodWeight,
                            createdAt

                        )
                        database = FirebaseDatabase.getInstance().getReference("PetsFoods")
                        database.child(uId).push().setValue(petFood).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.w(TAG, "success")
                                Toast.makeText(context, getString(R.string.success), Toast.LENGTH_SHORT).show()
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "AddFoodFragment"
    }

}