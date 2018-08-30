package com.vvicey.testPaper.service;

import com.vvicey.testPaper.dao.SingleChoiceMapper;
import com.vvicey.testPaper.entity.SingleChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:54
 * @Description 单选题实现类
 */
@Service("SingleChoiceServiceImpl")
public class SingleChoiceServiceImpl implements SingleChoiceService {

    @Autowired
    private SingleChoiceMapper singleChoiceMapper;

    /**
     * 根据学科查询指定数量的单选题
     *
     * @param examEiid 考试内在因素id号
     * @return 返回单选题集
     */
    @Override
    public List<SingleChoice> querySingleChoiceByEiid(Integer examEiid) {
        return singleChoiceMapper.selectByExamEiid(examEiid);
    }

    @Override
    public List<SingleChoice> querySingleChoiceAll(Integer subjectId) {
        return singleChoiceMapper.selectAll(subjectId);
    }
}
