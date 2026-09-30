package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;

public class JobExecutionContextForJson {

    private long jobRunTime;
    private int refireCount;
    private String fireTime;
    private String previousFireTime;
    private String nextFireTime;
    private String scheduledFireTime;

    public JobExecutionContextForJson(JobExecutionContext jec) {

	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN);
	jobRunTime = jec.getJobRunTime();
	refireCount = jec.getRefireCount();
	Date _fireTime = jec.getFireTime();
	if (_fireTime != null) {
	    fireTime = sdf.format(_fireTime);
	}
	Date _nextFireTime = jec.getNextFireTime();
	if (_nextFireTime != null) {
	    nextFireTime = sdf.format(_nextFireTime);
	}
	Date _previousFireTime = jec.getPreviousFireTime();
	if (_previousFireTime != null) {
	    previousFireTime = sdf.format(_previousFireTime);
	}
	Date _scheduledFireTime = jec.getScheduledFireTime();
	if (_scheduledFireTime != null) {
	    scheduledFireTime = sdf.format(_scheduledFireTime);
	}
    }

    public long getJobRunTime() {

	return jobRunTime;
    }

    public void setJobRunTime(long jobRunTime) {

	this.jobRunTime = jobRunTime;
    }

    public int getRefireCount() {

	return refireCount;
    }

    public void setRefireCount(int refireCount) {

	this.refireCount = refireCount;
    }

    public String getFireTime() {

	return fireTime;
    }

    public void setFireTime(String fireTime) {

	this.fireTime = fireTime;
    }

    public String getPreviousFireTime() {

	return previousFireTime;
    }

    public void setPreviousFireTime(String previousFireTime) {

	this.previousFireTime = previousFireTime;
    }

    public String getNextFireTime() {

	return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {

	this.nextFireTime = nextFireTime;
    }

    public String getScheduledFireTime() {

	return scheduledFireTime;
    }

    public void setScheduledFireTime(String scheduledFireTime) {

	this.scheduledFireTime = scheduledFireTime;
    }
}
