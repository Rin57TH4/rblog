package vn.rin.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.rin.blog.domain.entity.MessageEntity;

/**
 * @author Rin
 */
public interface MessageRepository extends JpaRepository<MessageEntity, Long>, JpaSpecificationExecutor<MessageEntity> {
    Page<MessageEntity> findAllByUserId(Pageable pageable, long userId);

    int countByUserIdAndStatus(long userId, int status);

    @Modifying
    @Query("update MessageEntity n set n.status = 1 where n.status = 0 and n.userId = :uid")
    int updateReadedByUserId(@Param("uid") Long uid);

    int deleteByPostId(long postId);
}
