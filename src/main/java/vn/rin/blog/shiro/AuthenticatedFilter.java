package vn.rin.blog.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.util.WebUtils;
import vn.rin.blog.base.Result;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Formatter;

/**
 * @author Rin
 */
public class AuthenticatedFilter extends OncePerRequestFilter {

    private static final String JS = "<script type='text/javascript'>var wp=window.parent; if(wp!=null){while(wp.parent&&wp.parent!==wp){wp=wp.parent;}wp.location.href='%1$s';}else{window.location.href='%1$s';}</script>";
    private String loginUrl = "/login";

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            chain.doFilter(request, response);
        } else {
            WebUtils.saveRequest(request);
            String path = WebUtils.getContextPath((HttpServletRequest) request);
            String url = loginUrl;
            if (StringUtils.isNotBlank(path) && path.length() > 1) {
                url = path + url;
            }

            if (isAjaxRequest((HttpServletRequest) request)) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSON.toJSONString(Result.failure("Bạn chưa đăng nhập!")));
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(new Formatter().format(JS, url).toString());
            }
        }
    }
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }


}
