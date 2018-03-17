package diffutils_with_coroutines

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by kvest on 7/18/2017.
 */
class BlockingDiffUtilDelegate<T>(val adapter: RecyclerView.Adapter<*>,
                                  private var items: List<T>,
                                  val itemsTheSameComparator: (T, T) -> Boolean) : ReadWriteProperty<Any?, List<T>> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): List<T> {
        return items
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: List<T>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    itemsTheSameComparator(items[oldItemPosition], value[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    items[oldItemPosition] == value[newItemPosition]

            override fun getOldListSize() = items.size

            override fun getNewListSize() = value.size
        })

        items = value
        diff.dispatchUpdatesTo(adapter)
    }
}
