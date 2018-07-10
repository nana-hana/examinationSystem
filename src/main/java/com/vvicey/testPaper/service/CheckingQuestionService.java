package com.vvicey.testPaper.service;

import com.vvicey.testPaper.entity.CheckingQuestion;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:58
 * @Description 判断题抽象类
 */
public interface CheckingQuestionService {
    List<CheckingQuestion> queryCheckingQuestionBySubjectId(Integer subjectId, Integer checkingNumber);
}
