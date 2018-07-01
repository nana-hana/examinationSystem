package com.vvicey.examination.dao;

import com.vvicey.examination.entity.ExaminationExternal;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 下午11:26
 * @Description 考试外在因素数据库CRUD映射抽象类
 */
public interface ExaminationExternalMapper {

    int deleteByEeid(Integer eeid);

    int insertSelective(ExaminationExternal examinationExternal);

    List<ExaminationExternal> selectByInstitute(int institute);

    ExaminationExternal selectByEiid(int eiid);

    int updateByEeidSelective(ExaminationExternal examinationExternal);
}