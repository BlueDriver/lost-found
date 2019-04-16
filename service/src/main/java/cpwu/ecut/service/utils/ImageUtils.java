package cpwu.ecut.service.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * lost-found
 * cpwu.ecut.web.utils
 * base64图片处理工具类
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 21:15 Monday
 */
@Service
public class ImageUtils {
    /**
     * 文件上传的路径
     */
    @Value("${web.upload-path}")
    private String path;
    //去掉base64编码的头部 如："data:image/jpeg;base64," 如果不去，转换的图片不可以查看
    //data:image/x-icon;base64,
    //data:image/png;base64,
    private static final String flag = ";base64,";
    private static final String imageSuffix = ".jpg";

    /**
     * 去掉base64头部
     */
    public String getBase64Image(String file) {
        int index = file.indexOf(";base64,");
        if (index == -1) {
            return "";
        } else {
            return file.substring(index + flag.length());
        }
    }

    /**
     * 保存文件
     */
    public Resource copyFileToResource(byte[] bytes) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        File tempFile = File.createTempFile("upload_", imageSuffix, f);

        FileCopyUtils.copy(bytes, tempFile);

        return new FileSystemResource(tempFile);
    }


}
