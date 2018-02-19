/**
 *
 */
package com.bgkh.jobs;

import com.bgkh.config.QuartzConfiguration;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author pavan.solapure
 *
 */
@Component
@DisallowConcurrentExecution
public class WorkOrderJobCronTrigger implements Job {

    private final Logger logger = LoggerFactory.getLogger(WorkOrderJobCronTrigger.class);

	@Value("${work.order.job.schedule}")
	private String frequency;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		logger.info("Running JobWithCronTrigger | frequency {}", frequency);
	}

	@Bean(name = "jobWithCronTriggerBean")
	public JobDetailFactoryBean sampleJob() {
		return QuartzConfiguration.createJobDetail(this.getClass());
	}

	@Bean(name = "jobWithCronTriggerBeanTrigger")
	public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithCronTriggerBean") JobDetail jobDetail) {
		return QuartzConfiguration.createCronTrigger(jobDetail, frequency);
	}
}
