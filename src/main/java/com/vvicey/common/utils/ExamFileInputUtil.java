package com.vvicey.common.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExamFileInputUtil {

    /**
     * 判断导入的文本文件中，各个种类题目数量是否符合要求，和题目总难度是否符合要求
     *
     * @param filePath    导入文本文件路径
     * @param singleNum   单选题规定数量
     * @param multipleNum 多选题规定数量
     * @param checkNum    判断题规定数量
     * @param level       考试难度
     * @return 符合要求返回true, 不符合返回false
     */
    @SuppressWarnings("finally")
    public static boolean checkTest(String filePath, int singleNum, int multipleNum, int checkNum, int level) {
        int fileSingleNum = 0;// 文件中单选题数量
        int fileMultipleNum = 0;// 文件中多选题数量
        int fileCheckNum = 0;// 文件中判断题数量
        int fileLevel = 0;// 文件中题目难度

        File file = new File(filePath);
        BufferedReader br = null;
        int catchStatus = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            String content;
            // 遍历文件，统计各种类题目数量
            while ((content = br.readLine()) != null) {// 使用readLine方法，一次读一行
//				System.out.println(content);
                JSONObject obj = new JSONObject().fromObject(content);// 将json字符串转换为json对象
                String kind = obj.getString("kind");
                fileLevel += obj.getInt("level");
                // 通过题目种类判断该插入哪种题目
                if ("single_choice".equals(kind)) {
                    fileSingleNum++;
                } else if ("multiple_choice".equals(kind)) {
                    fileMultipleNum++;
                } else if ("checking_question".equals(kind)) {
                    fileCheckNum++;
                }
            }
            br.close();
            int sum = fileSingleNum + fileMultipleNum + fileCheckNum;
            if (sum == 0) {
                return false;
            }
            fileLevel = fileLevel / sum;
            //判断是否符合要求
            if (fileSingleNum == singleNum && fileMultipleNum == multipleNum && fileCheckNum == checkNum
                    && fileLevel == level) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            catchStatus = 1;
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    if (catchStatus == 1) {
                        return false;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * @param paramList:被抽取list
     * @param count:抽取元素的个数
     * @function:从list中随机抽取若干不重复元素
     * @return:由抽取元素组成的新list
     */
    public static List getRandomList(List paramList, int count) {
        if (paramList.size() < count) {
            return paramList;
        }
        Random random = new Random();
        List<Integer> tempList = new ArrayList<>();
        List<Object> newList = new ArrayList<>();
        int temp = 0;
        for (int i = 0; i < count; i++) {
            temp = random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
            if (!tempList.contains(temp)) {
                tempList.add(temp);
                newList.add(paramList.get(temp));
            } else {
                i--;
            }
        }
        return newList;
    }
}
