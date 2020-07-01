package vn.rin.blog.base.oauth;

import java.lang.reflect.Method;

public class APIConfig {
    private static APIConfig config = new APIConfig();
    private String openid_github;
    private String openkey_github;
    private String redirect_github;
    private String lbs_ak_baidu;
    private String dp_key;
    private String dp_secret;

    private APIConfig() {

    }

    public static APIConfig getInstance() {
        if (config == null) {
            config = new APIConfig();
        }
        return config;
    }

    public String getValue(String attrName) {
        String firstLetter = attrName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + attrName.substring(1);
        Object value = "";
        try {
            Method method = APIConfig.class.getMethod(getter, new Class[0]);
            value = method.invoke(config, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) value;
    }

    public String getOpenid_github() {
        return this.openid_github;
    }

    public void setOpenid_github(String openid_github) {
        this.openid_github = openid_github;
    }

    public String getOpenkey_github() {
        return this.openkey_github;
    }

    public void setOpenkey_github(String openkey_github) {
        this.openkey_github = openkey_github;
    }

    public String getRedirect_github() {
        return this.redirect_github;
    }

    public void setRedirect_github(String redirect_github) {
        this.redirect_github = redirect_github;
    }

    public String getLbs_ak_baidu() {
        return this.lbs_ak_baidu;
    }

    public void setLbs_ak_baidu(String lbs_ak_baidu) {
        this.lbs_ak_baidu = lbs_ak_baidu;
    }

    public String getDp_key() {
        return this.dp_key;
    }

    public void setDp_key(String dp_key) {
        this.dp_key = dp_key;
    }

    public String getDp_secret() {
        return this.dp_secret;
    }

    public void setDp_secret(String dp_secret) {
        this.dp_secret = dp_secret;
    }
}
