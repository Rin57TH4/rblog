package vn.rin.blog.domain.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.rin.blog.domain.entity.RoleEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Rin
 */
@Getter
@Setter
@ToString
public class UserVO implements Serializable {
    private static final long serialVersionUID = -4897793501172496085L;
    private long id;
    private String username;
    private String password;
    private String avatar;
    private String name;
    private String email;
    private int posts;
    private int comments;
    private Date created;
    private Date lastLogin;
    private String signature;
    private int status;
    private List<RoleEntity> roles = new ArrayList<>();

}
