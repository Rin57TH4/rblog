package vn.rin.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rin.blog.domain.data.OpenOauthVO;
import vn.rin.blog.domain.data.UserVO;
import vn.rin.blog.domain.entity.UserEntity;
import vn.rin.blog.domain.entity.UserOauthEntity;
import vn.rin.blog.repositories.UserOauthRepo;
import vn.rin.blog.repositories.UserRepo;
import vn.rin.blog.service.OpenOauthService;
import vn.rin.blog.utils.BeanMapUtils;
import vn.rin.blog.utils.MD5;

import java.util.Optional;

/**
 * @author Rin
 */
@Service
@Transactional
public class OpenOauthServiceImpl implements OpenOauthService {
    @Autowired
    private UserOauthRepo userOauthRepository;
    @Autowired
    private UserRepo userRepository;

    @Override
    public UserVO getUserByOauthToken(String oauth_token) {
        UserOauthEntity thirdToken = userOauthRepository.findByAccessToken(oauth_token);
        Optional<UserEntity> po = userRepository.findById(thirdToken.getId());
        return BeanMapUtils.copy(po.get());
    }

    @Override
    public OpenOauthVO getOauthByToken(String oauth_token) {
        UserOauthEntity po = userOauthRepository.findByAccessToken(oauth_token);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    public OpenOauthVO getOauthByUid(long userId) {
        UserOauthEntity po = userOauthRepository.findByUserId(userId);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    public boolean checkIsOriginalPassword(long userId) {
        UserOauthEntity po = userOauthRepository.findByUserId(userId);
        if (po != null) {
            Optional<UserEntity> optional = userRepository.findById(userId);

            String pwd = MD5.md5(po.getAccessToken());

            if (optional.isPresent() && pwd.equals(optional.get().getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveOauthToken(OpenOauthVO oauth) {
        UserOauthEntity po = new UserOauthEntity();
        BeanUtils.copyProperties(oauth, po);
        userOauthRepository.save(po);
    }

    @Override
    public OpenOauthVO getOauthByOauthUserId(String oauthUserId) {
        UserOauthEntity po = userOauthRepository.findByOauthUserId(oauthUserId);
        OpenOauthVO vo = null;
        if (po != null) {
            vo = new OpenOauthVO();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

}
