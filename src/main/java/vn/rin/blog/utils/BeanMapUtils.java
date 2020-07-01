package vn.rin.blog.utils;

import org.springframework.beans.BeanUtils;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.*;
import vn.rin.blog.domain.entities.User;
import vn.rin.blog.domain.entity.*;

/**
 * @author Rin
 */
public class BeanMapUtils {
    private static String[] USER_IGNORE = new String[]{"password", "extend", "roles"};

    public static UserVO copy(UserEntity po) {
        if (po == null) {
            return null;
        }
        UserVO ret = new UserVO();
        BeanUtils.copyProperties(po, ret, USER_IGNORE);
        return ret;
    }

    public static AccountProfile copyPassport(User po) {
        AccountProfile passport = new AccountProfile(po.getId(), po.getUsername());
        passport.setNickname(po.getNickname());
        passport.setEmail(po.getEmail());
        passport.setAvatarURL(po.getAvatarURL());
        passport.setLatestLoginTime(po.getLatestLoginTime());
        passport.setStatus(po.getStatus());
        return passport;
    }

    public static CommentVO copy(CommentEntity po) {
        CommentVO ret = new CommentVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static PostVO copy(PostEntity po) {
        PostVO d = new PostVO();
        BeanUtils.copyProperties(po, d);
        return d;
    }

    public static MessageVO copy(MessageEntity po) {
        MessageVO ret = new MessageVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static FavoriteVO copy(FavoriteEntity po) {
        FavoriteVO ret = new FavoriteVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static PostTagVO copy(PostTagEntity po) {
        PostTagVO ret = new PostTagVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static TagVO copy(TagEntity po) {
        TagVO ret = new TagVO();
        BeanUtils.copyProperties(po, ret);
        return ret;
    }

    public static String[] postOrder(String order) {
        String[] orders;
        switch (order) {
            case Constants.order.HOTTEST:
                orders = new String[]{"comments", "views", "created"};
                break;
            case Constants.order.FAVOR:
                orders = new String[]{"favors", "created"};
                break;
            default:
                orders = new String[]{"weight", "created"};
                break;
        }
        return orders;
    }

}
