package com.example.obukesingleactivityapplication.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.obukesingleactivityapplication.R
import com.example.obukesingleactivityapplication.databinding.FragmentSplashScreenBinding
import com.example.obukesingleactivityapplication.models.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val bearerToken = sharedPreferences.getString("BEARER_TOKEN", "")
        viewModel.refreshToken(bearerToken ?: "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refreshTokenUserLiveData.observe(viewLifecycleOwner, {
            it?.let {
                saveUserToSharedPreferencesAndGoToGreetingsScreen(it)
            }
        })
        viewModel.refreshTokenStatus.observe(viewLifecycleOwner, {
            it?.let {
                if(!it){
                    lifecycleScope.launch(Dispatchers.Main) {
                        delay(1000)
                        findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                    }
                }
            }
        })
    }

    private fun saveUserToSharedPreferencesAndGoToGreetingsScreen(userObject: User) {
        val sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val userJson = Gson().toJson(userObject)
//        with(sharedPreferences.edit()) {
//            putString("USER_OBJECT", userJson)
//            apply()
//        }
        lifecycleScope.launch(Dispatchers.Main){
            delay(1000)
            findNavController().navigate(R.id.action_splashScreenFragment_to_greetingsFragment)
        }

    }
}