package cpwu.ecut.common.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


/**
 * parent
 * demo.web.utils
 * 公共工具类
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/03 15:09 Wednesday
 */
@Slf4j
public class CommonUtils {
    private static MessageDigest md5 = null;
    private static final BASE64Encoder base64en = new BASE64Encoder();

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("md5 init error", e);
        }
    }

    /**
     * md5编码加密
     *
     * @param sourceString: 需要被加密的文本
     * @return 加密后的文本
     * @throws UnsupportedEncodingException
     */
    public static String encodeByMd5(String sourceString) throws UnsupportedEncodingException {
        return base64en.encode(md5.digest(sourceString.getBytes("UTF-8")));
    }


    private static final SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获得当前时间yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDateTimeNow() {
        return sdfFull.format(System.currentTimeMillis());

    }

    /**
     * 格式化Date为yyyy-MM-dd HH:mm:ss
     */
    public static String getFormatDateTime(Date date) {
        return sdfFull.format(date);
    }

    /**
     * 格式化时间戳
     *
     * @param timeStamp
     */
    public static String getFormatDateTimeByTimeStamp(long timeStamp) {
        return sdfFull.format(timeStamp);
    }

    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * <P>将年月日字符串转成Date </p>
     *
     * @param dateString： 合乎规范的年月日字符串
     * @return Date
     */
    public static Date getDateByDateString(String dateString) {
        if (dateString == null) {
            return null;
        }
        Date date;
        try {
            date = sdfDate.parse(dateString);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    /**
     * 将Date转yyyy-MM-dd
     */
    public static String getDateStringByDate(Date date) {
        if (date == null) {
            return null;
        }
        return sdfDate.format(date);
    }

    public static Long getLongByDate(Date date) {
        if (date == null) return null;
        return date.getTime();
    }

    /**
     * 生成32位的UUID（去除-）
     * 8-4-4-4-12
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * <P>计算两个日期相隔天数 </p>
     *
     * @param before: 起始日期; then: 目标日期
     * @return 整数天
     */
    public static int getDifferenceDay(Date before, Date then) {
        Calendar last_c = Calendar.getInstance();
        last_c.setTime(before);
        Calendar now_c = Calendar.getInstance();
        now_c.setTime(then);
        int last_year = last_c.get(Calendar.YEAR);
        int now_year = now_c.get(Calendar.YEAR);
        if (last_year == now_year) {//the same year
            return now_c.get(Calendar.DAY_OF_YEAR) - last_c.get(Calendar.DAY_OF_YEAR);
        } else {
            //different year
            long length = then.getTime() - before.getTime();
            return (int) length / (1000 * 3600 * 24);
        }
    }

    /**
     * 获取request的IP地址
     *
     * @param request
     * @return IP address
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-real-ip");//先从nginx自定义配置获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 保留前几个和后几个字符，其他部分被替换
     * 用户隐私性文本处理
     * 如手机号：15911112222
     * 处理后为：159*****222
     *
     * @param source      源字符串
     * @param before      前几个将被保留
     * @param after       后几个将被保留
     * @param replacement 替换成的字符
     */
    public static String replaceString(String source, int before, int after, String replacement) {
        if (before + after >= source.length()) {
            return source;
        }
        StringBuffer sb = new StringBuffer(source.length());
        sb.append(source.substring(0, before));
        for (int i = 0, t = source.length() - before - after; i < t; i++) {
            sb.append(replacement);
        }
        sb.append(source.substring(source.length() - after));
        return sb.toString();
    }

    /**
     * 以*号替换部分字符
     *
     * @see CommonUtils#replaceString(String, int, int, String)
     */
    public static String replaceString(String source, int start, int end) {
        return replaceString(source, start, end, "*");
    }

    private static final String emailRegex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";

    /**
     * 校验是否为邮箱
     */
    public static boolean isEmail(String s) {
        return s.matches(emailRegex);
    }
}
