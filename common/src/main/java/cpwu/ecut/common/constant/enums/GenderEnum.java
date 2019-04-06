package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 性别
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 19:11 Friday
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements EnumInter {
    UNKNOWN(-1, "未知"),
    FEMALE(0, "女"),
    MALE(1, "男");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
