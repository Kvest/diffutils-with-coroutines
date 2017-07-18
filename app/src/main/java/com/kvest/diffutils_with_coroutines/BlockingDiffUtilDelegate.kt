package com.kvest.diffutils_with_coroutines

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by kvest on 7/18/2017.
 */
class BlockingDiffUtilDelegate<T>(val adapter: RecyclerView.Adapter<*>,
                                   initialValue: List<T>,
                                   val itemsTheSameComparator: ItemsTheSameComparator<T>) : ReadWriteProperty<Any?, List<T>> {
    private var items = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): List<T> {
        return items
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, newItems: List<T>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    itemsTheSameComparator(items[oldItemPosition], newItems[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    items[oldItemPosition] == newItems[newItemPosition]

            override fun getOldListSize(): Int {
                return items.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }
        })

        items = newItems
        diff.dispatchUpdatesTo(adapter)
    }
}
