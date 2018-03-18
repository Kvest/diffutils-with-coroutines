package kotlin_cz_java;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kvest on 3/17/18.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private List<CommentItem> items;

    public CommentsAdapter(List<CommentItem> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<CommentItem> newItems) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallback(items, newItems));

        items = newItems;
        diffResult.dispatchUpdatesTo(this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(CommentItem item) {
            //TODO
        }
    }

    private static class DiffUtilCallback extends DiffUtil.Callback {
        private List<CommentItem> oldList;
        private List<CommentItem> newList;

        public DiffUtilCallback(List<CommentItem> oldList, List<CommentItem> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            CommentItem oldItem = oldList.get(oldItemPosition);
            CommentItem newItem = newList.get(newItemPosition);

            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            CommentItem oldItem = oldList.get(oldItemPosition);
            CommentItem newItem = newList.get(newItemPosition);

            return oldItem.author.equals(newItem.author)
                    && oldItem.comment.equals(newItem.comment)
                    && oldItem.likesCount == newItem.likesCount;
        }
    }
}
