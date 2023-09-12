package com.fangzai.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerifyCodeServlet {
	static{
        System.setProperty("java.awt.headless", "true");
    }
	// 验证码图片的宽度。
    private int width = 80;
    // 验证码图片的高度。
    private int height = 40;
    // 验证码字符个数
    private int codeCount = 4;
    private int x = 0;
    // 字体高度
    private int fontHeight;
    private int codeY;
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'H', 'J',
            'K', 'L', 'M', 'N', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '3', '2', '2', '3', '4', '5', '6', '7', '8', '9' };
    private String randomsString = "null";
    /**
     * 初始化验证图片属性
     */
    public void init(){
        // 宽度
        String strWidth ="240";
        // 高度
        String strHeight ="90";
        // 字符个数
        String strCodeCount = "4";
        // 将配置的信息转换成数值
        try {
            if (strWidth != null && strWidth.length() != 0) {
                width = Integer.parseInt(strWidth);
            }
            if (strHeight != null && strHeight.length() != 0) {
                height = Integer.parseInt(strHeight);
            }
            if (strCodeCount != null && strCodeCount.length() != 0) {
                codeCount = Integer.parseInt(strCodeCount);
            }
        } catch (NumberFormatException e) {
        }
        x = width / (codeCount +2);
        fontHeight = height - 2;
        codeY = height - 14;
    }
    public BufferedImage getBufferedImage(){
    	 init();
    	// 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        Font font = new Font("Lucida Sans Typewriter", Font.PLAIN, fontHeight);

        // 设置字体。
        g.setFont(font);
        // 画边框。
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        randomsString	= String.valueOf(randomCode);
		return buffImg;
    }
    public String getVerifyCode(){
    	return randomsString;
    }
}
