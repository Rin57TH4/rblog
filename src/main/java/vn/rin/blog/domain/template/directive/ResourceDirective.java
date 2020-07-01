/**
 *
 */
package vn.rin.blog.domain.template.directive;

import org.springframework.stereotype.Component;
import vn.rin.blog.domain.template.DirectiveHandler;
import vn.rin.blog.domain.template.TemplateDirective;

/**
 * @author Rin
 */
@Component
public class ResourceDirective extends TemplateDirective {
    @Override
    public String getName() {
        return "resource";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String src = handler.getString("src", "#");
        if (src.startsWith("/storage") || src.startsWith("/theme")) {
            String base = handler.getContextPath();
            handler.renderString(base + src);
        } else {
            handler.renderString(src);
        }
    }

}
