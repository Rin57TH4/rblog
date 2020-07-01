package vn.rin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.rin.blog.domain.entity.ResourceEntity;

import java.util.Collection;
import java.util.List;

/**
 * @author Rin
 */
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long>, JpaSpecificationExecutor<ResourceEntity> {

    ResourceEntity findByMd5(String md5);

    List<ResourceEntity> findByMd5In(List<String> md5);

    @Query(value = "SELECT * FROM mto_resource WHERE amount <= 0 AND update_time < :time ", nativeQuery = true)
    List<ResourceEntity> find0Before(@Param("time") String time);

    @Modifying
    @Query("update ResourceEntity set amount = amount + :increment where md5 in (:md5s)")
    int updateAmount(@Param("md5s") Collection<String> md5s, @Param("increment") long increment);

    @Modifying
    @Query("update ResourceEntity set amount = amount + :increment where id in (:ids)")
    int updateAmountByIds(@Param("ids") Collection<Long> md5s, @Param("increment") long increment);
}
