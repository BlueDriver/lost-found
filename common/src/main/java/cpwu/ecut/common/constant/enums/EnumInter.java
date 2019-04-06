package cpwu.ecut.common.constant.enums;

/**
 * lost-found
 * cpwu.ecut.common.constant.enums
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 18:30 Friday
 */
public interface EnumInter {
    Integer getCode();

    String getDesc();

    boolean equals(Integer code);
}
