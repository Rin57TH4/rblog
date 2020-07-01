package vn.rin.blog.jpa.specification;

import javax.persistence.criteria.*;

/**
 * @author Rin
 */
public class NotEmptySpecification<T> extends AbstractSpecification<T> {
    private final String property;

    public NotEmptySpecification(String property) {
        this.property = property;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        From from = getRoot(property, root);
        String field = getProperty(property);
        return cb.isNotEmpty(from.get(field));
    }

}
