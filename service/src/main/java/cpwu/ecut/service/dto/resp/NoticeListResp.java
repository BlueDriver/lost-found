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
 * @date 2019/04/21 19:35 Sunday
 */
@Data
@Accessors(chain = true)
public class NoticeListResp {
//    private String icon;
    private String id;
    private String title;
    private String content;
    private Date time;
    private Integer fixTop;
}
