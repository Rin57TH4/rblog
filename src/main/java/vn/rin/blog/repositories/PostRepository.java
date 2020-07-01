package vn.rin.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.rin.blog.domain.entity.PostEntity;

import java.util.List;

/**
 * @author Rin
 */
public interface PostRepository extends JpaRepository<PostEntity, Long>, JpaSpecificationExecutor<PostEntity> {
    Page<PostEntity> findAllByAuthorId(Pageable pageable, long authorId);

    List<PostEntity> findByChannelId(long channelId);

    Page<PostEntity> findAllByAuthorIdAndStatus(Pageable pageable, long authorId, int status);

    @Query("select coalesce(max(weight), 0) from PostEntity")
    int maxWeight();

    @Modifying
    @Query("update PostEntity set views = views + :increment where id = :id")
    void updateViews(@Param("id") long id, @Param("increment") int increment);

    @Modifying
    @Query("update PostEntity set favors = favors + :increment where id = :id")
    void updateFavors(@Param("id") long id, @Param("increment") int increment);

    @Modifying
    @Query("update PostEntity set comments = comments + :increment where id = :id")
    void updateComments(@Param("id") long id, @Param("increment") int increment);

}
