package com.example.obukesingleactivityapplication.ui.registration

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.obukesingleactivityapplication.R
import com.example.obukesingleactivityapplication.databinding.FragmentValidationCodeBinding
import com.example.obukesingleactivityapplication.hideKeyboard
import com.example.obukesingleactivityapplication.models.RegisterVerificationBody
import com.example.obukesingleactivityapplication.models.User
import com.google.gson.Gson

class ValidationCodeFragment : Fragment() {

    private var _binding: FragmentValidationCodeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegistrationViewModel
    private var validationCode: String = ""

    private val args: ValidationCodeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentValidationCodeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            addListenerToEditText(firstDigitEditText, secondDigitEditText)
            addListenerToEditText(secondDigitEditText, thirdDigitEditText)
            addListenerToEditText(thirdDigitEditText, fourthDigitEditText)
            fourthDigitEditText.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if(s.toString().isNotEmpty()){
                        binding.root.hideKeyboard()
                        validationCode = "${validationCode}${s.toString()}"
                        viewModel.verifyUser(RegisterVerificationBody(args.email,args.password,validationCode))

                    }
                }
            })
        }
        viewModel.validationStatus.observe(viewLifecycleOwner, {
            it?.let {
                if(it) {
                    binding.validationStatusText.text = "VERIFICATION SUCCESSFUL"
                } else {
                    binding.validationStatusText.text = "VERIFICATION FAILED"
                    resetEditTexts()
                }
            }
        })
        viewModel.userLiveData.observe(viewLifecycleOwner, {
            it?.let {
                saveUserToSharedPreferencesAndGoToGreetingsScreen(it)
            }
        })
    }

    private fun saveUserToSharedPreferencesAndGoToGreetingsScreen(userObject: User) {
        val sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val userJson = Gson().toJson(userObject)
        with(sharedPreferences.edit()){
            putString("USER_OBJECT", userJson)
            apply()
        }
        findNavController().navigate(R.id.action_verificationFragment_to_greetingsFragment)
    }

    private fun resetEditTexts() {
        with(binding) {
            validationCode = ""
            firstDigitEditText.requestFocus()
            firstDigitEditText.text.clear()
            secondDigitEditText.text.clear()
            thirdDigitEditText.text.clear()
            fourthDigitEditText.text.clear()
            secondDigitEditText.setBackgroundResource(R.drawable.border_gray_round_8dp)
            thirdDigitEditText.setBackgroundResource(R.drawable.border_gray_round_8dp)
            fourthDigitEditText.setBackgroundResource(R.drawable.border_gray_round_8dp)
        }
    }

    private fun addListenerToEditText(currentText: EditText, nextEditText: EditText) {

        currentText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()){
                    validationCode = "${validationCode}${s.toString()}"
                   nextEditText.setBackgroundResource(R.drawable.border_white_round_8dp_black_outline)
                   nextEditText.requestFocus()
                }
            }
        })
    }
}