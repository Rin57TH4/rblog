package vn.rin.blog.domain.hook.interceptor.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.domain.entity.PostEntity;
import vn.rin.blog.domain.hook.interceptor.InterceptorHookSupport;
import vn.rin.blog.web.controller.site.ChannelController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rin
 */
@Component
public class ViewCopyrightPugin extends InterceptorHookSupport {

    @Override
    public String[] getInterceptor() {
        return new String[]{ChannelController.class.getName()};
    }

    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, ModelAndView modelAndView) throws Exception {
        Object obj = modelAndView.getModel().get("view");

        PostVO ret = null;

        if (obj instanceof PostVO || obj instanceof PostEntity) {
            ret = (PostVO) obj;
        }

        Object editing = modelAndView.getModel().get("editing");
        if (null == editing && ret != null) {
            String content = ret.getContent();
            if (!content.contains("Bài viết này thuộc về tác giả và không được sao chép mà không có sự cho phép của tác giả")) {
                content += "<br/><p class=\"copyright\">Note: Bài viết này thuộc về tác giả và không được sao chép mà không có sự cho phép của tác giả</p>";
                ret.setContent(content);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception {

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

    }
}
