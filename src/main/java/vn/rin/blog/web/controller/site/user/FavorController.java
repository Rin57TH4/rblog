package vn.rin.blog.web.controller.site.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.rin.blog.base.Result;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.event.MessageEvent;
import vn.rin.blog.service.PostService;
import vn.rin.blog.web.controller.BaseController;

/**
 * @author Rin
 */
@RestController
@RequestMapping("/user")
public class FavorController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping("/favor")
    public Result favor(Long id) {
        Result data = Result.failure("Thao tác thất bại");
        if (id != null) {
            try {
                AccountProfile up = getProfile();
                postService.favor(up.getId(), id);
                sendMessage(up.getId(), id);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }


    @RequestMapping("/unfavor")
    public Result unfavor(Long id) {
        Result data = Result.failure("Thao tác thất bại");
        if (id != null) {
            try {
                AccountProfile up = getProfile();
                postService.unfavor(up.getId(), id);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }


    private void sendMessage(long userId, long postId) {
        MessageEvent event = new MessageEvent("MessageEvent" + System.currentTimeMillis());
        event.setFromUserId(userId);
        event.setEvent(Constants.MESSAGE_EVENT_FAVOR_POST);
        event.setPostId(postId);
        applicationContext.publishEvent(event);
    }
}
