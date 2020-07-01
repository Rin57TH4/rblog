package vn.rin.blog.domain.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.rin.blog.domain.template.DirectiveHandler;
import vn.rin.blog.domain.template.TemplateDirective;
import vn.rin.blog.service.ChannelService;

/**
 * @author Rin
 */
@Component
public class ChannelDirective extends TemplateDirective {
    @Autowired
    private ChannelService channelService;

    @Override
    public String getName() {
        return "channel";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer id = handler.getInteger("id", 0);
        handler.put(RESULT, channelService.getById(id)).render();
    }
}