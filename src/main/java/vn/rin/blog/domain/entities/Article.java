package vn.rin.blog.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Rin
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "article")
public class Article implements Serializable {
    private static final long serialVersionUID = 2436696690653745208L;
    @Id
    private String article;
    private String articles;
    private String articleTitle;
    private String articleContent;
    private String articleRewardContent;
    private String articleRewardPoint;
    private String articleTags;
    private String articleAuthorId;
    private String articleCommentCount;
    private String articleViewCount;
    private String articlePermalink;
    private String articleCreateTime;
    private String articleCreateTimeStr;
    private String articleUpdateTime;
    private String articleUpdateTimeStr;
    private String articleLatestCmtTime;
    private String articleLatestCmtTimeStr;
    private String articleLatestCmterName;
    private String articleRandomDouble;
    private String articleCommentable;
    private String articleEditorType;
    private String articleStatus;
    private String articleType;
    private String articleThankCnt;
    private String articleGoodCnt;
    private String articleBadCnt;
    private String articleCollectCnt;
    private String articleWatchCnt;
    private String redditScore;
    private String articleCity;
    private String articleIP;
    private String articleUA;
    private String articleStick;
    private String articleAnonymous;
    private String articlePerfect;
    private String articleAnonymousView;
    private String articleAudioURL;
    private String articleQnAOfferPoint;
    private String articlePushOrder;
    private String articleImg1URL;
    private String articleRevisionCount;
    private String articleShowInList;
}
