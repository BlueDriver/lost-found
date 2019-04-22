package cpwu.ecut.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

/**
 * lost-found
 * cpwu.ecut.service.dto.req
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/22 10:56 Monday
 */
@Data
@NoArgsConstructor
@Validated
public class FeedbackAddReq {
    @Length(min = 1, max = 128, message = "主题长度必须在1-128之间")
    private String subject;
    @Length(max = 1024, message = "反馈详情最大长度1024")
    private String content;
}
