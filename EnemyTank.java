package com.atguigu.tankgame;

import java.util.Vector;

/**
 * @author psj
 * @date 2022/2/24 16:24
 * @File: EnemyTank.java
 * @Software: IntelliJ IDEA
 */
// 要让敌方tank自由移动，需要将其作为线程
public class EnemyTank extends Tank implements Runnable {

    // 使用Vector保存多个Shot对象
    Vector<Shot> shots = new Vector<>();
//    boolean isLive = true;

    // 存储除了当前enemyTank外的其他enemyTank
    Vector<EnemyTank> enemyTanks = new Vector<>();

    // 将Mypanel的成员enemyTanks设置当前类的enemyTanks
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    // 判断当前敌方tank是否和enemyTanks中的其他tank发生重叠或碰撞
    public boolean isTouchEnemyTank() {
        // 判读当前敌方tank的方向，不同方向处理方式不同
        switch (this.getDirect()) {
            case 0:  // 向上
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 和其他tank都比较
                    if (enemyTank != this) {
                        // 如果另一个敌人tank方向向上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 判断当前敌方tank的左上角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            // 判断当前敌方tank的右上角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        // 如果另一个敌人tank方向向左/右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 判断当前敌方tank的左上角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            // 判断当前敌方tank的右上角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }

                    }
                }
                break;
            case 1:  // 向右
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 和其他tank都比较
                    if (enemyTank != this) {
                        // 如果另一个敌人tank方向向上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 判断当前敌方tank的右上角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            // 判断当前敌方tank的右下角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        // 如果另一个敌人tank方向向左/右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 判断当前敌方tank的右上角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            // 判断当前敌方tank的右下角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }

                    }
                }
                break;
            case 2:  // 向下
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 和其他tank都比较
                    if (enemyTank != this) {
                        // 如果另一个敌人tank方向向上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 判断当前敌方tank的左下角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            // 判断当前敌方tank的右下角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        // 如果另一个敌人tank方向向左/右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 判断当前敌方tank的左下角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            // 判断当前敌方tank的右下角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }

                    }
                }
                break;
            case 3:  // 向左
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 和其他tank都比较
                    if (enemyTank != this) {
                        // 如果另一个敌人tank方向向上/下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            // 判断当前敌方tank的左上角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            // 判断当前敌方tank的左下角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        // 如果另一个敌人tank方向向左/右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            // 判断当前敌方tank的左上角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            // 判断当前敌方tank的左下角坐标是否在被比较的敌方tank范围内,在就碰撞
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }

                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {

            // 如果敌方tank存活并且子弹集合中子弹数目不足5，则创建子弹直到有5颗子弹
            if (isLive && shots.size() <= 4) {
                Shot shot = null;
                // 判断敌方tank方向创建对应子弹
                switch (getDirect()) {
                    case 0:  // 敌方tank向上
                        shot = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:  // 敌方tank向右
                        shot = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:  // 敌方tank向下
                        shot = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:  // 敌方tank向左
                        shot = new Shot(getX(), getY() + 20, 3);
                        break;
                }

                shots.add(shot);
                // 启动当前子弹线程
                new Thread(shot).start();
            }
            // 根据tank的方向继续移动
            switch (getDirect()) {
                case 0:  // 向上
                    // 朝一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        // 控制tank不会超过上边界并且不会和别的敌方tank碰撞
                        if (getY() > 0 && !isTouchEnemyTank())
                            moveUp();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:  // 向右
                    // 朝一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        // 控制tank不会超过右边界并且不会和别的敌方tank碰撞
                        if (getX() + 60 < 1000 && !isTouchEnemyTank())
                            moveRight();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:  // 向下
                    // 朝一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        // 控制tank不会超过下边界并且不会和别的敌方tank碰撞
                        if (getY() + 60 < 750 && !isTouchEnemyTank())
                            moveDown();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:  // 向左
                    // 朝一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        // 控制tank不会超过左边界并且不会和别的敌方tank碰撞
                        if (getX() > 0 && !isTouchEnemyTank())
                            moveLeft();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            // 随机改变tank方向
            setDirect((int) (Math.random() * 4));

            // 被我方子弹击中后线程退出
            if (!isLive) {
                break;
            }
        }
    }
}
