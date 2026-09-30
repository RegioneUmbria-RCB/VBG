package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.JobRepository;
import it.gruppoinit.pal.gp.core.service.JobRepositoryService;
import it.gruppoinit.pal.gp.core.service.JobSchedulerManager;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class JobSchedulerManagerImpl extends BaseEnvironment implements JobSchedulerManager {

    private static Logger logger = LoggerFactory.getLogger(JobSchedulerManagerImpl.class);
    @Autowired
    private JobRepositoryService jobrepositoryService;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void schedulePersistentJobsForScheduler() {

	try {
	    Properties p = new Properties();
	    InputStream is = getClass().getClassLoader().getResourceAsStream("deploy.properties");
	    p.load(is);
	    is.close();
	    String idcomunealias = p.getProperty("scheduler.idcomunealias");
	    setORMHelper(idcomunealias);
	    this.schedulePersistentJobs();
	} catch (Exception e) {
	    logger.error("schedulePersistentJobsForScheduler", e);
	} finally {
	    resetThreadLocalVars();
	}
    }

    @Override
    public void schedulePersistentJobs() {

	List<JobRepository> jobRepositoryList = jobrepositoryService.findAll(null, null);
	logger.info("Retriving Jobs from Database and Scheduling One by One | Total Number of Jobs: {}", jobRepositoryList.size());
	try {
	    Scheduler scheduler = schedulerFactoryBean.getScheduler();
	    for (JobRepository jobRepo : jobRepositoryList) {
		JobDetail job = new JobDetail();
		job.setName(jobRepo.getJobName());
		job.setJobClass(Class.forName(jobRepo.getJobClassName()));
		job.setJobDataMap(getJobDataMap(jobRepo));
		if (!jobRepo.getActive()) {
		    logger.info("Deleting Job: {}", jobRepo.getJobName());
		    if (checkExists(scheduler, jobRepo.getJobName()))
			scheduler.deleteJob(jobRepo.getJobName(), Scheduler.DEFAULT_GROUP);
		    continue;
		}
		if (checkExists(scheduler, jobRepo.getJobName())) {
		    logger.info("Rescheduling the Job: {}", jobRepo.getJobName());
		    Trigger oldTrigger = scheduler.getTrigger(jobRepo.getJobName() + "Trigger", Scheduler.DEFAULT_GROUP);
		    Trigger newTrigger = getTrigger(jobRepo);
		    scheduler.rescheduleJob(oldTrigger.getKey().getName(), Scheduler.DEFAULT_GROUP, newTrigger);
		} else {
		    logger.info("Scheduling the Job: {}", jobRepo.getJobName());
		    scheduler.scheduleJob(job, getTrigger(jobRepo));
		}
	    }
	} catch (Exception e) {
	    logger.error("schedulePersistentJobs", e);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException {

	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	return scheduler.getCurrentlyExecutingJobs();
    }

    private boolean checkExists(Scheduler scheduler, String jobName) throws SchedulerException {

	String[] jobs = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
	if (jobs != null) {
	    for (String _jobName : jobs) {
		if (jobName.equals(_jobName)) {
		    return true;
		}
	    }
	}
	return false;
    }

    private JobDataMap getJobDataMap(JobRepository jobRepository) {

	JobDataMap jobDataMap = new JobDataMap();
	jobDataMap.put(WebConstants.IDCOMUNE_ALIAS, jobRepository.getAlias());
	jobDataMap.put(WebConstants.SOFTWARE, jobRepository.getModulo());
	return jobDataMap;
    }

    private Trigger getTrigger(JobRepository jobRepository) throws ParseException {

	Trigger trigger = null;
	Long startDelay = jobRepository.getStartDelay() != null ? jobRepository.getStartDelay() : 0;
	Date startTime = new Date(System.currentTimeMillis() + startDelay);
	Integer repeatCount = SimpleTrigger.REPEAT_INDEFINITELY;
	Long repeatInterval = jobRepository.getRepeatInterval();
	String cronExpression = jobRepository.getCronExpression();
	String jobName = jobRepository.getJobName();
	if (jobRepository.getTriggerType().equals("SIMPLE")) {
	    trigger = new SimpleTrigger(jobName + "Trigger", Scheduler.DEFAULT_GROUP, jobName, Scheduler.DEFAULT_GROUP, startTime, null, repeatCount,
		    repeatInterval);
	} else {
	    trigger = new CronTrigger(jobName + "Trigger", Scheduler.DEFAULT_GROUP, jobName, Scheduler.DEFAULT_GROUP, cronExpression);
	}
	return trigger;
    }
}
