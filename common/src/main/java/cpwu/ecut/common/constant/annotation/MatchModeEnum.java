package cpwu.ecut.common.constant.annotation;

import cpwu.ecut.common.constant.enums.EnumInter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.annotation
 * 权限匹配模式枚举
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/19 15:21 星期五
 */
@Getter
@AllArgsConstructor
public enum MatchModeEnum implements EnumInter {
    MIN(100, "至少"),
    JUST(101, "仅此"),;

    private Integer code;

    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
