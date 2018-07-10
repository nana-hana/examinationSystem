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
     * @param subjectId    单选题的科目
     * @param singleNumber 指定数量的单选题
     * @return 返回单选题集
     */
    @Override
    public List<SingleChoice> querySingleChoiceBySubjectId(Integer subjectId, Integer singleNumber) {
        return singleChoiceMapper.selectBySubjectId(subjectId, singleNumber);
    }
}
