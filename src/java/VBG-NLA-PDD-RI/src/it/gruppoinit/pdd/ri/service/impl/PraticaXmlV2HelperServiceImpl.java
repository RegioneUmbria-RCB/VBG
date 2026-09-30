package it.gruppoinit.pdd.ri.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.domain.helper.PraticaXmlHelper;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.CodiceREA;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Comune;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.EMail;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Indirizzo;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.IndirizzoConRecapiti;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Provincia;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Sesso;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Stato;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.Telefono;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.AdempimentoSUAP;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.AllegatoGenerico;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.AnagraficaImpresa;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.AnagraficaPersona;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.AnagraficaRappresentante;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.Carica;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.EstremiDichiarante;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.EstremiSuap;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.FormaGiuridica;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.ImpiantoProduttivo;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.ImpiantoProduttivo.DatiCatastali;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.ImpiantoProduttivo.DatiCatastali.Sezione;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.Intestazione;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.ModelloAttivita;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.ModelloAttivita.TracciatoXml;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.OggettoComunicazione;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.RiepilogoPraticaSUAP;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.Struttura;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.TipoIntervento;
import it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.VersioneSchema;
import it.gruppoinit.pdd.ri.service.PraticaXmlHelperService;
import it.gruppoinit.pdd.utils.AllegatoHelper;
import it.gruppoinit.pdd.utils.ConfigurazioneHelper;
import it.gruppoinit.pdd.utils.EndoIstanzaHelper;
import it.gruppoinit.pdd.utils.IstanzaAllegatiHelper;
import it.gruppoinit.pdd.utils.IstanzaAllegatiHelperLoader;
import it.gruppoinit.pdd.utils.IstanzaHelper;
import it.gruppoinit.pdd.utils.LocalizzazioniIstanzaHelper;
import it.gruppoinit.pdd.utils.RegistroImpreseDataHelper;
import it.gruppoinit.pdd.utils.RichiedenteHelper;
import it.gruppoinit.pdd.utils.StpEndoHelperLoader;
import it.gruppoinit.pdd.utils.StpEndoTipo1Helper;
import it.gruppoinit.pdd.utils.StpEndoTipo2Helper;
import it.gruppoinit.pdd.utils.TIPO_PRATICA;
import it.gruppoinit.pdd.utils.Utilities;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

public class PraticaXmlV2HelperServiceImpl implements PraticaXmlHelperService<RiepilogoPraticaSUAP> {

    private static Logger log = LoggerFactory.getLogger(PraticaXmlV2HelperServiceImpl.class);
    private static final String VERSIONE_XSD_SUAP = "2.0.0";
    private static final String PRATICA_SUAP_1_0_1_XSD = "pratica_suap-2.0.0.xsd";
    private static final String WSDL_REGISTROIMPRESE_FOLDER = "wsdl/registroimprese/";
    private static XMLGregorianCalendar dataVersioneSuap;
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;
    static {
	GregorianCalendar cSuap = (GregorianCalendar) Calendar.getInstance();
	cSuap.set(Calendar.YEAR, 2012);
	cSuap.set(Calendar.MONTH, 4);
	cSuap.set(Calendar.DATE, 4);
	dataVersioneSuap = Utilities.convertFromGregorianCalendar(cSuap);
    }

    public PraticaXmlV2HelperServiceImpl(SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient) {

	this.sigeproSecurityWebServiceClient = sigeproSecurityWebServiceClient;
    }

    @Override
    public Class<RiepilogoPraticaSUAP> getEntityClass() {

	return RiepilogoPraticaSUAP.class;
    }

