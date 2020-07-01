package vn.rin.blog.base.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import vn.rin.blog.base.storage.Storage;
import vn.rin.blog.config.SiteOptions;
import vn.rin.blog.domain.entity.ResourceEntity;
import vn.rin.blog.exception.BlogException;
import vn.rin.blog.repositories.ResourceRepository;
import vn.rin.blog.utils.FileKit;
import vn.rin.blog.utils.FilePathUtils;
import vn.rin.blog.utils.ImageUtils;
import vn.rin.blog.utils.MD5;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Rin
 */
@Slf4j
public abstract class AbstractStorage implements Storage {
    @Autowired
    protected SiteOptions options;
    @Autowired
    protected ResourceRepository resourceRepository;

    protected void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BlogException("Tập tin không thể để trống");
        }

        if (!FileKit.checkFileType(file.getOriginalFilename())) {
            throw new BlogException("Định dạng tệp không được hỗ trợ");
        }
    }

    @Override
    public String store(MultipartFile file, String basePath) throws Exception {
        validateFile(file);
        return writeToStore(file.getBytes(), basePath, file.getOriginalFilename());
    }

    @Override
    public String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception {
        validateFile(file);
        byte[] bytes = ImageUtils.scaleByWidth(file, maxWidth);
        return writeToStore(bytes, basePath, file.getOriginalFilename());
    }

    @Override
    public String storeScale(MultipartFile file, String basePath, int width, int height) throws Exception {
        validateFile(file);
        byte[] bytes = ImageUtils.screenshot(file, width, height);
        return writeToStore(bytes, basePath, file.getOriginalFilename());
    }

    public String writeToStore(byte[] bytes, String src, String originalFilename) throws Exception {
        String md5 = MD5.md5File(bytes);
        ResourceEntity resource = resourceRepository.findByMd5(md5);
        if (resource != null) {
            return resource.getPath();
        }
        String path = FilePathUtils.wholePathName(src, originalFilename, md5);
        path = writeToStore(bytes, path);


        resource = new ResourceEntity();
        resource.setMd5(md5);
        resource.setPath(path);
        resource.setCreateTime(LocalDateTime.now());
        resourceRepository.save(resource);
        return path;
    }

}
