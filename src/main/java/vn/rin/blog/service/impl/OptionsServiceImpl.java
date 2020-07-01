package vn.rin.blog.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rin.blog.domain.entity.OptionsEntity;
import vn.rin.blog.repositories.OptionsRepo;
import vn.rin.blog.service.OptionsService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rin
 */
@Service
public class OptionsServiceImpl implements OptionsService {
    @Autowired
    private OptionsRepo optionsRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<OptionsEntity> findAll() {
        List<OptionsEntity> list = optionsRepository.findAll();
        List<OptionsEntity> rets = new ArrayList<>();

        for (OptionsEntity po : list) {
            OptionsEntity r = new OptionsEntity();
            BeanUtils.copyProperties(po, r);
            rets.add(r);
        }
        return rets;
    }

    @Override
    @Transactional
    public void update(Map<String, String> options) {
        if (options == null) {
            return;
        }

        options.forEach((key, value) -> {
            OptionsEntity entity = optionsRepository.findByKey(key);
            String val = StringUtils.trim(value);
            if (entity != null) {
                entity.setValue(val);
            } else {
                entity = new OptionsEntity();
                entity.setKey(key);
                entity.setValue(val);
            }
            optionsRepository.save(entity);
        });
    }

    @Override
    @Transactional
    public void initSettings(Resource resource) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> ScriptUtils.executeSqlScript(connection, resource));
    }

}
