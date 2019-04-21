package cpwu.ecut.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.service.dto.resp
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/20 14:45 Saturday
 */
@Data
@Accessors(chain = true)
public class UserInfoResp {
    private String userId;
    private String name;
    private String username;
    private String gender;
    private String email;
    private String phoneNumber;
    private String classNum;
    private String major;
    private String academy;
    private String campus;
    private Date lastLogin;
    private String status;
}
