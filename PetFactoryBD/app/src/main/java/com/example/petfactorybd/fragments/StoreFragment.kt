package com.example.petfactorybd.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.petfactorybd.R
import com.example.petfactorybd.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //SHOW GIF
        Glide.with(this)
            .asGif()
            .load("https://cdn.pixabay.com/animation/2023/03/29/10/53/10-53-26-16_512.gif") // Replace with your GIF URL or local resource
            .into(binding.gifImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}