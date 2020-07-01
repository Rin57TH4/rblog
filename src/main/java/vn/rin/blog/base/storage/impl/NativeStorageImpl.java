package vn.rin.blog.base.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.rin.blog.utils.FileKit;

import java.io.File;

/**
 * @author Rin
 */
@Slf4j
@Component

public class NativeStorageImpl extends AbstractStorage {
    @Override
    public void deleteFile(String storePath) {
        File file = new File(getStoragePath() + storePath);

        if (file.exists() && !file.isDirectory()) {
            file.delete();
            log.info("fileRepo delete " + storePath);
        }
    }

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String dest = getStoragePath() + pathAndFileName;
        log.info("dest ->>{}", dest);
        FileKit.writeByteArrayToFile(bytes, dest);
        return pathAndFileName;
    }

    private String getStoragePath() {
        return options.getLocation();
    }
}
