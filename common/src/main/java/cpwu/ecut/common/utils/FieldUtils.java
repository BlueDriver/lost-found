package cpwu.ecut.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.common.utils
 * 字段处理工具类
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/08 14:04 Monday
 */
public class FieldUtils {
    /**
     * 复制List中对象的属性到新的List中
     */
    public static <E, F> List<F> copyProperties(List<E> source, Class<F> target) {
        List<F> list = new ArrayList<>(source.size());
        try {
            F obj;
            for (E s : source) {
                obj = target.newInstance();
                BeanUtils.copyProperties(s, obj);
                list.add(obj);
            }
            return list;
        } catch (IllegalAccessException | InstantiationException e) {
            return list;
        }
    }
}