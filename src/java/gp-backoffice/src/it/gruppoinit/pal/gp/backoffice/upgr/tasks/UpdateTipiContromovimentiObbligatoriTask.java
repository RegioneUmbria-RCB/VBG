package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.service.TipicontromovimentoService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrUpdateTipiContromovimentiObbligatoriTask")
public class UpdateTipiContromovimentiObbligatoriTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdateTipiContromovimentiObbligatoriTask.class);
    @Autowired
    private TipicontromovimentoService tcmService;

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	int numUpdated = 0;
	String idComuneSession = ORMHelper.getIdcomune();
	String idComune = "";
	String idComuneTemp = null;
	String codiceTipoMov = "";
	String sql = "SELECT TIPOMOVIMENTO, IDCOMUNE FROM TIPIMOVIMENTO WHERE FLAG_INTERRUZIONE = 1 OR FLAG_RICHIESTAINTEGRAZIONE = 1 ORDER BY IDCOMUNE";
	SQLQuery qTipiMov = session.createSQLQuery(sql);
	List<Object[]> tipiMovInterrotti = qTipiMov.list();
	Tipicontromovimento tcm = null;
	int numCmovObbligatori = 0;
	for (Object[] datiTipoMov : tipiMovInterrotti) {
	    numCmovObbligatori = 0;
	    idComuneTemp = (String) datiTipoMov[1];
	    codiceTipoMov = (String) datiTipoMov[0];
	    if (!idComune.equals(idComuneTemp)) {
		//Nuovo IDCOMUNE
		ORMHelper.setIdcomune(idComuneTemp);
		idComune = idComuneTemp;
		activityLogDebug("Inizio ricerca tipi contromovimento da impostare come obbligatori per l'IDCOMUNE: {}", new Object[] { idComune });
	    }
	    //per ciascun tipo movimento selezionato recupero la lista dei contromovimenti
	    Tipimovimento tm = new Tipimovimento();
	    tm.getId().setIdcomune(idComune);
	    tm.getId().setTipomovimento(codiceTipoMov);
	    List<Tipicontromovimento> controMov = tcmService.findByTipimovimento(tm);
	    for (int i = 0; i < controMov.size(); i++) {
		tcm = controMov.get(i);
		if (BooleanUtils.isTrue(tcm.getFlagbase())) {
		    numCmovObbligatori++;
		    Tipimovimento tipoMovControMov = tcm.getTipocontromovimento();
		    if (BooleanUtils.isFalse(tipoMovControMov.getFlagFinesospinterr()) || tipoMovControMov.getFlagFinesospinterr() == null) {
			tipoMovControMov.setFlagFinesospinterr(true);
			session.update(tipoMovControMov);
			numUpdated++;
			activityLogInfo(
				"IDCOMUNE: {}, TIPOMOVIMENTO: {}, IMPOSTATO TIPO CONTROMOVIMENTO OBBLIGATORIO {} COME FINE SOSPENSIONE/INTERRUZIONE",
				new Object[] { idComune, codiceTipoMov, tcm.getTipocontromovimento().getId().getTipomovimento() });
		    } else {
			activityLogDebug(
				"IDCOMUNE: {}, TIPOMOVIMENTO: {}, IL TIPO CONTROMOVIMENTO OBBLIGATORIO {} E' GIA' CONFIGURATO COME FINE SOSPENSIONE/INTERRUZIONE",
				new Object[] { idComune, codiceTipoMov, tcm.getTipocontromovimento().getId().getTipomovimento() });
		    }
		}
	    }
	    if (numCmovObbligatori == 0) {
		String message = MessageFormat
			.format("IDCOMUNE: {0}, TIPOMOVIMENTO: {1}, NESSUN CONTROMOVIMENTO OBBLIGATORIO. E' IMPOSSIBILE IMPOSTARE LA FINE DELLA SOSPENSIONE/INTERRUZIONE.",
				new Object[] { idComune, codiceTipoMov });
		handleErrorCondition(message);
	    }
	}
	ORMHelper.setIdcomune(idComuneSession);
	this.commitTransaction();
	return numUpdated;
    }
}
