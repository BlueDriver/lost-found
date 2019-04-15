package cpwu.ecut.service.inter;

import cpwu.ecut.service.dto.req.PublicationAddReq;

import javax.servlet.http.HttpSession;

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
    void add(PublicationAddReq req, HttpSession session);
}
