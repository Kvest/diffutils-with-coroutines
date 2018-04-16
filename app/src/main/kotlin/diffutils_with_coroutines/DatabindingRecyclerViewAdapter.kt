package diffutils_with_coroutines

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class DatabindingRecyclerViewAdapter<T : Any>(context: Context) : RecyclerView.Adapter<DatabindingRecyclerViewAdapter.ViewHolder<T>>() {
    private val layoutInflater = LayoutInflater.from(context)
    var items: List<T> by DiffUtilDelegate(this, emptyList(), itemsTheSameComparator = getItemsTheSameComparator())
    var holderInit: (ViewDataBinding.() -> Unit)? = null

    protected abstract val variableId: Int
    protected abstract val layoutId: Int
    abstract fun getItemsTheSameComparator(): (T, T) -> Boolean

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        var binding = inflateBinding<ViewDataBinding>(layoutInflater, layoutId, parent)
        holderInit?.let {
            holderInit -> binding.holderInit()
        }

        return ViewHolder(binding, variableId)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder<in T>(private val binding: ViewDataBinding,
                           private val variableId: Int) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.setVariable(variableId, item)
            binding.executePendingBindings()
        }
    }
}