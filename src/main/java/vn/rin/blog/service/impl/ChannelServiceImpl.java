package vn.rin.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rin.blog.common.Constants;
import vn.rin.blog.domain.entity.ChannelEntity;
import vn.rin.blog.repositories.ChannelRepo;
import vn.rin.blog.service.ChannelService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rin
 */
@Service
@Transactional(readOnly = true)
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelRepo channelRepo;

    @Override
    public List<ChannelEntity> findAll(int status) {
        Sort sort = Sort.by(Sort.Direction.DESC, "weight", "id");
        List<ChannelEntity> list;
        if (status > Constants.IGNORE) {
            list = channelRepo.findAllByStatus(status, sort);
        } else {
            list = channelRepo.findAll(sort);
        }
        return list;
    }

    @Override
    public Map<Integer, ChannelEntity> findMapByIds(Collection<Integer> ids) {
        List<ChannelEntity> list = channelRepo.findAllById(ids);
        if (null == list) {
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(ChannelEntity::getId, n -> n));
    }

    @Override
    public ChannelEntity getById(int id) {
        return channelRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void update(ChannelEntity channel) {
        Optional<ChannelEntity> optional = channelRepo.findById(channel.getId());
        ChannelEntity po = optional.orElse(new ChannelEntity());
        BeanUtils.copyProperties(channel, po);
        channelRepo.save(po);
    }

    @Override
    @Transactional
    public void updateWeight(int id, int weighted) {
        ChannelEntity po = channelRepo.findById(id).orElse(null);

        int max = Constants.ZERO;
        if (Constants.FEATURED_ACTIVE == weighted) {
            max = channelRepo.maxWeight() + 1;
        }
        po.setWeight(max);
        channelRepo.save(po);
    }

    @Override
    @Transactional
    public void delete(int id) {
        channelRepo.deleteById(id);
    }

    @Override
    public long count() {
        return channelRepo.count();
    }

}
