package com.vvicey.permission.dao;

import com.vvicey.permission.entity.Permission;
import org.springframework.stereotype.Repository;

/**
 * @Author nana
 * @Date 18-6-26 下午2:07
 * @Description 权限数据库CRUD映射抽象类
 */
@Repository
public interface PermissionMapper {
    /**
     * 根据pid删除任务
     *
     * @param pid pid
     * @return 返回删除结果
     */
    int deleteByPrimaryKey(Integer pid);

    /**
     * 插入任务
     *
     * @param record 任务
     * @return 返回插入结果
     */
    int insert(Permission record);

    /**
     * 插入任务
     *
     * @param record 任务
     * @return 返回插入结果
     */
    int insertSelective(Permission record);

    /**
     * 根据主键搜索任务
     *
     * @param pid pid
     * @return 返回搜索结果
     */
    Permission selectByPrimaryKey(Integer pid);

    /**
     * 根据主键更新任务
     *
     * @param record 任务
     * @return 返回更新结果
     */
    int updateByPrimaryKeySelective(Permission record);

    /**
     * 根据主键更新任务
     *
     * @param record 任务
     * @return 返回更新结果
     */
    int updateByPrimaryKey(Permission record);
}