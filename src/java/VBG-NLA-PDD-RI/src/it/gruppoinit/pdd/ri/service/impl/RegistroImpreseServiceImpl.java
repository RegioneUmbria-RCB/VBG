package it.gruppoinit.pdd.ri.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import it.gruppoinit.domain.helper.PraticaXmlHelper;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Comune;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.EMail;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Indirizzo;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.IndirizzoConRecapiti;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Provincia;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Stato;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Telefono;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_1_0_1.ProtocolloRI;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_1_0_1.RiepilogoPraticaSUAP;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_1_0_1.TipoIntervento;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_1_0_1.VersioneSchema;
import it.gruppoinit.impresainungiorno.schema.suap.rea.AllegatoSuap;
import it.gruppoinit.impresainungiorno.schema.suap.rea.ComunicazioneREA.ComunicazioneEsitoScia;
import it.gruppoinit.impresainungiorno.schema.suap.rea.ComunicazioneREA.ComunicazioneScia;
import it.gruppoinit.impresainungiorno.schema.suap.rea.ComunicazioneREA.EstremiPraticaSuap;
import it.gruppoinit.impresainungiorno.schema.suap.rea.ComunicazioneREA.EstremiPraticaSuap.Impresa;
import it.gruppoinit.impresainungiorno.schema.suap.rea.ComunicazioneREA.SuapMittente;
import it.gruppoinit.impresainungiorno.schema.suap.rea.EsitoScia;
import it.gruppoinit.impresainungiorno.schema.suap.rea.OggettoComunicazioneREA;
import it.gruppoinit.impresainungiorno.schema.suap.rea.StatoPratica;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.AllegatoSUAP;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.AllegatoSUAPReaXml;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.AllegatoSUAPXml;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.BaseComunicazioneREA;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.ComunicazioneREA;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.ComunicazioneREAResponse;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.ComunicazioneREAStd;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.ComunicazioneREAStdResponse;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.DatiRispostaREA;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.IComunicazioneREA;
import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.SoggettoSUAP;
import it.gruppoinit.impresainungiorno.schema.suap.ri.iscrizione.IscrizioneImpresaRiSpcRequest;
import it.gruppoinit.impresainungiorno.schema.suap.ri.iscrizione.IscrizioneImpresaRiSpcResponse;
import it.gruppoinit.impresainungiorno.schema.suap.ri.iscrizione.IscrizioneImpresaRiSpcResponse.DatiIdentificativi;
import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.PraticaSUAPException_Exception;
import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.ProtocolloSUAP;
import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.ProtocolloSUAPException_Exception;
import it.gruppoinit.impresainungiorno.schema.suap.ri.messages.RichiestaIscrizioneImpresaRiSPC;
import it.gruppoinit.nlaproxy.service.stc.NlaWebService;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.getpraticasuap.CompilaPraticaSUAPXMLResponseType;
import it.gruppoinit.pdd.ri.service.PraticaXmlHelperService;
import it.gruppoinit.pdd.ri.service.RegistroImpreseService;
import it.gruppoinit.pdd.ri.ws.InterazioniRIClient;
import it.gruppoinit.pdd.utils.AllegatoHelper;
import it.gruppoinit.pdd.utils.ConfigurazioneHelper;
import it.gruppoinit.pdd.utils.ConfigurazioneHelperLoader;
import it.gruppoinit.pdd.utils.ConfigurazioneProtocolloSUAP;
import it.gruppoinit.pdd.utils.IstanzaAllegatiHelper;
import it.gruppoinit.pdd.utils.IstanzaAllegatiHelperLoader;
import it.gruppoinit.pdd.utils.IstanzaHelper;
import it.gruppoinit.pdd.utils.IstanzaHelperLoader;
import it.gruppoinit.pdd.utils.RegistroImpreseDataHelper;
import it.gruppoinit.pdd.utils.TIPO_PRATICA;
import it.gruppoinit.pdd.utils.Utilities;
import it.gruppoinit.sigepro.definitions.movimenti.MovimentiWSClient;
import it.gruppoinit.sigepro.definitions.oggetti.OggettiWSClient;
import it.gruppoinit.sigepro.schemas.messages.base.AllegatoBaseType;
import it.gruppoinit.sigepro.schemas.messages.base.FileBaseType;
import it.gruppoinit.sigepro.schemas.messages.movimenti.MovimentiAllegatiInsertRequest;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiFindResponse;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.schema.TokenInfoType;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.DocumentiType;

public class RegistroImpreseServiceImpl implements RegistroImpreseService {

    public static final Set<Integer> warningCodes;
    /// CAMPI VERSIONE E DATA REA.XML E SUAP.XML 
    private static final String VERSIONE_XSD_REA = "1.0.0";
    private static XMLGregorianCalendar dataVersioneRea;
    static {
	warningCodes = new HashSet<Integer>();
	warningCodes.add(201);
	warningCodes.add(202);
	warningCodes.add(203);
	warningCodes.add(204);
	GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
	c.set(Calendar.YEAR, 2012);
	c.set(Calendar.MONTH, 6);
	c.set(Calendar.DATE, 22);
	dataVersioneRea = Utilities.convertFromGregorianCalendar(c);
    }
    private static final String PRATICA_SUAP_1_0_1_XSD = "pratica_suap-1.0.1.xsd";
    private static final String SUAP_REA_1_0_0_XSD = "suap-rea-1.0.0.xsd";
    /// CAMPI VERSIONE E DATA REA.XML E SUAP.XML
    private static final String WSDL_REGISTROIMPRESE_FOLDER = "wsdl/registroimprese/";
    private static final String ALLEGATO_DESCRITTORE_SUAP_XML = "SUAP-XML";
    private static final String ALLEGATO_DESCRITTORE_ALTRO = "ALTRO";
    private static Logger log = LoggerFactory.getLogger(RegistroImpreseServiceImpl.class);
    static QName iscrizione = new QName("http://www.impresainungiorno.gov.it/schema/suap/ri/spc", "IscrizioneImpresaRiSpcRequest", "spc");
    static QName dettaglio = new QName("http://ejb.protocollo.infocamere.it/", "ComunicazioneREA", "ejb");
    private InterazioniRIClient interazioniRIClient;
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;
    private OggettiWSClient oggettiWSClient;
    private MovimentiWSClient movimentiWSClient;
    private boolean richiediVisuraPdf = true;
    private boolean richiediVisuraXml = false;

    @Override
    public IscrizioneImpresaRiSpcResponse findDatiImpresa(String idcomunealias, String idComune, String codiceFiscale) {

	if (log.isDebugEnabled()) {
	    log.debug("#findDatiImpresa({})", codiceFiscale);
	}
	if (StringUtils.isBlank(codiceFiscale)) {
	    throw new RuntimeException("Non è possibile invocare il servizio: Il codice fiscale dell'impresa è vuoto");
	}
	IscrizioneImpresaRiSpcRequest req = new IscrizioneImpresaRiSpcRequest();
	req.setCodiceFiscale(codiceFiscale);
	log.debug("#findDatiImpresa eseguo il marshal dell'oggetto");
	RichiestaIscrizioneImpresaRiSPC port = interazioniRIClient.getRichiestaIscrizionePort(idcomunealias, idComune);
	log.debug("#findDatiImpresa prima di invocare il WS");
	IscrizioneImpresaRiSpcResponse response = port.iscrizioneImpresaRiSpc(req);
	log.debug("#findDatiImpresa. Chiamata completata");
	return response;
    }

    public void setInterazioniRIClient(InterazioniRIClient interazioniRIClient) {

	this.interazioniRIClient = interazioniRIClient;
    }

    public void setSigeproSecurityWebServiceClient(SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient) {

	this.sigeproSecurityWebServiceClient = sigeproSecurityWebServiceClient;
    }

    public void setOggettiWSClient(OggettiWSClient oggettiWSClient) {

	this.oggettiWSClient = oggettiWSClient;
    }

    public void setRichiediVisuraPdf(boolean richiediVisuraPdf) {

	this.richiediVisuraPdf = richiediVisuraPdf;
    }

