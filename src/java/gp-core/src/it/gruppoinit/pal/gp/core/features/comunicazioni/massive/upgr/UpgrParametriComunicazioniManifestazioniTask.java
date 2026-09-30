package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.upgr;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrUpgrParametriComunicazioniManifestazioniTask")
public class UpgrParametriComunicazioniManifestazioniTask extends BaseJavaTask {

    @Autowired
    private IUpgrParametriComunicazioniManifestazioniService service;

    @Override
    public void initialize() throws SetupRunException {

	// non serve nessuna inizializzazione
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	this.service.aggiornaTipoComunicazioni202309();
	return 0;
    }
}
