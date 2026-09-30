package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("upgrUpdateConfigurazioneTask")
public class UpdateConfigurazioneTask extends BaseJavaTask {

    // QUERY: Recupera tutti i record della tabella configurazione raggruppati per idcomune
    private final static String sqlSelectConfigurazioneGrupByIdComune = "select idcomune from configurazione group by idcomune";
    // QUERY: Recupera tutti i record della tabella configurazione che devono essere bonificati
    private final static String sqlSelectConfigurazioneByIdComune = "select codice_accreditamento,software from configurazione where idcomune= ?";
    // QUERY: Recupera la lista dei comuni associati (almeno avrò un comune se è un istallazione singolo comune)
    private final static String sqlSelectComuniAssociatiByIdcomune = "select codicecomune from comuniassociati where idcomune= ?";
    // QUERY: Recupera  comuni associati software by id 
    private final static String sqlSelectComuniAssociatiById = "select codicecomune from comuniassociati where idcomune= ? and id= ?";
    // QUERY : Recuperara l'oggetto di comuni associati software filtrando per (idcomune,codicecomune,software)
    private final static String sqlSelectComuniAssociatiSoftware = "select idcomune from comuniassociatisoftware where idcomune = ? and codicecomune = ? and software = ?";
    // QUERY : Aggiorna il campo CODICE_ACCREDITAMENTO della tabella COMUNIASSOCIATISOFTWARE filtando per i campi idcomune,codicecomune,software
    private final static String slqUpdateComuniassociatiSoftware = "UPDATE comuniassociatisoftware set CODICE_ACCREDITAMENTO = ? where idcomune = ? and codicecomune = ? and software = ?";
    // QUERY :Inserisce un record  in COMUNIASSOCIATISOFTWARE con i valori CODICE_ACCREDITAMENTO, idcomune,codicecomune,software (Gli altri sono posti a null)
    private final static String slqInsertComuniassociatiSoftware = "insert into comuniassociatisoftware (IDCOMUNE,ID,SI_STEMMA,SOFTWARE,CODICE_ACCREDITAMENTO,CODICE_AOO,CODICECOMUNE,SI_INTESTAZIONE1,SI_INTESTAZIONE2,SI_INTESTAZIONE3,SI_PDP1,SI_PDP2,MAIL,MAILPEC)"
	    + " VALUES (:idcomune,:id,:si_stemma,:software,:codice_accreditamento,:codice_aoo,:codicecomune,:si_intestazione1,:si_intestazione2,:si_intestazione3,:si_pdp1,:si_pdp2,:mail,:mailpec)";
    // QUERY : Recupera max CURRVAL dalla tabella SEQUENCETABLE, per idcomune e nome tabella
    private final static String sqlMaxid = "SELECT MAX(CURRVAL) VAL FROM SEQUENCETABLE WHERE SEQUENCENAME = ? and IDCOMUNE=?";
    // QUERY : Aggiorna max CURRVAL dalla tabella SEQUENCETABLE, per idcomune e nome tabella
    private final static String sbCurrVal = "UPDATE SEQUENCETABLE SET CURRVAL = ? WHERE SEQUENCENAME = ? and IDCOMUNE=?";
    // QUERY : Insert max CURRVAL dalla tabella SEQUENCETABLE, per idcomune e nome tabella
    private final static String insertCurrVal = "insert into SEQUENCETABLE (CURRVAL,SEQUENCENAME,IDCOMUNE) values (?,?,?)";

