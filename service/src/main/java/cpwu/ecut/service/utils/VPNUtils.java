package cpwu.ecut.service.utils;

import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.constant.enums.GenderEnum;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.dao.entity.Student;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * lost-found
 * cpwu.ecut.service.utils
 * 用户VPN相关工具类（仅限ECUT）
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/06 11:04 Saturday
 */
@Service
public class VPNUtils {
    /**
     * VPN1登录地址
     */
    private static final String VPN1_URL = "https://webvpn1.ecit.cn/users/sign_in";
    /**
     * 连接超时，秒
     */
    private static final int TIME_OUT = 5_000;

    /**
     * 登录VPN1
     */
    public Map<String, String> getVPNCookies(String username, String password) throws Exception {
        Connection con1 = Jsoup.connect(VPN1_URL).timeout(TIME_OUT)
                .referrer(VPN1_URL).validateTLSCertificates(false);
        Connection.Response resp1 = con1.execute();
        Document doc1 = Jsoup.parse(resp1.body());//VPN登录页
        Elements inputs = doc1.getElementsByTag("input");
        Map<String, String> data1 = new HashMap<>(inputs.size());
        for (Element e : inputs) {
            data1.put(e.attr("name"), e.attr("value"));
        }
        data1.put("user[login]", username);
        data1.put("user[password]", password);

        Connection con2 = Jsoup.connect(VPN1_URL).timeout(TIME_OUT);
        con2.validateTLSCertificates(false).followRedirects(true);
        Connection.Response resp2 = con2.ignoreContentType(true).cookies(resp1.cookies()).data(data1)
                .method(Connection.Method.POST).execute();
        Document doc2 = Jsoup.parse(resp2.body());
        //System.err.println(doc2);功能页面
        if (doc2.getElementsByTag("input").size() == inputs.size()) {
            throw ExceptionUtils.createException(ErrorEnum.PASSWORD_USERNAME_ERROR, username);
        }
        //VPN的Cookies
        return resp2.cookies();
    }

    /**
     * 门户登录地址
     */
    private static final String LOGIN_URL = "https://portal.webvpn1.ecit.cn:443";

    /**
     * 登录门户
     */
    public Map<String, String> getLoginCookies(String username, String password) throws Exception {
        Map<String, String> vpnCookies = getVPNCookies(username, password);

        Connection con1 = Jsoup.connect(LOGIN_URL).timeout(TIME_OUT);
        con1.validateTLSCertificates(false).followRedirects(true);
        Connection.Response resp1 = con1.ignoreContentType(true).cookies(vpnCookies)
                .method(Connection.Method.POST).execute();
        String url = resp1.url().toString();
        Document doc1 = Jsoup.parse(resp1.body());//登录页面

        Elements inputs = doc1.getElementsByTag("input");
        Map<String, String> loginData = new HashMap<>(inputs.size());
        for (Element e : inputs) {
            loginData.put(e.attr("name"), e.attr("value"));
        }
        loginData.put("username", username);
        loginData.put("password", password);

        vpnCookies.putAll(resp1.cookies());

        Connection con2 = Jsoup.connect(url).validateTLSCertificates(false).timeout(TIME_OUT)
                .data(loginData).cookies(vpnCookies).method(Connection.Method.POST);
        Connection.Response resp2 = con2.execute();
        Document doc2 = Jsoup.parse(resp2.body());

        vpnCookies.putAll(resp2.cookies());

        //System.err.println(doc2);
        Elements links = doc2.getElementsByClass("pLink");
        if (links.size() == 0) {
            throw ExceptionUtils.createException(ErrorEnum.PASSWORD_USERNAME_ERROR, username);
        }

        String jw = "https://jw.webvpn1.ecit.cn";

        Connection con3 = Jsoup.connect(jw).timeout(6000);
        Connection.Response resp3 = con3.validateTLSCertificates(false).method(Connection.Method.GET)
                .cookies(vpnCookies).followRedirects(true).execute();
        Document doc3 = Jsoup.parse(resp3.body());//带凭证的链接页面

        vpnCookies.putAll(resp3.cookies());

        Element href = doc3.getElementsByTag("a").first();
        Connection con4 = Jsoup.connect(href.attr("href")).timeout(TIME_OUT);
        Connection.Response resp4 = con4.cookies(vpnCookies).timeout(TIME_OUT).method(Connection.Method.GET)
                .execute();
        //Document doc4 = Jsoup.parse(resp4.body());//教务系统frame

        vpnCookies.putAll(resp4.cookies());
        //所有cookie
        return vpnCookies;
    }

    /**
     * 学籍信息地址
     */
    private static final String INFO_URL = "https://jw.webvpn1.ecit.cn/xjInfoAction.do?oper=xjxx";

    /**
     * 获得学生学籍信息
     */
    public Student getStudentInfo(String username, String password) throws Exception {
        Map<String, String> vpnCookies = getLoginCookies(username, password);

        Connection con = Jsoup.connect(INFO_URL).timeout(TIME_OUT);
        Connection.Response resp3 = con.validateTLSCertificates(false).method(Connection.Method.GET)
                .cookies(vpnCookies).followRedirects(true).execute();

        Document doc = Jsoup.parse(resp3.body());//学籍信息页
        //System.err.println(doc);

        //基本信息table1
        Elements table1 = doc.select("body > table.fieldsettop > tbody > tr > td > table:nth-child(1)");
        Element t1 = table1.select("#tblView").get(0);
        Student student = new Student();
        //student_num学号
        String studentNum = t1.select("tbody > tr.odd > td:nth-child(2)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setStudentNum(studentNum);
        //name姓名
        String name = t1.select("tbody > tr.odd > td:nth-child(4)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setName(name);
        //gender性别：-1未知，0女，1男
        String genderString = t1.select("tbody > tr:nth-child(4) > td:nth-child(2)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setGender(GenderEnum.MALE.getDesc().equals(genderString) ?
                GenderEnum.MALE.getCode() : GenderEnum.FEMALE.getCode());
        //id_card_num身份证号
        String idCardNum = t1.select("tbody > tr:nth-child(3) > td:nth-child(4)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setIdCardNum(idCardNum);
        //student_kind学生类别
        String studentKind = t1.select("tbody > tr:nth-child(4) > td:nth-child(4)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setStudentKind(studentKind);
        //nation民族
        String nation = t1.select("tbody > tr:nth-child(6) > td:nth-child(2)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setNation(nation);

        //在校信息table2
        Elements table2 = doc.select("body > table.fieldsettop > tbody > tr > td > table:nth-child(2)");
        Element t2 = table2.select("#tblView").get(0);
        //academy学院
        String academy = t2.select("tbody > tr:nth-child(1) > td:nth-child(4)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setAcademy(academy);
        //major专业
        String major = t2.select("tbody > tr:nth-child(2) > td:nth-child(2)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setMajor(major);
        //class_num班级
        String classNum = t2.select("tbody > tr:nth-child(3) > td:nth-child(4)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setClassNum(classNum);
        //campus_name校区名称
        String campusName = t2.select("tbody > tr:nth-child(4) > td:nth-child(2)").get(0).html()
                .replaceAll("&nbsp;", "").trim();
        student.setCampusName(campusName);
        student.setCreateTime(new Date());

        return student;
    }
}