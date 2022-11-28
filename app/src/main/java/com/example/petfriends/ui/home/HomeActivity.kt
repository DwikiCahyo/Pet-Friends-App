package com.example.petfriends.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.petfriends.R
import com.example.petfriends.databinding.ActivityHomeBinding
import com.example.petfriends.ui.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = Firebase.auth

        binding.btnLogout.setOnClickListener {
            Toast.makeText(this@HomeActivity, getString(R.string.success_logout), Toast.LENGTH_SHORT).show()
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            mAuth.signOut()
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if(currentUser == null){
            Toast.makeText(this@HomeActivity, "Need login first!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }else{
            binding.tvName.text = currentUser.displayName.toString()
        }
    }
}