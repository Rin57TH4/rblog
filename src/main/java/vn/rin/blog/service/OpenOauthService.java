package vn.rin.blog.service;

import vn.rin.blog.domain.data.OpenOauthVO;
import vn.rin.blog.domain.data.UserVO;

/**
 * @author Rin
 */
public interface OpenOauthService {
    UserVO getUserByOauthToken(String oauth_token);

    OpenOauthVO getOauthByToken(String oauth_token);

    OpenOauthVO getOauthByOauthUserId(String oauthUserId);

    OpenOauthVO getOauthByUid(long userId);

    boolean checkIsOriginalPassword(long userId);

    void saveOauthToken(OpenOauthVO oauth);

}
