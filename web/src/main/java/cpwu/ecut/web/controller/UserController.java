package cpwu.ecut.web.controller;

import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.dao.entity.Student;
import cpwu.ecut.dao.inter.StudentDAO;
import cpwu.ecut.service.utils.VPNUtils;
import cpwu.ecut.web.dto.resp.ResponseDTO;
import cpwu.ecut.web.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;

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
public class UserController {


    @RequestMapping("/ex")
    public String hello() throws Exception {
        System.out.println(new Date());
        System.out.println(new Date());
        throw ExceptionUtils.createException(ErrorEnum.PASSWORD_USERNAME_ERROR, "1234");
    }

    @Autowired
    private VPNUtils vpnUtils;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private CommonUtils commonUtils;

    @RequestMapping
    public ResponseDTO std(@NotBlank(message = "用户名不能为空") @RequestParam String u,
                           @NotBlank(message = "密码不能为空") @RequestParam String p) throws Exception {
        Student std = new Student();
        std.setStudentNum(u);
        if (studentDAO.exists(Example.of(std))) {
            return ResponseDTO.successObj();
        }
        Student student = vpnUtils.getStudentInfo(u, p);
        student.setId(commonUtils.getUUID())
                .setUserId("000");
        studentDAO.save(student);
        return ResponseDTO.successObj().putData("std", student);
    }


}
