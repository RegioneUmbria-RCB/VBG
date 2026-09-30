package it.gruppoinit.pal.gp.core.scheduler.task;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportTaskAll extends ImportTask {

    private static final Logger log = LoggerFactory.getLogger(ImportTaskAll.class);
    @Autowired
    private ImportTaskFiereRegione taskFiereRegione;
    @Autowired
    private ImportTaskFiereSUAPE taskFiereSUAPE;
    @Autowired
    private ImportTaskSagreSUAPE taskSagreSUAPE;
    @Autowired
    private ComuniassociatiService comuniassociatiService;

    public void processTask() {

	ORMHelper.setIdcomuneAlias(deployProps.getProperty("idcomunealias"));
	ORMHelper.setIdcomune(deployProps.getProperty("idcomune"));
	List<Comuniassociati> listComuniImportAutomatico;
	try {
	    listComuniImportAutomatico = comuniassociatiService.findAllImportAutomatico();
	} finally {
	    ORMHelper.destroyORMHelper();
	}
	this.process(listComuniImportAutomatico);
    }

    @Override
    public void process(List<Comuniassociati> listComuniImportAutomatico) {

	log.info("process");
	taskFiereRegione.process(listComuniImportAutomatico);
	taskFiereSUAPE.process(listComuniImportAutomatico);
	taskSagreSUAPE.process(listComuniImportAutomatico);
	log.info("process...end");
    }
}
