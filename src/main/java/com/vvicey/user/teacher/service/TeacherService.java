package com.vvicey.user.teacher.service;

import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.tempEntity.TeacherLoginer;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-26 下午9:49
 * @Description 老师可执行的操作抽象类
 */
public interface TeacherService {

    void createTeacherInfo(Teacher teacher);

    int deleteTeacher(int uid);

    List<TeacherLoginer> queryAllTeacher();

    TeacherLoginer queryTeacherSelf(int uid);

    int updateTeacherInfoByTeacherNumber(Teacher teacher);

    int updateTeacherInfoByUid(Teacher teacher);

}
