package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 12:43 Monday
 */
@Getter
@AllArgsConstructor
public enum ActionEnum implements EnumInter {
    CREATE(0, "新增"),
    READ(1, "查询"),
    UPDATE(2, "更新"),
    DELETE(3, "删除"),;
    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }

}
