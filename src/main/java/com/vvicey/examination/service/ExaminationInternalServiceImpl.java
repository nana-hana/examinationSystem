package com.vvicey.examination.service;

import com.vvicey.examination.dao.ExaminationInternalMapper;
import com.vvicey.examination.entity.ExaminationInternal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author nana
 * @Date 18-6-30 下午5:52
 * @Description 考试事件的操作实现类
 */
@Service("ExaminationInternalServiceImpl")
public class ExaminationInternalServiceImpl implements ExaminationInternalService {

    private final ExaminationInternalMapper examinationInternalMapper;

    @Autowired
    public ExaminationInternalServiceImpl(ExaminationInternalMapper examinationInternalMapper) {
        this.examinationInternalMapper = examinationInternalMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createExamination(ExaminationInternal examinationInternal) {
        return examinationInternalMapper.insertSelective(examinationInternal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteExamination(int eiid) {
        return examinationInternalMapper.deleteByEiid(eiid);
    }

    @Override
    public ExaminationInternal queryExaminationInternalByEiid(int eiid) {
        return examinationInternalMapper.selectByEiid(eiid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateExaminationByEiid(ExaminationInternal examinationInternal) {
        examinationInternalMapper.updateByEiidSelective(examinationInternal);
    }

    @Override
    public List<ExaminationInternal> queryExaminationInternalByStudentClass(int studentClass) {
        return examinationInternalMapper.selectByStudentClass(studentClass);
    }
}
