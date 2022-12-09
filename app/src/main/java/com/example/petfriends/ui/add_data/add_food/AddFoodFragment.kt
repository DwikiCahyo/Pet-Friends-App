package com.example.petfriends.ui.add_data.add_food

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petfriends.R
import com.example.petfriends.data.local.model.PetFood
import com.example.petfriends.databinding.FragmentAddFoodBinding
import com.example.petfriends.ui.MainActivity
import com.example.petfriends.utils.DateHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
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

        showLoading(true)

        mAuth = Firebase.auth

        addFood()
    }

    private fun addFood() {
        showLoading(false)
        binding.apply {
            ivChangeHours.setOnClickListener { changeHours() }
            tvHours.text = DateHelper.getCurrentDate()
            btnFoodNext.setOnClickListener {
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
                            uId,
                            urlPhoto,
                            foodName,
                            foodWeight,
                            createdAt
                        )
                        database = FirebaseDatabase.getInstance().getReference("PetsFoods")
                        database.child(uId).push().setValue(petFood).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showLoading(false)
                                Log.w(TAG, "success")
                                AlertDialog.Builder(context).apply{
                                    setTitle(getString(R.string.success))
                                    setMessage(getString(R.string.success_add_food))
                                    setPositiveButton(getString(R.string.cont)){_, _ ->
                                        startActivity(Intent(context, MainActivity::class.java))
                                    }
                                    create()
                                    show()
                                }
                            }
                            else {
                                showLoading(false)
                                Log.w(TAG, "failure", task.exception)
                                AlertDialog.Builder(context).apply{
                                    setTitle(getString(R.string.failed))
                                    setMessage(getString(R.string.success_add_food))
                                    setPositiveButton(getString(R.string.cont)){_, _ ->
                                        show().dismiss()
                                    }
                                    create()
                                    show()
                                }
//                                Toast.makeText(context, getString(R.string.failed), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun changeHours() {

    }

    private fun showLoading(isLoading: Boolean){
        binding.pbAddpetfood.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "AddFoodFragment"
    }

}