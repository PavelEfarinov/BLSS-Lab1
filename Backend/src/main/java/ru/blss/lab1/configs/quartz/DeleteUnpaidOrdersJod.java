package ru.blss.lab1.configs.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.blss.lab1.service.DeliveryService;

@Slf4j
@Component
@DisallowConcurrentExecution
public class DeleteUnpaidOrdersJod implements Job {

    @Autowired
    private DeliveryService deliveryService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Removing all unpaid orders from quartz job.");
        deliveryService.RemoveAllUnpaid();
        log.info("Quartz job finished.");
    }
}
