package cpwu.ecut.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.service.dto.resp
 * 通知
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/21 19:35 Sunday
 */
@Data
@Accessors(chain = true)
public class NoticeListResp {
//    private String icon;
    /**
     * id
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 详情
     */
    private String content;
    /**
     * 时间
     */
    private Date time;
    /**
     * 置顶
     *
     * @see cpwu.ecut.common.constant.enums.YesNoEnum
     */
    private Integer fixTop;
}
