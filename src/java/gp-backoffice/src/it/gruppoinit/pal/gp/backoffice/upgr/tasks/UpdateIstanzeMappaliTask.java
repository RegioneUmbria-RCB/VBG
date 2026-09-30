package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("upgrUpdateIstanzeMappaliTask")
public class UpdateIstanzeMappaliTask extends BaseJavaTask {

    private static final String QUERY_SELECT_MAPPALI_CON_FKIDISTANZESTRADARIO_NULL = "Select _Istanzemappali.id.codice from Istanzemappali _Istanzemappali where _Istanzemappali.id.idcomune= ? and _Istanzemappali.istanzestradarioId IS NULL";
    private static final String QUERY_SELECT_ISTANZESTRADARIO_BY_MAPPALE_SQL = "Select id from istanzestradario  "
	    + " where idcomune=:_idcomune and fkidmappale =:_fkidmappale";
    private static final String UPDATE_ISTANZAMAPPALE = "Update Istanzemappali _Istanzemappali set _Istanzemappali.istanzestradarioId=? where _Istanzemappali.id.idcomune= ? and _Istanzemappali.id.codice= ?";
    private static final String QUERYSELECT_MAPPALI_BY_ID = "Select _Istanza.id.codice from Istanzemappali _Istanzemappali left join _Istanzemappali.istanza _Istanza where _Istanzemappali.id.idcomune= ? and _Istanzemappali.id.codice = ?";
    private static final String QUERY_SELECT_ISTANZE_STRADARIO_BY_ISTANZA = "Select _Istanzestradario.id.codice from Istanzestradario _Istanzestradario left join _Istanzestradario.istanza _Istanza where _Istanzestradario.id.idcomune= ? and _Istanza.id.codice = ? ORDER BY _Istanzestradario.primario DESC";
    // private static final String QUERY_DELETE_MAPPALE_BY_ID = "DELETE FROM istanzemappali WHERE idcomune=:_idcomune and idmappale:_idmappale";
    private static final Integer MAX_RESULT = 100;

