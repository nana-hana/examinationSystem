package com.vvicey.testPaper.service;

import com.vvicey.testPaper.dao.MultipleChoiceMapper;
import com.vvicey.testPaper.entity.MultipleChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:57
 * @Description 多选题抽象类
 */
@Service("MultipleChoiceServiceImpl")
public class MultipleChoiceServiceImpl implements MultipleChoiceService {

    @Autowired
    private MultipleChoiceMapper multipleChoiceMapper;

    /**
     * 根据学科查询指定数量的多选题
     *
     * @param examEiid 考试内在因素id号
     * @return 多选题集
     */
    @Override
    public List<MultipleChoice> queryMultipleChoiceByEiid(Integer examEiid) {
        return multipleChoiceMapper.selectByExamEiid(examEiid);
    }

    @Override
    public List<MultipleChoice> queryMultipleChoiceAll(Integer subjectId) {
        return multipleChoiceMapper.selectAll(subjectId);
    }
}
