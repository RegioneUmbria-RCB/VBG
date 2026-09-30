package it.gruppoinit.nlapec.service.helper;

import it.gruppoinit.impresainungiorno.schema.AdempimentoSUAP;
import it.gruppoinit.impresainungiorno.schema.AllegatoGenerico;
import it.gruppoinit.impresainungiorno.schema.AnagraficaImpresa;
import it.gruppoinit.impresainungiorno.schema.CodiceREA;
import it.gruppoinit.impresainungiorno.schema.Comune;
import it.gruppoinit.impresainungiorno.schema.EstremiDichiarante;
import it.gruppoinit.impresainungiorno.schema.EstremiEnte;
import it.gruppoinit.impresainungiorno.schema.FormaGiuridica;
import it.gruppoinit.impresainungiorno.schema.ImpiantoProduttivo;
import it.gruppoinit.impresainungiorno.schema.Indirizzo;
import it.gruppoinit.impresainungiorno.schema.IndirizzoConRecapiti;
import it.gruppoinit.impresainungiorno.schema.Intestazione;
import it.gruppoinit.impresainungiorno.schema.ModelloAttivita;
import it.gruppoinit.impresainungiorno.schema.ModelloAttivita.TracciatoXml;
import it.gruppoinit.impresainungiorno.schema.ObjectFactory;
import it.gruppoinit.impresainungiorno.schema.OggettoComunicazione;
import it.gruppoinit.impresainungiorno.schema.ProtocolloSUAP;
import it.gruppoinit.impresainungiorno.schema.RiepilogoPraticaSUAP;
import it.gruppoinit.impresainungiorno.schema.RiepilogoPraticaSUAP.InfoSchema;
import it.gruppoinit.impresainungiorno.schema.Struttura;
import it.gruppoinit.nlapec.schema.PraticaSTC;
import it.gruppoinit.nlapec.schema.mailservice.AttachmentType;
import it.gruppoinit.nlapec.schema.mailservice.AttachmentsType;
import it.gruppoinit.nlapec.schema.mailservice.MailMessageType;
import it.gruppoinit.nlapec.util.DateUtil;
import it.gruppoinit.nlapec.util.FileUtil;
import it.gruppoinit.sigepro.schemas.messages.mailtipo.MailtipoResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ValoreParametroType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Utilities;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe per la creazione della mail secondo DPR160 Art.5 comma 5
 * 
 * @author fabrizioc
 * 
 */
public class PopulateMailDPR160Art5Comma5 {

    private static final String VERSIONE_XSD_SUAP = "1.0.0-beta";
    private static XMLGregorianCalendar dataVersioneSuap;
    static {
	GregorianCalendar cSuap = (GregorianCalendar) Calendar.getInstance();
	cSuap.set(Calendar.YEAR, 2012);
	cSuap.set(Calendar.MONTH, 4);
	cSuap.set(Calendar.DATE, 4);
	dataVersioneSuap = convertFromGregorianCalendar(cSuap);
    }
    private static final Logger log = LoggerFactory.getLogger(PopulateMailDPR160Art5Comma5.class);
    private MailMessageType message;
    private InserimentoPraticaNLARequest request;
    private MailtipoResponse mailtipo;

    public PopulateMailDPR160Art5Comma5(InserimentoPraticaNLARequest request, MailtipoResponse mailtipo) {

	message = new MailMessageType();
	this.request = request;
	this.mailtipo = mailtipo;
	// this.sigeproSecurityWebServiceClient = sigeproSecurityWebServiceClient;
    }

    public MailMessageType populateMail() throws Exception {

	String destinatari = request.getSportelloDestinatario().getPecSportello();
	if (StringUtils.isBlank(destinatari)) {
	    log.error("populateMail: il campo perSportello dello sportello destinatario è vuoto");
	    throw new RuntimeException("Nessun destinatario specificato");
	}
	message.setDestinatari(destinatari);
	populateOggettoMail();
	populateCorpoMail();
	populateAllegatiMail();
	return message;
    }

