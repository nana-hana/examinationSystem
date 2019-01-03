package com.vvicey.user.administrator.service;

import com.vvicey.user.administrator.dao.AdministratorMapper;
import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.tempentity.AdministratorLoginer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author nana
 * @Date 18-6-30 上午10:43
 * @Description 管理员可执行的操作实现类
 */
@Service("AdministratorServiceImpl")
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorMapper administratorMapper;

    @Autowired
    public AdministratorServiceImpl(AdministratorMapper administratorMapper) {
        this.administratorMapper = administratorMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createAdministratorInfo(Administrator administrator) {
        administratorMapper.insertAdministratorRole(administrator.getUid());
        return administratorMapper.insertSelective(administrator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAdministrator(int uid) {
        return administratorMapper.deleteByUid(uid);
    }

    @Override
    public List<AdministratorLoginer> queryAllAdministrator() {
        return administratorMapper.selectAllAdministrator();
    }

    @Override
    public AdministratorLoginer queryAdministratorSelf(int uid) {
        return administratorMapper.selectAdministratorSelf(uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAdministratorInfoByName(Administrator administrator) {
        return administratorMapper.updateByAdministratorNameSelective(administrator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAdministratorInfoByUid(Administrator administrator) {
        administratorMapper.updateByUidSelective(administrator);
    }
}
