package cpwu.ecut.service.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * lost-found
 * cpwu.ecut.service.dto.req
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/17 13:38 Wednesday
 */
@Data
@Validated
public class CommentAddReq {
    @NotBlank(message = "评论目标id不能为空")
    private String targetId;

    @NotBlank(message = "评论内容不能为空")
    @Length(max = 128, min = 1)
    private String content;
}
