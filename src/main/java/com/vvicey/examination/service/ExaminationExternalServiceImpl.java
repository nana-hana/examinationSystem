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

    @Autowired
    private ExaminationExternalMapper examinationExternalMapper;

    /**
     * 创建考试详情
     *
     * @param examinationExternal 考试详情
     * @return 返回创建成功与否
     */
    @Override
    @Transactional
    public int createExaminationExternal(ExaminationExternal examinationExternal) {
        return examinationExternalMapper.insertSelective(examinationExternal);
    }

    /**
     * 删除考试详情
     *
     * @param eeid 考试编号id
     * @return 返回删除成功与否
     */
    @Override
    @Transactional
    public int deleteExaminationExternal(int eeid) {
        return examinationExternalMapper.deleteByEeid(eeid);
    }

    /**
     * 根据学院查询考试外在因素申请
     *
     * @param institute 学院
     * @return 返回查询结果
     */
    @Override
    public List<ExaminationExternal> queryExaminationExternalByInstitute(int institute) {
        return examinationExternalMapper.selectByInstitute(institute);
    }

    /**
     * 根据试卷id查询考试外在因素申请
     *
     * @param eiid 考试编号
     * @return 返回查询结果
     */
    @Override
    public ExaminationExternal queryExaminationExternalByEiid(int eiid) {
        return examinationExternalMapper.selectByEiid(eiid);
    }

    /**
     * 根据考试外在因素编号id对考试外在因素信息进行更新
     *
     * @param examinationExternal 考试详情
     * @return 返回更新结果
     */
    @Override
    @Transactional
    public int updateExaminationExternalByEeid(ExaminationExternal examinationExternal) {
        return examinationExternalMapper.updateByEeidSelective(examinationExternal);
    }
}
