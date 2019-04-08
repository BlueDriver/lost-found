package cpwu.ecut.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.inter.entity
 * 管理员表
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/07 19:10 Sunday
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_MANAGER",
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE_MANAGER", columnNames = {"campus_id", "user_id"})}
)
public class Manager {
    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;
    /**
     * 校区id（或学校）
     */
    @Column(name = "campus_id", nullable = false, length = 64)
    private String campusId;
    /**
     * user_id
     */
    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;
    /**
     * 联系地址
     */
    @Column(nullable = false, length = 128)
    private String address;
    /**
     * 电话
     */
    @Column(name = "phone_number", nullable = false, length = 16)
    private String phoneNumber;
    /**
     * 邮件
     */
    @Column(nullable = false, length = 256)
    private String email;
    /**
     * 创建人id
     */
    @Column(name = "creator_id", nullable = false, length = 64)
    private Date creatorId;
    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
}
