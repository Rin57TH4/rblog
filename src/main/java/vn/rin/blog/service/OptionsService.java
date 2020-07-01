package vn.rin.blog.service;

import org.springframework.core.io.Resource;
import vn.rin.blog.domain.entity.OptionsEntity;

import java.util.List;
import java.util.Map;


/**
 * @author Rin
 */
public interface OptionsService {

    List<OptionsEntity> findAll();

    void update(Map<String, String> options);

    void initSettings(Resource resource);
}
