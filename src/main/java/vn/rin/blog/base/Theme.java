package vn.rin.blog.base;

import lombok.Data;

import java.util.List;

/**
 * @author Rin
 */
@Data
public class Theme {

    private String path;


    private String name;


    private String slogan;


    private String version;


    private String author;


    private String website;


    private List<String> previews;
}
