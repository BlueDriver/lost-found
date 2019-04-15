package cpwu.ecut.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.dao.entity
 * T_NOTICE（公告信息/轮播表）
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 09:01 Monday
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_NOTICE")
public class Notice {
    /**
     * id
     */
    @Id
    @Column(nullable = false, length = 64)
    private String id;
    /**
     * 类型：0公告，1轮播
     *
     * @see cpwu.ecut.common.constant.enums.NoticeKindEnum
     */
    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private Integer kind;
    /**
     * 标题
     */
    @Column(nullable = false, length = 128)
    private String title;
    /**
     * 链接
     */
    @Column(nullable = true, length = 256)
    private String link;
    /**
     * 图片
     */
    @Column(nullable = true, length = 256)
    private String image;
    /**
     * 公告内容（HTML）
     */
    @Column(nullable = false, length = 1024)
    private String content;
    /**
     * 级别类型：
     * 0：系统级别（所有人接收）
     * 1：校园级别（所有该校的人接收）
     * 2：校区级别（该校区）
     * 3：个人级别（个人）
     * 4：所有管理员（校园管理员，校区管理员）
     * 5：所有普通用户（除了管理员以外的普通用户，如学生)
     *
     * @see cpwu.ecut.common.constant.enums.LevelEnum
     */
    @Column(nullable = false, columnDefinition = "int(11)")
    private Integer level;
    /**
     * 是否置顶
     * 0: no
     * 1: yes
     *
     * @see cpwu.ecut.common.constant.enums.YesNoEnum
     */
    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private Integer fixTop;
    /**
     * 目标用户id, 系统级别为"system"
     *
     * @see cpwu.ecut.common.constant.SystemProperties
     */
    @Column(nullable = false, length = 64)
    private String targetId;
    /**
     * 开始展示时间
     * 为空则表示无时间限制
     */
    @Column(nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startTime;
    /**
     * 结束展示时间
     * 为空则表示无时间限制
     */
    @Column(nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endTime;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 创建人id
     */
    @Column(nullable = false, length = 64)
    private String creatorId;
    /**
     * 状态
     * 0：无效
     * 1：正常
     * 2：未开始
     * 3：已过期
     *
     * 定时器执行状态更新
     *
     *
     * @see cpwu.ecut.common.constant.enums.EffectEnum
     */
    /**
     * todo: 4/15/2019,015 09:41 AM
     * 定时任务
     */
    private String status;
    /**
     * 记录状态：0：已删除1：有效
     *
     * @see cpwu.ecut.common.constant.enums.RecordStatusEnum
     */
    @Column(name = "record_status", nullable = false, columnDefinition = "int(11) default 1")
    private Integer recordStatus;
}
