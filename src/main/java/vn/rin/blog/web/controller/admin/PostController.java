package vn.rin.blog.web.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.rin.blog.base.Result;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.service.ChannelService;
import vn.rin.blog.service.PostService;
import vn.rin.blog.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Rin
 */
@Controller("adminPostController")
@RequestMapping("/admin/post")
public class PostController extends BaseController {
    @Autowired
    private PostService postService;
    @Autowired
    private ChannelService channelService;

    @RequestMapping("/list")
    public String list(String title, ModelMap model, HttpServletRequest request) {
        long id = ServletRequestUtils.getLongParameter(request, "id", Constants.ZERO);
        int channelId = ServletRequestUtils.getIntParameter(request, "channelId", Constants.ZERO);

        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "weight", "created"));
        Page<PostVO> page = postService.paging4Admin(pageable, channelId, title);
        model.put("page", page);
        model.put("title", title);
        model.put("id", id);
        model.put("channelId", channelId);
        model.put("channels", channelService.findAll(Constants.IGNORE));
        return "/admin/post/list";
    }


    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String toUpdate(Long id, ModelMap model) {
        String editor = siteOptions.getValue("editor");
        if (null != id && id > 0) {
            PostVO view = postService.get(id);
            if (StringUtils.isNoneBlank(view.getEditor())) {
                editor = view.getEditor();
            }
            model.put("view", view);
        }
        model.put("editor", editor);
        model.put("channels", channelService.findAll(Constants.IGNORE));
        return "/admin/post/view";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String subUpdate(PostVO post) {
        if (post != null) {
            if (post.getId() > 0) {
                postService.update(post);
            } else {
                AccountProfile profile = getProfile();
                post.setAuthorId(profile.getId());
                postService.post(post);
            }
        }
        return "redirect:/admin/post/list";
    }

    @RequestMapping("/featured")
    @ResponseBody
    public Result featured(Long id, HttpServletRequest request) {
        Result data = Result.failure("Hoạt động thất bại");
        int featured = ServletRequestUtils.getIntParameter(request, "featured", Constants.FEATURED_ACTIVE);
        if (id != null) {
            try {
                postService.updateFeatured(id, featured);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }

    @RequestMapping("/weight")
    @ResponseBody
    public Result weight(Long id, HttpServletRequest request) {
        Result data = Result.failure("Thao tác thất bại");
        int weight = ServletRequestUtils.getIntParameter(request, "weight", Constants.FEATURED_ACTIVE);
        if (id != null) {
            try {
                postService.updateWeight(id, weight);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id") List<Long> id) {
        Result data = Result.failure("Thao tác thất bại");
        if (id != null) {
            try {
                postService.delete(id);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }
}
