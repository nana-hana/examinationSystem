package com.vvicey.user.administrator.service;

import com.vvicey.user.administrator.dao.AdministratorMapper;
import com.vvicey.user.administrator.entity.Administrator;
import com.vvicey.user.tempEntity.AdministratorLoginer;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
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

    @Autowired
    private AdministratorMapper administratorMapper;

    /**
     * 创建管理员个人信息(包含创建管理员身份)
     *
     * @param administrator 需要创建的管理员信息
     * @return 返回创建成功与否
     */
    @Override
    @Transactional
    public int createAdministratorInfo(Administrator administrator) {
        administratorMapper.insertAdministratorRole(administrator.getUid());
        return administratorMapper.insertSelective(administrator);
    }

    /**
     * 删除管理员个人信息
     *
     * @param uid 根据管理员uid进行删除
     * @return 返回删除成功与否
     */
    @Override
    @Transactional
    public int deleteAdministrator(int uid) {
        return administratorMapper.deleteByUid(uid);
    }

    /**
     * 查询所有管理员
     *
     * @return 返回查询的管理员数据
     */
    @Override
    public List<AdministratorLoginer> queryAllAdministrator() {
        return administratorMapper.selectAllAdministrator();
    }

    /**
     * 查询登陆管理员自己
     *
     * @param uid 用户id
     * @return 返回登陆管理员数据
     */
    @Override
    public AdministratorLoginer queryAdministratorSelf(int uid) {
        return administratorMapper.selectAdministratorSelf(uid);
    }

    /**
     * 更新管理员个人信息(根据编号)
     *
     * @param administrator 传入需要更新的管理员信息
     * @return 返回更新成功与否
     */
    @Override
    @Transactional
    public int updateAdministratorInfoByName(Administrator administrator) {
        return administratorMapper.updateByAdministratorNameSelective(administrator);
    }

    /**
     * 更新管理员个人信息(根据账号id)
     *
     * @param administrator 管理员数据
     */
    @Override
    @Transactional
    public void updateAdministratorInfoByUid(Administrator administrator) {
        administratorMapper.updateByUidSelective(administrator);
    }
}
