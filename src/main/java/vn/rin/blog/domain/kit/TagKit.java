package vn.rin.blog.domain.kit;

import org.apache.commons.lang3.StringUtils;
import vn.rin.blog.cache.TagCache;
import vn.rin.blog.common.LightB3log;
import vn.rin.blog.domain.entities.Tag;
import vn.rin.blog.utils.SpringUtils;
import vn.rin.blog.utils.Strings;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Rin
 */
public class TagKit {
    //// Tag type constants
    /**
     * Tag type - creator.
     */
    public static final int TAG_TYPE_C_CREATOR = 0;

    /**
     * Tag type - article.
     */
    public static final int TAG_TYPE_C_ARTICLE = 1;

    /**
     * Tag type - user self.
     */
    public static final int TAG_TYPE_C_USER_SELF = 2;

    // Status constants
    /**
     * Tag status - valid.
     */
    public static final int TAG_STATUS_C_VALID = 0;

    /**
     * Tag status - invalid.
     */
    public static final int TAG_STATUS_C_INVALID = 1;

    // Tag title constants
    /**
     * Title - Sandbox.
     */
    public static final String TAG_TITLE_C_SANDBOX = "Sandbox";

    // Validation
    /**
     * Max tag title length.
     */
    public static final int MAX_TAG_TITLE_LENGTH = 12;

    /**
     * Max tag count.
     */
    public static final int MAX_TAG_COUNT = 4;

    /**
     * Tag title pattern string.
     */
    public static final String TAG_TITLE_PATTERN_STR = "[\\u4e00-\\u9fa5\\w&#+\\-.]+";

    /**
     * Tag title pattern.
     */
    public static final Pattern TAG_TITLE_PATTERN = Pattern.compile(TAG_TITLE_PATTERN_STR);

    /**
     * Normalized tag title mappings.
     */
    private static final Map<String, Set<String>> NORMALIZE_MAPPINGS = new HashMap<>();

    static {
        NORMALIZE_MAPPINGS.put("JavaScript", new HashSet<>(Arrays.asList("JS")));
        NORMALIZE_MAPPINGS.put("Elasticsearch", new HashSet<>(Arrays.asList("ES搜索引擎", "ES搜索", "ES")));
        NORMALIZE_MAPPINGS.put("golang", new HashSet<>(Arrays.asList("Go", "Go语言")));
        NORMALIZE_MAPPINGS.put("线程", new HashSet<>(Arrays.asList("多线程", "Thread")));
        NORMALIZE_MAPPINGS.put("Vue.js", new HashSet<>(Arrays.asList("Vue")));
        NORMALIZE_MAPPINGS.put("Node.js", new HashSet<>(Arrays.asList("NodeJS")));
    }

