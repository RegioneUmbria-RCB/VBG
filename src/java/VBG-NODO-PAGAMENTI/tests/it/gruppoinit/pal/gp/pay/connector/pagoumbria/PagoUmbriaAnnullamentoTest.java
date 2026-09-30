package it.gruppoinit.pal.gp.pay.connector.pagoumbria;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.ComunicazionePosizioniDebitorieOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.IdpAllineamentoPendenzeEnteOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.IdpAllineamentoPendenzeEnteOTFEsito;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.Destinatari;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.Destinatario;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.DettaglioImporto;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.DettaglioPagamentoInsertReplace;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.IdpAllineamentoPendenzeOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.IdpBody;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.Mittente;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.Pendenza;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.PendenzaInsertReplace;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.PendenzaInsertReplace.InfoPagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.VoceImporto;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.Dettaglio;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.Esito;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.IdpEsitoOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.InfoMessaggio;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito.StatoMessaggio;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.E2EReceiver;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.E2ESender;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.HeaderE2E;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.HeaderTRT;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.IdpHeader;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.IdpOTF;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.ServiceName;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.TRTReceiver;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header.TRTSender;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.Divisa;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.StatoPagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.StatoPendenza;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.TipoDestinatario;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.TipoOperazione;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.include.TipoPagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv.GeneraIUVRequest;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv.GeneraIUVResponseType;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generaiuv.GenerazioneIUV;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;

public class PagoUmbriaAnnullamentoTest {

    private static final String RECEIVER_ID = "PAGOUMBRIA";
    private static final String RECEIVER_SYS = "SIL_PAGOUMBRIA_ITR";
    private static final String VERSIONE = "01.03-02";
    private static final String DEFAULT_TIPO_VOCE_IMPORTO = "ONERI";
    private static final String DEFAULT_CODICE_VOCE_IMPORTO = "000";
    private static final int ANNI_VALIDITA_PAGAMENTO = 10;
    PagoUmbriaConfigurazioneParams p = null;

    public PagoUmbriaAnnullamentoTest(PagoUmbriaConfigurazioneParams p2) {

	this.p = p2;
    }

    public static void main(String[] args) throws PayException {

	PagoUmbriaAnnullamentoTest t = new PagoUmbriaAnnullamentoTest(new PagoUmbriaConfigurazioneParams());
    }

    private void testaAnnullamento() throws PayException {

	IdpAllineamentoPendenzeEnteOTFEsito response = registraPosizioni();
	gestisciVerificaErroreEsitoRegistrazionePosizione(response.getIdpEsitoOTF());
	response = pagaPrimaRata();
	gestisciVerificaErroreEsitoRegistrazionePosizione(response.getIdpEsitoOTF());
	response = annullaSecondaRata();
	gestisciVerificaErroreEsitoRegistrazionePosizione(response.getIdpEsitoOTF());
	response = pagaOfflineTerzaRata();
	gestisciVerificaErroreEsitoRegistrazionePosizione(response.getIdpEsitoOTF());
	response = annullaPendenza();
	gestisciVerificaErroreEsitoRegistrazionePosizione(response.getIdpEsitoOTF());
    }

    private void testaInserimento() throws PayException {

	IdpAllineamentoPendenzeEnteOTFEsito response = registraPosizioni();
	gestisciVerificaErroreEsitoRegistrazionePosizione(response.getIdpEsitoOTF());
    }

    private void testaPagaPrimaRata() throws PayException {

	IdpAllineamentoPendenzeEnteOTFEsito response = pagaPrimaRata();
	gestisciVerificaErroreEsitoRegistrazionePosizione(response.getIdpEsitoOTF());
    }

    private void testaAnnullaPendenza() throws PayException {

	IdpAllineamentoPendenzeEnteOTFEsito response = annullaPendenza();
	gestisciVerificaErroreEsitoRegistrazionePosizione(response.getIdpEsitoOTF());
    }

