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
 * @date 2019/04/17 09:30 Wednesday
 */
@Data
@Accessors(chain = true)
public class PublicationDetail {
    private String id;

    private String icon;

    private Integer kind;

    private String userId;

    private String username;

    private String realName;

    private Date time;

    private String location;

    private String title;

    private String about;

    private List<String> images;

    private String category;

    private Integer lookCount;

    private Integer status;

    private Date dealTime;

    private Boolean isSelf;

    private String email;

    private String phoneNumber;

    //private List<PublicationComment> comments;
}