    /**
     * <pre>
     * 
     * 1.Cerco tutti i record in istanze mappali con fkidistanzestradario IS NULL paginati per 100 record alla volta
     *    (questo passaggio verrà ripetuto fino a quando la lista ricercata non sarà vuota).
     * 2. Ricerco, in istanzestradario un record con fkidmappale uguale al codiceMappale trovato e verifico che la query non ritorni un valore >0: 
     *  	
     *  	2.1 risultato >0 : aggiorno il campo fkidistanzestradario della tabella istanze mappali con l'id del record di istanze stradario trovato
     *          2.2 risultato =0 : Faccio una ricerca su istanzestradario con CODICEISTANZA uguale al codice istanza presente nell'istanza mappale;
     *                             la ricerca sarà limitata ad un solo valore e ordinata per primario desc (verrà recuperato solo il primario 
     *                             o il primo non primario ):
     *                              
     *                             2.2.1 : La query ritorna un record: aggiorno il campo fkidistanzestradario della tabella istanze mappali con l'id 
     *                               	   del record di istanze stradario trovato
     *                             2.2.2 : La query non ritorna un valore, scrivo sul log un messaggio che notifica l'impossibilità di bonificare il mappale
     * 
     * 
     * </pre>
     * 
     * <pre>
     * &lt;java-task id="UPGR_2_14_ISTANZEMAPPALI" 
     * 		spring-bean-id="upgrUpdateIstanzeMappaliTask"
     * 		java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateIstanzeMappaliTask" 
     * 		fail-on-error="false"
     * 		autocommit="true">
     * &lt;/java-task>
     * </pre>
     */
    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpdateIstanzeMappaliTask.run: Inizio aggiornamento mappali");
	// Creo l'ultima parte della query dinamicamente dove vado ad escludere i recodr che non potanno essere bonificati
	List<Integer> listRecordNonBonificabili = new ArrayList<Integer>();
	List<Object[]> listRecordFkIstanzestradarioNull = new ArrayList<Object[]>();
	// Recupero solo i codici mappali dei record dei mappali con FKISTANZESTRADARIO null esclusi quelli non bonificabili
	//(associati ad una istanza inesistente o non asscoiati a nessuna istanza)
	listRecordFkIstanzestradarioNull = selectMappaliWithFkIstanzestradarioNull(listRecordNonBonificabili, session);
	// Finche ci sono record con FKISTANZESTARDARIO NULL continuo la bonifica, possono esistere due tipi di dati con FKISTANZESTARDARIO NULL
	// 1. FKISTANZESTARDARIO NULL, ma con il valore di idmappale che ha il riferimento su un record di istanza stradario (vecchia logica)
	// 2. FKISTANZESTARDARIO NULL e nessun riferimento su un record di istanze stradario.
	int ciclo = 1;
	while (listRecordFkIstanzestradarioNull.size() > 0) {
	    // Ricerco se esisto un record di istanze stradario con con fkidmappale uguale all' id del mappale
	    // passato. Se lo trova aggiorna il mappale inserendo id dell'istanza stradario trovato.
	    List<Integer> listMappaliNonBonificabiliTemp = updateIstanzeMappali(listRecordFkIstanzestradarioNull, session);
	    activityLogDebug("UpdateIstanzeMappaliTask.run: Aggiornati i mappali recuperati updateIstanzeMappali(...)", new Object[] { ciclo });
	    listRecordNonBonificabili = (List<Integer>) CollectionUtils.union(listRecordNonBonificabili, listMappaliNonBonificabiliTemp);
	    listRecordFkIstanzestradarioNull = selectMappaliWithFkIstanzestradarioNull(listRecordNonBonificabili, session);
	    ciclo++;
	}
	activityLogInfo("UpdateIstanzeMappaliTask.run: Inizio aggiornamento mappali");
	return 0;
    }

    private List<Object[]> selectMappaliWithFkIstanzestradarioNull(List<Integer> listRecordNonBonificabili, Session session) {

	List<Object[]> listRecordFkIstanzestradarioNull = new ArrayList<Object[]>();
	Integer position = 1;
	String QUERY_SELECT_MAPPALI_CON_FKIDISTANZESTRADARIO_NULL_AND_ESCLUDE_MAPPALI_VALUTATI = "";
	// Cerco tutte i record in istanze mappali con fkidistanzestradario IS NULL paginati per 100 record alla volta,
	// la query ritorna solo i codice dei mappali "idmappale" esclusi quelli che non possono essere bonificati
	if (!listRecordNonBonificabili.isEmpty()) {
	    activityLogDebug("UpdateIstanzeMappaliTask.run:selectMappaliWithFkIstanzestradarioNull(...) Creo query escludendo i mappali non bonificabili ");
	    // creo una stringa di secondo il formato "?,?,.....,?"
	    String qm = StringUtils.repeat("?,", listRecordNonBonificabili.size());
	    qm = qm.substring(0, qm.length() - 1);
	    QUERY_SELECT_MAPPALI_CON_FKIDISTANZESTRADARIO_NULL_AND_ESCLUDE_MAPPALI_VALUTATI = QUERY_SELECT_MAPPALI_CON_FKIDISTANZESTRADARIO_NULL
		    + " and _Istanzemappali.id.codice not in (" + qm + ")";
	    Query query2 = session.createQuery(QUERY_SELECT_MAPPALI_CON_FKIDISTANZESTRADARIO_NULL_AND_ESCLUDE_MAPPALI_VALUTATI);
	    query2.setString(0, ORMHelper.getIdcomune());
	    for (Integer codiciRecordNonBonificabili : listRecordNonBonificabili) {
		query2.setInteger(position, codiciRecordNonBonificabili);
		position++;
	    }
	    query2.setMaxResults(MAX_RESULT);
	    listRecordFkIstanzestradarioNull = query2.list();
	} else {
	    activityLogDebug("UpdateIstanzeMappaliTask.run:selectMappaliWithFkIstanzestradarioNull(...)");
	    //Cerco tutte i record in istanze mappali con fkidistanzestradario IS NULL paginati per 100 record alla volta,
	    // la query ritorna solo i codice dei mappali "idmappale" 
	    Query query = session.createQuery(QUERY_SELECT_MAPPALI_CON_FKIDISTANZESTRADARIO_NULL);
	    query.setString(0, ORMHelper.getIdcomune());
	    query.setMaxResults(MAX_RESULT);
	    listRecordFkIstanzestradarioNull = query.list();
	}
	return listRecordFkIstanzestradarioNull;
    }

    private List<Integer> updateIstanzeMappali(List<Object[]> list, Session session) {

	List<Integer> listMappaliNonBonificabili = new ArrayList<Integer>();
	for (Object valoriQuery : list) {
	    // Recupero il codice mappale dall'oggetto valoriQuery ritornato 
	    Integer codiceMappale = (Integer) valoriQuery;
	    //Ricerco,se esiste, un record di istanze stradario con fkidmappale uguale al codiceMappale, la query ritorna solo 
	    // il codice di istanze stradario
	    Query queryIstanzestradarioByMappale = session.createSQLQuery(QUERY_SELECT_ISTANZESTRADARIO_BY_MAPPALE_SQL);
	    // setto le condizione 
	    queryIstanzestradarioByMappale.setString("_idcomune", ORMHelper.getIdcomune());
	    queryIstanzestradarioByMappale.setInteger("_fkidmappale", codiceMappale);
	    activityLogDebug(
		    "UpdateIstanzeMappaliTask.run: QUERY_SELECT_ISTANZESTRADARIO_BY_MAPPALE_SQL con i parametri idcomune: {} and fkidmappale : {}",
		    new Object[] { ORMHelper.getIdcomune(), codiceMappale });
	    List<Object[]> listIstanzeStradarioByMappale = queryIstanzestradarioByMappale.list();
	    // Per ogni record (ci apsettiamo che la query restituisca o 0 o 1 valore) valutiamo il tipo di aggiornamento da fare:
	    // CASO 1: la lista è non vuoto, esiste un istanze stradario che ha popolato il campo fkidmappale (campo dismsso) con il mappale 
	    //passato
	    if (listIstanzeStradarioByMappale.size() > 0) {
		// Andremo ad aggiornare il recodor del mappale passoto secondo la logica, fkidistanzesrtradario = codicestradario trovato
		for (Object valoriQueryIstanzeStradario : listIstanzeStradarioByMappale) {
		    BigDecimal codiceIstanzeStradario = (BigDecimal) valoriQueryIstanzeStradario;
		    Query queryUpadateMappale = session.createQuery(UPDATE_ISTANZAMAPPALE);
		    queryUpadateMappale.setInteger(0, new Integer(codiceIstanzeStradario.intValue()));
		    queryUpadateMappale.setString(1, ORMHelper.getIdcomune());
		    queryUpadateMappale.setInteger(2, codiceMappale);
		    queryUpadateMappale.executeUpdate();
		    session.flush();
		}
	    } else {
		//CASO 2: lista è vuota, significa che non esiste nessun istanza stradario che contiene un riferimenti al mappale passato,
		// andremo a ricercare lo stradario tramite il codice istanza che entrambi contengono, se non è presente scriveremo un warning
		// per comunicare l'impossibilità di associare il mappale.
		// Cerco il codice istanza a cui è collegato il mappale passato
		Query querySelectMappaleById = session.createQuery(QUERYSELECT_MAPPALI_BY_ID);
		// setto le condizioni di ricerca
		querySelectMappaleById.setString(0, ORMHelper.getIdcomune());
		querySelectMappaleById.setInteger(1, codiceMappale);
		// Eseguo la query, ritorna solo il codice istanza
		activityLogDebug("UpdateIstanzeMappaliTask.run: QUERYSELECT_MAPPALI_BY_ID con i parametri idcomune: {} and idmappale : {}",
			new Object[] { ORMHelper.getIdcomune(), codiceMappale });
		List<Object[]> listMappaleById = querySelectMappaleById.list();
		for (Object valoriQueryIstanzeStradario : listMappaleById) {
		    Integer codiceIstanza = (Integer) valoriQueryIstanzeStradario;
		    // CASO 2.1 se codice istanza diverso da null cerco se esiste un almeno un record di istanze stradario con quel codice istanza
		    if (codiceIstanza != null) {
			// eseguo la query che ritorna solo il codice istanza stradario cercato per codice istanza, limiteremo la ricerca a un solo record.
			// e li ordineremo per primario desc. La query ritornerà se esiste il primario altrimenti il primo non primario.
			// Assoceremo il mappale a quello stradario
			Query querySelectIstanzeStradarioByIstanza = session.createQuery(QUERY_SELECT_ISTANZE_STRADARIO_BY_ISTANZA);
			querySelectIstanzeStradarioByIstanza.setString(0, ORMHelper.getIdcomune());
			querySelectIstanzeStradarioByIstanza.setInteger(1, codiceIstanza);
			querySelectIstanzeStradarioByIstanza.setMaxResults(1);
			// Eseguo la query
			activityLogDebug(
				"UpdateIstanzeMappaliTask.run: QUERY_SELECT_ISTANZE_STRADARIO_BY_ISTANZA con i parametri idcomune: {} and codiceistanza : {}",
				new Object[] { ORMHelper.getIdcomune(), codiceIstanza });
			List<Object[]> listQuerySelectIstanzeStradarioByIstanza = querySelectIstanzeStradarioByIstanza.list();
			if (listQuerySelectIstanzeStradarioByIstanza.size() > 0) {
			    for (Object valoriQueryIstanzeStradarioByIstanza : listQuerySelectIstanzeStradarioByIstanza) {
				// Aggiorno il valore di fkistradario del mappale passato con il codice di istanza stradario trovato.
				Integer codiceIstanzeStradario = (Integer) valoriQueryIstanzeStradarioByIstanza;
				Query queryUpadateMappale = session.createQuery(UPDATE_ISTANZAMAPPALE);
				queryUpadateMappale.setInteger(0, codiceIstanzeStradario);
				queryUpadateMappale.setString(1, ORMHelper.getIdcomune());
				queryUpadateMappale.setInteger(2, codiceMappale);
				queryUpadateMappale.executeUpdate();
				session.flush();
			    }
			} else {
			    handleErrorCondition("UpdateIstanzeMappaliTask.run: Attenzione non è stato possibile associare il mappale con codice:"
				    + codiceMappale + " non esiste l'istanza con codice codiceIstanza " + codiceIstanza);
			    listMappaliNonBonificabili.add(codiceMappale);
			}
		    } else {// CASO 2.2 : codiceistanza del mappale è null ,quindi rilancio subito il warning
			handleErrorCondition("UpdateIstanzeMappaliTask.run: Attenzione non è stato possibile associare il mappale con codice:"
				+ codiceMappale + " perché codiceistanza del mappale è null");
			listMappaliNonBonificabili.add(codiceMappale);
		    }
		}
	    }
	}
	return listMappaliNonBonificabili;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
