package com.vvicey.user.student.dao;

import com.vvicey.user.student.entity.Student;
import com.vvicey.user.tempEntity.StudentLoginer;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-25 下午3:51
 * @Description 学生数据库CRUD映射抽象类
 */
public interface StudentMapper {

    int deleteByUid(int uid);

    int insertSelective(Student student);

    void insertStudentRole(int uid);

    List<StudentLoginer> selectAllStudent();

    StudentLoginer selectStudentSelf(int uid);

    int updateByStudentNumberSelective(Student student);

    int updateByUidSelective(Student student);
}