    public void setRichiediVisuraXml(boolean richiediVisuraXml) {

	this.richiediVisuraXml = richiediVisuraXml;
    }

    public void setMovimentiWSClient(MovimentiWSClient movimentiWSClient) {

	this.movimentiWSClient = movimentiWSClient;
    }

    public static void main(String[] args) {

    }

    // modificato l'ogetto che ritorna il metodo 
    @Override
    public it.gruppoinit.wsanagrafe2.schema.Anagrafe iscrizioneImpresaToAnagrafe(IscrizioneImpresaRiSpcResponse response) throws Exception {

	if (log.isDebugEnabled()) {
	    log.debug("IscrizioneImpresaToAnagrafe");
	}
	if (response == null) {
	    log.error("Errore tornato dal servizio Infocamere: oggetto response nullo");
	    throw new RuntimeException("Errore tornato dal servizio Infocamere: oggetto response nullo");
	}
	if (response.getErrore() != null && StringUtils.isNotBlank(response.getErrore().getCodice())) {
	    log.error("Errore tornato dal servizio Infocamere: {}",
		    ReflectionToStringBuilder.toString(response.getErrore(), ToStringStyle.SHORT_PREFIX_STYLE));
	    throw new RuntimeException("Errore tornato dal servizio Infocamere: " +
				       ReflectionToStringBuilder.toString(response.getErrore(), ToStringStyle.SHORT_PREFIX_STYLE));
	}
	it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafe = new it.gruppoinit.wsanagrafe2.schema.Anagrafe();
	DatiIdentificativi dati = response.getDatiIdentificativi();
	if (dati != null) {
	    anagrafe.setNOMINATIVO(dati.getDenominazione());
	    anagrafe.setCODICEFISCALE(dati.getCodiceFiscale());
	    anagrafe.setPARTITAIVA(dati.getPartitaIva());
	    anagrafe.setPROVINCIAREA(dati.getCciaa());
	    anagrafe.setNUMISCRREA(dati.getNrea());
	}
	IndirizzoConRecapiti sedeLegale = response.getSedeLegale();
	if (sedeLegale != null) {
	    String indirizzo = "";
	    if (StringUtils.isNotBlank(sedeLegale.getToponimo())) {
		indirizzo += sedeLegale.getToponimo() + " ";
	    }
	    if (StringUtils.isNotBlank(sedeLegale.getDenominazioneStradale())) {
		indirizzo += sedeLegale.getDenominazioneStradale();
	    }
	    if (StringUtils.isNotBlank(sedeLegale.getNumeroCivico())) {
		indirizzo += ", " + sedeLegale.getNumeroCivico();
	    }
	    anagrafe.setINDIRIZZO(StringUtils.defaultString(indirizzo).trim());
	    anagrafe.setCAP(sedeLegale.getCap());
	    if (sedeLegale.getComune() != null) {
		if (StringUtils.isNotBlank(sedeLegale.getComune().getCodiceCatastale())) {
		    anagrafe.setCOMUNERESIDENZA(sedeLegale.getComune().getCodiceCatastale());
		}
	    }
	    anagrafe.setPROVINCIA(sedeLegale.getProvincia().getSigla());
	    if (sedeLegale.getTelefono() != null) {
		if (sedeLegale.getTelefono().size() > 0) {
		    List<Telefono> telefonos = sedeLegale.getTelefono();
		    for (Telefono telefono : telefonos) {
			if (StringUtils.isNotBlank(telefono.getValue())) {
			    // i valori Ufficio Cellulare Fax sono presi dagli xsd di registroimprese
			    if ("Ufficio".equalsIgnoreCase(telefono.getTipo())) {
				anagrafe.setTELEFONO(telefono.getValue());
			    }
			    if ("Cellulare".equalsIgnoreCase(telefono.getTipo())) {
				anagrafe.setTELEFONOCELLULARE(telefono.getValue());
			    }
			    if ("Fax".equalsIgnoreCase(telefono.getTipo())) {
				anagrafe.setFAX(telefono.getValue());
			    }
			}
		    }
		}
	    }
	    if (sedeLegale.getEMail() != null) {
		if (sedeLegale.getEMail().size() > 0) {
		    List<EMail> emails = sedeLegale.getEMail();
		    for (EMail eMail : emails) {
			if (StringUtils.isNotBlank(eMail.getValue())) {
			    if ("Standard".equalsIgnoreCase(eMail.getTipo())) {
				anagrafe.setEMAIL(eMail.getValue());
			    }
			    if ("PEC".equalsIgnoreCase(eMail.getTipo())) {
				anagrafe.setPec(eMail.getValue());
			    }
			}
		    }
		}
	    }
	}
	return anagrafe;
    }

