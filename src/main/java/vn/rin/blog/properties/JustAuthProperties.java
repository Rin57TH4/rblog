package vn.rin.blog.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Rin
 */
@Component
@ConfigurationProperties(prefix = "me.zhyd.oauth")
@Data
@EqualsAndHashCode(callSuper = false)
@Order(-1)
public class JustAuthProperties  {
    private AuthConfig github;
    private AuthConfig google;
    private AuthConfig facebook;
    private AuthConfig csdn;
    private AuthConfig linkedin;
    private AuthConfig microsoft;
    private AuthConfig pinterest;
    private AuthConfig stackoverflow;
}
