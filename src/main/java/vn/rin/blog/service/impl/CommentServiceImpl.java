package vn.rin.blog.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vn.rin.blog.domain.data.CommentVO;
import vn.rin.blog.domain.entity.CommentEntity;
import vn.rin.blog.repositories.CommentRepository;
import vn.rin.blog.service.CommentService;
import vn.rin.blog.service.PostService;
import vn.rin.blog.service.UserEventService;
import vn.rin.blog.service.UserService;
import vn.rin.blog.service.complementor.CommentComplementor;

import java.util.*;

/**
 * @author Rin
 */
@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserEventService userEventService;
    @Autowired
    private PostService postService;

    @Override
    public Page<CommentVO> paging4Admin(Pageable pageable) {
        Page<CommentEntity> page = commentRepository.findAll(pageable);
        List<CommentVO> rets = CommentComplementor.of(page.getContent())
                .flutBuildUser()
                .getComments();
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public Page<CommentVO> pagingByAuthorId(Pageable pageable, long authorId) {
        Page<CommentEntity> page = commentRepository.findAllByAuthorId(pageable, authorId);

        List<CommentVO> rets = CommentComplementor.of(page.getContent())
                .flutBuildUser()
                .flutBuildParent()
                .flutBuildPost()
                .getComments();
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public Page<CommentVO> pagingByPostId(Pageable pageable, long postId) {
        Page<CommentEntity> page = commentRepository.findAllByPostId(pageable, postId);

        List<CommentVO> rets = CommentComplementor.of(page.getContent())
                .flutBuildUser()
                .flutBuildParent()
                .getComments();
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public List<CommentVO> findLatestComments(int maxResults) {
        Pageable pageable = PageRequest.of(0, maxResults, Sort.by(Sort.Direction.DESC, "id"));
        Page<CommentEntity> page = commentRepository.findAll(pageable);
        return CommentComplementor.of(page.getContent())
                .flutBuildUser()
                .getComments();
    }

    @Override
    public Map<Long, CommentVO> findByIds(Set<Long> ids) {
        List<CommentEntity> list = commentRepository.findAllById(ids);
        return CommentComplementor.of(list)
                .flutBuildUser()
                .toMap();
    }

    @Override
    public CommentEntity findById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public long post(CommentVO comment) {
        CommentEntity po = new CommentEntity();

        po.setAuthorId(comment.getAuthorId());
        po.setPostId(comment.getPostId());
        po.setContent(comment.getContent());
        po.setCreated(new Date());
        po.setPid(comment.getPid());
        commentRepository.save(po);

        userEventService.identityComment(comment.getAuthorId(), true);
        return po.getId();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(List<Long> ids) {
        List<CommentEntity> list = commentRepository.removeByIdIn(ids);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(po -> {
                userEventService.identityComment(po.getAuthorId(), false);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(long id, long authorId) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            CommentEntity po = optional.get();
            Assert.isTrue(po.getAuthorId() == authorId, "Xác thực thất bại");
            commentRepository.deleteById(id);

            userEventService.identityComment(authorId, false);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteByPostId(long postId) {
        List<CommentEntity> list = commentRepository.removeByPostId(postId);
        if (CollectionUtils.isNotEmpty(list)) {
            Set<Long> userIds = new HashSet<>();
            list.forEach(n -> userIds.add(n.getAuthorId()));
            userEventService.identityComment(userIds, false);
        }
    }

    @Override
    public long count() {
        return commentRepository.count();
    }

    @Override
    public long countByAuthorIdAndPostId(long authorId, long toId) {
        return commentRepository.countByAuthorIdAndPostId(authorId, toId);
    }

}
