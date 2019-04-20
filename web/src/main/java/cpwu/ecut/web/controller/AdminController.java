package cpwu.ecut.web.controller;

import cpwu.ecut.common.constant.annotation.AuthCheck;
import cpwu.ecut.common.constant.enums.UserKindEnum;
import cpwu.ecut.service.dto.resp.base.ResponseDTO;
import cpwu.ecut.service.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * lost-found
 * cpwu.ecut.web.controller
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/20 14:43 Saturday
 */
@RestController
@Validated
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/userInfo")
    @AuthCheck(level = UserKindEnum.MANAGER)
    public ResponseDTO userInfo(@NotBlank(message = "用户id不能空") @RequestParam String userId) throws Exception {
        return ResponseDTO.successObj("user", userService.userInfo(userId));
    }
}
