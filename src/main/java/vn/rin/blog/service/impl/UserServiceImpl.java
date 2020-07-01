package vn.rin.blog.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vn.rin.blog.common.EntityStatus;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.data.BadgesCount;
import vn.rin.blog.domain.data.UserVO;
import vn.rin.blog.domain.entity.UserEntity;
import vn.rin.blog.exception.BlogException;
import vn.rin.blog.repositories.ShiroRoleRepo;
import vn.rin.blog.repositories.UserRepo;
import vn.rin.blog.service.MessageService;
import vn.rin.blog.service.UserService;
import vn.rin.blog.utils.BeanMapUtils;
import vn.rin.blog.utils.MD5;

import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @author Rin
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private MessageService messageService;

    @Autowired
    private ShiroRoleRepo roleRepository;

    @Override
    public Page<UserVO> paging(Pageable pageable, String name) {
        Page<UserEntity> page = userRepository.findAll((root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (StringUtils.isNoneBlank(name)) {
                predicate.getExpressions().add(
                        builder.like(root.get("name"), "%" + name + "%"));
            }

            query.orderBy(builder.desc(root.get("id")));
            return predicate;
        }, pageable);

        List<UserVO> rets = new ArrayList<>();
        page.getContent().forEach(n -> rets.add(BeanMapUtils.copy(n)));
        return new PageImpl<>(rets, pageable, page.getTotalElements());
    }

    @Override
    public Map<Long, UserVO> findMapByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }
        List<UserEntity> list = userRepository.findAllById(ids);
        Map<Long, UserVO> ret = new HashMap<>();

        list.forEach(po -> ret.put(po.getId(), BeanMapUtils.copy(po)));
        return ret;
    }

    @Override
    @Transactional
    public AccountProfile login(String username, String password) {
        UserEntity po = userRepository.findByUsername(username);

        if (null == po) {
            return null;
        }


        Assert.state(StringUtils.equals(po.getPassword(), password), "Sai mật khẩu");

        po.setLastLogin(Calendar.getInstance().getTime());
        userRepository.save(po);
        AccountProfile u = BeanMapUtils.copyPassport(po);

        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(u.getId()));

        u.setBadgesCount(badgesCount);
        return u;
    }

    @Override
    @Transactional
    public AccountProfile findProfile(Long id) {
        UserEntity po = userRepository.findById(id).orElse(null);

        Assert.notNull(po, "Tài khoản không tồn tại");

        po.setLastLogin(Calendar.getInstance().getTime());

        AccountProfile u = BeanMapUtils.copyPassport(po);

        BadgesCount badgesCount = new BadgesCount();
        badgesCount.setMessages(messageService.unread4Me(u.getId()));

        u.setBadgesCount(badgesCount);

        return u;
    }

    @Override
    @Transactional
    public UserVO register(UserVO user) {
        Assert.notNull(user, "Parameter user can not be null!");

        Assert.hasLength(user.getUsername(), "Tên người dùng không thể để trống!");
        Assert.hasLength(user.getPassword(), "Mật khẩu không thể để trống!");

        UserEntity check = userRepository.findByUsername(user.getUsername());

        Assert.isNull(check, "Tên người dùng đã tồn tại!");

        if (StringUtils.isNotBlank(user.getEmail())) {
            UserEntity emailCheck = userRepository.findByEmail(user.getEmail());
            Assert.isNull(emailCheck, "Email đã tồn tại!");
        }

        UserEntity po = new UserEntity();

        BeanUtils.copyProperties(user, po);

        if (StringUtils.isBlank(po.getName())) {
            po.setName(user.getUsername());
        }

        Date now = Calendar.getInstance().getTime();
        po.setPassword(MD5.md5(user.getPassword()));
        po.setStatus(EntityStatus.ENABLED);
        po.setCreated(now);

        userRepository.save(po);

        return BeanMapUtils.copy(po);
    }


    @Override
    @Transactional
    public AccountProfile update(UserVO user) {
        UserEntity po = userRepository.findById(user.getId()).orElse(null);
        po.setName(user.getName());
        po.setSignature(user.getSignature());
        userRepository.save(po);
        return BeanMapUtils.copyPassport(po);
    }

    @Override
    @Transactional
    public AccountProfile updateEmail(long id, String email) {
        UserEntity po = userRepository.findById(id).get();

        if (email.equals(po.getEmail())) {
            throw new BlogException("Địa chỉ email chưa được thay đổi");
        }

        UserEntity check = userRepository.findByEmail(email);

        if (check != null && check.getId() != po.getId()) {
            throw new BlogException("Địa chỉ email này đã được sử dụng");
        }
        po.setEmail(email);
        userRepository.save(po);
        return BeanMapUtils.copyPassport(po);
    }

    @Override
    public UserVO get(long userId) {
        Optional<UserEntity> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            return BeanMapUtils.copy(optional.get());
        }
        return null;
    }

    @Override
    public UserVO getByUsername(String username) {
        return BeanMapUtils.copy(userRepository.findByUsername(username));
    }

    @Override
    public UserVO getByEmail(String email) {
        return BeanMapUtils.copy(userRepository.findByEmail(email));
    }

    @Override
    @Transactional
    public AccountProfile updateAvatar(long id, String path) {
        UserEntity po = userRepository.findById(id).get();
        po.setAvatar(path);
        userRepository.save(po);
        return BeanMapUtils.copyPassport(po);
    }

    @Override
    @Transactional
    public void updatePassword(long id, String newPassword) {
        UserEntity po = userRepository.findById(id).get();

        Assert.hasLength(newPassword, "Mật khẩu không thể để trống!");

        po.setPassword(MD5.md5(newPassword));
        userRepository.save(po);
    }

    @Override
    @Transactional
    public void updatePassword(long id, String oldPassword, String newPassword) {
        UserEntity po = userRepository.findById(id).get();

        Assert.hasLength(newPassword, "Mật khẩu không thể để trống!");

        Assert.isTrue(MD5.md5(oldPassword).equals(po.getPassword()), "Mật khẩu hiện tại không chính xác");
        po.setPassword(MD5.md5(newPassword));
        userRepository.save(po);
    }

    @Override
    @Transactional
    public void updateStatus(long id, int status) {
        UserEntity po = userRepository.findById(id).get();

        po.setStatus(status);
        userRepository.save(po);
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}
