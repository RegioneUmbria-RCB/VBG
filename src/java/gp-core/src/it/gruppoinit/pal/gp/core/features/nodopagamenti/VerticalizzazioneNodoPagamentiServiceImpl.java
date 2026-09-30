package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListContestoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza.SoggettiPendenzaEnum;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class VerticalizzazioneNodoPagamentiServiceImpl implements IVerticalizzazioneNodoPagamentiService {

    private VerticalizzazioniService service;
    public static final String NOME_VERTICALIZZAZIONE = "NODO_PAGAMENTI";
    private static final String MESSAGGIO_ERRORE = "La verticalizzazione " +
	    VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE +
	    " non è attiva.";
    public static final String AR_COD_FISC_ENTE_CREDITORE = "AR_COD_FISC_ENTE_CREDITORE";
    public static final String AR_URL_BACK = "AR_URL_BACK";
    public static final String AR_URL_RITORNO = "AR_URL_RITORNO";
    public static final String BLACKLIST_TIME_PRESENZE = "BLACKLIST_TIME_PRESENZE";
    public static final String BLACKLIST_TIME_BOLLETTAZIONE = "BLACKLIST_TIME_BOLLETTAZIONE";
    public static final String ID_MODALITA_PAGAMENTO = "ID_MODALITA_PAGAMENTO";
    public static final String URL_WS = "URL_WS";
    public static final String INTERVALLO_PREDEFINITO_INSERIMENTO_BL = "P0Y0M0DT30H0M0S";
    public static final String INTERVALLO_PREDEFINITO_INSERIMENTO_BL_BOLLETTAZIONE = "P0Y0M30DT0H0M0S";
    public static final String TIPOMOVIMENTO_DOC_FATTURA = "TIPOMOVIMENTO_DOC_FATTURA";
    public static final String TIPOMOVIMENTO_DOC_AVVISO = "TIPOMOVIMENTO_DOC_AVVISO";
    public static final String SOGGETTO_PENDENZA = "SOGGETTO_PENDENZA";
    public static final String CREA_PER_SOGGETTI_COLLEGATI = "CREA_PER_SOGGETTI_COLLEGATI";
    private String codiceComune;
    private boolean attiva = false;
    private String urlWs = null;
    private String arCodFiscEnteCreditore = null;
    private Integer idModalitaPagamento = null;

    public VerticalizzazioneNodoPagamentiServiceImpl(VerticalizzazioniService service, String codiceComune) {

	if (codiceComune == null) {
	    throw new IllegalArgumentException("È stata richiamata la verticalizzazione del nodo pagamenti senza passare il codice comune");
	}
	this.service = service;
	this.codiceComune = codiceComune;
	this.attiva = isAttivaInternal();
	this.verificaEPopolaProprietaObbligatoriePerEnte();
    }

    @Override
    public boolean isAttiva() {

	return this.attiva;
    }

    @Override
    public String arUrlBack() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri verticalizzazioniparametriPerComune = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE, VerticalizzazioneNodoPagamentiServiceImpl.AR_URL_BACK,
		codiceComune);
	if (verticalizzazioniparametriPerComune != null) {
	    return verticalizzazioniparametriPerComune.getValore();
	}
	return null;
    }

    @Override
    public String arUrlRitorno() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri verticalizzazioniparametriPerComune = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE, VerticalizzazioneNodoPagamentiServiceImpl.AR_URL_RITORNO,
		codiceComune);
	if (verticalizzazioniparametriPerComune != null) {
	    return verticalizzazioniparametriPerComune.getValore();
	}
	return null;
    }

    
    @Override
    public String blackListTimeCheckPagam(BlackListContestoEnum contesto) {

	Verticalizzazioniparametri verticalizzazioniparametriPerComune = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE,
		BlackListContestoEnum.PRESENZE == contesto ? VerticalizzazioneNodoPagamentiServiceImpl.BLACKLIST_TIME_PRESENZE : VerticalizzazioneNodoPagamentiServiceImpl.BLACKLIST_TIME_BOLLETTAZIONE,
		codiceComune);
	
	if(verticalizzazioniparametriPerComune == null){
	    return null;
	}
	
	return verticalizzazioniparametriPerComune.getValore();
	
    }

    public static String getDefaultBlackListTime() {

	return VerticalizzazioneNodoPagamentiServiceImpl.INTERVALLO_PREDEFINITO_INSERIMENTO_BL;
    }

    public static String getDefaultBlackListTimeBollettazione() {

	return VerticalizzazioneNodoPagamentiServiceImpl.INTERVALLO_PREDEFINITO_INSERIMENTO_BL_BOLLETTAZIONE;
    }

    @Override
    public String arCodFiscEnteCreditore() {

	return this.arCodFiscEnteCreditore;
    }

    @Override
    public Integer idModalitaPagamento() {

	return this.idModalitaPagamento;
    }

    @Override
    public String urlWs() {

	return this.urlWs;
    }

    @Override
    public String tipomovimentoDocFattura() {

	Verticalizzazioniparametri verticalizzazioniparametriPerComune = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE, VerticalizzazioneNodoPagamentiServiceImpl.TIPOMOVIMENTO_DOC_FATTURA,
		codiceComune);
	if (verticalizzazioniparametriPerComune != null) {
	    return verticalizzazioniparametriPerComune.getValore();
	}
	return null;
    }

    @Override
    public String tipomovimentoDocAvviso() {

	Verticalizzazioniparametri verticalizzazioniparametriPerComune = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE, VerticalizzazioneNodoPagamentiServiceImpl.TIPOMOVIMENTO_DOC_AVVISO,
		codiceComune);
	if (verticalizzazioniparametriPerComune != null) {
	    return verticalizzazioniparametriPerComune.getValore();
	}
	return null;
    }

    @Override
    public SoggettiPendenzaEnum soggettoPendenza() {

	Verticalizzazioniparametri verticalizzazioniparametriPerComune = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE, VerticalizzazioneNodoPagamentiServiceImpl.SOGGETTO_PENDENZA,
		codiceComune);
	if (verticalizzazioniparametriPerComune != null) {
	    String valore = StringUtils.defaultString(verticalizzazioniparametriPerComune.getValore()).trim();
	    if (StringUtils.isNotEmpty(valore)) {
		return SoggettiPendenzaEnum.valueOf(valore.toUpperCase());
	    }
	}
	return SoggettiPendenzaEnum.RICHIEDENTE;
    }

    @Override
    public boolean creaPerSoggettiCollegati() {

	Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneNodoPagamentiServiceImpl.CREA_PER_SOGGETTI_COLLEGATI, codiceComune);
	return (vp != null && StringUtils.defaultIfEmpty(vp.getValore(), "N").trim().equalsIgnoreCase("S"));
    }

    @Override
    public List<String> findCfEntiCreditoriConfigurati() {

	return this.service.findValoreByModuloEParametro(VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneNodoPagamentiServiceImpl.AR_COD_FISC_ENTE_CREDITORE);
    }

    @Override
    public String findUrlConfigurato() {

	List<String> url = this.service.findValoreByModuloEParametro(VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneNodoPagamentiServiceImpl.URL_WS);
	if (url.size() > 0) {
	    return url.get(0);
	}
	return null;
    }

    private boolean isAttivaInternal() {

	return this.service.isAttivaPerComune(VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE, codiceComune);
    }

    private void verificaEPopolaProprietaObbligatoriePerEnte() {

	if (isAttiva()) {
	    this.arCodFiscEnteCreditore = arCodFiscEnteCreditoreInternal();
	    this.idModalitaPagamento = idModalitaPagamentoInternal();
	    this.urlWs = urlWsInternal();
	    if (StringUtils.isBlank(arCodFiscEnteCreditore) || (null == idModalitaPagamento) || (StringUtils.isBlank(urlWs))) {
		String params = StringUtils.isBlank(arCodFiscEnteCreditore) ? "[" + AR_COD_FISC_ENTE_CREDITORE + "] " : "";
		params += idModalitaPagamento == null ? "[" + ID_MODALITA_PAGAMENTO + "] " : "";
		params += StringUtils.isBlank(urlWs) ? "[" + URL_WS + "] " : "";
		throw new IllegalArgumentException("Per il comune " +
			codiceComune +
			" la regola [" +
			NOME_VERTICALIZZAZIONE +
			"] risulta attivata ma non configurata correttamente. Uno dei seguenti parametri " +
			params +
			" non è stato configurato ");
	    }
	}
    }

    private Integer idModalitaPagamentoInternal() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri verticalizzazioniparametriPerComune = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE, VerticalizzazioneNodoPagamentiServiceImpl.ID_MODALITA_PAGAMENTO,
		codiceComune);
	if (verticalizzazioniparametriPerComune != null) {
	    String valore = StringUtils.defaultString(verticalizzazioniparametriPerComune.getValore()).trim();
	    if (Utilities.isInteger(valore)) {
		return Integer.parseInt(valore);
	    }
	}
	return null;
    }

    private String urlWsInternal() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri verticalizzazioniparametriPerComune = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE, VerticalizzazioneNodoPagamentiServiceImpl.URL_WS, codiceComune);
	if (verticalizzazioniparametriPerComune != null) {
	    return verticalizzazioniparametriPerComune.getValore();
	}
	return null;
    }

    private String arCodFiscEnteCreditoreInternal() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(MESSAGGIO_ERRORE);
	}
	Verticalizzazioniparametri verticalizzazioniparametriPerComune = this.service.getVerticalizzazioniparametriPerComune(
		VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneNodoPagamentiServiceImpl.AR_COD_FISC_ENTE_CREDITORE, codiceComune);
	if (verticalizzazioniparametriPerComune != null && StringUtils.isNotBlank(verticalizzazioniparametriPerComune.getValore())) {
	    return verticalizzazioniparametriPerComune.getValore().trim();
	}
	return null;
    }
}
