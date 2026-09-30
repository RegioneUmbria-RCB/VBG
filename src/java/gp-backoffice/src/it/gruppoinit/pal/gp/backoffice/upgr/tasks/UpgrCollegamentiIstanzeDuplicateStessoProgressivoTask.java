package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.upgr.UpgrCollegamentiIstanzeDuplicateStessoProgressivoService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrUpgrCollegamentiIstanzeDuplicateStessoProgressivoTask")
public class UpgrCollegamentiIstanzeDuplicateStessoProgressivoTask extends BaseJavaTask {

    @Autowired
    private UpgrCollegamentiIstanzeDuplicateStessoProgressivoService upgrService;

    @Override
    public void initialize() throws SetupRunException {

	// implementazione non necessaria
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	this.upgrService.eseguiUpgr();
	return 0;
    }
}
