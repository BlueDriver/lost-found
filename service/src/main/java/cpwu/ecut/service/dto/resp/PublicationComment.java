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
 * @date 2019/04/17 09:31 Wednesday
 */
@Data
@Accessors(chain = true)
public class PublicationComment {
    private String id;

    private String icon;

    private String username;

    private Date time;

    private String content;
}
