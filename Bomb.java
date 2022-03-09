package com.atguigu.tankgame;

/**
 * @author psj
 * @date 2022/3/1 17:04
 * @File: Bomb.java
 * @Software: IntelliJ IDEA
 */
// 爆炸效果就是不同的图片进行切换
public class Bomb {
    int x, y;  // 炸弹坐标
    int life = 9;  // 子弹生命周期
    boolean isLive = true;  // 判断是否存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 减少生命值
    // 配合出现图片的爆炸效果
    public void LifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
