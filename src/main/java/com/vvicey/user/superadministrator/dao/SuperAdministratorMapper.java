package com.vvicey.user.superadministrator.dao;

import com.vvicey.user.superadministrator.entity.SuperAdministrator;
import com.vvicey.user.tempentity.SuperAdministratorLoginer;
import org.springframework.stereotype.Repository;

/**
 * @Author nana
 * @Date 18-8-30 上午11:09
 * @Description 超级管理员数据库CRUD映射抽象类
 */
@Repository
public interface SuperAdministratorMapper {
    /**
     * 根据主键删除超级管理员
     *
     * @param said said
     * @return 删除结果
     */
    int deleteByPrimaryKey(Integer said);

    /**
     * 插入超级管理员信息
     *
     * @param record 超级管理员信息
     * @return 插入结果
     */
    int insertSelective(SuperAdministrator record);

    /**
     * 根据uid查询超级管理员信息
     *
     * @param uid uid
     * @return 查询结果
     */
    SuperAdministratorLoginer selectSuperAdministratorSelf(int uid);

    /**
     * 根据uid更新超级管理员信息
     *
     * @param updateByUidSelective 超级管理员信息
     * @return 更新结果
     */
    int updateByUidSelective(SuperAdministrator updateByUidSelective);
}