package kotlin_cz

import android.support.v7.util.DiffUtil

/**
 * Created by kvest on 3/17/18.
 */
class CommentItemDiffUtillCallback(val newItems: List<CommentItem>,
                                   val oldItems: List<CommentItem>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size
}