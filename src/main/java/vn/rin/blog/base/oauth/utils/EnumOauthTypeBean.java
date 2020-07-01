package vn.rin.blog.base.oauth.utils;

public enum EnumOauthTypeBean {

    TYPE_GITHUB("github", 4);

    private String description;
    private int value;

    private EnumOauthTypeBean(String desc, int value) {
        this.description = desc;
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumOauthTypeBean getEnumStatus(int type) throws Exception {
        EnumOauthTypeBean[] status = values();
        for (int i = 0; i < status.length; i++) {
            if (status[i].getValue() == type) {
                return status[i];
            }
        }

        throw new Exception();
    }
}
