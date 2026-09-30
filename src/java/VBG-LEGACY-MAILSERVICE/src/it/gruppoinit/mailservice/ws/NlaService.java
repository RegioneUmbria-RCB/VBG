package it.gruppoinit.mailservice.ws;

import java.io.InputStream;
import java.util.Properties;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.apache.rampart.RampartMessageData;

import it.gruppoinit.mailservice.schemas.messages.AttachmentType;
import it.gruppoinit.mailservice.schemas.messages.AttachmentsType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.mailservice.schemas.messages.MessageRequest2;
import it.gruppoinit.sigepro.definitions.mailtipo.MailtipoServiceStub;
import it.gruppoinit.sigepro.definitions.mailtipo.MailtipoServiceStub.MailtipoRequest;
import it.gruppoinit.sigepro.definitions.mailtipo.MailtipoServiceStub.MailtipoResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.ApplicationInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.ContestoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoRequest;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.GetApplicationInfoResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginRequest;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceStub.LoginResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.definitions.StcServiceStub;
import it.init.sigepro.rte.definitions.StcServiceStub.AllegatoBinarioRequest;
import it.init.sigepro.rte.definitions.StcServiceStub.AllegatoBinarioResponse;
import it.init.sigepro.rte.definitions.StcServiceStub.DettaglioPraticaType;
import it.init.sigepro.rte.definitions.StcServiceStub.RichiestaPraticaRequest;
import it.init.sigepro.rte.definitions.StcServiceStub.RichiestaPraticaResponse;
import it.init.sigepro.rte.definitions.StcServiceStub.RiferimentiAllegatoType;
import it.init.sigepro.rte.definitions.StcServiceStub.RiferimentiPraticaType;
import it.init.sigepro.rte.definitions.StcServiceStub.SportelloType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;
import it.init.sigepro.rte.types.ValoreParametroType;

public class NlaService {

    private static final String SEGNAPOSTO_MAIL_DESTINATARIO = "$MAIL_DESTINATARIO$";
    private static final String SEGNAPOSTO_MAILTIPO = "$MAILTIPO$";
    private static final String SEGNAPOSTO_MAILTIPO_CHECK_PROT_IST = "$MAILTIPO_CHECK_PROT_IST$";
    public final Log log = LogFactory.getLog(this.getClass());
    private Properties deployProperties;

    public NlaService() {

	try {
	    InputStream is = this.getClass().getClassLoader().getResourceAsStream("deploy.properties");
	    deployProperties = new Properties();
	    deployProperties.load(is);
	    is.close();
	} catch (Exception e) {
	    log.error("Errore durante la lettura del file deploy.properties: " + e.getMessage(), e);
	}
    }

    public InserimentoAttivitaNLAResponse inserimentoAttivitaNLA(InserimentoAttivitaNLARequest req) {

	InserimentoAttivitaNLAResponse resp = new InserimentoAttivitaNLAResponse();
	try {
	    SenderMail2 senderMail = new SenderMail2();
	    MessageRequest2 messageRequest = this.createMail2(req);
	    senderMail.sendMail2(messageRequest);
	} catch (Exception e) {
	    log.error("inserimentoAttivitaNLA: " + e.getMessage(), e);
	    String _err = "Errore durante la notifica attività: " + e.getMessage();
	    ErroreType err = new ErroreType();
	    err.setNumeroErrore("MAILSERVICE");
	    err.setDescrizione(_err);
	    resp.addDettaglioErrore(err);
	    return resp;
	}
	RiferimentiAttivitaType rifAtt = new RiferimentiAttivitaType();
	rifAtt.setIdPratica(req.getDatiAttivita().getIdPratica());
	rifAtt.setIdAttivita(req.getDatiAttivita().getIdAttivita());
	resp.setDettaglioAttivita(rifAtt);
	return resp;
    }

