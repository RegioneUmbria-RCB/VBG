package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("upgrUpdateMappatureTask")
public class UpdateMappatureTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdateMappatureTask.class);

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpdateMappatureTask.run: inizio aggiornamento");
	int rowCount = 0;
	String sql = "Select MAPPATUREPEOPLER.IDCOMUNE AS idcomune, MAPPATUREPEOPLER.CODICETESTATA AS fkidscheda, "
		+ "MAPPATUREPEOPLER.CODICERIGA AS fkidcampo, MAPPATUREPEOPLER.NOMETAGPEOPLE AS nometagpeople, "
		+ "MAPPATUREPEOPLER.ACCODA as accoda, MAPPATUREPEOPLER.USADESCRIZIONE as usadescrizione, "
		+ "MAPPATUREPEOPLER.VALORERIFERIMENTO as valoreriferimento, MAPPATUREPEOPLER.VALOREFISSO as valorefisso "
		+ "From mappaturepeopler inner join mappaturepeoplet on mappaturepeopler.idcomune = mappaturepeoplet.idcomune and"
		+ " mappaturepeopler.fkidmappatura = mappaturepeoplet.idmappatura Where MAPPATUREPEOPLET.IDTIPOMAPPATURA = 6";
	Query query = session.createSQLQuery(sql);
	String idcomune = "";
	String fkidscheda = "";
	String fkidcampo = "";
	String nometagpeople = "";
	BigDecimal accoda = BigDecimal.ZERO;
	// usadescrizione
	BigDecimal usadescrizione = BigDecimal.ZERO;
	// valoreriferimento
	String valoreriferimento = "";
	// valorefisso
	String valorefisso = "";
	Integer codice = null;
	// Recupero id
	// Insert mappature
	List mappaturepeople = query.list();
	if (mappaturepeople.size() > 0) {
	    for (Object object : mappaturepeople) {
		Object[] row = (Object[]) object;
		idcomune = (String) row[0];
		if (row[1] != null) {
		    fkidscheda = (String) row[1];
		} else {
		    fkidscheda = null;
		}
		if (row[2] != null) {
		    fkidcampo = (String) row[2];
		} else {
		    fkidcampo = null;
		}
		if (row[3] != null) {
		    nometagpeople = (String) row[3];
		} else {
		    nometagpeople = null;
		}
		if (row[4] != null) {
		    accoda = (BigDecimal) row[4];
		} else {
		    accoda = BigDecimal.ZERO;
		}
		if (row[5] != null) {
		    usadescrizione = (BigDecimal) row[5];
		} else {
		    usadescrizione = BigDecimal.ZERO;
		}
		if (row[6] != null) {
		    valoreriferimento = (String) row[6];
		} else {
		    valoreriferimento = null;
		}
		if (row[7] != null) {
		    valorefisso = (String) row[7];
		} else {
		    valorefisso = null;
		}
		sql = "SELECT MAX(CURRVAL) VAL FROM SEQUENCETABLE WHERE SEQUENCENAME = ? and IDCOMUNE=?";
		Query queryid = session.createSQLQuery(sql);
		queryid.setString(0, "MAPPATURE.ID");
		queryid.setString(1, idcomune);
		List<BigDecimal> max = queryid.list();
		for (BigDecimal bigDecimal : max) {
		    if (bigDecimal != null) {
			codice = bigDecimal.intValue();
		    } else {
			codice = 1;
		    }
		}
		if (codice == null) {
		    codice = 1;
		}
		sql = "insert into MAPPATURE (IDCOMUNE,ID,FKIDSCHEDA,FKIDCAMPO,NOMETAGPEOPLE,TIPOREGOLA,VALORECONFRONTO,VALOREDECODIFICA)"
			+ " VALUES (:idcomune,:id,:fkidscheda,:fkidcampo,:nometagpeople,:tiporegola,:valoreconfronto,:valoredecodifica)";
		Query queryinsert = session.createSQLQuery(sql);
		queryinsert.setString("idcomune", idcomune);
		queryinsert.setInteger("id", codice);
		if (fkidscheda != null) {
		    queryinsert.setInteger("fkidscheda", Integer.parseInt(fkidscheda));
		} else {
		    queryinsert.setParameter("fkidscheda", Integer.valueOf(0));
		}
		if (fkidcampo != null) {
		    queryinsert.setInteger("fkidcampo", Integer.parseInt(fkidcampo));
		} else {
		    queryinsert.setParameter("fkidcampo", Integer.valueOf(0));
		}
		queryinsert.setString("nometagpeople", nometagpeople);
		if (accoda.equals(BigDecimal.valueOf(2))) {
		    queryinsert.setInteger("tiporegola", 2);
		} else {
		    if (usadescrizione.equals(BigDecimal.valueOf(1))) {
			queryinsert.setInteger("tiporegola", 1);
		    } else {
			queryinsert.setInteger("tiporegola", 0);
		    }
		    valoreriferimento = null;
		    valorefisso = null;
		}
		queryinsert.setString("valoreconfronto", valoreriferimento);
		queryinsert.setString("valoredecodifica", valorefisso);
		rowCount = queryinsert.executeUpdate();
		activityLogDebug("mappatura inserita MAPPATUREPEOPLER(IDCOMUNE, CODICETESTATA, CODICERIGA, NOMETAGPEOPLE) : (" + idcomune + "," + fkidscheda
			+ "," + fkidcampo + "," + nometagpeople + ")");
		String sbCurrVal = "UPDATE SEQUENCETABLE SET CURRVAL = ? WHERE SEQUENCENAME = ? and IDCOMUNE=?";
		SQLQuery qCurrVal = session.createSQLQuery(sbCurrVal);
		qCurrVal.setInteger(0, ++codice);
		qCurrVal.setString(1, "MAPPATURE.ID");
		qCurrVal.setString(2, idcomune);
		rowCount = qCurrVal.executeUpdate();
		if (rowCount == 0) {
		    sbCurrVal = "insert into SEQUENCETABLE (CURRVAL,SEQUENCENAME,IDCOMUNE) values (?,?,?)";
		    qCurrVal = session.createSQLQuery(sbCurrVal);
		    qCurrVal.setInteger(0, codice);
		    qCurrVal.setString(1, "MAPPATURE.ID");
		    qCurrVal.setString(2, idcomune);
		    rowCount = qCurrVal.executeUpdate();
		}
		activityLogDebug("sequenza aggiornata");
	    }
	}
	activityLogInfo("UpdateMappatureTask.run: Fine aggiornamento");
	return rowCount;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