    private IdpAllineamentoPendenzeEnteOTFEsito annullaPendenza() throws PayException {

	IdpAllineamentoPendenzeEnteOTF requestData = popolaAnnullamantoPendenza();
	ComunicazionePosizioniDebitorieOTF port = getComunicazionePosizioniDebitorieOTFPort();
	return port.idpAllineamentoPendenzeEnteOTF(requestData);
    }

    private IdpAllineamentoPendenzeEnteOTF popolaAnnullamantoPendenza() {

	IdpAllineamentoPendenzeEnteOTF allineamentoPendenzeEnteOTF = new IdpAllineamentoPendenzeEnteOTF();
	IdpAllineamentoPendenzeOTF allineamentoPendenzeOTF = new IdpAllineamentoPendenzeOTF();
	allineamentoPendenzeOTF.setVersione(VERSIONE);
	allineamentoPendenzeEnteOTF.setIdpAllineamentoPendenzeOTF(allineamentoPendenzeOTF);
	//sezione header
	IdpHeader header = this.popolaIdpHeader();
	allineamentoPendenzeOTF.setIdpHeader(header);
	//sezione body con i dati delle posizioni da caricare
	IdpBody body = new IdpBody();
	allineamentoPendenzeOTF.setIdpBody(body);
	//dati della registrazione contabile
	Pendenza pendenza = new Pendenza();
	pendenza.setTipoOperazione(TipoOperazione.DELETE);
	pendenza.setTipoPendenza(p.codiceVersamento);
	Mittente mittente = new Mittente();
	mittente.setId(p.codiceProfiloPsp);
	mittente.setDescrizione(p.amministrazione);
	pendenza.setMittente(mittente);
	Destinatari dests = new Destinatari();
	pendenza.setDestinatari(dests);
	Destinatario dest = createDestinatario();
	dests.getDestinatario().add(dest);
	pendenza.setIdPendenza(p.idPendenza);
	body.getPendenza().add(pendenza);
	return allineamentoPendenzeEnteOTF;
    }

    private IdpAllineamentoPendenzeEnteOTFEsito pagaOfflineTerzaRata() throws PayException {

	// la seconda l'ho rimossa
	IdpAllineamentoPendenzeEnteOTF requestData = popolaInserimentoPendenza(false, false);
	List<Pendenza> pendenza = requestData.getIdpAllineamentoPendenzeOTF().getIdpBody().getPendenza();
	for (Pendenza pend : pendenza) {
	    pend.setTipoOperazione(TipoOperazione.REPLACE);
	    PendenzaInsertReplace insertToReplace = pend.getInsert();
	    pend.setInsert(null);
	    InfoPagamento infoPagamento = insertToReplace.getInfoPagamento().get(0);
	    List<DettaglioPagamentoInsertReplace> dettaglioPagamento = infoPagamento.getDettaglioPagamento();
	    dettaglioPagamento.remove(1);
	    pend.setReplace(insertToReplace);
	}
	ComunicazionePosizioniDebitorieOTF port = getComunicazionePosizioniDebitorieOTFPort();
	return port.idpAllineamentoPendenzeEnteOTF(requestData);
    }

    private IdpAllineamentoPendenzeEnteOTFEsito annullaSecondaRata() throws PayException {

	IdpAllineamentoPendenzeEnteOTF requestData = popolaInserimentoPendenza(false, false);
	List<Pendenza> pendenza = requestData.getIdpAllineamentoPendenzeOTF().getIdpBody().getPendenza();
	for (Pendenza pend : pendenza) {
	    pend.setTipoOperazione(TipoOperazione.REPLACE);
	    PendenzaInsertReplace insertToReplace = pend.getInsert();
	    pend.setInsert(null);
	    InfoPagamento infoPagamento = insertToReplace.getInfoPagamento().get(0);
	    List<DettaglioPagamentoInsertReplace> dettaglioPagamento = infoPagamento.getDettaglioPagamento();
	    dettaglioPagamento.remove(1);
	    pend.setReplace(insertToReplace);
	}
	ComunicazionePosizioniDebitorieOTF port = getComunicazionePosizioniDebitorieOTFPort();
	return port.idpAllineamentoPendenzeEnteOTF(requestData);
    }

