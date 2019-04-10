package cpwu.ecut.service.bo.user;

import lombok.Data;

/**
 * lost-found
 * cpwu.ecut.service.bo.user
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/08 09:24 Monday
 */
@Data
public class UserSignInBO {
    private String username;
    private String password;
    private Integer kind;
}
