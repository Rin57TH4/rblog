package vn.rin.blog.web.controller.site.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.exception.BlogException;
import vn.rin.blog.service.MessageService;
import vn.rin.blog.service.UserService;
import vn.rin.blog.web.controller.BaseController;
import vn.rin.blog.web.controller.site.Views;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rin
 */
@Controller
@RequestMapping("/users")
public class UsersController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;


    @GetMapping(value = "/{userId}")
    public String posts(@PathVariable(value = "userId") Long userId,
                        ModelMap model, HttpServletRequest request) {
        return method(userId, Views.METHOD_POSTS, model, request);
    }

    @GetMapping(value = "/{userId}/{method}")
    public String method(@PathVariable(value = "userId") Long userId,
                         @PathVariable(value = "method") String method,
                         ModelMap model, HttpServletRequest request) {
        model.put("pageNo", ServletRequestUtils.getIntParameter(request, "pageNo", 1));

        if (Views.METHOD_MESSAGES.equals(method)) {
            AccountProfile profile = getProfile();
            if (null == profile || profile.getId() != userId) {
                throw new BlogException("Bạn không có quyền truy cập trang này");
            }
            messageService.readed4Me(profile.getId());
        }

        initUser(userId, model);
        return view(String.format(Views.USER_METHOD_TEMPLATE, method));
    }

    private void initUser(long userId, ModelMap model) {
        model.put("user", userService.get(userId));
        boolean owner = false;

        AccountProfile profile = getProfile();
        if (null != profile && profile.getId() == userId) {
            owner = true;
            putProfile(userService.findProfile(profile.getId()));
        }
        model.put("owner", owner);
    }

}
