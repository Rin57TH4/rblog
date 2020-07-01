package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.rin.blog.domain.entity.UserOauthEntity;

/**
 * @author Rin
 */
public interface UserOauthRepo extends JpaRepository<UserOauthEntity, Long> {
    UserOauthEntity findByAccessToken(String accessToken);

    UserOauthEntity findByOauthUserId(String oauthUserId);

    UserOauthEntity findByUserId(long userId);
}
