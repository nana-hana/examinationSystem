package com.vvicey.user.administrator.dao;

import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.tempentity.AdministratorLoginer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-25 下午3:51
 * @Description 管理员数据库CRUD映射抽象类
 */
@Repository
public interface AdministratorMapper {

    /**
     * 根据uid删除管理
     *
     * @param aid aid
     * @return 删除结果
     */
    int deleteByUid(int aid);

    /**
     * 插入管理员
     *
     * @param administrator
     * @return 插入结果
     */
    int insertSelective(Administrator administrator);

    /**
     * 插入管理员
     *
     * @param uid uid
     */
    void insertAdministratorRole(int uid);

    /**
     * 搜索所有管理员
     *
     * @return 搜索结果
     */
    List<AdministratorLoginer> selectAllAdministrator();

    /**
     * 根据uid查询管理员信息
     *
     * @param uid uid
     * @return 返回管理员信息
     */
    AdministratorLoginer selectAdministratorSelf(int uid);

    /**
     * 根据管理员名字更新信息
     *
     * @param administrator administrator
     * @return 返回更新结果
     */
    int updateByAdministratorNameSelective(Administrator administrator);

    /**
     * 根据uid更新管理员
     *
     * @param updateByUidSelective administrator
     * @return 返回更新结果
     */
    int updateByUidSelective(Administrator updateByUidSelective);

}