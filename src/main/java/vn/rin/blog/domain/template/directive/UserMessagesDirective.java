/**
 *
 */
package vn.rin.blog.domain.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vn.rin.blog.domain.data.MessageVO;
import vn.rin.blog.domain.template.DirectiveHandler;
import vn.rin.blog.domain.template.TemplateDirective;
import vn.rin.blog.service.MessageService;

/**
 * @author Rin
 */
@Component
public class UserMessagesDirective extends TemplateDirective {
    @Autowired
    private MessageService messageService;

    @Override
    public String getName() {
        return "user_messages";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        long userId = handler.getInteger("userId", 0);
        Pageable pageable = wrapPageable(handler);

        Page<MessageVO> result = messageService.pagingByUserId(pageable, userId);
        handler.put(RESULTS, result).render();
    }

}
