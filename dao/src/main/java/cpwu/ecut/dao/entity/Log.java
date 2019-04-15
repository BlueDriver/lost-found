package cpwu.ecut.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.dao.entity
 * T_LOG（日志表）
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 12:04 Monday
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_LOG")
public class Log {
    /**
     * 日志id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//是自动增长型
    @Column(nullable = false)
    private Long id;
    /**
     * 用户id
     */
    @Column(nullable = false, length = 64)
    private String userId;
    /**
     * 动作
     */
    @Column(nullable = false, length = 64)
    private String actionName;
    /**
     * 操作对象名
     */
    @Column(nullable = false, length = 64)
    private String targetName;
    /**
     * 操作对象主键id
     */
    @Column(nullable = false, length = 64)
    private String targetId;
    /**
     * 说明
     */
    @Column(nullable = true, length = 256)
    private String about;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
}
