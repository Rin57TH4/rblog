package vn.rin.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vn.rin.blog.domain.data.FavoriteVO;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.domain.entity.FavoriteEntity;
import vn.rin.blog.repositories.FavoriteRepository;
import vn.rin.blog.service.FavoriteService;
import vn.rin.blog.service.PostService;
import vn.rin.blog.utils.BeanMapUtils;

import java.util.*;

/**
 * @author Rin
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private PostService postService;

    @Override
    public Page<FavoriteVO> pagingByUserId(Pageable pageable, long userId) {
        Page<FavoriteEntity> page = favoriteRepository.findAllByUserId(pageable, userId);

        List<FavoriteVO> rets = new ArrayList<>();
        Set<Long> postIds = new HashSet<>();
        for (FavoriteEntity po : page.getContent()) {
            rets.add(BeanMapUtils.copy(po));
            postIds.add(po.getPostId());
        }

        if (postIds.size() > 0) {
            Map<Long, PostVO> posts = postService.findMapByIds(postIds);

            for (FavoriteVO t : rets) {
                PostVO p = posts.get(t.getPostId());
                t.setPost(p);
            }
        }
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void add(long userId, long postId) {
        FavoriteEntity po = favoriteRepository.findByUserIdAndPostId(userId, postId);

        Assert.isNull(po, "Bạn đã thích bài viết này");

        po = new FavoriteEntity();
        po.setUserId(userId);
        po.setPostId(postId);
        po.setCreated(new Date());

        favoriteRepository.save(po);
    }

    @Override
    @Transactional
    public void delete(long userId, long postId) {
        FavoriteEntity po = favoriteRepository.findByUserIdAndPostId(userId, postId);
        Assert.notNull(po, "Không thích bài viết này");
        favoriteRepository.delete(po);
    }

    @Override
    @Transactional
    public void deleteByPostId(long postId) {
        int rows = favoriteRepository.deleteByPostId(postId);
        log.info("favoriteRepository delete {}", rows);
    }

}
