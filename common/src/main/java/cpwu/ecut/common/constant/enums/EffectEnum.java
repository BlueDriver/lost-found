package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 有效性
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 21:37 Friday
 */
@Getter
@AllArgsConstructor
public enum EffectEnum implements EnumInter {
    INVALID(0, "无效"),
    VALID(1, "有效"),
    NOT_YET(2, "未开始"),
    EXPIRED(3, "已过期");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
