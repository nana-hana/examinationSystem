package com.vvicey.itemBank.service;

import com.vvicey.itemBank.dao.MultipleChoiceMapper;
import com.vvicey.itemBank.entity.MultipleChoice;
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

    /**
     * 根据学科id查询所有多选题
     *
     * @param subjectId 根据学科id
     * @return 返回所有多选题
     */
    @Override
    public List<MultipleChoice> queryMultipleChoiceAllBySubjectId(Integer subjectId) {
        return multipleChoiceMapper.selectAllBySubjectId(subjectId);
    }

    /**
     * 查询所有多选题
     *
     * @return 返回所有多选题
     */
    @Override
    public List<MultipleChoice> queryMultipleChoiceAll() {
        return multipleChoiceMapper.selectAll();
    }

    /**
     * 根据id删除多选题
     *
     * @param id 多选题id
     */
    @Override
    public void deleteMultipleChoiceById(int id) {
        multipleChoiceMapper.deleteMultipleChoiceById(id);
    }
}
