package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("upgrUpdate2_22ConfigurazioniProtocolloTask")
public class Update2_22ConfigurazioniProtocolloTask extends BaseJavaTask {

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("Update2_22ConfigurazioniProtocolloTask.run: Inizio l'aggiornamento");
	// sistemaVerticalizzazioni(session);
	sistemaAmministrazioni(session);
	sistemaAlberoproc(session);
	activityLogInfo("Update2_22ConfigurazioniProtocolloTask.run: Fine dell'aggiornamento");
	return 0;
    }

    private void sistemaAlberoproc(Session session) {

	activityLogInfo("Update2_22ConfigurazioniProtocolloTask.run: SISTEMA ALBEROPROC_PROTOCOLLO");
	String sqlCheck = "select count(*) as conta from ALBEROPROC_PROTOCOLLO";
	SQLQuery queryCheck = session.createSQLQuery(sqlCheck);
	queryCheck.addScalar("conta", Hibernate.INTEGER);
	int c = ((Integer) queryCheck.list().get(0)).intValue();
	if (c == 0) {
	    String sql = "select IDCOMUNE,SC_ID,SC_PROTCLASSIFICA,SC_PROTTIPODOCUMENTO,SC_PROTCODTESTO,SC_PROTAUTOMATICA"
		    + ",SC_FASCCLASSIFICA,SC_FASCCODTESTO,SC_FASCAUTOMATICA from ALBEROPROC where SC_PROTCLASSIFICA is not null "
		    + " or SC_PROTTIPODOCUMENTO is not null or SC_PROTCODTESTO is not null or SC_PROTAUTOMATICA  is not null "
		    + " ORDER BY IDCOMUNE,SOFTWARE,SC_ID";
	    SQLQuery query = session.createSQLQuery(sql);
	    query.addScalar("IDCOMUNE", Hibernate.STRING);
	    query.addScalar("SC_ID", Hibernate.INTEGER);
	    query.addScalar("SC_PROTCLASSIFICA", Hibernate.STRING);
	    query.addScalar("SC_PROTTIPODOCUMENTO", Hibernate.STRING);
	    query.addScalar("SC_PROTCODTESTO", Hibernate.STRING);
	    query.addScalar("SC_PROTAUTOMATICA", Hibernate.STRING);
	    query.addScalar("SC_FASCCLASSIFICA", Hibernate.STRING);
	    query.addScalar("SC_FASCCODTESTO", Hibernate.STRING);
	    query.addScalar("SC_FASCAUTOMATICA", Hibernate.STRING);
	    List list = query.list();
	    String idcomune = "";
	    Integer codiceAlberoproc = null;
	    String scProtClassifica = "";
	    String scProttipodocumento = "";
	    String scProtCodTesto = "";
	    String scProtAutomatica = "";
	    String scFascClassifica = "";
	    String scFascCodtesto = "";
	    String scFascAutomatica = "";
	    String sqlMax = "select max(id) AS PROSSIMO from ALBEROPROC_PROTOCOLLO where idcomune = :idcomune";
	    SQLQuery queryMax = session.createSQLQuery(sqlMax);
	    queryMax.addScalar("PROSSIMO", Hibernate.INTEGER);
	    int seqVal = 0;
	    String sqlInsert = "insert into ALBEROPROC_PROTOCOLLO (ID,IDCOMUNE,FKSCID,SC_PROTCLASSIFICA,SC_PROTTIPODOCUMENTO,SC_PROTCODTESTO,SC_PROTAUTOMATICA"
		    + ",SC_FASCCLASSIFICA,SC_FASCCODTESTO,SC_FASCAUTOMATICA) "
		    + "VALUES (:id,:idcomune,:fkscid,:sc_protclassifica,:sc_prottipodocumento,:sc_protcodtesto,:sc_protautomatica,:sc_fascclassifica,:sc_fasccodtesto,:sc_fascautomatica)";
	    SQLQuery queryinsert = session.createSQLQuery(sqlInsert);
	    queryinsert.addScalar("ID", Hibernate.INTEGER);
	    queryinsert.addScalar("IDCOMUNE", Hibernate.STRING);
	    queryinsert.addScalar("FKSCID", Hibernate.INTEGER);
	    queryinsert.addScalar("SC_PROTCLASSIFICA", Hibernate.STRING);
	    queryinsert.addScalar("SC_PROTTIPODOCUMENTO", Hibernate.STRING);
	    queryinsert.addScalar("SC_PROTCODTESTO", Hibernate.STRING);
	    queryinsert.addScalar("SC_PROTAUTOMATICA", Hibernate.STRING);
	    queryinsert.addScalar("SC_FASCCLASSIFICA", Hibernate.STRING);
	    queryinsert.addScalar("SC_FASCCODTESTO", Hibernate.STRING);
	    queryinsert.addScalar("SC_FASCAUTOMATICA", Hibernate.STRING);
	    if (list.size() > 0) {
		for (Object object : list) {
		    //IDCOMUNE,SC_ID,SC_PROTCLASSIFICA,SC_PROTTIPODOCUMENTO,SC_PROTCODTESTO,SC_PROTAUTOMATICA
		    // ,SC_FASCCLASSIFICA,SC_FASCCODTESTO,SC_FASCAUTOMATICA
		    Object[] result = (Object[]) object;
		    idcomune = (String) result[0];
		    codiceAlberoproc = (Integer) result[1];
		    scProtClassifica = (String) result[2];
		    scProttipodocumento = (String) result[3];
		    scProtCodTesto = (String) result[4];
		    scProtAutomatica = (String) result[5];
		    scFascClassifica = (String) result[6];
		    scFascCodtesto = (String) result[7];
		    scFascAutomatica = (String) result[8];
		    if (StringUtils.isNotBlank(scProtClassifica) || StringUtils.isNotBlank(scProttipodocumento)
			    || StringUtils.isNotBlank(scProtCodTesto) || StringUtils.isNotBlank(scProtAutomatica)
			    || StringUtils.isNotBlank(scFascClassifica) || StringUtils.isNotBlank(scFascCodtesto)
			    || StringUtils.isNotBlank(scFascAutomatica)) {
			queryMax.setString("idcomune", idcomune);
			List max = queryMax.list();
			if (max.size() > 0) {
			    for (Object ob : max) {
				if (ob != null) {
				    seqVal = (Integer) ob;
				}
			    }
			}
			if (codiceAlberoproc != null) {
			    // :id,:idcomune,:fkscid,:sc_protclassifica,:sc_prottipodocumento,:sc_protcodtesto,
			    // :sc_protautomatica,:sc_fascclassifica,:sc_fasccodtesto,:sc_fascautomatica
			    queryinsert.setInteger("id", ++seqVal);
			    queryinsert.setString("idcomune", idcomune);
			    queryinsert.setInteger("fkscid", codiceAlberoproc);
			    queryinsert.setString("sc_protclassifica", scProtClassifica);
			    queryinsert.setString("sc_prottipodocumento", scProttipodocumento);
			    queryinsert.setString("sc_protcodtesto", scProtCodTesto);
			    queryinsert.setString("sc_protautomatica", scProtAutomatica);
			    queryinsert.setString("sc_fascclassifica", scFascClassifica);
			    queryinsert.setString("sc_fasccodtesto", scFascCodtesto);
			    queryinsert.setString("sc_fascautomatica", scFascAutomatica);
			    int rowCount = queryinsert.executeUpdate();
			}
		    }
		}
	    }
	}
	activityLogInfo("Update2_22ConfigurazioniProtocolloTask.run: TERMINATO SISTEMA ALBEROPROC");
    }

    private void sistemaAmministrazioni(Session session) {

	activityLogInfo("Update2_22ConfigurazioniProtocolloTask.run: SISTEMA AMMINISTRAZIONI");
	String sqlCheck = "select count(*) as conta from AMMINISTR_PROTOCOLLO";
	SQLQuery queryCheck = session.createSQLQuery(sqlCheck);
	queryCheck.addScalar("conta", Hibernate.INTEGER);
	int c = ((Integer) queryCheck.list().get(0)).intValue();
	if (c == 0) {
	    String sql = "select IDCOMUNE,CODICEAMMINISTRAZIONE,PROT_UO,PROT_RUOLO from AMMINISTRAZIONI ORDER BY IDCOMUNE,CODICEAMMINISTRAZIONE";
	    SQLQuery query = session.createSQLQuery(sql);
	    query.addScalar("IDCOMUNE", Hibernate.STRING);
	    query.addScalar("CODICEAMMINISTRAZIONE", Hibernate.INTEGER);
	    query.addScalar("PROT_UO", Hibernate.STRING);
	    query.addScalar("PROT_RUOLO", Hibernate.STRING);
	    Integer codiceAmministrazione = null;
	    List list = query.list();
	    String idcomune = "";
	    String prot_uo = "";
	    String prot_ruolo = "";
	    String sqlMax = "select max(id) AS PROSSIMO from AMMINISTR_PROTOCOLLO where idcomune = :idcomune";
	    SQLQuery queryMax = session.createSQLQuery(sqlMax);
	    queryMax.addScalar("PROSSIMO", Hibernate.INTEGER);
	    int seqVal = 0;
	    String sqlInsert = "insert into AMMINISTR_PROTOCOLLO (ID,IDCOMUNE,CODICEAMMINISTRAZIONE,PROT_UO,PROT_RUOLO,SOFTWARE) "
		    + "VALUES (:id,:idcomune,:codiceamministrazione,:prot_uo,:prot_ruolo,:software)";
	    SQLQuery queryinsert = session.createSQLQuery(sqlInsert);
	    queryinsert.addScalar("ID", Hibernate.INTEGER);
	    queryinsert.addScalar("IDCOMUNE", Hibernate.STRING);
	    queryinsert.addScalar("CODICEAMMINISTRAZIONE", Hibernate.INTEGER);
	    queryinsert.addScalar("PROT_UO", Hibernate.STRING);
	    queryinsert.addScalar("PROT_RUOLO", Hibernate.STRING);
	    queryinsert.addScalar("SOFTWARE", Hibernate.STRING);
	    if (list.size() > 0) {
		for (Object object : list) {
		    Object[] result = (Object[]) object;
		    idcomune = (String) result[0];
		    codiceAmministrazione = (Integer) result[1];
		    prot_uo = (String) result[2];
		    prot_ruolo = (String) result[3];
		    if (StringUtils.isNotBlank(prot_uo) || StringUtils.isNotBlank(prot_ruolo)) {
			queryMax.setString("idcomune", idcomune);
			List max = queryMax.list();
			if (max.size() > 0) {
			    for (Object ob : max) {
				if (ob != null) {
				    seqVal = (Integer) ob;
				}
			    }
			}
			if (codiceAmministrazione != null) {
			    queryinsert.setInteger("id", ++seqVal);
			    queryinsert.setString("idcomune", idcomune);
			    queryinsert.setInteger("codiceamministrazione", codiceAmministrazione);
			    queryinsert.setString("prot_uo", prot_uo);
			    queryinsert.setString("prot_ruolo", prot_ruolo);
			    queryinsert.setString("software", WebConstants.SOFTWARE_TT);
			    int rowCount = queryinsert.executeUpdate();
			}
		    }
		}
	    }
	}
	activityLogInfo("Update2_22ConfigurazioniProtocolloTask.run: TERMINATO SISTEMA AMMINISTRAZIONI");
    }

    private void sistemaVerticalizzazioni(Session session) {

	//	activityLogInfo("Update2_22ConfigurazioniProtocolloTask.run: SISTEMA VERTICALIZZAZIONI");
	//	String sqlCheck = "select count(*) as conta from VERTICALIZZAZIONIPARAMETRI WHERE MODULO='PROTOCOLLO_ATTIVO' AND PARAMETRO IN ('CODTESTOISTANZE','CODTESTOMOVIMENTI')";
	//	SQLQuery queryCheck = session.createSQLQuery(sqlCheck);
	//	queryCheck.addScalar("conta", Hibernate.INTEGER);
	//	int c = ((Integer) queryCheck.list().get(0)).intValue();
	//	if (c == 0) {
	//	    String sql = "select IDCOMUNE,SOFTWARE,CODTESTOISTANZE,CODTESTOMOVIMENTI from PROTOCOLLO_CONFIGURAZIONE ORDER BY IDCOMUNE,SOFTWARE";
	//	    SQLQuery query = session.createSQLQuery(sql);
	//	    query.addScalar("IDCOMUNE", Hibernate.STRING);
	//	    query.addScalar("SOFTWARE", Hibernate.STRING);
	//	    query.addScalar("CODTESTOISTANZE", Hibernate.INTEGER);
	//	    query.addScalar("CODTESTOMOVIMENTI", Hibernate.INTEGER);
	//	    Integer codiceMailIstanza = null;
	//	    Integer codiceMailMovimento = null;
	//	    List list = query.list();
	//	    String idcomune = "";
	//	    String software = "";
	//	    String sqlMax = "select max(id) AS PROSSIMO from verticalizzazioniparametri where idcomune = :idcomune";
	//	    SQLQuery queryMax = session.createSQLQuery(sqlMax);
	//	    queryMax.addScalar("PROSSIMO", Hibernate.INTEGER);
	//	    int seqVal = 0;
	//	    String sqlInsert = "insert into VERTICALIZZAZIONIPARAMETRI (ID,IDCOMUNE,MODULO,PARAMETRO,VALORE,SOFTWARE) VALUES (:id,:idcomune,:modulo,:parametro,:valore,:software)";
	//	    SQLQuery queryinsert = session.createSQLQuery(sqlInsert);
	//	    queryinsert.addScalar("ID", Hibernate.INTEGER);
	//	    queryinsert.addScalar("IDCOMUNE", Hibernate.STRING);
	//	    queryinsert.addScalar("MODULO", Hibernate.STRING);
	//	    queryinsert.addScalar("PARAMETRO", Hibernate.STRING);
	//	    queryinsert.addScalar("VALORE", Hibernate.STRING);
	//	    queryinsert.addScalar("SOFTWARE", Hibernate.STRING);
	//	    if (list.size() > 0) {
	//		for (Object object : list) {
	//		    Object[] result = (Object[]) object;
	//		    idcomune = (String) result[0];
	//		    software = (String) result[1];
	//		    codiceMailIstanza = (Integer) result[2];
	//		    codiceMailMovimento = (Integer) result[3];
	//		    queryMax.setString("idcomune", idcomune);
	//		    List max = queryMax.list();
	//		    if (max.size() > 0) {
	//			for (Object ob : max) {
	//			    if (ob != null) {
	//				seqVal = (Integer) ob;
	//			    }
	//			}
	//		    }
	//		    if (codiceMailIstanza != null) {
	//			queryinsert.setInteger("id", ++seqVal);
	//			queryinsert.setString("idcomune", idcomune);
	//			queryinsert.setString("modulo", "PROTOCOLLO_ATTIVO");
	//			queryinsert.setString("parametro", "CODTESTOISTANZE");
	//			queryinsert.setString("valore", String.valueOf(codiceMailIstanza));
	//			queryinsert.setString("software", software);
	//			activityLogDebug("Inserisco in VERTICALIZZAZIONIPARAMETRI: CODTESTOISTANZE", new Object[] {});
	//			int rowCount = queryinsert.executeUpdate();
	//		    }
	//		    if (codiceMailMovimento != null) {
	//			queryinsert.setInteger("id", ++seqVal);
	//			queryinsert.setString("idcomune", idcomune);
	//			queryinsert.setString("modulo", "PROTOCOLLO_ATTIVO");
	//			queryinsert.setString("parametro", "CODTESTOMOVIMENTI");
	//			queryinsert.setString("valore", String.valueOf(codiceMailMovimento));
	//			queryinsert.setString("software", software);
	//			activityLogDebug("Inserisco in VERTICALIZZAZIONIPARAMETRI: CODTESTOMOVIMENTI", new Object[] {});
	//			int rowCount = queryinsert.executeUpdate();
	//		    }
	//		}
	//	    }
	//	}
	//	activityLogInfo("Update2_22ConfigurazioniProtocolloTask.run: TERMINATO SISTEMA verticalizzazioni");
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}