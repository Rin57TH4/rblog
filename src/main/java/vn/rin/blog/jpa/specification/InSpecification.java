package vn.rin.blog.jpa.specification;

import javax.persistence.criteria.*;
import java.util.Collection;

/**
 * @author Rin
 */
public class InSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Collection<?> values;

    public InSpecification(String property, Collection<?> values) {
        this.property = property;
        this.values = values;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        From from = getRoot(property, root);
        String field = getProperty(property);
        return from.get(field).in(values);
    }
}
