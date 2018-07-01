package com.vvicey.common.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-1 下午1:59
 * @Description 封装Quartz动态添加、修改和删除定时任务时间的方法
 */
public class QuartzManagerUtils {

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * 添加定时任务(jobGroup相当于triggerGroup为institute，jobName和trigger相同,后面的方法同此处)
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     * @param jobClass     任务的类类型  eg:TimedMassJob.class
     * @param cron         cron时间表达式
     * @param objects      可变参数需要进行传参的值
     */
    @SuppressWarnings("unchecked")
    public static void addJob(String jobName, String jobGroupName, Class jobClass, String cron, Object... objects) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            // 触发器
            if (objects != null) {
                for (int i = 0; i < objects.length; i++) {
                    //该数据可以通过Job中的JobDataMap dataMap = context.getJobDetail().getJobDataMap();来进行参数传递值
                    jobDetail.getJobDataMap().put("data" + (i + 1), objects[i]);
                }
            }
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(jobName, jobGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询当前正在运行的任务
     *
     * @return 返回当前正在运行的所有任务
     */
    public static List<JobExecutionContext> queryJob() {
        List<JobExecutionContext> jobList;
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            jobList = scheduler.getCurrentlyExecutingJobs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jobList;
    }

    /**
     * 更新任务的触发时间
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     * @param cron         cron时间表达式
     */
    public static void modifyJobTime(String jobName, String jobGroupName, String cron) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(jobName, jobGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除任务
     *
     * @param jobName      任务名
     * @param jobGroupName 任务组名
     */
    public static void removeJob(String jobName, String jobGroupName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}