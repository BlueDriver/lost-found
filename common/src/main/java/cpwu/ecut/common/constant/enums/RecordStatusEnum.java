package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 数据库记录状态
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 19:21 Friday
 */
@Getter
@AllArgsConstructor
public enum RecordStatusEnum implements EnumInter {
    DELETED(0, "已删除"),
    EXISTS(1, "存在");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
