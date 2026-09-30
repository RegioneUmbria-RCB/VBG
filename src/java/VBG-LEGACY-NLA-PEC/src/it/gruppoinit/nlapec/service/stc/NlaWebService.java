package it.gruppoinit.nlapec.service.stc;

import it.gruppoinit.nlapec.service.PECReader;
import it.gruppoinit.nlapec.service.PECSender;
import it.gruppoinit.nlapec.service.mailtipo.MailtipoWSClient;
import it.gruppoinit.nlapec.service.sigeprosecurity.SigeproSecurityWebServiceClient;
import it.gruppoinit.nlapec.util.NLAPecConstant;
import it.gruppoinit.sigepro.schemas.messages.mailtipo.MailtipoResponse;
import it.init.sigepro.rte.AggiungiDocumentiNLARequest;
import it.init.sigepro.rte.AggiungiDocumentiNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.TestNLARequest;
import it.init.sigepro.rte.TestNLAResponse;
import it.init.sigepro.rte.XsdNlaVersion;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DettaglioPraticaVisuraType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.RiferimentiAllegatoType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.StatoPraticaType;
import it.init.sigepro.rte.types.ValoreParametroType;
import it.init.sigepro.rte.types.XsdTypesVersion;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

@Endpoint
public class NlaWebService {

    private static final Logger log = LoggerFactory.getLogger(NlaWebService.class);
    private static final String MESSAGES_NAMESPACE = "http://sigepro.init.it/rte";
    private static final String INSERIMENTO_ATTIVITA = "InserimentoAttivitaNLARequest";
    private static final String INSERIMENTO_PRATICA = "InserimentoPraticaNLARequest";
    private static final String RICHIESTA_PRATICA = "RichiestaPraticaNLARequest";
    private static final String AGGIUNGI_DOCUMENTI = "AggiungiDocumentiNLARequest";
    private static final String ALLEGATO_BINARIO = "AllegatoBinarioNLARequest";
    private static final String TEST_NLA = "TestNLARequest";
    private StcWebServiceClientPECSender stcWebServiceClient;
    private MailtipoWSClient mailtipoWSClient;
    private PECSender pecSender;
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;
    private String mailServiceWsUrl;

    @PayloadRoot(localPart = INSERIMENTO_ATTIVITA, namespace = MESSAGES_NAMESPACE)
    public InserimentoAttivitaNLAResponse inserimentoAttivita(InserimentoAttivitaNLARequest request) {

	throw new RuntimeException("NLA-PEC inserimentoAttivita: Metodo non implementato");
    }

    @PayloadRoot(localPart = AGGIUNGI_DOCUMENTI, namespace = MESSAGES_NAMESPACE)
    public AggiungiDocumentiNLAResponse aggiungiDocumentiNLA(AggiungiDocumentiNLARequest aggiungiDocumentiNLARequest) {

	throw new RuntimeException("NLA-PEC aggiungiDocumentiNLA: Metodo non implementato");
    }

    /**
     * nel caso che il nodo venga chiamato da più enti devo tornare riferimenti univoci
     * 
     * @param request
     * @return
     */
    private String getIdPratica(InserimentoPraticaNLARequest request) {

	return request.getSportelloMittente().getIdEnte() + "-" + request.getDettaglioPratica().getIdPratica();
    }

    /**
     * nel caso che il nodo venga chiamato da più enti devo tornare riferimenti univoci
     * 
     * @param req
     * @return
     */
    private String getNumeroPratica(InserimentoPraticaNLARequest req) {

	return req.getSportelloMittente().getIdEnte() + "-" + req.getSportelloMittente().getIdSportello() + "-"
		+ req.getDettaglioPratica().getNumeroPratica();
    }

