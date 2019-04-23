package cpwu.ecut.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * lost-found
 * cpwu.ecut.dao.entity
 * Category复合主键
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/11 14:27 Thursday
 * @see Category
 */
@Data
@Accessors(chain = true)
public class CategoryKey implements Serializable {
    private String name;
    private Integer level;
    private String targetId;
}
