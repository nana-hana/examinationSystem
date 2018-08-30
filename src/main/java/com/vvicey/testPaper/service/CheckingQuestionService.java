package com.vvicey.testPaper.service;

import com.vvicey.testPaper.entity.CheckingQuestion;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:58
 * @Description 判断题抽象类
 */
public interface CheckingQuestionService {
	/**
     * 根据考试信息Eiid查询当前考试题目 
     * @param Eiid
     * @return
     */
	List<CheckingQuestion> queryCheckingQuestionByEiid(Integer Eiid);
    
    /**
     * 插入一道单选题
     * @param CheckingQuestion
     * @return 成功返回true,失败返回false
     */
    boolean insertCheckingQuestion(CheckingQuestion CheckingQuestion);
    
    List<CheckingQuestion> queryCheckingQuestionAll(Integer subjectId);
}
