package com.vvicey.common.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author nana
 * Date 19-1-3 上午11:31
 * @Description
 */
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
        // 文件中单选题数量
        int fileSingleNum = 0;
        // 文件中多选题数量
        int fileMultipleNum = 0;
        // 文件中判断题数量
        int fileCheckNum = 0;
        // 文件中题目难度
        int fileLevel = 0;

        File file = new File(filePath);
        BufferedReader br = null;
//        int catchStatus = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            String content;
            /* 遍历文件，统计各种类题目数量 */
            while ((content = br.readLine()) != null) {
                // 将json字符串转换为json对象
                JSONObject obj = JSONObject.fromObject(content);
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
            return fileSingleNum == singleNum && fileMultipleNum == multipleNum && fileCheckNum == checkNum
                    && fileLevel == level;
        } catch (Exception e) {
//            catchStatus = 1;
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
//                    if (catchStatus == 1) {
//                        return false;
//                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * @param paramList 被抽取list
     * @param count     抽取元素的个数
     * @return 由抽取元素组成的新list
     */
    public static List getRandomList(List paramList, int count) {
        if (paramList.size() < count) {
            return paramList;
        }
        Random random = new Random();
        List<Integer> tempList = new ArrayList<>();
        List<Object> newList = new ArrayList<>();
        int temp;
        for (int i = 0; i < count; i++) {
            //将产生的随机数作为被抽list的索引
            temp = random.nextInt(paramList.size());
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