    private IdpAllineamentoPendenzeEnteOTFEsito pagaPrimaRata() throws PayException {

	IdpAllineamentoPendenzeEnteOTF requestData = popolaInserimentoPendenza(false, false);
	List<Pendenza> pendenza = requestData.getIdpAllineamentoPendenzeOTF().getIdpBody().getPendenza();
	for (Pendenza pend : pendenza) {
	    pend.setTipoOperazione(TipoOperazione.REPLACE);
	    PendenzaInsertReplace insertToReplace = pend.getInsert();
	    pend.setInsert(null);
	    InfoPagamento infoPagamento = insertToReplace.getInfoPagamento().get(0);
	    List<DettaglioPagamentoInsertReplace> dettaglioPagamento = infoPagamento.getDettaglioPagamento();
	    for (int i = 0; i < dettaglioPagamento.size(); i++) {
		if (i == 0) {
		    DettaglioPagamentoInsertReplace dp = dettaglioPagamento.get(i);
		    dp.setStato(StatoPagamento.PAGATO);
		}
	    }
	    pend.setReplace(insertToReplace);
	}
	ComunicazionePosizioniDebitorieOTF port = getComunicazionePosizioniDebitorieOTFPort();
	return port.idpAllineamentoPendenzeEnteOTF(requestData);
    }

    private IdpAllineamentoPendenzeEnteOTFEsito registraPosizioni() throws PayException {

	IdpAllineamentoPendenzeEnteOTF requestData = popolaInserimentoPendenza(false, true);
	ComunicazionePosizioniDebitorieOTF port = getComunicazionePosizioniDebitorieOTFPort();
	return port.idpAllineamentoPendenzeEnteOTF(requestData);
    }

