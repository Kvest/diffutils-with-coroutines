package diffutils_with_coroutines

import android.content.Context
import com.kvest.diffutils_with_coroutines.BR
import com.kvest.diffutils_with_coroutines.R

class ListAdapter(context: Context) : DatabindingRecyclerViewAdapter<Item>(context) {
    override val variableId = BR.item
    override val layoutId = R.layout.list_item
    override fun getItemsTheSameComparator() = {
        (newId): Item, (oldId): Item -> newId == oldId
    }
}