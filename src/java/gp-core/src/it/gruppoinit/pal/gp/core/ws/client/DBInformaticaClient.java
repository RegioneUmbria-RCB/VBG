package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DBInformaticaClient {

    public static enum TIPO_PERSONA {
	PRIVATO, DITTA_INDIVIDUALE, AZIENDA, ENTE_ASSOCIAZIONE, CONDOMINIO, NON_DEFINITO
    };

    private static final String XPATH_BASE = "//DBE_GetDati_PODResponse/DBE_GetDati_PODResult/R/POD/";
    private final static Logger log = LoggerFactory.getLogger(DBInformaticaClient.class);
    private Document response;
    private XPath xpath;

    public DBInformaticaClient(String url, String codiceImpianto, String codicePOD) throws FunzioneBusinessRemotaException {

	XPathFactory xpathFactory = XPathFactory.newInstance();
	xpath = xpathFactory.newXPath();
	getDatiPod(url, codiceImpianto, codicePOD);
    }

    private String getValoreFromXpath(String xpathdaCercare) {

	try {
	    return getValueFromXml(xpathdaCercare, xpath, response);
	} catch (Exception e) {
	    log.error("{}", e);
	}
	return "";
    }

    public String getIndirizzoCliente() {

	return getValoreFromXpath(XPATH_BASE + "INDIRIZZO_CLIENTE");
    }

    public String getCivicoCliente() {

	return getValoreFromXpath(XPATH_BASE + "CIVICO_CLIENTE");
    }

    public String getCapCliente() {

	return getValoreFromXpath(XPATH_BASE + "CAP_CLIENTE");
    }

    public String getCittaCliente() {

	return getValoreFromXpath(XPATH_BASE + "CITTA_CLIENTE");
    }

    public String getProvinciaCliente() {

	return getValoreFromXpath(XPATH_BASE + "PROVINCIA_CLIENTE");
    }

    public String getEmail() {

	return getValoreFromXpath(XPATH_BASE + "EMAIL");
    }

    public String getRecapito1() {

	return getValoreFromXpath(XPATH_BASE + "RECAPITO1");
    }

    public String getRecapito2() {

	return getValoreFromXpath(XPATH_BASE + "RECAPITO2");
    }

    public boolean esistePOD() {

	String esiste = StringUtils.defaultString(getValoreFromXpath(XPATH_BASE + "POD_ESISTE"), "N");
	return esiste.equalsIgnoreCase("S");
    }

    public boolean isPODAttivo() {

	String attivo = StringUtils.defaultString(getValoreFromXpath(XPATH_BASE + "POD_ATTIVO"), "N");
	return attivo.equalsIgnoreCase("S");
    }

    public boolean isAttivoPOD() {

	return isPODAttivo();
    }

    public boolean isEsistentePOD() {

	return esistePOD();
    }

    public TIPO_PERSONA getTipoPersona() {

	String tipoPersona = getValoreFromXpath(XPATH_BASE + "FORMA_GIURIDICA");
	if (StringUtils.isBlank(tipoPersona)) {
	    return TIPO_PERSONA.NON_DEFINITO;
	}
	if (tipoPersona.equalsIgnoreCase("P")) {
	    return TIPO_PERSONA.PRIVATO;
	}
	if (tipoPersona.equalsIgnoreCase("D")) {
	    return TIPO_PERSONA.DITTA_INDIVIDUALE;
	}
	if (tipoPersona.equalsIgnoreCase("A")) {
	    return TIPO_PERSONA.AZIENDA;
	}
	if (tipoPersona.equalsIgnoreCase("E")) {
	    return TIPO_PERSONA.ENTE_ASSOCIAZIONE;
	}
	if (tipoPersona.equalsIgnoreCase("C")) {
	    return TIPO_PERSONA.CONDOMINIO;
	}
	return TIPO_PERSONA.NON_DEFINITO;
    }

    public String getRagioneSociale() {

	String val1 = getValoreFromXpath(XPATH_BASE + "NOMINATIVO1_CLINTE");
	String val2 = getValoreFromXpath(XPATH_BASE + "NOMINATIVO2_CLINTE");
	if (StringUtils.isNotBlank(val2)) {
	    return val1 + " " + val2;
	}
	return val1;
    }

    public String getCognome() {

	return getValoreFromXpath(XPATH_BASE + "NOMINATIVO1_CLINTE");
    }

    public String getNome() {

	return getValoreFromXpath(XPATH_BASE + "NOMINATIVO2_CLINTE");
    }

    public String getCodiceFiscale() {

	return getValoreFromXpath(XPATH_BASE + "CODICE_FISCALE");
    }

    public String getPiva() {

	return getValoreFromXpath(XPATH_BASE + "PARTITA_IVA");
    }

    public String getCodicePOD() {

	return getValoreFromXpath(XPATH_BASE + "CODICE_POD");
    }

    public String getCodiceCliente() {

	return getValoreFromXpath(XPATH_BASE + "CODICE_CLIENTE");
    }

    public String getIndirizzoFornitura() {

	return getValoreFromXpath(XPATH_BASE + "INDIRIZZO_FORNITURA");
    }

    public String getCivicoFornitura() {

	return getValoreFromXpath(XPATH_BASE + "CIVICO_FORNITURA");
    }

    public String getCapFornitura() {

	return getValoreFromXpath(XPATH_BASE + "CAP_FORNITURA");
    }

    public String getCittaFornitura() {

	return getValoreFromXpath(XPATH_BASE + "CITTA_FORNITURA");
    }

    public String getProvinciaFornitura() {

	return getValoreFromXpath(XPATH_BASE + "PROVINCIA_FORNITURA");
    }

    public String getTipoTariffa() {

	return getValoreFromXpath(XPATH_BASE + "TIPO_TARIFFA");
    }

    public String getPotenzaContrattuale() {

	return getValoreFromXpath(XPATH_BASE + "POTENZA_CONTRATTUALE");
    }

    public String getPotenzaPagata() {

	return getValoreFromXpath(XPATH_BASE + "POTENZA_PAGATA");
    }

    public String getPotenzaDisponibile() {

	return getValoreFromXpath(XPATH_BASE + "POTENZA_DISPONIBILE");
    }

    public String getServizio() {

	return getValoreFromXpath(XPATH_BASE + "SERVIZIO");
    }

    public String getTipologiaUtenza() {

	return getValoreFromXpath(XPATH_BASE + "TIPO_UTENZA");
    }

    public String getDistributoreRagioneSociale() {

	return getValoreFromXpath(XPATH_BASE + "DISTRIBUTORE/D_RAGIONE_SOCIALE");
    }

    public String getDistributorePiva() {

	return getValoreFromXpath(XPATH_BASE + "DISTRIBUTORE/P_IVA");
    }

    public String getDistributoreIndirizzo() {

	return getValoreFromXpath(XPATH_BASE + "DISTRIBUTORE/D_INDIRIZZO");
    }

    public String getDistributoreCAP() {

	return getValoreFromXpath(XPATH_BASE + "DISTRIBUTORE/D_CAP");
    }

    public String getDistributoreLocalita() {

	return getValoreFromXpath(XPATH_BASE + "DISTRIBUTORE/D_LOCALITA");
    }

    public static void main(String[] args) throws FunzioneBusinessRemotaException {

	DBInformaticaClient client = new DBInformaticaClient("http://assistenza.dbinfo.it/DBEnergiaDemo/ws/qc.asmx?wsdl", "0010", "IT001E00001979");
	client.dumpProperties();
    }

    // url = "http://assistenza.dbinfo.it/DBEnergiaDemo/ws/qc.asmx?wsdl"
    private void getDatiPod(String url, String codiceImpianto, String codicePOD) throws FunzioneBusinessRemotaException {

	log.debug("Invoco il servizio {} con i parametri impianto: {},POD: {}", new Object[] { url, codiceImpianto, codicePOD });
	try {
	    QName operation = new QName("http://tempuri.org/", "qcSoap12");
	    Service svc = Service.create(operation);
	    if (log.isDebugEnabled()) {
		log.debug("#invocaPortaDelegata: servizio creato, aggiungo la porta");
	    }
	    svc.addPort(operation, SOAPBinding.SOAP12HTTP_BINDING, url);
	    if (log.isDebugEnabled()) {
		log.debug("#invocaPortaDelegata: creo il dispatch");
	    }
	    Dispatch<Source> dispatch = svc.createDispatch(operation, Source.class, Service.Mode.PAYLOAD);
	    Source request = new StreamSource(new StringReader("<tem:DBE_GetDati_POD xmlns:tem=\"http://tempuri.org/\"><tem:CodiceImpianto>"
		    + codiceImpianto + "</tem:CodiceImpianto><tem:CodicePOD>" + codicePOD + "</tem:CodicePOD></tem:DBE_GetDati_POD>"));
	    Source res = dispatch.invoke(request);
	    String r = toString(res);
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    InputSource is = new InputSource();
	    is.setCharacterStream(new StringReader(r));
	    response = db.parse(is);
	} catch (Exception e) {
	    log.error("{}", e);
	    throw new FunzioneBusinessRemotaException("Si è verificato un errore nel recupero delle informazioni dai servizi DBINFO: "
		    + e.getMessage());
	}
    }

    private static String toString(Source source) {

	try {
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer();
	    StringWriter out = new StringWriter();
	    StreamResult result = new StreamResult(out);
	    transformer.transform(source, result);
	    return out.toString();
	} catch (Exception ex) {
	    throw new RuntimeException("Error converting to String", ex);
	}
    }

    private String getValueFromXml(String xpathParam, XPath xpath, Document document) throws ParserConfigurationException, SAXException, IOException,
	    XPathExpressionException {

	String result = xpath.evaluate(xpathParam, document);
	return result;
    }

    public void dumpProperties() {

	System.out.println("CF: " + getCodiceFiscale());
	System.out.println("CAP: " + getCapCliente());
	System.out.println("CITTA': " + getCittaCliente());
	System.out.println("CIVICO: " + getCivicoCliente());
	System.out.println("COGNOME: " + getCognome());
	System.out.println("EMAIL: " + getEmail());
	System.out.println("INDIRIZZO: " + getIndirizzoCliente());
	System.out.println("NOME: " + getNome());
	System.out.println("PIVA: " + getPiva());
	System.out.println("RAGIONE SOCIALE: " + getRagioneSociale());
	System.out.println("RECAPITO1: " + getRecapito1());
	System.out.println("RECAPITO2: " + getRecapito2());
	System.out.println("TIPO PERSONA: " + getTipoPersona());
	System.out.println("ESISTE POD: " + esistePOD());
	System.out.println("POD ATTIVO: " + isPODAttivo());
    }
}
