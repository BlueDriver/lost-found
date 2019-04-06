package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 发布物状态
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 23:10 Friday
 */
@Getter
@AllArgsConstructor
public enum PublicationStatusEnum implements EnumInter {
    WAITING(0, "待审批"),
    FINDING(1, "寻回中"),//寻回中（审批通过）
    REJECTED(2, "驳回"),//驳回（审批不通过）
    CLAIMED(3, "已寻回"),
    CLOSED(4, "已关闭");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

