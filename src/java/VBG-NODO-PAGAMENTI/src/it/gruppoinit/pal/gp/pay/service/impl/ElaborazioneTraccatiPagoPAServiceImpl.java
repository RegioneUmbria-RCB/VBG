package it.gruppoinit.pal.gp.pay.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gov.pagopa.rendicontazione.DatiSingoloPagamento;
import it.gov.pagopa.rendicontazione.FlussoRiversamento;
import it.gov.pagopa.rendicontazione.IdentificativoUnivocoIstituto;
import it.gov.pagopa.rendicontazione.IstitutoMittente;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.utils.IFileHelper;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.core.utils.ParamsSFTP;
import it.gruppoinit.pal.gp.core.utils.SFTPHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.IElaborazioneTracciatiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;

public class ElaborazioneTraccatiPagoPAServiceImpl implements IElaborazioneTracciatiService {

    private static final Logger log = LoggerFactory.getLogger(ElaborazioneTraccatiPagoPAServiceImpl.class);
    private static final String SERVICE_NAME = "ELABORAZIONE_TRACCIATI_PAGO_PA";
    private IPayConnector iPayConnector;
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    private PayProfiliEntiCreditoriService profiliEntiCreditoriService;
    private ConfigurazionePagamentiService configurazionePagamentiService;
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    private String idComune = null;
    private String codiceConnettore = null;
    private PayPagamentiService payPagamentiService;
    private String strategia = null;
    private String quartzExpression = null;
    private String codiceProfilo;
    private String ftpUSer = null;
    private String ftpPassword = null;
    private String serverFTP = null;
    private Integer portFTP = 22;
    private String rootfolderFTP = null;
    private String elaboratiFolder = "elaborati";
    private String erroriElaborazioneFolder = "errori";

    public ElaborazioneTraccatiPagoPAServiceImpl(IPayConnector iPayConnector, PayConnectorConfigValuesService payConnectorConfigValuesService,
	    PayPagamentiService payPagamentiService, PayProfiliEntiCreditoriService profiliEntiCreditoriService,
	    ConfigurazionePagamentiService configurazionePagamentiService, PayPosizioniDebitorieService payPosizioniDebitorieService)
	    throws PayConfigurationException {

	super();
	this.iPayConnector = iPayConnector;
	this.payConnectorConfigValuesService = payConnectorConfigValuesService;
	this.payPagamentiService = payPagamentiService;
	this.profiliEntiCreditoriService = profiliEntiCreditoriService;
	this.configurazionePagamentiService = configurazionePagamentiService;
	this.payPosizioniDebitorieService = payPosizioniDebitorieService;
	this.codiceConnettore = iPayConnector.getConnectorCode();
	String idComuneOrig = ORMHelper.getIdcomune();
	PayProfiliEntiCreditori pprof = this.profiliEntiCreditoriService.findByConnectorCode(this.codiceConnettore);
	this.codiceProfilo = pprof.getCfCodiceProfilo();
	this.idComune = pprof.getId().getIdcomune();
	ORMHelper.setIdcomune(idComune);
	parametri();
	ORMHelper.setIdcomune(idComuneOrig);
    }

