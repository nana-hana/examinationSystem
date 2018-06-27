package com.vvicey.user.teacher.service;

import com.vvicey.user.teacher.dao.TeacherMapper;
import com.vvicey.user.teacher.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author nana
 * @Date 18-6-26 下午9:50
 * @Description 老师可执行的操作实现类
 */
@Service("TeacherServiceImpl")
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 创建教师个人信息
     *
     * @param teacher 需要创建的教师信息
     * @return 返回创建成功与否
     */
    @Override
    public int createTeacherInfo(Teacher teacher) {
        return teacherMapper.insertSelective(teacher);
    }

    /**
     * 删除教师个人信息
     *
     * @param uid 根据教师uid进行删除
     * @return 返回删除成功与否
     */
    @Override
    public int deleteTeacher(int uid) {
        return teacherMapper.deleteByUid(uid);
    }

    /**
     * 查询教师个人信息
     *
     * @param teacherNumber 根据教师编号查询
     * @return 返回查询的教师数据
     */
    @Override
    public Teacher queryTeacherInfo(int teacherNumber) {
        return teacherMapper.selectByTeacherNumber(teacherNumber);
    }

    /**
     * 更新教师个人信息
     *
     * @param teacher 传入需要更新的教师信息
     * @return 返回更新成功与否
     */
    @Override
    public int updateTeacherInfo(Teacher teacher) {
        return teacherMapper.updateByTeacherNumberSelective(teacher);
    }
}
