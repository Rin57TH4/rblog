package vn.rin.blog.domain.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.MessageVO;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.domain.entity.CommentEntity;
import vn.rin.blog.domain.event.MessageEvent;
import vn.rin.blog.service.CommentService;
import vn.rin.blog.service.MessageService;
import vn.rin.blog.service.PostService;

/**
 * @author Rin
 */
@Component
public class MessageEventHandler implements ApplicationListener<MessageEvent> {
    @Autowired
    private MessageService messageService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @Async
    @Override
    public void onApplicationEvent(MessageEvent event) {
        MessageVO nt = new MessageVO();
        nt.setPostId(event.getPostId());
        nt.setFromId(event.getFromUserId());
        nt.setEvent(event.getEvent());

        PostVO p;
        switch (event.getEvent()) {
            case Constants.MESSAGE_EVENT_FAVOR_POST:
                p = postService.get(event.getPostId());
                Assert.notNull(p, "Bài viết không tồn tại");
                nt.setUserId(p.getAuthorId());
                break;
            case Constants.MESSAGE_EVENT_COMMENT:
                p = postService.get(event.getPostId());
                Assert.notNull(p, "Bài viết không tồn tại");
                nt.setUserId(p.getAuthorId());

                postService.identityComments(event.getPostId());
                break;
            case Constants.MESSAGE_EVENT_COMMENT_REPLY:
                CommentEntity c = commentService.findById(event.getCommentParentId());
                Assert.notNull(c, "Trả lời bình luận không tồn tại");
                nt.setUserId(c.getAuthorId());

                postService.identityComments(event.getPostId());
                break;
            default:
                nt.setUserId(event.getToUserId());
        }

        messageService.send(nt);
    }
}
