package cpwu.ecut.service.inter.impl;

import cpwu.ecut.common.constant.RecognizedSchool;
import cpwu.ecut.common.constant.enums.*;
import cpwu.ecut.common.utils.CommonUtils;
import cpwu.ecut.common.utils.EnumUtils;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.dao.entity.School;
import cpwu.ecut.dao.entity.Student;
import cpwu.ecut.dao.entity.User;
import cpwu.ecut.dao.inter.SchoolDAO;
import cpwu.ecut.dao.inter.StudentDAO;
import cpwu.ecut.dao.inter.UserDAO;
import cpwu.ecut.service.dto.req.StudentRecognizeReq;
import cpwu.ecut.service.dto.req.UserLoginReq;
import cpwu.ecut.service.dto.resp.StudentRecognizeResp;
import cpwu.ecut.service.dto.resp.UserInfoResp;
import cpwu.ecut.service.inter.UserService;
import cpwu.ecut.service.utils.MailSenderService;
import cpwu.ecut.service.utils.VPNUtils;
import cpwu.ecut.service.utils.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * lost-found
 * cpwu.ecut.service.inter.impl
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/05 12:20 Friday
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private VerifyCodeUtils codeUtils;

    @Autowired
    private VPNUtils vpnUtils;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SchoolDAO schoolDAO;

    @Value("${system.email}")
    private String systemEmail;

    @Value("${app.chinese.name}")
    private String appName;

    @Value("${user.activate.url}")
    private String activateUrl;

    @Autowired
    private MailSenderService mailSenderService;


    /**
     * 认证登录
     */
    @Transactional
    @Override
    public StudentRecognizeResp recognizeStudent(StudentRecognizeReq req, HttpSession session) throws Exception {
        //根据学号和学校id查出学生
        Student studentEx = new Student();
        studentEx.setStudentNum(req.getUsername())
                .setSchoolId(req.getSchoolId());
        Optional<Student> studentOptional = studentDAO.findOne(Example.of(studentEx));
        Student student = null;
        if (studentOptional.isPresent()) {//学生存在
            student = studentOptional.get();
        }

        //根据学校id查出学校
        School schoolEx = new School();
        schoolEx.setSchoolId(req.getSchoolId())
                .setStatus(AccountStatusEnum.NORMAL.getCode())
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
        Optional<School> schoolOptional = schoolDAO.findOne(Example.of(schoolEx));
        if (!schoolOptional.isPresent()) {
            //学校不存在
            throw ExceptionUtils.createException(ErrorEnum.SCHOOL_NOT_EXISTS,
                    req.getSchoolId());
        }
        //学校存在
        School school = schoolOptional.get();

        //是否为东华理工人？
        if (!RecognizedSchool.ECUT.equals(school.getSchoolName())) {
            //不支持认证登录
            throw ExceptionUtils.createException(ErrorEnum.SCHOOL_NOT_SUPPORT_REG,
                    school.getSchoolName());
        }

        //学生存在
        if (studentOptional.isPresent()) {
            Optional<User> userOptional = userDAO.findById(student.getUserId());
            //对应的用户不存在
            if (!userOptional.isPresent()) {
                //保存用户
                User user = saveUser(student, req.getEmail(), school);
                //发激活邮件
                sendActivateMail(user, school.getSchoolName());
                return null;
//                throw ExceptionUtils.createException(ErrorEnum.STUDENT_NUM_NO_USER_EXISTS,
//                        student.getStudentNum(), student.getUserId());
            }
            //用户存在
            User user = userOptional.get();
            if (AccountStatusEnum.PRE.equals(user.getStatus())) {
                //未激活
                user.setActivateCode(CommonUtils.getUUID());//重置激活码
                String initPassword = codeUtils.getRandomNum(6);//初始密码
                user.setPassword(CommonUtils.encodeByMd5(initPassword));//md5加密保存
                userDAO.saveAndFlush(user);
                user.setPassword(initPassword);
                //发送激活邮件
                sendActivateMail(user, school.getSchoolName());
                return null;
            } else if (AccountStatusEnum.NORMAL.equals(user.getStatus())) {
                //状态正常

                //验证登录
                vpnUtils.getStudentInfo(req.getUsername(), req.getPassword());

                //更新最后登录时间
                user.setLastLogin(new Date());
                userDAO.save(user);
                //session设置
                session.setAttribute("user", user);
                session.setAttribute("school", school);
                //session.setAttribute("student", student);

                //返回
                StudentRecognizeResp resp = new StudentRecognizeResp();
                resp.setIcon(user.getIcon())
                        .setRealName(user.getRealName())
                        .setStudentNum(student.getStudentNum())
                        .setSchoolName(school.getSchoolName())
                        .setKind(user.getKind())
                        .setEmail(user.getEmail())
                        .setGender(student.getGender())
                        .setCreateTime(user.getCreateTime())
                        .setLastLogin(user.getLastLogin());
                return resp;
            } else if (AccountStatusEnum.FREEZE.equals(user.getStatus())) {
                //冻结
                throw ExceptionUtils.createException(ErrorEnum.USER_HAS_FREEZE,
                        user.getUsername());
            } else {
                //非法状态
                throw ExceptionUtils.createException(ErrorEnum.USER_STATUS_ERROR,
                        user.getUsername(), user.getStatus());
            }
        }
        //学生不存在，新生
        //认证
        student = vpnUtils.getStudentInfo(req.getUsername(), req.getPassword());
        student.setId(CommonUtils.getUUID())
                .setSchoolId(school.getSchoolId())
                .setUserId(CommonUtils.getUUID());
        studentDAO.saveAndFlush(student);
        //保存用户
        User user = saveUser(student, req.getEmail(), school);
        //发激活邮件
        sendActivateMail(user, school.getSchoolName());
        return null;
    }

    /**
     * 激活
     */
    @Override
    public String activateUser(String code) {
        User userEx = new User();
        userEx.setActivateCode(code);
        Optional<User> userOptional = userDAO.findOne(Example.of(userEx));
        //存在
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (AccountStatusEnum.PRE.equals(user.getStatus())) {
                //未激活，激活之
                user.setStatus(AccountStatusEnum.NORMAL.getCode());
                userDAO.saveAndFlush(user);
                return "success";
            } else if (AccountStatusEnum.NORMAL.equals(user.getStatus())) {
                //已被激活
                return "already";
            }
            return "invalid";
        }
        //不存在
        return "notExists";
    }

    /**
     * 登录
     */
    @Override
    public StudentRecognizeResp loginUser(UserLoginReq req, HttpSession session) throws Exception {
        Optional<School> schoolOptional = schoolDAO.findById(req.getSchoolId());
        if (!schoolOptional.isPresent()) {
            //学校不存在
            throw ExceptionUtils.createException(ErrorEnum.SCHOOL_NOT_EXISTS, req.getSchoolId());
        }
        //学校存在
        School school = schoolOptional.get();

        User userEx = new User();

        if (CommonUtils.isEmail(req.getUsername())) {//邮箱
            userEx.setEmail(req.getUsername());//管理员
        } else {
            userEx.setUsername(req.getUsername());//学生
        }
        userEx.setSchoolId(req.getSchoolId());

        Optional<User> userOptional = userDAO.findOne(Example.of(userEx));
        //用户不存在
        if (!userOptional.isPresent()) {
            throw ExceptionUtils.createException(ErrorEnum.USER_NOT_EXISTS, req.getUsername());
        }

        //用户存在
        User user = userOptional.get();

        if (AccountStatusEnum.PRE.equals(user.getStatus())) {
            //未激活
            user.setActivateCode(CommonUtils.getUUID());//重置激活码
            String initPassword = codeUtils.getRandomNum(6);//初始密码
            user.setPassword(CommonUtils.encodeByMd5(initPassword));//md5加密保存
            userDAO.saveAndFlush(user);
            user.setPassword(initPassword);
            //发送激活邮件
            sendActivateMail(user, school.getSchoolName());
            return null;
        } else if (AccountStatusEnum.NORMAL.equals(user.getStatus())) {
            //密码不正确
            if (!CommonUtils.encodeByMd5(req.getPassword()).equals(user.getPassword())) {
                throw ExceptionUtils.createException(ErrorEnum.PASSWORD_USERNAME_ERROR, req.getUsername());
            }

            //更新最后登录时间
            user.setLastLogin(new Date());
            userDAO.save(user);
            //session设置
            session.setAttribute("user", user);
            session.setAttribute("school", school);

            //返回
            StudentRecognizeResp resp = new StudentRecognizeResp();
            resp.setIcon(user.getIcon())
                    .setRealName(user.getRealName())
                    .setStudentNum(user.getUsername())
                    .setSchoolName(school.getSchoolName())
                    .setKind(user.getKind())
                    .setEmail(user.getEmail())
                    .setPhoneNumber(user.getPhoneNumber())
                    .setGender(user.getGender())
                    .setCreateTime(user.getCreateTime())
                    .setLastLogin(user.getLastLogin());

            if (UserKindEnum.MANAGER.equals(user.getKind())) {//管理员
                resp.setIcon(school.getIcon());
            }


            return resp;
        } else if (AccountStatusEnum.FREEZE.equals(user.getStatus())) {
            //冻结
            throw ExceptionUtils.createException(ErrorEnum.USER_HAS_FREEZE,
                    user.getUsername());
        } else {
            //非法状态
            throw ExceptionUtils.createException(ErrorEnum.USER_STATUS_ERROR,
                    user.getUsername(), user.getStatus());
        }


    }

    /**
     * 创建并保存新用户
     */
    private User saveUser(Student student, String email, School school) throws UnsupportedEncodingException {
        User user = new User();
        String initPassword = codeUtils.getRandomNum(6);
        user.setId(student.getUserId())
                .setUsername(student.getStudentNum())
                .setActivateCode(CommonUtils.getUUID())
                .setPassword(CommonUtils.encodeByMd5(initPassword))
                .setEmail(email)
                .setRealName(student.getName())
                .setGender(student.getGender())
                .setSchoolId(school.getSchoolId())
                .setCampusId(school.getCampusId())
                .setCreateTime(new Date())
                .setKind(UserKindEnum.STUDENT.getCode())
                .setStatus(AccountStatusEnum.PRE.getCode())
                .setRecordStatus(RecordStatusEnum.EXISTS.getCode());
        userDAO.saveAndFlush(user);
        return user.setPassword(initPassword);
    }

    /**
     * 发送激活邮件
     */
    private void sendActivateMail(User user, String schoolName) throws IOException, MessagingException {
        //模板参数
        Map<String, Object> param = new HashMap<>();
        param.put("appName", appName);
        param.put("username", CommonUtils.replaceString(user.getUsername(), 4, 3));
        param.put("realName", CommonUtils.replaceString(user.getRealName(), 1, 0));
        param.put("password", user.getPassword());//初始密码
        param.put("schoolName", schoolName);
        param.put("link", activateUrl + user.getActivateCode());
        mailSenderService.sendTemplateMessage(user.getEmail(), param,
                "templates/mail/userActivate.html", "账号激活通知",
                systemEmail, appName);
    }


    /**
     * 用户信息
     */
    @Override
    public UserInfoResp userInfo(String userId) throws Exception {
        Optional<Student> studentOptional = studentDAO.findByUserIdEquals(userId);
        Optional<User> userOptional = userDAO.findById(userId);
        if (!studentOptional.isPresent() || !userOptional.isPresent()) {
            throw ExceptionUtils.createException(ErrorEnum.USER_NOT_EXISTS, userId);
        }

        Student student = studentOptional.get();
        User user = userOptional.get();
        UserInfoResp resp = new UserInfoResp();
        resp.setName(student.getName())
                .setUsername(student.getStudentNum())
                .setGender(GenderEnum.MALE.equals(student.getGender()) ?
                        GenderEnum.MALE.getDesc() : GenderEnum.FEMALE.getDesc())
                .setEmail(user.getEmail())
                .setPhoneNumber(user.getPhoneNumber())
                .setClassNum(student.getClassNum())
                .setMajor(student.getMajor())
                .setAcademy(student.getAcademy())
                .setCampus(student.getCampusName())
                .setLastLogin(user.getLastLogin())
                .setStatus(EnumUtils.getDesc(user.getStatus(), AccountStatusEnum.values()));
        return resp;
    }
}