package com.vvicey.itemBank.service;

import com.vvicey.itemBank.entity.CheckingQuestion;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:58
 * @Description 判断题抽象类
 */
public interface CheckingQuestionService {
    List<CheckingQuestion> queryCheckingQuestionByEiid(Integer Eiid);

    List<CheckingQuestion> queryCheckingQuestionAllBySubjectId(Integer subjectId);

    List<CheckingQuestion> queryCheckingQuestionAll();

    void deleteCheckingQuestionById(Integer id);
}
