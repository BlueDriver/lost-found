package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 消息状态
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 23:03 Friday
 */
@Getter
@AllArgsConstructor
public enum MessageStatusEnum implements EnumInter {
    UNREAD(0, "未读"),
    HAS_READ(1, "已读");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}