    private void parametri() {

	strategia = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_TRAC_PAGOPA_STRATEGIA,
		iPayConnector.getConnectorCode());
	quartzExpression = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_TRAC_PAGOPA_QUARTZEXP,
		iPayConnector.getConnectorCode());
	ftpUSer = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_TRAC_PAGOPA_FTP_USER,
		iPayConnector.getConnectorCode());
	ftpPassword = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_TRAC_PAGOPA_FTP_PASSWORD,
		iPayConnector.getConnectorCode());
	serverFTP = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_TRAC_PAGOPA_FTP_SERVER,
		iPayConnector.getConnectorCode());
	String sPortFTP = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_TRAC_PAGOPA_FTP_PORT,
		iPayConnector.getConnectorCode());
	if (StringUtils.isNotBlank(sPortFTP) && Utilities.isInteger(sPortFTP)) {
	    this.portFTP = Integer.parseInt(sPortFTP);
	}
	rootfolderFTP = payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.SCHED_TRAC_PAGOPA_FTP_FOLDER,
		iPayConnector.getConnectorCode());
    }

    @Override
    public boolean isAttivo() {

	return StringUtils.isNotBlank(strategia) && //
		StringUtils.isNotBlank(serverFTP) && // 
		portFTP != null && //
		StringUtils.isNotBlank(ftpUSer) && // 
		StringUtils.isNotBlank(ftpPassword) && // 
		StringUtils.isNotBlank(rootfolderFTP) && //
		StringUtils.isNotBlank(quartzExpression);
    }

    @Override
    public IPayConnector getIPayConnector() {

	return this.iPayConnector;
    }

    @Override
    public String getServiceName() {

	return SERVICE_NAME;
    }

    @Override
    public String getQuartzScheduleExpression() {

	return this.quartzExpression;
    }

    @Override
    public EsitoElaborazione elabora() {

	log.debug("Elaboro i traccati pagopa per codiceprofilo {}", this.codiceProfilo);
	try {
	    configurazionePagamentiService.configuraRequestPerEnteCreditore(this.codiceProfilo);
	} catch (PayConfigurationException e) {
	    log.error("Errore nella configurazione della request per codiceprofilo " + this.codiceProfilo, e);
	    return newEsitoConErrore(e);
	}
	ParamsSFTP p = new ParamsSFTP(this.ftpUSer, this.ftpPassword, this.serverFTP, this.portFTP, false);
	log.debug("Creo il client SFTP host {} per accedere alla cartella {}", this.serverFTP, this.rootfolderFTP);
	IFileHelper fHelper = new SFTPHelper(p);
	List<EsitoElaborazione> esiti = new ArrayList<>();
	try {
	    log.debug("Apro la connessione al server sftp {} codiceprofilo {}", this.serverFTP, this.codiceProfilo);
	    fHelper.open();
	    log.debug("Connessione aperta al server sftp {} codiceprofilo {} verifico se presenti file", this.serverFTP, this.codiceProfilo);
	    Set<File> files = fHelper.mGetFilesByFilter(".xml", this.rootfolderFTP);
	    for (File file : files) {
		String name = file.getName();
		log.debug("File {} nella cartella {} pronto per elaborazione", name, this.rootfolderFTP);
		EsitoElaborazione esito = elaboraFile(fHelper, name);
		esiti.add(esito);
		if (esito.isEsito()) {
		    fHelper.sposta(name, this.rootfolderFTP, this.elaboratiFolder);
		} else {
		    log.error("Errore nell'elaborazione del file {} name. Errore: {}", name, esito.getMessaggio());
		    fHelper.sposta(name, this.rootfolderFTP, this.erroriElaborazioneFolder);
		}
	    }
	    log.debug("Chiudo la connessione al server sftp {} codiceprofilo {}", this.serverFTP, this.codiceProfilo);
	    fHelper.close();
	    log.debug("Connessione chiusa al server sftp {} codiceprofilo {}", this.serverFTP, this.codiceProfilo);
	} catch (IOException e) {
	    log.error("Errore nell'elaborazione dei file per profilo " + this.codiceProfilo + " nel server " + this.serverFTP, e);
	    return newEsitoConErrore(e);
	}
	return new EsitoElaborazione(true, null);
    }

    private EsitoElaborazione elaboraFile(IFileHelper fHelper, String name) {

	boolean esito = true;
	String msg = "";
	try {
	    FlussoRiversamento flusso = leggiFile(fHelper, name);
	    String idFlusso = flusso.getIdentificativoFlusso();
	    //	    Date dataOraFlusso = flusso.getDataOraFlusso();
	    //	    String dataRegolamento = flusso.getDataRegolamento();
	    //	    String identificativoUnivocoRegolamento = flusso.getIdentificativoUnivocoRegolamento();
	    //	    Double importoTotalePagamenti = flusso.getImportoTotalePagamenti();
	    //	    Integer numeroTotalePagamenti = flusso.getNumeroTotalePagamenti();
	    //	    String versioneOggetto = flusso.getVersioneOggetto();
	    IstitutoMittente istitutoMittente = flusso.getIstitutoMittente();
	    String ragioneSocialePsp = istitutoMittente.getDenominazioneMittente();
	    IdentificativoUnivocoIstituto idUnMittente = istitutoMittente.getIdentificativoUnivocoMittente();
	    String idPsp = null;
	    if (idUnMittente != null) {
		idPsp = idUnMittente.getCodiceIdentificativoUnivoco();
	    }
	    List<DatiSingoloPagamento> datiSingoliPagamenti = flusso.getDatiSingoliPagamenti();
	    for (DatiSingoloPagamento dp : datiSingoliPagamenti) {
		try {
		    elaboraPagamento(dp, idFlusso, ragioneSocialePsp, idPsp);
		} catch (PayException e) {
		    esito = false;
		    msg += "Errore nell'elaborazione del pagamento con iuv " + dp.getIdentificativoUnivocoVersamento() + ". " + e.getMessage();
		}
	    }
	} catch (IOException e) {
	    log.error("Errore nell'elaborazione del file name", e);
	    esito = false;
	    msg = e.getMessage();
	}
	return new EsitoElaborazione(esito, msg);
    }

    private void elaboraPagamento(DatiSingoloPagamento dp, String idFlusso, String ragioneSocialePsp, String idPsp) throws PayException {

	// legge i tracciati PAGOPA
	// verifica dalle posizioni debitorie se sono state pagate o meno
	// se non sono state pagate allora registra come pagate con i dati a disposizione
	String iuv = dp.getIdentificativoUnivocoVersamento();
	String iur = dp.getIdentificativoUnivocoRiscossione();
	Integer codiceEsito = dp.getCodiceEsitoSingoloPagamento();
	Double singoloImportoPagato = dp.getSingoloImportoPagato();
	PayPosizioniDebitorie pos = payPosizioniDebitorieService.findByIUV(iuv);
	if (pos == null) {
	    log.error("Posizione debitoria non trovata per IUV {} e dati pagamento {}", iuv, dp);
	    return;
	}
	Integer payPosId = pos.getId().getCodice();
	log.debug("Elaboro il pagamento per la posizione {}, datiPagamento {}", payPosId, dp);
	if (codiceEsito.equals(3)) {
	    log.error("Il pagamento per la posizione {}, datiPagamento {} ha tornato codiceEsito = 3 segno la posizione come non pagata", payPosId,
		    dp);
	    this.payPosizioniDebitorieService.aggiornaPosizioneDebitoria(pos, StatiPagamento.CON_ERRORE,
		    "Pagamento revocato dal PSP ricevuto codice esito 3");
	    return;
	}
	Set<PayPagamenti> pagamenti = payPagamentiService.findByIdPosizioneDebitoria(payPosId);
	if (pagamenti.isEmpty()) {
	    PayPagamenti datiPag = new PayPagamenti();
	    Date dataPagamento = Calendar.getInstance().getTime();
	    try {
		dataPagamento = Utilities.getDate(dp.getDataEsitoSingoloPagamento(), "YYYY-MM-DD");
	    } catch (Exception e) {
		log.error("Errore nella conversione della data pagamento " + dp.getDataEsitoSingoloPagamento(), e);
	    }
	    datiPag.setDataSistema(Calendar.getInstance().getTime());
	    datiPag.setDataPagamento(dataPagamento);
	    datiPag.setPosizioneDebitoria(pos);
	    datiPag.setImportoPagato(BigDecimal.valueOf(singoloImportoPagato));
	    datiPag.setIdFlussoRendicontazione(idFlusso);
	    datiPag.setIdPsp(idPsp);
	    datiPag.setRagSocPsp(ragioneSocialePsp);
	    datiPag.setIur(iur);
	    log.debug("Registro l'avvenuto pagamento per la posizione {}, datiPagamento {}", payPosId, dp);
	    this.payPagamentiService.registraAvvenutoPagamento(datiPag, pos, null);
	}
    }

    private FlussoRiversamento leggiFile(IFileHelper fHelper, String name) throws IOException {

	InputStream inputStream = fHelper.get(name, "");
	String xml = org.apache.commons.io.IOUtils.toString(inputStream, StandardCharsets.UTF_8);
	try {
	    if (inputStream != null) {
		inputStream.close();
	    }
	} catch (Exception e) {
	    log.error("leggiFile - impossibile chiudere il file : {}", name);
	}
	return (FlussoRiversamento) IOUtils.unMarshallString(xml, FlussoRiversamento.class);
    }

    private EsitoElaborazione newEsitoConErrore(Exception e) {

	log.error("", e);
	return new EsitoElaborazione(false, e.getMessage());
    }
}
