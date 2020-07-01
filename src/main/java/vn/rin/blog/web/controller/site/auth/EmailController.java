package vn.rin.blog.web.controller.site.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.rin.blog.base.Result;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.data.UserVO;
import vn.rin.blog.service.MailService;
import vn.rin.blog.service.SecurityCodeService;
import vn.rin.blog.service.UserService;
import vn.rin.blog.web.controller.BaseController;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rin
 */
@RestController
@RequestMapping("/email")
public class EmailController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private SecurityCodeService securityCodeService;

    private static final String EMAIL_TITLE = "[{0}]Bạn đang sử dụng dịch vụ xác minh bảo mật hộp thư";

    @GetMapping("/send_code")
    public Result sendCode(String email, @RequestParam(name = "type", defaultValue = "1") Integer type) {
        Assert.hasLength(email, "Vui lòng nhập địa chỉ email của bạn");
        Assert.notNull(type, "Thiếu thông số cần thiết");

        String key = email;

        switch (type) {
            case Constants.CODE_BIND:
                AccountProfile profile = getProfile();
                Assert.notNull(profile, "Vui lòng đăng nhập trước khi làm điều này");
                key = String.valueOf(profile.getId());

                UserVO exist = userService.getByEmail(email);
                Assert.isNull(exist, "Hộp thư này đã được sử dụng");
                break;
            case Constants.CODE_FORGOT:
                UserVO user = userService.getByEmail(email);
                Assert.notNull(user, "Tài khoản không tồn tại");
                key = String.valueOf(user.getId());
                break;
            case Constants.CODE_REGISTER:
                key = email;
                break;
        }

        String code = securityCodeService.generateCode(key, type, email);
        Map<String, Object> context = new HashMap<>();
        context.put("code", code);

        String title = MessageFormat.format(EMAIL_TITLE, siteOptions.getValue("site_name"));
        mailService.sendTemplateEmail(email, title, Constants.EMAIL_TEMPLATE_CODE, context);
        return Result.successMessage("Email đã được gửi thành công");
    }

}
