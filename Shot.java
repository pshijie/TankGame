package com.atguigu.tankgame;

/**
 * @author psj
 * @date 2022/3/1 15:53
 * @File: Shot.java
 * @Software: IntelliJ IDEA
 */
public class Shot implements Runnable {

    int x;  // 子弹的x坐标
    int y;  // 子弹的y坐标
    int direct = 0;  // 子弹方向
    int speed = 2;  // 子弹速度
    boolean isLive = true;  // 子弹是否存活

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 根据方向改变x,y坐标
            switch (direct) {
                case 0:  //上
                    y -= speed;
                    break;
                case 1:  //右
                    x += speed;
                    break;
                case 2:  //下
                    y += speed;
                    break;
                case 3:  //左
                    x -= speed;
                    break;
            }

            // 当子弹到面板边界时，把子弹的线程销毁
            // 或者当子弹碰到敌人tank时也应该退出（此时子弹的isLive被设为false，以此为判断条件）
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                isLive = false;
                break;
            }
        }
    }
}
