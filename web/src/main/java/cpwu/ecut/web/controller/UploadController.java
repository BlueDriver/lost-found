package cpwu.ecut.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.util.Base64;

/**
 * lost-found
 * cpwu.ecut.web.controller
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/07 14:39 Sunday
 */
@RestController
public class UploadController {
    @PostMapping("/upload")
    public String upload(@RequestParam("file") String file){
        Base64.Decoder decoder = Base64.getDecoder();
        // 去掉base64编码的头部 如："data:image/jpeg;base64," 如果不去，转换的图片不可以查看
        //data:image/x-icon;base64,
        //data:image/png;base64,
        file = file.substring(23);
        //解码
        byte[] imgByte = decoder.decode(file);
        try {
            FileOutputStream out = new FileOutputStream("D:/1.jpg"); // 输出文件路径
            out.write(imgByte);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

}
