package com.example.petfriends.ui.add_data.add_pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.petfriends.R
import com.example.petfriends.databinding.FragmentFirstAddPetBinding


class FirstAddPetFragment : Fragment() {

    private var _binding: FragmentFirstAddPetBinding? = null
    private val binding get() = _binding!!

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
        binding.btnNextAddPet.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_firstAddPetFragment_to_secondAddPetFragment)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}