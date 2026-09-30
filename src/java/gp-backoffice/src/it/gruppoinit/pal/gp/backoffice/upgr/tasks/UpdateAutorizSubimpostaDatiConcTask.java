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
 *  		&lt;java-task id="UPGR_UPDATE_AUTORIZ_SUBIMPOSTA_DATI_CONC" spring-bean-id="UpdateAutorizSubimpostaDatiConcTask"
 * 				java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateAutorizSubimpostaDatiConcTask"
 * 				fail-on-error="false" autocommit="true">
 * 				
 * 		&lt;/java-task>
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("UpdateAutorizSubimpostaDatiConcTask")
public class UpdateAutorizSubimpostaDatiConcTask extends BaseJavaTask {

    @Override
    public int run(Session session) throws SetupRunException {

	Set<String> listaAlias = new HashSet<String>();
	activityLogInfo("UpdateAutorizSubimpostaDatiConcTask.run: inizio aggiornamento");
	String origIdcomune = ORMHelper.getIdcomune();
	int rowCount = 0;
	String sql = "select "
		+ " autorizzazioni_concessioni.fk_codicemercato,"
		+ " autorizzazioni_concessioni.fk_idmercatiuso,"
		+ " autorizzazioni_concessioni.fk_idposteggio,"
		+ " autorizzazioni_concessioni.fk_tipoconcessione,"
		+ " autorizzazioni_concessioni.stagionaleda,"
		+ " autorizzazioni_concessioni.stagionalea,"
		+ " autorizzazioni_subentri.idcomune,"
		+ " autorizzazioni_subentri.id "
		+ "from"
		+ " autorizzazioni"
		+ "   inner join autorizzazioni_concessioni on autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune and autorizzazioni_concessioni.fk_idaut_attuale = autorizzazioni.id "
		+ "   inner join autorizzazioni_subentri on autorizzazioni.idcomune = autorizzazioni_subentri.idcomune and autorizzazioni.id = autorizzazioni_subentri.fk_idaut_attuale "
		+ "   left join autorizzazioni_subentri_conc on autorizzazioni_subentri.idcomune = autorizzazioni_subentri_conc.idcomune and autorizzazioni_subentri.id = autorizzazioni_subentri_conc.fk_autsub_id "
		+ "where autorizzazioni_subentri_conc.id is null";
	SQLQuery query = session.createSQLQuery(sql);
	//	
	List list = query.list();
	String sqlUpdate = "update autorizzazioni_subentri set fk_mercato = ?, fk_uso = ?, fk_mercatod = ?, fk_concessionitipi = ?, "
		+ "stagionale_da = ?, stagionale_a = ? where idcomune = ? and id = ?";
	query.addScalar("fk_codicemercato", Hibernate.BIG_DECIMAL);
	query.addScalar("fk_idmercatiuso", Hibernate.BIG_DECIMAL);
	query.addScalar("fk_idposteggio", Hibernate.BIG_DECIMAL);
	query.addScalar("fk_tipoconcessione", Hibernate.STRING);
	query.addScalar("stagionaleda", Hibernate.STRING);
	query.addScalar("stagionalea", Hibernate.STRING);
	query.addScalar("idcomune", Hibernate.STRING);
	query.addScalar("id", Hibernate.BIG_DECIMAL);
	SQLQuery queryUpdate = null;
	//UPDATE
	queryUpdate = session.createSQLQuery(sqlUpdate);
	//	    set fk_mercato = ?, fk_uso = ?, fk_mercatod = ?, fk_concessionitipi = ?, "
	//			+ "stagionale_da = ?, stagionale_a = ? where idcomune = ? and id = ?"
	queryUpdate.addScalar("fk_mercato", Hibernate.BIG_DECIMAL);
	queryUpdate.addScalar("fk_uso", Hibernate.BIG_DECIMAL);
	queryUpdate.addScalar("fk_mercatod", Hibernate.BIG_DECIMAL);
	queryUpdate.addScalar("fk_concessionitipi", Hibernate.STRING);
	queryUpdate.addScalar("stagionale_da", Hibernate.STRING);
	queryUpdate.addScalar("stagionale_a", Hibernate.STRING);
	queryUpdate.addScalar("idcomune", Hibernate.STRING);
	queryUpdate.addScalar("id", Hibernate.BIG_DECIMAL);
	///INSERT
	for (Object values : list) {
	    Object[] vals = (Object[]) values;
	    //	    
	    //	    	" autorizzazioni_concessioni.fk_codicemercato,"+
	    //		" autorizzazioni_concessioni.fk_idmercatiuso,"+
	    //		" autorizzazioni_concessioni.fk_idposteggio,"+
	    //		" autorizzazioni_concessioni.fk_tipoconcessione,"+
	    //		" autorizzazioni_concessioni.stagionaleda,"+
	    //		" autorizzazioni_concessioni.stagionalea,"+
	    //		" autorizzazioni_subentri.idcomune,"+
	    //		" autorizzazioni_subentri.id "+
	    BigDecimal fk_mercato = (BigDecimal) vals[0];
	    BigDecimal fk_uso = (BigDecimal) vals[1];
	    BigDecimal fk_mercatod = (BigDecimal) vals[2];
	    String fk_concessionitipi = (String) vals[3];
	    String stagionale_da = (String) vals[4];
	    String stagionale_a = (String) vals[5];
	    String idcomune = (String) vals[6];
	    BigDecimal id = (BigDecimal) vals[7];
	    // 
	    listaAlias.add(idcomune);
	    // aggiorna aut_sub
	    //	    set fk_mercato = ?, fk_uso = ?, fk_mercatod = ?, fk_concessionitipi = ?, "
	    //			+ "stagionale_da = ?, stagionale_a = ? where idcomune = ? and id = ?"
	    queryUpdate.setInteger(0, fk_mercato.intValue());
	    queryUpdate.setInteger(1, fk_uso.intValue());
	    queryUpdate.setInteger(2, fk_mercatod.intValue());
	    queryUpdate.setString(3, fk_concessionitipi);
	    queryUpdate.setString(4, stagionale_da);
	    queryUpdate.setString(5, stagionale_a);
	    queryUpdate.setString(6, idcomune);
	    queryUpdate.setInteger(7, id.intValue());
	    queryUpdate.executeUpdate();
	    // end aggiorna aut_sub
	    // insert
	    session.flush();
	    this.commitTransaction();
	    session.flush();
	}
	ORMHelper.setIdcomune(origIdcomune);
	activityLogInfo("UpdateAutorizSubimpostaDatiConcTask.run: Fine aggiornamento");
	return rowCount;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}