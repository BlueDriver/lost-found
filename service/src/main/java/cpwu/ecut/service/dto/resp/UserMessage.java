package cpwu.ecut.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.service.dto.resp
 * 用户的消息（于我相关的评论）
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/17 19:13 Wednesday
 */
@Data
@Accessors(chain = true)
public class UserMessage {
    private String id;

    private String userId;

    private String icon;

    private String username;

    private Date time;

    private String title;

    private String content;

}
