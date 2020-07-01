package vn.rin.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rin.blog.domain.entity.PermissionEntity;
import vn.rin.blog.domain.entity.RolePermissionEntity;
import vn.rin.blog.repositories.PermissionRepository;
import vn.rin.blog.repositories.ShiroRolePermissionRepo;
import vn.rin.blog.service.RolePermissionService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rin
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ShiroRolePermissionRepo rolePermissionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionEntity> findPermissions(long roleId) {
        List<RolePermissionEntity> rps = rolePermissionRepository.findAllByRoleId(roleId);

        List<PermissionEntity> rets = null;
        if (rps != null && rps.size() > 0) {
            Set<Long> pids = new HashSet<>();
            rps.forEach(rp -> pids.add(rp.getPermissionId()));
            rets = permissionRepository.findAllById(pids);
        }
        return rets;
    }

    @Override
    @Transactional
    public void deleteByRoleId(long roleId) {
        rolePermissionRepository.deleteByRoleId(roleId);
    }

    @Override
    @Transactional
    public void add(Set<RolePermissionEntity> rolePermissions) {
        rolePermissionRepository.saveAll(rolePermissions);
    }
}
