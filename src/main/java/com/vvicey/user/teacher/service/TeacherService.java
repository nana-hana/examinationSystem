package com.vvicey.user.teacher.service;

import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.tempentity.TeacherLoginer;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-26 下午9:49
 * @Description 老师可执行的操作抽象类
 */
public interface TeacherService {

    /**
     * 创建教师个人信息(包含创建教师身份)
     *
     * @param teacher 需要创建的教师信息
     */
    void createTeacherInfo(Teacher teacher);

    /**
     * 删除教师个人信息
     *
     * @param uid 根据教师uid进行删除
     * @return 返回删除成功与否
     */
    int deleteTeacher(int uid);

    /**
     * 查询所有教师
     *
     * @return 返回查询的教师数据
     */
    List<TeacherLoginer> queryAllTeacher();

    /**
     * 查询登陆教师自己
     *
     * @param uid 传入uid
     * @return 返回教师数据
     */
    TeacherLoginer queryTeacherSelf(int uid);

    /**
     * 更新教师个人信息(根据编号)
     *
     * @param teacher 传入需要更新的教师信息
     * @return 返回更新成功与否
     */
    int updateTeacherInfoByTeacherNumber(Teacher teacher);

    /**
     * 更新教师个人信息(根据账号id)
     *
     * @param teacher 教师数据
     */
    void updateTeacherInfoByUid(Teacher teacher);

    /**
     * 导入题目，必须保证导入的文本文件中，题目数量和创建的考试中的题目数量一致， 并且题目难度必须和创建的考试中的题目难度一致
     *
     * @param filePath 文本文件存储路径
     * @param exam     当前考试信息
     * @return list集合存放导入失败信息
     */
    List<String> examInput(String filePath, ExaminationInternal exam);

    /**
     * 根据学院查询老师
     *
     * @param institute 学院
     * @return 返回登录信息
     */
    List<TeacherLoginer> queryByInstitute(Integer institute);
}
