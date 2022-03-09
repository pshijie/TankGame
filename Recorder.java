package com.atguigu.tankgame;

import java.io.*;
import java.util.Vector;

/**
 * @author psj
 * @date 2022/3/9 21:20
 * @File: Recorder.java
 * @Software: IntelliJ IDEA
 */
// 用于记录相关的信息，和文件交互
public class Recorder {
    // 记录我方击毁敌人tank数目
    private static int allEnemyTankNum = 0;
    // 定义IO对象
    private static BufferedWriter bufferedWriter = null;
    private static BufferedReader bufferedReader = null;
    private static String recordPath = "src\\myRecord.txt";
    // 定义存储敌方Tank的Vector
    private static Vector<EnemyTank> enemyTanks = null;
    // 定义存储敌方Tank信息的Vector
    private static Vector<Node> nodes = new Vector<>();

    public static String getRecordPath() {
        return recordPath;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }

    // 游戏退出时，保存信息到文件中
    public static void keepRecord() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(recordPath));
            bufferedWriter.write(allEnemyTankNum + "\r\n");
            // 遍历获取敌方tank的坐标和方向
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    bufferedWriter.write(record + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 读取文件，恢复相关信息
    public static Vector<Node> getNodesAndEnemyTankNum() {
        try {
            bufferedReader = new BufferedReader(new FileReader(recordPath));
            // 获取累计击毁tank数目
            allEnemyTankNum = Integer.parseInt(bufferedReader.readLine());
            // 获取敌方tank信息并封装为Node
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }
}
