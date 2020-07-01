package vn.rin.blog.service;

import java.util.Map;

/**
 * @author Rin
 */
public interface MailService {
    void config();

    void sendTemplateEmail(String to, String title, String template, Map<String, Object> content);
}
