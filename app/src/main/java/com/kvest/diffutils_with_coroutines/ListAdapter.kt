package com.kvest.diffutils_with_coroutines

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.properties.Delegates

/**
 * Created by kvest on 7/12/2017.
 */
class ListAdapter() : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    var items: List<Item> by Delegates.observable(emptyList()) {
        prop, oldList, newList ->
        notifyChanges(oldList, newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(parent.inflateWithoutAttach(R.layout.list_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =  holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun notifyChanges(oldList: List<Item>, newList: List<Item>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].id == newList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size
        })

        diff.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) = with(itemView) {
            itemId.text = item.id.toString()
            itemName.text = item.name
        }
    }
}

