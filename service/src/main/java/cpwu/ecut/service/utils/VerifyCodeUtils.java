package cpwu.ecut.service.utils;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * parent
 * demo.web.utils
 * 随机验证码生成工具类
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/04/03 14:39 Wednesday
 */
@Service
public class VerifyCodeUtils {
    /**
     * 宽度px
     */
    private static final int WIDTH = 250;
    /**
     * 高度px
     */
    private static final int HEIGHT = 100;

    /**
     * 根据code生成图片
     */
    public BufferedImage getImage(String code) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);

        Graphics g = image.getGraphics();

        drawBackground(g, Color.WHITE);

        drawBorder(g, Color.BLUE);

        drawRandomLine(g, code.length() * 4);

        drawRandomNumber((Graphics2D) g, code);

        return image;
    }

    /**
     * 绘制背景色
     */
    private void drawBackground(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(0, 0, WIDTH, HEIGHT);

    }

    /**
     * 绘制边框
     */
    private void drawBorder(Graphics g, Color color) {
        g.setColor(color);
        g.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
    }

    /**
     * 绘制随机干扰线
     */
    private void drawRandomLine(Graphics g, int lineCount) {
        Random random = new Random();
        for (int i = 1; i <= lineCount; i++) {
            g.setColor(getRandomColor());
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 绘制随机数
     */
    private void drawRandomNumber(Graphics2D g, String code) {
        // chinese [\u4e00-\u9f5a]
        g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, HEIGHT / 4 * 3));// linux: fc -list; win7: cmd
        // fonts
        int x = HEIGHT / 8;
        int y = HEIGHT / 2 + HEIGHT / 4;
        char[] num = code.toCharArray();
        for (char c : num) {
            g.setColor(getRandomColor());
            int d = new Random().nextInt() % 30;
            g.rotate(d * Math.PI / 180, x, y);
            g.drawString(c + "", x, y);
            g.rotate(-d * Math.PI / 180, x, y);
            x += WIDTH / (code.length() + 1);
        }
    }

    /**
     * 随机色
     */
    public Color getRandomColor() {
        return new Color(getRandomInt(255), getRandomInt(255), getRandomInt(255));
    }

    /**
     * 获取指定长度的随机整数
     */
    public String getRandomNum(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(getRandomInt(10));
        }
        return sb.toString();
    }

    /**
     * 获得指定范围内的随机整数，左开右闭[)
     */
    public int getRandomInt(int bound) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(bound);
    }
}
