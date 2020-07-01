package vn.rin.blog.domain.entity;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.search.annotations.*;

import javax.persistence.Index;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Rin
 */
@Entity
@Table(name = "post", indexes = {
        @Index(name = "IK_CHANNEL_ID", columnList = "channel_id")
})
@FilterDefs({
        @FilterDef(name = "POST_STATUS_FILTER", defaultCondition = "status = 0")})
@Filters({@Filter(name = "POST_STATUS_FILTER")})
@Indexed(index = "post")
@Analyzer(impl = SmartChineseAnalyzer.class)
public class PostEntity implements Serializable {
    private static final long serialVersionUID = 7144425803920583495L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SortableField
    @NumericField
    private long id;

    @Field
    @NumericField
    @Column(name = "channel_id", length = 5)
    private int channelId;

    @Field
    @Column(name = "title", length = 500)
    private String title;

    @Field
    @Column(length = 140)
    private String summary;

    @Column(length = 128)
    private String thumbnail;

    @Field
    @Column(length = 64)
    private String tags;

    @Field
    @NumericField
    @Column(name = "author_id")
    private long authorId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    private int favors;

    private int comments;

    private int views;

    private int status;

    private int featured;

    private int weight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getFavors() {
        return favors;
    }

    public void setFavors(int favors) {
        this.favors = favors;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
