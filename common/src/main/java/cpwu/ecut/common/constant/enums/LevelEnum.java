package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 级别
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 22:16 Friday
 */
@Getter
@AllArgsConstructor
public enum LevelEnum implements EnumInter {
    SYSTEM(0, "系统级别"),
    SCHOOL(1, "学校级别"),
    CAMPUS(2, "校区级别"),
    PERSONAL(3, "个人级别");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
