package vn.rin.blog.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Rin
 */
@Data
@Entity
@Table(name = "tag")
public class Tag implements Serializable {
    private static final long serialVersionUID = 2436696690653745208L;
    @Id
    private String id;
    private String tag;
    private String tags;
    private String title;
    private String uri;
    private String iconPath;
    private String css;
    private String description;
    private String referenceCount;
    private String commentCount;
    private String followerCount;
    private String linkCount;
    private String status;
    private String goodCnt;
    private String badCnt;
    private String seoTitle;
    private String seoKeywords;
    private String seoDesc;
    private double randomDouble;
    private String ad;
    private String showSideAd;

}
