package cpwu.ecut.common.constant.enums;

import cpwu.ecut.common.utils.ExceptionUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 反馈类型
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 11:07 Monday
 */
@Getter
@AllArgsConstructor
public enum FeedbackKindEnum implements EnumInter {
    USAGE(0, "使用反馈"),
    REPORT(1, "举报"),
//    COMPLAIN(2, "投诉"),
    ;
    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }

}
