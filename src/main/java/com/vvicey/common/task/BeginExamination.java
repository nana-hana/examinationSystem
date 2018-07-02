package com.vvicey.common.task;

import com.vvicey.examination.entity.ExaminationPaper;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @Author nana
 * @Date 18-7-1 下午12:20
 * @Description 事件开始考试定时生成
 */
@Component
public class BeginExamination implements Job {

    /**
     * 试卷类型
     */
    private static final int PAPER_RANDOM = 1;//随机不同卷
    private static final int PAPER_AB = 0;//AB卷

    /**
     * 试卷生成
     *
     * @param i 试卷类型
     */
    private ExaminationPaper paperGeneration(int i) {
        if (i == PAPER_AB) {
            return paperABGeneration();
        }
        if (i == PAPER_RANDOM) {
            return paperRandomGeneration();
        }
        return null;
    }

    /**
     * 试卷AB
     *
     * @return 返回生成的试卷
     */
    private ExaminationPaper paperABGeneration() {

        return null;
    }

    /**
     * 试卷随机
     *
     * @return 返回生成的试卷
     */
    private ExaminationPaper paperRandomGeneration() {

        return null;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        int examinationKind = (Integer) dataMap.get("data1");
        ExaminationPaper examinationPaper = paperGeneration(examinationKind);
        //如何返回paper？
    }
}
