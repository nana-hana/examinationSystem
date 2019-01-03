package com.vvicey.itembank.dao;

import com.vvicey.itembank.entity.SingleChoice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:49
 * @Description 单选题mapper
 */
@Repository
public interface SingleChoiceMapper {
    /**
     * 根据eiid删除单选题
     *
     * @param examEiid examEiid
     * @return 删除结果
     */
    int deleteByEiid(Integer examEiid);

    /**
     * 插入单选题
     *
     * @param record 单选题信息
     * @return 插入结果
     */
    int insertSelective(SingleChoice record);

    /**
     * 根据考试号搜索单选题
     *
     * @param examEiid examEiid
     * @return 查询结果
     */
    List<SingleChoice> selectByExamEiid(Integer examEiid);

    /**
     * 根据主键更新单选题信息
     *
     * @param record 单选题信息
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(SingleChoice record);

    /**
     * 根据科目id搜索单选题
     *
     * @param subjectId 科目id
     * @return 搜索结果
     */
    List<SingleChoice> selectAllBySubjectId(Integer subjectId);

    /**
     * 搜索所有单选题
     *
     * @return 搜索结果
     */
    List<SingleChoice> selectAll();

    /**
     * 根据id删除单选题
     *
     * @param id id
     */
    void deleteSingleChoiceById(int id);
}