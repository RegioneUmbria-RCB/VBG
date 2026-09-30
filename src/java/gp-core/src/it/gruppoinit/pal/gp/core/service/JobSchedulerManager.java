package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

public interface JobSchedulerManager {

    public void schedulePersistentJobs();

    public void schedulePersistentJobsForScheduler();

    public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException;

    public void triggerJob(String getIdentificativoJob) throws Exception;
}
