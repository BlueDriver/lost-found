package cpwu.ecut.common.constant.annotation;

import cpwu.ecut.common.constant.enums.EnumInter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 * 服务码枚举（所有功能）
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/19 10:00 星期五
 */
@Getter
@AllArgsConstructor
public enum ServiceEnum implements EnumInter {
    //用户相关
    USER_REG(5000, "用户身份认证"),
    USER_ACTIVATE(5001, "用户激活"),
    USER_LOGIN(5002, "用户登录"),
    USER_LOGOUT(5003, "用户登出"),
    //招领相关
    LOST_FUND_PUB(5101, "发布招领信息"),
    LOST_FOUND_PAGE(5102, "分页查询招领列表"),
    LOST_FOUND_DETAIL(5103, "查看招领详情"),
    LOST_FOUND_DELETE(5104, "删除招领信息"),

    //评论相关
    COMMENT_LIST(5201, "查看招领评论列表"),
    COMMENT_PUB(5202, "发布评论"),
    COMMENT_MINE(5203, "查看与我相关的评论"),
    COMMENT_DELETE(5204, "删除评论"),
    /**
     * todo: 4/23/2019,023 10:26 AM
     * 补齐所有
     *
     */
    ;

    private Integer code;
    /**
     * 对应Log类的about
     */
    private String desc;

    @Override
    public boolean equals(Integer code) {
        return this.getCode().equals(code);
    }
}
