package vn.rin.blog.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import vn.rin.blog.base.Result;
import vn.rin.blog.config.ContextStartup;
import vn.rin.blog.service.OptionsService;
import vn.rin.blog.utils.BlogUtils;
import vn.rin.blog.utils.FileKit;
import vn.rin.blog.web.controller.BaseController;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rin
 */
@Controller
@RequestMapping("/admin/theme")
public class ThemeController extends BaseController {
    @Autowired
    private OptionsService optionsService;
    @Autowired
    private ContextStartup contextStartup;

    @RequestMapping("/index")
    public String index(ModelMap model) {
        model.put("themes", BlogUtils.getThemes());
        return "/admin/theme/index";
    }

    @RequestMapping("/active")
    @ResponseBody
    public Result update(@RequestParam Map<String, String> body) {
        optionsService.update(body);
        contextStartup.reloadOptions(false);
        return Result.success();
    }

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file) {
        if (null == file || file.isEmpty()) {
            return Result.failure("Tập tin không thể để trống");
        } else {
            String suffix = FileKit.getSuffix(Objects.requireNonNull(file.getOriginalFilename()));
            if (!".zip".equalsIgnoreCase(suffix)) {
                return Result.failure("Vui lòng tải lên một tệp zip");
            }
            try {
                return BlogUtils.uploadTheme(file);
            } catch (IOException e) {
                return Result.failure(e.getMessage());
            }
        }
    }
}
