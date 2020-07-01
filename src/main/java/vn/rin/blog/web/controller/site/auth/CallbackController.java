package vn.rin.blog.web.controller.site.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.rin.blog.base.Result;
import vn.rin.blog.base.oauth.APIConfig;
import vn.rin.blog.base.oauth.OauthGithub;
import vn.rin.blog.base.oauth.utils.OpenOauthBean;
import vn.rin.blog.base.oauth.utils.TokenUtil;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.data.OpenOauthVO;
import vn.rin.blog.domain.data.UserVO;
import vn.rin.blog.exception.BlogException;
import vn.rin.blog.service.OpenOauthService;
import vn.rin.blog.service.UserService;
import vn.rin.blog.utils.FilePathUtils;
import vn.rin.blog.utils.ImageUtils;
import vn.rin.blog.web.controller.BaseController;
import vn.rin.blog.web.controller.site.Views;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Rin
 */
@Slf4j
@Controller
@RequestMapping("/oauth/callback")
@ConditionalOnProperty(name = "site.controls.register", havingValue = "true", matchIfMissing = true)
public class CallbackController extends BaseController {
    private static final String SESSION_STATE = "_SESSION_STATE_";

    @Autowired
    private OpenOauthService openOauthService;
    @Autowired
    private UserService userService;

    @RequestMapping("/call_github")
    public void callGithub(HttpServletRequest request, HttpServletResponse response) {

        log.info("dasdd");
        APIConfig.getInstance().setOpenid_github(siteOptions.getValue(Constants.GITHUB_CLIENT_ID));
        APIConfig.getInstance().setOpenkey_github(siteOptions.getValue(Constants.GITHUB_SECRET_KEY));
        APIConfig.getInstance().setRedirect_github(siteOptions.getValue(Constants.GITHUB_CALLBACK));

        try {
            response.setContentType("text/html;charset=utf-8");
            String state = TokenUtil.randomState();
            request.getSession().setAttribute(SESSION_STATE, state);
            response.sendRedirect(OauthGithub.me().getAuthorizeUrl(state));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/github")
    public String callback4Github(String code, String state, HttpServletRequest request, ModelMap model) {
        log.info("code {}", code);
        String session_state = (String) request.getSession().getAttribute(SESSION_STATE);

        if (StringUtils.isBlank(state) || StringUtils.isBlank(session_state) || !state.equals(session_state) || StringUtils.isBlank(code)) {
            throw new BlogException("Thiếu thông số cần thiết");
        }
        request.getSession().removeAttribute(SESSION_STATE);

        OpenOauthBean openOauthBean = null;
        try {
            openOauthBean = OauthGithub.me().getUserBeanByCode(code);
        } catch (Exception e) {
            throw new BlogException("Đã xảy ra lỗi khi phân tích thông tin");
        }

        OpenOauthVO openOauth = new OpenOauthVO();
        openOauth.setOauthCode(code);
        openOauth.setAccessToken(openOauthBean.getAccessToken());
        openOauth.setExpireIn(openOauthBean.getNickname());
        //openid
        openOauth.setOauthUserId(openOauthBean.getOauthUserId());
        openOauth.setOauthType(openOauthBean.getOauthType());
        openOauth.setUsername(openOauthBean.getUsername());
        openOauth.setNickname(openOauthBean.getNickname());
        openOauth.setAvatar(openOauthBean.getAvatar());


        OpenOauthVO thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());

        if (thirdToken == null) {
            model.put("open", openOauth);
            return view(Views.OAUTH_REGISTER);
        }
        String username = userService.get(thirdToken.getUserId()).getUsername();
        return login(username, thirdToken.getAccessToken());


    }

    @RequestMapping("/bind_oauth")
    public String bindOauth(OpenOauthVO openOauth, HttpServletRequest request) throws Exception {
        OpenOauthVO thirdToken = openOauthService.getOauthByOauthUserId(openOauth.getOauthUserId());
        String username = openOauth.getUsername();

        if (thirdToken != null) {
            username = userService.get(thirdToken.getUserId()).getUsername();
        } else {
            UserVO user = userService.getByUsername(username);
            if (user == null) {
                UserVO u = userService.register(wrapUser(openOauth));


                String ava100 = Constants.avatarPath + getAvaPath(u.getId(), 100);
                byte[] bytes = ImageUtils.download(openOauth.getAvatar());
                String imagePath = storageFactory.get().writeToStore(bytes, ava100);
                userService.updateAvatar(u.getId(), imagePath);

                thirdToken = new OpenOauthVO();
                BeanUtils.copyProperties(openOauth, thirdToken);
                thirdToken.setUserId(u.getId());
                openOauthService.saveOauthToken(thirdToken);
            } else {
                username = user.getUsername();
            }
        }
        return login(username, openOauth.getAccessToken());
    }


    private String login(String username, String accessToken) {
        String view = view(Views.LOGIN);

        if (StringUtils.isNotBlank(username)) {
            Result<AccountProfile> result = executeLogin(username, accessToken, false);
            if (result.isOk()) {
                view = String.format(Views.REDIRECT_USER_HOME, result.getData().getId());
            }
            return view;
        }
        throw new BlogException("Đăng nhập thất bại");
    }

    private UserVO wrapUser(OpenOauthVO openOauth) {
        UserVO user = new UserVO();
        user.setUsername(openOauth.getUsername());
        user.setName(openOauth.getNickname());
        user.setPassword(openOauth.getAccessToken());

        if (StringUtils.isNotBlank(openOauth.getAvatar())) {
            user.setAvatar(openOauth.getAvatar());
        } else {
            user.setAvatar(Constants.AVATAR);
        }
        return user;
    }

    public String getAvaPath(long uid, int size) {
        String base = FilePathUtils.getAvatar(uid);
        return String.format("/%s_%d.jpg", base, size);
    }

}
