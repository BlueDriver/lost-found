package cpwu.ecut.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * lost-found
 * cpwu.ecut.service.dto.req
 * 反馈回复req
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/22 19:06 Monday
 */
@Data
@NoArgsConstructor
@Validated
public class FeedbackReplyReq {
    @NotBlank
    private String id;

    @NotEmpty(message = "回复内容不能为空")
    @Length(min = 1, max = 1024, message = "回复内容长度在1-1024之间")
    private String content;
}
