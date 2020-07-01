package vn.rin.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Rin
 */
@SpringBootApplication
@EnableCaching
public class RinBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(RinBlogApplication.class, args);
    }
}
