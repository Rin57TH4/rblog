package vn.rin.blog.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import vn.rin.blog.common.Constants;
import vn.rin.blog.common.EntityStatus;
import vn.rin.blog.domain.entity.SecurityCodeEntity;
import vn.rin.blog.exception.BlogException;
import vn.rin.blog.repositories.SecurityCodeRepository;
import vn.rin.blog.service.SecurityCodeService;

import java.util.Date;

/**
 * @author Rin
 */
@Service
public class SecurityCodeServiceImpl implements SecurityCodeService {
    @Autowired
    private SecurityCodeRepository securityCodeRepository;

    private int survivalTime = 30;

    @Override
    @Transactional
    public String generateCode(String key, int type, String target) {
        SecurityCodeEntity po = securityCodeRepository.findByKey(key);

        String code = RandomStringUtils.randomNumeric(6);
        Date now = new Date();

        if (po == null) {
            po = new SecurityCodeEntity();
            po.setKey(key);
            po.setCreated(now);
            po.setExpired(DateUtils.addMinutes(now, survivalTime));
            po.setCode(code);
            po.setType(type);
            po.setTarget(target);
        } else {

            long interval = (now.getTime() - po.getCreated().getTime()) / 1000;

            if (interval <= 60) {
                throw new BlogException("Khoảng thời gian gửi phải ít nhất 1 phút");
            }


            po.setStatus(EntityStatus.ENABLED);
            po.setCreated(now);
            po.setExpired(DateUtils.addMinutes(now, survivalTime));
            po.setCode(code);
            po.setType(type);
            po.setTarget(target);
        }

        securityCodeRepository.save(po);

        return code;
    }

    @Override
    @Transactional
    public boolean verify(String key, int type, String code) {
        Assert.hasLength(code, "Mã xác minh không thể để trống");
        SecurityCodeEntity po = securityCodeRepository.findByKeyAndType(key, type);
        Assert.notNull(po, "Bạn chưa thực hiện xác minh loại");

        Date now = new Date();

        Assert.state(now.getTime() <= po.getExpired().getTime(), "Mã xác minh đã hết hạn");
        Assert.isTrue(po.getType() == type, "Loại mã xác minh sai");
        Assert.isTrue(po.getStatus() == Constants.CODE_STATUS_INIT, "Mã xác minh đã được sử dụng");
        Assert.state(code.equals(po.getCode()), "Mã xác minh không chính xác");

        po.setStatus(Constants.CODE_STATUS_CERTIFIED);
        securityCodeRepository.save(po);
        return true;
    }

}
