package com.example.obukesingleactivityapplication.ui.greetings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.obukesingleactivityapplication.R
import com.example.obukesingleactivityapplication.databinding.FragmentGreetingsBinding
import com.example.obukesingleactivityapplication.models.User
import com.example.obukesingleactivityapplication.showInfoSnackBar
import com.google.gson.Gson

class GreetingsFragment : Fragment() {

    private var _binding: FragmentGreetingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GreetingsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGreetingsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(GreetingsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val userJsonString = sharedPreferences.getString("USER_OBJECT", "")
        val bearerToken = sharedPreferences.getString("BEARER_TOKEN", "")
        val userObject = Gson().fromJson(userJsonString, User::class.java)

        with(binding) {
            val greetingsValueString = "Greetings ${userObject.firstName} ${userObject.lastName}"
            greetingsTitle.text = greetingsValueString

            logoutButton.setOnClickListener {
                bearerToken?.let {
                    viewModel.logout(it)
                }
            }
            goToActivityHistoryButton.setOnClickListener{
                findNavController().navigate(R.id.action_greetingsFragment_to_activityHistoryFragment)
            }
        }

        viewModel.logoutStatus.observe(viewLifecycleOwner, {
            it?.let {
                if(it){
                    findNavController().navigate(R.id.action_greetingsFragment_to_loginFragment)
                } else {
                    binding.root.showInfoSnackBar("LOGOUT FAILED. YOU DON'T HAVE AUTHORIZATION")
                }
            }
        })
    }
}