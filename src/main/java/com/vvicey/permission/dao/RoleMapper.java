package com.vvicey.permission.dao;

import com.vvicey.permission.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * @Author nana
 * @Date 18-6-26 下午2:07
 * @Description 角色数据库CRUD映射抽象类
 */
@Repository
public interface RoleMapper {
    /**
     * 根据主键删除角色
     *
     * @param rid rid
     * @return 删除结果
     */
    int deleteByPrimaryKey(Integer rid);

    /**
     * 插入角色
     *
     * @param record 角色
     * @return 返回插入成功与否
     */
    int insert(Role record);

    /**
     * 插入角色
     *
     * @param record 角色
     * @return 返回插入成功与否
     */
    int insertSelective(Role record);

    /**
     * 根据主键搜索角色
     *
     * @param rid rid
     * @return 返回搜索结果
     */
    Role selectByPrimaryKey(Integer rid);

    /**
     * 根据主键更新角色
     *
     * @param record 角色
     * @return 返回更新结果
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * 根据主键更新角色
     *
     * @param record 角色
     * @return 返回更新结果
     */
    int updateByPrimaryKey(Role record);
}