    /**
     * Uses the head tags.
     *
     * @param tagStr the specified tags
     * @param num    the specified used number
     * @return head tags
     */
    public static String useHead(final String tagStr, final int num) {
        final String[] tags = tagStr.split(",");
        if (tags.length <= num) {
            return tagStr;
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(tags[i]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * Formats the specified tags.
     * <ul>
     * <li>Trims every tag</li>
     * <li>Deduplication</li>
     * </ul>
     *
     * @param tagStr the specified tags
     * @return formatted tags string
     */
    public static String formatTags(final String tagStr) {
        final String tagStr1 = tagStr.replaceAll("\\s+", "").replaceAll("，", ",").replaceAll("、", ",").
                replaceAll("；", ",").replaceAll(";", ",");
        String[] tagTitles = tagStr1.split(",");

        tagTitles = Strings.trimAll(tagTitles);

        // deduplication
        final Set<String> titles = new LinkedHashSet<>();
        for (final String tagTitle : tagTitles) {
            if (!exists(titles, tagTitle)) {
                titles.add(tagTitle);
            }
        }

        tagTitles = titles.toArray(new String[0]);

        int count = 0;
        final StringBuilder tagsBuilder = new StringBuilder();
        for (final String tagTitle : tagTitles) {
            String title = tagTitle.trim();
            if (StringUtils.isBlank(title)) {
                continue;
            }

            if (containsWhiteListTags(title)) {
                tagsBuilder.append(title).append(",");
                count++;

                if (count >= MAX_TAG_COUNT) {
                    break;
                }
                continue;
            }

            if (StringUtils.length(title) > MAX_TAG_TITLE_LENGTH) {
                continue;
            }

            if (!TAG_TITLE_PATTERN.matcher(title).matches()) {
                continue;
            }

            title = normalize(title);
            tagsBuilder.append(title).append(",");
            count++;

            if (count >= MAX_TAG_COUNT) {
                break;
            }
        }
        if (tagsBuilder.length() > 0) {
            tagsBuilder.deleteCharAt(tagsBuilder.length() - 1);
        }

        return tagsBuilder.toString();
    }

    /**
     * Checks the specified tag string whether contains the reserved tags.
     *
     * @param tagStr the specified tag string
     * @return {@code true} if it contains, returns {@code false} otherwise
     */
    public static boolean containsReservedTags(final String tagStr) {
        for (final String reservedTag : LightB3log.RESERVED_TAGS) {
            if (StringUtils.containsIgnoreCase(tagStr, reservedTag)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks the specified tag string whether contains the white list tags.
     *
     * @param tagStr the specified tag string
     * @return {@code true} if it contains, returns {@code false} otherwise
     */
    public static boolean containsWhiteListTags(final String tagStr) {
        for (final String whiteListTag : LightB3log.WHITE_LIST_TAGS) {
            if (StringUtils.equalsIgnoreCase(tagStr, whiteListTag)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks the specified title exists in the specified title set.
     *
     * @param titles the specified title set
     * @param title  the specified title to check
     * @return {@code true} if exists, returns {@code false} otherwise
     */
    private static boolean exists(final Set<String> titles, final String title) {
        for (final String setTitle : titles) {
            if (setTitle.equalsIgnoreCase(title)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Normalizes the specified title. For example, Normalizes "JS" to "JavaScript.
     *
     * @param title the specified title
     * @return normalized title
     */
    private static String normalize(final String title) {
        TagCache cache = SpringUtils.getBean(TagCache.class);
        final List<Tag> iconTags = cache.getIconTags(Integer.MAX_VALUE);
        Collections.sort(iconTags, (t1, t2) -> {
            final String u1Title = t1.getTitle().toLowerCase();
            final String u2Title = t2.getTitle().toLowerCase();

            return u2Title.length() - u1Title.length();
        });

        for (final Tag iconTag : iconTags) {
            final String iconTagTitle = iconTag.getTitle();
            if (iconTagTitle.length() < 2) {
                break;
            }

            if (StringUtils.containsIgnoreCase(title, iconTagTitle)) {
                return iconTagTitle;
            }
        }

        final List<Tag> allTags = cache.getTags();
        Collections.sort(allTags, (t1, t2) -> {
            final String u1Title = t1.getTitle().toLowerCase();
            final String u2Title = t2.getTitle().toLowerCase();

            return u2Title.length() - u1Title.length();
        });

        for (final Map.Entry<String, Set<String>> entry : NORMALIZE_MAPPINGS.entrySet()) {
            final Set<String> oddTitles = entry.getValue();
            for (final String oddTitle : oddTitles) {
                if (StringUtils.equalsIgnoreCase(title, oddTitle)) {
                    return entry.getKey();
                }
            }
        }

        for (final Tag tag : allTags) {
            final String tagTitle = tag.getTitle();
            final String tagURI = tag.getUri();
            if (StringUtils.equalsIgnoreCase(title, tagURI) || StringUtils.equalsIgnoreCase(title, tagTitle)) {
                return tag.getTitle();
            }
        }

        return title;
    }

    /**
     * Fills the description for the specified tag.
     *
     * @param tag the specified tag
     */
    public static void fillDescription(final Tag tag) {
        if (null == tag) {
            return;
        }
//
//        String description = tag.getDescription();
//        String descriptionText = tag.getTitle();
//        if (StringUtils.isNotBlank(description)) {
//            description = Emotions.convert(description);
//            description = Markdowns.toHTML(description);
//
//            tag.put(Tag.TAG_DESCRIPTION, description);
//            descriptionText = Jsoup.parse(description).text();
//        }
//        tag.put(Tag.TAG_T_DESCRIPTION_TEXT, descriptionText);
    }
}
