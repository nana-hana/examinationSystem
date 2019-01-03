package com.vvicey.itembank.service;

import com.vvicey.itembank.entity.CheckingQuestion;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:58
 * @Description 判断题抽象类
 */
public interface CheckingQuestionService {

    /**
     * 根据学科查询指定数量的判断题
     *
     * @param eiid 考试内在因素id号
     * @return 返回判断题集
     */
    List<CheckingQuestion> queryCheckingQuestionByEiid(Integer eiid);

    /**
     * 根据学科id查询所有判断题
     *
     * @param subjectId 根据学科id
     * @return 返回所有判断题
     */
    List<CheckingQuestion> queryCheckingQuestionAllBySubjectId(Integer subjectId);

    /**
     * 查询所有判断题
     *
     * @return 返回所有判断题
     */
    List<CheckingQuestion> queryCheckingQuestionAll();

    /**
     * 根据id删除判断题
     *
     * @param id 判断题id
     */
    void deleteCheckingQuestionById(Integer id);
}
