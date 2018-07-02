package com.vvicey.examination.service;

import com.vvicey.examination.dao.ExaminationInternalMapper;
import com.vvicey.examination.entity.ExaminationInternal;
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

    @Autowired
    private ExaminationInternalMapper examinationInternalMapper;

    /**
     * 创建考试事件
     *
     * @param examinationInternal 试卷详情
     * @return 返回创建成功与否
     */
    @Override
    @Transactional
    public int createExamination(ExaminationInternal examinationInternal) {
        return examinationInternalMapper.insertSelective(examinationInternal);
    }

    /**
     * 删除考试事件
     *
     * @param eiid 考试编号id
     * @return 返回删除成功与否
     */
    @Override
    @Transactional
    public int deleteExamination(int eiid) {
        return examinationInternalMapper.deleteByEiid(eiid);
    }

    /**
     * 根据提交考试事情的eiid查询考试信息
     *
     * @param eiid 考试编号id
     * @return 返回查询结果
     */
    @Override
    public ExaminationInternal queryExaminationInternalByEiid(int eiid) {
        return examinationInternalMapper.selectByEiid(eiid);
    }

    /**
     * 根据考试编号id对考试信息进行更新
     *
     * @param examinationInternal 试卷详情
     * @return 返回更新结果
     */
    @Override
    @Transactional
    public int updateExaminationByEiid(ExaminationInternal examinationInternal) {
        return examinationInternalMapper.updateByEiidSelective(examinationInternal);
    }
}
