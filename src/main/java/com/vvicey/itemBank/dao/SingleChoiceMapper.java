package com.vvicey.itemBank.dao;

import com.vvicey.itemBank.entity.SingleChoice;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:49
 * @Description 单选题mapper
 */
public interface SingleChoiceMapper {
    int deleteByEiid(Integer examEiid);

    int insertSelective(SingleChoice record);

    List<SingleChoice> selectByExamEiid(Integer examEiid);

    int updateByPrimaryKeySelective(SingleChoice record);

    List<SingleChoice> selectAllBySubjectId(Integer subjectId);

    List<SingleChoice> selectAll();

    void deleteSingleChoiceById(int id);
}