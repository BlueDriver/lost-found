package cpwu.ecut.service.dto.req;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * lost-found
 * cpwu.ecut.service.dto.req
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/16 10:14 Tuesday
 */
@Data
@Validated
public class PublicationListReq {
    @NotNull
    private Integer kind;

    @NotNull
    private String category;

    @NotNull
    private String keyword;

    @NotNull
    private String username;

    @NotNull
    private Integer pageNum;

    @NotNull
    @Max(value = 100, message = "每页限制最大100条")
    @Min(value = 1, message = "每页限制最小1条")
    private Integer pageSize;
}
