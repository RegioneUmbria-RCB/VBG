package it.gruppoinit.pal.gp.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.StcWsClient;
import it.init.sigepro.rte.DirezioneSportelloResponse;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.ValoreParametroType;

public class NlaStcBaseServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(NlaStcBaseServiceImpl.class);
    public static final String RICHIESTA_PRATICA_ALTRO_DATO_TUTTE_LE_ATTIVITA_ESEGUITE_IN_VISURA = "#TUTTE_LE_ATTIVITA_ESEGUITE_IN_VISURA#";
    public static final String NOTIFICA_ATTIVITA_ALTRO_DATO_DYN2_MODELLIT = "DYN2_MODELLIT.ID";
    public static final String NOTIFICA_ATTIVITA_ALTRO_DATO_DYN2_MODELLIT_NOMESCHEDA = "DYN2_MODELLIT.NOMESCHEDA";
    public static final String NOTIFICA_ATTIVITA_ALTRO_DATO_DYN2_MODELLIT_ID_SCHEDA_NODO_ESTERNO = "DYN2_MODELLIT_ID_SCHEDA_NODO_ESTERNO";
    public static final String NON_INVIARE_PROCEDIMENTI = "$NON_INVIARE_PROCEDIMENTI$";
    public static final String OPERATORE_NOTIFICA = "$OPERATORE_NOTIFICA$";
    public static final String ALTRI_DATI_ESEGUI_MOV_FK_FO_SOGGETTI_ESTERNI = "$ESEGUI_MOV_FK_FO_SOGGETTI_ESTERNI$";
    public static final String ALTRI_DATI_ESEGUI_CONTROMOVIMENTO_DI = "$ESEGUI_CONTROMOVIMENTO_DI$";
    public static final String ALTRI_DATI_NOTIFICA_INTERA_PRATICA = "$notificaInteraPratica$";
    public static final String SPOSTA_ALLEGATI_IN_PRATICA = "$SPOSTA_ALLEGATI_IN_PRATICA$";
    @Autowired
    protected AmministrazioniService amministrazioniService;
    @Autowired
    protected StcWsClient stcWsClient;
    @Autowired
    protected VerticalizzazioniService verticalizzazioniService;
    @Autowired
    protected ContenttypesService contenttypesService;
    @Autowired
    private OggettiService oggettiService;
    //protected InvalidValue[] validationMessages;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private Istanzedyn2datiService istanzedyn2datiService;
    @Autowired
    private IstanzeService istanzeService;

    /**
     * Cerca tra le amministrazioni della base dati quella configurata per i parametri dello sportello indicato
     * 
     * @param sportello
     * @return il valore in stringa del codice dell'amministrazione sulla base dati
     * @throws RuntimeException
     *             nel caso che l'amministrazione non venga recuperata
     */
    protected String decodeAmministrazioneFromSportelloType(SportelloType sportello, Integer codiceAmministrazioneMittente) {

	String idEnte = StringUtils.defaultString(sportello.getIdEnte());
	String idSportello = StringUtils.defaultString(sportello.getIdSportello());
	String idNodo = StringUtils.defaultString(sportello.getIdNodo());
	Amministrazioni amministrazione = amministrazioniService.findAmministrazioneSTC(idNodo, idEnte, idSportello, codiceAmministrazioneMittente);
	String result = "";
	if (amministrazione != null) {
	    result = String.valueOf(amministrazione.getId().getCodice());
	} else {
	    log.error(
		    "decodeAmministrazioneFromSportelloType(): Nessuna Amministrazione mittente configurata per il nodo [idnodo={},idente={},idsportello={}]",
		    new Object[] { idNodo, idEnte, idSportello });
	    throw new RuntimeException("I dati del nodo NLA mittente: [idnodo=" + idNodo + ", idente=" + idEnte + ",idsportello=" + idSportello +
				       "] non corrispondono a nessuna Amministrazione. Configurare l'Amministrazione mittente con i dati del nodo NLA mittente.");
	}
	return result;
    }

    /**
     * metodo per verificare se passare o ricevere le info di protocollo generale
     * 
     * @param mitt
     * @param dest
     * @return
     */
    public boolean passaProt(SportelloType mitt, SportelloType dest) {

	boolean success = false;
	Verticalizzazioniparametri vertProtocolloGenerale = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		"PROTOCOLLOGENERALE");
	if (vertProtocolloGenerale != null && StringUtils.defaultIfEmpty(vertProtocolloGenerale.getValore(), "").equals("S")) {
	    success = true;
	} else {
	    log.debug("passaProt: direzioneSportelloMitt");
	    DirezioneSportelloResponse dirSportelloMit = stcWsClient.getDirezioneSportello(mitt);
	    log.debug("passaProt: direzioneSportelloDest");
	    DirezioneSportelloResponse dirSportelloDest = stcWsClient.getDirezioneSportello(dest);
	    if (dirSportelloMit.getDirezione() != null && dirSportelloDest.getDirezione() != null) {
		if (dirSportelloMit.getDirezione().getCodice().equals(dirSportelloDest.getDirezione().getCodice())) {
		    success = true;
		}
	    }
	}
	log.debug("passaProt: return {}", success);
	return success;
    }

    /**
     * Torna il codice dell'endo o stringa vuota
     * 
     * @param endoProcedimento
     * @return
     */
    protected String decodeEndoProcedimento(Inventarioprocedimenti endoProcedimento) {

	String result = "";
	if (endoProcedimento != null) {
	    if (endoProcedimento.getId() != null) {
		if (endoProcedimento.getId().getCodice() != null) {
		    result = endoProcedimento.getId().getCodice().toString();
		}
	    }
	}
	return result;
    }

    protected ProcedimentoType getProcedimentoPrincipale(List<ProcedimentoType> list) {

	ProcedimentoType pt = null;
	if (list != null) {
	    for (ProcedimentoType procedimentoType : list) {
		if (procedimentoType.isPrincipale()) {
		    pt = procedimentoType;
		    break;
		}
	    }
	}
	return pt;
    }

    /**
     * metodo che popola l'array validationMessages con i messaggi di errore passati come argomento e rilancia una
     * {@link BusinessValidationException}
     * 
     * @param ivs
     *            lista degli InvalidValue della business validate. (può essere nulla o vuota)
     */
    protected void throwValidationMessages(List<InvalidValue> ivs) {

	throw new BusinessValidationException(ivs, getMessageFromBundle("error.business_error_message", new Object[] { "" }), null);
    }

    protected String getMessageFromBundle(String chiave, Object[] args) {

	String message = Utilities.getMessageFromBundle(context, chiave, args);
	return message;
    }

    /**
     * Se l'interazione è tra nodi dello stesso backoffice (mittIdNodo=destIdNodo e mittIdEnte=destIdEnte) allora
     * protocollazione è PROTOCOLLAZIONE_PARAMETRI_ISTANZA_MOVIMENTI_AUTORIZZAZIONE_BACKOFFICE, altrimenti
     * PROTOCOLLAZIONE_PARAMETRI_DA_ONLINE
     */
    protected TipoInserimento checkTipoInserimento(SportelloType sportelloMittente, SportelloType sportelloDestinatario, Comuni comuneIstanza) {

	if (sportelloMittente == null) {
	    throw new RuntimeException("SportelloMittente è nullo");
	}
	if (sportelloDestinatario == null) {
	    throw new RuntimeException("SportelloDestinatario è nullo");
	}
	String mittIdNodo = StringUtils.defaultIfEmpty(sportelloMittente.getIdNodo(), "");
	String mittIdEnte = StringUtils.defaultIfEmpty(sportelloMittente.getIdEnte(), "");
	String destIdNodo = StringUtils.defaultIfEmpty(sportelloDestinatario.getIdNodo(), "");
	String destIdEnte = StringUtils.defaultIfEmpty(sportelloDestinatario.getIdEnte(), "");
	if (mittIdNodo.equalsIgnoreCase(destIdNodo) && mittIdEnte.equalsIgnoreCase(destIdEnte)) {
	    if (comuneIstanza != null && StringUtils.isNotBlank(comuneIstanza.getCodicecomune())) {
		Verticalizzazioniparametri lnmitt = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
			VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
			VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_LISTA_NODI_SOSTITUISCI_MITTENTI,
			comuneIstanza.getCodicecomune());
		if (lnmitt != null) {
		    log.debug("NLA_STC.checkTipoInserimento: LA LISTA DEI NODI PRESENTE");
		    if (StringUtils.isNotBlank(lnmitt.getValore())) {
			String idnodomitt = sportelloMittente.getIdNodo();
			String identemitt = sportelloMittente.getIdEnte();
			String idsportellomitt = sportelloMittente.getIdSportello();
			log.debug("NLA_STC.checkTipoInserimento: LA LISTA DEI NODI VALORI: {},{},{}",
				new String[] { idnodomitt, identemitt, idsportellomitt });
			if (StringUtils.isNotBlank(idnodomitt) && StringUtils.isNotBlank(identemitt) && StringUtils.isNotBlank(idsportellomitt)) {
			    String codiceMitt = idnodomitt + "_" + identemitt + "_" + idsportellomitt;
			    if (lnmitt.getValore().indexOf(codiceMitt) >= 0) {
				return TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_DA_ONLINE;
			    }
			}
		    }
		}
	    }
	    return TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_NORMALE;
	}
	return TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_DA_ONLINE;
    }

    public String getValoreListaNodiSTCNonAggAnagrafe() {

	if (mappaVerticalizzazioniListaNodiSTCNonAggAnagrafe == null) {
	    mappaVerticalizzazioniListaNodiSTCNonAggAnagrafe = new HashMap<String, String>();
	}
	String key = getVerticalizzazioniListaNodiSTCNonAggAnagrafeKey(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(),
		WebConstants.VERTICALIZZAZIONE_STC_LISTA_NODI_NON_AGG_ANAGRAFE);
	String valore = "";
	if (mappaVerticalizzazioniListaNodiSTCNonAggAnagrafe.get(key) == null) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_LISTA_NODI_NON_AGG_ANAGRAFE);
	    if (vp != null) {
		if (StringUtils.isNotBlank(vp.getValore())) {
		    valore = vp.getValore();
		}
	    }
	    mappaVerticalizzazioniListaNodiSTCNonAggAnagrafe.put(key, valore);
	} else {
	    valore = mappaVerticalizzazioniListaNodiSTCNonAggAnagrafe.get(key);
	}
	return valore;
    }

    private String getVerticalizzazioniListaNodiSTCNonAggAnagrafeKey(String idcomuneAlias, String software, String parametro) {

	return idcomuneAlias + "-" + software + "-" + parametro;
    }

    protected Map<String, String> mappaVerticalizzazioniListaNodiSTCNonAggAnagrafe = null;
    protected Map<String, String> mappaVerticalizzazioniListaNodiSTCInviaAllegati = null;

    public String getValoreListaNodiSTCScaricaAllegati() {

	if (mappaVerticalizzazioniListaNodiSTCInviaAllegati == null) {
	    mappaVerticalizzazioniListaNodiSTCInviaAllegati = new HashMap<String, String>();
	}
	String key = getVerticalizzazioniListaNodiSTCNonAggAnagrafeKey(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(),
		WebConstants.VERTICALIZZAZIONE_STC_LISTA_LISTA_NODI_MITT_GET_ALLEGATI);
	String valore = "";
	if (mappaVerticalizzazioniListaNodiSTCInviaAllegati.get(key) == null) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_LISTA_LISTA_NODI_MITT_GET_ALLEGATI);
	    if (vp != null) {
		if (StringUtils.isNotBlank(vp.getValore())) {
		    valore = vp.getValore();
		}
	    }
	    mappaVerticalizzazioniListaNodiSTCInviaAllegati.put(key, valore);
	} else {
	    valore = mappaVerticalizzazioniListaNodiSTCInviaAllegati.get(key);
	}
	return valore;
    }

    /**
     * La funzione legge se è configurato il parametro di verticalizzazione
     * {@link WebConstants#VERTICALIZZAZIONE_STC_LISTA_NODI_NON_AGG_ANAGRAFE}. <br />
     * Se configurato, questo contiene una stringa che rappresenta gli id dei nodi che non devono aggiornare gli
     * attributi della scheda anagrafica (qualora venisse identificata per CF o PIVA). Questo controllo è stato
     * realizzato per la richiesta di Cervia (Provincia di Ravenna) che non vuole che ad ogni domanda proveniente da
     * PEOPLE produca una scheda storicizzata.
     * 
     * @param sportelloMittente
     * @return
     */
    public boolean isScaricaAllegatiFisiciPerNodo(SportelloType sportello) {

	String valore = getValoreListaNodiSTCScaricaAllegati();
	if (StringUtils.isNotBlank(valore)) {
	    String idNodoMittente = StringUtils.defaultIfEmpty(sportello.getIdNodo(), "").trim();
	    String[] listaNodi = valore.split(",");
	    for (String nodo : listaNodi) {
		if (idNodoMittente.equalsIgnoreCase(StringUtils.defaultIfEmpty(nodo, "").trim())) {
		    return true;
		}
	    }
	}
	return false;
    }

    protected void popolaFileSuAllegato(AllegatiType allegato, Integer codiceOggetto) {

	if (codiceOggetto != null) {
	    if (allegato != null) {
		Oggetti o = oggettiService.findById(new PkId(codiceOggetto));
		if (o != null) {
		    AllegatoBinarioType file = new AllegatoBinarioType();
		    file.setBinaryData(Utilities.bytesToDataHandler(o.getOggetto()));
		    file.setFileName(o.getNomefile());
		    String mime = contenttypesService.findMimeTypeByFileName(file.getFileName());
		    file.setMimeType(mime);
		    allegato.setFile(file);
		}
	    }
	}
    }

    public SportelloType getSportelloMittente() {

	SportelloType mittente = new SportelloType();
	mittente.setIdEnte(ORMHelper.getIdcomuneAlias());
	mittente.setIdSportello(ORMHelper.getSoftware());
	Verticalizzazioniparametri vparam = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC, "NLA_IDNODO");
	if (vparam == null) {
	    String error = "Attenzione! non è stato configurato il parametro NLA_IDNODO della verticalizzazione STC";
	    log.error("getSportelloMittente(): {}", error);
	    throw new RuntimeException(error);
	}
	mittente.setIdNodo(vparam.getValore());
	return mittente;
    }

    protected SportelloType getSportelloDestinatario(Amministrazioni amm) {

	// DEVONO ESSERE CONFIGURATI I PARAMETRI DELL'AMMINISTRAZIONE DEL MOVIMENTO
	if (amm == null) {
	    String errorMessage = "Attenzione! Non è stata trovata l'amministrazione STC del movimento. MOVIMENTI.CODICEAMMINISTRAZIONE_STC è nullo";
	    log.error("getSportelloDestinatario(): {}", errorMessage);
	    throw new RuntimeException(errorMessage);
	}
	if (StringUtils.isBlank(amm.getStcIdsportello()) || StringUtils.isBlank(amm.getStcIdente()) || StringUtils.isBlank(amm.getStcIdnodo())) {
	    String errorMessage = "Attenzione! Non sono stati configurati i parametri della comunicazione STC per l'amministrazione " +
				  amm.getAmministrazione() + " (" + amm.getId().getCodice() + ")";
	    log.error("getSportelloDestinatario(): {}", errorMessage);
	    throw new RuntimeException(errorMessage);
	}
	SportelloType destinatario = new SportelloType();
	destinatario.setIdEnte(amm.getStcIdente());
	destinatario.setIdSportello(amm.getStcIdsportello());
	destinatario.setIdNodo(amm.getStcIdnodo());
	// utilizzata dall' NLA-ENTI
	destinatario.setPecSportello(amm.getPec());
	return destinatario;
    }

    /**
     * Metodo per inserire una lista di oggetti istanzedyn2dati a partire dalla lista altriDati. Ogni elemento
     * (parametroType) della sezione altriDati deve contenere nel campo nome la dicitura FO_DYN2DATO_XXX o
     * FO_DYN2DATO_DYN2CAMPI_NOMECAMPO_XXX. Nel primo caso XXX deve corrispondere al valore ID del record della tabella
     * DYN2_CAMPI mentre nel secondo al valore NOMECAMPO del record della tabella DYN2_CAMPI.
     * 
     * Il metodo prima elimina gli oggetti istanzedyn2dati "vecchi" e poi inserisce quelli nuovi.
     * 
     * @param altriDati
     * @param codiceIstanza
     */
    protected void insertIstanzedyn2datiFromAltriDati(List<ParametroType> altriDati, Integer codiceIstanza) {

	if (log.isDebugEnabled()) {
	    log.debug("insertIstanzedyn2datiFromAltriDati# start...");
	}
	try {
	    // Ciclo tutti i valori presenti in altri dati
	    for (ParametroType parametroType : altriDati) {
		// Cerco quelli che come nome hanno la dicitura FO_DYN2DATO_codice_campo dove codice campo è un stringa che rappresneta 
		// un numerico (Es. FO_DYN2DATO_001)
		if (parametroType.getNome().contains(WebConstants.FO_DYN2DATO)) {
		    String stringaDaRimuovere = WebConstants.FO_DYN2DATO + "_";
		    if (log.isDebugEnabled()) {
			log.debug("insertIstanzedyn2datiFromAltriDati# Recuperato il campo dinamico dalla sezioni di altri dati con nome: {} ",
				parametroType.getNome());
		    }
		    String nomeAltroDato = parametroType.getNome();
		    // Estraggo il valore numerico dalla stringa FO_DYN2DATO_001
		    String _codiceCampo = StringUtils.remove(nomeAltroDato, stringaDaRimuovere);
		    Integer codiceCampo = null;
		    //fabrizioc: modifica per recuperare dalla sezione altri dati i campi dinamici a partire dal nomecampo di dyn2campi
		    if (parametroType.getNome().contains(WebConstants.FO_DYN2DATO + "_DYN2CAMPI_NOMECAMPO_")) {
			//recupero il valore codiceCampo a partire da nomeCampo.
			stringaDaRimuovere = WebConstants.FO_DYN2DATO + "_DYN2CAMPI_NOMECAMPO_";
			String nomeCampo = StringUtils.remove(nomeAltroDato, stringaDaRimuovere);
			//TODO controllare se il metodo cerca per TT dopo il software corrente
			Dyn2Campi dyn2Campo = dyn2CampiService.findByNomeCampo(nomeCampo);
			codiceCampo = dyn2Campo.getId().getCodice();
		    } else {
			codiceCampo = Integer.parseInt(_codiceCampo);
		    }
		    //
		    Dyn2Campi dyn2Campo = dyn2CampiService.findById(new PkId(codiceCampo));
		    if (log.isDebugEnabled()) {
			log.debug(
				"insertIstanzedyn2datiFromAltriDati# Ricerco ed elimino i record di istanzeDyn2Dati filtrati per: codiceIstanza:{},codiceCampo:{}",
				new Object[] { codiceIstanza, codiceCampo });
		    }
		    // Recupero tutti i record di istanzedyn2dati che hanno codiceistanza =movimenti.getIstanza.getId.getCodice e fkD2cId=codiceCampo
		    List<Istanzedyn2dati> istanzedyn2datisDaCancellare = istanzedyn2datiService.findByIstanzaAndDyn2Campi(codiceIstanza, codiceCampo);
		    // Ciclo la lista trovata ed elimino campo per campo 
		    for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datisDaCancellare) {
			if (log.isDebugEnabled()) {
			    log.debug("insertIstanzedyn2datiFromAltriDati# Elimino il record istanzaDyn2dati con fkD2cId: {}, codiceIstanza: {}",
				    new Object[] { istanzedyn2dati.getId().getFkD2cId(), codiceIstanza });
			}
			istanzedyn2datiService.delete(istanzedyn2dati);
		    }
		    // Per ogni ParametroType selezionato (Il nome deve avere la forma FO_DYN2DATO_001) recupero tutti i valori
		    // avremo una lista di coppie di valori stringhe (codice,descrizione), per ogni coppia di valore creeremo
		    // un record di istanzeDyn2dati con  valore=valoreParametroType.getCodice();  valoredecodificato=valoreParametroType.getDescrizione(); 
		    // indice=0;indiceMoltiplicità = numero incrementale(partendo da zero)
		    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
		    Istanzedyn2dati istanzedyn2datiTemp = null;
		    Istanzedyn2datiId id = null;
		    List<ValoreParametroType> list = parametroType.getValore();
		    int molteplicita = 0;
		    for (ValoreParametroType valoreParametroType : list) {
			//popolo l'id
			id = new Istanzedyn2datiId();
			id.setCodiceistanza(codiceIstanza);
			id.setIndice(0);
			id.setFkD2cId(codiceCampo);
			id.setIndiceMolteplicita(molteplicita);
			// Popolo l'oggetto 
			istanzedyn2datiTemp = new Istanzedyn2dati();
			istanzedyn2datiTemp.setId(id);
			istanzedyn2datiTemp.setIstanza(istanza);
			istanzedyn2datiTemp.setDyn2Campi(dyn2Campo);
			istanzedyn2datiTemp.setValore(valoreParametroType.getCodice());
			istanzedyn2datiTemp.setValoredecodificato(valoreParametroType.getDescrizione());
			istanzedyn2datiService.insert(istanzedyn2datiTemp);
			molteplicita++;
		    }
		}
	    }
	    if (log.isDebugEnabled()) {
		log.debug("insertIstanzedyn2datiFromAltriDati# end");
	    }
	} catch (Exception e) {
	    log.error("insertIstanzedyn2datiFromAltriDati", e);
	}
    }
}
