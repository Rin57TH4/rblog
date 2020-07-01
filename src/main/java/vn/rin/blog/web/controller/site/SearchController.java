package vn.rin.blog.web.controller.site;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.rin.blog.domain.data.PostVO;
import vn.rin.blog.service.PostSearchService;
import vn.rin.blog.web.controller.BaseController;

/**
 * @author Rin
 */
@Controller
public class SearchController extends BaseController {
    @Autowired
    private PostSearchService postSearchService;

    @RequestMapping("/search")
    public String search(String kw, ModelMap model) {
        Pageable pageable = wrapPageable();
        try {
            if (StringUtils.isNotEmpty(kw)) {
                Page<PostVO> page = postSearchService.search(pageable, kw);
                model.put("results", page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.put("kw", kw);
        return view(Views.SEARCH);
    }

}
