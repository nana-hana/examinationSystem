package com.vvicey.testPaper.service;

import com.vvicey.testPaper.dao.CheckingQuestionMapper;
import com.vvicey.testPaper.entity.CheckingQuestion;
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
     * @param subjectId      判断题的科目
     * @param checkingNumber 指定数量的判断题
     * @return 返回判断题集
     */
    @Override
    public List<CheckingQuestion> queryCheckingQuestionBySubjectId(Integer subjectId, Integer checkingNumber) {
        return checkingQuestionMapper.selectBySubjectId(subjectId, checkingNumber);
    }
}
