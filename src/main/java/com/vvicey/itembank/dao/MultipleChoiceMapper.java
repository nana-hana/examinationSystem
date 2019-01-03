package com.vvicey.itembank.dao;

import com.vvicey.itembank.entity.MultipleChoice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:49
 * @Description 多选题mapper
 */
@Repository
public interface MultipleChoiceMapper {
    /**
     * 根据eiid删除多选题
     *
     * @param examEiid examEiid
     * @return 删除结果
     */
    int deleteByEiid(Integer examEiid);

    /**
     * 插入多选题
     *
     * @param record 多选题信息
     * @return 插入结果
     */
    int insertSelective(MultipleChoice record);

    /**
     * 根据eiid搜索多选题信息
     *
     * @param examEiid examEiid
     * @return 搜索结果
     */
    List<MultipleChoice> selectByExamEiid(Integer examEiid);

    /**
     * 根据主键更新多选题信息
     *
     * @param record 多选题信息
     * @return 搜索结果
     */
    int updateByPrimaryKeySelective(MultipleChoice record);

    /**
     * 根据科目id搜索所有多选题
     *
     * @param subjectId 科目id
     * @return 搜索结果
     */
    List<MultipleChoice> selectAllBySubjectId(Integer subjectId);

    /**
     * 搜索所有多选题
     *
     * @return 搜索结果
     */
    List<MultipleChoice> selectAll();

    /**
     * 根据id删除多选题信息
     *
     * @param id id
     */
    void deleteMultipleChoiceById(int id);
}