    public UpdateConfigurazioneTask() {

    }

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpdateConfigurazioneTask.run: inizio aggiornamento......");
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// Recupero la lista di tutti gli idcomune raggruppando gli oggetti della configurazione ////////////////////////////////////////////
	activityLogInfo("UpdateConfigurazioneTask.run:Recupero la lista di tutti gli idcomune raggruppando gli oggetti della configurazione ");
	Query selectConfigurazioneGrupByIdComune = session.createSQLQuery(sqlSelectConfigurazioneGrupByIdComune);
	List listIdcomuni = selectConfigurazioneGrupByIdComune.list();
	for (Object idcomuneObject : listIdcomuni) {
	    String idcomune = (String) idcomuneObject;
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ////////////////////////// Recupero la lista di tutti gli oggetti configurazione filtrando per idcomune////////////////////////////////////////////
	    activityLogInfo("UpdateConfigurazioneTask.run:Recupero gli oggetti configurazione ");
	    Query selectConfigurazioneByIdComune = session.createSQLQuery(sqlSelectConfigurazioneByIdComune);
	    selectConfigurazioneByIdComune.setString(0, idcomune);
	    List configurazione = selectConfigurazioneByIdComune.list();
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    for (Object object : configurazione) {
		Object[] result = (Object[]) object;
		String codice_accreditamento = (String) result[0];
		String software = (String) result[1];
		//String idcomune = (String) result[2];
		if (StringUtils.isNotBlank(codice_accreditamento)) {
		    activityLogInfo("UpdateConfigurazioneTask.run: Aggiorno per codice accreditamento {}", new Object[] { codice_accreditamento });
		    // Non è null allora inserisco/aggiorno la riga la con questo valore in COMUNIASSOCIATISOFTWARE
		    ///////////////////////////// Recupero i comuni associati filtrando per IDCOMUNE /////////////////////////////////////////////////////
		    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    activityLogInfo("UpdateConfigurazioneTask.run: Recupero tutti i comuni associati per idcomune: {}", new Object[] { idcomune });
		    Query selectComuniAssociatiByIdcomune = session.createSQLQuery(sqlSelectComuniAssociatiByIdcomune);
		    selectComuniAssociatiByIdcomune.setString(0, idcomune);
		    List comuniassociati = selectComuniAssociatiByIdcomune.list();
		    for (Object codiceComuniassociato : comuniassociati) {
			String codiceComune = (String) codiceComuniassociato;
			activityLogInfo(
				"UpdateConfigurazioneTask.run:Recupero la lista di oggetti Comuniassociatisoftware filtrando per : idcomune: {}, codicecomune: {},software: {}",
				new Object[] { idcomune, codiceComune, software });
			Query selectComuniAssociatiSoftware = session.createSQLQuery(sqlSelectComuniAssociatiSoftware);
			selectComuniAssociatiSoftware.setString(0, idcomune);
			selectComuniAssociatiSoftware.setString(1, codiceComune);
			selectComuniAssociatiSoftware.setString(2, software);
			List comuniassociatisoftware = selectComuniAssociatiSoftware.list();
			// Controllo se la lista è non vuota
			if (!comuniassociatisoftware.isEmpty()) {
			    activityLogInfo(
				    "UpdateConfigurazioneTask.run: Trovato il record comuni associati software filtrato per idcomue: {},codice comune: {},software: {} ",
				    new Object[] { idcomune, codiceComune, software });
			    activityLogInfo(
				    "UpdateConfigurazioneTask.run: Aggiorno il campo CODICE_ACCREDITAMENTO del record comuni associati  trovato con il valore {} ",
				    new Object[] { codice_accreditamento });
			    //Aggiorno il record trovato
			    SQLQuery updateComuniassociatiSoftwareQuery = session.createSQLQuery(slqUpdateComuniassociatiSoftware);
			    updateComuniassociatiSoftwareQuery.setString(0, codice_accreditamento);
			    updateComuniassociatiSoftwareQuery.setString(1, idcomune);
			    updateComuniassociatiSoftwareQuery.setString(2, codiceComune);
			    updateComuniassociatiSoftwareQuery.setString(3, software);
			    updateComuniassociatiSoftwareQuery.executeUpdate();
			} else {
			    activityLogInfo(
				    "UpdateConfigurazioneTask.run: Non trovato il record comuni associati software filtrato per idcomue: {},codice comune: {},software: {} ",
				    new Object[] { idcomune, codiceComune, software });
			    activityLogInfo(
				    "UpdateConfigurazioneTask.run:Inserisco nuovo record con le informazioni: idcomune:{},software: {},codice_accreditamento: {},codicecomune: {} ",
				    new Object[] { idcomune, software, codice_accreditamento, codiceComune });
			    //Inserisco un nuovo record 
			    // Calcolo il massimo preogressivo per l'id
			    activityLogDebug("UpdateConfigurazioneTask.run: Calcolo l'id progressivo per la tabella COMUNIASSOCIATISOFTWARE ");
			    Integer codice = null;
			    // Recupero il massimo progressivo per l'id comune 
			    Query queryid = session.createSQLQuery(sqlMaxid);
			    queryid.setString(0, "COMUNIASSOCIATISOFTWARE.ID");
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
			    int rowCount = 0;
			    activityLogDebug("UpdateConfigurazioneTask.run: id trovato {} ", new Object[] { codice });
			    try {
				// Controllo se già esiste un record con la chiave che andrò a creare , nel caso creo un nuovo id
				// e non faccio l'inserimento fino a quando non trovo un id libero
				codice = codice + 1;
				Query selectComuniAssociatiById = session.createSQLQuery(sqlSelectComuniAssociatiById);
				selectComuniAssociatiById.setString(0, idcomune);
				selectComuniAssociatiById.setInteger(1, codice);
				List comuniAssociatiTemp = selectComuniAssociatiById.list();
				while (!comuniAssociatiTemp.isEmpty()) {
				    codice++;
				    selectComuniAssociatiById.setString(0, idcomune);
				    selectComuniAssociatiById.setInteger(1, codice);
				    comuniAssociatiTemp = selectComuniAssociatiById.list();
				}
				Query queryinsert = session.createSQLQuery(slqInsertComuniassociatiSoftware);
				// Inserisco i campi da inserire
				queryinsert.setString("idcomune", idcomune);
				queryinsert.setInteger("id", codice + 1);
				queryinsert.setBigDecimal("si_stemma", null);
				queryinsert.setString("software", software);
				queryinsert.setString("codice_accreditamento", codice_accreditamento);
				queryinsert.setString("codice_aoo", null);
				queryinsert.setString("codicecomune", codiceComune);
				queryinsert.setString("si_intestazione1", null);
				queryinsert.setString("si_intestazione2", null);
				queryinsert.setString("si_intestazione3", null);
				queryinsert.setString("si_pdp1", null);
				queryinsert.setString("si_pdp2", null);
				queryinsert.setString("mail", null);
				queryinsert.setString("mailpec", null);
				rowCount = queryinsert.executeUpdate();
			    } catch (Exception exception) {
				activityLogDebug("UpdateConfigurazioneTask.run: Esiste già un record con la chiave primaria creata");
			    }
			    // Aggiorno la sequence table con il nuovo id usato
			    SQLQuery qCurrVal = session.createSQLQuery(sbCurrVal);
			    qCurrVal.setInteger(0, codice + 1);
			    qCurrVal.setString(1, "COMUNIASSOCIATISOFTWARE.ID");
			    qCurrVal.setString(2, idcomune);
			    rowCount = qCurrVal.executeUpdate();
			    if (rowCount == 0) {
				qCurrVal = session.createSQLQuery(insertCurrVal);
				qCurrVal.setInteger(0, codice++);
				qCurrVal.setString(1, "COMUNIASSOCIATISOFTWARE.ID");
				qCurrVal.setString(2, idcomune);
				rowCount = qCurrVal.executeUpdate();
			    }
			    activityLogDebug("sequenza aggiornata");
			}
		    }
		}
	    }
	}
	return 0;
    }
}
