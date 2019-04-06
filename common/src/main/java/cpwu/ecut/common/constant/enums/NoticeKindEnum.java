package cpwu.ecut.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 公告类别
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 22:19 Friday
 */
@Getter
@AllArgsConstructor
public enum NoticeKindEnum implements EnumInter {
    TEXT_NOTICE(0, "文字公告"),
    IMAGE_DISPLAY(1, "图片轮播");

    private Integer code;
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