    @Override
    public DatiRispostaREA notificaComunicazioneREA(InserimentoAttivitaNLARequest request, TIPO_NOTIFICA_ENUM tipoNotifica) {

	log.debug("#notificaComunicazioneREA({})", tipoNotifica);
	if (request == null) {
	    throw new RuntimeException("Non è possibile invocare il servizio: request è vuota");
	}
	log.debug("#notificaComunicazioneREA prima di creare la richiesta");
	String idPratica = request.getDatiAttivita().getIdPratica(); // codiceistanza
	String idAttivita = request.getDatiAttivita().getIdAttivita(); // codicemovimento
	if (log.isDebugEnabled()) {
	    log.debug("idPratica={}, idAttivita={}", idPratica, idAttivita);
	}
	// per convenzione l'idente del mittente è l'idcomunealias del backoffice
	String idComuneAlias = request.getSportelloMittente().getIdEnte();
	Integer codiceIstanza = null;
	Integer codiceMovimento = null;
	try {
	    String codiceIstanzaSenzaAlias = idPratica.replaceAll(idComuneAlias, "");
	    codiceIstanza = Integer.parseInt(codiceIstanzaSenzaAlias);
	    log.debug("notificaComunicazioneREA# codiceIstanza: {}", codiceIstanza);
	} catch (Exception e) {
	    log.error("Errore nella conversione del codiceIstanza da inserimentoAttivitarequest: codiceIstanza non valido {}", idPratica);
	    throw new RuntimeException(
		    "Errore nella conversione del codiceIstanza da inserimentoAttivitarequest: codiceIstanza non valido [" + idPratica + "]");
	}
	try {
	    String codiceMovimentosenzaAlias = idAttivita.replaceAll(idComuneAlias, "");
	    codiceMovimento = Integer.parseInt(codiceMovimentosenzaAlias);
	    log.debug("notificaComunicazioneREA# codiceMovimento: {}", codiceMovimento);
	} catch (Exception e) {
	    log.error("CodiceMovimento non valido: {}", idAttivita);
	    throw new RuntimeException(
		    "Errore nella conversione del codiceMovimento da inserimentoAttivitarequest: codiceMovimento non valido [" + idAttivita + "]");
	}
	log.debug("notificaComunicazioneREA# recupero le informazioni di connessione");
	GetDbConnectionInfoResponse dbconninfo = sigeproSecurityWebServiceClient.getConnectionProperties(idComuneAlias);
	log.debug("notificaComunicazioneREA# informazioni di connessione recuperate recupero le informazioni di connessione");
	log.debug("notificaComunicazioneREA# recupero un token per oggettiWS");
	String backofficeToken = sigeproSecurityWebServiceClient.loginAPP(idComuneAlias);
	log.debug("notificaComunicazioneREA# token recuperato{}", backofficeToken);
	log.debug("buildComunicazioneReaFromInserimentoAttivitaRequest# creo la directory temporanea");
	File tmpDir = Utilities.createPDDTmpDir(dbconninfo.getAlias(), request.getDatiAttivita().getIdPratica());
	log.debug("buildComunicazioneReaFromInserimentoAttivitaRequest# directory temporanea creata {}", tmpDir);
	List<AllegatoHelper> allegati = null;
	try {
	    allegati = scaricaAllegatiDomanda(backofficeToken, request, dbconninfo, tmpDir);
	} catch (IOException e1) {
	    log.error("Errore nel download dei documenti: " + idAttivita + ", errore: " + e1.getMessage(), e1);
	    throw new RuntimeException("Errore nel download dei documenti per codiceMovimento [" + idAttivita + "]", e1);
	}
	Connection c = sigeproSecurityWebServiceClient.getConnection(dbconninfo.getAlias());
	IstanzaHelper istanza = this.recuperaIstanza(c, dbconninfo, codiceIstanza.intValue());
	boolean comunicazioneSTD = new ConfigurazioneProtocolloSUAP(idComuneAlias).isComunicazioneSTD();
	BaseComunicazioneREA comunicazioneREA = buildComunicazioneReaFromInserimentoAttivitaRequest(c, request, tipoNotifica, dbconninfo, istanza,
		allegati, comunicazioneSTD);
	log.debug("Richiesta creata. Creo la porta di comunicazione");
	ProtocolloSUAP port = interazioniRIClient.getProtocolloSUAPPort(idComuneAlias, istanza.getIdcomune(), istanza.getIstComuneCodiceCatastale());
	log.debug("#notificaComunicazioneREA prima di invocare il WS");
	DatiRispostaREA ret = null;
	if (comunicazioneSTD) {
	    ComunicazioneREAStdResponse comunicazioneREAStd = null;
	    try {
		comunicazioneREAStd = port.comunicazioneREAStd(comunicazioneREA.getComunicazioneREAStd());
	    } catch (PraticaSUAPException_Exception e) {
		log.error("Errore tornato da Registro Imprese: " + e.getMessage(), e);
		throw new RuntimeException("Errore tornato da Registro Imprese: " + e.getMessage(), e);
	    } catch (ProtocolloSUAPException_Exception e) {
		log.error("Errore tornato da Registro Imprese: " + e.getMessage(), e);
		throw new RuntimeException("Errore tornato da Registro Imprese: " + e.getMessage(), e);
	    }
	    log.debug("#notificaComunicazioneREA. Chiamata completata");
	    if (comunicazioneREAStd == null) {
		Utilities.logAndThrowException("Risposta nulla dal servizio registro Imprese", getClass());
	    }
	    if (comunicazioneREAStd.getReturn() == null) {
		Utilities.logAndThrowException("Risposta (reaResponse.getReturn()) nulla dal servizio registro Imprese", getClass());
	    }
	    ret = DatiRispostaREA.fromRispostaREAStd(comunicazioneREAStd.getReturn());
	} else {
	    ComunicazioneREAResponse reaResponse = null;
	    try {
		reaResponse = port.dettaglioImpresa(comunicazioneREA.getComunicazioneREA());
	    } catch (PraticaSUAPException_Exception e) {
		log.error("Errore tornato da Registro Imprese: {},{}", e.getMessage(), e);
		throw new RuntimeException("Errore tornato da Registro Imprese: " + e.getMessage(), e);
	    } catch (ProtocolloSUAPException_Exception e) {
		log.error("Errore tornato da Registro Imprese: {},{}", e.getMessage(), e);
		throw new RuntimeException("Errore tornato da Registro Imprese: " + e.getMessage(), e);
	    }
	    log.debug("#notificaComunicazioneREA. Chiamata completata");
	    if (reaResponse == null) {
		Utilities.logAndThrowException("Risposta nulla dal servizio registro Imprese", getClass());
	    }
	    if (reaResponse.getReturn() == null) {
		Utilities.logAndThrowException("Risposta (reaResponse.getReturn()) nulla dal servizio registro Imprese", getClass());
	    }
	    ret = DatiRispostaREA.fromRispostaREA(reaResponse.getReturn());
	}
	// GESTIONE MESSAGGIO CON ERRORE DA REGISTROIMPRESE
	int status = ret.getEsitoRichiesta();
	if (status != 0) {
	    if (!warningCodes.contains(status)) { // se non è un codice warning
		String errMsg = "Errore tornato nella chiamata al Registro Imprese: codice:[" + status + "], descrizione: " +
				ret.getDettaglioEsitoRichiesta();
		Utilities.logAndThrowException(errMsg, getClass());
	    } else {
		log.error("notificaComunicazioneREA# LA COMUNICAZIONE HA RIPORTATO IL SEGUENTE WARNING [{}]", ret.getDettaglioEsitoRichiesta());
	    }
	}
	if (ret.getProtocollo() == null) {
	    String errMsg = "Errore tornato nella chiamata al Registro Imprese: I dati di protocollo sono nulli. Codice:[" + status +
			    "], descrizione: " + ret.getDettaglioEsitoRichiesta() + ".";
	    Utilities.logAndThrowException(errMsg, getClass());
	}
	// INSERIRE I FILE DI VISURA NELLA TABELLA ISTANZE_COMUNICAZIONI_RI (codiceistanza, codicemovimento, data/ora)
	// 1 INVOCARE IL WS PER INSERIMENTO OGGETTI
	notificaBackoffice(backofficeToken, codiceIstanza, codiceMovimento, idComuneAlias, ret, dbconninfo, tmpDir,
		request.getSportelloMittente().getIdSportello());
	return ret;
    }

    @Override
    public CompilaPraticaSUAPXMLResponseType compilaPraticaSUAP(String token, BigInteger codiceistanza) {

	TokenInfoType tinfo = sigeproSecurityWebServiceClient.getTokenInfo(token);
	String idComuneAlias = tinfo.getAlias();
	CompilaPraticaSUAPXMLResponseType response = new CompilaPraticaSUAPXMLResponseType();
	try {
	    PraticaXmlHelper<?> praticaXmlHelper = this.creaPraticaXml(idComuneAlias, codiceistanza);
	    if (praticaXmlHelper != null) {
		response.setFileName(praticaXmlHelper.getNomeFilePratica());
		response.setMimeType("text/xml");
		DataSource ds = new ByteArrayDataSource(praticaXmlHelper.getPraticaXml(), "text/xml; charset=UTF-8");
		DataHandler dh = new DataHandler(ds);
		response.setBinaryData(dh);
	    }
	    return response;
	} catch (Exception e) {
	    Utilities.logAndThrowException("Si è verificato un errore nella conversione in XML del file praticaSUAP " + e.getMessage(), e,
		    RegistroImpreseServiceImpl.class);
	}
	return null;
    }

    @Override
    public String compilaPraticaSUAPComeStringa(String idComuneAlias, BigInteger codiceistanza, boolean effettuaValidazione,
	    VERSIONE_PRATICA_SUAP versione, TIPO_PRATICA tipoPratica) {

	String risultato = "";
	log.debug("compilaPraticaSUAPComeStringa# Validazione attiva {}", effettuaValidazione);
	try {
	    PraticaXmlHelper<?> praticaXmlHelper = this.creaPraticaXml(idComuneAlias, codiceistanza, effettuaValidazione, versione, tipoPratica);
	    risultato = praticaXmlHelper.getPraticaXml();
	} catch (RuntimeException e) {
	    log.error("compilaPraticaSUAPComeStringa alias=" + idComuneAlias + ", codiceistanza=" + codiceistanza, e);
	    return "[KO]" + e.getMessage();
	}
	return risultato;
    }

    private IstanzaHelper recuperaIstanza(Connection c, GetDbConnectionInfoResponse dbconninfo, int codiceIstanza) {

	log.debug("recuperaIstanza# popolo l'oggetto istanzaHelper");
	IstanzaHelperLoader ihl = new IstanzaHelperLoader(c);
	return ihl.popolateIstanza(dbconninfo.getIdComune(), dbconninfo.getDbOwner(), codiceIstanza);
    }

