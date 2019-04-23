package cpwu.ecut.service.dto.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.service.dto.resp
 * 招领启事详情
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/17 09:30 Wednesday
 */
@Data
@Accessors(chain = true)
public class PublicationDetail {
    /**
     * id
     */
    private String id;
    /**
     * 发布人头像
     */
    private String icon;

    /**
     * 类型
     *
     * @see cpwu.ecut.common.constant.enums.ApplyKindEnum#LOST_PUBLISH
     * @see cpwu.ecut.common.constant.enums.ApplyKindEnum#FOUND_PUBLISH
     */
    private Integer kind;

    /**
     * 发布人id
     */
    private String userId;

    /**
     * 发布人用户名（学号等）
     */
    private String username;
    /**
     * 发布人真实姓名
     */
    private String realName;
    /**
     * 时间
     */
    private Date time;

    /**
     * 地点
     */
    private String location;
    /**
     * 标题
     */
    private String title;

    /**
     * 详细说明
     */
    private String about;

    /**
     * 图片地址（json array)
     */
    private List<String> images;
    /**
     * 类别名称
     */
    private String category;

    /**
     * 浏览量
     */
    private Integer lookCount;
    /**
     * 状态
     *
     * @see cpwu.ecut.common.constant.enums.PublicationStatusEnum
     */
    private Integer status;
    /**
     * 处理时间
     */
    private Date dealTime;
    /**
     * 是否为自己的？
     */
    private Boolean isSelf;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phoneNumber;
}
