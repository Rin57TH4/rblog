package vn.rin.blog.web.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.rin.blog.common.Constants;
import vn.rin.blog.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rin
 */
@Controller
public class IndexController extends BaseController {
    @RequestMapping(value = {"/", "/index"})
    public String root(ModelMap model, HttpServletRequest request) {
//        String order = ServletRequestUtils.getStringParameter(request, "order", Constants.order.NEWEST);
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
//        model.put("order", order);
        model.put("pageNo", pageNo);
        return view(Views.INDEX);
    }
}
