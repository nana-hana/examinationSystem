package com.vvicey.user.administrator.service;

import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.tempentity.AdministratorLoginer;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 上午10:40
 * @Description 管理员可执行的操作抽象类
 */
public interface AdministratorService {
    /**
     * 创建管理员个人信息(包含创建管理员身份)
     *
     * @param administrator 需要创建的管理员信息
     * @return 返回创建成功与否
     */
    int createAdministratorInfo(Administrator administrator);

    /**
     * 删除管理员个人信息
     *
     * @param uid 根据管理员uid进行删除
     * @return 返回删除成功与否
     */
    int deleteAdministrator(int uid);

    /**
     * 查询所有管理员
     *
     * @return 返回查询的管理员数据
     */
    List<AdministratorLoginer> queryAllAdministrator();

    /**
     * 查询登陆管理员自己
     *
     * @param uid 用户id
     * @return 返回登陆管理员数据
     */
    AdministratorLoginer queryAdministratorSelf(int uid);

    /**
     * 更新管理员个人信息(根据编号)
     *
     * @param administrator 传入需要更新的管理员信息
     * @return 返回更新成功与否
     */
    int updateAdministratorInfoByName(Administrator administrator);

    /**
     * 更新管理员个人信息(根据账号id)
     *
     * @param administrator 管理员数据
     */
    void updateAdministratorInfoByUid(Administrator administrator);
}
