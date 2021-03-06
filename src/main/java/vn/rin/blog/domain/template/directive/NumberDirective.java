package vn.rin.blog.domain.template.directive;

import org.springframework.stereotype.Component;
import vn.rin.blog.domain.template.DirectiveHandler;
import vn.rin.blog.domain.template.TemplateDirective;

/**
 * @author Rin
 */
@Component
public class NumberDirective extends TemplateDirective {
    @Override
    public String getName() {
        return "num";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer value = handler.getInteger("value", 1);
        String out = value.toString();

        if (value > 1000) {
            out = value / 1000 + "k";
        } else if (value > 10000) {
            out = value / 10000 + "m";
        }
        handler.renderString(out);
    }

}
