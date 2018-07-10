package com.vvicey.testPaper.dao;

import com.vvicey.testPaper.entity.SingleChoice;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:49
 * @Description 单选题mapper
 */
public interface SingleChoiceMapper {
    int deleteByPrimaryKey(Integer subjectId);

    int insertSelective(SingleChoice record);

    List<SingleChoice> selectBySubjectId(Integer subjectId, int SingleNumber);

    int updateByPrimaryKeySelective(SingleChoice record);
}