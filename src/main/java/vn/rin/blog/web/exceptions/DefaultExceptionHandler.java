package vn.rin.blog.web.exceptions;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import vn.rin.blog.base.Result;
import vn.rin.blog.exception.BlogException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rin
 */
@Slf4j
@Component
public class DefaultExceptionHandler implements HandlerExceptionResolver {
    private static final String errorView = "/error";

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        if (ex instanceof IllegalArgumentException || ex instanceof IllegalStateException || ex instanceof BlogException) {
            log.error(ex.getMessage());
        } else {
            log.error(ex.getMessage(), ex);
        }

        ModelAndView view = null;
        String ret = ex.getMessage();

        if (isAjax(handler)) {
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(JSON.toJSONString(Result.failure(ret)));
            } catch (IOException e) {
                // do something
            }

            view = new ModelAndView();
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", ret);
            map.put("base", request.getContextPath());
            view = new ModelAndView(errorView, map);
        }
        return view;
    }


    private boolean isAjax(Object handler) {
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ResponseBody.class);
            return responseBodyAnn != null;
        }

        return false;
    }

}
