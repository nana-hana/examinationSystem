package com.vvicey.user.teacher.dao;

import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.tempentity.TeacherLoginer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-27 上午9:54
 * @Description 教师数据库CRUD映射抽象类
 */
@Repository
public interface TeacherMapper {

    /**
     * 根据uid删除老师信息
     *
     * @param uid uid
     * @return 删除结果
     */
    int deleteByUid(int uid);

    /**
     * 插入老师信息
     *
     * @param teacher teacher
     * @return 插入结果
     */
    int insertSelective(Teacher teacher);

    /**
     * 插入老师角色
     *
     * @param uid uid
     */
    void insertTeacherRole(int uid);

    /**
     * 搜索所有老师
     *
     * @return 搜索结果
     */
    List<TeacherLoginer> selectAllTeacher();

    /**
     * 根据学院搜索老师
     *
     * @param institute institute
     * @return 搜索结果
     */
    List<TeacherLoginer> selectByInstitute(Integer institute);

    /**
     * 根据uid搜索老师
     *
     * @param uid uid
     * @return 搜索结果
     */
    TeacherLoginer selectTeacherSelf(int uid);

    /**
     * 根据老师编号更新老师信息
     *
     * @param teacher teacher
     * @return 更新结果
     */
    int updateByTeacherNumberSelective(Teacher teacher);

    /**
     * 根据uid更新老师信息
     *
     * @param teacher teacher
     * @return 更新结果
     */
    int updateByUidSelective(Teacher teacher);

}