    /**
     * oggetto della mail secondo dpr160<br />
     * SUAP: &lt;identificativo sportello destinatario&gt; - &lt;codice fiscale impresa&gt; - &lt;denominazione
     * impresa&gt;
     * 
     */
    private void populateOggettoMail() {

	if (mailtipo == null) {
	    StringBuffer ogg = new StringBuffer();
	    ogg.append("SUAP: ");
	    ogg.append(getIdentificativoSUAP());
	    ogg.append(" - ");
	    ogg.append(getCFOPIAzienda());
	    if (request.getDettaglioPratica().getAziendaRichiedente() != null) {
		ogg.append(" - ");
		ogg.append(request.getDettaglioPratica().getAziendaRichiedente().getRagioneSociale());
	    }
	    message.setOggetto(ogg.toString());
	} else {
	    message.setOggetto(mailtipo.getOggetto());
	}
    }

    /**
     * corpo della mail secondo dpr160<br />
     * SUAP: &lt;identificativo SUAP destinatario&gt;<br />
     * Pratica: &lt;codice pratica SUAP&gt;<br />
     * Impresa: &lt;codice fiscale impresa&gt; - &lt;denominazione impresa&gt;<br />
     * <br />
     * Richiesta &lt;tipologia richiesta&gt; (es. richiesta, esito, domanda)
     */
    private void populateCorpoMail() {

	if (mailtipo == null) {
	    StringBuffer corpo = new StringBuffer();
	    corpo.append("SUAP: ");
	    corpo.append(getIdentificativoSUAP());
	    corpo.append("\n");
	    corpo.append("Pratica: ");
	    if (StringUtils.isBlank(request.getDettaglioPratica().getIdPratica())) {
		log.error("Non è stato possibile recuperare l'identificativo pratica");
		throw new RuntimeException("Non è stato possibile recuperare l'identificativo pratica");
	    }
	    corpo.append(request.getDettaglioPratica().getIdPratica());
	    corpo.append("\n");
	    if (request.getDettaglioPratica().getAziendaRichiedente() != null) {
		corpo.append("Impresa: ");
		corpo.append(getCFOPIAzienda());
		corpo.append(" - ");
		corpo.append(request.getDettaglioPratica().getAziendaRichiedente().getRagioneSociale());
	    }
	    corpo.append("\n\n");
	    corpo.append("Richiesta: domanda ");
	    if (StringUtils.isNotBlank(request.getDettaglioPratica().getOggetto())) {
		corpo.append("\"").append(StringUtils.defaultString(request.getDettaglioPratica().getOggetto())).append("\"");
	    }
	    message.setCorpoMail(corpo.toString());
	} else {
	    message.setCorpoMail(mailtipo.getCorpo());
	}
    }

    /**
     * creo file &lt;codice-pratica&gt;.SUAP.zip contenente i seguenti file: <br />
     * &lt;codice-pratica&gt;.SUAP.xml<br />
     * &lt;codice-pratica&gt;.SUAP.PDF.P7M<br />
     * &lt;codice-pratica&gt;.MDA.xml<br />
     * &lt;codice-pratica&gt;.MDA.PDF.P7M<br />
     * &lt;codice-pratica&gt;.&lt;NNN numero progressivo nella pratica&gt;.PDF.P7M<br />
     * <br />
     * &lt;codice-pratica&gt; = &lt;codice-fiscale-impresa&gt;-&lt;GGMMAAAA-HHMM&gt;
     */
    private void populateAllegatiMail() throws Exception {

	String codicePratica = getCodicePratica();
	File zipFile = createZipArchive(codicePratica);
	AttachmentType att = new AttachmentType();
	att.setMimeType("application/zip");
	att.setFileName(zipFile.getName());
	att.setBinaryData(FileUtil.getBytesFromFile(zipFile));
	AttachmentsType atts = new AttachmentsType();
	atts.getAttachment().add(att);
	message.setAttachments(atts);
	FileUtil.deleteAllFiles();
    }

