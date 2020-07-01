/**
 *
 */
package vn.rin.blog.domain.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.domain.template.DirectiveHandler;
import vn.rin.blog.domain.template.TemplateDirective;
import vn.rin.blog.service.PostService;

/**
 * @author Rin
 */
@Component
public class UserContentsDirective extends TemplateDirective {
    @Autowired
    private PostService postService;

    @Override
    public String getName() {
        return "user_contents";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        long userId = handler.getInteger("userId", 0);
        int status = handler.getInteger("status", 0);
        Pageable pageable = wrapPageable(handler);

        Page<PostVO> result = postService.pagingByAuthorIdAndStatus(pageable, userId, status);
        handler.put(RESULTS, result).render();
    }

}
