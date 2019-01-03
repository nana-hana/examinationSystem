package com.vvicey.itembank.dao;

import com.vvicey.itembank.entity.CheckingQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:48
 * @Description 判断题mapper
 */
@Repository
public interface CheckingQuestionMapper {
    /**
     * 根据eiid删除
     * @param examEiid eiid
     * @return 返回删除结果
     */
    int deleteByEiid(Integer examEiid);

    /**
     * 插入选择题数据
     * @param record 选择题数据
     * @return 返回插入结果
     */
    int insertSelective(CheckingQuestion record);

    /**
     * 根据eiid搜索选择题
     * @param examEiid eiid
     * @return 返回搜索结果
     */
    List<CheckingQuestion> selectByExamEiid(Integer examEiid);

    /**
     *  根据主键更新数据
     * @param record 选择题数据
     * @return 返回更新结果
     */
    int updateByPrimaryKeySelective(CheckingQuestion record);

    /**
     * 根据科目搜索选择题
     * @param subjectId 科目id
     * @return 返回搜索结果
     */
    List<CheckingQuestion> selectAllBySubjectId(Integer subjectId);

    /**
     * 搜索所有选择题
     * @return 返回结果
     */
    List<CheckingQuestion> selectAll();

    /**
     * 根据id删除题目
     * @param id id
     */
    void deleteById(Integer id);
}