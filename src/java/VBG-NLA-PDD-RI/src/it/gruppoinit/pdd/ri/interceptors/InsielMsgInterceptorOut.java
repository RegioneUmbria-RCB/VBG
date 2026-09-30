package it.gruppoinit.pdd.ri.interceptors;

import it.gruppoinit.pdd.spcoop.insiel.AccordoServizioType;
import it.gruppoinit.pdd.spcoop.insiel.ComunicazioneBaseType;
import it.gruppoinit.pdd.spcoop.insiel.ComunicazioneType;
import it.gruppoinit.pdd.spcoop.insiel.DestinatarioType;
import it.gruppoinit.pdd.spcoop.insiel.Intestazione;
import it.gruppoinit.pdd.spcoop.insiel.MittenteType;
import it.gruppoinit.pdd.spcoop.insiel.RichiestaType;
import it.gruppoinit.pdd.spcoop.insiel.ServizioType;

import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsielMsgInterceptorOut extends AbstractSoapInterceptor {

    private String nomeMetodo;
    private String idcomunealias;
    private static Logger log = LoggerFactory.getLogger(InsielMsgInterceptorOut.class);
    private static final String INSIEL_SERVICELAYER_NAMESPACE = "http://www.insiel.it/ServiceLayer/";

    //        <servicelayer:Intestazione>
    //        <servicelayer:Richiesta versione="1">
    //                        <servicelayer:Mittente country="it"  orgUnit="orgMitt">FriuliVeneziaGiulia</servicelayer:Mittente>
    //                        <servicelayer:Destinatario country="it"  orgUnit="org" >PAGenericaPortaleImprese</servicelayer:Destinatario>
    //                        <servicelayer:Servizio versione="1" metodo="richiesta-iscrizione-impresa-RI">interazioni-ri</servicelayer:Servizio>
    //                        <servicelayer:Comunicazione>EGOV_IT_ServizioSincrono</servicelayer:Comunicazione>
    //                        <servicelayer:AccordoServizio versione="1">IdentificativoAccordoServizio</servicelayer:AccordoServizio>
    //        </servicelayer:Richiesta>
    //        </servicelayer:Intestazione>
    public InsielMsgInterceptorOut(String nomeMetodo, String idcomunealias) {

	super(Phase.WRITE);
	this.nomeMetodo = nomeMetodo;
	this.idcomunealias = idcomunealias;
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {

	try {
	    ConfigurazioneInsiel cfg = new ConfigurazioneInsiel(idcomunealias);
	    if (log.isDebugEnabled()) {
		log.debug("handleMessage# prima di scrivere gli header INSIEL");
	    }
	    setHeader(message, cfg);
	    if (log.isDebugEnabled()) {
		log.debug("handleMessage# header INSIEL Scritti");
	    }
	} catch (Exception e) {
	    log.debug("handleMessage# Errore nella scrittura degli header INSIEL {}", e);
	}
    }

    //    private static String toStringSE(Element element) {
    //
    //	TransformerFactory tf = TransformerFactory.newInstance();
    //	try {
    //	    Transformer trans = tf.newTransformer();
    //	    StringWriter sw = new StringWriter();
    //	    trans.transform(new DOMSource(element), new StreamResult(sw));
    //	    return sw.toString();
    //	} catch (Exception e) {
    //	    throw new RuntimeException(e);
    //	}
    //    }
    private void setHeader(SoapMessage message, ConfigurazioneInsiel cfg) {

	List<Header> list = message.getHeaders();
	QName q = new QName(INSIEL_SERVICELAYER_NAMESPACE, "Intestazione", "serviceLayer");
	Intestazione intestazione = new Intestazione();
	RichiestaType richiesta = new RichiestaType();
	richiesta.setVersione("1");
	MittenteType mittente = new MittenteType();
	mittente.setCountry(cfg.getMittenteCountry());
	mittente.setOrgUnit(cfg.getMittenteOrgUnit());
	mittente.setValue(cfg.getMittenteText());
	richiesta.setMittente(mittente);
	DestinatarioType destinatario = new DestinatarioType();
	destinatario.setCountry(cfg.getDestinatarioCountry());
	destinatario.setOrgUnit(cfg.getDestinatarioOrgUnit());
	destinatario.setValue(cfg.getDestinatarioText());
	richiesta.setDestinatario(destinatario);
	ServizioType servizio = new ServizioType();
	servizio.setVersione("1");
	servizio.setMetodo(nomeMetodo);
	servizio.setValue(cfg.getServizioText());
	richiesta.setServizio(servizio);
	ComunicazioneType comunicazione = new ComunicazioneType();
	ComunicazioneBaseType cbt = ComunicazioneBaseType.EGOV_IT_SERVIZIO_SINCRONO;
	if (StringUtils.isNotBlank(cfg.getComunicazioneText())) {
	    cbt = ComunicazioneBaseType.fromValue(cfg.getComunicazioneText());
	}
	comunicazione.setValue(cbt);
	richiesta.setComunicazione(comunicazione);
	AccordoServizioType accordoServizio = new AccordoServizioType();
	accordoServizio.setVersione("1");
	accordoServizio.setValue(cfg.getAccordoServizioText());
	richiesta.setAccordoServizio(accordoServizio);
	intestazione.setRichiesta(richiesta);
	JAXBDataBinding dataBinding = null;
	try {
	    dataBinding = new JAXBDataBinding(intestazione.getClass());
	} catch (JAXBException e1) {
	    e1.printStackTrace();
	}
	SoapHeader header = new SoapHeader(q, intestazione, dataBinding);
	list.add(header);
    }
    //    private Header getFVGHeaders(ConfigurazioneInsiel cfg) throws SOAPException {
    //
    //	SOAPFactory sf = SOAPFactory.newInstance();
    //	SOAPElement intestazione = sf.createElement(new QName(INSIEL_SERVICELAYER_NAMESPACE, "Intestazione", "servicelayer"));
    //	SOAPElement richiesta = sf.createElement(new QName(INSIEL_SERVICELAYER_NAMESPACE, "Richiesta", "servicelayer"));
    //	richiesta.addAttribute(new QName("versione"), "1");
    //	// mittente
    //	SOAPElement mittente = sf.createElement(new QName(INSIEL_SERVICELAYER_NAMESPACE, "Mittente", "servicelayer"));
    //	mittente.addAttribute(new QName("country"), cfg.getMittenteCountry());
    //	mittente.addAttribute(new QName("orgUnit"), cfg.getMittenteOrgUnit());
    //	mittente.addTextNode(cfg.getMittenteText());
    //	richiesta.addChildElement(mittente);
    //	// destinatario
    //	SOAPElement destinatario = sf.createElement(new QName(INSIEL_SERVICELAYER_NAMESPACE, "Destinatario", "servicelayer"));
    //	destinatario.addAttribute(new QName("country"), cfg.getDestinatarioCountry());
    //	destinatario.addAttribute(new QName("orgUnit"), cfg.getDestinatarioOrgUnit());
    //	destinatario.addTextNode(cfg.getDestinatarioText());
    //	richiesta.addChildElement(destinatario);
    //	// Servizio
    //	SOAPElement servizio = sf.createElement(new QName(INSIEL_SERVICELAYER_NAMESPACE, "Servizio", "servicelayer"));
    //	servizio.addAttribute(new QName("versione"), "1");
    //	servizio.addAttribute(new QName("metodo"), nomeMetodo);
    //	servizio.addTextNode(cfg.getServizioText());
    //	richiesta.addChildElement(servizio);
    //	// COMUNICAZIONE
    //	SOAPElement comunicazione = sf.createElement(new QName(INSIEL_SERVICELAYER_NAMESPACE, "Comunicazione", "servicelayer"));
    //	comunicazione.addTextNode(cfg.getComunicazioneText());
    //	richiesta.addChildElement(comunicazione);
    //	// accordo servizio
    //	SOAPElement accordoServizio = sf.createElement(new QName(INSIEL_SERVICELAYER_NAMESPACE, "AccordoServizio", "servicelayer"));
    //	accordoServizio.addAttribute(new QName("versione"), "1");
    //	accordoServizio.addTextNode(cfg.getAccordoServizioText());
    //	richiesta.addChildElement(accordoServizio);
    //	//
    //	intestazione.addChildElement(richiesta);
    //	Header serviceLayerHeader = new Header(new QName(INSIEL_SERVICELAYER_NAMESPACE, "Intestazione"), intestazione);
    //	return serviceLayerHeader;
    //    }
    //    public void handleMessage2(SoapMessage message) throws Fault {
    //
    //	try {
    //	    ConfigurazioneInsiel cfg = new ConfigurazioneInsiel();
    //	    Header header = getFVGHeaders(cfg);
    //	    message.getHeaders().add(header);
    //	} catch (SOAPException e) {
    //	    throw new Fault(e);
    //	}
    //    }
}
