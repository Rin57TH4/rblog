package vn.rin.blog.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.rin.blog.domain.entity.ChannelEntity;

import java.util.List;

/**
 * @author Rin
 */
public interface ChannelRepo extends JpaRepository<ChannelEntity, Integer>, JpaSpecificationExecutor<ChannelEntity> {
    List<ChannelEntity> findAllByStatus(int status, Sort sort);

    @Query("select coalesce(max(weight), 0) from ChannelEntity")
    int maxWeight();
}
