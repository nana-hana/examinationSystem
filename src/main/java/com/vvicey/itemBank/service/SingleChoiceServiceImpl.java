package com.vvicey.itemBank.service;

import com.vvicey.itemBank.dao.SingleChoiceMapper;
import com.vvicey.itemBank.entity.SingleChoice;
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

    /**
     * 根据学科id查询所有单选题
     *
     * @param subjectId 学科id
     * @return 返回所有单选题
     */
    @Override
    public List<SingleChoice> querySingleChoiceAllBySubjectId(Integer subjectId) {
        return singleChoiceMapper.selectAllBySubjectId(subjectId);
    }

    /**
     * 查询所有单选题
     *
     * @return 返回所有单选题
     */
    @Override
    public List<SingleChoice> querySingleChoiceAll() {
        return singleChoiceMapper.selectAll();
    }

    /**
     * 根据id删除单选题
     *
     * @param id 单选题id
     */
    @Override
    public void deleteSingleChoiceById(int id) {
        singleChoiceMapper.deleteSingleChoiceById(id);
    }
}
