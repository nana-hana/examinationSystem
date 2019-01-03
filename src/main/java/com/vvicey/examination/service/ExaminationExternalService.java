package com.vvicey.examination.service;

import com.vvicey.examination.entity.ExaminationExternal;
import com.vvicey.examination.entity.ExaminationInternal;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 下午11:59
 * @Description 考试外在因素的操作抽象类
 */
public interface ExaminationExternalService {

    /**
     * 创建考试详情
     *
     * @param examinationExternal 考试详情
     * @return 返回创建成功与否
     */
    int createExaminationExternal(ExaminationExternal examinationExternal);

    /**
     * 删除考试详情
     *
     * @param eeid 考试编号id
     * @return 返回删除成功与否
     */
    int deleteExaminationExternal(int eeid);

    /**
     * 根据试卷id查询考试外在因素申请
     *
     * @param eiid 考试编号
     * @return 返回查询结果
     */
    ExaminationExternal queryExaminationExternalByEiid(int eiid);

    /**
     * 根据学院查询考试外在因素申请
     *
     * @param institute 学院
     * @return 返回查询结果
     */
    List<ExaminationExternal> queryExaminationExternalByInstitute(int institute);

    /**
     * 根据考试外在因素编号id对考试外在因素信息进行更新
     *
     * @param examinationExternal 考试详情
     * @return 返回更新结果
     */
    int updateExaminationExternalByEeid(ExaminationExternal examinationExternal);

}
