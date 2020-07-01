package vn.rin.blog.web.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.rin.blog.base.Result;
import vn.rin.blog.common.Constants;
import vn.rin.blog.config.ContextStartup;
import vn.rin.blog.domain.entity.ChannelEntity;
import vn.rin.blog.service.ChannelService;
import vn.rin.blog.service.PostService;
import vn.rin.blog.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rin
 */
@Controller("adminChannelController")
@RequestMapping("/admin/channel")
public class ChannelController extends BaseController {
    @Autowired
    private ChannelService channelService;

    @Autowired
    private PostService postService;
    @Autowired
    private ContextStartup contextStartup;

    @RequestMapping("/list")
//	@RequiresPermissions("channel:list")
    public String list(ModelMap model) {
        model.put("list", channelService.findAll(Constants.IGNORE));
        return "/admin/channel/list";
    }

    @RequestMapping("/view")
    public String view(Integer id, ModelMap model) {
        if (id != null) {
            ChannelEntity view = channelService.getById(id);
            model.put("view", view);
        }
        return "/admin/channel/view";
    }

    @RequestMapping("/update")
//	@RequiresPermissions("channel:update")
    public String update(ChannelEntity view) {
        if (view != null) {
            channelService.update(view);

            contextStartup.resetChannels();
        }
        return "redirect:/admin/channel/list";
    }

    @RequestMapping("/weight")
    @ResponseBody
    public Result weight(@RequestParam Integer id, HttpServletRequest request) {
        int weight = ServletRequestUtils.getIntParameter(request, "weight", Constants.FEATURED_ACTIVE);
        channelService.updateWeight(id, weight);
        contextStartup.resetChannels();
        return Result.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
	@RequiresPermissions("channel:delete")
    public Result delete(Integer id) {
        Result data = Result.failure("Thất bại");
        if (id != null) {
            try {
                channelService.delete(id);
                data = Result.success();

                postService.deleteByChannelId(id);

                contextStartup.resetChannels();

            } catch (Exception e) {
                data = Result.failure(e.getMessage());
            }
        }
        return data;
    }

}
