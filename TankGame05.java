package com.atguigu.tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author psj
 * @date 2022/2/24 14:56
 * @File: TankGame05.java
 * @Software: IntelliJ IDEA
 */
public class TankGame05 extends JFrame {
    // 定义MyPanel
    MyPanel myPanel = null;
    Scanner scanner = new Scanner(System.in);
    public TankGame05(){
        System.out.println("请输入选择 1:新游戏 2:继续上局");
        String key = scanner.next();
        myPanel = new MyPanel(key);
        // 将map放入到Thread并启动(MyPanel也是一个线程，子弹也是一个线程)
        Thread thread = new Thread(myPanel);
        thread.start();
        // 将面板放入画框
        this.add(myPanel);
        // 设置窗口大小
        this.setSize(1300, 750);
        // 设置退出窗口时程序也退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 可视化
        this.setVisible(true);
        // 窗口JFrame可以监听键盘事件(参数是KeyListener接口，MyPanel实现了该接口)
        this.addKeyListener(myPanel);
        // 增加关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
            }
        });
    }
    public static void main(String[] args) {

        TankGame05 tankGame05 = new TankGame05();
    }
}
