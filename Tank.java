package com.atguigu.tankgame;

/**
 * @author psj
 * @date 2022/2/24 14:44
 * @File: Tank.java
 * @Software: IntelliJ IDEA
 */
public class Tank {
    private int x;  // tank横坐标
    private int y; // tank纵坐标
    private int direct;  // tank方向
    boolean isLive = true;
    private int speed = 1;  // tank速度

    // tank移动
    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
