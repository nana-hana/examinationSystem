package com.vvicey.user.student.service;

import com.vvicey.user.student.dao.StudentMapper;
import com.vvicey.user.student.entity.Student;
import com.vvicey.user.tempEntity.StudentLoginer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public int deleteStudent(int uid) {
        return studentMapper.deleteByUid(uid);
    }

    /**
     * 插入学生个人信息(包含创建学生身份)
     *
     * @param student 要插入的学生信息
     */
    @Override
    @Transactional
    public void createStudentInfo(Student student) {
        studentMapper.insertStudentRole(student.getUid());
        studentMapper.insertSelective(student);
    }

    /**
     * 查询所有学生
     *
     * @return 返回查询的学生数据
     */
    @Override
    public List<StudentLoginer> queryAllStudent() {
        return studentMapper.selectAllStudent();
    }

    /**
     * 查询登陆学生自己
     *
     * @param uid 传入uid
     * @return 返回学生数据
     */
    @Override
    public StudentLoginer queryStudentSelf(int uid) {
        return studentMapper.selectStudentSelf(uid);
    }

    /**
     * 更新学生个人信息(根据学号)
     *
     * @param student 学生数据
     * @return 返回更新结果
     */
    @Override
    @Transactional
    public int updateStudentInfoByStudentNumber(Student student) {
        return studentMapper.updateByStudentNumberSelective(student);
    }

    /**
     * 更新学生个人信息(根据账号id)
     *
     * @param student 学生数据
     * @return 返回更新结果
     */
    @Override
    @Transactional
    public int updateStudentInfoByUid(Student student) {
        return studentMapper.updateByUidSelective(student);
    }
}
