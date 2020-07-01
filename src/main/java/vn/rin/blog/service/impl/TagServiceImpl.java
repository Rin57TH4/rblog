package vn.rin.blog.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.PostTagVO;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.domain.data.TagVO;
import vn.rin.blog.domain.entity.PostTagEntity;
import vn.rin.blog.domain.entity.TagEntity;
import vn.rin.blog.repositories.PostTagRepository;
import vn.rin.blog.repositories.TagRepository;
import vn.rin.blog.service.PostService;
import vn.rin.blog.service.TagService;
import vn.rin.blog.utils.BeanMapUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rin
 */
@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostTagRepository postTagRepository;
    @Autowired
    private PostService postService;

    @Override
    public Page<TagVO> pagingQueryTags(Pageable pageable) {
        Page<TagEntity> page = tagRepository.findAll(pageable);

        Set<Long> postIds = new HashSet<>();
        List<TagVO> rets = page.getContent().stream().map(po -> {
            postIds.add(po.getLatestPostId());
            return BeanMapUtils.copy(po);
        }).collect(Collectors.toList());

        Map<Long, PostVO> posts = postService.findMapByIds(postIds);
        rets.forEach(n -> n.setPost(posts.get(n.getLatestPostId())));
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public Page<PostTagVO> pagingQueryPosts(Pageable pageable, String tagName) {
        TagEntity tag = tagRepository.findByName(tagName);
        Assert.notNull(tag, "Nhãn không tồn tại");
        Page<PostTagEntity> page = postTagRepository.findAllByTagId(pageable, tag.getId());

        Set<Long> postIds = new HashSet<>();
        List<PostTagVO> rets = page.getContent().stream().map(po -> {
            postIds.add(po.getPostId());
            return BeanMapUtils.copy(po);
        }).collect(Collectors.toList());

        Map<Long, PostVO> posts = postService.findMapByIds(postIds);
        rets.forEach(n -> n.setPost(posts.get(n.getPostId())));
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void batchUpdate(String names, long latestPostId) {
        if (StringUtils.isBlank(names.trim())) {
            return;
        }

        String[] ns = names.split(Constants.SEPARATOR);
        Date current = new Date();
        for (String n : ns) {
            String name = n.trim();
            if (StringUtils.isBlank(name)) {
                continue;
            }

            TagEntity po = tagRepository.findByName(name);
            if (po != null) {
                PostTagEntity pt = postTagRepository.findByPostIdAndTagId(latestPostId, po.getId());
                if (null != pt) {
                    pt.setWeight(System.currentTimeMillis());
                    postTagRepository.save(pt);
                    continue;
                }
                po.setPosts(po.getPosts() + 1);
                po.setUpdated(current);
            } else {
                po = new TagEntity();
                po.setName(name);
                po.setCreated(current);
                po.setUpdated(current);
                po.setPosts(1);
            }

            po.setLatestPostId(latestPostId);
            tagRepository.save(po);

            PostTagEntity pt = new PostTagEntity();
            pt.setPostId(latestPostId);
            pt.setTagId(po.getId());
            pt.setWeight(System.currentTimeMillis());
            postTagRepository.save(pt);
        }
    }

    @Override
    @Transactional
    public void deteleMappingByPostId(long postId) {
        Set<Long> tagIds = postTagRepository.findTagIdByPostId(postId);
        if (CollectionUtils.isNotEmpty(tagIds)) {
            tagRepository.decrementPosts(tagIds);
        }
        postTagRepository.deleteByPostId(postId);
    }
}
