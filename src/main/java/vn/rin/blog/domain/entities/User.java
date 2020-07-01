package vn.rin.blog.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Rin
 */
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 2436696690653745208L;
    @Id
    private String id;
    private String username;
    private String email;
    private String url;
    private String password;
    private String newPassword;
    private String role;
    private String roleId;
    private String indexRedirectURL;
    private String forwardPageStatus;
    private String guideStep;
    private String timezone;
    private String language;
    private String keyboardShortcutsStatus;
    private String subMailStatus;
    private String replyWatchArticleStatus;
    private String subMailSendTime;
    private String avatarViewMode;
    private String listPageSize;
    private String listViewMode;
    private String beezemoonStatus;
    private String pointStatus;
    private String followerStatus;
    private String followingArticleStatus;
    private String watchingArticleStatus;
    private String followingTagStatus;
    private String followingUserStatus;
    private String commentStatus;
    private String articleStatus;
    private String onlineStatus;
    private String uaStatus;
    private String notifyStatus;
    private String nickname;
    private String commentViewMode;
    private String geoStatus;
    private String updateTime;
    private String city;
    private String country;
    private String province;
    private String skin;
    private String mobileSkin;
    private String checkinTime;
    private String longestCheckinStreakStart;
    private String longestCheckinStreakEnd;
    private String longestCheckinStreak;
    private String currentCheckinStreakStart;
    private String currentCheckinStreakEnd;
    private String currentCheckinStreak;
    private String articleCount;
    private String commentCount;
    private String tagCount;
    private int status;
    private int point;
    private int usedPoint;
    private int joinPointRank;
    private int joinUsedPointRank;
    private String tags;
    private String qq;
    private String no;
    private String intro;
    private String avatarType;
    private String avatarURL;
    private String onlineFlag;
    private Date latestArticleTime;
    private Date latestCmtTime;
    private Date latestLoginTime;
    private String latestLoginIP;
    private String appRole;
}
