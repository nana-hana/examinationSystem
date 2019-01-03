package com.vvicey.itembank.service;

import com.vvicey.itembank.dao.MultipleChoiceMapper;
import com.vvicey.itembank.entity.MultipleChoice;
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

    private final MultipleChoiceMapper multipleChoiceMapper;

    @Autowired
    public MultipleChoiceServiceImpl(MultipleChoiceMapper multipleChoiceMapper) {
        this.multipleChoiceMapper = multipleChoiceMapper;
    }

    @Override
    public List<MultipleChoice> queryMultipleChoiceByEiid(Integer eiid) {
        return multipleChoiceMapper.selectByExamEiid(eiid);
    }

    @Override
    public List<MultipleChoice> queryMultipleChoiceAllBySubjectId(Integer subjectId) {
        return multipleChoiceMapper.selectAllBySubjectId(subjectId);
    }

    @Override
    public List<MultipleChoice> queryMultipleChoiceAll() {
        return multipleChoiceMapper.selectAll();
    }

    @Override
    public void deleteMultipleChoiceById(int id) {
        multipleChoiceMapper.deleteMultipleChoiceById(id);
    }
}
