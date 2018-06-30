package com.vvicey.examination.dao;

import com.vvicey.examination.entity.ExaminationInternal;

import java.util.List;

public interface ExaminationInternalMapper {

    int deleteByEiid(Integer eiid);

    int insertSelective(ExaminationInternal examinationInternal);

    ExaminationInternal selectByEiid(Integer eiid);

    List<ExaminationInternal> selectByTeacherNumber(Integer teacherNumber);

    int updateByEiidSelective(ExaminationInternal examinationInternal);

}