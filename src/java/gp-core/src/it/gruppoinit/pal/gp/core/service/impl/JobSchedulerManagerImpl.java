package it.gruppoinit.pal.gp.core.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.JobRepository;
import it.gruppoinit.pal.gp.core.domain.JobRepositoryParam;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.jobs.ScadenzeAutorizzazioniConcessioniJob;
import it.gruppoinit.pal.gp.core.service.JobRepositoryParamService;
import it.gruppoinit.pal.gp.core.service.JobRepositoryService;
import it.gruppoinit.pal.gp.core.service.JobSchedulerManager;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;

@Component
public class JobSchedulerManagerImpl extends BaseEnvironment implements JobSchedulerManager {

    public static final String SCHEDULER_ABILITA_ATTIVITA_SISTEMA = "scheduler.abilita.attivita.sistema";
    public static final String SYSTEM_SCHEDULER_PROPERTIES = "system-scheduler.properties";
    private static Logger logger = LoggerFactory.getLogger(JobSchedulerManagerImpl.class);
    @Autowired
    private JobRepositoryService jobrepositoryService;
    @Autowired
    private JobRepositoryParamService jobRepositoryParamService;
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
	    this.scheduleSystemJobs(idcomunealias);
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
		popolaESchedulaJob(scheduler, jobRepo);
	    }
	} catch (Exception e) {
	    logger.error("schedulePersistentJobs", e);
	}
    }

    private void popolaESchedulaJob(Scheduler scheduler, JobRepository jobRepo) throws ClassNotFoundException, SchedulerException, ParseException {

	JobDetail job = new JobDetail();
	job.setName(jobRepo.getIdentificativoJob());
	job.setJobClass(Class.forName(jobRepo.getJobClassName()));
	job.setJobDataMap(getJobDataMap(jobRepo));
	if (!jobRepo.getActive()) {
	    logger.info("Deleting Job: {}", jobRepo.getIdentificativoJob());
	    if (checkExists(scheduler, jobRepo.getIdentificativoJob()))
		scheduler.deleteJob(jobRepo.getIdentificativoJob(), Scheduler.DEFAULT_GROUP);
	    return;
	}
	if (checkExists(scheduler, jobRepo.getIdentificativoJob())) {
	    scheduler.deleteJob(jobRepo.getIdentificativoJob(), Scheduler.DEFAULT_GROUP);
	}
	logger.info("Scheduling the Job: {}", jobRepo.getIdentificativoJob());
	scheduler.scheduleJob(job, getTrigger(jobRepo));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException {

	Scheduler scheduler = schedulerFactoryBean.getScheduler();
	return scheduler.getCurrentlyExecutingJobs();
    }

    private boolean checkExists(Scheduler scheduler, String jobIdentificativo) throws SchedulerException {

	String[] jobs = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
	if (jobs != null) {
	    for (String _jobName : jobs) {
		if (jobIdentificativo.equals(_jobName)) {
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
	List<JobRepositoryParam> param = jobRepositoryParamService.findByJobRepository(jobRepository.getId());
	for (JobRepositoryParam jobRepositoryParam : param) {
	    jobDataMap.put(jobRepositoryParam.getEtichetta(), jobRepositoryParam.getValore());
	}
	return jobDataMap;
    }

    private Trigger getTrigger(JobRepository jobRepository) throws ParseException {

	Trigger trigger = null;
	Long startDelay = jobRepository.getStartDelay() != null ? jobRepository.getStartDelay() : 0;
	Date startTime = new Date(System.currentTimeMillis() + startDelay);
	Integer repeatCount = SimpleTrigger.REPEAT_INDEFINITELY;
	Long repeatInterval = jobRepository.getRepeatInterval();
	String cronExpression = jobRepository.getCronExpression();
	String jobIdentificativo = jobRepository.getIdentificativoJob();
	if (jobRepository.getTriggerType().equals("SIMPLE")) {
	    trigger = new SimpleTrigger(jobIdentificativo + "Trigger", Scheduler.DEFAULT_GROUP, jobIdentificativo, Scheduler.DEFAULT_GROUP, startTime,
		    null, repeatCount, repeatInterval);
	} else {
	    trigger = new CronTrigger(jobIdentificativo + "Trigger", Scheduler.DEFAULT_GROUP, jobIdentificativo, Scheduler.DEFAULT_GROUP,
		    cronExpression);
	    trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
	}
	return trigger;
    }

    @Override
    public void triggerJob(String jobIdentificativo) throws Exception {

	try {
	    Scheduler scheduler = schedulerFactoryBean.getScheduler();
	    if (!checkExists(scheduler, jobIdentificativo)) {
		JobRepository jobRepository = jobrepositoryService.findById(new JobRepository().getIdDaItentificativo(jobIdentificativo));
		popolaESchedulaJob(scheduler, jobRepository);
	    }
	    scheduler.triggerJob(jobIdentificativo, Scheduler.DEFAULT_GROUP);
	} catch (SchedulerException e) {
	    logger.error("triggerJob: {}: {}", jobIdentificativo, e.getMessage());
	    throw e;
	}
    }

    private Properties getSystemSchedulerProperties() throws IOException {

	Properties p = new Properties();
	InputStream is = getClass().getClassLoader().getResourceAsStream(SYSTEM_SCHEDULER_PROPERTIES);
	p.load(is);
	is.close();
	return p;
    }

    private void scheduleSystemJobs(String idcomunealiassistema) {

	try {
	    int idBaseJobSistema = 99000000;
	    Properties p = getSystemSchedulerProperties();
	    if (StringUtils.defaultString(p.getProperty(SCHEDULER_ABILITA_ATTIVITA_SISTEMA), "false").equalsIgnoreCase("true")) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobRepository jobRepo = getJobChiusuraAutorizzazioni(idcomunealiassistema, p, ++idBaseJobSistema);
		popolaESchedulaJobSistema(scheduler, jobRepo, p);
	    } else {
		logger.warn(
			"I Job di sistema non risultano abilitati! Controllare il parametro scheduler.abilita.attivita.sistema del file system-scheduler.properties");
	    }
	} catch (Exception e) {
	    logger.error("scheduleSystemJobs", e);
	}
    }

    private void popolaESchedulaJobSistema(Scheduler scheduler, JobRepository jobRepo, Properties systemSchedulerProperties)
	    throws ClassNotFoundException, SchedulerException, ParseException {

	JobDetail job = new JobDetail();
	job.setName(jobRepo.getIdentificativoJob());
	job.setJobClass(Class.forName(jobRepo.getJobClassName()));
	job.setJobDataMap(getJobDataMapSistema(jobRepo, systemSchedulerProperties));
	if (!jobRepo.getActive()) {
	    logger.info("Deleting Job: {}", jobRepo.getIdentificativoJob());
	    if (checkExists(scheduler, jobRepo.getIdentificativoJob()))
		scheduler.deleteJob(jobRepo.getIdentificativoJob(), Scheduler.DEFAULT_GROUP);
	    return;
	}
	if (checkExists(scheduler, jobRepo.getIdentificativoJob())) {
	    scheduler.deleteJob(jobRepo.getIdentificativoJob(), Scheduler.DEFAULT_GROUP);
	}
	logger.info("Scheduling the Job: {}", jobRepo.getIdentificativoJob());
	scheduler.scheduleJob(job, getTrigger(jobRepo));
    }

    public static void main(String[] args) throws IOException {

	System.out.println(ScadenzeAutorizzazioniConcessioniJob.class.getSimpleName());
	System.out.println(ScadenzeAutorizzazioniConcessioniJob.class.getName());
	String className = ScadenzeAutorizzazioniConcessioniJob.class.getName();
	JobSchedulerManagerImpl cl = new JobSchedulerManagerImpl();
	String nomeChiave = "scheduler." + className.substring(className.lastIndexOf(".") + 1) + ".param.";
	for (Entry<Object, Object> entry : cl.getSystemSchedulerProperties().entrySet()) {
	    String parametro = (String) entry.getKey();
	    String valore = (String) entry.getValue();
	    System.out.println(parametro);
	    if (StringUtils.defaultString(parametro).startsWith(nomeChiave)) {
		String chiave = parametro.replace(nomeChiave, "");
		System.out.println(chiave + "=" + valore);
		/// jobDataMap.put(chiave, valore);
	    }
	}
    }

    private JobDataMap getJobDataMapSistema(JobRepository jobRepository, Properties systemSchedulerProperties) {

	JobDataMap jobDataMap = new JobDataMap();
	jobDataMap.put(WebConstants.IDCOMUNE_ALIAS, jobRepository.getAlias());
	jobDataMap.put(WebConstants.SOFTWARE, WebConstants.SOFTWARE_TT);
	String className = jobRepository.getJobClassName();
	String nomeChiave = "scheduler." + className.substring(className.lastIndexOf(".") + 1) + ".param.";
	for (Entry<Object, Object> entry : systemSchedulerProperties.entrySet()) {
	    String parametro = (String) entry.getKey();
	    String valore = (String) entry.getValue();
	    if (StringUtils.defaultString(parametro).startsWith(nomeChiave) && StringUtils.isNotBlank(valore)) {
		String chiave = parametro.replace(nomeChiave, "");
		jobDataMap.put(chiave, valore);
	    }
	}
	return jobDataMap;
    }

    private JobRepository getJobChiusuraAutorizzazioni(String idcomunealiassistema, Properties p, int idBaseJobSistema) {

	JobRepository jobRepo = new JobRepository();
	jobRepo.setActive(Boolean.TRUE);
	jobRepo.setAlias(idcomunealiassistema);
	jobRepo.setDescription("Cessazione Automatica autorizzazioni per scadenza/cessazione/fine affitto");
	String cron = p.getProperty("scheduler." + ScadenzeAutorizzazioniConcessioniJob.class.getSimpleName() + ".cron");
	jobRepo.setCronExpression(StringUtils.defaultIfEmpty(cron, "0 5 20 ? * * *")); // alle 20.05 di ogni giorno
	jobRepo.setId(idBaseJobSistema);
	jobRepo.setJobClassName(ScadenzeAutorizzazioniConcessioniJob.class.getName());
	jobRepo.setJobName("CESSAZIONE_AUTOMATICA_AUTORIZZAZIONI");
	jobRepo.setModulo(WebConstants.SOFTWARE_TT);
	jobRepo.setTriggerType("CRON");
	return jobRepo;
    }
}
