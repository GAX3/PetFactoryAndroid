package com.example.petfactorybd.fragments.game

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.petfactorybd.AppViewModel
import com.example.petfactorybd.R
import com.example.petfactorybd.database.AppDatabase
import com.example.petfactorybd.database.UserRepository
import com.example.petfactorybd.databinding.FragmentGameBinding
import com.example.petfactorybd.viewmodel.UserViewModel
import kotlin.random.Random

class GameFragment : Fragment() {

    private lateinit var gameArea: FrameLayout
    private var score = 0
    private val handler = Handler(Looper.getMainLooper())
    private val objects = mutableListOf<TextView>()
    private var gameRunning = true
    private lateinit var timer: CountDownTimer
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayerSoundtrack: MediaPlayer


    //Coommon things
    private var _binding: FragmentGameBinding? = null
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
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameArea = binding.gameArea
        startGame()

        binding.btnEndGame.setOnClickListener { view ->
            viewModel.getData(model.data.value!!){ user ->
                val newCoins = user!!.coins + score
                viewModel.updateCoins(model.data.value!!.toInt(), newCoins)
            }
            view.findNavController().popBackStack()
        }

        // Initialize MediaPlayer with a sound resource
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.clap)
        mediaPlayerSoundtrack = MediaPlayer.create(requireContext(), R.raw.happy)

        mediaPlayerSoundtrack.start()

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
            endGame(false)
            findNavController().popBackStack()
        }


    }


    private fun startGame() {
        score = 0
        gameRunning = true
        startTimer()
        spawnObjects()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(30000, 1000) { // 30 seconds, 1-second intervals
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.txtTime.text = secondsRemaining.toString()
            }

            override fun onFinish() {
                gameRunning = false
                endGame()
            }
        }
        timer.start()
    }

    private fun spawnObjects() {
        if (!gameRunning || gameArea.width == 0 || gameArea.height == 0) {
            // Retry after a short delay if dimensions are not yet initialized
            handler.postDelayed({ spawnObjects() }, 100)
            return
        }

        val maxItems = 10
        if (objects.size >= maxItems) {
            // If there are already 10 items on the screen, don't spawn more
            return
        }

        val size = 150
        val maxX = gameArea.width - size
        val maxY = gameArea.height - size

        if (maxX <= 0 || maxY <= 0) {
            // If the game area is too small, skip spawning this object
            return
        }

        val textView = TextView(requireContext()).apply {
            text = "\uD83C\uDF88"
            textSize = 32f
            setTextColor(Color.RED)
            layoutParams = FrameLayout.LayoutParams(size, size)
            x = Random.nextInt(0, maxX).toFloat()
            y = Random.nextInt(0, maxY).toFloat()
        }

        textView.setOnClickListener {
            score++
            gameArea.removeView(textView)
            objects.remove(textView)
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.prepare() // Prepare it again to play the same sound
            }
            mediaPlayer.start() // Play the sound
            binding.txtPoints.text = "Puntos: $score"
        }

        objects.add(textView)
        gameArea.addView(textView)

        handler.postDelayed({
            if (objects.contains(textView)) {
                gameArea.removeView(textView)
                objects.remove(textView)
                checkGameOver()
            }
        }, 2000)

        handler.postDelayed({ spawnObjects() }, 1000)
    }

    private fun checkGameOver() {
        if (objects.isEmpty() && !gameRunning) {
            endGame()
        }
    }

    private fun endGame(flag: Boolean = true) {
        timer.cancel() // Stop the timer if it's still running
        gameRunning = false

        if(flag){
            binding.cvResults.visibility = View.VISIBLE
            binding.txtCoinsEarned.text = score.toString()
            binding.txtTime.visibility = View.GONE
            binding.txtPoints.visibility = View.GONE
        }

        mediaPlayerSoundtrack.stop()
        //Toast.makeText(requireContext(), "Juego terminado. Puntos finales: $score", Toast.LENGTH_LONG).show()

        // Optionally navigate to another screen or reset the game
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::timer.isInitialized) {
            timer.cancel() // Ensure timer is canceled if the fragment is destroyed
        }
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}