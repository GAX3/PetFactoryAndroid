package com.example.petfactorybd.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.room.Room
import com.example.petfactorybd.R
import com.example.petfactorybd.database.AppDatabase
import com.example.petfactorybd.database.UserRepository
import com.example.petfactorybd.databinding.FragmentCreateUserBinding
import com.example.petfactorybd.databinding.FragmentLoginBinding
import com.example.petfactorybd.viewmodel.UserViewModel

class CreateUserFragment : Fragment() {

    private var _binding: FragmentCreateUserBinding? = null
    private val binding get() = _binding!!

    private val database by lazy {
        Room.databaseBuilder(requireActivity(), AppDatabase::class.java, "user_db").build()
    }
    private val userDao by lazy { database.userDao() }
    private val repository by lazy { UserRepository(userDao) }
    private val viewModel by lazy { UserViewModel(repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.btnCreateUser.setOnClickListener {
            val user = binding.username.text.toString().lowercase()
            val password = binding.username.text.toString().lowercase()

            if (user.toString() == "" || password.toString() == ""){
                Toast.makeText(requireContext(), "Campos incompletos", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.registerUser(user.toString(), password.toString()) { success ->
                    if (success) {
                        Toast.makeText(requireContext(), "User registered successfully!", Toast.LENGTH_SHORT).show()
                        binding.username.text.clear()
                        binding.password.text.clear()
                    } else {
                        Toast.makeText(requireContext(), "User already exists!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}