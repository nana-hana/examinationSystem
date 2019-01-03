package com.vvicey.user.student.service;

import com.vvicey.user.student.entity.Student;
import com.vvicey.user.tempentity.StudentLoginer;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-27 上午9:19
 * @Description 学生可执行的操作抽象类
 */
public interface StudentService {

    /**
     * 删除学生个人信息
     *
     * @param uid 要删除的学生id
     * @return 返回删除成功与否
     */
    int deleteStudent(int uid);

    /**
     * 插入学生个人信息(包含创建学生身份)
     *
     * @param student 要插入的学生信息
     */
    void createStudentInfo(Student student);

    /**
     * 查询所有学生
     *
     * @return 返回查询的学生数据
     */
    List<StudentLoginer> queryAllStudent();

    /**
     * 查询登陆学生自己
     *
     * @param uid 传入uid
     * @return 返回学生数据
     */
    StudentLoginer queryStudentSelf(int uid);

    /**
     * 更新学生个人信息(根据学号)
     *
     * @param student 学生数据
     * @return 返回更新结果
     */
    int updateStudentInfoByStudentNumber(Student student);

    /**
     * 更新学生个人信息(根据账号id)
     *
     * @param student 学生数据
     */
    void updateStudentInfoByUid(Student student);
}
