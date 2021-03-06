package vn.rin.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.rin.blog.domain.data.MessageVO;

/**
 * @author Rin
 */
public interface MessageService {

    Page<MessageVO> pagingByUserId(Pageable pageable, long userId);

    void send(MessageVO message);

    int unread4Me(long userId);

    void readed4Me(long userId);

    int deleteByPostId(long postId);
}
