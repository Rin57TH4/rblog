/**
 *
 */
package vn.rin.blog.domain.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vn.rin.blog.domain.data.FavoriteVO;
import vn.rin.blog.domain.template.DirectiveHandler;
import vn.rin.blog.domain.template.TemplateDirective;
import vn.rin.blog.service.FavoriteService;

/**
 * @author Rin
 */
@Component
public class UserFavoritesDirective extends TemplateDirective {
    @Autowired
    private FavoriteService favoriteService;

    @Override
    public String getName() {
        return "user_favorites";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        long userId = handler.getInteger("userId", 0);
        Pageable pageable = wrapPageable(handler);

        Page<FavoriteVO> result = favoriteService.pagingByUserId(pageable, userId);
        handler.put(RESULTS, result).render();
    }

}
