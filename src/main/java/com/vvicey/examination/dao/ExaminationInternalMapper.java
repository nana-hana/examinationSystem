package com.vvicey.examination.dao;

import com.vvicey.examination.entity.ExaminationInternal;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 下午11:00
 * @Description 考试事件数据库CRUD映射抽象类
 */
@Repository
public interface ExaminationInternalMapper {

    /**
     *  根据eiid删除考试内在信息
     * @param eiid eiid
     * @return 返回删除结果
     */
    int deleteByEiid(Integer eiid);

    /**
     * 插入考试内在信息
     * @param examinationInternal examinationInternal
     * @return 返回插入结果
     */
    int insertSelective(ExaminationInternal examinationInternal);

    /**
     * 根据eiid搜索考试内在信息
     * @param eiid eiid
     * @return 返回搜索结果
     */
    ExaminationInternal selectByEiid(int eiid);

    /**
     * 根据eiid更新考试内在信息
     * @param examinationInternal examinationInternal
     */
    void updateByEiidSelective(ExaminationInternal examinationInternal);

    /**
     * 根据学生班级搜索考试内在信息
     * @param studentClass 学生班级
     * @return 返回搜索结果
     */
    List<ExaminationInternal> selectByStudentClass(int studentClass);
}