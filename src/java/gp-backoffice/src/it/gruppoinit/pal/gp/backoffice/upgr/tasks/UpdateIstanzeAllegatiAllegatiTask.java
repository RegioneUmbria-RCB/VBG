package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrUpdateIstanzeAllegatiAllegatiTask")
public class UpdateIstanzeAllegatiAllegatiTask extends BaseJavaTask {

    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;

    //private static final Logger log = LoggerFactory.getLogger(UpdateIstanzeAllegatiAllegatiTask.class);
    /**
     * in ISTANZEALLEGATI è stata inserita una FK (IDCOMUNE,FK_IDALLEGATO) verso ALLEGATI.
     * 
     * Si deve sviluppare una procedura di allineamento per i dati già esistenti secondo la logica:
     * 
     * <ul>
     * <li>Per ogni record di ISTANZEALLEGATI con il campo NUMEROALLEGATO diverso da NULL controlla se esistono in
     * ALLEGATI un record con (IDCOMUNE,CODICEINVENTARIO,NUMEROALLEGATO) uguale.
     * 
     * <ul>
     * <li>se NO passa al record successivo</li>
     * <li>se SI prende ID e IDCOMUNE del record e li setta su FK_IDALLEGATI,IDCOMUNE del corrispondete record su
     * ISTANZEALLEGATI</li>
     * <li>Setta a NULL il campo NUMEROALLEGATO sia sulla tabella ISTANZEALLEGATI che su ALLEGATI</li>
     * </ul>
     */
    @Override
    public int run(Session session) throws SetupRunException {

	String origIdcomune = ORMHelper.getIdcomune();
	activityLogInfo("Inizio aggiornamento UpdateIstanzeAllegatiAllegatiTask");
	String sql = "Select ia.idcomune,ia.codiceinventario,ia.numeroallegato,ia.id from Istanzeallegati ia order by ia.idcomune asc";
	Query query = session.createSQLQuery(sql);
	List values = query.list();
	String idcomune = "";
	BigDecimal codiceInventario = BigDecimal.ZERO;
	BigDecimal numeroAllegato = BigDecimal.ZERO;
	BigDecimal idIstanzeAllegati = BigDecimal.ZERO;
	sql = "select a.id from allegati a where a.idcomune=:idcomune and a.codiceinventario=:codiceinventario and a.numeroallegato=:numeroallegato";
	activityLogInfo("ciclo i record di istanzeallegati");
	for (Object object : values) {
	    Object[] result = (Object[]) object;
	    idcomune = (String) result[0];
	    codiceInventario = (BigDecimal) result[1];
	    numeroAllegato = (BigDecimal) result[2];
	    idIstanzeAllegati = (BigDecimal) result[3];
	    ORMHelper.setIdcomune(idcomune);
	    query = session.createSQLQuery(sql);
	    query.setString("idcomune", idcomune);
	    query.setBigDecimal("codiceinventario", codiceInventario);
	    query.setBigDecimal("numeroallegato", numeroAllegato);
	    activityLogInfo("cerco se esiste un allegato con questi dati [{},{},{}]", new Object[] { idcomune, codiceInventario, numeroAllegato });
	    List<BigDecimal> allegatis = query.list();
	    for (BigDecimal idAllegato : allegatis) {
		PkId id = new PkId(idcomune, idAllegato.intValue());
		Allegati allegato = allegatiService.findById(id);
		if (allegato != null) {
		    activityLogInfo("run: trovato l'allegato {}", new Object[] { allegato.getId() });
		    PkId idIstanzeall = new PkId(idcomune, idIstanzeAllegati.intValue());
		    Istanzeallegati istanzeallegati = istanzeallegatiService.findById(idIstanzeall);
		    istanzeallegati.setAllegati(allegato);
		    activityLogInfo("Associo l'allegato ad istanze allegati {}", new Object[] { istanzeallegati.getId() });
		    istanzeallegatiService.update(istanzeallegati);
		}
		break;
	    }
	}
	session.flush();
	this.commitTransaction();
	session.flush();
	ORMHelper.setIdcomune(origIdcomune);
	// update istanzeallegati numeroallegato a null
	// update allegati numeroallegato a null
	activityLogInfo("Fine aggiornamento UpdateIstanzeAllegatiAllegatiTask");
	return 0;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
