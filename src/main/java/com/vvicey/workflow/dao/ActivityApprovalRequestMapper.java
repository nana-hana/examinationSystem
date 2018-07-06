package com.vvicey.workflow.dao;

import com.vvicey.user.tempEntity.ActivityInternal;
import com.vvicey.workflow.entity.ActivityApprovalRequest;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-2 下午3:07
 * @Description 申请审批状态数据库CRUD映射抽象类
 */
public interface ActivityApprovalRequestMapper {

    void deleteByEiid(Integer eiid);

    int insertSelective(ActivityApprovalRequest activityApprovalRequest);

    List<ActivityInternal> selectByTeacherNumber(int teacherNumber);

    List<ActivityInternal> selectAll();

    ActivityInternal selectByEiidAI(int eiid);

    ActivityApprovalRequest selectByEiidA(int eiid);

    void updateByEiidSelective(ActivityApprovalRequest activityApprovalRequest);

}