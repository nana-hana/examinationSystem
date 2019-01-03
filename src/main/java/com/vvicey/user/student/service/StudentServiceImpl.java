package com.vvicey.user.student.service;

import com.vvicey.user.student.dao.StudentMapper;
import com.vvicey.user.student.entity.Student;
import com.vvicey.user.tempentity.StudentLoginer;
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

    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteStudent(int uid) {
        return studentMapper.deleteByUid(uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createStudentInfo(Student student) {
        studentMapper.insertStudentRole(student.getUid());
        studentMapper.insertSelective(student);
    }

    @Override
    public List<StudentLoginer> queryAllStudent() {
        return studentMapper.selectAllStudent();
    }

    @Override
    public StudentLoginer queryStudentSelf(int uid) {
        return studentMapper.selectStudentSelf(uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStudentInfoByStudentNumber(Student student) {
        return studentMapper.updateByStudentNumberSelective(student);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStudentInfoByUid(Student student) {
        studentMapper.updateByUidSelective(student);
    }
}
