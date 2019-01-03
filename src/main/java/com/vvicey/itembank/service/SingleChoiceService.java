package com.vvicey.itembank.service;

import com.vvicey.itembank.entity.SingleChoice;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:43
 * @Description 单选题增删改查抽象类
 */
public interface SingleChoiceService {

    /**
     * 根据学科查询指定数量的单选题
     *
     * @param eiid 考试内在因素id号
     * @return 返回单选题集
     */
    List<SingleChoice> querySingleChoiceByEiid(Integer eiid);

    /**
     * 根据学科id查询所有单选题
     *
     * @param subjectId 学科id
     * @return 返回所有单选题
     */
    List<SingleChoice> querySingleChoiceAllBySubjectId(Integer subjectId);

    /**
     * 查询所有单选题
     *
     * @return 返回所有单选题
     */
    List<SingleChoice> querySingleChoiceAll();

    /**
     * 根据id删除单选题
     *
     * @param id 单选题id
     */
    void deleteSingleChoiceById(int id);
}
