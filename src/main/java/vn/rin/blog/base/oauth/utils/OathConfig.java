package vn.rin.blog.base.oauth.utils;

import vn.rin.blog.base.oauth.APIConfig;


public class OathConfig {
    public static String getValue(String key) {
        return APIConfig.getInstance().getValue(key);
    }
}
