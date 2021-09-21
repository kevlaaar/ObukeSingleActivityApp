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
import com.example.obukesingleactivityapplication.databinding.FragmentLoginBinding
import com.example.obukesingleactivityapplication.isValidEmail
import com.example.obukesingleactivityapplication.models.User
import com.example.obukesingleactivityapplication.models.UserResponse
import com.example.obukesingleactivityapplication.showInfoSnackBar
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            createAccountButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
            loginButton.setOnClickListener {
                if (validInput()) {
                    loginProgressBar.visibility = View.VISIBLE
                    viewModel.login(
                        emailEditText.text.toString().trim(),
                        passwordEditText.text.toString()
                    )
                }
            }
        }


        viewModel.loginStatus.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    binding.loginProgressBar.visibility = View.GONE
                } else {
                    binding.loginProgressBar.visibility = View.GONE
                    binding.root.showInfoSnackBar("LOGIN FAILED")
                }
            }
        })
        viewModel.verificationStatus.observe(viewLifecycleOwner, {
            it?.let {
                if(it){
                    binding.loginProgressBar.visibility = View.GONE
                    val validationAction = LoginFragmentDirections
                        .actionLoginFragmentToVerificationFragment(binding.emailEditText.text.toString().trim(), binding.passwordEditText.text.toString())
                    findNavController().navigate(validationAction)
                }
            }
        })

        viewModel.userResponseLiveData.observe(viewLifecycleOwner, {
            it?.let { nonNullUserResponse ->
                saveUserToSharedPreferencesAndGoToGreetingsScreen(nonNullUserResponse)
            }
        })

        viewModel.bearerTokenLiveData.observe(viewLifecycleOwner, {
            it?.let { bearerToken ->
                val sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()){
                    putString("BEARER_TOKEN", bearerToken)
                    apply()
                }

            }
        })
    }

    private fun saveUserToSharedPreferencesAndGoToGreetingsScreen(userResponse: UserResponse) {
        val sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val userJson = Gson().toJson(userResponse.user)
        with(sharedPreferences.edit()){
            putString("USER_OBJECT", userJson)
            putString("BEARER_TOKEN", userResponse.accessToken)
            apply()
        }
        findNavController().navigate(R.id.action_loginFragment_to_greetingsFragment)
    }

    private fun validInput(): Boolean {
        with(binding) {
            if (!emailEditText.text.toString().isValidEmail()) {
                emailInputLayout.error = "Invalid email"
                return false
            } else {
                emailInputLayout.error = null
            }
            if (passwordEditText.text.length < 8) {
                passwordInputLayout.error = "Invalid password"
                return false
            } else {
                passwordInputLayout.error = null
            }
        }
        return true
    }


}