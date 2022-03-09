package com.atguigu.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @author psj
 * @date 2022/2/24 14:53
 * @File: MyPanel.java
 * @Software: IntelliJ IDEA
 * 游戏绘图区域
 */
// 为了让Panel不停重绘子弹，需要将MyPanel做成线程使用（子弹是另一个线程）
public class MyPanel extends JPanel implements KeyListener, Runnable {
    // 定义我的tank
    Hero hero = null;
    // 定义敌人tank并放入Vector集合
    Vector<EnemyTank> enemyTanks = new Vector<>();
    // 定义存储敌方Tank信息的Vector
    Vector<Node> nodes = new Vector<>();
    // 定义敌人tank数量
    int enemyTankNum = 3;
    // 定义Vector用于存放炸弹,当子弹击中一个tank时就加入一个Bomb对象到集合中
    Vector<Bomb> bombs = new Vector<>();

    // 定义三张炸弹图片用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String key) {
        // 判断记录文件是否存在
        File file = new File(Recorder.getRecordPath());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankNum();
        } else {
            System.out.println("记录文件不存在，只能开启新游戏");
            key = "1";
        }
        // 将当前类的enemyTanks赋予Recorder的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        // 初始化我的tank
        hero = new Hero(500, 100);
        switch (key) {
            case "1":  // 开始新游戏
                for (int i = 0; i < enemyTankNum; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    // 将当前的enemyTanks给每个敌方tank的enemyTanks属性，用于检测碰撞
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(2);
                    // 启动敌人tank线程
                    new Thread(enemyTank).start();
                    // 给敌人tank创建一个子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();  // 启动子弹，让它开始移动
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":  // 继续上局
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    // 将当前的enemyTanks给每个敌方tank的enemyTanks属性，用于检测碰撞
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect(node.getDirect());
                    // 启动敌人tank线程
                    new Thread(enemyTank).start();
                    // 给敌人tank创建一个子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();  // 启动子弹，让它开始移动
                    enemyTanks.add(enemyTank);
                }

                break;
            default:
                System.out.println("输入有误!");
        }
        // 初始化敌人tank

        // 初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_3.gif"));
    }

    // 显示我方击毁敌方tank数量
    public void showInfo(Graphics g) {
        // 画出玩家总成绩
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累计击毁的敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTankNum() + " ", 1080, 100);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1300, 750);
        // 显示信息
        showInfo(g);

        // 绘制自己tank
        if (hero != null && hero.isLive)
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);

        // 绘制我方tank的子弹集合中所有子弹
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            // 要判断子弹是否创建且还存活
            if (shot != null && shot.isLive != false) {
                g.draw3DRect(shot.x, shot.y, 2, 1, false);
            } else {
                // 如果当前子弹无效，就从集合中移除
                hero.shots.remove(shot);
            }
        }
        // 如果bombs中有对象就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            // 根据当前bomb对象的life值画出对于图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.LifeDown();
            // 如果life值为0，就从bombs集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }

        }
        // 绘制敌人tank和子弹
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            // 判断当前tank是否存活，存活再绘制
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                // 画出enemyTank的所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    // 绘制子弹
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 2, 1, false);
                    } else {
                        // 从Vector移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }

        }
    }

    /**
     * 绘制tank
     *
     * @param x      tank的左上角x坐标
     * @param y      tank的左上角y坐标
     * @param g      画笔
     * @param direct tank的方向
     * @param type   tank的类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        // 根据tank类型设置颜色
        switch (type) {
            case 0: // 我的tank
                g.setColor(Color.cyan);
                break;
            case 1: // 敌方tank
                g.setColor(Color.yellow);
                break;
        }
        // 根据tank方向绘制tank
        switch (direct) {
            case 0:  // 向上
                g.fill3DRect(x, y, 10, 60, false);  // tank左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);  // tank右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);  // tank的盖子
                g.fillOval(x + 10, y + 20, 20, 20);  // tank的圆盖
                g.drawLine(x + 20, y + 30, x + 20, y);  // tank的炮筒
                break;
            case 1:  // 向右
                g.fill3DRect(x, y, 60, 10, false);  // tank上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);  // tank下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);  // tank的盖子
                g.fillOval(x + 20, y + 10, 20, 20);  // tank的圆盖
                g.drawLine(x + 30, y + 20, x + 60, y + 20);  // tank的炮筒
                break;
            case 2:  // 向下
                g.fill3DRect(x, y, 10, 60, false);  // tank左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);  // tank右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);  // tank的盖子
                g.fillOval(x + 10, y + 20, 20, 20);  // tank的圆盖
                g.drawLine(x + 20, y + 30, x + 20, y + 60);  // tank的炮筒
                break;
            case 3:  // 向左
                g.fill3DRect(x, y, 60, 10, false);  // tank上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);  // tank下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);  // tank的盖子
                g.fillOval(x + 20, y + 10, 20, 20);  // tank的圆盖
                g.drawLine(x + 30, y + 20, x, y + 20);  // tank的炮筒
                break;
            default:
                System.out.println("nothing");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {  //W键向上
            hero.setDirect(0);  //修改坦克的坐标 y -= 1
            // 控制tank不会超过上边界
            if (hero.getY() > 0)
                hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {  //D 键向右
            hero.setDirect(1);
            // 控制tank不会超过右边界
            if (hero.getX() + 60 < 1000)
                hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//S 键
            hero.setDirect(2);
            // 控制tank不会超过下边界
            if (hero.getY() + 60 < 750)
                hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//A 键
            hero.setDirect(3);
            // 控制tank不会超过左边界
            if (hero.getX() > 0)
                hero.moveLeft();
        }

        // 如果用户按下J键就发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            // 只发射一颗子弹时，要判断我方tank子弹是否销毁或者还没创建，符合任意一个条件可再次发射子弹
            // if (hero.shot == null || !hero.shot.isLive) {
            //    hero.shotEnemyTank();
            // }
            // 发射多颗子弹
            hero.shotEnemyTank();
        }
        //让面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {  // 每隔100ms，重新绘制区域
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 判断我方子弹是否击中敌人
            hitEnemyTank();
            // 判断敌方子弹是否击中我方
            hitHero();
            this.repaint();
        }

    }

    public void hitEnemyTank() {
        // 我方tank发射了多颗子弹，需要将所有子弹去遍历是否击中敌方tank
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            // 每隔100ms判断是否击中敌方tank
            if (shot != null && shot.isLive) {  // 我方子弹存活
                // 遍历敌方所有tank,看子弹是否击中其中某个tank
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }

    }

    // 判断敌方子弹是否击中我方tank
    public void hitHero() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            // 取出某个敌人tank
            EnemyTank enemyTank = enemyTanks.get(i);
            // 取出当前敌人tank的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                // 判断是否击中我方tank
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }

        }
    }

    // 判断我方/敌方子弹是否击中敌方/我方tank
    public void hitTank(Shot shot, Tank tank) {
        switch (tank.getDirect()) {
            // tank向上和向下的情况(一样处理)
            case 0:
            case 2:
                if (shot.x > tank.getX() && shot.x < tank.getX() + 40
                        && shot.y > tank.getY() && shot.y < tank.getY() + 60) {
                    shot.isLive = false;
                    tank.isLive = false;
                    // 当我方子弹击中敌人tank后就将该tank从集合中移除
                    enemyTanks.remove(tank);
                    // 我方tank击毁敌方tank后，就将数值进行改变
                    if (tank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    // 创建Bomb对象加入到bombs集合中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            // tank向左和向右的情况(一样处理)
            case 1:
            case 3:
                if (shot.x > tank.getX() && shot.x < tank.getX() + 60
                        && shot.y > tank.getY() && shot.y < tank.getY() + 40) {
                    shot.isLive = false;
                    tank.isLive = false;
                    // 当我方子弹击中敌人tank后就将该tank从集合中移除
                    enemyTanks.remove(tank);
                    // 我方tank击毁敌方tank后，就将数值进行改变
                    if (tank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    // 创建Bomb对象加入到bombs集合中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;

        }
    }
}
