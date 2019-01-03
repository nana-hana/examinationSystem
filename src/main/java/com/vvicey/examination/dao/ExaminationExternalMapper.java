package com.vvicey.examination.dao;

import com.vvicey.examination.entity.ExaminationExternal;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 下午11:26
 * @Description 考试外在因素数据库CRUD映射抽象类
 */
@Repository
public interface ExaminationExternalMapper {

    /**
     * 根据eiid删除考试外在信息
     * @param eeid eiid
     * @return 返回删除结果
     */
    int deleteByEeid(Integer eeid);

    /**
     * 插入考试外在信息
     * @param examinationExternal examinationExternal
     * @return 返回插入结果
     */
    int insertSelective(ExaminationExternal examinationExternal);

    /**
     * 根据学院搜索考试外在信息
     * @param institute 学院
     * @return 返回搜索结果
     */
    List<ExaminationExternal> selectByInstitute(int institute);

    /**
     * 根据eiid搜索考试外在信息
     * @param eiid eiid
     * @return 返回搜索结果
     */
    ExaminationExternal selectByEiid(int eiid);

    /**
     * 根据eiid更新考试外在信息
     * @param examinationExternal examinationExternal
     * @return 返回更新结果
     */
    int updateByEeidSelective(ExaminationExternal examinationExternal);
}