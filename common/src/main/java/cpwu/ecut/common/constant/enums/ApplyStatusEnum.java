package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 流程状态
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 22:28 Friday
 */
@Getter
@AllArgsConstructor
public enum ApplyStatusEnum implements EnumInter {
    PROCESSING(0, "处理中"),
    PASSED(1, "已通过"),
    REJECTED(2, "已拒绝"),
    CANCELED(3, "已取消");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
