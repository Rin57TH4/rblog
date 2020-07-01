package vn.rin.blog.domain.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Rin
 */
@Getter
@Setter
@ToString
public class AccountProfile implements Serializable {
    private static final long serialVersionUID = 1748764917028425871L;
    private String id;
    private String username;
    private String avatar;
    private String name;
    private String email;
    private Date lastLogin;
    private int status;

    private BadgesCount badgesCount;

    public AccountProfile(long id, String username) {
        this.id = String.valueOf(id);
        this.username = username;
    }

    public AccountProfile(String id, String username) {
        this.id = id;
        this.username = username;
    }

}
