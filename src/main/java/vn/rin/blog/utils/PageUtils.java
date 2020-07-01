package vn.rin.blog.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author Rin
 */
public class PageUtils {

    public static PageRequest wrapPageable(Integer pageSize, Integer pageNo, Sort sort) {
        if (null == sort) {
            sort = Sort.unsorted();
        }
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }

    public static PageRequest wrapPageable(Integer pageSize, Integer pageNo) {
        return PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
    }

    public static PageRequest wrapPageable(Integer pageSize) {
        return PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "id"));
    }

    public static PageRequest wrapPageable(Integer pageSize, Sort sort) {
        if (null == sort) {
            sort = Sort.unsorted();
        }
        return PageRequest.of(0, pageSize, sort);
    }

    public static PageRequest wrapPageable(Sort sort) {
        if (null == sort) {
            sort = Sort.unsorted();
        }
        return PageRequest.of(0, Integer.MAX_VALUE, sort);
    }
}
