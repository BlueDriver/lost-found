package cpwu.ecut.service.dto.resp;

import lombok.Data;

/**
 * lost-found
 * cpwu.ecut.web.controller.dto.resp
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/08 13:43 Monday
 */
@Data
public class SchoolListResp {
    /**
     * 学校id
     */
    private String schoolId;
    /**
     * 学校名
     */
    private String schoolName;
}