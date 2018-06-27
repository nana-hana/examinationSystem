package com.vvicey.user.student.service;

import com.vvicey.user.student.dao.StudentMapper;
import com.vvicey.user.student.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author nana
 * @Date 18-6-27 上午9:21
 * @Description 学生可执行的操作实现类
 */
@Service("StudentServiceImpl")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 删除学生个人信息
     *
     * @param uid 要删除的学生id
     * @return 返回删除成功与否
     */
    @Override
    public int deleteStudent(int uid) {
        return studentMapper.deleteByUid(uid);
    }

    /**
     * 插入学生个人信息
     *
     * @param student 要插入的学生信息
     * @return 返回插入成功与否
     */
    @Override
    public int createStudentInfo(Student student) {
        return studentMapper.insertSelective(student);
    }

    /**
     * 查询学生个人信息
     *
     * @param studentNumber 学生学号
     * @return 返回查询的学生数据
     */
    @Override
    public Student queryStudentInfo(int studentNumber) {
        return studentMapper.selectByStudentNumber(studentNumber);
    }

    /**
     * 更新学生个人信息
     *
     * @param student 学生数据
     * @return 返回更新结果
     */
    @Override
    public int updateStudentInfo(Student student) {
        return studentMapper.updateByStudentNumberSelective(student);
    }
}
