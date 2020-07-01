package vn.rin.blog.web.controller.site;

/**
 * @author Rin
 */
public interface Views {

    String LOGIN = "/auth/login";

    String REGISTER = "/auth/register";

    String OAUTH_REGISTER = "/auth/oauth_register";

    String FORGOT = "/auth/forgot";

    String INDEX = "/index";

    String USER_METHOD_TEMPLATE = "/user/method_%s";

    String METHOD_POSTS = "posts";

    String METHOD_COMMENTS = "comments";

    String METHOD_FAVORITES = "favorites";

    String METHOD_MESSAGES = "messages";

    String SETTINGS_AVATAR = "/settings/avatar";

    String SETTINGS_PASSWORD = "/settings/password";

    String SETTINGS_PROFILE = "/settings/profile";

    String SETTINGS_EMAIL = "/settings/email";

    String TAG_INDEX = "/tag/index";

    String TAG_VIEW = "/tag/view";

    String SEARCH = "/search";

    String POST_EDITING = "/channel/editing";

    String POST_INDEX = "/channel/index";

    String POST_VIEW = "/channel/view";

    String REDIRECT_USER_HOME = "redirect:/users/%d";

    String REDIRECT_INDEX = "redirect:/index";
}
