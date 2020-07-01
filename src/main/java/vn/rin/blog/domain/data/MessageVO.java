package vn.rin.blog.domain.data;

import lombok.Getter;
import lombok.Setter;
import vn.rin.blog.domain.entity.MessageEntity;

/**
 * @author Rin
 */
@Getter
@Setter
public class MessageVO extends MessageEntity {
    // extend
    private UserVO from;
    private PostVO post;

}
