package com.vvicey.user.administrator.dao;

import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.tempEntity.AdministratorLoginer;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-25 下午3:51
 * @Description 管理员数据库CRUD映射抽象类
 */
public interface AdministratorMapper {

    int deleteByUid(int aid);

    int insertSelective(Administrator administrator);

    void insertAdministratorRole(int uid);

    List<AdministratorLoginer> selectAllAdministrator();

    AdministratorLoginer selectAdministratorSelf(int uid);

    int updateByAdministratorNameSelective(Administrator administrator);

    int updateByUidSelective(Administrator updateByUidSelective);

}