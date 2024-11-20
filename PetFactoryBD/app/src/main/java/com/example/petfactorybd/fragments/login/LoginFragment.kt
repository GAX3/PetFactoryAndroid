package com.example.petfactorybd.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.petfactorybd.AppViewModel
import com.example.petfactorybd.R
import com.example.petfactorybd.database.AppDatabase
import com.example.petfactorybd.database.UserRepository
import com.example.petfactorybd.databinding.FragmentLoginBinding
import com.example.petfactorybd.viewmodel.UserViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            view.findNavController().navigate(R.id.createUserFragment)
        }

        binding.loginButton.setOnClickListener {
            val username = binding.username.text.toString().lowercase()
            val password = binding.password.text.toString().lowercase()

            // Attempt login
            viewModel.loginUser(username, password) { user ->
                if (user != null) {
                    model.data.value = user.id
                    view.findNavController().navigate(R.id.homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Invalid credentials!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.forgotPassword.setOnClickListener {
            viewModel.fetchAllUsers { users ->
                if (users.isNotEmpty()) {
                    users.forEach { user ->
                        println(user.toString())
                    }
                } else {
                    println("No users found.")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}