    private MessageRequest2 createMail2(InserimentoAttivitaNLARequest req) throws Exception {

	try {
	    //1. recupero da altriDati il valore del parametro $MAILTIPO$
	    String codiceMailTipo = this.getCodiceMailTipo(req);
	    //2. recupero dal dettaglio della pratica il valore del campo domicilioElettronico da utilizzare come destinatario
	    String securityToken = this.getSecurityToken(req.getSportelloMittente().getIdEnte());
	    String codMov = req.getDatiAttivita().getIdAttivita();
	    //3. recupero la mail tipo dal backend utilizzando il codice ricavato da altriDati
	    MailtipoResponse mailTipo = this.getMailTipo(codiceMailTipo, codMov, securityToken);
	    //4. compongo la mail
	    MessageRequest2 messageRequest = new MessageRequest2();
	    messageRequest.setCodicemovimento(codMov);
	    MailMessageType mail = new MailMessageType();
	    mail.setInviaComeHtml(true);
	    mail.setDestinatari(this.getDestinatario(req));
	    //il mittente lo mette il mailservice recuperandolo dalla conf email del backend
	    //mail.setMittente(req.getSportelloMittente().getPecSportello());
	    mail.setCorpoMail(mailTipo.getCorpo());
	    mail.setOggetto(mailTipo.getOggetto());
	    //setto il message id per il recupero delle ricevute
	    mail.setMessageID(this.generateMessageID(req));
	    mail.setAttachments(this.getAttachments(req));
	    messageRequest.setMailMessage(mail);
	    messageRequest.setSoftware(req.getSportelloMittente().getIdSportello());
	    messageRequest.setToken(securityToken);
	    return messageRequest;
	} catch (Exception e) {
	    log.error("createMail: " + e.getMessage(), e);
	    throw new Exception("Errore durante la creazione della mail: " + e.getMessage());
	}
    }

    private String getDestinatario(InserimentoAttivitaNLARequest req) throws Exception {

	try {
	    ParametroType[] altriDati = req.getDatiAttivita().getAltriDati();
	    if (altriDati != null) {
		for (int i = 0; i < altriDati.length; i++) {
		    if (NlaService.SEGNAPOSTO_MAIL_DESTINATARIO.equals(altriDati[i].getNome())) {
			ValoreParametroType[] vals = altriDati[i].getValore();
			if (vals != null && vals.length > 0) {
			    return vals[0].getCodice();
			}
		    }
		}
	    }
	    return this.getDomicilioElettronico(req);
	} catch (Exception e) {
	    log.error("getDestinatario: " + e.getMessage(), e);
	    throw new Exception("Errore durante il recupero del destinatario della mail: " + e.getMessage());
	}
    }

    private void verificaIstanzaProtocollata(InserimentoAttivitaNLARequest req, DettaglioPraticaType dettaglioPraticaType) {

	String checkProt = "";
	ParametroType[] altriDati = req.getDatiAttivita().getAltriDati();
	if (altriDati != null) {
	    for (int i = 0; i < altriDati.length; i++) {
		if (NlaService.SEGNAPOSTO_MAILTIPO_CHECK_PROT_IST.equals(altriDati[i].getNome())) {
		    ValoreParametroType[] vals = altriDati[i].getValore();
		    if (vals != null && vals.length > 0) {
			checkProt = StringUtils.defaultString(vals[0].getCodice(), "N").trim();
		    }
		    break;
		}
	    }
	}
	if ("S".equalsIgnoreCase(checkProt)) {
	    String protocollo = StringUtils.defaultString(dettaglioPraticaType.getNumeroProtocolloGenerale()).trim();
	    if (StringUtils.isBlank(protocollo)) {
		log.error("createMail: L'istanza non risulta protocollata");
		throw new RuntimeException("L'istanza non risulta protocollata e la configurazione del movimento richiede che lo sia.");
	    }
	}
    }

