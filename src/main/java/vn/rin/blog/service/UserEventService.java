package vn.rin.blog.service;

import org.springframework.cache.annotation.CacheEvict;
import vn.rin.blog.common.Constants;

import java.util.Set;

/**
 * @author Rin
 */
public interface UserEventService {

    @CacheEvict(value = {Constants.CACHE_USER, Constants.CACHE_POST}, allEntries = true)
    void identityPost(Long userId, boolean plus);

    @CacheEvict(value = {Constants.CACHE_USER, Constants.CACHE_POST}, allEntries = true)
    void identityComment(Long userId, boolean plus);

    @CacheEvict(value = {Constants.CACHE_USER, Constants.CACHE_POST}, allEntries = true)
    void identityComment(Set<Long> userIds, boolean plus);
}
