package cpwu.ecut.web.controller.common;

import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.SchoolService;
import cpwu.ecut.service.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;


/**
 * lost-found
 * cpwu.ecut.web.controller.common
 * 公共接口，无访问限制
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/08 14:47 Monday
 */
@RestController
@RequestMapping("/api/v1/common")
public class CommonController {
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private VerifyCodeUtils verifyCodeUtils;

    @GetMapping("/schools")
    public ResponseDTO schools() {
        return ResponseDTO.successObj("schools", schoolService.getSchools());
    }

    /**
     * 验证码长度
     */
    private static final int codeLength = 5;

    /**
     * 生成随机验证码
     * http://localhost:8080//api/v1/common/verifyCode
     */
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        String code = verifyCodeUtils.getRandomNum(codeLength);
        //将code存入session，用于后期校验
        session.setAttribute("code", code);
        BufferedImage image = verifyCodeUtils.getImage(code);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setDateHeader(HttpHeaders.EXPIRES, -1L);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");//http 1.1
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");//http 1.0
        //将图片写入响应输出流
        ImageIO.write(image, "jpg", response.getOutputStream());
    }


}
