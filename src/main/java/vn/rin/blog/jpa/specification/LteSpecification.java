package vn.rin.blog.jpa.specification;

import javax.persistence.criteria.*;

/**
 * @author Rin
 */
public class LteSpecification<T> extends AbstractSpecification<T> {
    private final String property;
    private final transient Comparable<Object> compare;

    public LteSpecification(String property, Comparable<? extends Object> compare) {
        this.property = property;
        this.compare = (Comparable<Object>) compare;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        From from = getRoot(property, root);
        String field = getProperty(property);
        return cb.lessThanOrEqualTo(from.get(field), compare);
    }
}
