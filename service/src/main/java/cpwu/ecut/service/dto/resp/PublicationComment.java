package cpwu.ecut.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.service.dto.resp
 * 评论
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/17 09:31 Wednesday
 */
@Data
@Accessors(chain = true)
public class PublicationComment {
    /**
     * id
     */
    private String id;
    /**
     * 头像
     */
    private String icon;

    /**
     * 用户名（学号等）
     */
    private String username;
    /**
     * 时间
     */
    private Date time;

    /**
     * 内容
     */
    private String content;
}
