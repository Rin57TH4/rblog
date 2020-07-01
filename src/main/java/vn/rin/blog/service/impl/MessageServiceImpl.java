package vn.rin.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.data.MessageVO;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.domain.data.UserVO;
import vn.rin.blog.domain.entity.MessageEntity;
import vn.rin.blog.repositories.MessageRepository;
import vn.rin.blog.service.MessageService;
import vn.rin.blog.service.PostService;
import vn.rin.blog.service.UserService;
import vn.rin.blog.utils.BeanMapUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rin
 */
@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    public Page<MessageVO> pagingByUserId(Pageable pageable, long userId) {
        Page<MessageEntity> page = messageRepository.findAllByUserId(pageable, userId);

        Set<Long> postIds = new HashSet<>();
        Set<Long> fromUserIds = new HashSet<>();


        List<MessageVO> rets = page
                .stream()
                .map(po -> {
                    if (po.getPostId() > 0) {
                        postIds.add(po.getPostId());
                    }
                    if (po.getFromId() > 0) {
                        fromUserIds.add(po.getFromId());
                    }

                    return BeanMapUtils.copy(po);
                })
                .collect(Collectors.toList());

        Map<Long, PostVO> posts = postService.findMapByIds(postIds);
        Map<Long, UserVO> fromUsers = userService.findMapByIds(fromUserIds);

        rets.forEach(n -> {
            if (n.getPostId() > 0) {
                n.setPost(posts.get(n.getPostId()));
            }
            if (n.getFromId() > 0) {
                n.setFrom(fromUsers.get(n.getFromId()));
            }
        });

        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void send(MessageVO message) {
        if (message == null || message.getUserId() <= 0 || message.getFromId() <= 0) {
            return;
        }

        MessageEntity po = new MessageEntity();
        BeanUtils.copyProperties(message, po);
        po.setCreated(new Date());

        messageRepository.save(po);
    }

    @Override
    public int unread4Me(long userId) {
        return messageRepository.countByUserIdAndStatus(userId, Constants.UNREAD);
    }

    @Override
    @Transactional
    public void readed4Me(long userId) {
        messageRepository.updateReadedByUserId(userId);
    }

    @Override
    @Transactional
    public int deleteByPostId(long postId) {
        return messageRepository.deleteByPostId(postId);
    }
}
