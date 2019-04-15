package cpwu.ecut.web.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import cpwu.ecut.common.constant.enums.ApplyKindEnum;
import cpwu.ecut.common.utils.EnumUtils;
import cpwu.ecut.dao.entity.LostFound;
import cpwu.ecut.service.dto.req.PublicationAddReq;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.web.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.web.controller
 * 用户相关
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/06 22:03 Saturday
 */

@RestController
@Validated
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private ImageUtils imageUtils;

    /**
     * 发布启事
     */
    @PostMapping("/pub")
    public ResponseDTO publicationAddReq(@Valid @RequestBody PublicationAddReq req, HttpSession session) throws Exception {
        EnumUtils.checkAndGetCode(req.getApplyKind(), ApplyKindEnum.values());
        LostFound lostFound = new LostFound();
        List<String> imageList = new ArrayList<>(req.getImages().size());
        Base64.Decoder decoder = Base64.getDecoder();
        for (String img : req.getImages()) {
            String image = imageUtils.getBase64Image(img);
            byte[] bytes = decoder.decode(image);
            String fileName = imageUtils.copyFileToResource(bytes).getFilename();
            imageList.add(fileName);
        }
        //System.err.println(imageList);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(imageList);
        return ResponseDTO.successObj("list", mapper.readValue(json, List.class));
    }

}