    private IdpAllineamentoPendenzeEnteOTF popolaInserimentoPendenza(boolean onTheFly, boolean generaIuv) throws PayException {

	IdpAllineamentoPendenzeEnteOTF allineamentoPendenzeEnteOTF = new IdpAllineamentoPendenzeEnteOTF();
	IdpAllineamentoPendenzeOTF allineamentoPendenzeOTF = new IdpAllineamentoPendenzeOTF();
	allineamentoPendenzeOTF.setVersione(VERSIONE);
	allineamentoPendenzeEnteOTF.setIdpAllineamentoPendenzeOTF(allineamentoPendenzeOTF);
	//sezione header
	PayProfiliEntiCreditori enteCfg = PayConfigurationHelper.getProfiloEnteCreditore();
	IdpHeader header = this.popolaIdpHeader();
	allineamentoPendenzeOTF.setIdpHeader(header);
	//sezione OTF Header per pagamenti on the fly
	if (onTheFly) {
	    IdpOTF otf = new IdpOTF();
	    String urlCallback = enteCfg.getUrlEsitoPagamento() + "&id=";
	    otf.setURLBACK(urlCallback);
	    if (StringUtils.isNotBlank(enteCfg.getUrlAnnullamentoPagamento())) {
	    }
	    otf.setURLCANCEL(urlCallback);
	    otf.setOFFLINEPAYMENTMETHODS(false);
	    allineamentoPendenzeOTF.setIdpOTF(otf);
	}
	//sezione body con i dati delle posizioni da caricare
	IdpBody body = new IdpBody();
	allineamentoPendenzeOTF.setIdpBody(body);
	//dati della registrazione contabile
	Pendenza pendenza = null;
	pendenza = createPendenza();
	pendenza.setIdPendenza(p.idPendenza);
	PendenzaInsertReplace insert = new PendenzaInsertReplace();
	pendenza.setInsert(insert);
	insert.setDescrizioneCausale(p.descrizioneCausale);
	insert.setDataCreazione(header.getTRT().getXMLCrtDt());
	Date now = new Date();
	GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
	cal.setTime(now);
	cal.roll(Calendar.YEAR, ANNI_VALIDITA_PAGAMENTO);
	insert.setDataPrescrizione(Utilities.getXMLGregorianCalendar(cal));
	cal = new GregorianCalendar();
	cal.set(Calendar.YEAR, 2023);
	insert.setAnnoRiferimento(Utilities.getXMLGregorianCalendar(cal));
	insert.setDataEmissione(Utilities.getXMLGregorianCalendar(now));
	insert.setDivisa(Divisa.EUR);
	insert.setStato(StatoPendenza.APERTA);
	InfoPagamento infoP = new InfoPagamento();
	infoP.setTipoPagamento(p.rateizzato ? TipoPagamento.PAGAMENTO_A_RATE : TipoPagamento.PAGAMENTO_UNICO);
	insert.getInfoPagamento().add(infoP);
	BigDecimal totRegistrazione = BigDecimal.ZERO;
	GenerazioneIUV generazioneIUVClient = this.getGenerazioneIUVPort();
	GeneraIUVRequest generaIUVRequest = new GeneraIUVRequest();
	generaIUVRequest.setIdentificativoDominio(p.piva);
	generaIUVRequest.setTipoDebito(p.codiceVersamento);
	for (int i = 0; i < p.numposizioni; i++) {
	    DettaglioPagamentoInsertReplace dpir = new DettaglioPagamentoInsertReplace();
	    dpir.setDettaglioImporto(new DettaglioImporto());
	    dpir.setStato(StatoPagamento.NON_PAGATO);
	    dpir.setCausalePagamento(p.descrizioneCausale);
	    if (p.rateizzato) {
		dpir.setCausalePagamento(p.descrizioneCausale + " rata " + i);
	    }
	    cal = (GregorianCalendar) GregorianCalendar.getInstance();
	    if (!onTheFly) {
		cal.setTime(p.dataRegistrazione);
	    } else {
		cal.setTime(now);
		cal.add(Calendar.DATE, -1);
	    }
	    dpir.setDataInizioValidita(Utilities.getXMLGregorianCalendar(cal));
	    cal.roll(Calendar.YEAR, ANNI_VALIDITA_PAGAMENTO);
	    dpir.setDataScadenza(Utilities.getXMLGregorianCalendar(cal));
	    dpir.setDataFineValidita(cal);
	    String iuv = p.prefissoIUV + "000000000000" + i;
	    if (generaIuv) {
		GeneraIUVResponseType generaIUVResponseType = generazioneIUVClient.generaIUV(generaIUVRequest);
		iuv = generaIUVResponseType.getBody().getElencoIdentificativi().getIdentificativoUnivocoVersamento();
	    }
	    dpir.setIdPagamento(iuv);
	    System.out.println("iuv: " + iuv);
	    //scrivo nella posizione debitoria l'id trasmesso al PSP
	    BigDecimal importo = BigDecimal.ONE;
	    VoceImporto voce = new VoceImporto();
	    voce.setImporto(importo);
	    String tipoVoce = DEFAULT_TIPO_VOCE_IMPORTO;
	    voce.setCapitoloBilancio("T525009");
	    voce.setCodice(DEFAULT_CODICE_VOCE_IMPORTO);
	    voce.setTipo(tipoVoce);
	    voce.setDescrizione("Onere pagoumbria della pratica num. 57/2022 TEST SPOLETO");
	    dpir.getDettaglioImporto().getVoce().add(voce);
	    infoP.getDettaglioPagamento().add(i, dpir);
	    dpir.setImporto(importo);
	    totRegistrazione = totRegistrazione.add(importo);
	}
	insert.setImportoTotale(totRegistrazione);
	Destinatari dests = new Destinatari();
	pendenza.setDestinatari(dests);
	Destinatario dest = createDestinatario();
	dests.getDestinatario().add(dest);
	body.getPendenza().add(pendenza);
	return allineamentoPendenzeEnteOTF;
    }

