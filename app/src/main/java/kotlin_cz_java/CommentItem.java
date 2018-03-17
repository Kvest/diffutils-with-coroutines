package kotlin_cz_java;

/**
 * Created by kvest on 3/17/18.
 */

public class CommentItem {
    public final int id;
    public final String author;
    public final String comment;
    public final int likesCount;

    public CommentItem(int id, String author, String comment, int likesCount) {
        this.id = id;
        this.author = author;
        this.comment = comment;
        this.likesCount = likesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentItem that = (CommentItem) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
