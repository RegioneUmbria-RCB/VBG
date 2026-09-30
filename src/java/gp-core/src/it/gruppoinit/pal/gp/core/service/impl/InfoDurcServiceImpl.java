package it.gruppoinit.pal.gp.core.service.impl;

import it.cassaedileweb.serviziodurc.authentication.DURCAuthentication;
import it.cassaedileweb.serviziodurc.check.in.DURCCheckRequest;
import it.cassaedileweb.serviziodurc.check.out.DURCCheckResult;
import it.cassaedileweb.serviziodurc.insert.in.Address;
import it.cassaedileweb.serviziodurc.insert.in.Company;
import it.cassaedileweb.serviziodurc.insert.in.DURCInsertRequest;
import it.cassaedileweb.serviziodurc.insert.in.Institute;
import it.cassaedileweb.serviziodurc.insert.in.Office;
import it.cassaedileweb.serviziodurc.insert.out.DURCInsertResult;
import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.AnagrafedocumentiDurc;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiDurcService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.InfoDurcService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.TipidocumentoService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.DurcNonValidoException;
import it.gruppoinit.pal.gp.core.service.helper.NuovoDURCHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.InfoDurcWSClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoDurcServiceImpl implements InfoDurcService {

    private static final Logger log = LoggerFactory.getLogger(InfoDurcServiceImpl.class);
    private static final String RESULT_VALORE_OPERAZIONE_ESEGUITA = "X";
    private AnagrafedocumentiService anagrafedocumentiService;
    private AnagrafedocumentiDurcService anagrafedocumentiDurcService;
    private AnagrafeService anagrafeService;
    private IstanzeService istanzeService;
    private OggettiService oggettiService;
    private TipidocumentoService tipidocumentoService;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setAnagrafedocumentiDurcService(AnagrafedocumentiDurcService anagrafedocumentiDurcService) {

	this.anagrafedocumentiDurcService = anagrafedocumentiDurcService;
    }

    @Autowired
    public void setAnagrafedocumentiService(AnagrafedocumentiService anagrafedocumentiService) {

	this.anagrafedocumentiService = anagrafedocumentiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setTipidocumentoService(TipidocumentoService tipidocumentoService) {

	this.tipidocumentoService = tipidocumentoService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    /**
     * Ricerca l'anagrafe identificata da quel codice e fa una prevalidazione dei dati obbligatori minimi: <br />
     * - valido solo se Persona Giuridica <br />
     * - codice fiscale non deve essere vuoto <br />
     * - valido se Persona Giuridica
     * 
     * @param codiceAnagrafe
     * @return
     */
    private Anagrafe getDatiAnagrafe(Integer codiceAnagrafe) {

	if (codiceAnagrafe == null) {
	    throw new RuntimeException("Non è stato passato nessun codice anagrafe");
	}
	Anagrafe a = anagrafeService.findById(new PkId(codiceAnagrafe));
	if (a == null) {
	    throw new RuntimeException("Non esiste nessuna anagrafe con codice " + codiceAnagrafe);
	}
	if (StringUtils.defaultString(a.getTipoanagrafe(), WebConstants.PERSONA_FISICA).equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
	    throw new RuntimeException("Le funzionalità DURC sono possibili solamente per persone giuridiche");
	}
	String codiceFiscale = a.getCodicefiscale();
	if (StringUtils.isBlank(codiceFiscale)) {
	    throw new RuntimeException("Non è presente il codice fiscale impresa per l'anagrafe " + a.getDescrizioneRichiedente()
		    + ". Inserire il valore corretto nel campo \"Codice fiscale\".");
	}
	String nominativo = a.getNominativo();
	if (StringUtils.isBlank(nominativo)) {
	    throw new RuntimeException("Non è presente la ragione sociale per l'anagrafe " + a.getDescrizioneRichiedente() + ". Verificare i dati.");
	}
	return a;
    }

    private String getMessaggioErrore(String result, String resultCode, String resultDescription) {

	//	String errore = "La richiesta non è stata elaborata a causa di errori. ";
	String errore = "";
	//	if (StringUtils.defaultString(result).equalsIgnoreCase(RESULT_VALORE_OPERAZIONE_ESEGUITA)) {
	//	    errore = "La richiesta non è stata elaborata a causa di un' anomalia allo sportello INAIL. ";
	//	}
	//	if (StringUtils.isNotBlank(resultCode)) {
	//	    errore += "Codice errore: " + resultCode + ". ";
	//	}
	if (StringUtils.isNotBlank(resultCode)) {
	    //	    errore += "Descrizione errore: " + resultDescription + ". ";
	    errore += "Codice errore: " + resultCode + ". ";
	    errore += resultDescription + ". E' possibile fare una nuova richiesta di invio DURC";
	}
	return errore;
    }

    private Tipidocumento getTipodocumentoDefault() {

	String codiceTipoDoc = StringUtils.defaultString(getValoreParametroWSDURC(WebConstants.VERTICALIZZAZIONE_WSDURC_TIPODOC_ANAGRAFE, false))
		.trim();
	if (StringUtils.isNotBlank(codiceTipoDoc)) {
	    if (Utilities.isInteger(codiceTipoDoc)) {
		Tipidocumento td = tipidocumentoService.findById(new PkId(Integer.parseInt(codiceTipoDoc)));
		return td;
	    }
	}
	return null;
    }

    private String getValoreParametroWSDURC(String nomeparametro, boolean required) {

	String valore = StringUtils.defaultString(verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_WSDURC,
		nomeparametro));
	if (StringUtils.isBlank(valore) && required) {
	    throw new InvalidConfigurationException("Non è stato configurato il valore del parametro di verticalizzazione " + nomeparametro
		    + " del Modulo " + WebConstants.VERTICALIZZAZIONE_WSDURC);
	}
	return valore;
    }

    @Override
    public Anagrafedocumenti verificaEsistenzaDURC(Integer codiceAnagrafe, Integer codiceIstanza, Date dataVerifica)
	    throws FunzioneBusinessRemotaException, InvalidConfigurationException, DurcNonValidoException {

	Anagrafe a = getDatiAnagrafe(codiceAnagrafe);
	InfoDurcWSClient port = new InfoDurcWSClient();
	DURCAuthentication authentication = getAuthentication();
	DURCCheckRequest req = new DURCCheckRequest();
	req.setCompanyFiscalCode(a.getCodicefiscale());
	req.setCompanyName(a.getNominativo());
	// Deve essere generato XMLGregorianCalendar in cui non sia presente il timezone, altrimenti
	// anche se i mette una data antecedente alla validità del DURC il sistema invia
	// la risposta di DURC valido.
	String _data = Utilities.formatDate(dataVerifica, WebConstants.DATE_FORMAT_PATTERN);
	GregorianCalendar c = Utilities.getDate(_data, WebConstants.DATE_FORMAT_PATTERN);
	XMLGregorianCalendar xmldata = Utilities.getXMLGregorianCalendarWithoutTimeZone(c);
	req.setCheckDate(xmldata);
	DURCCheckResult result = port.checkDurcExistence(getUrlWS(), authentication, req);
	if (result != null) {
	    String resultValue = result.getResultData().getResult();
	    Byte _byte = new Byte(result.getResultData().getResultCode());
	    String resultCode = _byte.toString();
	    //String resultCode = result.getResultData().getResultCode();
	    String resultdescription = result.getResultData().getResultDescription();
	    if (!StringUtils.defaultString(resultValue).equalsIgnoreCase(RESULT_VALORE_OPERAZIONE_ESEGUITA)) {
		// errore o anomalia
		String messaggioErrore = getMessaggioErrore(resultValue, resultCode, resultdescription);
		if (StringUtils.defaultString(resultCode).equalsIgnoreCase("80")) {
		    throw new DurcNonValidoException(messaggioErrore);
		} else {
		    throw new FunzioneBusinessRemotaException(messaggioErrore);
		}
	    }
	    Anagrafedocumenti out = new Anagrafedocumenti();
	    out.setAnagrafe(a);
	    if (codiceIstanza != null) {
		Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
		out.setIstanza(istanza);
	    }
	    out.setTipidocumento(getTipodocumentoDefault());
	    out.setRifdocumento("Verifica DURC ");
	    out.setDatainiziovalidita(result.getIssueDate().toGregorianCalendar().getTime());
	    out.setDatafinevalidita(result.getExpirationDate().toGregorianCalendar().getTime());
	    out.setDataregistrazione(Calendar.getInstance().getTime());
	    anagrafedocumentiService.insert(out);
	    String xmlOggetto = Utilities.marshallObject(result);
	    Oggetti o = new Oggetti();
	    o.setOggetto(xmlOggetto.getBytes());
	    o.setNomefile("checkDurc-" + codiceAnagrafe + "-" + Utilities.getToday(true) + ".xml");
	    oggettiService.insert(o);
	    // Conversione XML to PDF (traformazione da xml-->html e da html -->pdf)
	    try {
		InputStream in = null;
		in = this.getClass().getClassLoader().getResource("it/gruppoinit/xslt/durc.xsl").openStream();
		Oggetti oggetti = Utilities.convertXmlToPdf(o, in);
		oggetti.setNomefile("Durc.pdf");
		out.setOggetto(oggetti);
		oggettiService.insert(oggetti);
		anagrafedocumentiService.update(out);
	    } catch (Exception e) {
		log.error("verificaEsistenzaDURC# Sie è verificato un errore durante la conversione in PDF. Errore {}({})", new java.lang.Object[] {
			e.getMessage(), e });
	    }
	    AnagrafedocumentiDurc adurc = new AnagrafedocumentiDurc();
	    adurc.setOggetto(o);
	    adurc.setAnagrafedocumenti(out);
	    adurc.setCip(String.valueOf(result.getCIP()));
	    adurc.setRequestid(result.getRequestID());
	    adurc.setProtocol(String.valueOf(result.getProtocol()));
	    adurc.setResultcode(resultCode);
	    adurc.setResultdescription(resultdescription);
	    if (result.getExpirationDate() != null) {
		adurc.setExpirationdate(result.getExpirationDate().toGregorianCalendar().getTime());
	    }
	    if (result.getIssueDate() != null) {
		adurc.setIssuedate(result.getIssueDate().toGregorianCalendar().getTime());
	    }
	    anagrafedocumentiDurcService.insert(adurc);
	    return out;
	} else {
	    throw new BusinessValidationException("Risposta nulla dal servizio INFODURC");
	}
    }

    @Override
    public Anagrafedocumenti nuovaRichiestaDURC(NuovoDURCHelper helper) throws FunzioneBusinessRemotaException, InvalidConfigurationException {

	Anagrafe a = getDatiAnagrafe(helper.getCodiceAnagrafe());
	InfoDurcWSClient port = new InfoDurcWSClient();
	DURCAuthentication authentication = getAuthentication();
	DURCInsertRequest req = new DURCInsertRequest();
	Company company = helper.getDurc().getCompany();
	req.setCompany(company);
	// Inserisco gli oggetti institute
	req.getInstitute().addAll(helper.getDurc().getInstitute());
	DURCInsertResult result = port.sendDurcRequest(getUrlWS(), authentication, req);
	if (result != null) {
	    String resultValue = result.getResultData().getResult();
	    String resultCode = result.getResultData().getResultCode();
	    String resultdescription = result.getResultData().getResultDescription();
	    if (!StringUtils.defaultString(resultValue).equalsIgnoreCase(RESULT_VALORE_OPERAZIONE_ESEGUITA)) {
		// errore o anomalia
		String messaggioErrore = getMessaggioErrore(resultValue, resultCode, resultdescription);
		if (StringUtils.defaultString(resultCode).equalsIgnoreCase("80")) {
		    throw new DurcNonValidoException(messaggioErrore);
		} else {
		    throw new FunzioneBusinessRemotaException(messaggioErrore);
		}
	    }
	    Anagrafedocumenti out = new Anagrafedocumenti();
	    out.setAnagrafe(a);
	    if (helper.getCodiceIstanza() != null) {
		Istanze istanza = istanzeService.findById(new PkId(helper.getCodiceIstanza()));
		out.setIstanza(istanza);
	    }
	    out.setTipidocumento(getTipodocumentoDefault());
	    out.setRifdocumento("Nuova richiesta DURC ");
	    out.setDataregistrazione(Calendar.getInstance().getTime());
	    anagrafedocumentiService.insert(out);
	    String xmlOggetto = Utilities.marshallObject(result);
	    Oggetti o = new Oggetti();
	    o.setOggetto(xmlOggetto.getBytes());
	    o.setNomefile("checkDurc-" + helper.getCodiceAnagrafe() + "-" + Utilities.getToday(true) + ".xml");
	    oggettiService.insert(o);
	    AnagrafedocumentiDurc adurc = new AnagrafedocumentiDurc();
	    adurc.setOggetto(o);
	    adurc.setAnagrafedocumenti(out);
	    adurc.setCip(result.getCIP());
	    adurc.setRequestid(result.getRequestID());
	    adurc.setProtocol(String.valueOf(result.getProtocol()));
	    adurc.setResultcode(resultCode);
	    adurc.setResultdescription(resultdescription);
	    anagrafedocumentiDurcService.insert(adurc);
	    return out;
	} else {
	    throw new BusinessValidationException("Risposta nulla dal servizio INFODURC");
	}
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	authsCached = new HashMap<String, DURCAuthentication>();
	urlwsCached = new HashMap<String, String>();
    }

    private Map<String, DURCAuthentication> authsCached = new HashMap<String, DURCAuthentication>();
    private Map<String, String> urlwsCached = new HashMap<String, String>();

    private String getAuthKey() {

	return ORMHelper.getIdcomuneAlias() + "-" + ORMHelper.getSoftware();
    }

    private String getUrlWS() {

	String key = getAuthKey();
	String wsUrl = urlwsCached.get(key);
	if (wsUrl == null) {
	    wsUrl = getValoreParametroINFODURC(WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC_URLWS_SERVIZIODURC, true);
	    if (StringUtils.isNotBlank(wsUrl)) {
		urlwsCached.put(key, wsUrl);
	    }
	}
	return wsUrl;
    }

    private DURCAuthentication getAuthentication() {

	String key = getAuthKey();
	DURCAuthentication auth = authsCached.get(key);
	if (auth == null) {
	    auth = new DURCAuthentication();
	    auth.setInstituteCode(getValoreParametroINFODURC(WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC_INSTITUTE_CODE, true));
	    auth.setSoftwareCodeID(getValoreParametroINFODURC(WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC_SOFTWARE_CODE_ID, true));
	    auth.setUserID(getValoreParametroINFODURC(WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC_USERID, true));
	    auth.setUserPassword(getValoreParametroINFODURC(WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC_USERPASSWORD, true));
	    authsCached.put(key, auth);
	}
	return auth;
    }

    private String getValoreParametroINFODURC(String nomeparametro, boolean required) {

	String valore = StringUtils.defaultString(verticalizzazioniService.getVerticalizzazioniparametriValore(
		WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC, nomeparametro));
	if (StringUtils.isBlank(valore) && required) {
	    throw new InvalidConfigurationException("Non è stato configurato il valore del parametro di verticalizzazione " + nomeparametro
		    + " del Modulo " + WebConstants.VERTICALIZZAZIONE_WSDURC_INFODURC);
	}
	return valore;
    }

    @Override
    public NuovoDURCHelper validaNuovaRichiestaDURC(Integer codiceAnagrafe) {

	Anagrafe a = getDatiAnagrafe(codiceAnagrafe);
	NuovoDURCHelper helper = new NuovoDURCHelper();
	// Setto nuovamente codiceAnagrafe e codiceIstanza
	helper.setCodiceAnagrafe(codiceAnagrafe);
	helper.setCodiceIstanza(helper.getCodiceIstanza());
	DURCInsertRequest req = new DURCInsertRequest();
	Company c = new Company();
	c.setCompanyFiscalCode(a.getCodicefiscale());
	//c.setCompanyName(StringUtils.left(a.getDescrizioneRichiedente(), 160));
	c.setCompanyName(StringUtils.left(a.getNominativo(), 160));
	// Spostato all'interno del metodo "gestisciEmail(...)"
	//	if (StringUtils.isNotBlank(a.getEmail())) {
	//	    c.setCompanyEmail(a.getEmail());
	//	}
	//	if (StringUtils.isNotBlank(a.getPec())) {
	//	    c.setCompanyPecAddress(a.getPec());
	//	}
	if (StringUtils.isNotBlank(a.getFax())) {
	    c.setCompanyFax(a.getFax());
	}
	// verifico gli indirizzi
	Map<String, List<String>> errori = gestisciIndirizzi(a, c);
	helper.setMessaggioValidazione("");
	String mesgV = "La validazione delle seguenti proprietà non è andata a buon fine: <ul>";
	if (errori.isEmpty()) {
	    helper.setValidato(true);
	} else {
	    helper.setValidato(false);
	    //String mesgV = "La validazione delle seguenti proprietà non è andata a buon fine: <ul>";
	    mesgV = "La validazione delle seguenti proprietà non è andata a buon fine: <ul>";
	    for (Map.Entry<String, List<String>> el : errori.entrySet()) {
		String key = el.getKey();
		List<String> errs = el.getValue();
		String tipoSede = "Sede operativa";
		if (key.equalsIgnoreCase("LEGAL")) {
		    tipoSede = "Sede legale";
		}
		mesgV += "<li>Sezione " + tipoSede + " presenta i seguenti errori: <ol>";
		for (String err : errs) {
		    mesgV += "<li>" + err + "</li>";
		}
		mesgV += "</ol></li>";
	    }
	    //mesgV += "</ul>";
	    helper.setMessaggioValidazione(mesgV);
	}
	// VERIFICO E POPOLO INDIRIZZI EMAIL (Deve essere presente uno tra email e pec)
	Map<String, List<String>> erroriEmail = gestisciEmail(a, c);
	if (!erroriEmail.isEmpty()) {
	    helper.setValidato(false);
	    mesgV += "<li>Sezione contatti presenta i seguenti errori (E' necessario specificare almeno un indirizzo tra e-mail e PEC): <ol>";
	    List<String> errs = erroriEmail.get("MAIL");
	    for (String errore : errs) {
		mesgV += "<li>" + errore + "</li>";
	    }
	    mesgV += "</ol></li>";
	}
	// VERIFICO ED INSERISCO DATI "istituti previdenziali"
	Map<String, List<String>> erroriInfoIstitutiPrevidenziali = gestisciInfoIstitutiPrevidenziali(a, req);
	if (!erroriInfoIstitutiPrevidenziali.isEmpty()) {
	    helper.setValidato(false);
	    mesgV += "<li>Sezione Info Istituti previdenziali: (E' necessario specificare almeno uno tra ente INAIL o INPS) <ol>";
	    List<String> erroriMatricolaIstitutiPrevidenziali = erroriInfoIstitutiPrevidenziali.get("ERRORI_MATRICOLA");
	    List<String> erroriSediIstitutiPrevidenziali = erroriInfoIstitutiPrevidenziali.get("ERRORI_SEDE");
	    if (erroriMatricolaIstitutiPrevidenziali != null && !erroriMatricolaIstitutiPrevidenziali.isEmpty()) {
		for (String errore : erroriMatricolaIstitutiPrevidenziali) {
		    mesgV += "<li>" + errore + "</li>";
		}
		//		mesgV += "</ol></li>";
	    }
	    if (erroriSediIstitutiPrevidenziali != null && !erroriSediIstitutiPrevidenziali.isEmpty()) {
		for (String errore : erroriSediIstitutiPrevidenziali) {
		    mesgV += "<li>" + errore + "</li>";
		}
		mesgV += "</ol></li>";
	    } else {
		mesgV += "</ol></li>";
	    }
	}
	// setto il messaggio di errore, se presente, nell'oggetto NuovoDURCHelper
	if (!helper.isValidato()) {
	    mesgV += "</ul>";
	    helper.setMessaggioValidazione(mesgV);
	}
	req.setCompany(c);
	// TODO POPOLARE E VALIDARE I DATI INSTITUTE
	// req.getInstitute();
	//	req.getInstitute().add(INAIL);
	//	req.getInstitute().add(INPS);
	//	req.getInstitute().add(CE);
	helper.setDurc(req);
	return helper;
    }

    private Map<String, List<String>> gestisciIndirizzi(Anagrafe a, Company c) {

	Map<String, List<String>> errori = new HashMap<String, List<String>>();
	Address sedeLegale = null;
	Address sedeOperativa = null;
	if (c.getCompanyAddress().isEmpty()) {
	    // LEGALE
	    sedeLegale = new Address();
	    sedeLegale.setType("LEGAL");
	    sedeLegale.setCAP(a.getCap());
	    if (a.getComuneResidenza() != null) {
		sedeLegale.setCity(a.getComuneResidenza().getComune());
		sedeLegale.setCityFiscalID(a.getComuneResidenza().getCf());
		sedeLegale.setCityID(a.getComuneResidenza().getCodiceistat());
		sedeLegale.setProvince(a.getComuneResidenza().getSiglaprovincia());
	    }
	    if (StringUtils.isNotBlank(a.getProvincia())) {
		sedeLegale.setProvince(a.getProvincia());
	    }
	    recuperaIndirizzo(a.getIndirizzo(), sedeLegale);
	    // OPERATIVA
	    sedeOperativa = new Address();
	    sedeOperativa.setType("OPERATIVE");
	    sedeOperativa.setCAP(a.getCapcorrispondenza());
	    if (a.getComunecorrispondenza() != null) {
		sedeOperativa.setCity(a.getComunecorrispondenza().getComune());
		sedeOperativa.setCityFiscalID(a.getComunecorrispondenza().getCf());
		sedeOperativa.setCityID(a.getComunecorrispondenza().getCodiceistat());
		sedeOperativa.setProvince(a.getComunecorrispondenza().getSiglaprovincia());
	    }
	    if (StringUtils.isNotBlank(a.getProvinciacorrispondenza())) {
		sedeOperativa.setProvince(a.getProvinciacorrispondenza());
	    }
	    recuperaIndirizzo(a.getIndirizzocorrispondenza(), sedeOperativa);
	    c.getCompanyAddress().add(sedeLegale);
	    c.getCompanyAddress().add(sedeOperativa);
	} else {
	    // la seconda volta che passo
	    List<Address> ind = c.getCompanyAddress();
	    for (Address add : ind) {
		if (add.getType().equalsIgnoreCase("LEGAL")) {
		    sedeLegale = add;
		} else {
		    sedeOperativa = add;
		}
	    }
	}
	List<String> erroriLegale = validaIndirizzo(sedeLegale);
	if (!erroriLegale.isEmpty()) {
	    errori.put("LEGAL", erroriLegale);
	}
	List<String> erroriOpe = validaIndirizzo(sedeOperativa);
	if (!erroriOpe.isEmpty()) {
	    errori.put("OPERATIVE", erroriOpe);
	}
	return errori;
    }

    private List<String> validaIndirizzo(Address a) {

	List<String> errori = new ArrayList<String>();
	String toponimo = validaStringa(a.getParticle(), 10, true);
	addToErrori("Toponimo", toponimo, errori);
	String indirizzo = validaStringa(a.getDescription(), 50, true);
	addToErrori("Indirizzo", indirizzo, errori);
	String number = validaStringa(a.getNumber(), 10, true);
	addToErrori("Civico", number, errori);
	String cap = validaStringa(a.getCAP(), 5, true);
	addToErrori("Cap", cap, errori);
	String istat = validaStringa(a.getCityID(), 6, true);
	addToErrori("Codice istat", istat, errori);
	String cf = validaStringa(a.getCityFiscalID(), 4, true);
	addToErrori("Codice Berfiore", cf, errori);
	String comune = validaStringa(a.getCity(), 40, true);
	addToErrori("Comune", comune, errori);
	String provincia = validaStringa(a.getProvince(), 2, true);
	addToErrori("Provincia", provincia, errori);
	return errori;
    }

    private void addToErrori(String prop, String errore, List<String> errori) {

	if (StringUtils.isNotBlank(errore)) {
	    errori.add("Il campo " + prop + " non è valido per i seguenti motivi: " + errore);
	}
    }

    private String validaStringa(String val, int maxlength, boolean required) {

	List<String> result = new ArrayList<String>();
	if (StringUtils.isBlank(val)) {
	    if (required) {
		result.add("obbligatorio");
	    }
	} else {
	    if (val.length() > maxlength) {
		result.add("ammessi solamente " + maxlength + " caratteri");
	    }
	}
	if (result.isEmpty()) {
	    return "";
	} else {
	    String errori = "";
	    for (String e : result) {
		errori += e + ", ";
	    }
	    if (errori.length() > 1) {
		errori = errori.substring(0, (errori.length() - 2));
	    }
	    return errori;
	}
    }

    private void recuperaIndirizzo(String indirizzo, Address address) {

	if (StringUtils.isNotBlank(indirizzo)) {
	    String[] tokens = indirizzo.split(" ");
	    if (tokens.length == 3) {
		address.setParticle(tokens[0]);
		address.setDescription(tokens[1]);
		address.setNumber(tokens[2]);
	    } else if (tokens.length == 2) {
		address.setParticle(tokens[0]);
		address.setDescription(tokens[1]);
	    } else if (tokens.length == 1) {
		address.setDescription(tokens[0]);
	    } else if (tokens.length > 3) {
		address.setParticle(tokens[0]);
		String desc = "";
		for (int i = 1; i < (tokens.length - 1); i++) {
		    desc += tokens[i] + " ";
		}
		address.setDescription(desc);
		address.setNumber(tokens[(tokens.length - 1)]);
	    }
	}
    }

    @Override
    public NuovoDURCHelper validaDURCHelper(NuovoDURCHelper nuovoDURCHelper) {

	Anagrafe a = getDatiAnagrafe(nuovoDURCHelper.getCodiceAnagrafe());
	NuovoDURCHelper helper = new NuovoDURCHelper();
	// Setto  nuovamente codiceAnagrafe e codiceIstanza al nuovo oggetto
	helper.setCodiceAnagrafe(nuovoDURCHelper.getCodiceAnagrafe());
	helper.setCodiceIstanza(nuovoDURCHelper.getCodiceIstanza());
	DURCInsertRequest req = new DURCInsertRequest();
	Company c = nuovoDURCHelper.getDurc().getCompany();
	c.setCompanyFiscalCode(a.getCodicefiscale());
	//c.setCompanyName(StringUtils.left(a.getDescrizioneRichiedente(), 160));
	c.setCompanyName(StringUtils.left(a.getNominativo(), 160));
	//	if (StringUtils.isNotBlank(a.getEmail())) {
	//	    c.setCompanyEmail(a.getEmail());
	//	}
	//	if (StringUtils.isNotBlank(a.getPec())) {
	//	    c.setCompanyPecAddress(a.getPec());
	//	}
	if (StringUtils.isNotBlank(a.getFax())) {
	    c.setCompanyFax(a.getFax());
	}
	// VERIFICO INDIRIZZI
	Map<String, List<String>> errori = gestisciIndirizzi(a, c);
	helper.setMessaggioValidazione("");
	String mesgV = "La validazione delle seguenti proprietà non è andata a buon fine: <ul>";
	if (errori.isEmpty()) {
	    helper.setValidato(true);
	} else {
	    helper.setValidato(false);
	    //	    mesgV = "La validazione delle seguenti proprietà non è andata a buon fine: <ul>";
	    for (Map.Entry<String, List<String>> el : errori.entrySet()) {
		String key = el.getKey();
		List<String> errs = el.getValue();
		String tipoSede = "Sede operativa";
		if (key.equalsIgnoreCase("LEGAL")) {
		    tipoSede = "Sede legale";
		}
		mesgV += "<li>Sezione " + tipoSede + " presenta i seguenti errori: <ol>";
		for (String err : errs) {
		    mesgV += "<li>" + err + "</li>";
		}
		mesgV += "</ol></li>";
	    }
	}
	// VERIFICO INDIRIZZI EMAIL (Deve essere presente uno tra email e pec)
	Map<String, List<String>> erroriEmail = gestisciEmail(a, c);
	if (!erroriEmail.isEmpty()) {
	    helper.setValidato(false);
	    mesgV += "<li>Sezione contatti presenta i seguenti errori (E' necessario specificare almeno un indirizzo tra e-mail e PEC): <ol>";
	    List<String> errs = erroriEmail.get("MAIL");
	    for (String errore : errs) {
		mesgV += "<li>" + errore + "</li>";
	    }
	    mesgV += "</ol></li>";
	}
	// VERIFICO ED INSERISCO DATI "istituti previdenziali"
	Map<String, List<String>> erroriInfoIstitutiPrevidenziali = gestisciInfoIstitutiPrevidenziali(a, req);
	if (erroriInfoIstitutiPrevidenziali != null && !erroriInfoIstitutiPrevidenziali.isEmpty()) {
	    helper.setValidato(false);
	    mesgV += "<li>Sezione Info Istituti previdenziali: (E' necessario specificare almeno uno tra ente INAIL o INPS) <ol>";
	    List<String> erroriMatricolaIstitutiPrevidenziali = erroriInfoIstitutiPrevidenziali.get("ERRORI_MATRICOLA");
	    List<String> erroriSediIstitutiPrevidenziali = erroriInfoIstitutiPrevidenziali.get("ERRORI_SEDE");
	    if (erroriMatricolaIstitutiPrevidenziali != null && !erroriMatricolaIstitutiPrevidenziali.isEmpty()) {
		for (String errore : erroriMatricolaIstitutiPrevidenziali) {
		    mesgV += "<li>" + errore + "</li>";
		}
		mesgV += "</ol></li>";
	    }
	    if (erroriSediIstitutiPrevidenziali != null && !erroriSediIstitutiPrevidenziali.isEmpty()) {
		mesgV += "</ol><li>";
		for (String errore : erroriSediIstitutiPrevidenziali) {
		    mesgV += "<li>" + errore + "</li>";
		}
		mesgV += "</ol></li>";
	    }
	}
	// setto il messaggio di errore, se presente, nell'oggetto NuovoDURCHelper
	if (!helper.isValidato()) {
	    mesgV += "</ul>";
	    helper.setMessaggioValidazione(mesgV);
	}
	req.setCompany(c);
	helper.setDurc(req);
	return helper;
    }

    private Map<String, List<String>> gestisciEmail(Anagrafe a, Company c) {

	Map<String, List<String>> erroriEmail = new HashMap<String, List<String>>();
	List<String> listErrori = new ArrayList<String>();
	boolean isPecPresente = true;
	boolean isEmailPresente = true;
	if (StringUtils.isBlank(c.getCompanyEmail())) {
	    if (StringUtils.isBlank(a.getEmail())) {
		String email = "Indirizzo E-mail non presente";
		listErrori.add(email);
		isEmailPresente = false;
	    } else {
		c.setCompanyEmail(a.getEmail());
	    }
	}
	if (StringUtils.isBlank(c.getCompanyPecAddress())) {
	    if (StringUtils.isBlank(a.getPec())) {
		String pec = "Indirizzo Pec non presente";
		listErrori.add(pec);
		isPecPresente = false;
	    } else {
		c.setCompanyPecAddress(a.getPec());
	    }
	}
	if (!isEmailPresente && !isPecPresente) {
	    erroriEmail.put("MAIL", listErrori);
	}
	return erroriEmail;
    }

    private Map<String, List<String>> gestisciInfoIstitutiPrevidenziali(Anagrafe a, DURCInsertRequest req) {

	Map<String, List<String>> erroriInfo = new HashMap<String, List<String>>();
	List<String> listErroriMAtricola = new ArrayList<String>();
	List<String> listErroriSede = new ArrayList<String>();
	// Definisco i tre istituti
	Institute INAIL = new Institute();
	Institute INPS = new Institute();
	Institute CE = new Institute();
	//TODO: per ora non viene gestito
	//Institute CE = new Institute();
	boolean isINAILMatricolaPresente = true;
	boolean isINPSMatricolaPresente = true;
	boolean isCassaEdileMatricolaPresente = true;
	boolean isINAILSedePresente = true;
	boolean isINPSSedePresente = true;
	boolean isCassaEdileSedePresente = true;
	//boolean isCEPresente = true;
	if (req.getInstitute().isEmpty()) {
	    // Provo a popolarli
	    //INAIL
	    INAIL.setInstituteName("INAIL");
	    if (StringUtils.isBlank(a.getInailMatricola())) {
		String inailMatricolaError = "Matricola INAIL non presente";
		listErroriMAtricola.add(inailMatricolaError);
		isINAILMatricolaPresente = false;
	    } else {
		INAIL.setCompanyCode(a.getInailMatricola());
	    }
	    if (a.getSedeInail() != null) {
		if (StringUtils.isBlank(a.getSedeInail().getCodice())) {
		    String inailSedeError = "Sede INAIL non presente";
		    listErroriSede.add(inailSedeError);
		    isINAILSedePresente = false;
		} else {
		    Office officeINAIL = new Office();
		    officeINAIL.setOfficeCode(a.getSedeInail().getCodice());
		    officeINAIL.setOfficeName(a.getSedeInail().getDescrizione());
		    INAIL.setInstituteOffice(officeINAIL);
		}
	    } else {
		String inailSedeError = "Sede INAIL non presente";
		listErroriSede.add(inailSedeError);
		isINAILSedePresente = false;
	    }
	    if (isINAILSedePresente && isINAILMatricolaPresente) {
		req.getInstitute().add(INAIL);
	    }
	    //
	    //INPS
	    INPS.setInstituteName("INPS");
	    if (StringUtils.isBlank(a.getInpsMatricola())) {
		String inpsMatricolaError = "Matricola INPS non presente";
		listErroriMAtricola.add(inpsMatricolaError);
		isINPSMatricolaPresente = false;
	    } else {
		INPS.setCompanyCode(a.getInpsMatricola());
	    }
	    if (a.getSedeInps() != null) {
		if (StringUtils.isBlank(a.getSedeInps().getCodice())) {
		    String inpsSedeError = "Sede INPS non presente";
		    listErroriSede.add(inpsSedeError);
		    isINPSSedePresente = false;
		} else {
		    Office officeINPS = new Office();
		    officeINPS.setOfficeCode(a.getSedeInps().getCodice());
		    officeINPS.setOfficeName(a.getSedeInps().getDescrizione());
		    INPS.setInstituteOffice(officeINPS);
		}
	    } else {
		String inpsSedeError = "Sede INPS non presente";
		listErroriSede.add(inpsSedeError);
		isINPSSedePresente = false;
	    }
	    if (isINPSMatricolaPresente && isINPSSedePresente) {
		req.getInstitute().add(INPS);
	    }
	}
	CE.setInstituteName("CE");
	if (StringUtils.isBlank(a.getCassaedileMatricola())) {
	    String cassaEdileMatricolaError = "Matricola Cassa Edile non presente";
	    listErroriMAtricola.add(cassaEdileMatricolaError);
	    isCassaEdileMatricolaPresente = false;
	} else {
	    CE.setCompanyCode(a.getCassaedileMatricola());
	}
	if (a.getSedeCassaedile() != null) {
	    if (StringUtils.isBlank(a.getSedeCassaedile().getCodice())) {
		String cassaedileSedeError = "Sede Cassa Edile non presente";
		listErroriSede.add(cassaedileSedeError);
		isCassaEdileSedePresente = false;
	    } else {
		Office officeCE = new Office();
		officeCE.setOfficeName(a.getSedeCassaedile().getDescrizione());
		officeCE.setOfficeCode(a.getSedeCassaedile().getCodice());
		CE.setInstituteOffice(officeCE);
	    }
	} else {
	    String cassaedileSedeError = "Sede CASSA EDILE non presente";
	    listErroriSede.add(cassaedileSedeError);
	    isCassaEdileSedePresente = false;
	}
	if (isCassaEdileMatricolaPresente /*&& isCassaEdileSedePresente*/) {
	    req.getInstitute().add(CE);
	}
	if (!isINAILMatricolaPresente && !isINPSMatricolaPresente && !isCassaEdileMatricolaPresente) {
	    erroriInfo.put("ERRORI_MATRICOLA", listErroriMAtricola);
	}
	if (!isINAILSedePresente && !isINPSSedePresente && !isCassaEdileSedePresente) {
	    erroriInfo.put("ERRORI_SEDE", listErroriSede);
	}
	return erroriInfo;
    }
}
