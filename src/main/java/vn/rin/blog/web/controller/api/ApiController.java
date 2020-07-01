package vn.rin.blog.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.rin.blog.base.Result;
import vn.rin.blog.utils.BeanMapUtils;
import vn.rin.blog.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Rin
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {
//    @Autowired
//    private PostService postService;
//    @Autowired
//    private CommentService commentService;

    @PostMapping(value = "/login")
    public Result login(String username, String password) {
        return executeLogin(username, password, false);
    }

//    @RequestMapping("/posts")
//    public Page<PostVO> posts(HttpServletRequest request) {
//        String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
//        int channelId = ServletRequestUtils.getIntParameter(request, "channelId", 0);
//        return postService.paging(wrapPageable(Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order))), channelId, null);
//    }

//    @RequestMapping(value = "/latest_comments")
//    public List<CommentVO> latestComments(@RequestParam(name = "size", defaultValue = "6") Integer size) {
//        return commentService.findLatestComments(size);
//    }
}
