package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 账号状态
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 21:11 Friday
 */
@Getter
@AllArgsConstructor
public enum AccountStatusEnum implements EnumInter {
    PRE(0, "未激活"),
    NORMAL(1, "正常"),
    FREEZE(2, "已冻结"),
    CANCEL(3, "已注销"),
    CHECKING(4, "审核中");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