    private String getDomicilioElettronico(InserimentoAttivitaNLARequest req) throws Exception {

	try {
	    StcServiceStub stcServiceStub = new StcServiceStub(deployProperties.getProperty("ws.stc.url"));
	    RichiestaPraticaRequest richiestaPraticaRequest = new RichiestaPraticaRequest();
	    SportelloType mitt = new SportelloType();
	    SportelloType dest = new SportelloType();
	    mitt.setIdEnte(req.getSportelloDestinatario().getIdEnte());
	    mitt.setIdNodo(req.getSportelloDestinatario().getIdNodo());
	    mitt.setIdSportello(req.getSportelloDestinatario().getIdSportello());
	    dest.setIdEnte(req.getSportelloMittente().getIdEnte());
	    dest.setIdNodo(req.getSportelloMittente().getIdNodo());
	    dest.setIdSportello(req.getSportelloMittente().getIdSportello());
	    richiestaPraticaRequest.setSportelloDestinatario(dest);
	    richiestaPraticaRequest.setSportelloMittente(mitt);
	    richiestaPraticaRequest.setToken(req.getToken());
	    RiferimentiPraticaType rif = new RiferimentiPraticaType();
	    rif.setIdPratica(req.getRifPraticaMittente().getIdPratica());
	    richiestaPraticaRequest.setRifPratica(rif);
	    RichiestaPraticaResponse resp = stcServiceStub.richiestaPratica(richiestaPraticaRequest);
	    String domicilioElettronico = resp.getDettaglioPratica().getDettaglioPratica().getDomicilioElettronico();
	    if (StringUtils.isBlank(domicilioElettronico)) {
		throw new Exception("Il campo DomicilioElettronico di DettaglioPratica è vuoto");
	    }
	    //0. verifico se istanza protocollata
	    this.verificaIstanzaProtocollata(req, resp.getDettaglioPratica().getDettaglioPratica());
	    return domicilioElettronico;
	} catch (Exception e) {
	    log.error("getDomicilioElettronico: " + e.getMessage(), e);
	    throw new Exception("Errore durante il recupero del domicilio elettronico dalla pratica: " + e.getMessage());
	}
    }

    private String getCodiceMailTipo(InserimentoAttivitaNLARequest req) throws Exception {

	try {
	    String codiceMailTipo = "";
	    ParametroType[] altriDati = req.getDatiAttivita().getAltriDati();
	    if (altriDati != null) {
		for (int i = 0; i < altriDati.length; i++) {
		    if (NlaService.SEGNAPOSTO_MAILTIPO.equals(altriDati[i].getNome())) {
			ValoreParametroType[] vals = altriDati[i].getValore();
			if (vals != null && vals.length > 0) {
			    codiceMailTipo = vals[0].getCodice();
			}
			break;
		    }
		}
	    }
	    if (StringUtils.isBlank(codiceMailTipo)) {
		throw new Exception("Nella sezione AltriDati di DatiAttivita non è presente il parametro " + SEGNAPOSTO_MAILTIPO);
	    }
	    return codiceMailTipo;
	} catch (Exception e) {
	    log.error("getCodiceMailTipo: " + e.getMessage(), e);
	    throw new Exception("Errore durante il recupero del parametro di mapping " + SEGNAPOSTO_MAILTIPO + ": " + e.getMessage());
	}
    }

    private String getSecurityToken(String idcomunealias) throws Exception {

	try {
	    SigeproSecurityServiceStub security = new SigeproSecurityServiceStub(deployProperties.getProperty("ws.token.url"));
	    LoginRequest req = new LoginRequest();
	    req.setAlias(idcomunealias);
	    req.setContesto(ContestoType.APP);
	    req.setUsername("");
	    req.setPassword("");
	    req.setIpAddress("");
	    this.setSecurityHeader(security._getServiceClient());
	    LoginResponse resp = security.login(req);
	    return resp.getToken();
	} catch (Exception e) {
	    log.error("Errore durante la login al security: " + e.getMessage(), e);
	    throw new Exception("Errore durante la login al security: " + e.getMessage());
	}
    }

    private void setSecurityHeader(ServiceClient client) throws Exception {

	Options options = client.getOptions();
	client.engageModule("rampart");
	ClassLoader loader = NlaService.class.getClassLoader();
	InputStream resource = loader.getResourceAsStream("policy.xml");
	StAXOMBuilder builder = new StAXOMBuilder(resource);
	Policy policy = PolicyEngine.getPolicy(builder.getDocumentElement());
	options.setProperty(RampartMessageData.KEY_RAMPART_POLICY, policy);
	options.setUserName(deployProperties.getProperty("ws.token.user"));
	options.setPassword(deployProperties.getProperty("ws.token.pwd"));
    }

