package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

/**
 * 
 * <pre>
 *  		&lt;java-task id="UPGR_ELABORAZIONE_SUBENTRI_CONC" spring-bean-id="UpdateAutorizzazioniSubentriConcTask"
 * 				java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateAutorizzazioniSubentriConcTask"
 * 				fail-on-error="false" autocommit="true">
 * 				
 * 		&lt;/java-task>
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("UpdateAutorizzazioniSubentriConcTask")
public class UpdateAutorizzazioniSubentriConcTask extends BaseJavaTask {

    @Override
    public int run(Session session) throws SetupRunException {

	Set<String> listaAlias = new HashSet<String>();
	activityLogInfo("UpdateAutorizzazioniSubentriConcTask.run: inizio aggiornamento");
	String origIdcomune = ORMHelper.getIdcomune();
	int rowCount = 0;
	String sql = "select id,idcomune,fk_mercato,fk_uso,fk_mercatod,fk_concessionitipi,stagionale_da,stagionale_a,fk_idaut_attuale,fk_idautsub_autcoll"
		+ " from autorizzazioni_subentri where fk_mercato is not null order by idcomune,fk_mercato,id";
	SQLQuery query = session.createSQLQuery(sql);
	//	
	List list = query.list();
	String sqlUpdate = "update Autorizzazioni_Subentri a set a.fk_mercato = null,fk_uso=null,fk_mercatod=null,fk_concessionitipi=null,"
		+ "stagionale_da=null,stagionale_a=null where a.idcomune=? and a.id=?";
	String sqlInsert = "INSERT INTO autorizzazioni_subentri_conc (idcomune,id,fk_autsub_id,fk_codicemercato,fk_idmercatiuso,fk_idposteggio,fk_tipoconcessione,stagionaleda,stagionalea,fk_idaut_attuale,fk_idautsub_autcoll) "
		+ "VALUES (:idcomune,:id,:fk_autsub_id,:fk_codicemercato,:fk_idmercatiuso,:fk_idposteggio,:fk_tipoconcessione,:stagionaleda,:stagionalea,:fk_idaut_attuale,:fk_idautsub_autcoll )";
	query.addScalar("id", Hibernate.BIG_DECIMAL);
	query.addScalar("idcomune", Hibernate.STRING);
	query.addScalar("fk_mercato", Hibernate.BIG_DECIMAL);
	query.addScalar("fk_uso", Hibernate.BIG_DECIMAL);
	query.addScalar("fk_mercatod", Hibernate.BIG_DECIMAL);
	query.addScalar("fk_concessionitipi", Hibernate.STRING);
	query.addScalar("stagionale_da", Hibernate.STRING);
	query.addScalar("stagionale_a", Hibernate.STRING);
	query.addScalar("fk_idaut_attuale", Hibernate.BIG_DECIMAL);
	query.addScalar("fk_idautsub_autcoll", Hibernate.BIG_DECIMAL);
	SQLQuery queryUpdate = null;
	SQLQuery queryInsert = null;
	//UPDATE
	queryUpdate = session.createSQLQuery(sqlUpdate);
	queryUpdate.addScalar("idcomune", Hibernate.STRING);
	queryUpdate.addScalar("id", Hibernate.BIG_DECIMAL);
	///INSERT
	queryInsert = session.createSQLQuery(sqlInsert);
	queryInsert.addScalar("id", Hibernate.BIG_DECIMAL);
	queryInsert.addScalar("idcomune", Hibernate.STRING);
	queryInsert.addScalar("fk_autsub_id", Hibernate.BIG_DECIMAL);
	queryInsert.addScalar("fk_mercato", Hibernate.BIG_DECIMAL);
	queryInsert.addScalar("fk_uso", Hibernate.BIG_DECIMAL);
	queryInsert.addScalar("fk_mercatod", Hibernate.BIG_DECIMAL);
	queryInsert.addScalar("fk_concessionitipi", Hibernate.STRING);
	queryInsert.addScalar("stagionale_da", Hibernate.STRING);
	queryInsert.addScalar("stagionale_a", Hibernate.STRING);
	queryInsert.addScalar("fk_idaut_attuale", Hibernate.BIG_DECIMAL);
	queryInsert.addScalar("fk_idautsub_autcoll", Hibernate.BIG_DECIMAL);
	String sqlSeq = "select max(id)+1 as val from autorizzazioni_subentri_conc where idcomune=?";
	SQLQuery queryid = session.createSQLQuery(sqlSeq);
	queryid.addScalar("val", Hibernate.BIG_DECIMAL);
	for (Object values : list) {
	    Object[] vals = (Object[]) values;
	    BigDecimal idAutSub = (BigDecimal) vals[0];
	    String idcomune = (String) vals[1];
	    BigDecimal fk_mercato = (BigDecimal) vals[2];
	    BigDecimal fk_uso = (BigDecimal) vals[3];
	    BigDecimal fk_mercatod = (BigDecimal) vals[4];
	    String fk_concessionitipi = (String) vals[5];
	    String stagionale_da = (String) vals[6];
	    String stagionale_a = (String) vals[7];
	    BigDecimal fk_idaut_attuale = (BigDecimal) vals[8];
	    BigDecimal fk_idautsub_autcoll = (BigDecimal) vals[9];
	    // 
	    listaAlias.add(idcomune);
	    // aggiorna aut_sub
	    queryUpdate.setString(0, idcomune);
	    queryUpdate.setInteger(1, idAutSub.intValue());
	    queryUpdate.executeUpdate();
	    // end aggiorna aut_sub
	    // insert
	    queryid.setString(0, idcomune);
	    int id = 1;
	    List<BigDecimal> max = queryid.list();
	    for (BigDecimal bigDecimal : max) {
		if (bigDecimal != null) {
		    id = bigDecimal.intValue();
		} else {
		    id = 1;
		}
	    }
	    //:idcomune,:id,:fk_autsub_id,:fk_codicemercato,:fk_idmercatiuso,:fk_idposteggio,
	    //:fk_tipoconcessione,:stagionaleda,:stagionalea,:fk_idaut_attuale,:fk_idaut_collegata
	    queryInsert.setString("idcomune", idcomune);
	    queryInsert.setBigDecimal("id", BigDecimal.valueOf(id));
	    queryInsert.setBigDecimal("fk_autsub_id", idAutSub);
	    queryInsert.setBigDecimal("fk_codicemercato", fk_mercato);
	    queryInsert.setBigDecimal("fk_idmercatiuso", fk_uso);
	    queryInsert.setBigDecimal("fk_idposteggio", fk_mercatod);
	    queryInsert.setString("fk_tipoconcessione", fk_concessionitipi);
	    queryInsert.setString("stagionaleda", stagionale_da);
	    queryInsert.setString("stagionalea", stagionale_a);
	    queryInsert.setBigDecimal("fk_idaut_attuale", fk_idaut_attuale);
	    queryInsert.setBigDecimal("fk_idautsub_autcoll", fk_idautsub_autcoll);
	    queryInsert.executeUpdate();
	    session.flush();
	    this.commitTransaction();
	    session.flush();
	}
	for (String alias : listaAlias) {
	    handleErrorCondition("ATTENZIONE!!! Aggiornamento SUBENTRI_CONCESSIONI PER ALIAS " + alias
		    + " avvisare Formazione e sistemisti dell'applicativo");
	}
	ORMHelper.setIdcomune(origIdcomune);
	activityLogInfo("UpdateAutorizzazioniSubentriConcTask.run: Fine aggiornamento");
	return rowCount;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
