package cpwu.ecut.service.dto.resp;

import cpwu.ecut.common.utils.CommonUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.service.dto.resp
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/08 15:31 Monday
 */
@Data
@Accessors(chain = true)
public class StudentRecognizeResp {
    private String studentNum;
    private String realName;
    private String icon;
    private String schoolName;
    private Integer kind;
    private String email;
    private Integer gender;
    private String createTime;
    private String lastLogin;

    public StudentRecognizeResp setCreateTime(Date date) {
        this.createTime = CommonUtils.getFormatDateTime(date);
        return this;
    }

    public StudentRecognizeResp setLastLogin(Date date) {
        this.lastLogin = CommonUtils.getFormatDateTime(date);
        return this;
    }
}
