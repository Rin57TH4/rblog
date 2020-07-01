package vn.rin.blog.domain.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.rin.blog.domain.entity.TagEntity;

import java.io.Serializable;

/**
 * @author Rin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagVO extends TagEntity implements Serializable {
    private static final long serialVersionUID = -7787865229252467418L;

    private PostVO post;
}
