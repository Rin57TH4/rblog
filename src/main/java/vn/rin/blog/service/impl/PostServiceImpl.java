package vn.rin.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.aspect.PostStatusFilter;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.domain.data.UserVO;
import vn.rin.blog.domain.entity.*;
import vn.rin.blog.domain.event.PostUpdateEvent;
import vn.rin.blog.repositories.PostAttributeRepository;
import vn.rin.blog.repositories.PostRepository;
import vn.rin.blog.repositories.PostResourceRepository;
import vn.rin.blog.repositories.ResourceRepository;
import vn.rin.blog.service.*;
import vn.rin.blog.utils.BeanMapUtils;
import vn.rin.blog.utils.MarkdownUtils;
import vn.rin.blog.utils.PreviewTextUtils;
import vn.rin.blog.utils.ResourceLock;
import vn.rin.blog.web.formatter.JsonUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Rin
 */
@Service
@Slf4j
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostAttributeRepository postAttributeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PostResourceRepository postResourceRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    @PostStatusFilter
    public Page<PostVO> paging(Pageable pageable, int channelId, Set<Integer> excludeChannelIds) {
        Page<PostEntity> page = postRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (channelId > Constants.ZERO) {
                predicate.getExpressions().add(
                        builder.equal(root.get("channelId").as(Integer.class), channelId));
            }

            if (null != excludeChannelIds && !excludeChannelIds.isEmpty()) {
                predicate.getExpressions().add(
                        builder.not(root.get("channelId").in(excludeChannelIds)));
            }

//			predicate.getExpressions().add(
//					builder.equal(root.get("featured").as(Integer.class), Consts.FEATURED_DEFAULT));

            return predicate;
        }, pageable);

        return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    public Page<PostVO> paging4Admin(Pageable pageable, int channelId, String title) {
        Page<PostEntity> page = postRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (channelId > Constants.ZERO) {
                predicate.getExpressions().add(
                        builder.equal(root.get("channelId").as(Integer.class), channelId));
            }
            if (StringUtils.isNotBlank(title)) {
                predicate.getExpressions().add(
                        builder.like(root.get("title").as(String.class), "%" + title + "%"));
            }
            return predicate;
        }, pageable);

        return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    @PostStatusFilter
    public Page<PostVO> pagingByAuthorId(Pageable pageable, long userId) {
        Page<PostEntity> page = postRepository.findAllByAuthorId(pageable, userId);
        return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    public Page<PostVO> pagingByAuthorIdAndStatus(Pageable pageable, long userId, int status) {
        Page<PostEntity> page = postRepository.findAllByAuthorIdAndStatus(pageable, userId, status);
        return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
    }

    @Override
    @PostStatusFilter
    public List<PostVO> findLatestPosts(int maxResults) {
        return find("created", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
    }

    @Override
    @PostStatusFilter
    public List<PostVO> findHottestPosts(int maxResults) {
        return find("views", maxResults).stream().map(BeanMapUtils::copy).collect(Collectors.toList());
    }

    @Override
    @PostStatusFilter
    public Map<Long, PostVO> findMapByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }

        List<PostEntity> list = postRepository.findAllById(ids);
        Map<Long, PostVO> rets = new HashMap<>();

        HashSet<Long> uids = new HashSet<>();

        list.forEach(po -> {
            rets.put(po.getId(), BeanMapUtils.copy(po));
            uids.add(po.getAuthorId());
        });

        buildUsers(rets.values(), uids);
        return rets;
    }

    @Override
    @Transactional
    public long post(PostVO post) {
        PostEntity po = new PostEntity();

        BeanUtils.copyProperties(post, po);

        po.setCreated(new Date());
        po.setStatus(post.getStatus());

        if (StringUtils.isBlank(post.getSummary())) {
            po.setSummary(trimSummary(post.getEditor(), post.getContent()));
        } else {
            po.setSummary(post.getSummary());
        }

        String s = JsonUtils.toJson(po);
        log.info("s=>{}", s);
        postRepository.save(po);
        tagService.batchUpdate(po.getTags(), po.getId());

        String key = ResourceLock.getPostKey(po.getId());
        AtomicInteger lock = ResourceLock.getAtomicInteger(key);
        try {
            synchronized (lock) {
                PostAttributeEntity attr = new PostAttributeEntity();
                attr.setContent(post.getContent());
                attr.setEditor(post.getEditor());
                attr.setId(po.getId());
                postAttributeRepository.save(attr);

                countResource(po.getId(), null, attr.getContent());
                onPushEvent(po, vn.rin.blog.domain.event.PostUpdateEvent.ACTION_PUBLISH);
                return po.getId();
            }
        } finally {
            ResourceLock.giveUpAtomicInteger(key);
        }
    }

    @Override
    public PostVO get(long id) {
        Optional<PostEntity> po = postRepository.findById(id);
        if (po.isPresent()) {
            PostVO d = BeanMapUtils.copy(po.get());

            d.setAuthor(userService.get(d.getAuthorId()));
            d.setChannel(channelService.getById(d.getChannelId()));

            PostAttributeEntity attr = postAttributeRepository.findById(d.getId()).orElse(null);
            d.setContent(attr.getContent());
            d.setEditor(attr.getEditor());
            return d;
        }
        return null;
    }

    @Override
    @Transactional
    public void update(PostVO p) {
        Optional<PostEntity> optional = postRepository.findById(p.getId());

        if (optional.isPresent()) {
            String key = ResourceLock.getPostKey(p.getId());
            AtomicInteger lock = ResourceLock.getAtomicInteger(key);
            try {
                synchronized (lock) {
                    PostEntity po = optional.get();
                    po.setTitle(p.getTitle());
                    po.setChannelId(p.getChannelId());
                    po.setThumbnail(p.getThumbnail());
                    po.setStatus(p.getStatus());

                    if (StringUtils.isBlank(p.getSummary())) {
                        po.setSummary(trimSummary(p.getEditor(), p.getContent()));
                    } else {
                        po.setSummary(p.getSummary());
                    }

                    po.setTags(p.getTags());


                    Optional<PostAttributeEntity> attributeOptional = postAttributeRepository.findById(po.getId());
                    String originContent = "";
                    if (attributeOptional.isPresent()) {
                        originContent = attributeOptional.get().getContent();
                    }
                    PostAttributeEntity attr = new PostAttributeEntity();
                    attr.setContent(p.getContent());
                    attr.setEditor(p.getEditor());
                    attr.setId(po.getId());
                    postAttributeRepository.save(attr);

                    tagService.batchUpdate(po.getTags(), po.getId());

                    countResource(po.getId(), originContent, p.getContent());
                }
            } finally {
                ResourceLock.giveUpAtomicInteger(key);
            }
        }
    }

    @Override
    @Transactional
    public void updateFeatured(long id, int featured) {
        PostEntity po = postRepository.findById(id).orElse(null);
        int status = Constants.FEATURED_ACTIVE == featured ? Constants.FEATURED_ACTIVE : Constants.FEATURED_DEFAULT;
        po.setFeatured(status);
        postRepository.save(po);
    }

    @Override
    @Transactional
    public void updateWeight(long id, int weighted) {
        PostEntity po = postRepository.findById(id).orElse(null);

        int max = Constants.ZERO;
        if (Constants.FEATURED_ACTIVE == weighted) {
            max = postRepository.maxWeight() + 1;
        }
        po.setWeight(max);
        postRepository.save(po);
    }

    @Override
    @Transactional
    public void delete(long id, long authorId) {
        PostEntity po = postRepository.findById(id).get();

        Assert.isTrue(po.getAuthorId() == authorId, "Xác thực thất bại");

        String key = ResourceLock.getPostKey(po.getId());
        AtomicInteger lock = ResourceLock.getAtomicInteger(key);
        try {
            synchronized (lock) {
                postRepository.deleteById(id);
                postAttributeRepository.deleteById(id);
                cleanResource(po.getId());
                onPushEvent(po, PostUpdateEvent.ACTION_DELETE);
            }
        } finally {
            ResourceLock.giveUpAtomicInteger(key);
        }
    }

    @Override
    public void deleteByChannelId(long channelId) {
        List<PostEntity> posts = postRepository.findByChannelId(channelId);
        if (CollectionUtils.isNotEmpty(posts)) {
            delete(posts.stream().map(PostEntity::getId).collect(Collectors.toList()));
        }
    }

    @Override
    @Transactional
    public void delete(Collection<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<PostEntity> list = postRepository.findAllById(ids);
            list.forEach(po -> {
                String key = ResourceLock.getPostKey(po.getId());
                AtomicInteger lock = ResourceLock.getAtomicInteger(key);
                try {
                    synchronized (lock) {
                        postRepository.delete(po);
                        postAttributeRepository.deleteById(po.getId());
                        cleanResource(po.getId());
                        onPushEvent(po, PostUpdateEvent.ACTION_DELETE);
                    }
                } finally {
                    ResourceLock.giveUpAtomicInteger(key);
                }
            });
        }
    }

    @Override
    @Transactional
    public void identityViews(long id) {
        postRepository.updateViews(id, Constants.IDENTITY_STEP);
    }

    @Override
    @Transactional
    public void identityComments(long id) {
        postRepository.updateComments(id, Constants.IDENTITY_STEP);
    }

    @Override
    @Transactional
    public void favor(long userId, long postId) {
        postRepository.updateFavors(postId, Constants.IDENTITY_STEP);
        favoriteService.add(userId, postId);
    }

    @Override
    @Transactional
    public void unfavor(long userId, long postId) {
        postRepository.updateFavors(postId, Constants.DECREASE_STEP);
        favoriteService.delete(userId, postId);
    }

    @Override
    @PostStatusFilter
    public long count() {
        return postRepository.count();
    }

    @PostStatusFilter
    private List<PostEntity> find(String orderBy, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, orderBy));

        Set<Integer> excludeChannelIds = new HashSet<>();

        List<ChannelEntity> channels = channelService.findAll(Constants.STATUS_CLOSED);
        if (channels != null) {
            channels.forEach((c) -> excludeChannelIds.add(c.getId()));
        }

        Page<PostEntity> page = postRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (excludeChannelIds.size() > 0) {
                predicate.getExpressions().add(
                        builder.not(root.get("channelId").in(excludeChannelIds)));
            }
            return predicate;
        }, pageable);
        return page.getContent();
    }

    private String trimSummary(String editor, final String text) {
        if (Constants.EDITOR_MARKDOWN.endsWith(editor)) {
            return PreviewTextUtils.getText(MarkdownUtils.renderMarkdown(text), 126);
        } else {
            return PreviewTextUtils.getText(text, 126);
        }
    }

    private List<PostVO> toPosts(List<PostEntity> posts) {
        HashSet<Long> uids = new HashSet<>();
        HashSet<Integer> groupIds = new HashSet<>();

        List<PostVO> rets = posts
                .stream()
                .map(po -> {
                    uids.add(po.getAuthorId());
                    groupIds.add(po.getChannelId());
                    return BeanMapUtils.copy(po);
                })
                .collect(Collectors.toList());

        buildUsers(rets, uids);
        buildGroups(rets, groupIds);

        return rets;
    }

    private void buildUsers(Collection<PostVO> posts, Set<Long> uids) {
        Map<Long, UserVO> userMap = userService.findMapByIds(uids);
        posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
    }

    private void buildGroups(Collection<PostVO> posts, Set<Integer> groupIds) {
        Map<Integer, ChannelEntity> map = channelService.findMapByIds(groupIds);
        posts.forEach(p -> p.setChannel(map.get(p.getChannelId())));
    }

    private void onPushEvent(PostEntity post, int action) {
        PostUpdateEvent event = new PostUpdateEvent(System.currentTimeMillis());
        event.setPostId(post.getId());
        event.setUserId(post.getAuthorId());
        event.setAction(action);
        applicationContext.publishEvent(event);
    }

    private void countResource(Long postId, String originContent, String newContent) {
        if (StringUtils.isEmpty(originContent)) {
            originContent = "";
        }
        if (StringUtils.isEmpty(newContent)) {
            newContent = "";
        }

        Set<String> exists = extractImageMd5(originContent);
        Set<String> news = extractImageMd5(newContent);

        List<String> adds = ListUtils.removeAll(news, exists);
        List<String> deleteds = ListUtils.removeAll(exists, news);

        if (adds.size() > 0) {
            List<ResourceEntity> resources = resourceRepository.findByMd5In(adds);

            List<PostResourceEntity> prs = resources.stream().map(n -> {
                PostResourceEntity pr = new PostResourceEntity();
                pr.setResourceId(n.getId());
                pr.setPostId(postId);
                pr.setPath(n.getPath());
                return pr;
            }).collect(Collectors.toList());
            postResourceRepository.saveAll(prs);

            resourceRepository.updateAmount(adds, 1);
        }

        if (deleteds.size() > 0) {
            List<ResourceEntity> resources = resourceRepository.findByMd5In(deleteds);
            List<Long> rids = resources.stream().map(ResourceEntity::getId).collect(Collectors.toList());
            postResourceRepository.deleteByPostIdAndResourceIdIn(postId, rids);
            resourceRepository.updateAmount(deleteds, -1);
        }
    }

    private void cleanResource(long postId) {
        List<PostResourceEntity> list = postResourceRepository.findByPostId(postId);
        if (null == list || list.isEmpty()) {
            return;
        }
        List<Long> rids = list.stream().map(PostResourceEntity::getResourceId).collect(Collectors.toList());
        resourceRepository.updateAmountByIds(rids, -1);
        postResourceRepository.deleteByPostId(postId);
    }

    private Set<String> extractImageMd5(String text) {
        Pattern pattern = Pattern.compile("(?<=/_signature/)(.+?)(?=\\.)");
//		Pattern pattern = Pattern.compile("(?<=/_signature/)[^/]+?jpg");

        Set<String> md5s = new HashSet<>();

        Matcher originMatcher = pattern.matcher(text);
        while (originMatcher.find()) {
            String key = originMatcher.group();
//			md5s.add(key.substring(0, key.lastIndexOf(".")));
            md5s.add(key);
        }

        return md5s;
    }
}
