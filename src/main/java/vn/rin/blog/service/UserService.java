package vn.rin.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.data.UserVO;

import java.util.Map;
import java.util.Set;

/**
 * @author Rin
 */
public interface UserService {
    Page<UserVO> paging(Pageable pageable, String name);

    Map<Long, UserVO> findMapByIds(Set<Long> ids);

    AccountProfile login(String username, String password);

    AccountProfile findProfile(String id);

    UserVO register(UserVO user);

    AccountProfile update(UserVO user);

    AccountProfile updateEmail(String id, String email);

    UserVO get(String userId);

    UserVO getByUsername(String username);

    UserVO getByEmail(String email);

    AccountProfile updateAvatar(long id, String path);

    void updatePassword(long id, String newPassword);

    void updatePassword(long id, String oldPassword, String newPassword);

    void updateStatus(long id, int status);

    long count();
}
