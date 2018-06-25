package com.vvicey.user.student.dao;

import com.vvicey.user.student.entity.Student;

/**
 * @Author nana
 * @Date 18-6-25 下午3:51
 * @Description 学生数据库CRUD映射抽象类
 */
public interface StudentMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}