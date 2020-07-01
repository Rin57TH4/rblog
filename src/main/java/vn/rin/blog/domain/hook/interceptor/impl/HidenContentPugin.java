package vn.rin.blog.domain.hook.interceptor.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import vn.rin.blog.domain.data.AccountProfile;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.domain.entity.PostEntity;
import vn.rin.blog.domain.hook.interceptor.InterceptorHookSupport;
import vn.rin.blog.service.CommentService;
import vn.rin.blog.web.controller.site.ChannelController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rin
 */
@Component
public class HidenContentPugin extends InterceptorHookSupport {
    @Autowired
    private CommentService commentService;

    private static final String SHOW = "<blockquote>Hidden content, please check back after replying</blockquote>";

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
            PostVO post = new PostVO();
            BeanUtils.copyProperties(ret, post);
            if (check(ret.getId(), ret.getAuthor().getId())) {
                String c = post.getContent().replaceAll("\\[hide\\]([\\s\\S]*)\\[\\/hide\\]", SHOW);
                post.setContent(c);
            } else {
                String c = post.getContent().replaceAll("\\[hide\\]([\\s\\S]*)\\[\\/hide\\]", "$1");
                post.setContent(c);
            }
            modelAndView.getModelMap().put("view", post);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Exception ex) throws Exception {

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) throws Exception {

    }

    private boolean check(long id, long userId) {
        Subject subject = SecurityUtils.getSubject();
        AccountProfile profile = (AccountProfile) subject.getPrincipal();
        if (profile != null) {
            if (profile.getId() == userId) {
                return false;
            }
            return commentService.countByAuthorIdAndPostId(profile.getId(), id) <= 0;
        }
        return true;
    }
}
