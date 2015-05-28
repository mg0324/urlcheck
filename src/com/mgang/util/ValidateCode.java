package com.mgang.util;
/**
 * @author 梅刚 2014-7-21
 * 来源于网上，借来用用
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateCode {

    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";//放到session中的key
    private static Random random = new Random();
    private static String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";//随机产生的字符串
    
    private static int width = 80;//图片宽
    private static int height = 23;//图片高
    private static int lineSize = 40;//干扰线数量
    private static int stringNum = 4;//随机产生字符数量
    private static int fontsize = 18;
    /*
     * 获得字体
     */
    private static Font getFont(){
        return new Font("Fixedsys",Font.CENTER_BASELINE,fontsize);
    }
    /*
     * 获得颜色
     */
    private static Color getRandColor(int fc,int bc){
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc-fc-16);
        int g = fc + random.nextInt(bc-fc-14);
        int b = fc + random.nextInt(bc-fc-18);
        return new Color(r,g,b);
    }
    /**
     * 生成随机图片
     */
    public static void getRandcode(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,fontsize));
        g.setColor(getRandColor(110, 133));
        //绘制干扰线
        for(int i=0;i<=lineSize;i++){
            drowLine(g);
        }
        //绘制随机字符
        String randomString = "";
        for(int i=1;i<=stringNum;i++){
            randomString=drowString(g,randomString,i);
        }
        session.removeAttribute(RANDOMCODEKEY);
        session.setAttribute(RANDOMCODEKEY, randomString);
        //System.out.println("validateCode : "+randomString);
        LogUtil.log.info("验证码 : "+randomString);
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * 绘制字符串
     */
    private static String drowString(Graphics g,String randomString,int i){
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString +=rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13*i, 16);
        return randomString;
    }
    /*
     * 绘制干扰线
     */
    private static void drowLine(Graphics g){
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x+xl, y+yl);
    }
    /*
     * 获取随机的字符
     */
    public static String getRandomString(int num){
        return String.valueOf(randString.charAt(num));
    }
    /**
     * 比对传进来的验证码和session中的是否一致(不区分大小写)
     * @param request
     * @return 一致就返回true,反之返回false
     */
    public static boolean checkInCode(HttpServletRequest request,String checkCode){
    	boolean b = false;
    	Object code = request.getSession().getAttribute(ValidateCode.RANDOMCODEKEY);
    	if(code == null || checkCode == null)
    		return false;
    	if(code.toString().toUpperCase()
    			.equals(checkCode.toUpperCase())){
    		b = true;
    	}
    	return b;
    }
}