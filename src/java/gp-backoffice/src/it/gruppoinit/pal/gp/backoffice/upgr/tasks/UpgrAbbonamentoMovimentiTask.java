package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.upgr.IUpgrAbbonamentoMovimentiService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrAbbonamentoMovimentiTask")
public class UpgrAbbonamentoMovimentiTask extends BaseJavaTask {

    @Autowired
    private IUpgrAbbonamentoMovimentiService service;

    @Override
    public void initialize() throws SetupRunException {

	//implementazione non necessaria
    }
    
    @Override
    public int run(Session arg0) throws SetupRunException {

	this.service.aggiornaMovimentiAbbonamento();
	return 0;
    }
}
