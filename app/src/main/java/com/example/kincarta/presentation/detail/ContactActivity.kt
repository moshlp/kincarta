package com.example.kincarta.presentation.detail

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kincarta.R
import com.example.kincarta.commons.BaseActivity
import com.example.kincarta.data.model.Contact
import com.example.kincarta.databinding.ActivityContactBinding
import com.example.kincarta.presentation.detail.viewmodel.ContactViewModel
import com.example.kincarta.presentation.list.viewmodel.ContactListViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactActivity : BaseActivity() {

    private lateinit var binding: ActivityContactBinding
    private lateinit var viewModel : ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        initViews()
    }

    private fun initViews() {
        binding.viewmodel = viewModel
        val contact = intent.getParcelableExtra<Contact>("contact")
        viewModel.contact = contact
        Picasso.get()
            .load(contact.largeImageURL)
            .error(R.drawable.usersmall)
            .placeholder(R.drawable.usersmall)
            .into(binding.imageView2)
    }
}