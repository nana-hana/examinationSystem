package com.vvicey.itemBank.dao;

import com.vvicey.itemBank.entity.CheckingQuestion;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:48
 * @Description 判断题mapper
 */
public interface CheckingQuestionMapper {
    int deleteByEiid(Integer examEiid);

    int insertSelective(CheckingQuestion record);

    List<CheckingQuestion> selectByExamEiid(Integer examEiid);

    int updateByPrimaryKeySelective(CheckingQuestion record);
    
    List<CheckingQuestion> selectAllBySubjectId(Integer subjectId);

    List<CheckingQuestion> selectAll();

    void deleteById(Integer id);
}