    private MailtipoResponse getMailTipo(String codiceMailTipo, String codiceMovimento, String securityToken) throws Exception {

	try {
	    SigeproSecurityServiceStub security = new SigeproSecurityServiceStub(deployProperties.getProperty("ws.token.url"));
	    this.setSecurityHeader(security._getServiceClient());
	    GetApplicationInfoRequest appReq = new GetApplicationInfoRequest();
	    appReq.setParam("WSHOSTURL_JAVA");
	    GetApplicationInfoResponse appResp = security.getApplicationInfo(appReq);
	    ApplicationInfoType[] appInfo = appResp.getApplicationInfo();
	    String backendWSURL = appInfo[0].getValue();
	    MailtipoServiceStub mailtipoServiceStub = new MailtipoServiceStub(backendWSURL + "/services/mailtipo?wsdl");
	    MailtipoRequest mailtReq = new MailtipoRequest();
	    mailtReq.setCodicemailtipo(Integer.parseInt(codiceMailTipo));
	    mailtReq.setCodicemovimento(Integer.parseInt(codiceMovimento));
	    mailtReq.setToken(securityToken);
	    MailtipoResponse mailtResp = mailtipoServiceStub.mailtipo(mailtReq);
	    return mailtResp;
	} catch (Exception e) {
	    log.error("getMailTipo: " + e.getMessage(), e);
	    throw new Exception("Errore durante il recupero della mail tipo: " + e.getMessage());
	}
    }

    private AttachmentsType getAttachments(InserimentoAttivitaNLARequest req) throws Exception {

	try {
	    StcServiceStub stcServiceStub = new StcServiceStub(deployProperties.getProperty("ws.stc.url"));
	    SportelloType mitt = new SportelloType();
	    SportelloType dest = new SportelloType();
	    mitt.setIdEnte(req.getSportelloDestinatario().getIdEnte());
	    mitt.setIdNodo(req.getSportelloDestinatario().getIdNodo());
	    mitt.setIdSportello(req.getSportelloDestinatario().getIdSportello());
	    dest.setIdEnte(req.getSportelloMittente().getIdEnte());
	    dest.setIdNodo(req.getSportelloMittente().getIdNodo());
	    dest.setIdSportello(req.getSportelloMittente().getIdSportello());
	    AttachmentsType atts = new AttachmentsType();
	    DocumentiType[] docs = req.getDatiAttivita().getDocumenti();
	    if (docs != null) {
		for (int i = 0; i < docs.length; i++) {
		    DocumentiType doc = docs[i];
		    if (doc.getAllegati() != null) {
			if (StringUtils.isNotBlank(doc.getAllegati().getId())) {
			    AllegatoBinarioRequest allBinReq = new AllegatoBinarioRequest();
			    allBinReq.setSportelloDestinatario(dest);
			    allBinReq.setSportelloMittente(mitt);
			    allBinReq.setToken(req.getToken());
			    RiferimentiAllegatoType rifAllBin = new RiferimentiAllegatoType();
			    rifAllBin.setIdAllegato(doc.getAllegati().getId());
			    rifAllBin.setIdAttivita(req.getDatiAttivita().getIdAttivita());
			    rifAllBin.setIdPratica(req.getDatiAttivita().getIdPratica());
			    rifAllBin.setIdDocumento(doc.getId());
			    allBinReq.setRiferimentiAllegato(rifAllBin);
			    AllegatoBinarioResponse allBinResp = stcServiceStub.allegatoBinario(allBinReq);
			    AttachmentType att = new AttachmentType();
			    att.setDescrizione(doc.getDocumento());
			    att.setBinaryData(allBinResp.getBinaryData());
			    att.setFileName(allBinResp.getFileName());
			    att.setMimeType(allBinResp.getMimeType());
			    atts.addAttachment(att);
			}
		    }
		}
	    }
	    return atts;
	} catch (Exception e) {
	    log.error("getAttachments: " + e.getMessage(), e);
	    throw new Exception("Errore durante il recupero degli allegati: " + e.getMessage());
	}
    }

    private String generateMessageID(InserimentoAttivitaNLARequest req) {

	StringBuffer b = new StringBuffer("NLA-MAILSERVICE");
	b.append("-");
	b.append(req.getSportelloMittente().getIdNodo());
	b.append("-");
	b.append(req.getSportelloMittente().getIdEnte());
	b.append("-");
	b.append(req.getSportelloMittente().getIdSportello());
	b.append("-");
	b.append(req.getRifPraticaMittente().getIdPratica());
	b.append("-");
	b.append(req.getDatiAttivita().getIdAttivita());
	b.append("-");
	b.append(System.currentTimeMillis());
	return b.toString();
    }
}
