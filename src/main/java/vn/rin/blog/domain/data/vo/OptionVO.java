package vn.rin.blog.domain.data.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Rin
 */
@Getter
@Setter
@ToString
public class OptionVO {
    private String id;
    private String value;
    private String category;
    private String label;
}
