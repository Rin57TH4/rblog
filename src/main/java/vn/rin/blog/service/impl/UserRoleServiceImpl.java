package vn.rin.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rin.blog.domain.entity.RoleEntity;
import vn.rin.blog.domain.entity.UserRoleEntity;
import vn.rin.blog.repositories.ShiroUserRoleRepo;
import vn.rin.blog.service.RoleService;
import vn.rin.blog.service.UserRoleService;

import java.util.*;

/**
 * @author Rin
 */
@Service
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private ShiroUserRoleRepo userRoleRepository;
    @Autowired
    private RoleService roleService;

    @Override
    public List<Long> listRoleIds(long userId) {
        List<UserRoleEntity> list = userRoleRepository.findAllByUserId(userId);
        List<Long> roleIds = new ArrayList<>();
        if (null != list) {
            list.forEach(po -> roleIds.add(po.getRoleId()));
        }
        return roleIds;
    }

    @Override
    public List<RoleEntity> listRoles(long userId) {
        List<Long> roleIds = listRoleIds(userId);
        return new ArrayList<>(roleService.findByIds(new HashSet<>(roleIds)).values());
    }

    @Override
    public Map<Long, List<RoleEntity>> findMapByUserIds(List<Long> userIds) {
        List<UserRoleEntity> list = userRoleRepository.findAllByUserIdIn(userIds);
        Map<Long, Set<Long>> map = new HashMap<>();

        list.forEach(po -> {
            Set<Long> roleIds = map.computeIfAbsent(po.getUserId(), k -> new HashSet<>());
            roleIds.add(po.getRoleId());
        });

        Map<Long, List<RoleEntity>> ret = new HashMap<>();
        map.forEach((k, v) -> {
            ret.put(k, new ArrayList<>(roleService.findByIds(v).values()));
        });
        return ret;
    }

    @Override
    @Transactional
    public void updateRole(long userId, Set<Long> roleIds) {
        if (null == roleIds || roleIds.isEmpty()) {
            userRoleRepository.deleteByUserId(userId);
        } else {
            List<UserRoleEntity> list = userRoleRepository.findAllByUserId(userId);
            List<Long> exitIds = new ArrayList<>();

            if (null != list) {
                list.forEach(po -> {
                    if (!roleIds.contains(po.getRoleId())) {
                        userRoleRepository.delete(po);
                    } else {
                        exitIds.add(po.getRoleId());
                    }
                });
            }


            roleIds.stream().filter(id -> !exitIds.contains(id)).forEach(roleId -> {
                UserRoleEntity po = new UserRoleEntity();
                po.setUserId(userId);
                po.setRoleId(roleId);

                userRoleRepository.save(po);
            });
        }


    }
}
