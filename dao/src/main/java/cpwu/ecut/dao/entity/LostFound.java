package cpwu.ecut.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.dao.entity
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 10:19 Monday
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_LOST_FOUND",
        indexes = {
                @Index(name = "INDEX_LF", columnList = "title,category_id")}
)
public class LostFound {
    /**
     * id
     */
    @Id
    @Column(nullable = false, length = 64)
    private String id;
    /**
     * 用户id
     */
    @Column(nullable = false, length = 64)
    private String userId;
    /**
     * 校区id
     */
    @Column(nullable = false, length = 64)
    private String campusId;
    /**
     * 类型
     * 0: 失物发布
     * 1: 认领发布
     *
     * @see cpwu.ecut.common.constant.enums.ApplyKindEnum
     */
    @Column(nullable = false, columnDefinition = "int(11)")
    private Integer kind;
    /**
     * 标题
     */
    @Column(name = "title", nullable = false, length = 128)
    private String title;
    /**
     * 说明
     */
    @Column(nullable = false, length = 512)
    private String about;
    /**
     * 地点
     */
    @Column(nullable = true, length = 512)
    private String location;
    /**
     * 图片链接(json array)
     */
    @Column(nullable = true, length = 1024)
    private String images;
//    /**
//     * 图片2链接
//     */
//    @Column(nullable = true, length = 256)
//    private String image2;
//    /**
//     * 图片3链接
//     */
//    @Column(nullable = true, length = 256)
//    private String image3;
    /**
     * 类型id
     *
     * @see Category#name
     */
    @Column(name = "category_id", nullable = false, length = 128)
    private String categoryId;
    /**
     * 是否置顶
     * 0: no
     * 1: yes
     *
     * @see cpwu.ecut.common.constant.enums.YesNoEnum
     */
    @Column(name = "fix_top", nullable = false, columnDefinition = "int(11) default 0")
    private Integer fixTop;
    /**
     * 浏览次数
     */
    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private Integer lookCount;
    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 状态：<br />
     * 0：待审批<br/>
     * 1：寻回中（审批通过）<br/>
     * 2：驳回（审批不通过）<br />
     * 3：已寻回<br/>
     * 4：已关闭
     *
     * @see cpwu.ecut.common.constant.enums.PublicationStatusEnum
     */
    @Column(nullable = false, columnDefinition = "int(11)")
    private Integer status;
    /**
     * 处理完成时间
     */
    @Column(nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dealTime;
    /**
     * 记录状态：0：已删除1：有效
     *
     * @see cpwu.ecut.common.constant.enums.RecordStatusEnum
     */
    @Column(name = "record_status", nullable = false, columnDefinition = "int(11) default 1")
    private Integer recordStatus;
}
