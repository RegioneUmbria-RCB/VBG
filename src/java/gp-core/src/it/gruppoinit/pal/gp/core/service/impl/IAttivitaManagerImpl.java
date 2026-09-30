package it.gruppoinit.pal.gp.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaDaChiudereHelper;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ICalcoloSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitaManager;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;
import it.gruppoinit.pal.gp.core.utils.LoggerChiusuraAttivitaScadute;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class IAttivitaManagerImpl extends BaseEnvironment implements IAttivitaManager {

    private IAttivitaService iAttivitaService;
    private ICalcoloSnapshotService calcoloSnapshotService;

    @Autowired
    public void setCalcoloSnapshotService(ICalcoloSnapshotService calcoloSnapshotService) {

	this.calcoloSnapshotService = calcoloSnapshotService;
    }

    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Override
    public void updateProcessaAttivitaChiudere(String idComuneAlias, String software) {

	if (StringUtils.isNotBlank(idComuneAlias)) {
	    if (StringUtils.isNotBlank(software)) {
		setORMHelperSoftware(idComuneAlias, software);
	    } else {
		setORMHelper(idComuneAlias);
	    }
	}
	Date today = new Date();
	LoggerChiusuraAttivitaScadute.logInfo("Metto NON OPERANTI e non ATTIVE le attivita con data < {}",
		new String[] { Utilities.formatDate(today, false) });
	List<IAttivitaDaChiudereHelper> iAttivitaDaChiudereHelpers = iAttivitaService.findAttivitaScadute(today, null, null);
	LoggerChiusuraAttivitaScadute.logInfo("NUMERO ATTIVITA DA CHIUDERE: {}", new String[] { String.valueOf(iAttivitaDaChiudereHelpers.size()) });
	int ok = 0;
	int ko = 0;
	for (IAttivitaDaChiudereHelper iAttivitaDaChiudereHelper : iAttivitaDaChiudereHelpers) {
	    try {
		calcoloSnapshotService.chiudiAttivitaTemporanea(iAttivitaDaChiudereHelper.getCodiceattivita());
		ok++;
		LoggerChiusuraAttivitaScadute.logInfo("######OK####### Attivita {} [{}] - {} - {} CHIUSA",
			new String[] { iAttivitaDaChiudereHelper.getAttivita(), iAttivitaDaChiudereHelper.getCodiceattivita().toString(),
				iAttivitaDaChiudereHelper.getIdcomune(), iAttivitaDaChiudereHelper.getSoftware() });
	    } catch (Exception e) {
		ko++;
		LoggerChiusuraAttivitaScadute.logInfo("######KO####### Attivita {} [{}] - {} - {} non aggiornata. CAUSA: {}",
			new String[] { iAttivitaDaChiudereHelper.getAttivita(), iAttivitaDaChiudereHelper.getCodiceattivita().toString(),
				iAttivitaDaChiudereHelper.getIdcomune(), iAttivitaDaChiudereHelper.getSoftware(), e.getMessage() });
	    }
	}
	LoggerChiusuraAttivitaScadute.logInfo("##################   RESOCONTO   #####################");
	LoggerChiusuraAttivitaScadute.logInfo("NUMERO ATTIVITA CHIUSE: {}/{}",
		new String[] { String.valueOf(ok), String.valueOf(iAttivitaDaChiudereHelpers.size()) });
	LoggerChiusuraAttivitaScadute.logInfo("NUMERO ATTIVITA CHIUSRA KO: {}",
		new String[] { String.valueOf(ko), String.valueOf(iAttivitaDaChiudereHelpers.size()) });
    }
}
