package vn.rin.blog.web.controller.site.comment;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import vn.rin.blog.base.Result;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.data.CommentVO;
import vn.rin.blog.domain.event.MessageEvent;
import vn.rin.blog.service.CommentService;
import vn.rin.blog.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rin
 */
@RestController
@RequestMapping("/comment")
@ConditionalOnProperty(name = "site.controls.comment", havingValue = "true", matchIfMissing = true)
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/list/{toId}")
    public Page<CommentVO> view(@PathVariable Long toId) {
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "id"));
        return commentService.pagingByPostId(pageable, toId);
    }

    @RequestMapping("/submit")
    public Result post(Long toId, String text, HttpServletRequest request) {
        if (!isAuthenticated()) {
            return Result.failure("Vui lòng đăng nhập trước để hoạt động");
        }

        long pid = ServletRequestUtils.getLongParameter(request, "pid", 0);

        if (toId <= 0 || StringUtils.isBlank(text)) {
            return Result.failure("Hoạt động thất bại");
        }

        AccountProfile profile = getProfile();

        CommentVO c = new CommentVO();
        c.setPostId(toId);
        c.setContent(HtmlUtils.htmlEscape(text));
        c.setAuthorId(profile.getId());

        c.setPid(pid);

        commentService.post(c);

        sendMessage(profile.getId(), toId, pid);

        return Result.successMessage("Xuất bản thành công");
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam(name = "id") Long id) {
        Result data;
        try {
            commentService.delete(id, getProfile().getId());
            data = Result.success();
        } catch (Exception e) {
            data = Result.failure(e.getMessage());
        }
        return data;
    }

    private void sendMessage(long userId, long postId, long pid) {
        MessageEvent event = new MessageEvent("MessageEvent");
        event.setFromUserId(userId);

        if (pid > 0) {
            event.setEvent(Constants.MESSAGE_EVENT_COMMENT_REPLY);
            event.setCommentParentId(pid);
        } else {
            event.setEvent(Constants.MESSAGE_EVENT_COMMENT);
        }
        event.setPostId(postId);
        applicationContext.publishEvent(event);
    }
}