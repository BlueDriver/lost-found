package cpwu.ecut.service.inter;

import cpwu.ecut.service.dto.req.PublicationAddReq;
import cpwu.ecut.service.dto.req.PublicationListReq;
import cpwu.ecut.service.dto.resp.PublicationDetail;
import cpwu.ecut.service.dto.resp.PublicationPageResp;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * lost-found
 * cpwu.ecut.service.inter
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 21:07 Monday
 */
public interface LostFoundService {
    /**
     * 发布启事
     */
    void add(PublicationAddReq req, HttpSession session) throws Exception;

    /**
     * 查询列表
     */
    PublicationPageResp page(PublicationListReq req, HttpSession session) throws Exception;

    /**
     * 查看启事详情
     */
    PublicationDetail detail(String id) throws Exception;

    /**
     * 删除启事（软删除）
     */
    void removeLostFound(List<String> idList, HttpSession session) throws Exception;
}