    private BaseComunicazioneREA buildComunicazioneReaFromInserimentoAttivitaRequest(Connection c, InserimentoAttivitaNLARequest request,
	    TIPO_NOTIFICA_ENUM tipoNotifica, GetDbConnectionInfoResponse dbconninfo, IstanzaHelper ihelper, List<AllegatoHelper> allegati,
	    boolean isStandard) {

	BaseComunicazioneREA ret = new BaseComunicazioneREA();
	IComunicazioneREA com = new ComunicazioneREA();
	if (isStandard) {
	    com = new ComunicazioneREAStd();
	    ret.setComunicazioneREAStd(com);
	} else {
	    ret.setComunicazioneREA(com);
	}
	// REMEMBER pag 24 stef STEF-interazione_SUAP_RI_bozza_v2.pdf "Accorgimenti da tenere e workaround di questa prima fase"
	// nel messaggio di richiesta non va valorizzato l'elemento pridpratica
	com.setPridPratica(null);
	ConfigurazioneHelperLoader chl = new ConfigurazioneHelperLoader(c);
	ConfigurazioneHelper confHelper = chl.loadConfigurazione(dbconninfo.getAlias(), dbconninfo.getIdComune(), dbconninfo.getDbOwner(),
		request.getSportelloMittente().getIdSportello(), ihelper.getIstComuneCodiceComune(), true);
	Utilities.gracefullyReleaseResources(c, null, null);
	// NON OBBLIGATORIO AL MOMENTO METTO LE CREDENZIALI DEL TOKEN
	String userId = StringUtils.defaultString(ihelper.getResponsabileUserid(), ihelper.getResponsabile()) + "@" + dbconninfo.getIdComune();
	SoggettoSUAP s = new SoggettoSUAP();
	s.setUseridAddetto(userId);
	com.setSoggetti(s);
	log.debug("buildComunicazioneReaFromInserimentoAttivitaRequest# prima di popolare SUAP_REA.xml");
	AllegatoSUAPReaXml reaXml = createReaXml(request, tipoNotifica, allegati, dbconninfo, ihelper, confHelper);
	if (reaXml != null) {
	    com.setSuapReaXml(reaXml);
	}
	log.debug("buildComunicazioneReaFromInserimentoAttivitaRequest# prima di popolare SUAP.xml");
	if (tipoNotifica.equals(TIPO_NOTIFICA_ENUM.AVVIO)) {
	    AllegatoSUAPXml suapXML = verificaAllegatoSuapXML(request, tipoNotifica, allegati, dbconninfo, ihelper, confHelper);
	    com.setSuapXml(suapXML);
	}
	com.setVisuraPDF(isVisura(request, "pdf"));
	com.setVisuraXML(isVisura(request, "xml"));
	log.debug("buildComunicazioneReaFromInserimentoAttivitaRequest# prima di aggiungere gli allegati");
	List<AllegatoSUAP> allegatis = allegatiHelperToAllegatiSUAP(allegati);
	com.getAllegati().addAll(allegatis);
	return ret;
    }

    /**
     * 
     * @param request
     * @param tipo
     *            (a scelta tra pdf|xml)
     * @return
     */
    private boolean isVisura(InserimentoAttivitaNLARequest request, String tipo) {

	if (tipo.equalsIgnoreCase("pdf")) {
	    String valoreParametro = NlaWebService.getValoreAltroDato(request, NlaWebService.ALTRI_DATI_VISURA_PDF);
	    if (StringUtils.isBlank(valoreParametro)) {
		return this.richiediVisuraPdf;
	    }
	    return "S".equalsIgnoreCase(valoreParametro);
	} else {
	    String valoreParametro = NlaWebService.getValoreAltroDato(request, NlaWebService.ALTRI_DATI_VISURA_XML);
	    if (StringUtils.isBlank(valoreParametro)) {
		return this.richiediVisuraXml;
	    }
	    return "S".equalsIgnoreCase(valoreParametro);
	}
    }

    private List<AllegatoSUAP> allegatiHelperToAllegatiSUAP(List<AllegatoHelper> alls) {

	List<AllegatoSUAP> result = new ArrayList<AllegatoSUAP>();
	if (alls != null) {
	    for (AllegatoHelper helper : alls) {
		AllegatoSUAP allegato = new AllegatoSUAP();
		allegato.setName(helper.getNomefile());
		allegato.setTipo(helper.getMimeType());
		if (helper.getFileContent() != null) {
		    DataSource fds = new FileDataSource(helper.getFileContent());
		    allegato.setAllegatoDataHandler(new DataHandler(fds));
		} else {
		    allegato.setAllegatoDataHandler(helper.getDataHandler());
		}
		result.add(allegato);
	    }
	}
	return result;
    }

    private AllegatoSUAPXml verificaAllegatoSuapXML(InserimentoAttivitaNLARequest request, TIPO_NOTIFICA_ENUM tipoNotifica, List<AllegatoHelper> alls,
	    GetDbConnectionInfoResponse dbconninfo, IstanzaHelper ihelper, ConfigurazioneHelper confHelper) {

	AllegatoSUAPXml result = new AllegatoSUAPXml();
	// REMEMBER pag 24 stef STEF-interazione_SUAP_RI_bozza_v2.pdf "Accorgimenti da tenere e workaround di questa prima fase"
	// In questa fase il suap.xml va inviato privo dei dati relativi agli enti(lista di elementi ente-coinvolto all'interno dell'elemento modulo) 
	// e privo del codice-pratica
	// Se questi dati sono presenti la pratica non viene accettata
	PraticaXmlHelperService<RiepilogoPraticaSUAP> praticaXmlHelperService = new PraticaSuapFactory()
		.getCreatePraticaSUAPService(VERSIONE_PRATICA_SUAP.V_1_0, sigeproSecurityWebServiceClient);
	PraticaXmlHelper<RiepilogoPraticaSUAP> praticaXmlHelper = praticaXmlHelperService.getPraticaXmlHelper(alls, ihelper, confHelper, true,
		TIPO_PRATICA.MODULO_UNICO, dbconninfo);
	Source src = validatePraticaSUAP(praticaXmlHelper.getOggettoPratica());
	result.setDataHandler(src);
	return result;
    }

    private Source validatePraticaSUAP(RiepilogoPraticaSUAP praticaSUAP) {

	Source src = null;
	try {
	    src = toSource(praticaSUAP);
	} catch (Exception e) {
	    Utilities.logAndThrowException("errore nella trasformazione dell'oggetto SUAP_XML: " + e.getMessage() + ".\n dettaglio SUAP_XML[" +
					   ReflectionToStringBuilder.toString(praticaSUAP, ToStringStyle.SHORT_PREFIX_STYLE) + "]",
		    e, getClass());
	}
	try {
	    Schema schema = Utilities.getSchemaForMessage(WSDL_REGISTROIMPRESE_FOLDER + PRATICA_SUAP_1_0_1_XSD);
	    Utilities.validaXml(src, schema);
	} catch (Exception e) {
	    Utilities.logAndThrowException("Errore nella validazione del file SUAP-XML: " + e.getMessage() + ".\n dettaglio SUAP_XML[" +
					   ReflectionToStringBuilder.toString(praticaSUAP, ToStringStyle.SHORT_PREFIX_STYLE) + "]",
		    e, getClass());
	}
	return src;
    }

    private String decodeTipoProcedimento(IstanzaHelper ihelper) {

	if (StringUtils.isNotBlank(ihelper.getCodiceProcedimentoRi())) {
	    return StringUtils.defaultString(ihelper.getCodiceProcedimentoRi()).trim();
	}
	return null;
    }

    private static TipoIntervento decodeTipoIntervento(IstanzaHelper ihelper) {

	if (StringUtils.isNotBlank(ihelper.getCodiceInterventoRi())) {
	    try {
		return TipoIntervento.fromValue(ihelper.getCodiceInterventoRi());
	    } catch (Exception e) {
		log.error("decodeTipoIntervento# errore nella decodifica del tipo intervento con valore {}", ihelper.getCodiceInterventoRi());
	    }
	}
	return null;
    }

