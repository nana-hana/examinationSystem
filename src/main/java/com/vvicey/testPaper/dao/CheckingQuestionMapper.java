package com.vvicey.testPaper.dao;

import com.vvicey.testPaper.entity.CheckingQuestion;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:48
 * @Description 判断题mapper
 */
public interface CheckingQuestionMapper {
    int deleteByPrimaryKey(Integer subjectId);

    int insertSelective(CheckingQuestion record);

    List<CheckingQuestion> selectBySubjectId(Integer subjectId, int checkingNumber);

    int updateByPrimaryKeySelective(CheckingQuestion record);
}