package vn.rin.blog.web.controller.site.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import vn.rin.blog.base.Result;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.UserVO;
import vn.rin.blog.service.SecurityCodeService;
import vn.rin.blog.service.UserService;
import vn.rin.blog.web.controller.BaseController;
import vn.rin.blog.web.controller.site.Views;

/**
 * @author Rin
 */
@Controller
public class ForgotController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityCodeService securityCodeService;

    @GetMapping("/forgot")
    public String view() {
        return view(Views.FORGOT);
    }

    @PostMapping("/forgot")
    public String reset(String email, String code, String password, ModelMap model) {
        String view = view(Views.FORGOT);
        try {
            Assert.hasLength(email, "Vui lòng nhập địa chỉ email của bạn");
            Assert.hasLength(code, "Vui lòng nhập mã xác minh");
            UserVO user = userService.getByEmail(email);
            Assert.notNull(user, "Tài khoản không tồn tại");

            securityCodeService.verify(String.valueOf(user.getId()), Constants.CODE_FORGOT, code);
            userService.updatePassword(user.getId(), password);
            model.put("data", Result.successMessage("Xin chúc mừng, thiết lập lại mật khẩu của bạn đã thành công"));
            view = view(Views.LOGIN);
        } catch (Exception e) {
            model.put("data", Result.failure(e.getMessage()));
        }
        return view;
    }
}
