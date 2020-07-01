package vn.rin.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.rin.blog.domain.entity.LinksEntity;
import vn.rin.blog.repositories.LinksRepository;
import vn.rin.blog.service.LinksService;

import java.util.List;
import java.util.Optional;

/**
 * @author Rin
 */
@Service
@Transactional(readOnly = true)
public class LinksServiceImpl implements LinksService {
    @Autowired
    private LinksRepository linksRepository;

    @Override
    public List<LinksEntity> findAll() {
        return linksRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional
    public void update(LinksEntity links) {
        Optional<LinksEntity> optional = linksRepository.findById(links.getId());
        LinksEntity po = optional.orElse(new LinksEntity());
        BeanUtils.copyProperties(links, po, "created", "updated");
        linksRepository.save(po);
    }

    @Override
    @Transactional
    public void delete(long id) {
        linksRepository.deleteById(id);
    }
}
