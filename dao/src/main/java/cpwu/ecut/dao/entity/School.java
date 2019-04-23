package cpwu.ecut.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.inter.entity
 * 校区表
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/07 18:08 Sunday
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_SCHOOL",
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE_SCHOOL", columnNames = {"school_name", "status"})}
)
public class School {
    /**
     * 校区id，为学校则同school_id
     */
    @Id
    @Column(name = "campus_id", nullable = false, length = 64)
    private String campusId;
    /**
     * 学校id
     */
    @Column(name = "school_id", nullable = false, length = 64)
    private String schoolId;
    /**
     * 校区名称
     */
    @Column(name = "campus_name", nullable = false, length = 128)
    private String campusName;
    /**
     * 学校名称
     */
    @Column(name = "school_name", nullable = false, length = 64)
    private String schoolName;
    /**
     * 地址
     */
    @Column(length = 256)
    private String address;
    /**
     * 描述
     */
    @Column(length = 1024)
    private String about;
    /**
     * 电话
     */
    @Column(name = "phone_number", length = 16)
    private String phoneNumber;
    /**
     * 学校图标
     */
    @Column(length = 256)
    private String icon;
    /**
     * 状态：0：无效, 1：正常, 2：已冻结, 3：已注销
     *
     * @see cpwu.ecut.common.constant.enums.AccountStatusEnum
     */
    @Column(nullable = false, columnDefinition = "int(11) default 1")
    private Integer status;
    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 创建人id
     */
    @Column(name = "creator_id", nullable = false, length = 64)
    private String creatorId;
    /**
     * 记录状态：0：已删除1：有效
     *
     * @see cpwu.ecut.common.constant.enums.RecordStatusEnum
     */
    @Column(name = "record_status", nullable = false, columnDefinition = "int(11) default 1")
    private Integer recordStatus;
}
