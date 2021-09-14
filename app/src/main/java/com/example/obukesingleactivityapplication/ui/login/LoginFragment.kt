package com.example.obukesingleactivityapplication.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.obukesingleactivityapplication.R
import com.example.obukesingleactivityapplication.databinding.FragmentLoginBinding
import com.example.obukesingleactivityapplication.isValidEmail
import com.example.obukesingleactivityapplication.showInfoSnackBar

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
                    binding.root.showInfoSnackBar("LOGIN PROSAO")
                } else {
                    binding.loginProgressBar.visibility = View.GONE
                    binding.root.showInfoSnackBar("LOGIN FAILED")
                }
            }
        })
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