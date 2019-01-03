package com.vvicey.examination.service;

import com.vvicey.examination.dao.ExaminationExternalMapper;
import com.vvicey.examination.dao.ExaminationInternalMapper;
import com.vvicey.examination.entity.ExaminationExternal;
import com.vvicey.examination.entity.ExaminationInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 下午11:59
 * @Description 考试外在因素的操作实现类
 */
@Service("ExaminationExternalServiceImpl")
public class ExaminationExternalServiceImpl implements ExaminationExternalService {

    private final ExaminationExternalMapper examinationExternalMapper;

    @Autowired
    public ExaminationExternalServiceImpl(ExaminationExternalMapper examinationExternalMapper) {
        this.examinationExternalMapper = examinationExternalMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createExaminationExternal(ExaminationExternal examinationExternal) {
        return examinationExternalMapper.insertSelective(examinationExternal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteExaminationExternal(int eeid) {
        return examinationExternalMapper.deleteByEeid(eeid);
    }

    @Override
    public List<ExaminationExternal> queryExaminationExternalByInstitute(int institute) {
        return examinationExternalMapper.selectByInstitute(institute);
    }

    @Override
    public ExaminationExternal queryExaminationExternalByEiid(int eiid) {
        return examinationExternalMapper.selectByEiid(eiid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateExaminationExternalByEeid(ExaminationExternal examinationExternal) {
        return examinationExternalMapper.updateByEeidSelective(examinationExternal);
    }
}
