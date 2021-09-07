package com.example.obukesingleactivityapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private val args: ProfileFragmentArgs by navArgs()

    private lateinit var viewModel: ProfileViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        val amountText = view.findViewById<TextView>(R.id.amountOfMoneyLabel)
        val amountOfMoneyText = view.findViewById<TextView>(R.id.amountOfMoneyTextView)
        amountOfMoneyText.setOnClickListener{
            viewModel.shouldShowWarning.postValue(true)
        }
        amountText.text = "${args.amountOfMoneyArg}"

        viewModel.shouldShowWarning.observe(viewLifecycleOwner, {
            println("OBSERVE SE DESIO")
            it?.let {
                if(it){
                    Snackbar.make(view, "WARNING FOR TRUE", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "WARNING FOR FALSE", Snackbar.LENGTH_LONG).show()
                }
            }
        })

        viewModel.warningMessage.observe(viewLifecycleOwner, {
            it?.let {
                amountText.text = it
            }
        })

        amountOfMoneyText.text = viewModel.message



    }
}