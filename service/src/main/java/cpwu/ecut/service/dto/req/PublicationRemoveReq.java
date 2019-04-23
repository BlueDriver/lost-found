package cpwu.ecut.service.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.service.dto.req
 * 删除启事
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/18 15:37 Thursday
 */
@Data
@Validated
@NoArgsConstructor
public class PublicationRemoveReq {
    @NotNull
    @Size(min = 1, max = 20, message = "id个数必须介于1-20")
    private List<String> idList;
}
