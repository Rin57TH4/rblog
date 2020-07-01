package vn.rin.blog.domain.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.rin.blog.domain.template.DirectiveHandler;
import vn.rin.blog.domain.template.TemplateDirective;
import vn.rin.blog.service.LinksService;

/**
 * Eg:
 * <@links>
 * <#list results as row>
 * <li><a href="${row.url}">${row.name}</a></li>
 * </#list>
 * </@links>
 *
 * @author Rin
 */
@Component
public class LinksDirective extends TemplateDirective {
    @Autowired
    private LinksService linksService;

    @Override
    public String getName() {
        return "links";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        handler.put(RESULTS, linksService.findAll()).render();
    }
}
