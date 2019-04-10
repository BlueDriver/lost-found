package cpwu.ecut.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.inter.entity
 * 学生信息表
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/06 14:58 Saturday
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "T_STUDENT",
//        indexes = {
//                @Index(name = "INDEX_STD", columnList = "user_id,student_num")},
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE_STUDENT", columnNames = {"user_id", "student_num"})}
)
public class Student {
    /**
     * 学生ID
     */
    @Id
    @Column(name = "id", nullable = false, length = 64)
    private String id;
    /**
     * 学校id
     */
    @Column(name = "school_id", nullable = false, length = 64)
    private String schoolId;
    /**
     * 对应T_USER的id
     */
    @Column(name = "user_id", nullable = false, length = 64)
    private String userId;
    /**
     * 学号
     */
    @Column(name = "student_num", nullable = false, length = 32)
    private String studentNum;
    /**
     * 姓名
     */
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    /**
     * 性别：-1未知，0女，1男     *
     *
     * @see cpwu.ecut.common.constant.enums.GenderEnum
     */
    @Column(nullable = false, columnDefinition = "int(11) default -1")
    private Integer gender;
    /**
     * 身份证号
     */
    @Column(name = "id_card_num", nullable = false, length = 64)
    private String idCardNum;
    /**
     * 学生类别
     */
    @Column(name = "student_kind", nullable = false, length = 32)
    private String studentKind;
    /**
     * 民族
     */
    @Column(nullable = false, length = 32)
    private String nation;
    /**
     * 学院
     */
    @Column(nullable = false, length = 128)
    private String academy;
    /**
     * 专业
     */
    @Column(nullable = false, length = 128)
    private String major;
    /**
     * 班级
     */
    @Column(name = "class_num", nullable = false, length = 32)
    private String classNum;
    /**
     * 校区名称
     */
    @Column(name = "campus_name", nullable = false, length = 128)
    private String campusName;
    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;
}
