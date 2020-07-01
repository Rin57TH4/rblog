package vn.rin.blog.domain.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.rin.blog.domain.entity.PostTagEntity;

import java.io.Serializable;

/**
 * @author Rin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostTagVO extends PostTagEntity implements Serializable {
    private static final long serialVersionUID = 73354108587481371L;

    private PostVO post;
}
