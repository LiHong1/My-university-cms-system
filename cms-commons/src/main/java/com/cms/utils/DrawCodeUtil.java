package com.cms.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DrawCodeUtil {
    private int width;
    private int height;
    private int num=4;
    private static final Random ran = new Random();
  //中文区间[\u4e00-\u9fa5]  unicode
    private  String base ="QWERTYUIOPLKJHGFDSAZXCVBNMqwertyuioplkjhgfdsaxcvbnmz1234567890";
    private static DrawCodeUtil drawCodeUtil;
    private DrawCodeUtil(){
        
    }
    public static DrawCodeUtil getInstance(){
        if(drawCodeUtil==null)
            drawCodeUtil=new DrawCodeUtil();
        return drawCodeUtil;
    }

    public void set(int width,int height,int num,String base) {
        this.width = width;
        this.height = height;
        this.num=num;
        this.base=base;
    }
    
    public void set(int width,int height) {
        this.width = width;
        this.height = height;
    }
    public String generateCheckcode() {
        StringBuffer cc = new StringBuffer();
        for(int i=0;i<num;i++) {
            cc.append(base.charAt(ran.nextInt(base.length())));
        }
        return cc.toString();
    }
    public BufferedImage generateCheckImg(String checkcode) {
        //创建一个图片对象
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取图片对象的画笔
        Graphics2D graphic = img.createGraphics();
        graphic.setColor(Color.WHITE);
        graphic.fillRect(0, 0, width, height);
        graphic.setColor(Color.BLACK);
        graphic.drawRect(0, 0, width-1, height-1);
        Font font = new Font("宋体",Font.BOLD+Font.ITALIC,(int)(height*0.8));
        graphic.setFont(font);
        for(int i=0;i<num;i++) {
            int degree=ran.nextInt(30)%30;
            graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
            int x=i*(width/num)+4;
            graphic.rotate(degree*Math.PI/180,x,20);//设置旋转角度
            graphic.drawString(String.valueOf(checkcode.charAt(i)), x, (int)(height*0.8));
            graphic.rotate(-degree*Math.PI/180,x,20);//设置旋转角度
        }
        
        //加一些点
        for(int i=0;i<(width+height)/2;i++) {
            graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
            graphic.drawOval(ran.nextInt(width), ran.nextInt(height), 1, 1);
        }
        
        //加一些线
        for(int i=0;i<6;i++) {
            graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
            graphic.drawLine(ran.nextInt(width), ran.nextInt(height),ran.nextInt(width), ran.nextInt(height));
        }
        return img;
    }
}
