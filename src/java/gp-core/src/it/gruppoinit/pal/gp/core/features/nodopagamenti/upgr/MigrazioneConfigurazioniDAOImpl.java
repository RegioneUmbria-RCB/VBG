package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione.UpgrCausaliParametriBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione.UpgrCausaliToNodoBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione.UpgrDatiConto;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione.UpgrDatiVecchiaCausaleBean;

@Repository
public class MigrazioneConfigurazioniDAOImpl extends BaseDAOImpl<Conti, PkId> implements IMigrazioneConfigurazioniDAO {

    private static final String COLONNA_DB_CODICEVERSAMENTO = "codiceversamento";
    private static final String COLONNA_DB_DESCRIZIONE = "descrizione";
    private static final String COLONNA_DB_IDCOMUNE = "idcomune";
    private static final String SILFI_PAY_CONNECTOR = "it.gruppoinit.pal.gp.pay.connector.silfi.SilfiPayConnector";
    private static final String PAGO_UMBRIA_PAY_CONNECTOR = "it.gruppoinit.pal.gp.pay.connector.pagoumbria.PagoUmbriaPayConnector";
    private static final String ENTRA_NEXT_CONNECTOR = "it.gruppoinit.pal.gp.pay.connector.entranext.EntraNextConnector";
    private static final String UNICREDIT_EASY_PA_CONNECTOR = "it.gruppoinit.pal.gp.pay.connector.easypa.UnicreditEasyPAConnector";

    private enum CHIAVE_PARAMETRO_CAUSALE {
	CODICE_TASSONOMIA,
	ANNO_ACCERTAMENTO,
	DATI_RISCOSSIONE,
	NUMERO_ACCERTAMENTO,
	NUMERO_SOTTO_ACCERTAMENTO,
	//
	TIPO_RIFERIMENTO_CREDITORE,
	TIPO_DOCUMENTO_SDI,
	//
	DESCRIZIONE_CAUSALE_PSP,
	//
	PAGO_PA_SERVICE_ID_CAUSALE_IUV
    }

    @Override
    public Class<Conti> getEntityClass() {

	return Conti.class;
    }

    private static String getSQLRicercaConti() {

	return " SELECT  " + //
	       " verticalizzazioniparametri.valore AS cfcodiceprofilo " + //
	       " ,conti.idcomune AS idcomune " + //
	       " ,conti.id AS id " + //
	       " ,conti.codiceconto AS codiceconto " + //
	       " ,conti.codicesottoconto AS datiriscossione " + //
	       " ,conti.descrizione AS descrizione " + //
	       " ,conti.note AS note " + //
	       " ,conti.iva  AS iva  " + //
	       " ,conti.anno_accertamento AS annoaccertamento " + //
	       " ,conti.numero_accertamento AS numeroaccertamento " + //
	       " ,conti.datascadenza AS datascadenza " + //
	       " ,conti.numero_sotto_accertamento AS numerosottoaccertamento " + //
	       " ,tipicausalioneri.codicetassonomia AS codicetassonomia " + //
	       " ,tipicausalioneri.mappaturanodopag AS codiceversamento " + //
	       " ,tipicausalioneridettaglio.flag_attivo AS attivo " + //
	       "  FROM  " + //
	       " conti  " + //
	       " INNER JOIN verticalizzazioniparametri ON " + //
	       " verticalizzazioniparametri.idcomune=conti.idcomune AND " + //
	       " verticalizzazioniparametri.modulo='NODO_PAGAMENTI' AND " + //
	       " verticalizzazioniparametri.parametro='AR_COD_FISC_ENTE_CREDITORE' " + //
	       " LEFT JOIN tipicausalioneridettaglio ON  " + //
	       " tipicausalioneridettaglio.idcomune=conti.idcomune AND   " + //
	       " tipicausalioneridettaglio.fkconto=conti.id  " + //
	       " AND tipicausalioneridettaglio.flag_attivo=1 " + //
	       " LEFT JOIN tipicausalioneri ON  " + //
	       " tipicausalioneridettaglio.idcomune=tipicausalioneri.idcomune AND   " + //
	       " tipicausalioneridettaglio.fkcausale=tipicausalioneri.co_id " + //
	       " WHERE conti.mappaturanodopag IS NULL  " + //
	       " GROUP BY  " + //
	       " verticalizzazioniparametri.valore  " + //
	       " ,conti.idcomune" + //
	       " ,conti.software" + //
	       " ,conti.id " + //
	       " ,conti.codiceconto " + //
	       " ,conti.codicesottoconto " + //
	       " ,conti.descrizione " + //
	       " ,conti.note " + //
	       " ,conti.iva  " + //
	       " ,conti.anno_accertamento " + //
	       " ,conti.numero_accertamento " + //
	       " ,conti.datascadenza " + //
	       " ,conti.numero_sotto_accertamento " + //
	       " ,tipicausalioneri.codicetassonomia  " + //
	       " ,tipicausalioneri.mappaturanodopag " + //
	       " ,tipicausalioneridettaglio.flag_attivo  " + //
	       " ORDER BY conti.idcomune, conti.software";
    }

