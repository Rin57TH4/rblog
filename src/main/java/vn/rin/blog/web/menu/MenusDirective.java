package vn.rin.blog.web.menu;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import vn.rin.blog.domain.entity.RoleEntity;
import vn.rin.blog.domain.template.DirectiveHandler;
import vn.rin.blog.domain.template.TemplateDirective;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Rin
 */
@Component
public class MenusDirective extends TemplateDirective {
    @Override
    public String getName() {
        return "menus";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        List<Menu> menus = filterMenu(SecurityUtils.getSubject());
        handler.put(RESULTS, menus).render();
    }

    private List<Menu> filterMenu(Subject subject) {
        List<Menu> menus = MenuJsonUtils.getMenus();
        if (!subject.hasRole(RoleEntity.ROLE_ADMIN)) {
            menus = check(subject, menus);
        }
        return menus;
    }

    private List<Menu> check(Subject subject, List<Menu> menus) {
        List<Menu> results = new LinkedList<>();
        for (Menu menu : menus) {
            if (check(subject, menu)) {
                results.add(menu);
            }
        }

        return results;
    }

    private boolean check(Subject subject, Menu menu) {
        boolean authorized = false;
        if (StringUtils.isBlank(menu.getPermission())) {
            authorized = true;
        } else {
            for (String perm : menu.getPermission().split(",")) {
                if (subject.isPermitted(perm)) {
                    authorized = true;
                    break;
                }
            }
        }
        return authorized;
    }

}
