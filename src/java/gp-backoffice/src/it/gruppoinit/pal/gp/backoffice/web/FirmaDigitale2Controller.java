package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.InputStreamDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.firma.ws.Firma;
import it.gruppoinit.pal.firma.ws.schema.GetSignedFileRequest;
import it.gruppoinit.pal.firma.ws.schema.GetSignedFileResponse;
import it.gruppoinit.pal.firma.ws.schema.SetFileToSignRequest;
import it.gruppoinit.pal.firma.ws.schema.SetFileToSignResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.FirmeRemote;
import it.gruppoinit.pal.gp.core.domain.FirmeRemoteParametri;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceFileIdDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.FirmaremotaOTPResultHelper;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.FirmaRemotaClientService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione.FirmeRemoteService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.AggiungiDocumentiRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.AvviaProcessoRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.AvviaProcessoResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.ConfigurazioneParametro;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.FirmaDocumentiRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.RecuperaFileFirmatoRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.RecuperaFileFirmatoResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.RecuperaParametriByIdRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.RecuperaParametriByIdResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.SbloccaDocumentiRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.VerificaStatoRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.VerificaStatoResponse;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FirmaWSClient;

@Controller
@SessionAttributes(value = { "firmaremotaOTPHelper" })
public class FirmaDigitale2Controller extends BaseJsonController {

    private static final Logger log = LoggerFactory.getLogger(FirmaDigitale2Controller.class);
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private DocumentiDaFirmareService documentiDaFirmareService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private FirmeRemoteService firmeRemoteService;
    @Autowired
    private FirmaRemotaClientService firmaRemotaClientService;
    @Autowired
    protected ApplicationContext context;
    private final String errorAttribute = "error";
    private final String contentTypeJson = "application/json";
    private final String charsetUtf8 = "utf-8";

