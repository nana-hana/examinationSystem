package com.vvicey.user.superadministrator.service;

import com.vvicey.user.superadministrator.dao.SuperAdministratorMapper;
import com.vvicey.user.superadministrator.entity.SuperAdministrator;
import com.vvicey.user.tempentity.SuperAdministratorLoginer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author nana
 * @Date 18-8-30 上午11:09
 * @Description 超级管理员可执行的操作实现类
 */
@Service("SuperAdministratorServiceImpl")
public class SuperAdministratorServiceImpl implements SuperAdministratorService {

    private final SuperAdministratorMapper superAdministratorMapper;

    @Autowired
    public SuperAdministratorServiceImpl(SuperAdministratorMapper superAdministratorMapper) {
        this.superAdministratorMapper = superAdministratorMapper;
    }

    @Override
    public SuperAdministratorLoginer querySuperAdministratorSelf(int uid) {
        return superAdministratorMapper.selectSuperAdministratorSelf(uid);
    }

    @Override
    public void updateSuperAdministratorInfoByUid(SuperAdministrator superAdministrator) {
        superAdministratorMapper.updateByUidSelective(superAdministrator);
    }
}