    /**
     * il metodo popola i campi toponimo, denominazioneStradale, civico a partire da una stringa che rappresenta un
     * indirizzo (es: Via Manzoni, 6) secondo le seguenti regole
     * <ul>
     * <li>toponimo (prima stringa sinistra)</li>
     * <li>denominazione stradale (al netto di civico e toponimo)</li>
     * <li>civico (da destra prima stringa fino alla virgola, se non c'è virgola allora testo fisso "snc")</li>
     * </ul>
     * 
     * Se non presente lo spazio allora:
     * <ul>
     * <li>toponimo="." se e solo se isCAPPresente=true altrimenti non lo mette</li>
     * <li>denominazione stradale=denominazione</li>
     * <li>civico=snc</li>
     * </ul>
     * 
     * @param irv
     * @param denominazione
     * @param isCAPPresente
     */
    private static void popolateIndirizzoFromDenominazione(Indirizzo irv, String denominazione, boolean isCAPPresente) {

	if (StringUtils.isNotBlank(denominazione)) {
	    String[] splitted = denominazione.split(" ");
	    if (splitted.length > 1) {
		String toponimo = splitted[0];
		irv.setToponimo(StringUtils.defaultString(toponimo).trim());
		if (isCAPPresente && StringUtils.isBlank(toponimo)) {
		    irv.setToponimo(".");
		}
		String civico = "snc";
		denominazione = denominazione.replaceFirst(toponimo, "");
		denominazione = denominazione.trim();
		int posCivico = denominazione.lastIndexOf(",");
		if (posCivico > 0) {
		    civico = denominazione.substring(posCivico + 1);
		    denominazione = denominazione.substring(0, posCivico);
		}
		irv.setDenominazioneStradale(StringUtils.defaultString(denominazione).trim());
		irv.setNumeroCivico(StringUtils.defaultString(civico).trim());
	    } else {
		if (isCAPPresente) {
		    irv.setToponimo("."); // tag obbligatorio solo se presente il CAP
		}
		irv.setDenominazioneStradale(denominazione);
		irv.setNumeroCivico("snc");
	    }
	}
    }

