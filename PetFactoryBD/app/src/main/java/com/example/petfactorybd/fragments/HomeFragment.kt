package com.example.petfactorybd.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.petfactorybd.AppViewModel
import com.example.petfactorybd.R
import com.example.petfactorybd.database.AppDatabase
import com.example.petfactorybd.database.UserRepository
import com.example.petfactorybd.databinding.FragmentHomeBinding
import com.example.petfactorybd.viewmodel.UserViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val database by lazy {
        Room.databaseBuilder(requireActivity(), AppDatabase::class.java, "user_db").build()
    }
    private val userDao by lazy { database.userDao() }
    private val repository by lazy { UserRepository(userDao) }
    private val viewModel by lazy { UserViewModel(repository) }

    private val model: AppViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfig.setOnClickListener { view ->
            view.findNavController().navigate(R.id.configurationFragment)
        }

        Toast.makeText(requireContext(), model.data.value.toString(), Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}