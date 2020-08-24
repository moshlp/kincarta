package com.example.kincarta.presentation.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kincarta.R
import com.example.kincarta.data.model.Contact
import com.example.kincarta.databinding.ItemContactBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_header.view.*
import java.util.stream.Collectors

const val TYPE_HEADER: Int = 0
const val TYPE_ITEM: Int = 1
const val HEADER_FAV : String = "Favorites"
const val HEADER_OTHERS : String = "Other Contacts"

class Adapter2(val clickListener: ItemListener) :
    ListAdapter<Adapter2.DataItem, RecyclerView.ViewHolder>(ContactDiffCallback()) {

    sealed class DataItem {
        abstract val id: Long

        data class ContactItem(val contact: Contact) : DataItem() {
            override val id: Long = contact.id!!.toLong()
        }

        object Header : DataItem() {
            override val id = Long.MIN_VALUE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> TYPE_HEADER
            is DataItem.ContactItem -> TYPE_ITEM
        }
    }

    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Contact, clickListener: ItemListener) {
            binding.name.text = item.name
            binding.companyName.text = item.companyName
            binding.start.visibility = View.INVISIBLE
            if (item.isFavorite == true){
                binding.start.visibility = View.VISIBLE
            }
            Picasso.get()
                .load(item.largeImageURL)
                .error(R.drawable.usersmall)
                .placeholder(R.drawable.usersmall)
                .into(binding.imageView)
            itemView.setOnClickListener{
                clickListener.clickListener(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemContactBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> TextViewHolder.from(parent)
            TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    fun addHeaderAndSubmitList(list: List<Contact>?) {

        val favorites = list?.stream()?.filter{ contact -> contact.isFavorite == true}?.collect(
            Collectors.toList())
        val itemF = when (favorites) {
            null -> listOf(DataItem.Header)
            else -> favorites.map { DataItem.ContactItem(it) }
        }

        val others = list?.stream()?.filter{ contact -> contact.isFavorite == false}?.collect(
            Collectors.toList())
        val itemOther = when (others) {
            null -> listOf(DataItem.Header)
            else -> others.map { DataItem.ContactItem(it) }
        }

        val joined = ArrayList<DataItem>()
        var header = DataItem.Header
        joined.add(0, header)
        joined.addAll(itemF)
        header = DataItem.Header
        joined.add(itemF.size + 1, header)
        joined.addAll(itemOther)

        submitList(joined)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val contact = getItem(position) as DataItem.ContactItem
                holder.bind(contact.contact, clickListener)
            }
        }
    }

    class ItemListener(val clickListener: (contact: Contact) -> Unit) {
        fun onClick(contact: Contact) = clickListener(contact)
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}