    private AllegatoSUAPReaXml createReaXml(InserimentoAttivitaNLARequest request, TIPO_NOTIFICA_ENUM tipoNotifica, List<AllegatoHelper> alls,
	    GetDbConnectionInfoResponse dbconninfo, IstanzaHelper ihelper, ConfigurazioneHelper confHelper) {

	it.gruppoinit.impresainungiorno.schema.suap.rea.ComunicazioneREA reaXml = new it.gruppoinit.impresainungiorno.schema.suap.rea.ComunicazioneREA();
	reaXml.setLingua("IT");
	VersioneSchema versione = new VersioneSchema();
	versione.setVersione(VERSIONE_XSD_REA);
	versione.setData(dataVersioneRea);
	reaXml.setInfoSchema(versione);
	SuapMittente suapMittente = new SuapMittente();
	suapMittente.setIdSuap(confHelper.convertCodiceAccreditamentoToBigInteger(true));
	if (StringUtils.isNotBlank(request.getDatiAttivita().getNumeroProtocolloGenerale())
		&& (request.getDatiAttivita().getDataProtocolloGenerale() != null)) {
	    it.gruppoinit.impresainungiorno.schema.suap.pratica.v_1_0_1.ProtocolloSUAP protocolloMovimento = new it.gruppoinit.impresainungiorno.schema.suap.pratica.v_1_0_1.ProtocolloSUAP();
	    protocolloMovimento.setNumeroRegistrazione(
		    RegistroImpreseDataHelper.normalizzaProtocollo(request.getDatiAttivita().getNumeroProtocolloGenerale(), true));
	    protocolloMovimento.setDataRegistrazione(request.getDatiAttivita().getDataProtocolloGenerale());
	    //
	    protocolloMovimento.setCodiceAmministrazione(confHelper.getCodiceAmministrazioneIpa());
	    protocolloMovimento.setCodiceAoo(confHelper.getCodiceAoo());
	    reaXml.setProtocolloSuap(protocolloMovimento);
	}
	reaXml.setSuapMittente(suapMittente);
	EstremiPraticaSuap eps = new EstremiPraticaSuap();
	it.gruppoinit.impresainungiorno.schema.suap.pratica.v_1_0_1.ProtocolloSUAP protocolloIstanza = new it.gruppoinit.impresainungiorno.schema.suap.pratica.v_1_0_1.ProtocolloSUAP();
	String numeroProtocollo = RegistroImpreseDataHelper.normalizzaProtocollo(ihelper.getNumeroprotocollo(), true);
	protocolloIstanza.setNumeroRegistrazione(numeroProtocollo);
	if (ihelper.getDataprotocollo() != null) {
	    try {
		GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
		c.setTime(ihelper.getDataprotocollo());
		protocolloIstanza.setDataRegistrazione(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
	    } catch (DatatypeConfigurationException e) {
		throw new RuntimeException("Errore nella creazione dell'oggetto XMLGregorianCalendar: " + e.getMessage(), e);
	    }
	} else {
	    Utilities.logAndThrowException(
		    "Non è stata settata la data di protocollo dell'istanza. E' obbligatorio per la comunicazione con il Registro Imprese.",
		    getClass());
	}
	protocolloIstanza.setCodiceAmministrazione(confHelper.getCodiceAmministrazioneIpa());
	protocolloIstanza.setCodiceAoo(confHelper.getCodiceAoo());
	eps.setProtocolloSuap(protocolloIstanza);
	eps.setCodicePratica(String.valueOf(ihelper.getCodiceIstanza()));
	Impresa datiImpresa = popolatedatiImpresaPerRea(ihelper);
	eps.setImpresa(datiImpresa);
	OggettoComunicazioneREA ocr = new OggettoComunicazioneREA();
	// REMEMBER pag 24 stef STEF-interazione_SUAP_RI_bozza_v2.pdf "Accorgimenti da tenere e workaround di questa prima fase"
	// all'interno del suap_rea l'oggetto della comunicazione deve essere una stringa al massimo di 80 caratteri	
	ocr.setValue(StringUtils.right(ihelper.getLavori(), 80));
	TipoIntervento ti = decodeTipoIntervento(ihelper);
	if (ti != null) {
	    ocr.setTipoIntervento(ti);
	}
	String tipoProcedimento = decodeTipoProcedimento(ihelper);
	if (StringUtils.isNotBlank(tipoProcedimento)) {
	    ocr.setTipoProcedimento(tipoProcedimento);
	}
	eps.setOggetto(ocr);
	if (ihelper.getProtocolloRI() != null) {
	    ProtocolloRI pri = new ProtocolloRI();
	    if (StringUtils.isNotBlank(ihelper.getProtocolloRI().getAnno())) {
		String annoStr = ihelper.getProtocolloRI().getAnno();
		int annoI = Integer.parseInt(annoStr);
		GregorianCalendar annoP = (GregorianCalendar) GregorianCalendar.getInstance();
		annoP.set(Calendar.YEAR, annoI);
		XMLGregorianCalendar anno = Utilities.convertFromGregorianCalendar(annoP);
		pri.setAnno(anno);
	    }
	    // REMEMBER pag 24 stef STEF-interazione_SUAP_RI_bozza_v2.pdf
	    // in data-protocollo va inserita la data odierna
	    XMLGregorianCalendar dataPr = Utilities.convertFromDate(Calendar.getInstance().getTime());
	    pri.setDataProtocolloRi(dataPr);
	    String numeroprotocolloriString = ihelper.getProtocolloRI().getNumeroProtocolloRI();
	    if (StringUtils.isNotBlank(numeroprotocolloriString)) {
		numeroprotocolloriString = numeroprotocolloriString.trim();
		// REMEMBER pag 24 stef STEF-interazione_SUAP_RI_bozza_v2.pdf
		// in numero-protocollo-ri va inserita la prima parte del numer fino al '-' e va escluso da
		// '-' in poi (Es. se il numero è 123456-00051 va considerato solo 123456)
		if (numeroprotocolloriString.indexOf("-") > 0) {
		    numeroprotocolloriString = numeroprotocolloriString.substring(0, numeroprotocolloriString.indexOf("-"));
		}
		int numprotocolloRi = 0;
		try {
		    numprotocolloRi = Integer.parseInt(numeroprotocolloriString);
		} catch (NumberFormatException e) {
		    Utilities.logAndThrowException(
			    "Numero protocollo ri non valido [" + ihelper.getProtocolloRI().getNumeroProtocolloRI() + "]: " + e.getMessage(), e,
			    getClass());
		}
		pri.setNumeroProtocolloRi(numprotocolloRi);
	    }
	    // REMEMBER pag 24 stef STEF-interazione_SUAP_RI_bozza_v2.pdf
	    // va messo sempre 0
	    pri.setSottoNumeroProtocolloRi(0);
	    pri.setUfficioRi(StringUtils.defaultString(ihelper.getProtocolloRI().getUfficioRI().toUpperCase()));
	    eps.setProtocolloRi(pri);
	}
	reaXml.setEstremiPraticaSuap(eps);
	StatoPratica sp = StatoPratica.ISTRUTTORIA;
	switch (tipoNotifica) {
	case AVVIO:
	    reaXml.setComunicazioneScia(popolaComunicazioneScia(alls, request, ihelper, dbconninfo));
	    sp = getStatoPraticaFromRequest(request, StatoPratica.ISTRUTTORIA);
	    break;
	case ESITO:
	    reaXml.setComunicazioneEsitoScia(popolaComunicazioneEsito(alls, request, ihelper, dbconninfo));
	    sp = getStatoPraticaFromRequest(request, StatoPratica.EVASA);
	    break;
	default:
	    Utilities.logAndThrowException("Nessuna operazione corripondente al tipo: " + tipoNotifica, getClass());
	}
	reaXml.setStatoPratica(sp);
	AllegatoSUAPReaXml result = new AllegatoSUAPReaXml();
	Source src = null;
	try {
	    src = toSource(reaXml);
	} catch (JAXBException e) {
	    Utilities.logAndThrowException("errore nella trasformazione dell'oggetto reaXml: " + e.getMessage() + ".\n dettaglio reaxml[" +
					   ReflectionToStringBuilder.toString(reaXml, ToStringStyle.SHORT_PREFIX_STYLE) + "]",
		    e.getClass());
	} catch (Exception e) {
	    Utilities.logAndThrowException("errore nella trasformazione dell'oggetto reaXml: " + e.getMessage() + ".\n dettaglio reaxml[" +
					   ReflectionToStringBuilder.toString(reaXml, ToStringStyle.SHORT_PREFIX_STYLE) + "]",
		    e, getClass());
	}
	try {
	    Schema schema = Utilities.getSchemaForMessage(WSDL_REGISTROIMPRESE_FOLDER + SUAP_REA_1_0_0_XSD);
	    Utilities.validaXml(src, schema);
	} catch (Exception e) {
	    Utilities.logAndThrowException("errore nella validazione del file SUAP-REA.xml: " + e.getMessage() + ".\n dettaglio reaxml[" +
					   ReflectionToStringBuilder.toString(reaXml, ToStringStyle.SHORT_PREFIX_STYLE) + "]",
		    e, getClass());
	}
	result.setDataHandler(src);
	return result;
    }

    /**
     * Il metodo cerca di recuperare lo stato pratica da request.getDatiAttivita().getTipoAttivita().getCodice();
     * (configurazione notifica STC da Backoffice sul movimento).<br />
     * Se il valore esegue con successo il bind con uno degli stati pratica dichiarati ({@link StatoPratica#EVASA},
     * {@link StatoPratica#ISTRUTTORIA}, {@link StatoPratica#RIFIUTATA}, {@link StatoPratica#SOSPESA}) allora torna
     * quello altrimenti torna il valore di default settato nel parametro defaultStato
     * 
     * @param request
     * @param defaultStato
     * @return
     */
    private static StatoPratica getStatoPraticaFromRequest(InserimentoAttivitaNLARequest request, StatoPratica defaultStato) {

	String stato = request.getDatiAttivita().getTipoAttivita().getCodice();
	if (StringUtils.isNotBlank(stato)) {
	    try {
		return StatoPratica.fromValue(stato);
	    } catch (Exception e) {
		// non fa niente torna lo stato di defaults
	    }
	}
	return defaultStato;
    }

    private Source toSource(Object obj) throws JAXBException, ParserConfigurationException, SAXException, IOException {

	if (log.isDebugEnabled()) {
	    log.debug("toSource# {}", obj);
	    String xmlString = Utilities.marshallObject(obj);
	    log.debug("toSource# xml={}", xmlString);
	}
	return new JAXBSource(JAXBContext.newInstance(obj.getClass()), obj);
    }

    private Impresa popolatedatiImpresaPerRea(IstanzaHelper istanza) {

	Impresa result = null;
	if (istanza.getImpresa() != null) {
	    result = new Impresa();
	    String denominazione = istanza.getImpresa().getDenominazione();
	    String codiceFiscale = istanza.getImpresa().getCodiceFiscale();
	    String partitaIva = istanza.getImpresa().getPartitaIva();
	    result.setDenominazione(denominazione);
	    if (StringUtils.isBlank(codiceFiscale) && StringUtils.isBlank(partitaIva)) {
		Utilities
			.logAndThrowException("Attenzione! L'impresa " + denominazione + "[cf: " + codiceFiscale + ", piva: " + partitaIva +
					      "] non ha definito il valore di partita iva e/o codicefiscale obbligatorio per effettuare la comunicazione al registro imprese.",
				getClass());
	    }
	    if (StringUtils.isBlank(codiceFiscale)) {
		result.setCodiceFiscale(StringUtils.defaultString(partitaIva).toUpperCase());
	    } else {
		result.setCodiceFiscale(codiceFiscale.toUpperCase());
	    }
	    result.setProvinciaCciaaCompetente(StringUtils.defaultString(istanza.getImpresa().getProvinciaREA()).toUpperCase());
	    /// popolo l'indirizzo dell'impresa per il REA
	    Indirizzo irv = new Indirizzo();
	    Stato stato = new Stato();
	    if (StringUtils.defaultString(istanza.getImpresa().getResidenzaComuneSiglaProv()).equalsIgnoreCase("EE")) { // stato estero
		stato.setCodiceCatastale(istanza.getImpresa().getResidenzaComuneCodCatastale());
		stato.setCodice(istanza.getImpresa().getResidenzaComuneCodCatastale());
		irv.setCittaStraniera(istanza.getImpresa().getResidenzaComune());
	    } else {
		// COMUNE E PROVINCIA SOLAMENTE SE CITTA' ITALIANA
		stato.setCodice("IT");
		stato.setValue("ITALIA");
		Comune comune = new Comune();
		comune.setCodiceCatastale(istanza.getImpresa().getResidenzaComuneCodCatastale());
		comune.setValue(istanza.getImpresa().getResidenzaComune());
		irv.setComune(comune);
		Provincia provincia = new Provincia();
		if (StringUtils.isBlank(istanza.getImpresa().getResidenzaComuneSiglaProv())) {
		    Utilities
			    .logAndThrowException("Attenzione! La scheda dell'impresa " + denominazione +
						  " non ha definito il valore di residenza.provincia obbligatorio per effettuare la comunicazione al registro imprese.",
				    getClass());
		}
		provincia.setSigla(istanza.getImpresa().getResidenzaComuneSiglaProv());
		provincia.setValue(istanza.getImpresa().getResidenzaComuneProv());
		irv.setProvincia(provincia);
	    }
	    irv.setStato(stato);
	    boolean isCAPPresente = false;
	    if (StringUtils.isNotBlank(istanza.getImpresa().getIndirizzoCAP())) {
		irv.setCap(istanza.getImpresa().getIndirizzoCAP());
		isCAPPresente = true;
	    }
	    popolateIndirizzoFromDenominazione(irv, istanza.getImpresa().getIndirizzoDenominazione(), isCAPPresente);
	    result.setIndirizzo(irv);
	} else {
	    log.error("Impresa non presente per l'istanza [codice={} , idcomune={}]", istanza.getCodiceIstanza(), istanza.getIdcomune());
	    throw new RuntimeException(
		    "Attenzione! Non è stata definita nessuna impresa per l'istanza. Per effettuare la comunicazione REA è necessario specificarla.");
	}
	return result;
    }

    private ComunicazioneEsitoScia popolaComunicazioneEsito(List<AllegatoHelper> allegati, InserimentoAttivitaNLARequest request,
	    IstanzaHelper ihelper, GetDbConnectionInfoResponse dbconninfo) {

	ComunicazioneEsitoScia escia = new ComunicazioneEsitoScia();
	EsitoScia esito = new EsitoScia();
	esito.setData(request.getDatiAttivita().getDataAttivita());
	String esitoPositivoNegativo = "Istanza conclusa ";
	if (request.getDatiAttivita().isEsito()) {
	    esitoPositivoNegativo += "positivamente";
	} else {
	    esitoPositivoNegativo += "negativamente";
	}
	esito.setDescrizione(esitoPositivoNegativo);
	if (StringUtils.isNotBlank(request.getDatiAttivita().getParere())) {
	    esito.setDettaglioEsito(request.getDatiAttivita().getParere());
	}
	escia.setEsito(esito);
	if (allegati != null) {
	    for (AllegatoHelper ah : allegati) {
		it.gruppoinit.impresainungiorno.schema.suap.rea.AllegatoGenerico all = new it.gruppoinit.impresainungiorno.schema.suap.rea.AllegatoGenerico();
		all.setNomeFile(ah.getNomefile());
		if (StringUtils.isNotBlank(ah.getDescrizioneDocumento())) {
		    all.setDescrizione(ah.getDescrizioneDocumento());
		}
		all.setMime(Utilities.getContentType(ah.getNomefile()));
		escia.getAttoEnte().add(all);
	    }
	}
	return escia;
    }

    private ComunicazioneScia popolaComunicazioneScia(List<AllegatoHelper> allegati, InserimentoAttivitaNLARequest request, IstanzaHelper ihelper,
	    GetDbConnectionInfoResponse dbconninfo) {

	ComunicazioneScia scia = new ComunicazioneScia();
	List<DocumentiType> alls = request.getDatiAttivita().getDocumenti();
	boolean trovatoSUAPXml = false;
	String nomeFileSuapXml = StringUtils.defaultString(String.valueOf(ihelper.getCodicePraticaTelematica())) + ".SUAP.XML";
	for (DocumentiType doc : alls) {
	    if (doc.getAllegati() != null) {
		AllegatiType ab = doc.getAllegati();
		String nomeFile = ab.getAllegato();
		AllegatoSuap as = new AllegatoSuap();
		if (StringUtils.isNotBlank(doc.getDocumento())) {
		    as.setDescrizione(doc.getDocumento());
		}
		if (StringUtils.defaultString(nomeFile).toUpperCase().endsWith("SUAP.XML")) {
		    trovatoSUAPXml = true;
		    as.setDescrittore(ALLEGATO_DESCRITTORE_SUAP_XML);
		} else {
		    as.setDescrittore(ALLEGATO_DESCRITTORE_ALTRO);
		}
		as.setNomeFile(StringUtils.defaultString(nomeFile, "test.txt"));
		as.setMime(StringUtils.defaultString(Utilities.getContentType(nomeFile), "application/octet-stream"));
		scia.getAllegato().add(as);
	    }
	}
	if (!trovatoSUAPXml) {
	    AllegatoSuap suapXML = new AllegatoSuap();
	    suapXML.setDescrittore(ALLEGATO_DESCRITTORE_SUAP_XML);
	    suapXML.setNomeFile(nomeFileSuapXml);
	    suapXML.setMime(Utilities.getContentType(nomeFileSuapXml));
	    scia.getAllegato().add(suapXML);
	}
	return scia;
    }

    private List<AllegatoHelper> scaricaAllegatiDomanda(String backofficeToken, InserimentoAttivitaNLARequest request,
	    GetDbConnectionInfoResponse dbconninfo, File tmpDir) throws IOException {

	List<AllegatoHelper> _return = new ArrayList<AllegatoHelper>();
	// GLI ALLEGATI LI LEGGO DALLA REQUEST
	List<DocumentiType> docs = request.getDatiAttivita().getDocumenti();
	for (DocumentiType doc : docs) {
	    AllegatiType all = doc.getAllegati();
	    if (all != null) {
		String codiceOggetto = all.getId();
		if (StringUtils.isNotBlank(codiceOggetto)) {
		    BigInteger codoggetto = null;
		    try {
			codoggetto = new BigInteger(codiceOggetto);
		    } catch (Exception e) {
			continue;
		    }
		    OggettiFindResponse file = oggettiWSClient.find(codoggetto, backofficeToken);
		    AllegatoHelper ah = new AllegatoHelper();
		    ah.setCodiceOggetto(codiceOggetto);
		    ah.setDescrizioneDocumento(doc.getDocumento());
		    ah.setFileContent(copyFileFromDataHandler(codiceOggetto, file, tmpDir));
		    ah.setNomefile(file.getFileName());
		    if (StringUtils.isNotBlank(doc.getTipoDocumento())) {
			ah.setTipoDocumento(doc.getTipoDocumento());
		    }
		    _return.add(ah);
		}
	    }
	}
	return _return;
    }

    private File copyFileFromDataHandler(String codiceOggetto, OggettiFindResponse file, File tmpDir) throws IOException {

	File out = new File(tmpDir, String.valueOf(codiceOggetto) + "-" + System.currentTimeMillis() + ".raw");
	try (InputStream is = file.getBinaryData().getInputStream(); // 
		OutputStream os = new FileOutputStream(out);) //
	{
	    IOUtils.copy(is, os);
	}
	return out;
    }

    /**
     * Completa la comunicazione inviando i file al backoffice.
     * <ol>
     * <li>Recupera gli allegati della response (se presenti).
     * <ul>
     * <li>Salva gli allegati con il ws movimenti#movimentiAllegatiInsert</li>
     * </ul>
     * </li>
     * <li>Salva i riferimenti protocollo RI (se presenti) in ISTANZE_RI</li>
     * </ol>
     * 
     * @param token
     * @param codiceIstanza
     * @param codiceMovimento
     * @param idComuneAlias
     * @param ret
     * @param dbconninfo
     * @param tmpDir
     * @param software
     */
    private void notificaBackoffice(String token, Integer codiceIstanza, Integer codiceMovimento, String idComuneAlias, DatiRispostaREA ret,
	    GetDbConnectionInfoResponse dbconninfo, File tmpDir, String software) {

	List<AllegatoSUAP> allegati = ret.getAllegati();
	if (log.isDebugEnabled()) {
	    log.debug("inserisco gli oggetti tornati da Registro imprese");
	}
	String idcomune = dbconninfo.getIdComune();
	String dbowner = dbconninfo.getDbOwner();
	Connection c = sigeproSecurityWebServiceClient.getConnection(idComuneAlias);
	for (AllegatoSUAP all : allegati) {
	    salvaAllegatoInMovimenti(all, codiceMovimento, software, token);
	}
	if (ret.getProtocollo() != null) {
	    String numeroprotocolloRI = ret.getProtocollo().getNumeroProtocolloRi();
	    String annoRI = ret.getProtocollo().getAnno();
	    String dataProtocolloRI = ret.getProtocollo().getDataProtocolloRi();
	    String ufficioRI = ret.getProtocollo().getUfficioRi();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    // trovo se ho già inserito i dettagli della comunicazione ri
	    Integer id = null;
	    try {
		pstmt = c.prepareStatement(selectComunicazioniRI.replaceAll("DB_SCHEMA", dbowner));
		pstmt.setString(1, dbconninfo.getIdComune());
		pstmt.setInt(2, codiceIstanza);
		rs = pstmt.executeQuery();
		if (rs.next()) {
		    id = rs.getInt(1);
		}
	    } catch (SQLException e) {
		Utilities.logAndThrowException("Errore durante la query di ricerca progressivo " + ISTANZE_COMUNICAZIONI_RI_TABLE, e, getClass());
	    } finally {
		Utilities.gracefullyReleaseResources(null, pstmt, rs);
	    }
	    if (id == null) {
		// NON E' STATO INSERITO NELLA TABELLA TROVO LA MAX
		try {
		    pstmt = c.prepareStatement(selectMaxIstanzeRI.replaceAll("DB_SCHEMA", dbowner));
		    pstmt.setString(1, dbconninfo.getIdComune());
		    rs = pstmt.executeQuery();
		    if (rs.next()) {
			id = rs.getInt(1);
		    }
		} catch (SQLException e) {
		    Utilities.logAndThrowException("Errore durante la query di ricerca progressivo " + ISTANZE_COMUNICAZIONI_RI_TABLE, e, getClass());
		} finally {
		    Utilities.gracefullyReleaseResources(null, pstmt, rs);
		}
		// INSERISCI SU ISTANZE_COMUNICAZIONI_RI
		// idcomune, id, codiceistanza, anno,dataprotocollori,numeroprotocollori,ufficiori
		try {
		    pstmt = c.prepareStatement(insertComunicazioniRI.replaceAll("DB_SCHEMA", dbowner));
		    pstmt.setString(1, idcomune);
		    pstmt.setInt(2, ++id);
		    pstmt.setInt(3, codiceIstanza);
		    setStringNullSafe(pstmt, 4, annoRI);
		    setStringNullSafe(pstmt, 5, dataProtocolloRI);
		    setStringNullSafe(pstmt, 6, numeroprotocolloRI);
		    setStringNullSafe(pstmt, 7, ufficioRI);
		    int recordsAffected = pstmt.executeUpdate();
		    if (recordsAffected != 1) {
			log.error("Attenzione!!! la query di inserimento istanze_ri ha restituito [{}] record modificati.", recordsAffected);
		    }
		} catch (SQLException e) {
		    Utilities.logAndThrowException("Errore durante la query di ricerca progressivo " + ISTANZE_COMUNICAZIONI_RI_TABLE, e, getClass());
		} finally {
		    Utilities.gracefullyReleaseResources(c, pstmt, null);
		}
	    } else {
		// aggiorno i valori? in teoria no perchè i valori del protocollo li ho salvati al primo inserimento
	    }
	}
	Utilities.gracefullyReleaseResources(c, null, null);
    }

    private void salvaAllegatoInMovimenti(AllegatoSUAP all, Integer codiceMovimento, String software, String token) {

	// può succedere che torni l'allegato senza nome file. assumiamo che sia PDF
	String nomeFile = StringUtils.defaultString(all.getName(), "allegato_cciaa_" + System.currentTimeMillis() + ".pdf");
	log.debug("trovato file con nome={}", nomeFile);
	MovimentiAllegatiInsertRequest request = new MovimentiAllegatiInsertRequest();
	request.setToken(token);
	request.setSoftware(software);
	request.setCodicemovimento(codiceMovimento.intValue());
	String tipoFile = (StringUtils.defaultIfEmpty(nomeFile, "1.pdf").toLowerCase().endsWith("pdf")) ? "pdf" : "xml";
	request.setDescrizione("Visura camerale registro Imprese (" + tipoFile + ")");
	request.setFlagPubblica(false);
	request.setStcIdAllegato("RI");
	request.setStcIdAllegato("RI");
	AllegatoBaseType oggetto = new AllegatoBaseType();
	FileBaseType datiFile = new FileBaseType();
	datiFile.setBinaryData(all.getAllegatoDataHandler());
	datiFile.setFileName(nomeFile + "." + tipoFile);
	String mimeType = Utilities.getContentType(datiFile.getFileName());
	datiFile.setMimeType(mimeType);
	oggetto.setDatiFile(datiFile);
	request.setAllegato(oggetto);
	movimentiWSClient.insertMovimentiAllegati(request);
    }

    private void setStringNullSafe(PreparedStatement pstmt, int parIdx, String value) throws SQLException {

	if (StringUtils.isNotBlank(value)) {
	    pstmt.setString(parIdx, value);
	} else {
	    pstmt.setNull(parIdx, Types.NULL);
	}
    }

    private PraticaXmlHelper creaPraticaXml(String idComuneAlias, BigInteger codiceistanza) {

	return this.creaPraticaXml(idComuneAlias, codiceistanza, true, VERSIONE_PRATICA_SUAP.V_1_0, TIPO_PRATICA.MODULO_UNICO);
    }

    private PraticaXmlHelper creaPraticaXml(String idComuneAlias, BigInteger codiceistanza, boolean effettuaValidazione,
	    VERSIONE_PRATICA_SUAP versione, TIPO_PRATICA tipoPRATICA) {

	log.debug("creaPraticaXml# Validazione attiva {}", effettuaValidazione);
	log.debug("creaPraticaXml# recupero le informazioni di connessione tramite l'alias {}", idComuneAlias);
	GetDbConnectionInfoResponse dbconninfo = sigeproSecurityWebServiceClient.getConnectionProperties(idComuneAlias);
	log.debug("creaPraticaXml# informazioni di connessione recuperate recupero le informazioni di connessione");
	log.debug("creaPraticaXml# prima di aprire la connessione al backoffice");
	Connection c = sigeproSecurityWebServiceClient.getConnection(dbconninfo.getAlias());
	IstanzaHelperLoader ihl = new IstanzaHelperLoader(c);
	log.debug("creaPraticaXml# popolo l'oggetto istanzaHelper");
	IstanzaHelper ihelper = ihl.popolateIstanza(dbconninfo.getIdComune(), dbconninfo.getDbOwner(), codiceistanza.intValue());
	if (log.isDebugEnabled()) {
	    log.debug("creaPraticaXml# helper popolato {}", ReflectionToStringBuilder.toString(ihelper, ToStringStyle.SHORT_PREFIX_STYLE));
	}
	log.debug("creaPraticaXml# popolo la configurazione");
	ConfigurazioneHelperLoader chl = new ConfigurazioneHelperLoader(c);
	ConfigurazioneHelper confHelper = chl.loadConfigurazione(dbconninfo.getAlias(), dbconninfo.getIdComune(), dbconninfo.getDbOwner(),
		ihelper.getSoftware(), ihelper.getIstComuneCodiceComune(), effettuaValidazione);
	log.debug("creaPraticaXml# popolo la lista degli allegati");
	IstanzaAllegatiHelperLoader iallhlo = new IstanzaAllegatiHelperLoader(c);
	List<IstanzaAllegatiHelper> allegati = iallhlo.popolateAllegati(dbconninfo.getIdComune(), dbconninfo.getDbOwner(),
		codiceistanza.intValue());
	List<AllegatoHelper> alls = new ArrayList<>();
	for (IstanzaAllegatiHelper iah : allegati) {
	    AllegatoHelper ah = new AllegatoHelper();
	    ah.setCodiceOggetto(String.valueOf(iah.getCodiceOggetto()));
	    ah.setDescrizioneDocumento(iah.getDescrizioneDocumento());
	    ah.setNomefile(iah.getNomeFile());
	    ah.setTipoDocumento(iah.getTipo());
	    alls.add(ah);
	}
	Utilities.gracefullyReleaseResources(c, null, null);
	log.debug("creaPraticaXml# creo il file pratica-suap.xml");
	PraticaXmlHelperService praticaXmlHelperService = new PraticaSuapFactory().getCreatePraticaSUAPService(versione,
		sigeproSecurityWebServiceClient);
	return praticaXmlHelperService.getPraticaXmlHelper(alls, ihelper, confHelper, effettuaValidazione, tipoPRATICA, dbconninfo);
    }

    private String ISTANZE_COMUNICAZIONI_RI_TABLE = "ISTANZE_RI";
    private String selectComunicazioniRI = "select id from DB_SCHEMA." + ISTANZE_COMUNICAZIONI_RI_TABLE +
					   " where idcomune=? and codiceistanza=? order by id asc";
    private String insertComunicazioniRI = "insert into DB_SCHEMA." + ISTANZE_COMUNICAZIONI_RI_TABLE +
					   " (idcomune,id,codiceistanza,anno,dataprotocollori,numeroprotocollori,ufficiori) values (?, ?, ?, ?, ?, ?, ?)";
    private String selectMaxIstanzeRI = "select max(id) from DB_SCHEMA." + ISTANZE_COMUNICAZIONI_RI_TABLE + " where idcomune=?";
}
