package com.vvicey.itembank.service;

import com.vvicey.itembank.dao.SingleChoiceMapper;
import com.vvicey.itembank.entity.SingleChoice;
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

    private final SingleChoiceMapper singleChoiceMapper;

    @Autowired
    public SingleChoiceServiceImpl(SingleChoiceMapper singleChoiceMapper) {
        this.singleChoiceMapper = singleChoiceMapper;
    }

    @Override
    public List<SingleChoice> querySingleChoiceByEiid(Integer eiid) {
        return singleChoiceMapper.selectByExamEiid(eiid);
    }

    @Override
    public List<SingleChoice> querySingleChoiceAllBySubjectId(Integer subjectId) {
        return singleChoiceMapper.selectAllBySubjectId(subjectId);
    }

    @Override
    public List<SingleChoice> querySingleChoiceAll() {
        return singleChoiceMapper.selectAll();
    }

    @Override
    public void deleteSingleChoiceById(int id) {
        singleChoiceMapper.deleteSingleChoiceById(id);
    }
}
