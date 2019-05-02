package cpwu.ecut.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * lost-found
 * cpwu.ecut.service.dto.req
 * 查询启事列表
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/16 10:14 Tuesday
 */
@Data
@Validated
@NoArgsConstructor
public class PublicationListReq {
    /**
     * 类型
     */
    @NotNull
    private Integer kind;
    /**
     * 类别
     */
    @NotNull
    private String category;
    /**
     * 关键字
     */
    @NotNull
    private String keyword;
    /**
     * 学号
     */
    @NotNull
    private String username;
    /**
     * 页号
     */
    @NotNull
    private Integer pageNum;
    /**
     * 页大小
     */
    @NotNull
    @Max(value = 100, message = "每页限制最大100条")
    @Min(value = 1, message = "每页限制最小1条")
    private Integer pageSize;
}
