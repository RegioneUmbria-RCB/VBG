package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("upgrUpdateAutorizzazioniTask")
public class UpdateAutorizzazioniTask extends BaseJavaTask {

    private static final Logger log = LoggerFactory.getLogger(UpdateAutorizzazioniTask.class);

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpdateAutorizzazioniTask.run: inizio aggiornamento");
	String origIdcomune = ORMHelper.getIdcomune();
	/*
	 * 
	 * L'upgr deve solamente aggiornare il campo datascadenza della tabella
	AUTORIZZAZIONI che rappresentano una concessione.
	Tiri fuori tutte le autorizzazioni_concessioni. Da questa recuperi
	l'autorizzazione campo FK_IDAUT_ATTUALE e se la data scadenza non è settata la
	setti ed aggiorni l'autorizzazione.
	 */
	int rowCount = 0;
	String sql = "Select FK_IDAUT_ATTUALE,DATASCADENZA,IDCOMUNE From Autorizzazioni_Concessioni where datascadenza is not null";
	Query query = session.createSQLQuery(sql);
	List list = query.list();
	String hql = "update Autorizzazioni a set a.datascadenza = ? where a.id.idcomune=? and a.id.codice=?";
	for (Object values : list) {
	    Object[] vals = (Object[]) values;
	    BigDecimal idAutorizzazione = (BigDecimal) vals[0];
	    Date dataScadenza = (Date) vals[1];
	    String idcomune = (String) vals[2];
	    query = session.createQuery(hql);
	    query.setDate(0, dataScadenza);
	    query.setString(1, idcomune);
	    query.setBigDecimal(2, idAutorizzazione);
	    query.executeUpdate();
	    session.flush();
	    this.commitTransaction();
	    session.flush();
	}
	ORMHelper.setIdcomune(origIdcomune);
	activityLogInfo("UpdateAutorizzazioniTask.run: Fine aggiornamento");
	return rowCount;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
