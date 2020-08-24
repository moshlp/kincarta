package com.example.kincarta.presentation.list

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.VIEW_LOG_TAG
import com.example.kincarta.R
import com.example.kincarta.commons.BaseActivity
import com.example.kincarta.data.model.Contact
import com.example.kincarta.databinding.ActivityContactListBinding
import com.example.kincarta.presentation.list.adapter.Adapter2
import com.example.kincarta.presentation.list.adapter.ContactListAdapter
import com.example.kincarta.presentation.list.viewmodel.ContactListViewModel
import com.example.kincarta.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.stream.Collectors


class ContactListActivity : BaseActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var adapterFav: Adapter2
    private lateinit var adapterOthers: Adapter2
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
       // adapterFav.setData(contacts.sortedWith(compareBy({it.isFavorite == false} , {it.name})))
        //adapterOthers.setData(contacts.stream().filter{ !it.isFavorite!!}.collect(Collectors.toList()))

        adapterFav.addHeaderAndSubmitList(contacts)
    }

    private fun initViews() {
        binding.rv.layoutManager = LinearLayoutManager(this)
        adapterFav = Adapter2(Adapter2.ItemListener{

        })
        binding.rv.adapter = adapterFav

//        adapterFav = ContactListAdapter(binding.rv, ContactListAdapter.ContactListener {
//            onClick(it)
//        })
//        binding.rv.adapter = adapterFav
//        binding.rvOther.layoutManager = LinearLayoutManager(this)
//        adapterOthers = Adapter2(Adapter2.ItemListener{
//
//        })
//        binding.rvOther.adapter = adapterOthers
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                binding.rv.context,
                (binding.rv.layoutManager as LinearLayoutManager).orientation
            )
        )

//        binding.tv.setOnClickListener{
//            if (binding.rv.visibility == View.VISIBLE){
//                binding.rv.visibility = View.GONE
//            } else {
//                binding.rv.visibility = View.VISIBLE
//            }
//        }
//        binding.rv.addItemDecoration(object : ItemDecoration() {
//            private val textSize = 50
//            private val groupSpacing = 100
//            private val itemsInGroup = 3
//            private val paint: Paint = Paint()
//            var isFirst = true
//            var isFirstOther = true
//            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//                var isFirstonDrawOver = true
//                var isFirstOtheronDrawOver = true
//                for (i in 0 until parent.childCount) {
//                    val view = parent.getChildAt(i)
//                    val contact = adapterFav.getItem(i)
//                    if (contact?.isFavorite == true && isFirstonDrawOver){
//                        c.drawText(
//                            getString(R.string.fav_contacts) , view.left.toFloat(),
//                            (view.top - groupSpacing / 2 + textSize / itemsInGroup).toFloat(), paint
//                        )
//                    //    c.drawColor(getColor(android.R.color.darker_gray))
//                        isFirstonDrawOver = false
//                    }
////                    else if (contact?.isFavorite == false && isFirstOther){
//////                        c.drawText(
//////                            getString(R.string.other_contacts) , view.left.toFloat(),
//////                            (view.top - groupSpacing / 2 + textSize / i).toFloat(), paint
//////                        )
////                        isFirstOther = false
////                    }
//
//
//
//           /*         val view = parent.getChildAt(i)
//                    val position = parent.getChildAdapterPosition(view)*/
////                    val holder = parent.findViewHolderForAdapterPosition(position)
////                    if (position % itemsInGroup == 0) {
////                        c.drawText(
////                            "Group " + (position / itemsInGroup + 1), view.left.toFloat(),
////                            (view.top - groupSpacing / 2 + textSize / 3).toFloat(), paint
////                        )
////                    }
//                }
//            }
//
//            override fun getItemOffsets(
//                outRect: Rect,
//                view: View,
//                parent: RecyclerView,
//                state: RecyclerView.State
//            ) {
//
//                val contact = adapterFav.getItem(parent.getChildAdapterPosition(view))
//                if (contact?.isFavorite == true && isFirst){
//                    outRect.set(10, groupSpacing, 10, 10)
//                    isFirst = false
//                } else if (contact?.isFavorite == false && isFirstOther){
//                    outRect.set(10, groupSpacing, 10, 10)
//                    isFirstOther = false
//                }
//            }
//
//            init {
//                paint.textSize = textSize.toFloat()
//            }
//        })
     //   binding.rvOther.adapter = adapterOthers

    }

    private fun onClick(contact: Contact) {

    }
}