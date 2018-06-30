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
     * 创建教师个人信息(包含创建教师身份)
     *
     * @param teacher 需要创建的教师信息
     * @return 返回创建成功与否
     */
    @Override
    public int createTeacherInfo(Teacher teacher) {
        teacherMapper.insertTeacherRole(teacher.getUid());
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
     * @param teacherNumber 教师编号
     * @return 返回查询的教师数据
     */
    @Override
    public Teacher queryTeacherInfoByTeacherNumber(int teacherNumber) {
        return teacherMapper.selectByTeacherNumber(teacherNumber);
    }

    /**
     * 查询教师个人信息
     *
     * @param uid 用户id
     * @return 返回查询的教师数据
     */
    @Override
    public Teacher queryTeacherInfoByUid(int uid) {
        return teacherMapper.selectByUid(uid);
    }

    /**
     * 更新教师个人信息(根据编号)
     *
     * @param teacher 传入需要更新的教师信息
     * @return 返回更新成功与否
     */
    @Override
    public int updateTeacherInfoByTeacherNumber(Teacher teacher) {
        return teacherMapper.updateByTeacherNumberSelective(teacher);
    }

    /**
     * 更新教师个人信息(根据账号id)
     *
     * @param teacher 教师数据
     * @return 返回更新成功与否
     */
    @Override
    public int updateTeacherInfoByUid(Teacher teacher) {
        return teacherMapper.updateByUidSelective(teacher);
    }
}
