package it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.TipoMittDestAutoEnum;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class VerticalizzazioneProtocolloAttivoServiceImpl implements IVerticalizzazioneProtocolloAttivoService {

    private VerticalizzazioniService service;
    private boolean attiva = false;
    private String codiceComune;
    //
    public static final String NOME_VERTICALIZZAZIONE = "PROTOCOLLO_ATTIVO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_APPLICA_LAYER = "APPLICA_LAYER";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_ABILITA_INDIRIZZI_EMAIL = "ABILITA_INDIRIZZI_EMAIL";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTIONE_PEC = "GESTIONE_PEC";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VIS_PANEL_RICERCA_FASCICOLO = "VIS_PANEL_RICERCA_FASCICOLO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_PANEL_RIC_FASC_PREC_CLASSIF = "PANEL_RIC_FASC_PREC_CLASSIF";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_LOGICA_MITTENTI_MULTIPLI = "LOGICA_MITT_MULTIPLI";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CLASSIFICADEFAULT_BO = "CLASSIFICADEFAULT_BO";
    /**
     * Timeout della chiamata tra il backend e l'interfaccia dei servizi di protocollazione
     * {@link BackofficeNETConstants#getURL_WS_PROTOCOLLAZIONE()}
     */
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIMEOUT_CHIAMATA_WS_INTERNA = "TIMEOUT_CHIAMATA_WS_INTERNA";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CLASSIFICA_FASC_DEFAULT_BO = "CLASSIFICA_FASC_DEFAULT_BO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_COD_ALBERO_ROOT_PROTOCOLL = "COD_ALBERO_ROOT_PROTOCOLL";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NUMDATAPROTMITT = "NUMDATAPROTMITT";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE = "GESTISCI_FASCICOLAZIONE";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_DESTINATARICC = "DESTINATARICC";
    /**
     * E’ il parametro che permette di stabilire se nella pagina di richiesta protocollo sia presente o meno la sezione
     * per inviare gli allegati della pratica e dei movimenti.Inoltre permette di stabilire se gestire gli allegati o
     * meno nel caso di protocollazione automatiche da BO (Inserimento normale, inserimento rapido e protocollazione
     * massiva di movimenti. Es. 1=Sezione non presente, 0=Sezione presente
     */
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NOALLEGATI = "NOALLEGATI";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZAUORAGGRUPPATE = "VISUALIZZAUORAGGRUPPATE";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT = "CODICEAMMINISTRAZIONEDEFAULT";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOVNOPRECOMPILAMITT_DEST = "MOVNOPRECOMPILAMITT_DEST";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPODOCUMENTODEFAULTBO = "TIPODOCUMENTODEFAULTBO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPODOCUMENTODEFAULT = "TIPODOCUMENTODEFAULT";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI = "VISUALIZZABOTTONELEGGI";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONESTAMPA = "VISUALIZZABOTTONESTAMPA";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONESTAMPA_URL = "VISUALIZZABOTTONESTAMPA_URL";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO = "TIPOPROTOCOLLO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODIFICA_CLASSIFICA = "MODIFICA_CLASSIFICA";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODIFICA_CLASSIFICA_PARAMETRI_PROT = "MODIFICA_CLASSIFICAFASC";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODALITA_TRASMISSIONE_DEFAULT = "MODALITA_TRASMISSIONE_DEFAULT";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MEZZO_DEFAULT = "MEZZO_DEFAULT";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FLUSSO_MOSTRATO_DEFAULT = "FLUSSO_MOSTRATO_DEFAULT";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTIONE_ACCETTAZIONE = "GESTIONE_ACCETTAZIONE";
    /**
     * Gestisce la visulazzazione della funzionalità aggiungi documento
     */
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VIS_BOTTONE_ADD_DOCUMENTO = "VIS_BOTTONE_ADD_DOCUMENTO";
    /**
     * Gestisce la funzionalità di smistamento multiplo
     */
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_IS_SMISTAMENTO_MULTIPLO = "IS_SMISTAMENTO_MULTIPLO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MAPPATURA_DITTA_INDIVIDUALE = "MAPPATURA_DITTA_INDIVIDUALE";
    /**
     * Valorizzare a S se si desidera gestire la funzionalità della messa alla firma. Sarà visualizzato il pulsante
     * METTI ALLA FIRMA nella maschera dei movimenti ma solo se non presenti i riferimenti di protocollo
     */
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOSTRA_METTI_ALLA_FIRMA = "MOSTRA_METTI_ALLA_FIRMA";
    /**
     * Parametro che permette di decire se mostrare a video in fase di lettura del protocollo i valori nulli.
     */
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOSTRA_DATI_PROTOCOLLO_NULLI = "MOSTRA_DATI_PROTOCOLLO_NULLI";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPO_MITTDEST_AUTO = "TIPO_MITTDEST_AUTO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOSMISTAMENTODEFAULT = "TIPOSMISTAMENTODEFAULT";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FLUSSI_VER_FIRMA_DOC_PRINC = "FLUSSI_VER_FIRMA_DOC_PRINC";
    /**
     * Consente di selezionare quali degli allegati che verranno protocollati devono essere inviati anche per PEC
     */
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_SELEZIONA_ALLEGATI_PEC = "SELEZIONA_ALLEGATI_PEC";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_SEGNA_PROT_AUT_KO = "SEGNA_PROT_AUT_KO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_LISTA_NODI_SOSTITUISCI_MITTENTI = "LISTA_NODI_SOSTIT_MITTENTI";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_RICHIEDI_AMM_AZIONI_PROTOCOLLO = "RICHIEDI_AMM_AZIONI_PROTOCOLLO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FORZA_FASCICOL_NOT_AUTOMATICA = "FORZA_FASCICOL_NOT_AUTOMATICA";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_URL_WS_PROTOCOLLO = "URL_WS_PROTOCOLLO";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOMOV_RICEVUTA = "TIPOMOV_RICEVUTA";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FLUSSODEFAULT = "FLUSSODEFAULT";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_OGGETTO_UPPERCASE = "OGGETTO_UPPERCASE";
    public static final String VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NUM_CARATTERI_OGGETTO = "NUM_CARATTERI_OGGETTO";

    public VerticalizzazioneProtocolloAttivoServiceImpl(VerticalizzazioniService service, String codiceComune) {

	if (codiceComune == null) {
	    throw new IllegalArgumentException("È stata richiamata la verticalizzazione del protocollo attivo senza passare il codice comune");
	}
	this.service = service;
	this.codiceComune = codiceComune;
	this.attiva = isAttivaInternal();
    }

    @Override
    public String nomeVerticalizzazione() {

	return VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE;
    }

    @Override
    public boolean isAttiva() {

	return this.attiva;
    }

    private boolean isAttivaInternal() {

	return this.service.isAttivaPerComune(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE, codiceComune);
    }

    @Override
    public String isAttivoApplicaLayer() {

	if (!this.isAttiva()) {
	    return "N";
	}
	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_APPLICA_LAYER, codiceComune);
	return vp != null ? vp.getValore() : "N";
    }

    @Override
    public boolean isForzaFascicolazioneNotificaAutomatica() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FORZA_FASCICOL_NOT_AUTOMATICA, codiceComune);
	String valore = "N";
	if (vp != null) {
	    valore = StringUtils.defaultString(vp.getValore(), "N").trim();
	}
	return valore.equalsIgnoreCase("S");
    }

    @Override
    public Integer getGestionePEC() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTIONE_PEC, codiceComune);
	if (vp != null) {
	    return Integer.parseInt(StringUtils.defaultString(vp.getValore(), "0").trim());
	}
	return 0;
    }

    @Override
    public String getTipoMovRicevuta() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOMOV_RICEVUTA, codiceComune);
	return (vp != null) ? StringUtils.defaultString(vp.getValore(), "").trim() : null;
    }

    @Override
    public String getFlussoDefault() {

	return this.getFlussoDefault(ORMHelper.getSoftware());
    }

    @Override
    public String getFlussoDefault(String software) {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FLUSSODEFAULT, codiceComune, software);
	return (vp != null) ? StringUtils.defaultString(vp.getValore(), "").trim() : null;
    }

    @Override
    public String getMappaturaDittaIndividuale() {

	return this.getMappaturaDittaIndividuale(ORMHelper.getSoftware());
    }

    @Override
    public String getMappaturaDittaIndividuale(String software) {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MAPPATURA_DITTA_INDIVIDUALE, codiceComune, software);
	return (vp != null) ? StringUtils.defaultString(vp.getValore(), "").trim() : null;
    }

    @Override
    public Integer getCodiceAmministrazioneDefault() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT, codiceComune);
	if (vp != null && StringUtils.isNotBlank(vp.getValore())) {
	    return Integer.parseInt(vp.getValore());
	}
	return null;
    }

    @Override
    public ProtocolloMezzi getMezzoDefault() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MEZZO_DEFAULT, codiceComune);
	if (vp == null || StringUtils.isBlank(vp.getValore())) {
	    return null;
	}
	ProtocolloMezzi mezzo = new ProtocolloMezzi();
	mezzo.setCodice(vp.getValore());
	return mezzo;
    }

    @Override
    public ProtocolloModalitainvio getModalitaTrasmissioneDefault() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODALITA_TRASMISSIONE_DEFAULT, codiceComune);
	if (vp == null || StringUtils.isBlank(vp.getValore())) {
	    return null;
	}
	ProtocolloModalitainvio modalita = new ProtocolloModalitainvio();
	modalita.setCodice(vp.getValore());
	return modalita;
    }

    @Override
    public TipoMittDestAutoEnum getTipoMittDestAuto() {

	return this.getTipoMittDestAuto(ORMHelper.getSoftware());
    }

    @Override
    public TipoMittDestAutoEnum getTipoMittDestAuto(String software) {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPO_MITTDEST_AUTO, codiceComune, software);
	String valore = (vp == null) ? "" : StringUtils.defaultIfEmpty(vp.getValore(), "").trim();
	return TipoMittDestAutoEnum.fromParametroVerticalizzazione(valore);
    }

    @Override
    public String getTipoDocumentoDefault() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPODOCUMENTODEFAULT, codiceComune);
	return (vp != null) ? StringUtils.defaultString(vp.getValore(), "").trim() : null;
    }

    @Override
    public String getTipoDocumentoDefaultBo() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPODOCUMENTODEFAULTBO, codiceComune);
	return (vp != null) ? StringUtils.defaultString(vp.getValore(), "").trim() : null;
    }

    @Override
    public String getTipoSmistamentoDefault() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOSMISTAMENTODEFAULT, codiceComune);
	return (vp != null) ? StringUtils.defaultString(vp.getValore(), "").trim() : null;
    }

    @Override
    public boolean trasformaOggettoProtocolloUpperCase() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_OGGETTO_UPPERCASE, codiceComune);
	return (vp != null && StringUtils.defaultString(vp.getValore(), "0").trim().equals("1"));
    }

    @Override
    public Integer lunghezzaMassimaOggettoProtocollo() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_NUM_CARATTERI_OGGETTO, codiceComune);
	return (vp != null && !StringUtils.isBlank(vp.getValore())) //
		? Integer.parseInt(vp.getValore()) //
		: null;
    }

    @Override
    public String getClassificaDefaultBO() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CLASSIFICADEFAULT_BO, codiceComune);
	return (vp != null) ? StringUtils.defaultString(vp.getValore(), "").trim() : null;
    }
}
