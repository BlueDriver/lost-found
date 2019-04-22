package cpwu.ecut.service.inter;

import cpwu.ecut.service.dto.req.NoticeAddReq;
import cpwu.ecut.service.dto.resp.NoticeListResp;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.service.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/21 19:34 Sunday
 */
public interface NoticeService {
    /**
     * 增加通知
     */
    void noticeAdd(NoticeAddReq req, HttpSession session) throws Exception;

    /**
     * 查询通知列表
     */
    List<NoticeListResp> noticeList(HttpSession session) throws Exception;

    /**
     * 删除通知
     */
    void noticeDelete(String id) throws Exception;

    /**
     * 通知置顶切换
     */
    void noticeSwitch(String id) throws Exception;
}
