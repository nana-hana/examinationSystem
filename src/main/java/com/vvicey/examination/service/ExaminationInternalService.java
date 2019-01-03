package com.vvicey.examination.service;

import com.vvicey.examination.entity.ExaminationInternal;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 下午5:46
 * @Description 考试事件的操作抽象类
 */
public interface ExaminationInternalService {

    /**
     * 创建考试事件
     *
     * @param examinationInternal 试卷详情
     * @return 返回创建成功与否
     */
    int createExamination(ExaminationInternal examinationInternal);

    /**
     * 删除考试事件
     *
     * @param eiid 考试编号id
     * @return 返回删除成功与否
     */
    int deleteExamination(int eiid);

    /**
     * 根据提交考试事情的eiid查询考试信息
     *
     * @param eiid 考试编号id
     * @return 返回查询结果
     */
    ExaminationInternal queryExaminationInternalByEiid(int eiid);

    /**
     * 根据考试编号id对考试信息进行更新
     *
     * @param examinationInternal 试卷详情
     */
    void updateExaminationByEiid(ExaminationInternal examinationInternal);

    /**
     * 根据学生班级查询考试内在信息
     * @param studentClass 学生班级
     * @return 返回该班级全部试卷详情
     */
    List<ExaminationInternal> queryExaminationInternalByStudentClass(int studentClass);
}