    /**
     * rinomina gli allegati presenti nell'xml stc secondo il dpr160
     * 
     * @param codicePratica
     */
    private void renameDocumenti(String codicePratica) {

	int counter = 0;
	NumberFormat formatter = new DecimalFormat("#000");
	List<DocumentiType> documenti = request.getDettaglioPratica().getDocumenti();
	if (documenti != null) {
	    for (DocumentiType docuType : documenti) {
		String tipoDoc = docuType.getTipoDocumento();
		if (StringUtils.isNotBlank(tipoDoc)) {
		    if (tipoDoc.compareTo("RiepilogoDomanda") == 0) {
			//distinta del modello riepilogo da rinominare in <codice-pratica>.SUAP.PDF.P7M
			docuType.setDocumento(codicePratica + ".SUAP.PDF.P7M");
		    } else if (tipoDoc.compareTo("RiepilogoAllegati") == 0) {
			//distinta del modello attività da rinominare in <codice-pratica>.MDA.PDF.P7M
			docuType.setDocumento(codicePratica + ".MDA.PDF.P7M");
		    } else {
			//eventuali allegati da rinominare in <codice-pratica>.<NNN numero progressivo nella pratica>.PDF.P7M
			String _counter = formatter.format(counter);
			docuType.setDocumento(codicePratica + "." + _counter + ".PDF.P7M");
			counter++;
		    }
		}
	    }
	}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void createModelloRiepilogoXML(String codicePratica, File zipDir) throws Exception {

	try {
	    log.debug("createModelloRiepilogoXML(): codicePratica={}", codicePratica);
	    File modelloRiepilogoFile = new File(zipDir, codicePratica + ".SUAP.xml");
	    JAXBContext jc = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
	    ObjectFactory objFactory = new ObjectFactory();
	    RiepilogoPraticaSUAP riepilogoPraticaSUAP = (RiepilogoPraticaSUAP) objFactory.createRiepilogoPraticaSUAP();
	    populateRiepilogopraticaSUAP(riepilogoPraticaSUAP, codicePratica);
	    Marshaller m = jc.createMarshaller();
	    FileOutputStream fos = new FileOutputStream(modelloRiepilogoFile);
	    m.marshal(new JAXBElement(new QName("http://www.impresainungiorno.gov.it/schema/suap/pratica", "RiepilogoPraticaSUAP"),
		    RiepilogoPraticaSUAP.class, riepilogoPraticaSUAP), fos);
	    fos.close();
	} catch (Exception e) {
	    log.error("createModelloRiepilogoXML(): {}", e.getMessage());
	    //TODO inviare evento a backoffice
	    throw e;
	}
    }

    private void populateRiepilogopraticaSUAP(RiepilogoPraticaSUAP praticaSUAP, String codicePratica) {

	// RiepilogoPraticaSUAP praticaSUAP = new RiepilogoPraticaSUAP();
	// VersioneSchema infoSchema = new VersioneSchema();
	InfoSchema infoSchema = new InfoSchema();
	infoSchema.setVersione(VERSIONE_XSD_SUAP);
	infoSchema.setData(dataVersioneSuap);
	praticaSUAP.setInfoSchema(infoSchema);
	Intestazione intestazione = new Intestazione();
	EstremiEnte ufficioSUAP = new EstremiEnte();
	ufficioSUAP.setCodiceAmministrazione(getCodiceIPA());
	ufficioSUAP.setCodiceAoo(getCodiceAoo());
	ufficioSUAP.setIdentificativoSuap(getCodiceAccreditamento());
	intestazione.setUfficioDestinatario(ufficioSUAP);
	AnagraficaImpresa aimp = popolatedatiImpresaPerSuapXml();
	intestazione.setImpresa(aimp);
	OggettoComunicazione ocr = new OggettoComunicazione();
	ocr.setValue(request.getDettaglioPratica().getOggetto());
	intestazione.setOggettoComunicazione(ocr);
	intestazione.setDichiarante(popolaDatiDichiaranteSuapXml());
	if (StringUtils.isNotBlank(request.getDettaglioPratica().getDomicilioElettronico())) {
	    intestazione.setDomicilioElettronico(request.getDettaglioPratica().getDomicilioElettronico());
	}
	ProtocolloSUAP pSuap = new ProtocolloSUAP();
	pSuap.setCodiceAmministrazione(getCodiceIPA());
	pSuap.setCodiceAoo(getCodiceAoo());
	String numeroProtocollo = normalizzaProtocollo(request.getDettaglioPratica().getNumeroProtocolloGenerale());
	pSuap.setNumeroRegistrazione(numeroProtocollo);
	if (request.getDettaglioPratica().getDataProtocolloGenerale() != null) {
	    pSuap.setDataRegistrazione(request.getDettaglioPratica().getDataProtocolloGenerale());
	}
	intestazione.setProtocollo(pSuap);
	intestazione.setImpiantoProduttivo(popolaImpiantiProduttivi());
	praticaSUAP.setIntestazione(intestazione);
	Struttura struttura = new Struttura();
	AdempimentoSUAP adempimentoPrincipale = new AdempimentoSUAP();
	if (request.getDettaglioPratica().getIntervento() != null) {
	    adempimentoPrincipale.setNome(request.getDettaglioPratica().getIntervento().getDescrizione());
	}
	gestisciAllegatiSUAPXml(adempimentoPrincipale, codicePratica);
	struttura.getModulo().add(adempimentoPrincipale);
	praticaSUAP.setStruttura(struttura);
    }

    private EstremiDichiarante popolaDatiDichiaranteSuapXml() {

	EstremiDichiarante dich = new EstremiDichiarante();
	dich.setCodiceFiscale(request.getDettaglioPratica().getRichiedente().getAnagrafica().getCodiceFiscale());
	dich.setCognome(request.getDettaglioPratica().getRichiedente().getAnagrafica().getCognome());
	dich.setNome(request.getDettaglioPratica().getRichiedente().getAnagrafica().getNome());
	dich.setPec(request.getDettaglioPratica().getRichiedente().getAnagrafica().getPec());
	if (request.getDettaglioPratica().getRichiedente().getRuolo() != null) {
	    dich.setQualifica(request.getDettaglioPratica().getRichiedente().getRuolo().getRuolo());
	}
	return dich;
    }

    private AnagraficaImpresa popolatedatiImpresaPerSuapXml() {

	AnagraficaImpresa imp = null;
	if (request.getDettaglioPratica().getAziendaRichiedente() != null) {
	    imp = new AnagraficaImpresa();
	    imp.setCodiceFiscale(request.getDettaglioPratica().getAziendaRichiedente().getCodiceFiscale());
	    imp.setPartitaIva(request.getDettaglioPratica().getAziendaRichiedente().getPartitaIva());
	    imp.setRagioneSociale(request.getDettaglioPratica().getAziendaRichiedente().getRagioneSociale());
	    if (request.getDettaglioPratica().getAziendaRichiedente().getNaturaGiuridica() != null) {
		FormaGiuridica fg = new FormaGiuridica();
		fg.setCodice(request.getDettaglioPratica().getAziendaRichiedente().getNaturaGiuridica());
		imp.setFormaGiuridica(fg);
	    }
	    if (request.getDettaglioPratica().getAziendaRichiedente().getIscrizioneREA() != null) {
		CodiceREA codiceREA = new CodiceREA();
		codiceREA.setProvincia(request.getDettaglioPratica().getAziendaRichiedente().getIscrizioneREA().getSiglaProvincia());
		codiceREA.setValue(request.getDettaglioPratica().getAziendaRichiedente().getIscrizioneREA().getNumero());
		imp.setCodiceREA(codiceREA);
	    }
	    if (request.getDettaglioPratica().getAziendaRichiedente().getSedeLegale() != null) {
		IndirizzoConRecapiti indirizzo = new IndirizzoConRecapiti();
		if (request.getDettaglioPratica().getAziendaRichiedente().getSedeLegale().getComune() != null) {
		    Comune comune = new Comune();
		    if (request.getDettaglioPratica().getAziendaRichiedente().getSedeLegale().getComune().getComune() != null) {
			comune.setValue(request.getDettaglioPratica().getAziendaRichiedente().getSedeLegale().getComune().getComune());
		    }
		    if (request.getDettaglioPratica().getAziendaRichiedente().getSedeLegale().getComune().getCodiceCatastale() != null) {
			comune.setCodiceCatastale(request.getDettaglioPratica().getAziendaRichiedente().getSedeLegale().getComune()
				.getCodiceCatastale());
		    }
		    if (request.getDettaglioPratica().getAziendaRichiedente().getSedeLegale().getComune().getCodiceIstat() != null) {
			comune.setCodiceIstat(request.getDettaglioPratica().getAziendaRichiedente().getSedeLegale().getComune().getCodiceIstat());
		    }
		    indirizzo.setComune(comune);
		}
		imp.setIndirizzo(indirizzo);
	    }
	}
	return imp;
    }

    private BigInteger getCodiceAccreditamento() {

	// TODO Auto-generated method stub
	return null;
    }

    private String getCodiceAoo() {

	// TODO Auto-generated method stub
	return null;
    }

    private String getCodiceIPA() {

	// TODO Auto-generated method stub
	return null;
    }

    private String normalizzaProtocollo(String numeroProtocollo) {

	if (StringUtils.isBlank(numeroProtocollo)) {
	    return "";
	}
	String patternStr = "^([0-9]+)";
	Pattern pattern = Pattern.compile(patternStr);
	Matcher matcher = pattern.matcher(numeroProtocollo);
	if (matcher.find()) {
	    numeroProtocollo = (matcher.group());
	}
	if (numeroProtocollo.length() > 7) {
	    logAndThrowException(
		    "Numero protocollo dell'istanza non valido (> 7 caratteri). E' obbligatorio per la comunicazione con il Registro Imprese.",
		    getClass());
	}
	if (numeroProtocollo.length() < 7) {
	    numeroProtocollo = StringUtils.repeat("0", (7 - numeroProtocollo.length())) + numeroProtocollo;
	}
	return numeroProtocollo;
    }

    public static void logAndThrowException(String message, Exception e, Class<?> c) {

	LoggerFactory.getLogger(c).error(message + ": {}\n{}", e.getMessage(), e);
	throw new RuntimeException(message + ": " + e.getMessage(), e);
    }

    public static void logAndThrowException(String message, Class<?> c) {

	LoggerFactory.getLogger(c).error(message);
	throw new RuntimeException(message);
    }

    private ImpiantoProduttivo popolaImpiantiProduttivi() {

	ImpiantoProduttivo ip = null;
	if (request.getDettaglioPratica().getLocalizzazione() != null) {
	    if (request.getDettaglioPratica().getLocalizzazione().size() > 1) {
		LocalizzazioneNelComuneType loc = request.getDettaglioPratica().getLocalizzazione().get(0);
		ip = new ImpiantoProduttivo();
		Indirizzo indirizzo = new Indirizzo();
		indirizzo.setDenominazioneStradale(loc.getDenominazione());
		indirizzo.setNumeroCivico(loc.getCivico());
		if (request.getDettaglioPratica().getCodiceComune() != null) {
		    Comune comune = new Comune();
		    if (request.getDettaglioPratica().getCodiceComune().getComune() != null) {
			comune.setValue(request.getDettaglioPratica().getCodiceComune().getComune());
		    }
		    if (request.getDettaglioPratica().getCodiceComune().getCodiceCatastale() != null) {
			comune.setCodiceCatastale(request.getDettaglioPratica().getCodiceComune().getCodiceCatastale());
		    }
		    if (request.getDettaglioPratica().getCodiceComune().getCodiceIstat() != null) {
			comune.setCodiceIstat(request.getDettaglioPratica().getCodiceComune().getCodiceIstat());
		    }
		    indirizzo.setComune(comune);
		}
		ip.setIndirizzo(indirizzo);
	    }
	}
	return ip;
    }

    private void gestisciAllegatiSUAPXml(AdempimentoSUAP adempimentoPrincipale, String codicePratica) {

	ModelloAttivita mda = new ModelloAttivita();
	mda.setNomeFile(codicePratica + ".MDA.PDF");
	TracciatoXml txml = new TracciatoXml();
	txml.setNomeFile(codicePratica + ".MDA.XML");
	txml.setMime("text/xml");
	mda.setTracciatoXml(txml);
	adempimentoPrincipale.setDistintaModelloAttivita(mda);
	AtomicInteger i = new AtomicInteger(0);
	List<DocumentiType> docs = request.getDettaglioPratica().getDocumenti();
	for (DocumentiType doc : docs) {
	    String tipoDoc = "";
	    if (StringUtils.isNotBlank(doc.getTipoDocumento())) {
		tipoDoc = doc.getTipoDocumento();
	    }
	    if (tipoDoc.equalsIgnoreCase("MDA-PDF")) {
		mda.setNomeFileOriginale(doc.getAllegati().getAllegato());
		continue;
	    }
	    String nomefile = doc.getAllegati().getAllegato();
	    if (StringUtils.isNotBlank(nomefile)) {
		AllegatoGenerico ag = new AllegatoGenerico();
		ag.setNomeFile(codicePratica + "." + i.incrementAndGet() + nomefile.substring(nomefile.lastIndexOf(".")));
		ag.setNomeFileOriginale(doc.getAllegati().getAllegato());
		adempimentoPrincipale.getDocumentoAllegato().add(ag);
	    }
	}
    }

    private void createModelloAttivitaXML(String codicePratica, File zipDir) throws Exception {

	try {
	    log.debug("createModelloAttivitaXML(): codicePratica={}", codicePratica);
	    File modelloAttivitaFile = new File(zipDir, codicePratica + ".MDA.xml");
	    JAXBContext jc = JAXBContext.newInstance(PraticaSTC.class);
	    DettaglioPraticaType dettaglioPraticaType = request.getDettaglioPratica();
	    PraticaSTC praticaSTC = new PraticaSTC();
	    praticaSTC.setDettaglioPratica(dettaglioPraticaType);
	    Marshaller m = jc.createMarshaller();
	    FileOutputStream fos = new FileOutputStream(modelloAttivitaFile);
	    m.marshal(praticaSTC, fos);
	    fos.close();
	} catch (Exception e) {
	    log.error("createModelloAttivitaXML(): {}", e.getMessage());
	    throw e;
	}
    }

    private File createZipArchive(String codicePratica) throws Exception {

	//rinomino i doc allegati secondo dpr
	renameDocumenti(codicePratica);
	//creo e aggiungo agli allegati quelli mancanti
	// salvo tutti gli allegati su filesystem in una cartella con nome = <codice-pratica>-<timestamp>
	String zipArchiveName = codicePratica + ".SUAP.zip";
	File zipDir = FileUtil.createFolder(codicePratica + "-" + System.currentTimeMillis());
	createModelloAttivitaXML(codicePratica, zipDir);
	createModelloRiepilogoXML(codicePratica, zipDir);
	if (request.getDettaglioPratica().getDocumenti() != null) {
	    for (DocumentiType doc : request.getDettaglioPratica().getDocumenti()) {
		if (doc.getAllegati() != null && doc.getAllegati().getFile() != null && doc.getAllegati().getFile().getBinaryData() != null) {
		    try {
			FileUtil.saveFile(doc.getAllegati().getAllegato(), doc.getAllegati().getFile().getBinaryData().getInputStream(), zipDir);
		    } catch (IOException e) {
			log.error("createZipArchive: {}", e.getMessage());
		    }
		}
	    }
	}
	// creo lo zip con tutti gli allegati
	File zipFile = FileUtil.createZipArchive(zipArchiveName, zipDir);
	FileUtil.deleteAllFiles(zipDir);
	return zipFile;
    }

    /**
     * ritorna il codice pratica nel formato:<br />
     * &lt;codice-fiscale-impresa&gt;-&lt;GGMMAAAA-HHMM&gt;
     * 
     * @return
     */
    private String getCodicePratica() {

	StringBuffer buf = new StringBuffer();
	String cf = getCFOPIAzienda();
	buf.append(cf);
	buf.append("-");
	buf.append(DateUtil.formatDate(request.getDettaglioPratica().getDataPratica(), "ddMMyyyy-HHmm"));
	return buf.toString();
    }

    private String getCFOPIAzienda() {

	String cf = "";
	if (request.getDettaglioPratica().getAziendaRichiedente() != null) {
	    cf = request.getDettaglioPratica().getAziendaRichiedente().getCodiceFiscale();
	    if (StringUtils.isBlank(cf)) {
		cf = request.getDettaglioPratica().getAziendaRichiedente().getPartitaIva();
	    }
	}
	if (StringUtils.isBlank(cf)) {
	    log.error("Non è stato possibile recuperare il codicefiscale o la partitaIva dell'azienda");
	    throw new RuntimeException("Non è stato possibile recuperare il codicefiscale o la partitaIva dell'azienda");
	}
	return cf.toUpperCase();
    }

    private String getIdentificativoSUAP() {

	String id = "";
	List<ParametroType> altriDati = request.getDettaglioPratica().getAltriDati();
	if (altriDati != null) {
	    for (ParametroType parametroType : altriDati) {
		if ("IDENTIFICATIVO_SUAP".equalsIgnoreCase(parametroType.getNome())) {
		    List<ValoreParametroType> valori = parametroType.getValore();
		    if (valori != null) {
			id = valori.get(0).getCodice();
		    }
		}
	    }
	}
	if (StringUtils.isBlank(id)) {
	    log.error("Il parametro IDENTIFICATIVO_SUAP non è stato passato nella sezione AltriDati");
	    throw new RuntimeException("Il parametro IDENTIFICATIVO_SUAP non è stato passato nella sezione AltriDati");
	}
	return id;
    }

    public static XMLGregorianCalendar convertFromGregorianCalendar(GregorianCalendar c) {

	XMLGregorianCalendar anno = null;
	try {
	    anno = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	} catch (DatatypeConfigurationException e) {
	    logAndThrowException("Errore nella trasformazione di  xmlgregoriancalendar [" + c + "] a causa di " + e.getMessage(), e, Utilities.class);
	}
	return anno;
    }
}
