package vn.rin.blog.cache;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import vn.rin.blog.common.LightB3log;
import vn.rin.blog.domain.entities.Tag;
import vn.rin.blog.domain.kit.TagKit;
import vn.rin.blog.jpa.Specifications;
import vn.rin.blog.repositories.TagRepo;
import vn.rin.blog.utils.BeanUtil;
import vn.rin.blog.utils.PageUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rin
 */
@Slf4j
@Component
public class TagCache {

    @Autowired
    private TagRepo tagRepo;
    /**
     * Icon tags.
     */
    private static final List<Tag> ICON_TAGS = new ArrayList<>();

    /**
     * New tags.
     */
    private static final List<Tag> NEW_TAGS = new ArrayList<>();

    /**
     * All tags.
     */
    private static final List<Tag> TAGS = new ArrayList<>();

    /**
     * &lt;title, URI&gt;
     */
    private static final Map<String, String> TITLE_URIS = new ConcurrentHashMap<>();

    /**
     * &lt;id, tag&gt;
     */
    private static final Map<String, Tag> CACHE = new ConcurrentHashMap<>();

    /**
     * Gets a tag by the specified tag id.
     *
     * @param id the specified tag id
     * @return tag, returns {@code null} if not found
     */
    public Tag getTag(final String id) {
        final Tag tag = CACHE.get(id);
        if (null == tag) {
            return null;
        }

        final Tag ret = BeanUtil.copyProperties(tag, Tag.class);

        TITLE_URIS.put(ret.getTitle(), ret.getUri());

        return ret;
    }

    /**
     * Adds or updates the specified tag.
     *
     * @param tag the specified tag
     */
    public void putTag(final Tag tag) {
        CACHE.put(tag.getId(), BeanUtil.copyProperties(tag, Tag.class));

        TITLE_URIS.put(tag.getTitle(), tag.getUri());
    }

    /**
     * Removes a tag by the specified tag id.
     *
     * @param id the specified tag id
     */
    public void removeTag(final String id) {
        final Tag tag = CACHE.get(id);
        if (null == tag) {
            return;
        }

        CACHE.remove(id);

        TITLE_URIS.remove(tag.getTitle());
    }

    /**
     * Gets a tag URI with the specified tag title.
     *
     * @param title the specified tag title
     * @return tag URI, returns {@code null} if not found
     */
    public String getURIByTitle(final String title) {
        return TITLE_URIS.get(title);
    }

    /**
     * Gets new tags with the specified fetch size.
     *
     * @return new tags
     */
    public List<Tag> getNewTags() {
        if (NEW_TAGS.isEmpty()) {
            return Collections.emptyList();
        }


        return BeanUtil.copyProperties(NEW_TAGS, Tag.class);
    }

    /**
     * Gets icon tags with the specified fetch size.
     *
     * @param fetchSize the specified fetch size
     * @return icon tags
     */
    public List<Tag> getIconTags(final int fetchSize) {
        if (ICON_TAGS.isEmpty()) {
            return Collections.emptyList();
        }

        final int end = fetchSize >= ICON_TAGS.size() ? ICON_TAGS.size() : fetchSize;

        return BeanUtil.copyProperties(ICON_TAGS.subList(0, end), Tag.class);
    }

    /**
     * Gets all tags.
     *
     * @return all tags
     */
    public List<Tag> getTags() {
        if (TAGS.isEmpty()) {
            return Collections.emptyList();
        }

        return BeanUtil.copyProperties(TAGS, Tag.class);
    }

    /**
     * Loads all tags.
     */
    public void loadTags() {
        loadAllTags();
        loadIconTags();
        loadNewTags();
    }

    /**
     * Loads new tags.
     */
    private void loadNewTags() {
        List<Tag> tags = tagRepo.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            predicate.getExpressions().add(
                    builder.greaterThan(root.get("referenceCount"), 0)
            );
            return predicate;
        }, PageUtils.wrapPageable(LightB3log.TAGS_CNT)).getContent();

        NEW_TAGS.clear();
        NEW_TAGS.addAll(tags);

    }

    /**
     * Loads icon tags.
     */
    private void loadIconTags() {
        List<Tag> tags = tagRepo.findAll(Specifications.<Tag>and()
                .isNotEmpty("iconPath")
                .eq("status", TagKit.TAG_STATUS_C_VALID)
                .build(), PageUtils.wrapPageable(Sort.by(Sort.Direction.ASC, "randomDouble"))).getContent();

        final List<Tag> toUpdateTags = new ArrayList<>();

        for (final Tag tag : tags) {
            Tag t = BeanUtil.copyProperties(tag, Tag.class);
            t.setRandomDouble(Math.random());
            toUpdateTags.add(BeanUtil.copyProperties(tag, Tag.class));
        }

        if (!CollectionUtils.isEmpty(toUpdateTags))
            tagRepo.saveAll(toUpdateTags);

        for (final Tag tag : tags) {
            TagKit.fillDescription(tag);
//                tag.put(Tag.TAG_T_TITLE_LOWER_CASE, tag.optString(Tag.TAG_TITLE).toLowerCase());
        }

        ICON_TAGS.clear();
        ICON_TAGS.addAll(tags);
    }

    /**
     * Loads all tags.
     */
    public void loadAllTags() {
        final List<Tag> tags = tagRepo.findByStatus(TagKit.TAG_STATUS_C_VALID);
        final Iterator<Tag> iterator = tags.iterator();
        while (iterator.hasNext()) {
            final Tag tag = iterator.next();

            String title = tag.getTitle();
            if ("".equals(title)
                    || StringUtils.contains(title, " ")
                    || StringUtils.contains(title, "　")) { // filter legacy data
                iterator.remove();
                continue;
            }

            if (!TagKit.containsWhiteListTags(title)) {
                if (!TagKit.TAG_TITLE_PATTERN.matcher(title).matches() || title.length() > TagKit.MAX_TAG_TITLE_LENGTH) {
                    iterator.remove();
                    continue;
                }
            }

            TagKit.fillDescription(tag);
//                tag.put(Tag.TAG_T_TITLE_LOWER_CASE, tag.optString(Tag.TAG_TITLE).toLowerCase());
        }

        tags.sort((t1, t2) -> {
            final String u1Title = t1.getTitle().toLowerCase();
            final String u2Title = t2.getTitle().toLowerCase();

            return u1Title.compareTo(u2Title);
        });

        TAGS.clear();
        TAGS.addAll(tags);

        TITLE_URIS.clear();
        for (final Tag tag : tags) {
            TITLE_URIS.put(tag.getTitle(), tag.getUri());
        }
    }
}
