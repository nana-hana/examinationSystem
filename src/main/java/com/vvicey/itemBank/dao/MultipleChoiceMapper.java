package com.vvicey.itemBank.dao;

import com.vvicey.itemBank.entity.MultipleChoice;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:49
 * @Description 多选题mapper
 */
public interface MultipleChoiceMapper {
    int deleteByEiid(Integer examEiid);

    int insertSelective(MultipleChoice record);

    List<MultipleChoice> selectByExamEiid(Integer examEiid);

    int updateByPrimaryKeySelective(MultipleChoice record);
    
    List<MultipleChoice> selectAllBySubjectId(Integer subjectId);

    List<MultipleChoice> selectAll();

    void deleteMultipleChoiceById(int id);
}