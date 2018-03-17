package diffutils_with_coroutines

import android.support.v7.util.DiffUtil
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by kvest on 7/17/2017.
 */
interface ChangePayloadCalculator {
    fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any?
}

class DiffUtilDelegate<T>(val adapter: RecyclerView.Adapter<*>,
                          initialValue: List<T>,
                          val calculateDiffContext: CoroutineContext = CommonPool,
                          val itemsTheSameComparator: (T, T) -> Boolean) : ReadWriteProperty<Any?, List<T>> {
    private val TAG = "DiffUtilDelegate"
    private var items = initialValue
    private val newItemsActor = actor<List<T>>(UI, capacity = Channel.CONFLATED) {
        for (event in channel) {
            calculateDiff(event)
        }
    }
    private val changePayloadCalculator = adapter as? ChangePayloadCalculator

    private suspend fun calculateDiff(newItems: List<T>) {
        val diff = async(calculateDiffContext) {
            Log.d(TAG, "Start calculateDiff in ${Thread.currentThread().name} ${items.size} -> ${newItems.size}");
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                        itemsTheSameComparator(items[oldItemPosition], newItems[newItemPosition])

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                        items[oldItemPosition] == newItems[newItemPosition]

                override fun getOldListSize() = items.size

                override fun getNewListSize() = newItems.size

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) =
                    changePayloadCalculator?.getChangePayload(oldItemPosition, newItemPosition)
            })
        }.await()

        Log.d(TAG, "Set items in ${Thread.currentThread().name} ${items.size} -> ${newItems.size}");

        items = newItems
        diff.dispatchUpdatesTo(adapter)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): List<T> {
        return items
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: List<T>) {
        newItemsActor.offer(value)
    }
}