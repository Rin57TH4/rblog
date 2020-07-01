package vn.rin.blog.service;

import vn.rin.blog.domain.entity.LinksEntity;

import java.util.List;

/**
 * @author Rin
 */
public interface LinksService {
    List<LinksEntity> findAll();

    void update(LinksEntity links);

    void delete(long id);
}
