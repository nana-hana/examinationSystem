package com.vvicey.examination.service;

import com.vvicey.examination.entity.ExaminationInternal;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 下午5:46
 * @Description 考试事件的操作抽象类
 */
public interface ExaminationInternalService {

    int createExamination(ExaminationInternal examinationInternal);

    int deleteExamination(int eiid);

    ExaminationInternal queryExaminationInternalByEiid(int eiid);

    void updateExaminationByEiid(ExaminationInternal examinationInternal);

    List<ExaminationInternal> queryExaminationInternalByStudentClass(int studentClass);
}
