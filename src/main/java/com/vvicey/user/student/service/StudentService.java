package com.vvicey.user.student.service;

import com.vvicey.user.student.entity.Student;

/**
 * @Author nana
 * @Date 18-6-27 上午9:19
 * @Description 学生可执行的操作抽象类
 */
public interface StudentService {

    int deleteStudent(int uid);

    int createStudentInfo(Student student);

    Student queryStudentInfoByStudentNumber(int studentNumber);

    Student queryStudentInfoByUid(int uid);

    int updateStudentInfoByStudentNumber(Student student);

    int updateStudentInfoByUid(Student student);
}
