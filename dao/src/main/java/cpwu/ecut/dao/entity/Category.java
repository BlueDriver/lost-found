package cpwu.ecut.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.dao.entity
 * T_CATEGORY（物品类别表）
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/11 13:59 Thursday
 */
@Data
@Accessors(chain = true)
@Entity
@IdClass(CategoryKey.class)
@Table(name = "T_CATEGORY")
public class Category {
    /**
     * 类型名
     */
    @Id
    @Column(nullable = false, length = 128)
    private String name;
    /**
     * 说明
     */
    @Column(length = 256)
    private String about;
    /**
     * 图标
     */
    @Column(length = 256)
    private String image;
    /**
     * 创建人id
     */
    @Column(nullable = false, length = 256)
    private String creatorId;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 级别类型：0：系统级别, 1：校园级别, 2：校区级别
     *
     * @see cpwu.ecut.common.constant.enums.LevelEnum
     */
    @Id
    @Column(nullable = false, columnDefinition = "int(11)")
    private Integer level;
    /**
     * 作用对象id系统级别为“system”
     */
    @Id
    @Column(nullable = false, length = 64)
    private String targetId;

    /**
     * 记录状态：0：已删除1：有效
     *
     * @see cpwu.ecut.common.constant.enums.RecordStatusEnum
     */
    @Column(nullable = false, columnDefinition = "int(11) default 1")
    private Integer recordStatus;
}



