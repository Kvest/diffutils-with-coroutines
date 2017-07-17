package com.kvest.diffutils_with_coroutines

import android.support.v7.util.DiffUtil
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import android.support.v7.widget.RecyclerView
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by kvest on 7/17/2017.
 */
typealias ItemsTheSameComparator<T> = (T, T) -> Boolean

class DiffUtilDelegate<T>(val adapter: RecyclerView.Adapter<*>,
                          initialValue: List<T>,
                          val calculateDiffContext: CoroutineContext = CommonPool,
                          val itemsTheSameComparator: ItemsTheSameComparator<T>) : ReadWriteProperty<Any?, List<T>> {
    private var items = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): List<T> {
        return items
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, newItems: List<T>) {
        launch (UI) {
            val diff = async(calculateDiffContext) {
                DiffUtil.calculateDiff(object : DiffUtil.Callback() {
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
            }.await()

            items = newItems
            diff.dispatchUpdatesTo(adapter)
        }
    }
}