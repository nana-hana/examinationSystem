package com.vvicey.user.teacher.dao;

import com.vvicey.user.teacher.entity.Teacher;

/**
 * @Author nana
 * @Date 18-6-25 下午3:51
 * @Description 教师数据库CRUD映射抽象类
 */
public interface TeacherMapper {
    int deleteByPrimaryKey(Integer tid);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);
}