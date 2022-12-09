package com.example.petfriends.ui.add_data.add_food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.petfriends.R
import com.example.petfriends.data.local.database.entity.PetFood
import com.example.petfriends.databinding.ActivityAddFoodBinding
import com.example.petfriends.ui.add_data.add_food.viewmodel.AddPetFoodViewModel
import com.example.petfriends.ui.auth.RegisterActivity
import com.example.petfriends.ui.viewmodelfactory.ViewModelFactory
import com.example.petfriends.utils.DateHelper
import com.example.petfriends.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AddFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFoodBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var viewModel: AddPetFoodViewModel
    private var petFood: PetFood? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth

        initViewModel()
        addAction()
    }

    private fun addAction() {
        binding.apply {
            btnAddFood.setOnClickListener {
                val foodname = edNameFood.text.toString()
                val foodkind = edKindFood.text.toString()
                val foodweight = edWeightFood.text.toString()
                val fooddate = edDateFood.text.toString()

                val uId = mAuth.currentUser?.uid.toString()
                val petId = "01"

                when {
                    foodname.isEmpty() -> {
                        edNameFood.error = getString(R.string.enter_name)
                    }
                    foodkind.isEmpty() -> {

                    }
                    foodweight.isEmpty() -> {

                    }
                    fooddate.isEmpty() -> {

                    }
                    else -> {
                        petFood.let { petFood ->
                            petFood?.petId = petId
                            petFood?.uId = uId
                            petFood?.petFoodName = foodname
                            petFood?.petFoodKind = foodkind
                            petFood?.petFoodWeight = foodweight.toLong()
                            petFood?.petFoodDate = fooddate
                            petFood?.createdAt = DateHelper.getCurrentDate()

                        }
                        database = FirebaseDatabase.getInstance().getReference("Pets")
                        database.child(uId).setValue(petFood).addOnCompleteListener(this@AddFoodActivity){ task ->
                            if (task.isSuccessful) {
                                Log.w(TAG, "success")
                                Toast.makeText(this@AddFoodActivity, getString(R.string.success), Toast.LENGTH_SHORT).show()
//                                viewModel.insertPetFoods(petFood as PetFood)
                            }else {
                                Log.w(TAG, "failure", task.exception)
                                Toast.makeText(this@AddFoodActivity, getString(R.string.failed), Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this))
            .get(AddPetFoodViewModel::class.java)
    }

    companion object{
        private const val TAG = "AddFoodActivity"
    }
}