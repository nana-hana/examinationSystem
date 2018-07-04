package com.vvicey.user.teacher.dao;

import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.tempEntity.TeacherLoginer;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-27 上午9:54
 * @Description 教师数据库CRUD映射抽象类
 */
public interface TeacherMapper {

    int deleteByUid(int uid);

    int insertSelective(Teacher teacher);

    void insertTeacherRole(int uid);

    List<TeacherLoginer> selectAllTeacher();

    TeacherLoginer selectTeacherSelf(int uid);

    int updateByTeacherNumberSelective(Teacher teacher);

    int updateByUidSelective(Teacher teacher);

}