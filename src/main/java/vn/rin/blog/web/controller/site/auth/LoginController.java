package vn.rin.blog.web.controller.site.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.rin.blog.base.Result;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.web.controller.BaseController;
import vn.rin.blog.web.controller.site.Views;

/**
 * @author Rin
 */
@Controller
public class LoginController extends BaseController {
    @GetMapping(value = "/login")
    public String view() {
        return view(Views.LOGIN);
    }


    @PostMapping(value = "/login")
    public String login(String username,
                        String password,
                        @RequestParam(value = "rememberMe", defaultValue = "0") Boolean rememberMe,
                        ModelMap model) {
        String view = view(Views.LOGIN);

        Result<AccountProfile> result = executeLogin(username, password, rememberMe);

        if (result.isOk()) {
            view = String.format(Views.REDIRECT_USER_HOME, result.getData().getId());
        } else {
            model.put("message", result.getMessage());
        }
        return view;
    }

}
