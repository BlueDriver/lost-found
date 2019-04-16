package cpwu.ecut.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.service.dto.resp
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/16 13:59 Tuesday
 */
@Data
@Accessors(chain = true)
public class PublicationItem {
    private String id;

    private String icon;

    private Integer kind;

    private String username;

    private Date time;

    private String location;

    private String title;

    private String about;

    private List<String> images;

    private String category;

    private Integer lookCount;

    private Long commentCount;
}