    @Override
    public List<String> eseguiMigrazione() {

	List<String> errori = new ArrayList<String>();
	Session session = getSession();
	// Recupero tutti i conti - con MAPPATURANODOPAG nulla così posso eseguire più volte l'upgr - 
	// considerando il fatto che i conti vengono usati solamente per l'integrazione con il nodo dei pagamenti
	Map<String, List<UpgrDatiConto>> mappaPerProfili = getMappaContiPerProfili(session);
	for (Entry<String, List<UpgrDatiConto>> profilo : mappaPerProfili.entrySet()) {
	    String cfCodiceProfilo = profilo.getKey();
	    List<UpgrDatiConto> conti = profilo.getValue();
	    for (UpgrDatiConto conto : conti) {
		String mappaturaClient = getMappaturaClient(conto);
		//   dai conti - idcomune prendo tutti i cf_codice_profilo configurati nelle verticalizzazioni
		// Salvo il valore della conti.mappaturanodopag = conti.id
		salvaMappaturaSuConto(session, conto, mappaturaClient);
		// Cerco pay_registrazioni_causali dove mappatura_client = MAPPATURANODOPAG SE LA TROVO DO ERRORE O WARNING
		if (!verificaCausaleGiaINseritaPermappatura(session, cfCodiceProfilo, mappaturaClient)) {
		    String idComuneNodoPagamenti = getIdComuneNodoPagamenti(session, cfCodiceProfilo);
		    if (StringUtils.isNotBlank(idComuneNodoPagamenti)) {
			nuovaCausale(session, conto, idComuneNodoPagamenti, mappaturaClient);
		    } else {
			errori.add("Non è stato possibile creare la causale per il conto " + conto + " e profilo " + cfCodiceProfilo +
				   ". Profilo non trovato");
		    }
		} else {
		    errori.add("Non è stato possibile creare la causale per il conto " + conto + " e profilo " + cfCodiceProfilo +
			       ". Causale già inserita");
		}
		flush();
	    }
	}
	migraPagoPaServiceGenerazioneIUV(session);
	return errori;
    }

    private void nuovaCausale(Session session, UpgrDatiConto conto, String idComuneNodoPagamenti, String mappaturaClient) {

	UpgrDatiVecchiaCausaleBean datiVecchiaCaus = recuperoDatiDaVecchiaCausale(session, conto, idComuneNodoPagamenti);
	// Inserisco nuova pay_registrazioni.causale coi parametri e MAPPATURA_CLIENT (CONTI.MAPPATURANODOPAG). 
	//      Copio i dati contabili dal conto.
	String descrizioneCausale = conto.getDescrizione();
	String codiceVersamento = null;
	String vecchiaColonnaParametriDellaCausale = null;
	List<ChiaveValoreBean<String, String>> parametriVecchiaCausale = null;
	String javaclass = null;
	if (datiVecchiaCaus != null) {
	    descrizioneCausale = datiVecchiaCaus.getDescrizione();
	    vecchiaColonnaParametriDellaCausale = datiVecchiaCaus.getParametri();
	    codiceVersamento = datiVecchiaCaus.getCodiceversamento();
	    parametriVecchiaCausale = datiVecchiaCaus.getRegCausaliParametri();
	    javaclass = datiVecchiaCaus.getJavaclass();
	}
	int nuovoId = nuovoIdPerComune(session, idComuneNodoPagamenti, "pay_registrazioni_causali", "id");
	String sql = "insert into pay_registrazioni_causali (idcomune, id, software, descrizione, ordine, codice_versamento, mappatura_client ) " + //
		     "values (?, ?, ?, ?, ?, ?, ?)";
	SQLQuery q = session.createSQLQuery(sql);
	q.setString(0, idComuneNodoPagamenti);
	q.setInteger(1, nuovoId);
	q.setString(2, "TT");
	q.setString(3, descrizioneCausale);
	q.setInteger(4, 0);
	q.setString(5, codiceVersamento);
	q.setString(6, mappaturaClient);
	q.executeUpdate();
	// Sistemo il codice versamento con i dati del legame 
	inserisciParametriSuNuovaCausale(session, idComuneNodoPagamenti, nuovoId, parametriVecchiaCausale, descrizioneCausale, conto);
	aggiornaColonnaParam(session, idComuneNodoPagamenti, nuovoId, vecchiaColonnaParametriDellaCausale, javaclass);
    }

