package com.vvicey.user.superAdministrator.service;

import com.vvicey.user.superAdministrator.dao.SuperAdministratorMapper;
import com.vvicey.user.superAdministrator.entity.SuperAdministrator;
import com.vvicey.user.tempEntity.SuperAdministratorLoginer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author nana
 * @Date 18-8-30 上午11:09
 * @Description 超级管理员可执行的操作实现类
 */
@Service("SuperAdministratorServiceImpl")
public class SuperAdministratorServiceImpl implements SuperAdministratorService {

    @Autowired
    private SuperAdministratorMapper superAdministratorMapper;

    /**
     * 根据uid查询超级管理员信息
     *
     * @param uid 超级管理员的uid
     * @return 返回超级管理员对象
     */
    @Override
    public SuperAdministratorLoginer querySuperAdministratorSelf(int uid) {
        return superAdministratorMapper.selectSuperAdministratorSelf(uid);
    }

    /**
     * 根据超级管理员uid对超级管理员信息进行更新
     *
     * @param superAdministrator 包含需要修改uid的超级管理员信息
     */
    @Override
    public void updateSuperAdministratorInfoByUid(SuperAdministrator superAdministrator) {
        superAdministratorMapper.updateByUidSelective(superAdministrator);
    }
}
