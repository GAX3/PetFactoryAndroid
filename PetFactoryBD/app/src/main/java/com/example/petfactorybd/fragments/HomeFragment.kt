package com.example.petfactorybd.fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
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

        //Toast.makeText(requireContext(), model.data.value.toString(), Toast.LENGTH_SHORT).show()

        viewModel.getData(model.data.value!!){ user ->
            binding.nivel.text = user!!.level.toString()
            binding.txtCoins.text = user.coins.toString()
        }

        binding.btnPlay.setOnClickListener { view ->
            view.findNavController().navigate(R.id.gameFragment)
        }

        binding.txtCoins.setOnClickListener {
            viewModel.getData(model.data.value!!){ user ->
                binding.nivel.text = user!!.level.toString()
                binding.txtCoins.text = user.coins.toString()
            }
        }

        //UPDATE COINS + 10
        binding.btnCoins.setOnClickListener {
            viewModel.getData(model.data.value!!){ user ->
                val newCoins = user!!.coins + 100
                viewModel.updateCoins(model.data.value!!.toInt(), newCoins)
            }
        }

        // Assuming you have initialized ViewModel using ViewModelProvider
        viewModel.getUserById(model.data.value!!.toInt()).observe(requireActivity()) { updatedUser ->
            // Update the views with the updated user details
            binding.nivel.text = updatedUser!!.level.toString()
            binding.txtCoins.text = updatedUser.coins.toString()
        }


        binding.nivel.setOnClickListener {
            if(!model.showLevel){
                viewModel.getData(model.data.value!!){ user ->
                    val div = user!!.level / 10
                    val integerPart = div.toInt()
                    val number = integerPart + 1
                    binding.nivel.text = number.toString()
                }
            }else{
                viewModel.getData(model.data.value!!){ user ->
                    binding.nivel.text = "${user!!.level} / 10"
                }
            }
            model.showLevel = !model.showLevel
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}