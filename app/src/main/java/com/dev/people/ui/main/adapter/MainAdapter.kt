package com.dev.people.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.people.R
import com.dev.people.data.model.User
import kotlinx.android.synthetic.main.item_layout.view.*

/**
 * Created on 10 Dec 2022 by Sajid
 * MainActivity Adapter kotlin class for loading the listView items
 *
 */

class MainAdapter(arrayListOf: ArrayList<Any>) : RecyclerView.Adapter<MainAdapter.DataViewHolder>(), Filterable {

    private val users: ArrayList<User> = ArrayList<User>()
    var userFilterList: ArrayList<User> = ArrayList<User>()

    //data binding for the listView items
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.textViewUserName.text = user.name
            Glide.with(itemView.imageViewAvatar.context)
                .load(user.avatar)
                .into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = userFilterList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(userFilterList[position])

    //add items to the list
    fun addData(list: List<User>) {
        users.addAll(list)
        userFilterList.addAll(list)
    }

    //clear the search, users list to refresh the data
    fun removeData() {
        users.clear()
        userFilterList.clear()
    }

    //search filter to check the name with the search text
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                userFilterList = if (charSearch.isEmpty()) {
                    users
                } else {
                    val resultList = ArrayList<User>()
                    for (row in users) {
                        if (row.name.toLowerCase()
                                .contains(charSearch.toLowerCase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = userFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userFilterList = results?.values as ArrayList<User>
                notifyDataSetChanged()
            }

        }
    }
}