    @Override
    public PraticaXmlHelper<RiepilogoPraticaSUAP> getPraticaXmlHelper(List<AllegatoHelper> alls, IstanzaHelper ihelper,
	    ConfigurazioneHelper confHelper, boolean isEffettuaValidazione, TIPO_PRATICA tipoPRATICA, GetDbConnectionInfoResponse dbconninfo) {

	log.debug("createPraticaSUAPXml# Validazione attiva {}", isEffettuaValidazione);
	RiepilogoPraticaSUAP praticaSUAP = new RiepilogoPraticaSUAP();
	VersioneSchema versione = new VersioneSchema();
	versione.setVersione(VERSIONE_XSD_SUAP);
	versione.setData(dataVersioneSuap);
	praticaSUAP.setInfoSchema(versione);
	Intestazione intestazione = new Intestazione();
	EstremiSuap ufficioSUAP = new EstremiSuap();
	ufficioSUAP.setCodiceAmministrazione(confHelper.getCodiceAmministrazioneIpa());
	ufficioSUAP.setCodiceAoo(confHelper.getCodiceAoo());
	ufficioSUAP.setIdentificativoSuap(confHelper.convertCodiceAccreditamentoToBigInteger(isEffettuaValidazione));
	intestazione.setUfficioDestinatario(ufficioSUAP);
	AnagraficaImpresa aimp = popolatedatiImpresaPerSuapXml(ihelper, isEffettuaValidazione);
	if (aimp != null) {
	    intestazione.setImpresa(aimp);
	} else {
	    intestazione.setRichiedente(popolaRichiedentePerSuapXML(ihelper));
	}
	OggettoComunicazione ocr = new OggettoComunicazione();
	ocr.setValue(ihelper.getLavori());
	intestazione.setOggettoComunicazione(ocr);
	TipoIntervento ti = decodeTipoIntervento(ihelper);
	if (ti != null) {
	    ocr.setTipoIntervento(ti);
	}
	String tipoProcedimento = decodeTipoProcedimento(ihelper);
	if (StringUtils.isNotBlank(tipoProcedimento)) {
	    ocr.setTipoProcedimento(tipoProcedimento);
	}
	/* REMEMBER pag 24 stef STEF-interazione_SUAP_RI_bozza_v2.pdf  privo del codice-pratica
	intestazione.setCodicePratica("")
	*/
	// al momento non obbligatorio intestazione.setProcuraSpeciale(null)
	intestazione.setDichiarante(popolaDatiDichiaranteSuapXml(ihelper));
	if (StringUtils.isNotBlank(ihelper.getDomicilioElettronico())) {
	    intestazione.setDomicilioElettronico(ihelper.getDomicilioElettronico());
	}
	if (StringUtils.isNotBlank(ihelper.getNumeroprotocollo())) {
	    it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.ProtocolloSUAP pSuap = new it.gruppoinit.impresainungiorno.schema.suap.pratica.v_2_0_0.ProtocolloSUAP();
	    pSuap.setCodiceAmministrazione(confHelper.getCodiceAmministrazioneIpa());
	    pSuap.setCodiceAoo(confHelper.getCodiceAoo());
	    // il numero protocollo dell'istanza deve essere sempre presente
	    String numeroProtocollo = RegistroImpreseDataHelper.normalizzaProtocollo(ihelper.getNumeroprotocollo(), true);
	    pSuap.setNumeroRegistrazione(numeroProtocollo);
	    if (ihelper.getDataprotocollo() != null) {
		try {
		    GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
		    c.setTime(ihelper.getDataprotocollo());
		    pSuap.setDataRegistrazione(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		} catch (DatatypeConfigurationException e) {
		    throw new RuntimeException("Errore nella creazione dell'oggetto XMLGregorianCalendar: " + e.getMessage(), e);
		}
	    } else {
		Utilities.logAndThrowException(
			"Non è stata settata la data di protocollo dell'istanza. E' obbligatorio per la comunicazione con il Registro Imprese.",
			getClass());
	    }
	    intestazione.setProtocollo(pSuap);
	}
	intestazione.setImpiantoProduttivo(popolaImpiantiProduttivi(ihelper, isEffettuaValidazione));
	praticaSUAP.setIntestazione(intestazione);
	Struttura struttura = new Struttura();
	switch (tipoPRATICA) {
	case MODULO_UNICO:
	    populateStrutturaDefault(struttura, ihelper, isEffettuaValidazione, alls);
	    break;
	case REGIONE_TOSCANA:
	case TUTTI_MODULI:
	    if (ihelper.getDataprotocollo() == null && isEffettuaValidazione) {
		Utilities.logAndThrowException(
			"Non è stata settata la data di protocollo dell'istanza. E' obbligatorio per la comunicazione con il Registro Imprese.",
			getClass());
	    }
	    struttura = populateStrutturaRegioneToscana(struttura, ihelper, isEffettuaValidazione, dbconninfo);
	    // Nel caso di regione toscana la data dello schema la sostituiamo con la data di presentazione istanza
	    try {
		GregorianCalendar cDataPresentazione = (GregorianCalendar) GregorianCalendar.getInstance();
		cDataPresentazione.setTime(ihelper.getData());
		praticaSUAP.getInfoSchema().setData(DatatypeFactory.newInstance().newXMLGregorianCalendar(cDataPresentazione));
	    } catch (DatatypeConfigurationException e) {
		if (isEffettuaValidazione) {
		    throw new RuntimeException("Errore nella creazione dell'oggetto XMLGregorianCalendar: " + e.getMessage(), e);
		}
	    }
	    break;
	default:
	    break;
	}
	praticaSUAP.setStruttura(struttura);
	if (isEffettuaValidazione) {
	    log.debug("creaPraticaXml# Applico validazione su pratica creata");
	    validatePraticaSUAP(praticaSUAP);
	} else {
	    log.debug("creaPraticaXml# Non validazione su pratica creata");
	}
	try {
	    String praticaSUAPString = Utilities.marshallObject(praticaSUAP);
	    return new PraticaXmlHelper<RiepilogoPraticaSUAP>(ihelper.getCodicePraticaTelematica() + ".SUAP.XML", praticaSUAPString, praticaSUAP);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private AnagraficaPersona popolaRichiedentePerSuapXML(IstanzaHelper ihelper) {

	AnagraficaPersona result = new AnagraficaPersona();
	result.setCodiceFiscale(ihelper.getRichiedente().getCodiceFiscale());
	result.setCognome(ihelper.getRichiedente().getCognome());
	// result.setPartitaIva(ihelper.getRichiedente().get)
	result.setNome(ihelper.getRichiedente().getNome());
	if (StringUtils.isNotBlank(ihelper.getRichiedente().getRichComune())) {
	    result.setResidenza(popolaResidenza(ihelper.getRichiedente()));
	}
	result.setSesso(sessoFromCF(ihelper.getRichiedente().getCodiceFiscale()));
	return result;
    }

    private Sesso sessoFromCF(String codiceFiscale) {

	if (StringUtils.defaultString(codiceFiscale).length() != 16) {
	    return null;
	}
	String giornonascita = codiceFiscale.substring(9, 11);
	if (StringUtils.isNotBlank(giornonascita) && //
		giornonascita.matches("^-?(\\d)+$") && //
		Integer.parseInt(giornonascita) > 31) {
	    return Sesso.F;
	}
	return Sesso.M;
    }

    private Indirizzo popolaResidenza(RichiedenteHelper richiedente) {

	Indirizzo residenza = new Indirizzo();
	Comune c = new Comune();
	c.setCodiceCatastale(richiedente.getRichComuneCodiceCatastale());
	residenza.setComune(c);
	return residenza;
    }

    private Source toSource(Object obj) throws JAXBException, IOException {

	if (log.isDebugEnabled()) {
	    log.debug("toSource# {}", obj);
	    String xmlString = Utilities.marshallObject(obj);
	    log.debug("toSource# xml={}", xmlString);
	}
	return new JAXBSource(JAXBContext.newInstance(obj.getClass()), obj);
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

    private AnagraficaImpresa popolatedatiImpresaPerSuapXml(IstanzaHelper ihelper, boolean isEffettuaValidazione) {

	log.debug("popolatedatiImpresaPerSuapXml# Validazione attiva {}", isEffettuaValidazione);
	AnagraficaImpresa result = null;
	if (ihelper.getImpresa() != null) {
	    result = new AnagraficaImpresa();
	    String denominazione = ihelper.getImpresa().getDenominazione();
	    String codiceFiscale = ihelper.getImpresa().getCodiceFiscale();
	    String partitaIva = ihelper.getImpresa().getPartitaIva();
	    String provinciaRea = ihelper.getImpresa().getProvinciaREA();
	    String nrRea = ihelper.getImpresa().getNumREA();
	    String formagiuridica = ihelper.getImpresa().getCodiceFormaGiuridicaCCIAA();
	    FormaGiuridica fg = new FormaGiuridica();
	    fg.setCodice(formagiuridica);
	    result.setFormaGiuridica(fg);
	    result.setRagioneSociale(denominazione);
	    if (StringUtils.isBlank(codiceFiscale) && StringUtils.isBlank(partitaIva) && isEffettuaValidazione) {
		Utilities
			.logAndThrowException(
				"Attenzione! La scheda dell' impresa " + denominazione + " [cf: " + codiceFiscale + ", piva: " + partitaIva +
					      "] non ha definito il valore di partita iva o codicefiscale obbligatori per effettuare la comunicazione al registro imprese.",
				getClass());
	    }
	    if (StringUtils.isNotBlank(codiceFiscale)) {
		result.setCodiceFiscale(codiceFiscale.toUpperCase());
	    } else if (StringUtils.isNotBlank(partitaIva)) {
		result.setPartitaIva(partitaIva.toUpperCase());
	    } else if (StringUtils.isNotBlank(nrRea)) {
		CodiceREA rea = new CodiceREA();
		rea.setValue(nrRea);
		rea.setProvincia(provinciaRea);
		Date datarea = ihelper.getImpresa().getDataIscrizioneREA();
		if (datarea != null) {
		    try {
			GregorianCalendar ca = (GregorianCalendar) GregorianCalendar.getInstance();
			ca.setTime(datarea);
			XMLGregorianCalendar datairc = DatatypeFactory.newInstance().newXMLGregorianCalendar(ca);
			rea.setDataIscrizione(datairc);
		    } catch (DatatypeConfigurationException e) {
			throw new RuntimeException("Errore nella creazione dell'oggetto XMLGregorianCalendar: " + e.getMessage(), e);
		    }
		}
		result.setCodiceREA(rea);
	    }
	    IndirizzoConRecapiti irv = new IndirizzoConRecapiti();
	    Stato stato = new Stato();
	    if (StringUtils.defaultString(ihelper.getImpresa().getResidenzaComuneSiglaProv()).equalsIgnoreCase("EE")) { // stato estero
		stato.setCodiceCatastale(ihelper.getImpresa().getResidenzaComuneCodCatastale());
		stato.setCodice(ihelper.getImpresa().getResidenzaComuneCodCatastale());
		irv.setCittaStraniera(ihelper.getImpresa().getResidenzaComune());
	    } else {
		// COMUNE E PROVINCIA SOLAMENTE SE CITTA' ITALIANA
		stato.setCodice("IT");
		stato.setValue("ITALIA");
		Comune comune = new Comune();
		comune.setCodiceCatastale(ihelper.getImpresa().getResidenzaComuneCodCatastale());
		comune.setValue(ihelper.getImpresa().getResidenzaComune());
		irv.setComune(comune);
		Provincia provincia = new Provincia();
		if (StringUtils.isBlank(ihelper.getImpresa().getResidenzaComuneSiglaProv()) && isEffettuaValidazione) {
		    Utilities
			    .logAndThrowException("Attenzione! La scheda dell'impresa " + denominazione +
						  " non ha definito il valore di residenza.provincia obbligatorio per effettuare la comunicazione al registro imprese.",
				    getClass());
		}
		provincia.setSigla(ihelper.getImpresa().getResidenzaComuneSiglaProv());
		provincia.setValue(ihelper.getImpresa().getResidenzaComuneProv());
		irv.setProvincia(provincia);
	    }
	    irv.setStato(stato);
	    boolean isCAPPresente = false;
	    if (StringUtils.isNotBlank(ihelper.getImpresa().getIndirizzoCAP())) {
		irv.setCap(ihelper.getImpresa().getIndirizzoCAP());
		isCAPPresente = true;
	    }
	    popolateIndirizzoFromDenominazione(irv, ihelper.getImpresa().getIndirizzoDenominazione(), isCAPPresente);
	    //* telefono non obbligatorio anche perché si aspetta solo numeri
	    if (StringUtils.isNotBlank(ihelper.getImpresa().getTelefono())) {
		Telefono t = new Telefono();
		t.setTipo("Ufficio");
		t.setValue(ihelper.getImpresa().getTelefono());
		irv.getTelefono().add(t);
	    }
	    if (StringUtils.isNotBlank(ihelper.getImpresa().getTelefonocellulare())) {
		Telefono t = new Telefono();
		t.setTipo("Cellulare");
		t.setValue(ihelper.getImpresa().getTelefonocellulare());
		irv.getTelefono().add(t);
	    }
	    if (StringUtils.isNotBlank(ihelper.getImpresa().getFax())) {
		Telefono t = new Telefono();
		t.setTipo("Fax");
		t.setValue(ihelper.getImpresa().getFax());
		irv.getTelefono().add(t);
	    }
	    // */
	    if (StringUtils.isNotBlank(ihelper.getImpresa().getEmail())) {
		EMail e = new EMail();
		e.setTipo("Standard");
		e.setValue(ihelper.getImpresa().getEmail());
		irv.getEMail().add(e);
	    }
	    if (StringUtils.isNotBlank(ihelper.getImpresa().getPec())) {
		EMail e = new EMail();
		e.setTipo("PEC");
		e.setValue(ihelper.getImpresa().getPec());
		irv.getEMail().add(e);
	    }
	    result.setIndirizzo(irv);
	    AnagraficaRappresentante lrapp = new AnagraficaRappresentante();
	    Carica carica = new Carica();
	    carica.setCodice(ihelper.getRichiedente().getCodiceCarica());
	    // carica.setValue(ihelper.getRichiedente().getCarica()); NON OBBLIGATORIO
	    lrapp.setCarica(carica);
	    lrapp.setNome(ihelper.getRichiedente().getNome());
	    lrapp.setCodiceFiscale(ihelper.getRichiedente().getCodiceFiscale());
	    lrapp.setCognome(ihelper.getRichiedente().getCognome());
	    result.setLegaleRappresentante(lrapp);
	} else {
	    log.warn("Impresa non presente per l'istanza [codice={}, idcomune={}]", ihelper.getCodiceIstanza(), ihelper.getIdcomune());
	}
	if (log.isDebugEnabled()) {
	    log.debug("popolatedatiImpresaPerRea# Oggetto Impresa= {}", ReflectionToStringBuilder.toString(result, ToStringStyle.SHORT_PREFIX_STYLE));
	}
	return result;
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

    private ImpiantoProduttivo popolaImpiantiProduttivi(IstanzaHelper ihelper, boolean isEffettuaValidazione) {

	log.debug("popolaImpiantiProduttivi# Validazione attiva {}", isEffettuaValidazione);
	LocalizzazioniIstanzaHelper impianto = ihelper.getImpianto();
	ImpiantoProduttivo result = null;
	if (impianto != null) {
	    result = new ImpiantoProduttivo();
	    Indirizzo indirizzo = new Indirizzo();
	    /////////////////////////////
	    Stato stato = new Stato();
	    if (StringUtils.defaultString(ihelper.getIstComuneSiglaProvincia()).equalsIgnoreCase("EE")) { // stato estero
		stato.setCodiceCatastale(ihelper.getIstComuneCodiceCatastale());
		stato.setCodice(ihelper.getIstComuneCodiceCatastale());
		indirizzo.setCittaStraniera(ihelper.getIstComune());
	    } else {
		// COMUNE E PROVINCIA SOLAMENTE SE CITTA' ITALIANA
		stato.setCodice("IT");
		stato.setValue("ITALIA");
		Comune comune = new Comune();
		comune.setCodiceCatastale(ihelper.getIstComuneCodiceCatastale());
		comune.setValue(ihelper.getIstComune());
		indirizzo.setComune(comune);
		Provincia provincia = new Provincia();
		if (StringUtils.isBlank(ihelper.getIstComuneSiglaProvincia()) && isEffettuaValidazione) {
		    Utilities.logAndThrowException(
			    "Attenzione! L'istanza non ha definito il valore di provincia obbligatorio per effettuare la comunicazione al registro imprese.",
			    getClass());
		}
		provincia.setSigla(ihelper.getIstComuneSiglaProvincia());
		provincia.setValue(ihelper.getIstComuneProvincia());
		indirizzo.setProvincia(provincia);
	    }
	    indirizzo.setStato(stato);
	    boolean isCAPPresente = false;
	    if (StringUtils.isNotBlank(impianto.getCap())) {
		indirizzo.setCap(impianto.getCap());
		isCAPPresente = true;
	    }
	    ////////////////////////////
	    if (isCAPPresente) {
		indirizzo.setToponimo(impianto.getPrefisso());
		indirizzo.setDenominazioneStradale(impianto.getIndirizzo());
	    } else {
		indirizzo.setDenominazioneStradale(impianto.getPrefisso() + " " + impianto.getIndirizzo());
	    }
	    if (StringUtils.isBlank(impianto.getCivico()) && isEffettuaValidazione) {
		Utilities.logAndThrowException("Attenzione! L'istanza non ha definito il numero civico nella localizzazione.", getClass());
	    }
	    indirizzo.setNumeroCivico(impianto.getCivico());
	    result.setIndirizzo(indirizzo);
	    if (StringUtils.isNotBlank(impianto.getTipoCatasto())) {
		DatiCatastali dc = new DatiCatastali();
		String tipoCatasto = impianto.getTipoCatasto().equalsIgnoreCase("f") ? "fabbricati" : "terreni";
		dc.setTipo(tipoCatasto);
		dc.setFoglio(impianto.getFoglio());
		if (StringUtils.isNotBlank(impianto.getSezione())) {
		    Sezione sezione = new Sezione();
		    sezione.setValue(impianto.getSezione());
		    dc.setSezione(sezione);
		}
		if (StringUtils.isNotBlank(impianto.getParticella())) {
		    // obbligatorio
		    dc.getMappale().add(impianto.getParticella());
		} else {
		    if (isEffettuaValidazione) {
			Utilities.logAndThrowException(
				"Non sono stati definiti correttamente i dati catastali dell'istanza. La particella è obbligatoria.", getClass());
		    }
		}
		if (StringUtils.isNotBlank(impianto.getSub())) {
		    dc.getSubalterno().add(impianto.getSub());
		}
		result.getDatiCatastali().add(dc);
	    }
	}
	return result;
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

    private EstremiDichiarante popolaDatiDichiaranteSuapXml(IstanzaHelper ihelper) {

	EstremiDichiarante result = null;
	if (ihelper.getRichiedente() != null) {
	    result = new EstremiDichiarante();
	    result.setNome(ihelper.getRichiedente().getNome());
	    result.setCognome(ihelper.getRichiedente().getCognome());
	    if (StringUtils.defaultString(ihelper.getRichiedente().getCodiceFiscale()).length() == 16) {
		result.setCodiceFiscale(ihelper.getRichiedente().getCodiceFiscale());
	    } else {
		Stato stato = new Stato();
		stato.setCodiceCatastale(ihelper.getRichiedente().getRichNascComuneCodiceCatastale());
		result.setSenzaCodiceFiscale(stato);
	    }
	    RegistroImpreseDataHelper ridh = new RegistroImpreseDataHelper();
	    String tipoSoggetto = StringUtils.defaultString(ihelper.getRichiedente().getTipoSoggetto()).toUpperCase().trim();
	    String qualifica = "";
	    if (StringUtils.isNotBlank(tipoSoggetto)) {
		qualifica = ridh.getQualifiche().get(tipoSoggetto);
	    }
	    if (StringUtils.isBlank(qualifica)) {
		qualifica = RegistroImpreseDataHelper.QUALIFICA_DEFAULT_VALUE_ALTRO;
	    }
	    result.setQualifica(qualifica);
	    /* NON OBBLIGATORIO SI ASPETTA TUTTE CIFRE
	    if (StringUtils.isNotBlank(ihelper.getRichiedente().getTelefono())) {
	    result.setTelefono(ihelper.getRichiedente().getTelefono())
	    }
	    */
	    if (StringUtils.isNotBlank(ihelper.getRichiedente().getPec())) {
		result.setPec(ihelper.getRichiedente().getPec());
	    }
	} else {
	    log.error("Richiedente non presente per l'istanza [codice=" + ihelper.getCodiceIstanza().intValue() + ", idcomune=" +
		      ihelper.getIdcomune() + "]");
	    throw new RuntimeException(
		    "Attenzione! Non è stato definito nessun richiedente per l'istanza [codice=" + ihelper.getCodiceIstanza().intValue() +
				       ", idcomune=" + ihelper.getIdcomune() + "]. Per effettuare la comunicazione REA è necessario specificarla.");
	}
	return result;
    }

    private Struttura populateStrutturaRegioneToscana(Struttura struttura, IstanzaHelper ihelper, boolean isEffettuaValidazione,
	    GetDbConnectionInfoResponse dbconninfo) {

	log.debug("populateStrutturaRegioneToscana# populateStrutturaRegioneToscana....");
	Connection c = sigeproSecurityWebServiceClient.getConnection(dbconninfo.getAlias());
	StpEndoHelperLoader stpEndoHelperLoader = new StpEndoHelperLoader(c);
	Integer codiceIntervento = ihelper.getCodiceIntervento();
	log.debug("populateStrutturaRegioneToscana# Recupero l'stp endo tipo 2 dal codice intervento {}", codiceIntervento);
	StpEndoTipo2Helper stpEndoTipo2Helper = stpEndoHelperLoader.findStpEndoTipo2Helper(ihelper.getIdcomune(), dbconninfo.getDbOwner(),
		codiceIntervento);
	log.debug("populateStrutturaRegioneToscana#Popolo adempimento relativo all'attività");
	AdempimentoSUAP adempimentoAttivita = new AdempimentoSUAP();
	Integer codiceEndoDellAttivita = null;
	if (stpEndoTipo2Helper != null) {
	    adempimentoAttivita.setCod(stpEndoTipo2Helper.getCodiceEndoRegionale().trim());
	    adempimentoAttivita.setNome(stpEndoTipo2Helper.getDescrizioneInventario());
	    codiceEndoDellAttivita = stpEndoTipo2Helper.getCodiceInventario();
	} else {
	    log.debug(
		    "populateStrutturaRegioneToscana#Record in sto endo tipo 2 non presente per codice intervento {}. Riporto solo la descrizione dell'intervento",
		    codiceIntervento);
	    adempimentoAttivita.setNome(ihelper.getIntervento());
	}
	IstanzaAllegatiHelperLoader iallhlo = new IstanzaAllegatiHelperLoader(c);
	log.debug("populateStrutturaRegioneToscana#Recupero allegati in documenti istanza, movimenti allegati e procure");
	List<IstanzaAllegatiHelper> allegati = iallhlo.popolateAllegatiIstanzaAndProcure(dbconninfo.getIdComune(),
		dbconninfo.getDbOwner(), ihelper.getCodiceIstanza());
	List<AllegatoHelper> alls = new ArrayList<AllegatoHelper>();
	for (IstanzaAllegatiHelper iah : allegati) {
	    try {
		verificaEstensioneAllegatoGenerico(iah.getNomeFile(), isEffettuaValidazione);
		AllegatoHelper ah = new AllegatoHelper();
		ah.setCodiceOggetto(String.valueOf(iah.getCodiceOggetto()));
		ah.setDescrizioneDocumento(iah.getDescrizioneDocumento());
		ah.setNomefile(iah.getNomeFile());
		ah.setTipoDocumento(iah.getTipo());
		alls.add(ah);
	    } catch (Exception e) {
		log.warn("populateStrutturaRegioneToscana# Il file con nome {} ha un estensione consentita, non verrà preso in considerazione",
			iah.getNomeFile());
	    }
	}
	if (codiceEndoDellAttivita != null) {
	    log.debug("populateStrutturaRegioneToscana# L'intervento è legato ad un endo, recupero  gli allegati dell'endo con codice {}",
		    codiceEndoDellAttivita);
	    List<IstanzaAllegatiHelper> allegatiEndoAttivita = iallhlo.popolateDocumentiEndo( dbconninfo.getIdComune(),
		    dbconninfo.getDbOwner(), ihelper.getCodiceIstanza(), codiceEndoDellAttivita);
	    for (IstanzaAllegatiHelper iah : allegatiEndoAttivita) {
		try {
		    verificaEstensioneAllegatoGenerico(iah.getNomeFile(), isEffettuaValidazione);
		    AllegatoHelper ah = new AllegatoHelper();
		    ah.setCodiceOggetto(String.valueOf(iah.getCodiceOggetto()));
		    ah.setDescrizioneDocumento(iah.getDescrizioneDocumento());
		    ah.setNomefile(iah.getNomeFile());
		    ah.setTipoDocumento(iah.getTipo());
		    alls.add(ah);
		} catch (Exception e) {
		    log.warn("populateStrutturaRegioneToscana# Il file con nome {} ha un estensione consentita, non verrà preso in considerazione",
			    iah.getNomeFile());
		}
	    }
	}
	recuperaMDAPratica(ihelper, alls, adempimentoAttivita, isEffettuaValidazione);
	struttura.getModulo().add(adempimentoAttivita);
	log.debug("populateStrutturaRegioneToscana# Recupero tutti i procedimenti della pratica");
	List<EndoIstanzaHelper> endoIstanzaHelpers = stpEndoHelperLoader.findEndoIstanzaHelper(ihelper.getIdcomune(), dbconninfo.getDbOwner(),
		ihelper.getCodiceIstanza());
	log.debug(
		"populateStrutturaRegioneToscana# Ciclo tutti gli endo per creare una struttura per ogni endo (escludo quello legato all'intervento già trattato)");
	AdempimentoSUAP adempimentoEndo = null;
	for (EndoIstanzaHelper endoIstanzaHelper : endoIstanzaHelpers) {
	    // può accadere il caso che stpEndoTipo2Helper==null quindi questa non sarà mai 
	    if (stpEndoTipo2Helper == null
		    || (stpEndoTipo2Helper != null && !endoIstanzaHelper.getCodiceEndo().equals(stpEndoTipo2Helper.getCodiceInventario()))) {
		adempimentoEndo = new AdempimentoSUAP();
		StpEndoTipo1Helper stpEndoTipo1Helper = stpEndoHelperLoader.findStpEndoTipo1Helper(ihelper.getIdcomune(), dbconninfo.getDbOwner(),
			endoIstanzaHelper.getCodiceEndo());
		if (stpEndoTipo1Helper != null) {
		    adempimentoEndo.setCod(stpEndoTipo1Helper.getCodiceEndoRegionale().trim());
		} else {
		    log.debug("populateStrutturaRegioneToscana# Codice endo regionale non presente. Endo procedimento locale");
		}
		adempimentoEndo.setNome(endoIstanzaHelper.getDescrizioneInventario());
		log.debug("populateStrutturaRegioneToscana# Recupero gli allegati legati all'endo");
		List<IstanzaAllegatiHelper> allegatiEndo = iallhlo.popolateDocumentiEndo(dbconninfo.getIdComune(),
			dbconninfo.getDbOwner(), ihelper.getCodiceIstanza(), endoIstanzaHelper.getCodiceEndo());
		List<AllegatoHelper> allsEndo = new ArrayList<AllegatoHelper>();
		for (IstanzaAllegatiHelper iah : allegatiEndo) {
		    try {
			verificaEstensioneAllegatoGenerico(iah.getNomeFile(), isEffettuaValidazione);
			AllegatoHelper ah = new AllegatoHelper();
			ah.setCodiceOggetto(String.valueOf(iah.getCodiceOggetto()));
			ah.setDescrizioneDocumento(iah.getDescrizioneDocumento());
			ah.setNomefile(iah.getNomeFile());
			ah.setTipoDocumento(iah.getTipo());
			allsEndo.add(ah);
		    } catch (Exception e) {
			log.warn(
				"populateStrutturaRegioneToscana# Il file con nome {} ha un estensione consentita, non verrà preso in considerazione",
				iah.getNomeFile());
		    }
		}
		recuperaMDAPratica(ihelper, allsEndo, adempimentoEndo, isEffettuaValidazione);
		struttura.getModulo().add(adempimentoEndo);
	    }
	}
	return struttura;
    }

    /**
     * Le estensioni ammesse per l'allegato generico sono le seguenti. In caso di altra estensione lancio un errore.
     * 
     * <pre>
     * 	CF-GGMMAAAA-HHMM.NNN.PDF.P7M o  CF-GGMMAAAA-HHMM.NNN.PDF 
     *  Es: DPRTNT00A01A012T-08072010-1733.01243.pdf.p7m
     *  Formati ammessi: pdf; pdf.p7m; xml; dwf; dwf.p7m; svg; svg.p7m; jpg; jpg.p7m
     * </pre>
     */
    private void verificaEstensioneAllegatoGenerico(final String nomefile, boolean isEffettuaValidazione) {

	// NO VALIDAZIONE
	//	String extAllowed = "(([Pp][Dd][Ff])|([Pp][Dd][Ff]\\.[Pp]7[Mm])|([Xx][Mm][Ll])|([Dd][Ww][Ff])|([Dd][Ww][Ff]\\.[Pp]7[Mm])|"
	//		+ "([Ss][Vv][Gg])|([Ss][Vv][Gg]\\.[Pp]7[Mm])|([Jj][Pp][Gg])|([Jj][Pp][Gg]\\.[Pp]7[Mm]))"
	if (StringUtils.isNotBlank(nomefile) && !(nomefile.toLowerCase().endsWith(".pdf") || nomefile.toLowerCase().endsWith(".pdf.p7m")
		|| nomefile.toLowerCase().endsWith(".xml") || nomefile.toLowerCase().endsWith(".dwf") || nomefile.toLowerCase().endsWith(".dwf.p7m")
		|| nomefile.toLowerCase().endsWith(".svg") || nomefile.toLowerCase().endsWith(".svg.p7m") || nomefile.toLowerCase().endsWith(".jpg")
		|| nomefile.toLowerCase().endsWith(".jpg.p7m")) && isEffettuaValidazione) {
	    Utilities
		    .logAndThrowException(
			    "Errore in validazione SUAP.XML: il file \"" + StringUtils.defaultIfEmpty(nomefile, "-nome-file-non-settato-") +
					  "\" non è ammesso per l'invio.\n È possibile inviare solamente file con le seguenti estensioni: \"pdf; pdf.p7m; xml; dwf; dwf.p7m; svg; svg.p7m; jpg; jpg.p7m\"",
			    RegistroImpreseServiceImpl.class);
	}
    }

    /**
     * Il metodo deve trovare il file relativo all'MDA della pratica telematica. deve assegnare il nome file compliant
     * alla nomenclatura DPR160.<br />
     * Es: BCCRCR73H23G888O-22102012-1732.000.MDA.PDF.P7M oppure BCCRCR73H23G888O-22102012-1732.000.MDA.PDF
     * 
     * @param ihelper
     * @param alls
     * @param adempimentoPrincipale
     * @return
     */
    private void recuperaMDAPratica(IstanzaHelper ihelper, List<AllegatoHelper> alls, AdempimentoSUAP adempimentoPrincipale,
	    boolean isEffettuaValidazione) {

	log.debug("recuperaMDAPratica# Validazione attiva {}", isEffettuaValidazione);
	ModelloAttivita mda = new ModelloAttivita();
	String codicePratica = ihelper.getCodicePraticaTelematica();
	boolean trovatoMdaFile = false;
	boolean trovatoMdaXmlFile = false;
	if (StringUtils.isBlank(codicePratica) && isEffettuaValidazione) {
	    Utilities.logAndThrowException("Attenzione!!! Non è stato definito il codicepratica telematico dell'istanza.", getClass());
	}
	for (AllegatoHelper allegato : alls) {
	    String tipoDocumento = StringUtils.defaultString(allegato.getTipoDocumento());
	    if (tipoDocumento.equalsIgnoreCase("MDA-PDF") && !trovatoMdaFile) {
		if (StringUtils.isNotBlank(allegato.getDescrizioneDocumento())) {
		    mda.setDescrizione(allegato.getDescrizioneDocumento());
		}
		String nomefile = ihelper.getCodicePraticaTelematica() + "." + calcolaProgressivo(allegato.getCodiceOggetto());
		if (allegato.getNomefile().toUpperCase().endsWith("PDF")) {
		    nomefile += ".MDA.PDF";
		} else {
		    nomefile += ".MDA.PDF.P7M";
		}
		mda.setNomeFile(nomefile);
		mda.setNomeFileOriginale(allegato.getNomefile());
		mda.setMime(Utilities.getContentType(mda.getNomeFile()));
		adempimentoPrincipale.setDistintaModelloAttivita(mda);
		trovatoMdaFile = true;
	    } else {
		// TRACCIATO XML DEL MODELLO DISTINTA ATTIVITA' DEL MODULO PRINCIPALE
		if (tipoDocumento.equalsIgnoreCase("MDA-XML") && !trovatoMdaXmlFile) {
		    TracciatoXml mdaXml = new TracciatoXml();
		    String nomefile = ihelper.getCodicePraticaTelematica() + "." + calcolaProgressivo(allegato.getCodiceOggetto()) + ".MDA.XML";
		    mdaXml.setDescrizione(StringUtils.defaultIfEmpty(allegato.getDescrizioneDocumento(), ""));
		    mdaXml.setNomeFile(nomefile);
		    mdaXml.setMime(Utilities.getContentType(nomefile));
		    mdaXml.setNomeFileOriginale(allegato.getNomefile());
		    mda.setTracciatoXml(mdaXml);
		    trovatoMdaXmlFile = true;
		} else {
		    // ALTRI ALLEGATI DELLA PRATICA
		    AllegatoGenerico all = new AllegatoGenerico();
		    String estensione = estraiEstensioneDaAllegatoGenerico(allegato.getNomefile(), isEffettuaValidazione);
		    if (StringUtils.isNotBlank(allegato.getDescrizioneDocumento())) {
			all.setDescrizione(allegato.getDescrizioneDocumento());
		    }
		    all.setNomeFile(ihelper.getCodicePraticaTelematica() + "." + calcolaProgressivo(allegato.getCodiceOggetto()) + "." + estensione);
		    all.setNomeFileOriginale(allegato.getNomefile());
		    all.setMime(Utilities.getContentType(allegato.getNomefile()));
		    adempimentoPrincipale.getDocumentoAllegato().add(all);
		}
	    }
	}
	if (!trovatoMdaFile) {
	    // CERCO PER TipoDocumentoType.RIEPILOGO_DOMANDA
	    String nomeFileDuplicato = null;
	    for (AllegatoHelper allegato : alls) {
		String tipoDocumento = StringUtils.defaultString(allegato.getTipoDocumento());
		if (tipoDocumento.equalsIgnoreCase("RiepilogoDomanda")) {
		    if (StringUtils.isNotBlank(allegato.getDescrizioneDocumento())) {
			mda.setDescrizione(allegato.getDescrizioneDocumento());
		    }
		    String nomefile = ihelper.getCodicePraticaTelematica() + "." + calcolaProgressivo(allegato.getCodiceOggetto());
		    if (allegato.getNomefile().toUpperCase().endsWith("PDF")) {
			nomefile += ".MDA.PDF";
		    } else {
			nomefile += ".MDA.PDF.P7M";
		    }
		    mda.setNomeFile(nomefile);
		    mda.setNomeFileOriginale(allegato.getNomefile());
		    nomeFileDuplicato = allegato.getNomefile();
		    mda.setMime(Utilities.getContentType(mda.getNomeFile()));
		    adempimentoPrincipale.setDistintaModelloAttivita(mda);
		    trovatoMdaFile = true;
		    break;
		}
	    }
	    if (trovatoMdaFile) {
		// verifico se ho inserito due volte il file
		List<AllegatoGenerico> s = adempimentoPrincipale.getDocumentoAllegato();
		List<AllegatoGenerico> newList = new ArrayList<AllegatoGenerico>(s);
		for (AllegatoGenerico a : s) {
		    if (a.getNomeFileOriginale().equalsIgnoreCase(nomeFileDuplicato)) {
			log.warn("Trovato un file duplicato {}", nomeFileDuplicato);
			newList.remove(a);
			break;
		    }
		}
		adempimentoPrincipale.getDocumentoAllegato().clear();
		adempimentoPrincipale.getDocumentoAllegato().addAll(newList);
	    }
	}
	if (!trovatoMdaFile && isEffettuaValidazione) {
	    Utilities.logAndThrowException(
		    "Attenzione!!! Non è stato definito nessun file MDA (modello distinta attività) per l'istanza. Il file deve essere in formato PDF o PDF.P7M.",
		    getClass());
	}
    }

    private String calcolaProgressivo(String indice) {

	return StringUtils.repeat("0", (7 - String.valueOf(indice).length())) + indice;
    }

    /**
     * Il metodo tenta di indovinare l'estensione del file tenendo in cosiderazione che il file potrebbe avere più
     * estensioni. Es: "pdf.p7m".<br />
     * È successo a Ravenna che hanno inviato un file con nome <b>doc. vazquez.pdf</b> e il precedente algoritmo tirava
     * fuori come estensione vazquez.pdf.<br />
     * Il metodo è abbastanza triviale e tira fuori fino alla seconda estensione. (se ce ne fossero più il nome file non
     * sarebbe più validato dalla regular expression) Es:
     * 
     * <pre>
     * 	CF-GGMMAAAA-HHMM.NNN.PDF.P7M   o   CF-GGMMAAAA-HHMM.NNN.PDF 
     *  Es: DPRTNT00A01A012T-08072010-1733.01243.pdf.p7m
     *  Formati ammessi: pdf; pdf.p7m; xml; dwf; dwf.p7m; svg; svg.p7m; jpg; jpg.p7m
     * </pre>
     * 
     * @param nomefile
     * @return
     */
    private String estraiEstensioneDaAllegatoGenerico(final String nomefile, boolean isEffettuaValidazione) {

	String estensione = "";
	if (StringUtils.isNotBlank(nomefile)) {
	    if (nomefile.toLowerCase().endsWith(".pdf")) {
		estensione = "pdf";
	    } else if (nomefile.toLowerCase().endsWith(".pdf.p7m")) {
		estensione = "pdf.p7m";
	    } else if (nomefile.toLowerCase().endsWith(".xml")) {
		estensione = "xml";
	    } else if (nomefile.toLowerCase().endsWith(".dwf")) {
		estensione = "dwf";
	    } else if (nomefile.toLowerCase().endsWith(".dwf.p7m")) {
		estensione = "dwf.p7m";
	    } else if (nomefile.toLowerCase().endsWith(".svg")) {
		estensione = "svg";
	    } else if (nomefile.toLowerCase().endsWith(".svg.p7m")) {
		estensione = "svg.p7m";
	    } else if (nomefile.toLowerCase().endsWith(".jpg")) {
		estensione = "jpg";
	    } else if (nomefile.toLowerCase().endsWith(".jpg.p7m")) {
		estensione = "jpg.p7m";
	    }
	}
	verificaEstensioneAllegatoGenerico(nomefile, isEffettuaValidazione);
	return estensione;
    }

    private Struttura populateStrutturaDefault(Struttura struttura, IstanzaHelper ihelper, boolean isEffettuaValidazione, List<AllegatoHelper> alls) {

	log.debug("populateStrutturaDefautl# populateStrutturaDefautl");
	AdempimentoSUAP adempimentoPrincipale = new AdempimentoSUAP();
	// NON OBBLIGATORIO AL MOMENTO NON LO MANDO adempimentoPrincipale.setCod(null)
	// suap_xml codice adempimento principale dove lo prendo?
	adempimentoPrincipale.setNome(ihelper.getIntervento());
	log.debug("populateStrutturaDefautl# recupero mda pratica");
	recuperaMDAPratica(ihelper, alls, adempimentoPrincipale, isEffettuaValidazione);
	struttura.getModulo().add(adempimentoPrincipale);
	return struttura;
    }
}
