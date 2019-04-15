package cpwu.ecut.service.inter.impl;

import cpwu.ecut.dao.entity.LostFound;
import cpwu.ecut.dao.entity.User;
import cpwu.ecut.service.dto.req.PublicationAddReq;
import cpwu.ecut.service.inter.LostFoundService;

import javax.servlet.http.HttpSession;

/**
 * lost-found
 * cpwu.ecut.service.inter.impl
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/15 21:09 Monday
 */
public class LostFoundServiceImpl implements LostFoundService {
    @Override
    public void add(PublicationAddReq req, HttpSession session) {
        User user = (User) session.getAttribute("user");
        LostFound lostFound = new LostFound();
    }
}
