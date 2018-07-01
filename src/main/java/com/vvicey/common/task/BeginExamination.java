package com.vvicey.common.task;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.awt.print.Paper;

/**
 * @Author nana
 * @Date 18-7-1 下午12:20
 * @Description 事件开始考试定时生成
 */
@Component
public class BeginExamination implements Job {


    private void paperGeneration(int i) {
        if (i == 0) {
            System.out.println(0);
        } else {
            System.out.println(1);
        }
    }

    private Paper paperABGeneration() {

        return null;
    }

    private Paper paperRandomGeneration() {

        return null;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        int examinationKind = (Integer) dataMap.get("data1");
        paperGeneration(examinationKind);
    }
}
