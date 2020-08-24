package com.example.kincarta.presentation.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kincarta.R
import com.example.kincarta.commons.BaseActivity
import com.example.kincarta.data.model.Contact
import com.example.kincarta.databinding.ActivityContactListBinding
import com.example.kincarta.presentation.detail.ContactActivity
import com.example.kincarta.presentation.list.adapter.Adapter2
import com.example.kincarta.presentation.list.viewmodel.ContactListViewModel
import com.example.kincarta.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactListActivity : BaseActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var adapter: Adapter2
    private val viewModel by viewModel<ContactListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list)
        initViews()
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        viewModel.getcontacts().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.rv.visibility = View.VISIBLE
                        binding.pb.visibility = View.GONE
                        resource.data?.let { contacts -> retrieveList(contacts) }
                    }
                    Status.ERROR -> {
                        binding.rv.visibility = View.VISIBLE
                        binding.pb.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.pb.visibility = View.VISIBLE
                        binding.rv.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(contacts: List<Contact>) {
        adapter.addHeaderAndSubmitList(contacts)
    }

    private fun initViews() {
        binding.rv.layoutManager = LinearLayoutManager(this)
        adapter = Adapter2(Adapter2.ItemListener{
            onClick(it)
        })
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                binding.rv.context,
                (binding.rv.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun onClick(contact: Contact) {
        var intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("contact", contact)
        startActivity(intent)
    }
}