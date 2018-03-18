package kotlin_cz

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import diffutils_with_coroutines.DiffUtilDelegate

/**
 * Created by kvest on 3/17/18.
 */
class CommentsAdapter(initialCommentsList: List<CommentItem>)
        : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    var items: List<CommentItem> by DiffUtilDelegate(this, initialCommentsList) {
        newItem, oldItem -> newItem.id == oldItem.id
        //(oldId), (newId) -> oldId == newId
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        TODO("not implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =  holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CommentItem) {
            //TODO
        }
    }
}