    private Destinatario createDestinatario() {

	Destinatario dest = new Destinatario();
	dest.setTipo(TipoDestinatario.CITTADINO);
	dest.setId("BCCRCR73H23G888O");
	dest.setDescrizione("Bocci Riccardo");
	return dest;
    }

    private IdpHeader popolaIdpHeader() {

	IdpHeader header = new IdpHeader();
	HeaderTRT trt = new HeaderTRT();
	trt.setMsgId(p.getIdmessaggio());
	/*
	 * l'id messaggio deve essere quello di un nuovo PayIoEventi che verrà creato per ogni singola invocazione del
	 * WS del PSP, tutti gli ioeventi di chiamata al PSP devono avere lo stesso codice comunicazione dell'IOEventi
	 * passato come argomento che rappresenta invece l'evento di invocazione iniziale del servizio di caricamento
	 * delle posizioni esposto dal nodo pagamenti
	 */
	//trt.setMsgId(cmd.getIdMessaggio());
	trt.setServiceName(ServiceName.IDP_ALLINEAMENTO_PENDENZE);
	Date now = new Date();
	trt.setXMLCrtDt(Utilities.getXMLGregorianCalendar(now));
	TRTSender sender = new TRTSender();
	sender.setSenderId(p.senderId);
	sender.setSenderSys(p.senderSys);
	trt.setSender(sender);
	TRTReceiver receiver = new TRTReceiver();
	receiver.setReceiverId(RECEIVER_ID);
	receiver.setReceiverSys(RECEIVER_SYS);
	trt.setReceiver(receiver);
	header.setTRT(trt);
	HeaderE2E e2e = new HeaderE2E();
	e2e.setE2EMsgId(trt.getMsgId());
	e2e.setE2ESrvcNm(trt.getServiceName().value());
	E2ESender e2eSender = new E2ESender();
	e2eSender.setE2ESndrId(sender.getSenderId());
	e2eSender.setE2ESndrSys(sender.getSenderSys());
	e2e.setSender(e2eSender);
	E2EReceiver e2eReceiver = new E2EReceiver();
	e2eReceiver.setE2ERcvrId(receiver.getReceiverId());
	e2eReceiver.setE2ERcvrSys(receiver.getReceiverSys());
	e2e.setReceiver(e2eReceiver);
	header.setE2E(e2e);
	return header;
    }

    private Pendenza createPendenza() {

	Pendenza pendenza = new Pendenza();
	pendenza.setTipoOperazione(TipoOperazione.INSERT);
	pendenza.setTipoPendenza(p.codiceVersamento);
	Mittente mittente = new Mittente();
	mittente.setId(p.codiceProfiloPsp);
	mittente.setDescrizione(p.amministrazione);
	pendenza.setMittente(mittente);
	return pendenza;
    }

    private ComunicazionePosizioniDebitorieOTF getComunicazionePosizioniDebitorieOTFPort() throws PayException {

	ComunicazionePosizioniDebitorieOTF info = (ComunicazionePosizioniDebitorieOTF) this.getWSPort(ComunicazionePosizioniDebitorieOTF.class,
		p.wsPortCaricamento);
	return info;
    }

    private GenerazioneIUV getGenerazioneIUVPort() throws PayException {

	GenerazioneIUV info = (GenerazioneIUV) this.getWSPort(GenerazioneIUV.class, p.wsPortIUV);
	return info;
    }

    protected Object getWSPort(Class<?> serviceClass, String url) {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(serviceClass);
	factory.setAddress(url);
	Object port = factory.create();
	Client client = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	httpClientPolicy.setReceiveTimeout(120000);
	conduit.setClient(httpClientPolicy);
	Map<String, List<String>> headers = new HashMap<String, List<String>>();
	headers.put("Authorization", Arrays.asList("Bearer " + p.bearer));
	client.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
	return port;
    }

