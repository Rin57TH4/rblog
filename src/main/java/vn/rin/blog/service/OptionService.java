package vn.rin.blog.service;

import vn.rin.blog.domain.entities.Option;

/**
 * @author Rin
 */
public interface OptionService {
    void removeOption(String id);
    void addOption(Option option);
    void updateOption(Option option);
}