    @RequestMapping(method = RequestMethod.GET)
    public String start(Model model, @RequestParam("codiceoggetto") Integer[] codiceoggettoArray,
	    @RequestParam(value = "mettiAllaFirma", required = false) String mettiAllaFirma,
	    @RequestParam(value = "errore", required = false) String errore, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	log.debug("start codiceoggetto={}, mettiAllaFirma={}", codiceoggettoArray, mettiAllaFirma);
	try {
	    List<CodiceFileIdDescrizioneBean> listaFiles = new ArrayList<CodiceFileIdDescrizioneBean>();
	    String wsHostUrlFirma = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_FIRMA);
	    FirmaWSClient firmaWSClient = new FirmaWSClient();
	    Firma firma = firmaWSClient.getFirmaWSPort(wsHostUrlFirma);
	    String firmaSessionId = null;
	    String[] firmaFileIdArray = new String[codiceoggettoArray.length];
	    Integer[] realCodiceoggettoArray = new Integer[codiceoggettoArray.length];
	    for (int i = 0; i < codiceoggettoArray.length; i++) {
		SetFileToSignRequest setFileToSignRequest = new SetFileToSignRequest();
		String mimeType = null;
		String codiceoggetto = null;
		if (StringUtils.isNotBlank(mettiAllaFirma)) {
		    //l'array di codiceoggetto rappresenta una lista di id di entity mettiallafirma da cui ricavo il codiceoggetto della entity oggetti
		    DocumentiDaFirmare documentiDaFirmare = documentiDaFirmareService.findById(new PkId(codiceoggettoArray[i]));
		    setFileToSignRequest.setClientFileId(documentiDaFirmare.getId().getCodice().toString());
		    codiceoggetto = documentiDaFirmare.getOggetti().getId().getCodice().toString();
		    model.addAttribute("mettiAllaFirma", mettiAllaFirma);
		} else {
		    codiceoggetto = codiceoggettoArray[i].toString();
		    setFileToSignRequest.setClientFileId(codiceoggetto);
		}
		realCodiceoggettoArray[i] = Integer.valueOf(codiceoggetto);
		Oggetti ogg = oggettiService.verificaConvertiPdf(oggettiService.findById(new PkId(Integer.valueOf(codiceoggetto))));
		mimeType = contenttypesService.findMimeTypeByFileName(ogg.getNomefile());
		InputStream is = oggettiService.getOggettoAsInputStream(Integer.valueOf(codiceoggetto));
		if (firmaSessionId != null) {
		    setFileToSignRequest.setSessionId(firmaSessionId);
		}
		setFileToSignRequest.setFileName(ogg.getNomefile());
		setFileToSignRequest.setBinaryData(new DataHandler(new InputStreamDataSource(is, mimeType)));
		SetFileToSignResponse setFileToSignResponse = firma.setFileToSign(setFileToSignRequest);
		CodiceFileIdDescrizioneBean cdb = new CodiceFileIdDescrizioneBean();
		cdb.setCodice(codiceoggetto);
		cdb.setFileId(setFileToSignResponse.getFileId());
		cdb.setDescrizione(Utilities.eliminaCaratteriNonAscii(ogg.getNomefile()));
		cdb.setMimeType(mimeType);
		listaFiles.add(cdb);
		firmaSessionId = setFileToSignResponse.getSessionId();
		firmaFileIdArray[i] = setFileToSignResponse.getFileId();
		IOUtils.closeQuietly(is);
	    }
	    String firmaFileId = StringUtils.join(firmaFileIdArray, ",");
	    model.addAttribute("firmaSessionId", firmaSessionId);
	    model.addAttribute("firmaFileId", firmaFileId);
	    model.addAttribute("listaFilesDaFirmare", listaFiles);
	    model.addAttribute("codiceoggettoArray", codiceoggettoArray);
	    Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	    oggettiService.updateFilesBloccaModifica(realCodiceoggettoArray, codiceResponsabile);
	} catch (Exception e) {
	    log.error("start codiceoggetto={}, mettiAllaFirma={}\n{}", new Object[] { codiceoggettoArray, mettiAllaFirma, e });
	    model.addAttribute(errorAttribute, e.getMessage());
	}
	// Gestisce la visualizzazione dell'errore nel caso si presenti in fase di confersione di un file in PDF
	// (Può succedere quanto il metoto start.htm viene richiamato dainsertTrasformaInPdf.htm )
	if (StringUtils.isNotBlank(errore)) {
	    model.addAttribute(errorAttribute, errore);
	}
	boolean isMettiAllafirma = StringUtils.isNotBlank(mettiAllaFirma) && "true".equals(mettiAllaFirma) ? true : false;
	model.addAttribute("isMettiAllafirma", isMettiAllafirma);
	model.addAttribute("isSegnaComeFirmatoAttivo", true);
	return "firmadigitale2/form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public void ajaxGetSignedFile(@RequestParam("firmaSessionId") String firmaSessionId, @RequestParam("firmaFileId") String firmaFileId,
	    @RequestParam(value = "mettiAllaFirma", required = false) String mettiAllaFirma, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	log.debug("ajaxGetSignedFile firmaSessionId={}, firmaFileId={}, mettiAllaFirma={}",
		new Object[] { firmaSessionId, firmaFileId, mettiAllaFirma });
	try {
	    String wsHostUrlFirma = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_FIRMA);
	    FirmaWSClient firmaWSClient = new FirmaWSClient();
	    Firma firma = firmaWSClient.getFirmaWSPort(wsHostUrlFirma);
	    GetSignedFileRequest getSignedFileRequest = new GetSignedFileRequest();
	    getSignedFileRequest.setSessionId(firmaSessionId);
	    getSignedFileRequest.setFileId(firmaFileId);
	    GetSignedFileResponse getSignedFileResponse = firma.getSignedFile(getSignedFileRequest);
	    if (BooleanUtils.isTrue(getSignedFileResponse.isIsSigned())) {
		//save signed file
		DataHandler dh = getSignedFileResponse.getBinaryData();
		String fileName = getSignedFileResponse.getFileName();
		if (StringUtils.contains(fileName, ".p7m")) {
		    log.debug("ajaxGetSignedFile# file {} contiene più estensioni .p7m concatenate, bonifico il file lasciandone una... ", fileName);
		    String vp = verticalizzazioniService.getVerticalizzazioniparametriValore(
			    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA,
			    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA_FIRMA_CADES_NO_EXT_MULTI_P7M);
		    if ("1".equals(vp)) {
			log.debug("ajaxGetSignedFile# Comportamento {}.{}:{} ",
				new Object[] { WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA,
					WebConstants.VERTICALIZZAZIONE_COMPORTAMENTO_COMPONENTE_FIRMA_FIRMA_CADES_NO_EXT_MULTI_P7M, vp });
			fileName = StringUtils.substring(fileName, 0, StringUtils.indexOf(fileName, ".p7m") + 4);
			log.debug("ajaxGetSignedFile# file bonificato: {}", fileName);
		    }
		}
		String codiceoggetto = null;
		DocumentiDaFirmare documentiDaFirmare = null;
		if (StringUtils.isNotBlank(mettiAllaFirma)) {
		    String mettiAllaFirmaId = getSignedFileResponse.getClientFileId();
		    documentiDaFirmare = documentiDaFirmareService.findById(new PkId(Integer.valueOf(mettiAllaFirmaId)));
		    codiceoggetto = documentiDaFirmare.getOggetti().getId().getCodice().toString();
		} else {
		    codiceoggetto = getSignedFileResponse.getClientFileId();
		}
		Oggetti ogg = oggettiService.findById(new PkId(Integer.valueOf(codiceoggetto)));
		ogg.setNomefile(fileName);
		ogg.setOggetto(Utilities.dataHandlerToBytes(dh));
		oggettiService.update(ogg);
		try {
		    OggettiMetadatiId id = new OggettiMetadatiId();
		    id.setChiave(WebConstants.METADATO_CONSERVAZIONE_DOC_SOSPESA);
		    id.setCodiceoggetto(ogg.getId().getCodice());
		    id.setIdcomune(ORMHelper.getIdcomune());
		    OggettiMetadati om = oggettiMetadatiService.findById(id);
		    if (om != null) {
			om.setValore(WebConstants.NO);
			oggettiMetadatiService.update(om);
		    }
		    //aggiorna documentidafirmare se presente
		    if (documentiDaFirmare != null) {
			documentiDaFirmareService.updateSingolo(documentiDaFirmare, StatiDocumentiDaFirmare.FIRMA_COMPLETA.name(), "");
		    }
		} catch (Exception e1) {
		    log.error("ajaxGetSignedFile# Errore durante l'eliminazione del metadato chiave = {} per l'oggetto codice = {} ", WebConstants.NO,
			    ogg.getId().getCodice());
		}
		response.getWriter().write("TRUE");
	    } else {
		response.getWriter().write("FALSE");
	    }
	} catch (Exception e) {
	    log.error("ajaxGetSignedFile firmaSessionId={}, firmaFileId={}, mettiAllaFirma={}\n{}",
		    new Object[] { firmaSessionId, firmaFileId, mettiAllaFirma, e });
	    throw new IOException(e.getMessage());
	}
    }

    @RequestMapping(method = RequestMethod.POST)
    public String insertTrasformaInPdf(@RequestParam("codiceOggetto") Integer codiceOggetto,
	    @RequestParam("codiciDocDaFirmareArray") Integer[] codiciDocDaFirmareArray,
	    @RequestParam(value = "mettiAllaFirma", required = false) Boolean isMettiAllaFirma,
	    @RequestParam(value = "isFirmaRemota") boolean isFirmaRemota, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	StringBuffer errore = new StringBuffer();
	String mettiAllaFirma = null;
	try {
	    if (isMettiAllaFirma != null && isMettiAllaFirma) {
		documentiDaFirmareService.insertTrasformaInPdfDocumentiMessiAllaFirma(codiceOggetto);
		mettiAllaFirma = "true";
	    } else {
		oggettiService.convertFileInPdfAndSostituisci(codiceOggetto);
	    }
	} catch (Exception e) {
	    errore.append("Errore nella creazione del file PDF:").append(e.getMessage());
	}
	if (!isFirmaRemota) {
	    return start(model, codiciDocDaFirmareArray, mettiAllaFirma, errore.toString(), request, response);
	} else {
	    return startFirmaRemota(model, codiciDocDaFirmareArray, mettiAllaFirma, errore.toString(), request, response);
	}
    }

    @RequestMapping(method = RequestMethod.POST)
    public String startFirmaRemota(Model model, @RequestParam("codiceoggetto") Integer[] codiceoggettoArray,
	    @RequestParam(value = "mettiAllaFirma", required = false) String mettiAllaFirma,
	    @RequestParam(value = "errore", required = false) String errore, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	log.debug("start codiceoggetto={}, startFirmaRemota={}", codiceoggettoArray, mettiAllaFirma);
	//legge l'impostazione e la mette nel helper set profilo
	try {
	    setModel(model, codiceoggettoArray, mettiAllaFirma, false, false, null);
	} catch (Exception e) {
	    log.error("start codiceoggetto={}, mettiAllaFirma={}\n{}", new Object[] { codiceoggettoArray, mettiAllaFirma, e });
	    setModel(model, codiceoggettoArray, mettiAllaFirma, false, false, null);
	    model.addAttribute(errorAttribute, e.getMessage());
	}
	// Gestisce la visualizzazione dell'errore nel caso si presenti in fase di confersione di un file in PDF
	// (Può succedere quanto il metoto start.htm viene richiamato da insertTrasformaInPdf.htm )
	if (StringUtils.isNotBlank(errore)) {
	    model.addAttribute(errorAttribute, errore);
	}
	return "firmadigitale2/formRemota";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String startFirmaRemota(Model model, @RequestParam("codiceoggetto") Integer codiceoggetto,
	    @RequestParam(value = "errore", required = false) String errore, HttpServletRequest request, HttpServletResponse response) {

	log.debug("startFirmaRemota codiceoggetto={}", codiceoggetto);
	//legge l'impostazione e la mette nel helper set profilo
	Integer[] codiciOggetto = new Integer[1];
	codiciOggetto[0] = codiceoggetto;
	try {
	    setModel(model, codiciOggetto, null, false, false, null);
	} catch (Exception e) {
	    log.error("startFirmaRemota codiceoggetto={} \n{}", new Object[] { codiciOggetto, e });
	    setModel(model, codiciOggetto, null, false, false, null);
	    model.addAttribute(errorAttribute, e.getMessage());
	}
	// Gestisce la visualizzazione dell'errore nel caso si presenti in fase di confersione di un file in PDF
	// (Può succedere quanto il metoto start.htm viene richiamato da insertTrasformaInPdf.htm )
	if (StringUtils.isNotBlank(errore)) {
	    model.addAttribute(errorAttribute, errore);
	}
	return "firmadigitale2/formRemota";
    }

    @SuppressWarnings("unchecked")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonRecuperaParametri(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    RecuperaParametriByIdRequest parametriRequest = (RecuperaParametriByIdRequest) fromJson(request.getInputStream(),
		    RecuperaParametriByIdRequest.class);
	    FirmeRemote firma = this.firmeRemoteService.findById(new PkId(parametriRequest.getIdComponente()));
	    List<FirmeRemoteParametri> parametri = this.firmeRemoteService.findParamertiByIdTestata(parametriRequest.getIdComponente());
	    RecuperaParametriByIdResponse parametriResponse = new RecuperaParametriByIdResponse();
	    for (FirmeRemoteParametri parametro : parametri) {
		String messagesProperty = "firmaremota." + firma.getProvider() + "." + parametro.getId().getChiave();
		ConfigurazioneParametro cfgPar = new ConfigurazioneParametro();
		cfgPar.setChiave(parametro.getId().getChiave());
		cfgPar.setDescrizione(parametro.getDescrizione());
		cfgPar.setEtichetta(Utilities.getMessageFromBundle(context, messagesProperty.toLowerCase()));
		cfgPar.setObbligatorio(parametro.getObbligatorio());
		cfgPar.setValore(parametro.getValoreDefault());
		cfgPar.setVisibile(parametro.getVisibile());
		cfgPar.setReadOnly(parametro.getReadonly());
		cfgPar.setTipoCampo(parametro.getTipoCampo());
		cfgPar.setOrdine(parametro.getOrdine());
		parametriResponse.getParametri().add(cfgPar);
	    }
	    response.setContentType(contentTypeJson);
	    String jsonParametriResponse = toJson(parametriResponse, true);
	    response.getOutputStream().write(jsonParametriResponse.getBytes(charsetUtf8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @SuppressWarnings("unchecked")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonAvviaProcesso(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    AvviaProcessoRequest avviaProcessoRequest = (AvviaProcessoRequest) fromJson(request.getInputStream(), AvviaProcessoRequest.class);
	    FirmeRemote firmaIntegrata = this.firmeRemoteService.findById(new PkId(avviaProcessoRequest.getIdConfigurazione()));
	    AvviaProcessoResponse avviaProcessoResponse = this.firmaRemotaClientService.avviaProcesso(firmaIntegrata.getEndpoint(),
		    avviaProcessoRequest.getParametri());
	    response.setContentType(contentTypeJson);
	    String jsonProcessoResponse = toJson(avviaProcessoResponse, true);
	    response.getOutputStream().write(jsonProcessoResponse.getBytes(charsetUtf8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @SuppressWarnings("unchecked")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonAggiungiDocumenti(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    AggiungiDocumentiRequest aggiungiDocumentiRequest = (AggiungiDocumentiRequest) fromJson(request.getInputStream(),
		    AggiungiDocumentiRequest.class);
	    FirmeRemote firmaIntegrata = this.firmeRemoteService.findById(new PkId(aggiungiDocumentiRequest.getIdConfigurazione()));
	    this.firmaRemotaClientService.aggiungiDocumenti(firmaIntegrata.getEndpoint(), aggiungiDocumentiRequest.getSessionId(),
		    aggiungiDocumentiRequest.getIdOggetti());
	    response.setContentType(contentTypeJson);
	    response.getOutputStream().write("OK".getBytes(charsetUtf8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @SuppressWarnings("unchecked")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonFirmaDocumenti(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    FirmaDocumentiRequest firmaDocumentiRequest = (FirmaDocumentiRequest) fromJson(request.getInputStream(), FirmaDocumentiRequest.class);
	    FirmeRemote firmaIntegrata = this.firmeRemoteService.findById(new PkId(firmaDocumentiRequest.getIdConfigurazione()));
	    this.firmaRemotaClientService.firmaDocumenti(firmaIntegrata.getEndpoint(), firmaDocumentiRequest.getSessionId(),
		    firmaDocumentiRequest.getParametri(), firmaDocumentiRequest.getIdOggetti());
	    response.setContentType(contentTypeJson);
	    response.getOutputStream().write("OK".getBytes(charsetUtf8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @SuppressWarnings("unchecked")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonStatoAvanzamento(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    VerificaStatoRequest verificaStatoRequest = (VerificaStatoRequest) fromJson(request.getInputStream(), VerificaStatoRequest.class);
	    FirmeRemote firmaIntegrata = this.firmeRemoteService.findById(new PkId(verificaStatoRequest.getIdConfigurazione()));
	    VerificaStatoResponse verificaStatoResponse = this.firmaRemotaClientService.verificaStato(firmaIntegrata.getEndpoint(),
		    verificaStatoRequest.getSessionId());
	    response.setContentType(contentTypeJson);
	    String jsonProcessoResponse = toJson(verificaStatoResponse, true);
	    response.getOutputStream().write(jsonProcessoResponse.getBytes(charsetUtf8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @SuppressWarnings("unchecked")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonRecuperaFileFirmato(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    RecuperaFileFirmatoRequest recuperaFileFirmatoRequest = (RecuperaFileFirmatoRequest) fromJson(request.getInputStream(),
		    RecuperaFileFirmatoRequest.class);
	    FirmeRemote firmaIntegrata = this.firmeRemoteService.findById(new PkId(recuperaFileFirmatoRequest.getIdConfigurazione()));
	    RecuperaFileFirmatoResponse recuperaFileFirmatoResponse = this.firmaRemotaClientService.recuperaFileFirmato(firmaIntegrata.getEndpoint(),
		    recuperaFileFirmatoRequest.getSessionId(), recuperaFileFirmatoRequest.getCodiceOggetto());
	    response.setContentType(contentTypeJson);
	    String jsonProcessoResponse = toJson(recuperaFileFirmatoResponse, true);
	    response.getOutputStream().write(jsonProcessoResponse.getBytes(charsetUtf8));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @SuppressWarnings("unchecked")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonSbloccaDocumenti(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    SbloccaDocumentiRequest sbloccaDocumentiRequest = (SbloccaDocumentiRequest) fromJson(request.getInputStream(),
		    SbloccaDocumentiRequest.class);
	    Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	    this.oggettiService.updateFilesRimuoviBloccoModifica(sbloccaDocumentiRequest.getIdOggetti(), codiceResponsabile);
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    private void setModel(Model model, Integer[] arrayCodiceOggettoOrDocumentiDaFirmare, String mettiAllaFirma, boolean isFirmato, Boolean bloccaFile,
	    List<FirmaremotaOTPResultHelper> firmaremotaOTPResultHelpers) {

	// Definizione variabili 
	boolean isPadesPossibile = true;
	Integer[] codiceoggettoArray = arrayCodiceOggettoOrDocumentiDaFirmare;
	Integer[] codiciOggettiReali = new Integer[codiceoggettoArray.length];
	// Lista utilizzato sul form per mostrare le informazioni dei file che andrò a firmare (nome,codice,etc..)
	List<CodiceFileIdDescrizioneBean> listaFiles = new ArrayList<CodiceFileIdDescrizioneBean>();
	// trasformo la stringa "mettiAllaFirma" in booleano
	boolean isMettiAllafirma = StringUtils.isNotBlank(mettiAllaFirma) && "true".equals(mettiAllaFirma);
	log.debug("setModel# Funzionalità metti alla firma: {}", isMettiAllafirma);
	// Ciclo i codice oggetto per popolare la lista List<CodiceFileIdDescrizioneBean> listaFiles
	for (int i = 0; i < codiceoggettoArray.length; i++) {
	    String mimeType = null;
	    Integer codiceoggetto = null;
	    if (StringUtils.isNotBlank(mettiAllaFirma) && isMettiAllafirma) {
		//l'array di codiceoggetto rappresenta una lista di id di entity mettiallafirma da cui ricavo il codiceoggetto della entity oggetti
		DocumentiDaFirmare documentiDaFirmare = documentiDaFirmareService.findById(new PkId(codiceoggettoArray[i]));
		codiceoggetto = documentiDaFirmare.getOggetti().getId().getCodice();
		codiciOggettiReali[i] = codiceoggetto;
		model.addAttribute("mettiAllaFirma", mettiAllaFirma);
	    } else {
		codiciOggettiReali[i] = codiceoggettoArray[i];
		codiceoggetto = codiceoggettoArray[i];
	    }
	    // Recupero l'oggetto
	    Oggetti ogg = oggettiService.findByIdLazy(new PkId(codiciOggettiReali[i]));
	    //verifico se possibile la firma pades (solo file pdf)
	    log.debug("setModel# Controllo se il file {} può essere firmato in modalità PAdES (solo per file pdf)", ogg.getNomefile());
	    if (isPadesPossibile) {
		isPadesPossibile = checkPossibilitaFirmaPades(ogg);
	    }
	    mimeType = contenttypesService.findMimeTypeByFileName(ogg.getNomefile());
	    CodiceFileIdDescrizioneBean cdb = new CodiceFileIdDescrizioneBean();
	    cdb.setCodice(codiceoggetto.toString());
	    cdb.setDescrizione(Utilities.eliminaCaratteriNonAscii(ogg.getNomefile()));
	    cdb.setMimeType(mimeType);
	    listaFiles.add(cdb);
	}
	// Blocco i file che sto per firmare in modo che nessun altro possa farlo
	Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	if (Boolean.TRUE.equals(bloccaFile)) {
	    log.debug("setModel# Blocco i file selezionati alla firma. File bloccati dal responsabile con codice {} ", codiceResponsabile);
	    oggettiService.updateFilesBloccaModifica(codiciOggettiReali, codiceResponsabile);
	} else {
	    log.debug("setModel# Sblocco i file selezionati alla firma. File bloccati dal responsabile con codice {} ", codiceResponsabile);
	    oggettiService.updateFilesRimuoviBloccoModifica(codiciOggettiReali, codiceResponsabile);
	}
	// Nel caso richiamiamo il metdoto dopo aver firmato confronto le liste
	// 1. List<CodiceFileIdDescrizioneBean> listaFiles : oggetti che contengono le informazioni da visualizzare (nome,id,etc)
	// 2. List<FirmaremotaOTPResultHelper> firmaremotaOTPResultHelpers : oggetti che conteniene l'esito della di firma avvenuta
	// Il confronto serve per agganciare all'oggetto CodiceFileIdDescrizioneBean l'esito della firma presente firmaremotaOTPResultHelpers
	// e mostrarlo a video.
	if (isFirmato && firmaremotaOTPResultHelpers != null) {
	    for (CodiceFileIdDescrizioneBean codiceFileIdDescrizioneBean : listaFiles) {
		for (FirmaremotaOTPResultHelper firmaremotaOTPResultHelper : firmaremotaOTPResultHelpers) {
		    Integer cod = Integer.parseInt(codiceFileIdDescrizioneBean.getCodice());
		    if (cod.equals(firmaremotaOTPResultHelper.getCodiceOggetto())) {
			StringBuilder sb = new StringBuilder();
			if (!firmaremotaOTPResultHelper.getStatus().equals("OK")) {
			    sb = sb.append("Processo di firma terminato con errore: ").append(firmaremotaOTPResultHelper.getReturnCode()).append(": ")
				    .append(firmaremotaOTPResultHelper.getDescription());
			} else {
			    sb = sb.append("Processo di firma terminato correttamente");
			}
			codiceFileIdDescrizioneBean.setStatusFirma(firmaremotaOTPResultHelper.getStatus());
			codiceFileIdDescrizioneBean.setEsitoFirma(sb.toString());
			break;
		    }
		}
	    }
	    model.addAttribute("isFirmato", isFirmato);
	}
	// Setto il model
	model.addAttribute("listaFilesDaFirmare", listaFiles);
	model.addAttribute("codiceoggettoArray", codiceoggettoArray);
	model.addAttribute("isMettiAllafirma", isMettiAllafirma);
	model.addAttribute("isSegnaComeFirmatoAttivo", true);
	model.addAttribute("isPadesPossibile", isPadesPossibile);
	model.addAttribute("providerFirma", getFirmeRemoteAttive());
    }

    private boolean checkPossibilitaFirmaPades(Oggetti oggetto) {

	if (!WebConstants.PDF.equalsIgnoreCase(Utilities.getFileExtension(oggetto.getNomefile()))) {
	    log.debug("checkPossibilitaFirmaPades# Trovato un file non pdf. {} ", oggetto.getNomefile());
	    return false;
	}
	return true;
    }

    private List<ChiaveValoreBean<Integer, String>> getFirmeRemoteAttive() {

	return this.firmeRemoteService.getFirmeAttive();
    }

    @Override
    protected void setPageAttributes(Model model) {

	// Implementazione non necessaria
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	// Implementazione non necessaria
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	// Implementazione non necessaria
    }
}
