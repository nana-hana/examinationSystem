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

    int createExaminationExternal(ExaminationExternal examinationExternal);

    int deleteExaminationExternal(int eeid);

    ExaminationExternal queryExaminationExternalByEiid(int eiid);

    List<ExaminationExternal> queryExaminationExternalByInstitute(int institute);

    int updateExaminationExternalByEeid(ExaminationExternal examinationExternal);

}
