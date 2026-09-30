package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Component;

@Component("upgrUpdatePasswordUtentiTask")
/**
 * Task che si occupa di aggiornare le password sostituendo le password in chiaro con i corrispondenti valori MD5.
 * Operazioni svolte:
 * 1) 	aggiornamento delle password nella tabella RESPONSABILI
 * 2) 	aggiornamento dei software per ciascun responsabile (tabella RESPONSABILISOFTWARE)
 * 3) 	aggiornamento delle pssword nella tabella ANAGRAFE,
 * 	quest'ultima operazione viene eseguita utilizzando codice SQL a basso livello (java.sql anzichè hibernate) per motivi di prestazioni.
 * 4) 	aggiornamento delle password nella tabella AMMINISTRAZIONI. (anche questa diretamente in java.sql)
 */
public class UpdatePasswordUtentiTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdatePasswordUtentiTask.class);
    @Override
    public int run(Session session) throws SetupRunException {

	int pageSize = 400;
	int pageNumber = 0;
	int numUpdated = 0;
	activityLogInfo("UpdatePasswordUtentiTask: inizio aggiornamento");
	String hql = "From Responsabili r order by r.id.idcomune";
	Query query = session.createQuery(hql);
	List<Responsabili> responsabilis = query.list();
	activityLogInfo("Aggiorno le password ai responsabili");
	for (Responsabili responsabili : responsabilis) {
	    String password = responsabili.getPassword();
	    String idcomune = responsabili.getId().getIdcomune();
	    Integer codiceResponsabile = responsabili.getId().getCodice();
	    if (StringUtils.isNotBlank(password)) {
		if (password.length() < 32) {
		    password = Utilities.getHashText(password, "MD5", false);
		    hql = "update Responsabili r set r.password = ? where r.id.idcomune = ? and r.id.codice = ?";
		    query = session.createQuery(hql);
		    query.setString(0, password);
		    query.setString(1, idcomune);
		    query.setInteger(2, codiceResponsabile);
		    numUpdated += query.executeUpdate();
		    session.flush();
		    this.commitTransaction();
		    session.flush();
		    activityLogInfo("Aggiornata la password al responsabile [{}]-{}",
			    new Object[] { responsabili.getId(), responsabili.getResponsabile() });
		}
	    }
	    if (StringUtils.defaultIfEmpty(responsabili.getAmministratore(), "0").equals("1")) {
		activityLogInfo("Aggiorno i softwareattivi al responsabile [{}]-{}",
			new Object[] { responsabili.getId(), responsabili.getResponsabile() });
		hql = "delete from Responsabilisoftware r where r.id.idcomune=? and r.id.codiceresponsabile=?";
		query = session.createQuery(hql);
		query.setString(0, idcomune);
		query.setInteger(1, codiceResponsabile);
		query.executeUpdate();
		hql = "from Softwareattivi r where r.id.idcomune=?";
		query = session.createQuery(hql);
		query.setString(0, idcomune);
		List<Softwareattivi> list = query.list();
		for (Softwareattivi softwareattivi : list) {
		    String sql = "insert into Responsabilisoftware(idcomune,software,codiceresponsabile) values (?,?,?)";
		    query = session.createSQLQuery(sql);
		    query.setString(0, idcomune);
		    query.setString(1, softwareattivi.getId().getFkSoftware());
		    query.setInteger(2, codiceResponsabile);
		    numUpdated += query.executeUpdate();
		    session.flush();
		    this.commitTransaction();
		    session.flush();
		}
	    } else {
		String hqlrs = "From Responsabilisoftware rs where rs.id.idcomune=? and rs.id.codiceresponsabile=? and rs.id.software=?";
		Query queryrs = session.createQuery(hqlrs);
		queryrs.setString(0, idcomune);
		queryrs.setInteger(1, codiceResponsabile);
		queryrs.setString(2, WebConstants.SOFTWARE_TT);
		List<Responsabilisoftware> responsabilisoftwares = queryrs.list();
		if (responsabilisoftwares == null || responsabilisoftwares.isEmpty()) {
		    String sql = "insert into Responsabilisoftware(idcomune,software,codiceresponsabile) values (?,?,?)";
		    query = session.createSQLQuery(sql);
		    query.setString(0, idcomune);
		    query.setString(1, WebConstants.SOFTWARE_TT);
		    query.setInteger(2, codiceResponsabile);
		    numUpdated += query.executeUpdate();
		    session.flush();
		    this.commitTransaction();
		    session.flush();
		}
	    }
	}
	session.clear();
	activityLogInfo("Aggiorno le password alle anagrafiche");
	boolean directSQL = false;
	String stringLengthFunctionCall = null;
	Dialect dialect = Dialect.getDialect(getHibernateConfig().getProperties());
	Map<String, SQLFunction> funcMap = dialect.getFunctions();
	SQLFunction functionDef = funcMap.get("length");
	if (functionDef != null) {
	    directSQL = true;
	    ArrayList<String> funcArgs = new ArrayList<String>();
	    funcArgs.add("PASSWORD");
	    stringLengthFunctionCall = functionDef.render(funcArgs, (SessionFactoryImplementor) session.getSessionFactory());
	    try {
		//aggiornamento di ANAGRAFE
		MD5PasswordWork w = new MD5PasswordWork();
		w.setTableName("ANAGRAFE");
		w.setPasswordColumnName("PASSWORD");
		w.setLengthFunctionCall(stringLengthFunctionCall);
		w.setKeyColumnNames("IDCOMUNE, CODICEANAGRAFE");
		session.doWork(w);
		commitTransaction();
		activityLogInfo("Tabella ANAGRAFE completata, aggiornate {} password", new Object[] { w.coutUpdatedRows() });
		numUpdated += w.coutUpdatedRows();
		//aggiornamento di AMMINISTRAZIONI
		w = new MD5PasswordWork();
		w.setTableName("AMMINISTRAZIONI");
		w.setPasswordColumnName("PASSWORD");
		w.setLengthFunctionCall(stringLengthFunctionCall);
		w.setKeyColumnNames("IDCOMUNE, CODICEAMMINISTRAZIONE");
		session.doWork(w);
		commitTransaction();
		activityLogInfo("Tabella AMMINISTRAZIONI, completata aggiornate {} password", new Object[] { w.coutUpdatedRows() });
		numUpdated += w.coutUpdatedRows();
	    } catch (HibernateException he) {
		if (he.getCause() instanceof SQLFeatureNotSupportedException) {
		    directSQL = false;
		    errorLogWarn("Impossibile aggiornare le password direttamente dal ResultSet perchè il database in uso non supporta ResultSet aggiornabili. \r\nSarà utilizzato Hibernate con un notevole calo di prestazioni.");
		} else {
		    handleErrorCondition(he);
		}
	    } catch (SetupRunException sre) {
		directSQL = false;
		handleErrorCondition(sre);
	    }
	} else {
	    errorLogWarn(
		    "Impossibile aggiornare le password direttamente dal ResultSet perchè non è stato possibile individuare il nome della funzione SQL per il calcolo della lunghezza delle stringhe per il dialetto SQL {}. \r\nSarà utilizzato Hibernate con un notevole calo di prestazioni.",
		    new Object[] { dialect.getClass().getName() });
	}
	if (!directSQL) {
	    //hql = "select count(a.id.codice) from Anagrafe a WHERE length(a.password)>1 order by a.id.idcomune";
	    //Lion eliminato order by nella select count perchè da errore in SQLServer se i campi in ORDER BY non sono anche in GROUP BY
	    hql = "select count(a.id.codice) from Anagrafe a WHERE length(a.password)>1";
	    query = session.createQuery(hql);
	    List<Long> counts = query.list();
	    int count = counts.get(0).intValue();
	    if (count > 0) {
		if (count < pageSize) {
		    pageNumber = 1;
		} else {
		    pageNumber = count / pageSize;
		}
		for (int i = 0; i < pageNumber; i++) {
		    hql = "from Anagrafe a WHERE length(a.password)>1 order by a.id.idcomune";
		    query = session.createQuery(hql);
		    query.setFirstResult(i * pageSize);
		    query.setMaxResults(pageSize);
		    List<Anagrafe> anagrafes = query.list();
		    for (Anagrafe anagrafe : anagrafes) {
			String password = anagrafe.getPassword();
			if (StringUtils.isNotBlank(password)) {
			    if (password.length() < 32) {
				password = Utilities.getHashText(password, "MD5", false);
				hql = "update Anagrafe a set a.password=? where a.id.idcomune = ? and a.id.codice=?";
				query = session.createQuery(hql);
				query.setString(0, password);
				query.setString(1, anagrafe.getId().getIdcomune());
				query.setInteger(2, anagrafe.getId().getCodice());
				numUpdated += query.executeUpdate();
				session.flush();
				this.commitTransaction();
				session.flush();
				activityLogInfo("Aggiornata la password al richiedente [{}]", new Object[] { anagrafe.getId() });
			    }
			}
		    }
		    session.clear();
		}
	    }
	    activityLogInfo("Aggiorno le password alle amministrazioni");
	    hql = "From Amministrazioni a where length(a.password)>1 order by a.id.idcomune";
	    query = session.createQuery(hql);
	    List<Amministrazioni> amministrazionis = query.list();
	    for (Amministrazioni amministrazioni : amministrazionis) {
		String password = amministrazioni.getPassword();
		if (StringUtils.isNotBlank(password)) {
		    if (password.length() < 32) {
			password = Utilities.getHashText(password, "MD5", false);
			hql = "update Amministrazioni a set a.password=? where a.id.idcomune = ? and a.id.codice=?";
			query = session.createQuery(hql);
			query.setString(0, password);
			query.setString(1, amministrazioni.getId().getIdcomune());
			query.setInteger(2, amministrazioni.getId().getCodice());
			numUpdated += query.executeUpdate();
			activityLogInfo("Aggiornata la password all'amministrazione [{}]-{}",
				new Object[] { amministrazioni.getId(), amministrazioni.getAmministrazione() });
			session.flush();
			this.commitTransaction();
			session.flush();
		    }
		}
		session.clear();
	    }
	}
	session.flush();
	this.commitTransaction();
	session.flush();
	activityLogInfo("UpdatePasswordUtentiTask: fine aggiornamento. Aggiornati in tutto {} record.", new Object[] { numUpdated });
	return numUpdated;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