    /**
     * La colonna PAY_REG_CAUSALI.PARAM CONTIENE DELLE CONFIGURAZIONI CHE DEVONO ESSERE SPOSTATE NEI PARAMETRI DELLA
     * CAUSALE
     * 
     * @param session
     * @param idComuneNodoPagamenti
     * @param idCausale
     * @param vecchiaColonnaParametriDellaCausale
     * @param javaclass
     */
    private void aggiornaColonnaParam(Session session, String idComuneNodoPagamenti, Integer idCausale, String vecchiaColonnaParametriDellaCausale,
	    String javaclass) {

	if (StringUtils.isNotBlank(javaclass) && StringUtils.isNotBlank(vecchiaColonnaParametriDellaCausale)) {
	    CHIAVE_PARAMETRO_CAUSALE nomeParametro = getNomeParametroForConnectorEColonnaPARAMETRI(javaclass);
	    if (nomeParametro != null) {
		// Verifica che già non esista il parametro
		// Se non esiste lo inserisco 
		aggiornaParametro(session, idComuneNodoPagamenti, idCausale, vecchiaColonnaParametriDellaCausale, nomeParametro);
	    }
	}
    }

    private void inserisciParametriSuNuovaCausale(Session session, String idComuneNodoPagamenti, Integer idCausale,
	    List<ChiaveValoreBean<String, String>> parametriVecchiaCausale, String descrizioneCausale, UpgrDatiConto b) {

	aggiornaParametro(session, idComuneNodoPagamenti, idCausale, b.getCodicetassonomia(), CHIAVE_PARAMETRO_CAUSALE.CODICE_TASSONOMIA);
	// e la descrizione 
	//      ( ==> parametri.DESCRIZIONE_CAUSALE_IN_PSP) e la colonna parametri.
	// Il parametro DESCRIZIONE_CAUSALE_IN_PSP deve diventare standard per tutti i connettori. 
	aggiornaParametro(session, idComuneNodoPagamenti, idCausale, descrizioneCausale, CHIAVE_PARAMETRO_CAUSALE.DESCRIZIONE_CAUSALE_PSP);
	if (checkPresentiDatiContabili(b)) {
	    //	Ho i dati pay_registrazioni_causali.idcomune e pay_registrazioni_causali.id e le informazioni contabili e posso inserire nella tabella pay_regcausali_parametri i parametri:
	    //		CODICE_TASSONOMIA
	    //		ANNO_ACCERTAMENTO
	    //		DATI_RISCOSSIONE
	    //		NUMERO_ACCERTAMENTO
	    //		NUMERO_SOTTO_ACCERTAMENTO
	    aggiornaParametroInt(session, idComuneNodoPagamenti, idCausale, b.getAnnoaccertamento(), CHIAVE_PARAMETRO_CAUSALE.ANNO_ACCERTAMENTO);
	    aggiornaParametro(session, idComuneNodoPagamenti, idCausale, b.getNumeroaccertamento(), CHIAVE_PARAMETRO_CAUSALE.NUMERO_ACCERTAMENTO);
	    aggiornaParametro(session, idComuneNodoPagamenti, idCausale, b.getNumerosottoaccertamento(),
		    CHIAVE_PARAMETRO_CAUSALE.NUMERO_SOTTO_ACCERTAMENTO);
	    aggiornaParametro(session, idComuneNodoPagamenti, idCausale, b.getDatiriscossione(), CHIAVE_PARAMETRO_CAUSALE.DATI_RISCOSSIONE);
	}
	// Migrare sulla nuova causale anche i parametri già esistenti dalla vecchia causale alla nuova.
	if (parametriVecchiaCausale != null) {
	    for (ChiaveValoreBean<String, String> cvb : parametriVecchiaCausale) {
		// aggiungo i vecchi parametri della causale se presenti
		aggiornaParametroString(session, idComuneNodoPagamenti, idCausale, cvb.getValore(), cvb.getChiave());
	    }
	}
    }

