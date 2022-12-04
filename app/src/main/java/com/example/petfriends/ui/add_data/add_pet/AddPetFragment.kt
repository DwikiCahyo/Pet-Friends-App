package com.example.petfriends.ui.add_data.add_pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petfriends.databinding.FragmentAddPetBinding


class AddPetFragment : Fragment() {

    private var _binding: FragmentAddPetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPetBinding.inflate(inflater, container, false)
        val view: View = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}