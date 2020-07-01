package vn.rin.blog.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.rin.blog.base.Result;
import vn.rin.blog.domain.data.CommentVO;
import vn.rin.blog.service.CommentService;
import vn.rin.blog.web.controller.BaseController;

import java.util.List;

/**
 * @author Rin
 */
@Controller("adminCommentController")
@RequestMapping("/admin/comment")
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/list")
    public String list(ModelMap model) {
        Pageable pageable = wrapPageable();
        Page<CommentVO> page = commentService.paging4Admin(pageable);
        model.put("page", page);
        return "/admin/comment/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id") List<Long> id) {
        Result data = Result.failure("Thao tacs thất bại");
        if (id != null) {
            try {
                commentService.delete(id);
                data = Result.success();
            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }
}
