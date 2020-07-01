package vn.rin.blog.domain.data;

import vn.rin.blog.domain.entity.FavoriteEntity;

/**
 * @author Rin
 */
public class FavoriteVO extends FavoriteEntity {
    // extend
    private PostVO post;

    public PostVO getPost() {
        return post;
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
