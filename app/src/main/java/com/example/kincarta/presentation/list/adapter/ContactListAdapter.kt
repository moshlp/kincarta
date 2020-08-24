package com.example.kincarta.presentation.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kincarta.R
import com.example.kincarta.commons.BaseAdapter
import com.example.kincarta.commons.BindingViewHolder
import com.example.kincarta.data.model.Contact
import com.example.kincarta.databinding.ItemContactBinding
import com.squareup.picasso.Picasso

class ContactListAdapter(rv: RecyclerView, private val clickListener: ContactListener) :
    BaseAdapter<Contact, ItemContactBinding>(rv, R.layout.item_contact) {


    class ContactListener(val clickListener: (item: Contact) -> Unit) {
        fun onClick(item: Contact) = clickListener(item)
    }

    fun getItem(position: Int): Contact? {
        return items[position] as Contact
    }

    override fun populateBindViewHolder(
        holder: BindingViewHolder<ItemContactBinding>?,
        item: Contact?,
        position: Int
    ) {
        if (item != null && holder != null) {
            holder.binding.name.text = item.name
            holder.binding.companyName.text = item.companyName
            if (item.isFavorite == true){
                holder.binding.start.visibility = View.VISIBLE
            }
            Picasso.get()
                .load(item.smallImageURL)
                .error(R.drawable.usersmall)
                .placeholder(R.drawable.usersmall)
                .into(holder.binding.imageView)
            holder.itemView.setOnClickListener{
                clickListener.clickListener(item)
            }
        }
    }


    override fun compareItems(itemA: Contact?, itemB: Contact?): Boolean {
        if (itemA != null && itemB != null) {
            return itemA.id.equals(itemB.id)
        }
        return false
    }

    override fun compareItemsContent(itemA: Contact?, itemB: Contact?): Boolean {
        if (itemA != null && itemB != null) {
            return itemA.name.equals(itemB.name)
        }
        return false
    }
}