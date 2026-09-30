package it.gruppoinit.pal.gp.pay.scheduler;

import java.util.Calendar;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.service.IElaborazioneTracciatiService;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;

public class ServizioSchedulatoTracciati implements IServizioSchedulato, Job {

    private static final Logger log = LoggerFactory.getLogger(ServizioSchedulatoTracciati.class);
    public static final int SCHEDULER_TRIGGER_START_DELAY_MIN = 1;

    enum PARAMETRI {
	SERVICE_NAME,
	INSTANCE_OF_SERVICE
    }

    private IElaborazioneTracciatiService etService;

    private ServizioSchedulatoTracciati() {

	super();
    }

    public ServizioSchedulatoTracciati(IElaborazioneTracciatiService etService) {

	this();
	this.etService = etService;
    }

    @Override
    public void configuraESchedula(Scheduler scheduler) {

	String serviceName = etService.getServiceName();
	if (!etService.isAttivo()) {
	    log.error("configuraESchedula - Non è stato possibile attivare lo schedulatore per il servizio {} del connettore {}", serviceName,
		    etService.getIPayConnector().getConnectorCode() + "-" + etService.getIPayConnector().getConnectorName());
	    return;
	}
	Calendar triggerStartTime = Calendar.getInstance();
	JobDataMap data = new JobDataMap();
	data.put(PARAMETRI.SERVICE_NAME.name(), serviceName);
	data.put(PARAMETRI.INSTANCE_OF_SERVICE.name(), etService);
	JobDetail jd = JobBuilder.newJob(ServizioSchedulatoTracciati.class).withIdentity(serviceName, etService.getIPayConnector().getConnectorCode())
		.usingJobData(data).build();
	//creo il cron trigger
	triggerStartTime.add(Calendar.MINUTE, SCHEDULER_TRIGGER_START_DELAY_MIN);
	String quartzScheduleExpression = etService.getQuartzScheduleExpression();
	ScheduleBuilder<CronTrigger> sb = CronScheduleBuilder.cronSchedule(quartzScheduleExpression);
	Trigger ct = TriggerBuilder.newTrigger().withIdentity(serviceName, etService.getIPayConnector().getConnectorCode()).withSchedule(sb)
		.startAt(triggerStartTime.getTime()).build();
	//schedulo il job nello scheduler
	try {
	    scheduler.scheduleJob(jd, ct);
	    String stato = " stato attivo";
	    if (log.isInfoEnabled()) {
		log.info("configuraESchedula - schedulato job {} con intervallo {}{} start at {}", jd.getKey(), quartzScheduleExpression, stato,
			Utilities.formatDate(triggerStartTime.getTime(), true));
	    }
	} catch (SchedulerException e) {
	    log.error("configuraESchedula - errore nella schedulazione del job " + jd.getKey(), e);
	}
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

	IElaborazioneTracciatiService service = (IElaborazioneTracciatiService) context.getMergedJobDataMap()
		.get(PARAMETRI.INSTANCE_OF_SERVICE.name());
	EsitoElaborazione elabora = service.elabora();
	if (!elabora.isEsito()) {
	    log.error("Errore nell'elaborazione del servizio schedulato {}: {}", service.getServiceName(), elabora.getMessaggio());
	}
    }
}
