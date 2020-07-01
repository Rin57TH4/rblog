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
    private String id;
    private String article;
    private String articles;
    private String title;
    private String content;
    private String rewardContent;
    private String rewardPoint;
    private String tags;
    private String authorId;
    private String commentCount;
    private String viewCount;
    private String permalink;
    private String createTime;
    private String createTimeStr;
    private String updateTime;
    private String updateTimeStr;
    private String latestCmtTime;
    private String latestCmtTimeStr;
    private String latestCmterName;
    private String randomDouble;
    private String commentable;
    private String editorType;
    private String status;
    private String type;
    private String thankCnt;
    private String goodCnt;
    private String badCnt;
    private String collectCnt;
    private String watchCnt;
    private String redditScore;
    private String city;
    private String ip;
    private String ua;
    private String stick;
    private String anonymous;
    private String perfect;
    private String anonymousView;
    private String audioURL;
    private String qnAOfferPoint;
    private String pushOrder;
    private String img1URL;
    private String revisionCount;
    private String showInList;
}
