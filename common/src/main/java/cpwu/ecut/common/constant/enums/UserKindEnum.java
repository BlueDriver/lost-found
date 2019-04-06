package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 用户类型
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 19:15 Friday
 */
@Getter
@AllArgsConstructor
public enum UserKindEnum implements EnumInter {
    STUDENT(0, "学生"),
    STAFF(1, "教职工"),
    MANAGER(2, "管理员");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
