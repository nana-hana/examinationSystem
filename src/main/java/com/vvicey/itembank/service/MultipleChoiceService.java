package com.vvicey.itembank.service;

import com.vvicey.itembank.entity.MultipleChoice;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:56
 * @Description 多选题抽象类
 */
public interface MultipleChoiceService {

    /**
     * 根据学科查询指定数量的多选题
     *
     * @param eiid 考试内在因素id号
     * @return 多选题集
     */
    List<MultipleChoice> queryMultipleChoiceByEiid(Integer eiid);

    /**
     * 根据学科id查询所有多选题
     *
     * @param subjectId 根据学科id
     * @return 返回所有多选题
     */
    List<MultipleChoice> queryMultipleChoiceAllBySubjectId(Integer subjectId);

    /**
     * 查询所有多选题
     *
     * @return 返回所有多选题
     */
    List<MultipleChoice> queryMultipleChoiceAll();

    /**
     * 根据id删除多选题
     *
     * @param id 多选题id
     */
    void deleteMultipleChoiceById(int id);
}
