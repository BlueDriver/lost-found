package cpwu.ecut.web.controller;

import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.dao.entity.Student;
import cpwu.ecut.service.utils.VPNUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.web.controller
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/06 22:03 Saturday
 */

@RestController
@Validated
public class HelloController {


    @RequestMapping("/ex")
    public String hello() throws Exception {
        System.out.println(new Date());
        System.out.println(new Date());
        throw ExceptionUtils.createException(ErrorEnum.PASSWORD_USERNAME_ERROR, "1234");
    }

    @Autowired
    private VPNUtils vpnUtils;

    @RequestMapping
    public Student std(@NotBlank(message = "用户名不能为空") @RequestParam String u) throws Exception {
        return vpnUtils.getStudentInfo(u, "a");
    }


}
