package cpwu.ecut.service;

import cpwu.ecut.common.constant.enums.ErrorEnum;
import cpwu.ecut.common.constant.enums.GenderEnum;
import cpwu.ecut.common.utils.ExceptionUtils;
import cpwu.ecut.dao.entity.Student;
import cpwu.ecut.service.utils.VPNUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * lost-found
 * java.cpwu.ecut.service
 * 学生VPN登录测试
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/06 10:20 Saturday
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Test.class)
public class Test {
    String vpn1 = "https://webvpn1.ecit.cn/users/sign_in";
    String std = "201520180508";
    String pwd = "";
    VPNUtils vpnUtils = new VPNUtils();

    @org.junit.Test
    public void test1() throws Exception {
        String url = vpn1;
        Connection con1 = Jsoup.connect(url).timeout(6000);
        con1.referrer("https://webvpn1.ecit.cn/users/sign_in");
        con1.validateTLSCertificates(false);
        Response resp1 = con1.execute();
        Document doc1 = Jsoup.parse(resp1.body());
        Elements elements = doc1.getElementsByTag("abcdefg");
        System.out.println(elements.size());
        Map<String, String> data1 = new HashMap<>(5);
        for (Element e : elements) {
            String name = e.attr("name");
            String value = e.attr("value");
            System.out.println(name + ": " + value);
            data1.put(name, value);
        }
        data1.put("user[login]", std);
        data1.put("user[password]", pwd);

        Connection con2 = Jsoup.connect(url).timeout(10_000);
        con2.validateTLSCertificates(false);
        con2.followRedirects(true);
        Response resp2 = con2.ignoreContentType(true).cookies(resp1.cookies()).data(data1).
                method(Connection.Method.POST).execute();
        Document doc2 = Jsoup.parse(resp2.body());
        //System.err.println(doc2);功能页面
        if (doc2.getElementsByTag("input").size() == elements.size()) {
            throw new Exception("用户名或密码不正确！");
        }
        //VPN的Cookies
        Map<String, String> cookie = resp2.cookies();


        String oa = "https://portal.webvpn1.ecit.cn:443";
        //302重定向到https://cas-443.webvpn1.ecit.cn/index.jsp?service=https://portal.webvpn1.ecit.cn/Authentication
        //门户页

        Connection con3 = Jsoup.connect(oa).timeout(5_000).validateTLSCertificates(false).followRedirects(true)
                .cookies(cookie).method(Connection.Method.GET);

        Response resp3 = con3.execute();
        Document doc3 = Jsoup.parse(resp3.body());
        System.err.println(doc3);
    }


    String loginURL = "https://cas-443.webvpn1.ecit.cn/index.jsp?service=https://portal.webvpn1.ecit.cn/Authentication";
    String oa = "https://portal.webvpn1.ecit.cn:443";

    @org.junit.Test
    public void test2() throws Exception {
        Map<String, String> vpnCookies = vpnUtils.getVPNCookies(std, pwd);
        Connection con1 = Jsoup.connect(oa).timeout(5000);
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
        loginData.put("username", std);
        loginData.put("password", pwd);

        vpnCookies.putAll(resp1.cookies());

        Connection con2 = Jsoup.connect(url).validateTLSCertificates(false).timeout(5000)
                .data(loginData).cookies(vpnCookies).method(Connection.Method.POST);
        Response resp2 = con2.execute();

        vpnCookies.putAll(resp2.cookies());

        Document doc2 = Jsoup.parse(resp2.body());
//        System.err.println(doc2);
        Elements links = doc2.getElementsByClass("pLink");
        if (links.size() == 0) {
            throw ExceptionUtils.createException(ErrorEnum.PASSWORD_USERNAME_ERROR, std);
        }

        String jw = "https://jw.webvpn1.ecit.cn";

        Connection con3 = Jsoup.connect(jw).timeout(6000);
        Response resp3 = con3.validateTLSCertificates(false).method(Connection.Method.GET)
                .cookies(vpnCookies).followRedirects(true).execute();

        vpnCookies.putAll(resp3.cookies());

        Document doc3 = Jsoup.parse(resp3.body());//带凭证的链接页面

        Element href = doc3.getElementsByTag("a").first();

        Connection con4 = Jsoup.connect(href.attr("href")).timeout(6000);

        Response resp4 = con4.cookies(vpnCookies).timeout(8000).method(Connection.Method.GET).execute();

        Document doc4 = Jsoup.parse(resp4.body());//frame

        System.err.println(doc4);
    }

    String info_url = "https://jw.webvpn1.ecit.cn/xjInfoAction.do?oper=xjxx";

    @org.junit.Test
    public void test3() throws Exception {
        Map<String, String> vpnCookies = vpnUtils.getLoginCookies(std, pwd);

        Connection con = Jsoup.connect(info_url).timeout(6000);
        Response resp3 = con.validateTLSCertificates(false).method(Connection.Method.GET)
                .cookies(vpnCookies).followRedirects(true).execute();

        Document doc = Jsoup.parse(resp3.body());
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
        System.err.println(student);

    }
}
