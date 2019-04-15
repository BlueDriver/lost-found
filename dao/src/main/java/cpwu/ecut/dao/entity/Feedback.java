package cpwu.ecut.dao.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.dao.entity
 * T_FEEDBACK（反馈表）
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 10:59 Monday
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_FEEDBACK")
public class Feedback {
    /**
     * id
     */
    @Id
    @Column(nullable = false, length = 64)
    private String id;
    /**
     * 反馈类型
     * 0：使用反馈
     * 1：举报
     *
     * @see cpwu.ecut.common.constant.enums.FeedbackKindEnum
     */
    @Column(nullable = false, columnDefinition = "int(11)")
    private Integer kind;
    /**
     * 主题贴id（互动回复或招领id）
     */
    @Column(nullable = true, length = 64)
    private String targetId;
    /**
     * 用户id
     */
    @Column(nullable = false, length = 64)
    private String userId;
    /**
     * 主题
     */
    @Column(nullable = false, length = 256)
    private String subject;
    /**
     * 内容
     */
    @Column(nullable = false, length = 1024)
    private String content;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 状态
     * 0：未读
     * 1：已读
     */
    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private String status;
    /**
     * 处理人id
     */
    @Column(nullable = true, length = 64)
    private String handlerId;
    /**
     * 回复内容
     */
    @Column(nullable = true, length = 1024)
    private String answer;
    /**
     * 处理时间
     */
    @Column(nullable = true)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date handleTime;
    /**
     * /**
     * 记录状态：0：已删除1：有效
     *
     * @see cpwu.ecut.common.constant.enums.RecordStatusEnum
     */
    @Column(name = "record_status", nullable = false, columnDefinition = "int(11) default 1")
    private Integer recordStatus;
}