    @SuppressWarnings("unchecked")
    private UpgrDatiVecchiaCausaleBean recuperoDatiDaVecchiaCausale(Session session, UpgrDatiConto conto, String idComuneNodoPagamenti) {

	//  codiceversamento è la vecchia mappatura ripresa da tipicausalioneri.mappaturanodopag
	if (StringUtils.isBlank(conto.getCodiceversamento())) {
	    return null;
	}
	// vedo se presente per il comune una causale con la vecchia mappatura presa da tipicausalioneri.mappaturanodopag da questa cerco di recuperare la descrizione il versamento e i parametri
	UpgrDatiVecchiaCausaleBean ret = null;
	String sql = "SELECT  " + //
		     "pay_registrazioni_causali.id " + //
		     ",pay_registrazioni_causali.descrizione " + //
		     ",pay_registrazioni_causali.codice_versamento AS codiceversamento " + //
		     ", pay_registrazioni_causali.parametri " + //
		     ",pay_connector_config.pay_connector_java_class AS javaclass " + //
		     " FROM pay_registrazioni_causali INNER JOIN pay_profili_enti_creditori ON " + //
		     " pay_profili_enti_creditori.idcomune=pay_registrazioni_causali.idcomune " + //
		     " INNER JOIN pay_connector_config ON  " + //
		     " pay_profili_enti_creditori.codice_connettore=pay_connector_config.codice " + //
		     " WHERE pay_registrazioni_causali.idcomune=? AND pay_registrazioni_causali.mappatura_client=? ";
	SQLQuery q = session.createSQLQuery(sql);
	q.setString(0, idComuneNodoPagamenti);
	q.setString(1, conto.getCodiceversamento());
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar(COLONNA_DB_DESCRIZIONE, Hibernate.STRING);
	q.addScalar(COLONNA_DB_CODICEVERSAMENTO, Hibernate.STRING);
	q.addScalar("parametri", Hibernate.STRING);
	q.addScalar("javaclass", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(UpgrDatiVecchiaCausaleBean.class));
	List<UpgrDatiVecchiaCausaleBean> list = q.list();
	if (list.isEmpty()) {
	    return null;
	}
	ret = list.get(0);
	sql = "SELECT chiave,valore " + // 
	      " FROM pay_regcausali_parametri WHERE idcomune=? AND fk_payregcausale_id=?";
	q = session.createSQLQuery(sql);
	q.setString(0, idComuneNodoPagamenti);
	q.setInteger(1, ret.getId());
	q.addScalar("chiave", Hibernate.STRING);
	q.addScalar("valore", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(ChiaveValoreBean.class));
	List<ChiaveValoreBean<String, String>> params = q.list();
	if (!params.isEmpty()) {
	    ret.getRegCausaliParametri().addAll(params);
	}
	return ret;
    }

