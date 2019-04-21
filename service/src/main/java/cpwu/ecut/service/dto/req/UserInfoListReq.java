package cpwu.ecut.service.dto.req;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * lost-found
 * cpwu.ecut.service.dto.req
 * 查询用户信息列表
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/21 10:47 Sunday
 */
@Data
@Validated
public class UserInfoListReq {

    @NotNull
    private String keyword;

    @NotNull
    private Integer pageNum;

    @NotNull
    @Max(value = 100, message = "每页限制最大100条")
    @Min(value = 1, message = "每页限制最小1条")
    private Integer pageSize;
}
