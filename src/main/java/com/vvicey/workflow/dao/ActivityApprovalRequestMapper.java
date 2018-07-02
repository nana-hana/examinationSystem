package com.vvicey.workflow.dao;

import com.vvicey.workflow.entity.ActivityApprovalRequest;

/**
 * @Author nana
 * @Date 18-7-2 下午3:07
 * @Description 申请审批状态数据库CRUD映射抽象类
 */
public interface ActivityApprovalRequestMapper {

    int deleteByEiid(Integer eiid);

    int insertSelective(ActivityApprovalRequest activityApprovalRequest);

    ActivityApprovalRequest selectByEiid(Integer eiid);

    int updateByEiidSelective(ActivityApprovalRequest activityApprovalRequest);

}