package com.example.obukesingleactivityapplication

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class TransactionsFragment: Fragment(R.layout.fragment_transactions) {

    lateinit var viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<ImageButton>(R.id.addTransactionButton)
        viewModel = ViewModelProvider(requireActivity()).get(ProfileViewModel::class.java)
        viewModel.warningMessage.postValue("TRANSACTION WARNING MESSAGE")
        viewModel.message = "MESSAGE FROM TRANSACTION"
        button.setOnClickListener{
            val profileDirection = TransactionsFragmentDirections.actionTransactionFragmentToProfileFragment(amountOfMoneyArg = 100)
            findNavController().navigate(profileDirection)
        }
    }
}