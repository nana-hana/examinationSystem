package com.vvicey.user.teacher.service;

import com.vvicey.user.teacher.entity.Teacher;

/**
 * @Author nana
 * @Date 18-6-26 下午9:49
 * @Description 老师可执行的操作抽象类
 */
public interface TeacherService {

    int createTeacherInfo(Teacher teacher);

    int deleteTeacher(int uid);

    Teacher queryTeacherInfo(int teacherNumber);

    int updateTeacherInfo(Teacher teacher);
}
