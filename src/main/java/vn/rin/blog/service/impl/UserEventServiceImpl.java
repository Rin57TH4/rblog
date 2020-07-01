package vn.rin.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rin.blog.common.Constants;
import vn.rin.blog.repositories.UserRepo;
import vn.rin.blog.service.UserEventService;

import java.util.Collections;
import java.util.Set;

/**
 * @author Rin
 */
@Service
@Transactional
public class UserEventServiceImpl implements UserEventService {
    @Autowired
    private UserRepo userRepository;

    @Override
    public void identityPost(Long userId, boolean plus) {
        userRepository.updatePosts(userId, (plus) ? Constants.IDENTITY_STEP : Constants.DECREASE_STEP);
    }

    @Override
    public void identityComment(Long userId, boolean plus) {
        userRepository.updateComments(Collections.singleton(userId), (plus) ? Constants.IDENTITY_STEP : Constants.DECREASE_STEP);
    }

    @Override
    public void identityComment(Set<Long> userIds, boolean plus) {
        userRepository.updateComments(userIds, (plus) ? Constants.IDENTITY_STEP : Constants.DECREASE_STEP);
    }

}