    @PayloadRoot(localPart = INSERIMENTO_PRATICA, namespace = MESSAGES_NAMESPACE)
    public InserimentoPraticaNLAResponse inserimentoPratica(InserimentoPraticaNLARequest request) {

	Integer codiceMailTipo = null;
	if (request.getDettaglioPratica() != null) {
	    if (request.getDettaglioPratica().getAltriDati() != null) {
		if (request.getDettaglioPratica().getAltriDati().size() > 0) {
		    ValoreParametroType vp = getCampoDaAltriDati(request.getDettaglioPratica().getAltriDati(), "$MAILTIPO$");
		    if (vp != null) {
			String codice = StringUtils.defaultString(vp.getCodice()).trim();
			if (isInteger(codice)) {
			    codiceMailTipo = Integer.valueOf(codice);
			}
		    }
		}
	    }
	}
	MailtipoResponse mailtipo = null;
	boolean isDpr160Art5Comma5 = true;
	Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
	String url = PECReader.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILTIPO_WSDL);
	String token = sigeproSecurityWebServiceClient.loginAPP(request.getSportelloMittente().getIdEnte());
	if (codiceMailTipo != null) {
	    try {
		mailtipo = mailtipoWSClient.mailtipoFrontend(url, token, codiceMailTipo.intValue(), request.getDettaglioPratica());
		isDpr160Art5Comma5 = false;
	    } catch (Exception e) {
		log.error("inserimentoPratica: mailtipoWSClient.mailtipoFrontend()", e);
		throw new RuntimeException(e);
	    }
	}
	InserimentoPraticaNLAResponse response = new InserimentoPraticaNLAResponse();
	try {
	    // fabrizioc:(sp2con) recupero da deploy.properties l'url del
	    // mailservice se questo è vuoto allora utilizzo quello del security
	    if (StringUtils.isBlank(mailServiceWsUrl)) {
		Map<String, String> params = sigeproSecurityWebServiceClient.getParams("WSHOSTURL_MAILSERVICE");
		mailServiceWsUrl = params.get("WSHOSTURL_MAILSERVICE");
	    }
	    getBinaryAttachments(request);
	    pecSender.sendPECMessage(request, mailtipo, isDpr160Art5Comma5, token, mailServiceWsUrl);
	    RiferimentiPraticaType riferimentiPraticaType = new RiferimentiPraticaType();
	    riferimentiPraticaType.setIdPratica(getIdPratica(request));
	    riferimentiPraticaType.setNumeroPratica(getNumeroPratica(request));
	    response.setDettaglioPratica(riferimentiPraticaType);
	} catch (Exception e) {
	    log.error("inserimentoPratica", e);
	    ErroreType et = new ErroreType();
	    et.setDescrizione(e.getMessage());
	    et.setNumeroErrore("[NLA-PEC]");
	    response.getDettaglioErrore().add(et);
	}
	return response;
    }

    public ValoreParametroType getCampoDaAltriDati(List<ParametroType> altriDati, String nomeParametroType) {

	log.debug("Cerco nella sezione altri dati il valore con ParametroType.nome {} ", nomeParametroType);
	ValoreParametroType valoreParametroType = null;
	Iterator<ParametroType> it = altriDati.iterator();
	log.debug("Itero tutti i valori presenti nella sezioni altri dati");
	while (it.hasNext()) {
	    ParametroType parametroType = (ParametroType) it.next();
	    if (parametroType != null && StringUtils.isNotBlank(parametroType.getNome()) && parametroType.getNome().equals(nomeParametroType)) {
		log.debug("Trovato campo di Altri dati con ParametroType.nome {}", nomeParametroType);
		List<ValoreParametroType> valoreParametroTypes = parametroType.getValore();
		if (valoreParametroTypes != null && !valoreParametroTypes.isEmpty()) {
		    log.debug("Trovato campo di Altri dati con ParametroType.nome con valoreParametroType non vuoto");
		    valoreParametroType = new ValoreParametroType();
		    valoreParametroType = valoreParametroTypes.get(0);
		    log.debug("Valore trovato, fine iterazione lista");
		    break;
		}
	    }
	}
	return valoreParametroType;
    }

    @PayloadRoot(localPart = RICHIESTA_PRATICA, namespace = MESSAGES_NAMESPACE)
    public RichiestaPraticaNLAResponse richiestaPratica(RichiestaPraticaNLARequest request) {

	RichiestaPraticaNLAResponse response = new RichiestaPraticaNLAResponse();
	DettaglioPraticaVisuraType dettaglioVisuraPratica = new DettaglioPraticaVisuraType();
	dettaglioVisuraPratica.setStatoPratica(StatoPraticaType.ATTIVA);
	DettaglioPraticaType dettaglioPratica = new DettaglioPraticaType();
	dettaglioPratica.setIdPratica(request.getRifPratica().getIdPratica());
	dettaglioPratica.setNumeroPratica(request.getRifPratica().getIdPratica());
	GregorianCalendar gregorianCalendar = new GregorianCalendar();
	try {
	    DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
	    XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
	    dettaglioPratica.setDataPratica(now);
	} catch (Exception e) {
	}
	dettaglioPratica.setOggetto("......");
	// inserito solo per formdinamici in modo da capire che questo metodo è
	// finto
	dettaglioPratica.setCodicePraticaTelematica("N");
	dettaglioVisuraPratica.setDettaglioPratica(dettaglioPratica);
	response.setDettaglioPratica(dettaglioVisuraPratica);
	return response;
    }

    @PayloadRoot(localPart = ALLEGATO_BINARIO, namespace = MESSAGES_NAMESPACE)
    public AllegatoBinarioNLAResponse allegatoBinario(AllegatoBinarioNLARequest request) {

	throw new RuntimeException("NLA-PEC allegatoBinario: Metodo non implementato");
    }

    @PayloadRoot(localPart = TEST_NLA, namespace = MESSAGES_NAMESPACE)
    public TestNLAResponse testNLA(TestNLARequest request) {

	TestNLAResponse response = new TestNLAResponse();
	response.setNlaXsdVersion(XsdNlaVersion.V_1_13);
	response.setTypesXsdVersion(XsdTypesVersion.V_1_13);
	log.info("testNLA: nla.xsd={}, types.xsd={}", response.getNlaXsdVersion(), response.getTypesXsdVersion());
	return response;
    }

    private void getBinaryAttachments(InserimentoPraticaNLARequest request) throws Exception {

	String token = stcWebServiceClient.login();
	if (request.getDettaglioPratica().getDocumenti() != null) {
	    for (DocumentiType doc : request.getDettaglioPratica().getDocumenti()) {
		if (doc.getAllegati() != null && StringUtils.isNotBlank(doc.getAllegati().getId())) {
		    if (doc.getAllegati().getFile() == null || doc.getAllegati().getFile().getBinaryData() == null) {
			RiferimentiAllegatoType rifAllegato = new RiferimentiAllegatoType();
			rifAllegato.setIdAllegato(doc.getAllegati().getId());
			rifAllegato.setIdPratica(request.getDettaglioPratica().getIdPratica());
			rifAllegato.setIdDocumento(doc.getId());
			AllegatoBinarioResponse allegatoBinarioResponse = stcWebServiceClient.getAllegatoBinario(token, request, rifAllegato);
			AllegatoBinarioType allegatoBinarioType = new AllegatoBinarioType();
			allegatoBinarioType.setBinaryData(allegatoBinarioResponse.getBinaryData());
			allegatoBinarioType.setFileName(allegatoBinarioResponse.getFileName());
			allegatoBinarioType.setMimeType(allegatoBinarioResponse.getMimeType());
			doc.getAllegati().setFile(allegatoBinarioType);
		    }
		}
	    }
	}
    }

    public void setStcWebServiceClient(StcWebServiceClientPECSender stcWebServiceClient) {

	this.stcWebServiceClient = stcWebServiceClient;
    }

    public void setMailtipoWSClient(MailtipoWSClient mailtipoWSClient) {

	this.mailtipoWSClient = mailtipoWSClient;
    }

    public void setSigeproSecurityWebServiceClient(SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient) {

	this.sigeproSecurityWebServiceClient = sigeproSecurityWebServiceClient;
    }

    public void setPecSender(PECSender pecSender) {

	this.pecSender = pecSender;
    }

    /**
     * Controlla se la string passata rappresenta un numero intero (positivo o negativo): nel controllo la stringa viene
     * ripulita degli spazi a destra e sinistra (TRIM)
     * 
     * @param str
     * @return
     */
    private boolean isInteger(String str) {

	if (StringUtils.isBlank(StringUtils.defaultString(str).trim())) {
	    return false;
	}
	return StringUtils.defaultString(str).trim().matches("^-?(\\d)+$");
    }

    public String getMailServiceWsUrl() {

	return mailServiceWsUrl;
    }

    public void setMailServiceWsUrl(String mailServiceWsUrl) {

	this.mailServiceWsUrl = mailServiceWsUrl;
    }
}
