package com.vvicey.itemBank.service;

import com.vvicey.itemBank.dao.CheckingQuestionMapper;
import com.vvicey.itemBank.entity.CheckingQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:59
 * @Description 判断题实现类
 */
@Service("CheckingQuestionServiceImpl")
public class CheckingQuestionServiceImpl implements CheckingQuestionService {
    @Autowired
    private CheckingQuestionMapper checkingQuestionMapper;

    /**
     * 根据学科查询指定数量的判断题
     *
     * @param examEiid 考试内在因素id号
     * @return 返回判断题集
     */
    @Override
    public List<CheckingQuestion> queryCheckingQuestionByEiid(Integer examEiid) {
        return checkingQuestionMapper.selectByExamEiid(examEiid);
    }

    /**
     * 根据学科id查询所有判断题
     *
     * @param subjectId 根据学科id
     * @return 返回所有判断题
     */
    @Override
    public List<CheckingQuestion> queryCheckingQuestionAllBySubjectId(Integer subjectId) {
        return checkingQuestionMapper.selectAllBySubjectId(subjectId);
    }

    /**
     * 查询所有判断题
     *
     * @return 返回所有判断题
     */
    @Override
    public List<CheckingQuestion> queryCheckingQuestionAll() {
        return checkingQuestionMapper.selectAll();
    }

    /**
     * 根据id删除判断题
     *
     * @param id 判断题id
     */
    @Override
    public void deleteCheckingQuestionById(Integer id) {
        checkingQuestionMapper.deleteById(id);
    }
}
