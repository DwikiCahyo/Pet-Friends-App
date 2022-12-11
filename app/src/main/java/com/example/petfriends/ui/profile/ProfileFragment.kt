package com.example.petfriends.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.petfriends.R
import com.example.petfriends.databinding.FragmentHomeBinding
import com.example.petfriends.databinding.FragmentProfileBinding
import com.example.petfriends.ui.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = Firebase.auth
        val user = Firebase.auth.currentUser

        user?.let {

            binding.tvNameProfile.text = user.displayName
            binding.tvEmailProfile.text = user.email
        }
        binding.btnLogout.setOnClickListener {
            Toast.makeText(context, getString(R.string.success_logout), Toast.LENGTH_SHORT).show()
            mAuth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        binding.tvNameProfile.text = currentUser?.displayName
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser == null){
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}


