package cpwu.ecut.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

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
}
