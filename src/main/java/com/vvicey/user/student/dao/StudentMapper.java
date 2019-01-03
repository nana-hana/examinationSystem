package com.vvicey.user.student.dao;

import com.vvicey.user.student.entity.Student;
import com.vvicey.user.tempentity.StudentLoginer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-25 下午3:51
 * @Description 学生数据库CRUD映射抽象类
 */
@Repository
public interface StudentMapper {

    /**
     * 根据uid删除学生
     * @param uid uid
     * @return 删除结果
     */
    int deleteByUid(int uid);

    /**
     * 插入学生
     * @param student student
     * @return 插入结果
     */
    int insertSelective(Student student);

    /**
     * 插入学生角色
     * @param uid uid
     */
    void insertStudentRole(int uid);

    /**
     * 搜索所有学生
     * @return 搜索结果
     */
    List<StudentLoginer> selectAllStudent();

    /**
     * 根据uid搜索学生
     * @param uid uid
     * @return 搜索结果
     */
    StudentLoginer selectStudentSelf(int uid);

    /**
     * 根据学号更新学生信息
     * @param student student
     * @return 更新结果
     */
    int updateByStudentNumberSelective(Student student);

    /**
     * 根据uid更新学生信息
     * @param student student
     * @return 更新结果
     */
    int updateByUidSelective(Student student);
}