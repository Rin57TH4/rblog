package vn.rin.blog.service;

import vn.rin.blog.domain.entity.ChannelEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Rin
 */
public interface ChannelService {
    List<ChannelEntity> findAll(int status);

    Map<Integer, ChannelEntity> findMapByIds(Collection<Integer> ids);

    ChannelEntity getById(int id);

    void update(ChannelEntity channel);

    void updateWeight(int id, int weighted);

    void delete(int id);

    long count();
}
