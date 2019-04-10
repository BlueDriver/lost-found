package cpwu.ecut.service.inter.impl;

import cpwu.ecut.common.utils.FieldUtils;
import cpwu.ecut.dao.entity.School;
import cpwu.ecut.dao.inter.SchoolDAO;
import cpwu.ecut.service.dto.resp.SchoolListResp;
import cpwu.ecut.service.inter.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * lost-found
 * cpwu.ecut.service.inter.impl
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/08 13:55 Monday
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDAO schoolDAO;

    @Override
    public List<SchoolListResp> getSchools() {
        List<School> schoolList = schoolDAO.getSchools();
        return FieldUtils.copyProperties(schoolList, SchoolListResp.class);
    }
}
