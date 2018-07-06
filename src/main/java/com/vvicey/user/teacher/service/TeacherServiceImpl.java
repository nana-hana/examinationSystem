package com.vvicey.user.teacher.service;

import com.vvicey.user.teacher.dao.TeacherMapper;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.tempEntity.TeacherLoginer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     */
    @Override
    @Transactional
    public void createTeacherInfo(Teacher teacher) {
        teacherMapper.insertTeacherRole(teacher.getUid());
        teacherMapper.insertSelective(teacher);
    }

    /**
     * 删除教师个人信息
     *
     * @param uid 根据教师uid进行删除
     * @return 返回删除成功与否
     */
    @Override
    @Transactional
    public int deleteTeacher(int uid) {
        return teacherMapper.deleteByUid(uid);
    }

    /**
     * 查询所有教师
     *
     * @return 返回查询的教师数据
     */
    @Override
    public List<TeacherLoginer> queryAllTeacher() {
        return teacherMapper.selectAllTeacher();
    }

    /**
     * 查询登陆教师自己
     *
     * @param uid 传入uid
     * @return 返回教师数据
     */
    @Override
    public TeacherLoginer queryTeacherSelf(int uid) {
        return teacherMapper.selectTeacherSelf(uid);
    }

    /**
     * 更新教师个人信息(根据编号)
     *
     * @param teacher 传入需要更新的教师信息
     * @return 返回更新成功与否
     */
    @Override
    @Transactional
    public int updateTeacherInfoByTeacherNumber(Teacher teacher) {
        return teacherMapper.updateByTeacherNumberSelective(teacher);
    }

    /**
     * 更新教师个人信息(根据账号id)
     *
     * @param teacher 教师数据
     */
    @Override
    @Transactional
    public void updateTeacherInfoByUid(Teacher teacher) {
        teacherMapper.updateByUidSelective(teacher);
    }
}