    @SuppressWarnings("unchecked")
    private String getIdComuneNodoPagamenti(Session session, String cfCodiceProfilo) {

	String sql = "SELECT pay_profili_enti_creditori.idcomune as idcomune FROM pay_profili_enti_creditori WHERE pay_profili_enti_creditori.cf_codice_profilo=?";
	SQLQuery q = session.createSQLQuery(sql);
	q.setString(0, cfCodiceProfilo);
	q.addScalar(COLONNA_DB_IDCOMUNE, Hibernate.STRING);
	List<String> list = q.list();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    private boolean verificaCausaleGiaINseritaPermappatura(Session session, String cfCodiceProfilo, String mappaturaClient) {

	String sql = "SELECT COUNT(*) as conta FROM pay_profili_enti_creditori INNER JOIN pay_registrazioni_causali ON " + //  
		     " pay_profili_enti_creditori.idcomune=pay_registrazioni_causali.idcomune  " + //
		     " WHERE pay_profili_enti_creditori.cf_codice_profilo=?  " + //
		     " AND mappatura_client=?";
	SQLQuery q = session.createSQLQuery(sql);
	q.setString(0, cfCodiceProfilo);
	q.setString(1, mappaturaClient);
	q.addScalar("conta", Hibernate.INTEGER);
	List<Integer> list = q.list();
	return list.get(0) > 0;
    }

    private void salvaMappaturaSuConto(Session session, UpgrDatiConto conto, String mappaturaClient) {

	// inserisco il parametro
	String sql = "update conti set mappaturanodopag=? where idcomune=? and id=?";
	SQLQuery q = session.createSQLQuery(sql);
	q.setString(0, mappaturaClient);
	q.setString(1, conto.getIdcomune());
	q.setInteger(2, conto.getId());
	q.executeUpdate();
    }

    private String getMappaturaClient(UpgrDatiConto conto) {

	return String.valueOf(conto.getId());
    }

    @SuppressWarnings("unchecked")
    private Map<String, List<UpgrDatiConto>> getMappaContiPerProfili(Session session) {

	Map<String, List<UpgrDatiConto>> m = new HashMap<String, List<UpgrDatiConto>>();
	SQLQuery q = session.createSQLQuery(getSQLRicercaConti());
	q.addScalar("cfcodiceprofilo", Hibernate.STRING);
	q.addScalar(COLONNA_DB_IDCOMUNE, Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("codiceconto", Hibernate.STRING);
	q.addScalar("datiriscossione", Hibernate.STRING);
	q.addScalar(COLONNA_DB_DESCRIZIONE, Hibernate.STRING);
	q.addScalar("note", Hibernate.STRING);
	q.addScalar("iva", Hibernate.INTEGER);
	q.addScalar("annoaccertamento", Hibernate.INTEGER);
	q.addScalar("numeroaccertamento", Hibernate.STRING);
	q.addScalar("datascadenza", Hibernate.DATE);
	q.addScalar("numerosottoaccertamento", Hibernate.STRING);
	q.addScalar("codicetassonomia", Hibernate.STRING);
	q.addScalar(COLONNA_DB_CODICEVERSAMENTO, Hibernate.STRING);
	q.addScalar("attivo", Hibernate.BOOLEAN);
	q.setResultTransformer(Transformers.aliasToBean(UpgrDatiConto.class));
	List<UpgrDatiConto> list = q.list();
	// Raggruppo per CF le causali che devo creare
	for (UpgrDatiConto ub : list) {
	    String cfcodiceprofile = ub.getCfcodiceprofilo();
	    List<UpgrDatiConto> conto = m.get(cfcodiceprofile);
	    if (conto == null) {
		conto = new ArrayList<UpgrDatiConto>();
	    }
	    conto.add(ub);
	    m.put(cfcodiceprofile, conto);
	}
	return m;
    }

    private void aggiornaParametroInt(Session session, String idcomuneNodo, Integer idCausaleNodoPagamenti, Integer annoaccertamento,
	    CHIAVE_PARAMETRO_CAUSALE parametro) {

	if (annoaccertamento != null) {
	    aggiornaParametro(session, idcomuneNodo, idCausaleNodoPagamenti, String.valueOf(annoaccertamento), parametro);
	}
    }

    private void aggiornaParametroString(Session session, String idcomuneNodo, Integer idCausaleNodoPagamenti, String valore, String nomeParametro) {

	if (StringUtils.isBlank(valore)) {
	    return;
	}
	// verifico che non esista parametro configurato per causale e idcomune
	if (!verificaEsistenzaParametro(session, idcomuneNodo, idCausaleNodoPagamenti, nomeParametro)) {
	    // se non esiste
	    // trovo max+1 per sequenza
	    int nuovoId = nuovoIdPerComune(session, idcomuneNodo, "pay_regcausali_parametri", "id");
	    // inserisco il parametro
	    String sql = "insert into pay_regcausali_parametri(idcomune,id,fk_payregcausale_id,chiave,valore) values (?,?,?,?,?)";
	    SQLQuery q = session.createSQLQuery(sql);
	    q.setString(0, idcomuneNodo);
	    q.setInteger(1, nuovoId);
	    q.setInteger(2, idCausaleNodoPagamenti);
	    q.setString(3, nomeParametro);
	    q.setString(4, valore);
	    q.executeUpdate();	    
	    flush();
	}
    }

    /**
     * Se valore vuoto esce. se già esiste per la causale esce se non esiste lo inserisce
     * 
     * @param session
     * @param idcomuneNodo
     * @param idCausaleNodoPagamenti
     * @param valore
     * @param parametro
     */
    private void aggiornaParametro(Session session, String idcomuneNodo, Integer idCausaleNodoPagamenti, String valore,
	    CHIAVE_PARAMETRO_CAUSALE parametro) {

	aggiornaParametroString(session, idcomuneNodo, idCausaleNodoPagamenti, valore, parametro.name());
    }

    @SuppressWarnings("unchecked")
    private int nuovoIdPerComune(Session session, String idcomuneNodoPagamenti, String nomeTabella, String nomeCampo) {

	String sql = "SELECT COALESCE(MAX(" + nomeCampo + "), 0)+1 AS nuovoid FROM " + nomeTabella + " WHERE idcomune=?";
	SQLQuery q = session.createSQLQuery(sql);
	q.setString(0, idcomuneNodoPagamenti);
	q.addScalar("nuovoid", Hibernate.INTEGER);
	List<Integer> list = q.list();
	return list.get(0);
    }

    @SuppressWarnings("unchecked")
    private boolean verificaEsistenzaParametro(Session session, String idcomuneNodo, Integer idCausaleNodoPagamenti, String parametro) {

	String sql = "select count(*) as conta from pay_regcausali_parametri where idcomune=? and fk_payregcausale_id=? and chiave=? ";
	SQLQuery q = session.createSQLQuery(sql);
	q.setString(0, idcomuneNodo);
	q.setInteger(1, idCausaleNodoPagamenti);
	q.setString(2, parametro);
	q.addScalar("conta", Hibernate.INTEGER);
	List<Integer> list = q.list();
	return list.get(0) > 0;
    }

    /**
     * il campo anno accertamento era obbligatorio e quindi veniva comunque salvato. se ho trovato solo quello non
     * aggiorno i parametri
     * 
     * @param b
     * @return
     */
    private boolean checkPresentiDatiContabili(UpgrDatiConto b) {

	return StringUtils.isNotBlank(b.getDatiriscossione()) || StringUtils.isNotBlank(b.getNumeroaccertamento())
		|| StringUtils.isNotBlank(b.getNumerosottoaccertamento());
    }

    private void migraPagoPaServiceGenerazioneIUV(Session session) {

	Set<String> connettori = new HashSet<String>();
	connettori.add(PAGO_UMBRIA_PAY_CONNECTOR);
	connettori.add(SILFI_PAY_CONNECTOR);
	List<UpgrCausaliParametriBean> list = getCausaliPerConnettore(session, connettori);
	for (UpgrCausaliParametriBean upgrBean : list) {
	    // Verifica che già non esista il parametro
	    // Se non esiste lo inserisco 
	    // UPGR id pay_registrazioni causali per calcolo IUV PAGO_PA_SERVICE_ID_CAUSALE_IUV( pagoumbria, silfi)
	    aggiornaParametro(session, upgrBean.getIdcomune(), upgrBean.getId(), String.valueOf(upgrBean.getId()),
		    CHIAVE_PARAMETRO_CAUSALE.PAGO_PA_SERVICE_ID_CAUSALE_IUV);
	}
    }

    private CHIAVE_PARAMETRO_CAUSALE getNomeParametroForConnectorEColonnaPARAMETRI(String javaclass) {

	if (javaclass.equalsIgnoreCase(UNICREDIT_EASY_PA_CONNECTOR)) {
	    return CHIAVE_PARAMETRO_CAUSALE.TIPO_RIFERIMENTO_CREDITORE;
	} else if (javaclass.equalsIgnoreCase(ENTRA_NEXT_CONNECTOR)) {
	    return CHIAVE_PARAMETRO_CAUSALE.TIPO_DOCUMENTO_SDI;
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    private List<UpgrCausaliParametriBean> getCausaliPerConnettore(Session session, Set<String> connectorJavaClass) {

	// Upgr parametri ==> DESCRIZIONE_CAUSALE_PSP (Nexi, plugnpay, jcity)
	String sql = "SELECT pay_registrazioni_causali.idcomune as idcomune" + //
		     ", pay_registrazioni_causali.id as id" + //
		     " , pay_registrazioni_causali.parametri as parametri" + //
		     ", pay_registrazioni_causali.descrizione as descrizione " + //
		     ",pay_connector_config.PAY_CONNECTOR_JAVA_CLASS as javaclass " + //
		     " FROM pay_registrazioni_causali  " + //
		     " INNER JOIN pay_profili_enti_creditori ON  " + //
		     " pay_registrazioni_causali.idcomune=pay_profili_enti_creditori.idcomune " + //
		     " INNER JOIN pay_connector_config ON  " + //
		     " pay_profili_enti_creditori.codice_connettore = pay_connector_config.codice " + //
		     " WHERE pay_connector_config.PAY_CONNECTOR_JAVA_CLASS IN ( LISTA_CONNETTORI )";
	String qm = StringUtils.repeat("?,", connectorJavaClass.size());
	qm = qm.substring(0, qm.length() - 1);
	sql = sql.replace("LISTA_CONNETTORI", qm);
	SQLQuery q = session.createSQLQuery(sql);
	int pos = 0;
	for (String conn : connectorJavaClass) {
	    q.setString(pos++, conn);
	}
	q.addScalar(COLONNA_DB_IDCOMUNE, Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("parametri", Hibernate.STRING);
	q.addScalar(COLONNA_DB_DESCRIZIONE, Hibernate.STRING);
	q.addScalar("javaclass", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(UpgrCausaliParametriBean.class));
	return q.list();
    }

    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    @Override
    public List<String> upgrCodiceVersamentoPerCausaliSingole() {

	List<String> errori = new ArrayList<String>();
	// UPGR PER CODICE_VERSAMENTO
	Session session = getSession();
	String sql = "SELECT idcomune FROM pay_registrazioni_causali WHERE pay_registrazioni_causali.codice_versamento IS NULL GROUP BY pay_registrazioni_causali.idcomune ";
	SQLQuery q = session.createSQLQuery(sql);
	q.addScalar(COLONNA_DB_IDCOMUNE, Hibernate.STRING);
	List<String> list = q.list();
	for (String idComuneNodo : list) {
	    sql = "SELECT idcomune, id,descrizione,codice_versamento as codiceversamento, mappatura_client as mappaturaclient FROM " + //
		  " pay_registrazioni_causali WHERE pay_registrazioni_causali.idcomune=? and pay_registrazioni_causali.codice_versamento IS not NULL";
	    q = session.createSQLQuery(sql);
	    q.setString(0, idComuneNodo);
	    q.addScalar(COLONNA_DB_IDCOMUNE, Hibernate.STRING);
	    q.addScalar("id", Hibernate.INTEGER);
	    q.addScalar(COLONNA_DB_DESCRIZIONE, Hibernate.STRING);
	    q.addScalar(COLONNA_DB_CODICEVERSAMENTO, Hibernate.STRING);
	    q.addScalar("mappaturaclient", Hibernate.STRING);
	    q.setResultTransformer(Transformers.aliasToBean(UpgrCausaliToNodoBean.class));
	    List<UpgrCausaliToNodoBean> datiCausali = q.list();
	    if (datiCausali.size() == 1) {
		UpgrCausaliToNodoBean ub = datiCausali.get(0);
		if (StringUtils.isNotBlank(ub.getCodiceversamento())) {
		    sql = "update pay_registrazioni_causali set codice_versamento=? where idcomune=? and codice_versamento is null";
		    q = session.createSQLQuery(sql);
		    q.setString(0, ub.getCodiceversamento());
		    q.setString(1, idComuneNodo);
		    q.executeUpdate();
		}
	    } else {
		errori.add("Non è stato possibile aggiornare il codice versamento per idcomune " + idComuneNodo);
	    }
	}
	return errori;
    }
}
