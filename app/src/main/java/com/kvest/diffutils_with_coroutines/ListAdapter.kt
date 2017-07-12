package com.kvest.diffutils_with_coroutines

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.properties.Delegates

/**
 * Created by kvest on 7/12/2017.
 */
class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>(), AutoUpdatableAdapter {
    var items: List<Item> by Delegates.observable(emptyList()) {
        _, oldList, newList ->
        autoNotify(oldList, newList) { (oldId), (newId) -> oldId == newId }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(parent.inflateWithoutAttach(R.layout.list_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =  holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) = with(itemView) {
            itemId.text = item.id.toString()
            itemName.text = item.name
        }
    }
}

