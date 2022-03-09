package com.atguigu.tankgame;

import java.util.Vector;

/**
 * @author psj
 * @date 2022/2/24 14:45
 * @File: Hero.java
 * @Software: IntelliJ IDEA
 * 我的tank
 */
public class Hero extends Tank {

    // 定义Shot对象，表示一个射击线程
    Shot shot = null;

    // 创建集合保持发射的多颗子弹
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemyTank() {
        // 设置面板上最多发射5颗子弹
        if (shots.size() == 5) {
            return;
        }
        // 创建Shot对象
        // 根据当前Hero对象的位置和方向创建Shot对象
        switch (getDirect()) {  // 得到Hero对象的方向
            case 0:  // 向上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1:  // 向右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2:  // 向下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3:  // 向左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }

        // 把新创建的shot放入shots中
        shots.add(shot);
        // 启动Shot线程
        Thread thread = new Thread(shot);
        thread.start();
    }

}
