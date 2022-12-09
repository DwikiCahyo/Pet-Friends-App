package com.example.petfriends.ui.add_data.add_pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.petfriends.R
import com.example.petfriends.databinding.FragmentFirstAddPetBinding
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


        binding.btnSelectDog.setOnClickListener {
            binding.cvCat.visibility = View.GONE
            binding.btnAddNext.visibility = View.VISIBLE
            binding.btnSelectDog.visibility = View.GONE
            selectDog()

        }

        binding.btnSelectCat.setOnClickListener {
            binding.cvDog.visibility = View.GONE
            binding.btnAddNext.visibility = View.VISIBLE
            binding.btnSelectCat.visibility = View.GONE
            selectCat()
        }
    }

    private fun selectCat(){
        binding.btnAddNext.setOnClickListener {
            val mBundleCat = Bundle()
            mBundleCat.putString(TYPE_NAME, "Cat")
            it.findNavController()
                .navigate(R.id.action_firstAddPetFragment_to_secondAddPetFragment, mBundleCat)
        }
    }

    private fun selectDog() {
        binding.btnAddNext.setOnClickListener {
                val mBundleDog = Bundle()
                mBundleDog.putString(TYPE_NAME, "Dog")
                it.findNavController()
                    .navigate(R.id.action_firstAddPetFragment_to_secondAddPetFragment, mBundleDog)
            }
        }


    companion object{
       const val TYPE_NAME = "type_name"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}