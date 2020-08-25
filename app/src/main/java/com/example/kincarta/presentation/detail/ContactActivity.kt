package com.example.kincarta.presentation.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kincarta.R
import com.example.kincarta.commons.BaseActivity
import com.example.kincarta.data.model.Contact
import com.example.kincarta.databinding.ActivityContactBinding
import com.example.kincarta.presentation.detail.viewmodel.ContactViewModel
import com.squareup.picasso.Picasso
import java.util.*

class ContactActivity : BaseActivity() {

    private lateinit var binding: ActivityContactBinding
    private lateinit var viewModel: ContactViewModel
    private var contact = Contact()
    private var contacts = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        initViews()
    }

    private fun initViews() {
        binding.viewmodel = viewModel
        contact = intent.getParcelableExtra<Contact>("contact")
        contacts = intent.getParcelableArrayListExtra("contacts")
        viewModel.contact = contact
        Picasso.get()
            .load(contact.largeImageURL)
            .error(R.drawable.usersmall)
            .placeholder(R.drawable.usersmall)
            .into(binding.imageView2)
        //actionbar
        val actionbar = supportActionBar
        this!!.title = "Contacts"
        with(actionbar) {
            this!!.title = "Contacts"
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (contact.isFavorite!! && menu != null) {
            menu[0].setIcon(R.drawable.favoritetrue)
        } else {
            menu?.get(0)?.setIcon(R.drawable.favoritefalse)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.favorite) {
            toggleIcon(item)
            setFavorite(item, contact)
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun setFavorite(item: MenuItem, contact: Contact) {
        var fav = false
        if (item.title == "favoritetrue") {
            fav = true
        }
        contacts.forEach {
            if (it.id == contact.id) {
                it.isFavorite = fav
                return
            }
        }
    }

    private fun toggleIcon(item: MenuItem) {
        var drawable = R.drawable.favoritetrue
        if (item.title == "favoritetrue") {
            drawable = R.drawable.favoritefalse
            item.title = "favoritefalse"
        } else {
            item.title = "favoritetrue"
        }
        item.icon = ContextCompat.getDrawable(this, drawable);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}