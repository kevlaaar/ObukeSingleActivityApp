package com.example.obukesingleactivityapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.obukesingleactivityapplication.databinding.FragmentRegistrationBinding
import com.example.obukesingleactivityapplication.models.RegisterUserBody
import com.google.android.material.snackbar.Snackbar

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            createUserButton.setOnClickListener {
                if (validInput()) {
                    createUser()
                }
            }
        }

        viewModel.registrationStatus.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    findNavController().navigate(R.id.action_registrationFragment_to_profileFragment)
                } else {
                    Snackbar.make(binding.root, "REGISTRATION FAILED", Snackbar.LENGTH_LONG).show()
                }
            }
        })
        binding.ageEditText.doAfterTextChanged {
            it.toString().let { string ->
                if(string.isNotEmpty()){
                    val age = it.toString().toInt()
                    if(age < 18){
                        binding.ageCheckbox.visibility = View.VISIBLE
                        binding.ageText.visibility = View.VISIBLE
                    }else {
                        binding.ageCheckbox.visibility = View.GONE
                        binding.ageText.visibility = View.GONE
                    }
                }
            }

        }
    }

    private fun validInput(): Boolean {
        binding.apply {
            if (emailEditText.text.length < 3) {
                emailInputLayout.error = "Invalid email"
                binding.root.scrollTo(0, emailInputLayout.top)
                return false
            } else {
                emailInputLayout.error = null
            }

            if (firstNameEditText.text.length < 3) {
                firstNameInputLayout.error = "Invalid first name"
                binding.root.scrollTo(0, firstNameInputLayout.top)
                return false
            } else {
                firstNameInputLayout.error = null
            }
            if (lastNameEditText.text.length < 3) {
                lastNameInputLayout.error = "Invalid last name"
                binding.root.scrollTo(0, lastNameInputLayout.top)
                return false
            } else {
                lastNameInputLayout.error = null
            }
            if (passwordEditText.text.length < 3) {
                passwordInputLayout.error = "Invalid password"
                binding.root.scrollTo(0, passwordInputLayout.top)
                return false
            } else {
                passwordInputLayout.error = null
            }
            if (repeatPasswordEditText.text.toString() != passwordEditText.text.toString()) {
                repeatPasswordInputLayout.error = "Passwords are not matching"
                binding.root.scrollTo(0, repeatPasswordInputLayout.top)
                return false
            } else {
                repeatPasswordInputLayout.error = null
            }
        }
        return true
    }

    private fun createUser() {
        val registerUserBody = RegisterUserBody(
            email = binding.emailEditText.text.toString(),
            firstName = binding.firstNameEditText.text.toString(),
            lastName = binding.lastNameEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
            repeatPassword = binding.repeatPasswordEditText.text.toString(),
            birthYear = binding.ageEditText.text.toString()
        )
        viewModel.registerUser(registerUserBody)
    }
}