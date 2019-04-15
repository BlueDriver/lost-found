package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 申请类型
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 22:22 Friday
 */
@Getter
@AllArgsConstructor
public enum ApplyKindEnum implements EnumInter {
    LOST_PUBLISH(0, "失物发布"),
    FOUND_PUBLISH(1, "认领发布"),
    IDENTITY_VERIFY(2, "身份认证"),
    ITEM_CLAIM(3, "物品认领"),
    ACCOUNT_APPEAL(4, "账号申诉");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
