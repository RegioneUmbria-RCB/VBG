package it.gruppoinit.pal.gp.core.features.scheduler;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.sorteggi.categorie.SorteggiCategorieService;
import it.gruppoinit.pal.gp.core.features.sorteggi.scheduler.SorteggiSchedulerServiceImpl;
import it.gruppoinit.pal.gp.core.features.sorteggi.testata.SorteggitestataService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;

public class TaskSchedulerServiceFactory {

    private Map<TaskEnum, ITaskSchedulerService> _services;

    public TaskSchedulerServiceFactory(AlberoprocService alberoProcService, AmministrazioniService amministrazioniService,
	    ComuniassociatiService comuniAssociatiService, MailtipoService mailTipoService, ResponsabiliService responsabiliService,
	    SorteggiCategorieService sorteggiCategorieService, SorteggitestataService sorteggitestataService,
	    TipiMovimentoService tipiMovimentoService) {

	this._services = new HashMap<TaskEnum, ITaskSchedulerService>();
	this._services.put(SorteggiSchedulerServiceImpl.getTipo(),
		new SorteggiSchedulerServiceImpl(alberoProcService, amministrazioniService, comuniAssociatiService, mailTipoService,
			responsabiliService, sorteggiCategorieService, sorteggitestataService, tipiMovimentoService));
    }

    public ITaskSchedulerService get(TaskEnum tipo) {

	return this._services.get(tipo);
    }
}
