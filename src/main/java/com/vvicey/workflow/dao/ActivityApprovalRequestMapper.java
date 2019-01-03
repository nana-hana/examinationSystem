package com.vvicey.workflow.dao;

import com.vvicey.user.tempentity.ActivityInternal;
import com.vvicey.workflow.entity.ActivityApprovalRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-2 下午3:07
 * @Description 申请审批状态数据库CRUD映射抽象类
 */
@Repository
public interface ActivityApprovalRequestMapper {

    /**
     * 根据eiid删除审批事件
     *
     * @param eiid eiid
     */
    void deleteByEiid(Integer eiid);

    /**
     * 插入申请事件
     *
     * @param activityApprovalRequest activityApprovalRequest
     * @return 插入结果
     */
    int insertSelective(ActivityApprovalRequest activityApprovalRequest);

    /**
     * 根据老师编号查询申请事件
     *
     * @param teacherNumber teacherNumber
     * @return 查询结果
     */
    List<ActivityInternal> selectByTeacherNumber(int teacherNumber);

    /**
     * 搜索全部
     *
     * @return 查询结果
     */
    List<ActivityInternal> selectAll();

    /**
     * 根据eiid搜索
     *
     * @param eiid eiid
     * @return 查询结果
     */
    ActivityInternal selectByEiidAI(int eiid);

    /**
     * 根据eiid搜索
     *
     * @param eiid eiid
     * @return 查询结果
     */
    ActivityApprovalRequest selectByEiidA(int eiid);

    /**
     * 根据eiid更新申请信息
     *
     * @param activityApprovalRequest activityApprovalRequest
     */
    void updateByEiidSelective(ActivityApprovalRequest activityApprovalRequest);

}