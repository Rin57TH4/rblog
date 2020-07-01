package vn.rin.blog.domain.kit;

import org.apache.commons.lang3.StringUtils;
import vn.rin.blog.domain.entities.Article;

/**
 * @author Rin
 */
public class ArticleKit {
    // Anonymous constants
    /**
     * Article anonymous - public.
     */
    public static final int ARTICLE_ANONYMOUS_C_PUBLIC = 0;

    /**
     * Article anonymous - anonymous.
     */
    public static final int ARTICLE_ANONYMOUS_C_ANONYMOUS = 1;

    // Perfect constants
    /**
     * Article perfect - not perfect.
     */
    public static final int ARTICLE_PERFECT_C_NOT_PERFECT = 0;

    /**
     * Article perfect - perfect.
     */
    public static final int ARTICLE_PERFECT_C_PERFECT = 1;

    // Anonymous view constants
    /**
     * Article anonymous view - use global.
     */
    public static final int ARTICLE_ANONYMOUS_VIEW_C_USE_GLOBAL = 0;

    /**
     * Article anonymous view - not allow.
     */
    public static final int ARTICLE_ANONYMOUS_VIEW_C_NOT_ALLOW = 1;

    /**
     * Article anonymous view - allow.
     */
    public static final int ARTICLE_ANONYMOUS_VIEW_C_ALLOW = 2;

    // Status constants
    /**
     * Article status - valid.
     */
    public static final int ARTICLE_STATUS_C_VALID = 0;

    /**
     * Article status - invalid.
     */
    public static final int ARTICLE_STATUS_C_INVALID = 1;

    /**
     * Article status - locked.
     */
    public static final int ARTICLE_STATUS_C_LOCKED = 2;

    // Type constants
    /**
     * Article type - normal.
     */
    public static final int ARTICLE_TYPE_C_NORMAL = 0;

    /**
     * Article type - discussion.
     */
    public static final int ARTICLE_TYPE_C_DISCUSSION = 1;

    /**
     * Article type - city broadcast.
     */
    public static final int ARTICLE_TYPE_C_CITY_BROADCAST = 2;

    /**
     * Article type - <a href="https://hacpai.com/article/1441942422856">thought</a>.
     */
    public static final int ARTICLE_TYPE_C_THOUGHT = 3;

    /**
     * Article type - <a href="https://github.com/b3log/symphony/issues/486">QnA</a>.
     */
    public static final int ARTICLE_TYPE_C_QNA = 5;

    // Show in list constants
    /**
     * Article show in list - not.
     */
    public static final Integer ARTICLE_SHOW_IN_LIST_C_NOT = 0;

    /**
     * Article show in list - yes.
     */
    public static final Integer ARTICLE_SHOW_IN_LIST_C_YES = 1;

    /**
     * Checks the specified article1 is different from the specified article2.
     *
     * @param a1 the specified article1
     * @param a2 the specified article2
     * @return {@code true} if they are different, otherwise returns {@code false}
     */
    public static boolean isDifferent(final Article a1, final Article a2) {
        final String title1 = a1.getArticleTitle();
        final String title2 = a2.getArticleTitle();
        if (!StringUtils.equalsIgnoreCase(title1, title2)) {
            return true;
        }

        final String tags1 = a1.getArticleTags();
        final String tags2 = a2.getArticleTags();
        if (!StringUtils.equalsIgnoreCase(tags1, tags2)) {
            return true;
        }

        final String content1 = a1.getArticleContent();
        final String content2 = a2.getArticleContent();
        return !StringUtils.equalsIgnoreCase(content1, content2);
    }

    /**
     * Checks the specified article type is whether invalid.
     *
     * @param articleType the specified article type
     * @return {@code true} if it is invalid, otherwise returns {@code false}
     */
    public static boolean isInvalidArticleType(final int articleType) {
        return articleType < 0 || articleType > Article.ARTICLE_TYPE_C_QNA;
    }

}
