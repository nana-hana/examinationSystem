package com.vvicey.itembank.service;

import com.vvicey.itembank.dao.CheckingQuestionMapper;
import com.vvicey.itembank.entity.CheckingQuestion;
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
    private final CheckingQuestionMapper checkingQuestionMapper;

    @Autowired
    public CheckingQuestionServiceImpl(CheckingQuestionMapper checkingQuestionMapper) {
        this.checkingQuestionMapper = checkingQuestionMapper;
    }

    @Override
    public List<CheckingQuestion> queryCheckingQuestionByEiid(Integer eiid) {
        return checkingQuestionMapper.selectByExamEiid(eiid);
    }

    @Override
    public List<CheckingQuestion> queryCheckingQuestionAllBySubjectId(Integer subjectId) {
        return checkingQuestionMapper.selectAllBySubjectId(subjectId);
    }

    @Override
    public List<CheckingQuestion> queryCheckingQuestionAll() {
        return checkingQuestionMapper.selectAll();
    }

    @Override
    public void deleteCheckingQuestionById(Integer id) {
        checkingQuestionMapper.deleteById(id);
    }
}
