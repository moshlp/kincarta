package com.example.kincarta.presentation.main

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.kincarta.R
import com.example.kincarta.commons.BaseActivity
import com.example.kincarta.data.model.Contact
import com.example.kincarta.databinding.ActivityMainBinding
import com.example.kincarta.presentation.list.ContactListActivity
import com.example.kincarta.presentation.main.viewmodel.MainViewModel
import com.example.kincarta.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        viewModel.getcontacts().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { contacts -> retrieveList(contacts) }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun retrieveList(contacts: List<Contact>) {
        val intent = Intent(this, ContactListActivity::class.java)
        intent.putParcelableArrayListExtra("contacts", contacts as ArrayList<out Parcelable>)
        startActivity(intent)
    }
}