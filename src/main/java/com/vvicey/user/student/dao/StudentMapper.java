package com.vvicey.user.student.dao;

import com.vvicey.user.student.entity.Student;

/**
 * @Author nana
 * @Date 18-6-25 下午3:51
 * @Description 学生数据库CRUD映射抽象类
 */
public interface StudentMapper {

    int deleteByUid(int uid);

    int insertSelective(Student student);

    Student selectByStudentNumber(int studentNumber);

    int updateByStudentNumberSelective(Student student);
}