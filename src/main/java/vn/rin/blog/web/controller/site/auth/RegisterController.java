package vn.rin.blog.web.controller.site.auth;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import vn.rin.blog.base.Result;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.data.UserVO;
import vn.rin.blog.service.SecurityCodeService;
import vn.rin.blog.service.UserService;
import vn.rin.blog.web.controller.BaseController;
import vn.rin.blog.web.controller.site.Views;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rin
 */
@Controller
@ConditionalOnProperty(name = "site.controls.register", havingValue = "true", matchIfMissing = true)
public class RegisterController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityCodeService securityCodeService;

    @GetMapping("/register")
    public String view() {
        AccountProfile profile = getProfile();
        if (profile != null) {
            return String.format(Views.REDIRECT_USER_HOME, profile.getId());
        }
        return view(Views.REGISTER);
    }

    @PostMapping("/register")
    public String register(UserVO post, HttpServletRequest request, ModelMap model) {
        String view = view(Views.REGISTER);
        try {
            if (siteOptions.getControls().isRegister_email_validate()) {
                String code = request.getParameter("code");
                Assert.state(StringUtils.isNotBlank(post.getEmail()), "Vui lòng nhập địa chỉ email của bạn");
                Assert.state(StringUtils.isNotBlank(code), "Vui lòng nhập mã xác minh email của bạn");
                securityCodeService.verify(post.getEmail(), Constants.CODE_REGISTER, code);
            }
            post.setAvatar(Constants.AVATAR);
            userService.register(post);
            Result<AccountProfile> result = executeLogin(post.getUsername(), post.getPassword(), false);
            view = String.format(Views.REDIRECT_USER_HOME, result.getData().getId());
        } catch (Exception e) {
            model.addAttribute("post", post);
            model.put("data", Result.failure(e.getMessage()));
        }
        return view;
    }

}
