package com.vvicey.examination.dao;

import com.vvicey.examination.entity.ExaminationInternal;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 下午11:00
 * @Description 考试事件数据库CRUD映射抽象类
 */
public interface ExaminationInternalMapper {

    int deleteByEiid(Integer eiid);

    int insertSelective(ExaminationInternal examinationInternal);

    ExaminationInternal selectByEiid(int eiid);

    int updateByEiidSelective(ExaminationInternal examinationInternal);

}