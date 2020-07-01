package vn.rin.blog.web.controller.site.posts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import vn.rin.blog.base.Result;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.service.ChannelService;
import vn.rin.blog.service.PostService;
import vn.rin.blog.web.controller.BaseController;
import vn.rin.blog.web.controller.site.Views;

/**
 * @author Rin
 */
@Controller
@RequestMapping("/post")
public class PostController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private ChannelService channelService;

    @GetMapping("/editing")
    public String view(Long id, ModelMap model) {
        model.put("channels", channelService.findAll(Constants.STATUS_NORMAL));
        model.put("editing", true);
        String editor = siteOptions.getValue("editor");
        if (null != id && id > 0) {
            AccountProfile profile = getProfile();
            PostVO view = postService.get(id);

            Assert.notNull(view, "Bài viết này đã bị xóa");
            Assert.isTrue(view.getAuthorId() == profile.getId(), "Bài viết này không thuộc về bạn");

            Assert.isTrue(view.getChannel().getStatus() == Constants.STATUS_NORMAL, "Vui lòng chỉnh sửa bài viết này trong nền");
            model.put("view", view);

            if (StringUtils.isNoneBlank(view.getEditor())) {
                editor = view.getEditor();
            }
        }
        model.put("editor", editor);
        return view(Views.POST_EDITING);
    }


    @PostMapping("/submit")
    public String post(PostVO post) {
        Assert.notNull(post, "Thông số không đầy đủ");
        Assert.state(StringUtils.isNotBlank(post.getTitle()), "Tiêu đề không thể để trống");
        Assert.state(StringUtils.isNotBlank(post.getContent()), "Nội dung không thể để trống");

        AccountProfile profile = getProfile();
        post.setAuthorId(profile.getId());

        if (post.getId() > 0) {
            PostVO exist = postService.get(post.getId());
            Assert.notNull(exist, "Bài viết không tồn tại");
            Assert.isTrue(exist.getAuthorId() == profile.getId(), "Bài viết này không thuộc về bạn");

            postService.update(post);
        } else {
            postService.post(post);
        }
        return String.format(Views.REDIRECT_USER_HOME, profile.getId());
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable Long id) {
        Result data;
        try {
            postService.delete(id, getProfile().getId());
            data = Result.success();
        } catch (Exception e) {
            data = Result.failure(e.getMessage());
        }
        return data;
    }

}