    private void gestisciVerificaErroreEsitoRegistrazionePosizione(IdpEsitoOTF esitoPsp) throws PayException {

	if (esitoPsp != null) {
	    InfoMessaggio infoMessaggio = esitoPsp.getIdpBody().getInfoMessaggio();
	    //se esito complessivo = con errori ciclo i dettagli e 	    
	    if (infoMessaggio.getStato().equals(StatoMessaggio.ELABORATO_CON_ERRORI)) {
		//per ciascun dettaglio esito con errore recupero l'esito (a uso dl nodo) per idPosizionePSP e aggiorno l'esito a KO e imposto codice e messaggio di errore
		boolean erroreTrovato = false;
		if (infoMessaggio.getEsiti() != null && !infoMessaggio.getEsiti().getEsito().isEmpty()) {
		    List<Esito> esiti = infoMessaggio.getEsiti().getEsito();
		    Esito esito = esiti.get(0);
		    erroreTrovato = true;
		    System.out.println("dettaglio errore nell'operazione per la posizione debitoria  esito.getCodice()  " + esito.getCodice() +
				       ", esito.getDescrizione()" + esito.getDescrizione());
		}
		if (!erroreTrovato && esitoPsp.getIdpBody().getInfoDettaglio() != null
			&& !esitoPsp.getIdpBody().getInfoDettaglio().getDettaglio().isEmpty()) {
		    List<Dettaglio> esitiPsp = esitoPsp.getIdpBody().getInfoDettaglio().getDettaglio();
		    Dettaglio dettaglio = esitiPsp.get(0);
		    if (dettaglio != null) {
			erroreTrovato = true;
			if (dettaglio.getEsiti() != null && !dettaglio.getEsiti().getEsito().isEmpty()) { //N esiti per ogni posizione ?????? prendo il primo
			    Esito esitoPU = dettaglio.getEsiti().getEsito().get(0);
			    System.out.println("dettaglio errore nell'operazione per la posizione debitoria  esito.getCodice()  " +
					       esitoPU.getCodice() + ", esito.getDescrizione()" + esitoPU.getDescrizione());
			} else {
			    System.out.println("dettaglio errore nell'operazione per la posizione debitoria, esito.getDescrizione()" +
					       dettaglio.getStato().value());
			}
		    }
		}
		//se non ci sono dettagli sugli errori riscontrati allora imposto tutti gli esiti a KO e lo stato delle posizioni su CON_ERRORE
		if (!erroreTrovato) {
		    System.out.println("dettaglio errore nell'operazione per la posizione debitoria  - ERRORE GENERICO");
		}
		throw new PayException();
	    } else {
		System.out.println("Elaborata correttamente");
	    }
	}
    }
}

class PagoUmbriaConfigurazioneParams {

    public String getIdmessaggio() {

	return System.currentTimeMillis() + "";
    }

    public String piva = "80000130544";
    String prefissoIUV = "9002";
    String idPendenza = prefissoIUV + "-TST-SPOLETO";
    String codiceVersamento = "PROVE_OTF_1";
    String senderId = "RU";
    String senderSys = "";
    String codiceProfiloPsp = "RU";
    String amministrazione = "SUAPE (test AUA)";
    String descrizioneCausale = "Onere pagoumbria  TEST SPOLETO";
    boolean rateizzato = true;
    Date dataRegistrazione = Calendar.getInstance().getTime();
    String bearer = "";
    String wsPortCaricamento = "/pagoumbriacomunicazioneposizionedebitoria/";
    String wsPortVerifica = "/pagoumbriaverificastatopag/";
    String wsPortIUV = "/pagoumbriagenerazioneiuv/";
    int numposizioni = 1;
}