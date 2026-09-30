package it.gruppoinit.pal.gp.backoffice.aop;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.service.IAttivitaSnapshotService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.impl.IattivitaShapshotTask;

@Aspect
public class IattivitaSnapShotAspect {

    private static final Logger log = LoggerFactory.getLogger(IattivitaSnapShotAspect.class);
    private TaskExecutor taskExecutor;
    private IAttivitaService iAttivitaService;
    private IstanzeService istanzeService;
    private IAttivitaSnapshotService iAttivitaSnapshotService;

    @Autowired
    public void setTaskExecutor(TaskExecutor taskExecutor) {

	this.taskExecutor = taskExecutor;
    }

    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setiAttivitaSnapshotService(IAttivitaSnapshotService iAttivitaSnapshotService) {

	this.iAttivitaSnapshotService = iAttivitaSnapshotService;
    }

    // metodo eseguito dall'aspetto che cattura l' IattivitaService.update() (Salva attivita a video)
    public void doSnapShot(IAttivita attivita) {

	ThreadPoolTaskExecutor executor = new org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor();
	executor.setCorePoolSize(20);
	executor.setMaxPoolSize(30);
	executor.setQueueCapacity(40);
	log.debug("Inizio esecuzione aspetto : doSnapShot......");
	taskExecutor.execute(new IattivitaShapshotTask(iAttivitaService, istanzeService, iAttivitaSnapshotService, false,
		attivita.getIstanza().getId().getCodice(), ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(),
		ORMHelper.getToken()));
	log.debug("Fine esecuzione aspetto : doSnapShot......");
    }

    public void doSnapShotCollega(Istanze istanza, IAttivita attivita) {

	ThreadPoolTaskExecutor executor = new org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor();
	executor.setCorePoolSize(20);
	executor.setMaxPoolSize(30);
	executor.setQueueCapacity(40);
	log.debug("Inizio esecuzione aspetto : doSnapShotCollegaOrScollega......");
	iAttivitaService.updateCampiSchedeDinamiche(attivita.getId().getCodice(), null);
	taskExecutor.execute(new IattivitaShapshotTask(iAttivitaService, istanzeService, iAttivitaSnapshotService, false, istanza.getId().getCodice(),
		ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper.getToken()));
	log.debug("Fine esecuzione aspetto : doSnapShotCollegaOrScollega......");
    }

    public void doSnapShotScollega(Istanze istanza, IAttivita attivita) {

	ThreadPoolTaskExecutor executor = new org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor();
	executor.setCorePoolSize(20);
	executor.setMaxPoolSize(30);
	executor.setQueueCapacity(40);
	log.debug("Inizio esecuzione aspetto : doSnapShotCollegaOrScollega......");
	iAttivitaService.updateCampiSchedeDinamiche(attivita.getId().getCodice(), null);
	// Prima lo prendevo dall'attività
	taskExecutor.execute(new IattivitaShapshotTask(iAttivitaService, istanzeService, iAttivitaSnapshotService, true, istanza.getId().getCodice(),
		ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper.getToken()));
	//	executor.execute(new IattivitaShapshotTask(iAttivitaService, istanzeService, iAttivitaSnapshotService, true, istanza.getId().getCodice(),
	//		ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper.getToken()));
	log.debug("Fine esecuzione aspetto : doSnapShotCollegaOrScollega......");
    }

    public void doSnapShotCalcolaDataValiditaIstanza(Integer codiceIstanza) {

	//	ThreadPoolTaskExecutor executor = new org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor();
	//	executor.setCorePoolSize(1);
	//	executor.setMaxPoolSize(1);
	//	executor.setQueueCapacity(10);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (EntityUtils.getNestedProperty(istanza.getAttivita(), "id.codice") != null) {
	    Integer codiceAttivita = istanza.getAttivita().getId().getCodice();
	    iAttivitaService.updateCampiSchedeDinamiche(codiceAttivita, null);
	    iAttivitaService.flush();
	}
	log.debug("Inizio esecuzione aspetto : doSnapShotCalcolaDataValiditaIstanza......");
	//	taskExecutor.execute(new IattivitaShapshotTask(iAttivitaService, istanzeService, iAttivitaSnapshotService, false, codiceIstanza, ORMHelper
	//		.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper.getToken()));
	//	iAttivitaSnapshotService.updateRutineSnapShot(false, codiceIstanza, ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(),
	//		ORMHelper.getSoftware(), ORMHelper.getToken());
	Thread iattivitaShapshotTask = new IattivitaShapshotTask(iAttivitaService, istanzeService, iAttivitaSnapshotService, false, codiceIstanza,
		ORMHelper.getIdcomuneAlias(), ORMHelper.getIdcomune(), ORMHelper.getSoftware(), ORMHelper.getToken());
	iattivitaShapshotTask.start();
	log.debug("Fine esecuzione aspetto : doSnapShotCalcolaDataValiditaIstanza......");
    }
}
