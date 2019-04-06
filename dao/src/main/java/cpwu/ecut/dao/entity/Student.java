package cpwu.ecut.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * lost-found
 * cpwu.ecut.dao.entity
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/06 14:58 Saturday
 */
@Data
public class Student {
    /**
     * 学生ID
     */
    private String id;
    /**
     * 对应T_USER的id
     */
    private String userId;
    /**
     * 学号
     */
    private String studentNum;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别：-1未知，0女，1男
     *
     * @see cpwu.ecut.common.constant.enums.GenderEnum
     */
    private Integer gender;
    /**
     * 身份证号
     */
    private String idCardNum;
    /**
     * 学生类别
     */
    private String studentKind;
    /**
     * 民族
     */
    private String nation;
    /**
     * 学院
     */
    private String academy;
    /**
     * 专业
     */
    private String major;
    /**
     * 班级
     */
    private String classNum;
    /**
     * 校区名称
     */
    private String campusName;
    /**
     * 创建时间
     */
    private Date createTime;
}
