package vn.rin.blog.web.controller.site.posts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import vn.rin.blog.common.Constants;
import vn.rin.blog.utils.FileKit;
import vn.rin.blog.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Rin
 */
@Controller
@RequestMapping("/post")
public class UploadController extends BaseController {
    public static HashMap<String, String> errorInfo = new HashMap<>();

    static {
        errorInfo.put("SUCCESS", "SUCCESS"); //Thành công theo mặc định
        errorInfo.put("NOFILE", "Tên miền tải lên tệp không được bao gồm");
        errorInfo.put("TYPE", "Định dạng tệp không được phép");
        errorInfo.put("SIZE", "Kích thước tệp vượt quá giới hạn，Hỗ trợ lên tới 2Mb");
        errorInfo.put("ENTYPE", "Lỗi yêu cầu loại ENTYPE");
        errorInfo.put("REQUEST", "Ngoại lệ yêu cầu tải lên");
        errorInfo.put("IO", "Ngoại lệ IO");
        errorInfo.put("DIR", "Tạo thư mục không thành công");
        errorInfo.put("UNKNOWN", "Lỗi không xác định");
    }

    @PostMapping("/upload")
    @ResponseBody
    public UploadResult upload(@RequestParam(value = "file", required = false) MultipartFile file,
                               HttpServletRequest request) throws IOException {
        UploadResult result = new UploadResult();
        String crop = request.getParameter("crop");
        int size = ServletRequestUtils.getIntParameter(request, "size", siteOptions.getIntegerValue(Constants.STORAGE_MAX_WIDTH));


        if (null == file || file.isEmpty()) {
            return result.error(errorInfo.get("NOFILE"));
        }

        String fileName = file.getOriginalFilename();


        if (!FileKit.checkFileType(fileName)) {
            return result.error(errorInfo.get("TYPE"));
        }


        String limitSize = siteOptions.getValue(Constants.STORAGE_LIMIT_SIZE);
        if (StringUtils.isBlank(limitSize)) {
            limitSize = "2";
        }
        if (file.getSize() > (Long.parseLong(limitSize) * 1024 * 1024)) {
            return result.error(errorInfo.get("SIZE"));
        }

        try {
            String path;
            if (StringUtils.isNotBlank(crop)) {
                Integer[] imageSize = siteOptions.getIntegerArrayValue(crop, Constants.SEPARATOR_X);
                int width = ServletRequestUtils.getIntParameter(request, "width", imageSize[0]);
                int height = ServletRequestUtils.getIntParameter(request, "height", imageSize[1]);
                path = storageFactory.get().storeScale(file, Constants.thumbnailPath, width, height);
            } else {
                path = storageFactory.get().storeScale(file, Constants.thumbnailPath, size);
            }
            result.ok(errorInfo.get("SUCCESS"));
            result.setName(fileName);
            result.setPath(path);
            result.setSize(file.getSize());

        } catch (Exception e) {
            result.error(errorInfo.get("UNKNOWN"));
            e.printStackTrace();
        }

        return result;
    }

    public static class UploadResult {
        public static int OK = 200;
        public static int ERROR = 400;

        private int status;


        private String message;


        private String name;


        private long size;


        private String path;

        public UploadResult ok(String message) {
            this.status = OK;
            this.message = message;
            return this;
        }

        public UploadResult error(String message) {
            this.status = ERROR;
            this.message = message;
            return this;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }
}
