package cpwu.ecut.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * lost-found
 * cpwu.ecut.service.dto.req
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/10 12:39 Wednesday
 */
@Data
@Validated
@NoArgsConstructor
public class UserLoginReq {
    @NotBlank(message = "学校id不能为空")
    private String schoolId;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String code;
}
