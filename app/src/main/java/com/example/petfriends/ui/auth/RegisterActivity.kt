package com.example.petfriends.ui.auth

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.petfriends.R
import com.example.petfriends.data.local.model.UserModel
import com.example.petfriends.databinding.ActivityRegisterBinding
import com.example.petfriends.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = Firebase.auth

        setupRegister()
        binding.tvLoginRegister.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun setupRegister() {
        binding.apply {
            btnRegister.setOnClickListener {
                val name = edNameRegister.text.toString()
                val email = edEmailRegister.text.toString()
                val password = edPasswordRegister.text.toString()

                when{
                    name.isEmpty() -> {
                        edNameRegister.error = getString(R.string.enter_name)
                        edNameRegister.requestFocus()
                    }
                    email.isEmpty() -> {
                        edEmailRegister.error = getString(R.string.enter_email)
                        edEmailRegister.requestFocus()
                    }
                    password.isEmpty() -> {
                        edPasswordRegister.error = getString(R.string.enter_password)
                        edPasswordRegister.requestFocus()
                    }
                    password.length < 6 -> {
                        edPasswordRegister.error = getString(R.string.length_password)
                        edPasswordRegister.requestFocus()
                    }
                    !isEmailValid(email) -> {
                        edEmailRegister.error = getString(R.string.email_invalid)
                        edEmailRegister.requestFocus()
                    }
                    else -> {

                        val user = UserModel(
                            name,
                            email,
                            password
                        )

                        database = FirebaseDatabase.getInstance().getReference("Users")
                        database.child(name).setValue(user)
                            .addOnCompleteListener(this@RegisterActivity){
                                showLoading(true)
                                if (it.isSuccessful) {
                                    showLoading(false)
                                    actionAuth(user)
                                }else {
                                    showLoading(false)
                                    Log.w(ContentValues.TAG, "failure", it.exception)
                                    Toast.makeText(baseContext, getString(R.string.failed_register),
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }
        }
    }

    private fun actionAuth(user: UserModel) {
        mAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener(this@RegisterActivity){
            val mUser = Firebase.auth.currentUser
            if(it.isSuccessful) {
                val profileUpdates = userProfileChangeRequest{
                    displayName = user.name
                }
                mUser!!.updateProfile(profileUpdates).addOnCompleteListener(this@RegisterActivity){
                    if (it.isSuccessful){
                        Log.d(ContentValues.TAG, "User profile updated.")
                    }
                }
                Log.d(ContentValues.TAG, getString(R.string.success_register))
                val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                startActivity(intent)
            }else {
                Log.d(ContentValues.TAG, getString(R.string.failed_register), it.exception)
                Toast.makeText(baseContext, getString(R.string.failed_register),
                    Toast.LENGTH_SHORT).show()
                binding.edEmailRegister.error = getString(R.string.email_is_used)
                binding.edEmailRegister.setText("")
            }
        }
    }

    private fun clear() {
        binding.apply {
            edNameRegister.setText("")
            edEmailRegister.setText("")
            edPasswordRegister.setText("")
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun showLoading(isLoading: Boolean){
        binding.pbRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}