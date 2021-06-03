package ru.blss.lab1.configs.quartz;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzSubmitJobs {
    @Bean(name = "dropUnpaid")
    public JobDetailFactoryBean jobMemberStats() {
        return QuartzConfig.createJobDetail(DeleteUnpaidOrdersJod.class, "Remove unpaid orders job");
    }

    @Bean(name = "dropUnpaidTrigger")
    public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("dropUnpaid") JobDetail jobDetail) {
//        return QuartzConfig.createTrigger(jobDetail, 86400000, "Remove unpaid Trigger");
        return QuartzConfig.createTrigger(jobDetail, 5000, "Remove unpaid Trigger");
    }
}
