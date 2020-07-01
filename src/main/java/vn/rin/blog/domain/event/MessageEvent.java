package vn.rin.blog.domain.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Rin
 */
public class MessageEvent extends ApplicationEvent {
    private static final long serialVersionUID = -4261382494171476390L;

    private long fromUserId;

    private long toUserId;

    private int event;

    private long postId;

    private long commentParentId;

    public MessageEvent(Object source) {
        super(source);
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getCommentParentId() {
        return commentParentId;
    }

    public void setCommentParentId(long commentParentId) {
        this.commentParentId = commentParentId;
    }
}
