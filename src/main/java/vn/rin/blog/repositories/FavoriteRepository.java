package vn.rin.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.rin.blog.domain.entity.FavoriteEntity;

/**
 * @author Rin
 */
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long>, JpaSpecificationExecutor<FavoriteEntity> {
    FavoriteEntity findByUserIdAndPostId(long userId, long postId);

    Page<FavoriteEntity> findAllByUserId(Pageable pageable, long userId);

    int deleteByPostId(long postId);
}
