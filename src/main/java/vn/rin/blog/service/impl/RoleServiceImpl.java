package vn.rin.blog.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vn.rin.blog.domain.entity.PermissionEntity;
import vn.rin.blog.domain.entity.RoleEntity;
import vn.rin.blog.domain.entity.RolePermissionEntity;
import vn.rin.blog.domain.entity.UserRoleEntity;
import vn.rin.blog.repositories.ShiroRoleRepo;
import vn.rin.blog.repositories.ShiroUserRoleRepo;
import vn.rin.blog.service.RolePermissionService;
import vn.rin.blog.service.RoleService;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author Rin
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private ShiroRoleRepo roleRepository;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private ShiroUserRoleRepo userRoleRepository;

    @Override
    public Page<RoleEntity> paging(Pageable pageable, String name) {
        Page<RoleEntity> page = roleRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (StringUtils.isNoneBlank(name)) {
                predicate.getExpressions().add(
                        builder.like(root.get("name"), "%" + name + "%"));
            }

            query.orderBy(builder.desc(root.get("id")));
            return predicate;
        }, pageable);
        return page;
    }

    @Override
    public List<RoleEntity> list() {
        List<RoleEntity> list = roleRepository.findAllByStatus(RoleEntity.STATUS_NORMAL);
        return list;
    }

    @Override
    public Map<Long, RoleEntity> findByIds(Set<Long> ids) {
        List<RoleEntity> list = roleRepository.findAllById(ids);
        Map<Long, RoleEntity> ret = new LinkedHashMap<>();
        list.forEach(po -> {
            RoleEntity vo = toVO(po);
            ret.put(vo.getId(), vo);
        });
        return ret;
    }

    @Override
    public RoleEntity get(long id) {
        return toVO(roleRepository.findById(id).get());
    }

    @Override
    public void update(RoleEntity r, Set<PermissionEntity> permissions) {
        Optional<RoleEntity> optional = roleRepository.findById(r.getId());
        RoleEntity po = optional.orElse(new RoleEntity());
        po.setName(r.getName());
        po.setDescription(r.getDescription());
        po.setStatus(r.getStatus());

        roleRepository.save(po);

        rolePermissionService.deleteByRoleId(po.getId());

        if (permissions != null && permissions.size() > 0) {
            Set<RolePermissionEntity> rps = new HashSet<>();
            long roleId = po.getId();
            permissions.forEach(p -> {
                RolePermissionEntity rp = new RolePermissionEntity();
                rp.setRoleId(roleId);
                rp.setPermissionId(p.getId());
                rps.add(rp);
            });

            rolePermissionService.add(rps);
        }
    }

    @Override
    public boolean delete(long id) {
        List<UserRoleEntity> urs = userRoleRepository.findAllByRoleId(id);
        Assert.state(urs == null || urs.size() == 0, "Ký tự này đã được sử dụng và không thể xóa");
        roleRepository.deleteById(id);
        rolePermissionService.deleteByRoleId(id);
        return true;
    }

    @Override
    public void activate(long id, boolean active) {
        RoleEntity po = roleRepository.findById(id).get();
        po.setStatus(active ? RoleEntity.STATUS_NORMAL : RoleEntity.STATUS_CLOSED);
    }

    private RoleEntity toVO(RoleEntity po) {
        RoleEntity r = new RoleEntity();
        r.setId(po.getId());
        r.setName(po.getName());
        r.setDescription(po.getDescription());
        r.setStatus(po.getStatus());

        r.setPermissions(rolePermissionService.findPermissions(r.getId()));
        return r;
    }
}
