package it.gruppoinit.pal.gp.areariservata.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.areariservata.security.LoggedUser;
import it.gruppoinit.pal.gp.areariservata.service.AlberoProcARJService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.areariservata.web.util.DeployProperties;
import it.gruppoinit.pal.gp.areariservata.web.util.FileUtils;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.ElenchiprofessionalibaseService;
import it.gruppoinit.pal.gp.core.service.InventarioprocTipititoloService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.TitoliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;
import it.lineacomune.ws.FileTemporaneiWSClient;
import it.lineacomune.ws.ReportWSClient;
import it.phoops.people.hostingtemporaneo.service.FileTrovato;
import it.phoops.people.hostingtemporaneo.service.RichiestaRicerca;
import it.phoops.people.hostingtemporaneo.service.RispostaRicerca;
import it.phoops.people.hostingtemporaneo.service.RispostaRicerca.Files;

@Controller
public class AjaxController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AjaxController.class);
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private CittadinanzaService cittadinanzaService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private AlberoProcARJService alberoProcARJService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private InventarioprocTipititoloService inventarioprocTipititoloService;
    @Autowired
    private ReportWSClient reportWSClient;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private DeployProperties deployProperties;
    @Autowired
    private FileTemporaneiWSClient fileTemporaneiWsClient;
    @Autowired
    private TitoliService titoliService;
    @Autowired
    private ElenchiprofessionalibaseService elenchiprofessionalibaseService;

    @RequestMapping
    public void getOrdineProfessionisti(@RequestParam("term") String term, HttpServletResponse response) throws IOException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	if (StringUtils.isNotBlank(term)) {
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(new FilterField<String>("epDescrizione", FieldOperationsEnum.CONTAINS, new String[] { term }, String.class));
	    ft.addRestriction(criterio);
	}
	FilterOrder<String> orderByScDescrizione = new FilterOrder<String>(new FilterField<String>("epDescrizione", null, String.class));
	ft.addOrder(orderByScDescrizione);
	List<Elenchiprofessionalibase> list = elenchiprofessionalibaseService.findByFilterTable(ft);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (Elenchiprofessionalibase albo : list) {
		buffer.append("{\"label\":\"").append(albo.getEpDescrizione()).append("\",\"id\":\"").append(albo.getId()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getComune(@RequestParam("term") String term, HttpServletResponse response) throws IOException {

	log.debug("getComune({})", term);
	List<Comuni> list = comuniService.findByDescrizione(term);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (Comuni comune : list) {
		buffer.append("{\"id\":\"").append(comune.getCf()).append("\",\"label\":\"").append(comune.getComune()).append("\",\"value\":\"")
			.append(comune.getComune()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getCittadinanza(@RequestParam("term") String term, HttpServletResponse response) throws IOException {

	log.debug("getCittadinanza({})", term);
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	if (StringUtils.isNotBlank(term)) {
	    String[] params = StringUtils.split(term);
	    FilterRestriction criterio = new FilterRestriction();
	    for (String param : params) {
		criterio.addFilterField(new FilterField<String>("cittadinanza", FieldOperationsEnum.CONTAINS, new String[] { param }, String.class));
	    }
	    ft.addRestriction(criterio);
	}
	FilterOrder<String> orderByScDescrizione = new FilterOrder<String>(new FilterField<String>("cittadinanza", null, String.class));
	ft.addOrder(orderByScDescrizione);
	List<Cittadinanza> list = cittadinanzaService.findByFilterTable(ft);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (Cittadinanza citt : list) {
		buffer.append("{\"id\":\"").append(citt.getCodice()).append("\",\"label\":\"").append(citt.getCittadinanza())
			.append("\",\"value\":\"").append(citt.getCittadinanza()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getLocalizzazione(@RequestParam("term") String term, HttpServletResponse response) throws IOException {

	//TODO manca gestione comuni associati
	String codiceComune = null;// TODO manca gestione comuni associati
	List<Stradario> list = stradarioService.findByMatchParziale(term, codiceComune/* TODO */, 0, WebConstants.NUM_MAX_RESULTS);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (Stradario loc : list) {
		buffer.append("{\"id\":\"").append(loc.getId().getCodice()).append("\",\"viario\":\"").append(loc.getCodviario())
			.append("\",\"label\":\"").append(loc.getDescrizioneCompleta()).append("\",\"value\":\"").append(loc.getDescrizioneCompleta())
			.append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getTitolo(@RequestParam("term") String term, HttpServletResponse response) throws IOException {

	List<Titoli> list = titoliService.findByDescrizione(term);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (Titoli titolo : list) {
		buffer.append("{\"id\":\"").append(titolo.getId().getCodice()).append("\",\"label\":\"").append(titolo.getTitolo())
			.append("\",\"value\":\"").append(titolo.getTitolo()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getAlberoProc(@RequestParam("dir") String scCodice, HttpServletResponse response) throws IOException {

	StringBuffer buffer = new StringBuffer();
	String ext = "txt";
	scCodice = scCodice.replaceAll("/", "");
	List<Alberoproc> list = alberoProcARJService.findSubTree(scCodice);
	buffer.append("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
	for (Alberoproc alberoproc : list) {
	    String current = alberoproc.getScCodice();
	    if (alberoProcARJService.hasSubTree(current)) {
		buffer.append("<li class=\"directory collapsed\"><a href=\"#\" id=\"" +
			alberoproc.getId().getCodice() +
			"\" rel=\"" +
			current +
			"/\">" +
			alberoproc.getScDescrizione() +
			"</a></li>");
	    } else {
		buffer.append("<li class=\"file ext_" +
			ext +
			"\"><a href=\"#\" id=\"" +
			alberoproc.getId().getCodice() +
			"\" rel=\"" +
			alberoproc.getVwAlberoproc().getScDescrizione() +
			"\">" +
			alberoproc.getScDescrizione() +
			"</a></li>");
	    }
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getAlberoProc2(@RequestParam("id") String scCodice, HttpServletResponse response) throws IOException {

	StringBuffer buffer = new StringBuffer("[");
	List<Alberoproc> list = alberoProcARJService.findSubTree(scCodice);
	for (Alberoproc alberoproc : list) {
	    String childScCodice = alberoproc.getScCodice();
	    if (alberoProcARJService.hasSubTree(childScCodice)) {
		//folder
		buffer.append("{\"data\" : { \"title\" : \"").append(alberoproc.getScDescrizione()).append("\"}, \"attr\" : {\"id\": \"")
			.append(childScCodice).append("\", \"rel\":\"").append("FOLDER").append("\"},\"state\" : \"closed\"},");
	    } else {
		//file
		buffer.append("{\"data\" : { \"title\" : \"" + alberoproc.getScDescrizione())
			.append("\", \"icon\": \"../images/file.png\"}, \"attr\" : {\"id\": \"").append(alberoproc.getId().getCodice())
			.append("\", \"rel\":\"").append(alberoproc.getVwAlberoproc().getScDescrizione()).append("\"},\"state\" : \"opened\"},");
	    }
	}
	buffer.deleteCharAt(buffer.length() - 1);
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getProcedimentoTipiTitolo(@RequestParam("id") Integer codiceProcedimento, HttpServletResponse response) throws IOException {

	List<InventarioprocTipititolo> list = inventarioprocTipititoloService.findByInventarioproc(codiceProcedimento);
	StringBuffer buffer = new StringBuffer("[");
	for (InventarioprocTipititolo tipoTitolo : list) {
	    buffer.append("{\"id\":\"").append(tipoTitolo.getId().getCodice());
	    buffer.append("\",\"label\":\"").append(tipoTitolo.getTipotitolo());
	    buffer.append("\",\"value\":\"").append(tipoTitolo.getTipotitolo());
	    //
	    buffer.append("\",\"mostra_numero\":\"").append(tipoTitolo.getFlgMostraNumero());
	    buffer.append("\",\"mostra_data\":\"").append(tipoTitolo.getFlgMostraData());
	    buffer.append("\",\"mostra_da\":\"").append(tipoTitolo.getFlgMostraRilasciatoDa());
	    buffer.append("\",\"richiede_allegato\":\"").append(tipoTitolo.getFlgRichiedeAllegato());
	    buffer.append("\",\"verifica_firma\":\"").append(tipoTitolo.getFlgVerificaFirmaAllegato());
	    //
	    buffer.append("\"},");
	}
	buffer.deleteCharAt(buffer.length() - 1);
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void findRicercaDyncampi(@RequestParam("term") String term, @RequestParam("codiceCampo") Integer codiceCampo, HttpServletResponse response)
	    throws IOException {

	log.debug("findRicercaDyncampi({})", term);
	List<ChiaveValoreBean<String, String>> list = dyn2CampiService.findValoriPerCampo(term, codiceCampo, WebConstants.NUM_MAX_RESULTS);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (ChiaveValoreBean<String, String> bean : list) {
		buffer.append("{\"id\":\"").append(bean.getChiave()).append("\",\"label\":\"").append(bean.getValore()).append("\",\"value\":\"")
			.append(bean.getValore()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void findCurrentSoftware(HttpServletResponse response) throws IOException {

	String code = ORMHelper.getSoftware();
	String descrizione = "";
	Software software = softwareService.findById(code);
	if (software != null) {
	    descrizione = software.getDescrizione();
	}
	response.setContentType("text/plain");
	response.getWriter().write(descrizione);
	if (log.isDebugEnabled())
	    log.debug("call findCurrentSoftware return: '" + descrizione + "'");
    }

    @RequestMapping
    public void downloadOggetto(@RequestParam(value = "idOggetto") Integer idOggetto,
	    @RequestParam(value = "fileRename", required = false) String fileRename, HttpServletResponse response) throws IOException {

	if (idOggetto != null) {
	    //response.setContentType("text/plain");
	    Oggetti obj = this.oggettiService.findById(new PkId(idOggetto));
	    if (null != obj) {
		byte[] data = obj.getOggetto();
		String fileName = obj.getNomefile();
		String mimeType = AttachmentsUtils.getMimeTypeForFileName(fileName);
		if (StringUtils.isNotBlank(fileRename)) {
		    fileName = fileRename;
		}
		response.setContentType(mimeType);
		response.setContentLength(data.length);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		ServletOutputStream sos = response.getOutputStream();
		AttachmentsUtils.writeBytesToStream(data, sos);
		sos.flush();
		sos.close();
	    } else {
		response.setStatus(404);
	    }
	} else {
	    //file non trovato
	    response.setStatus(404);
	}
    }

    @RequestMapping
    public void download(@RequestParam(value = "id") Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (id != null) {
	    if (log.isDebugEnabled()) {
		log.debug("download# verifico la querystring {}", request.getQueryString());
	    }
	    boolean verificaMac = FileUtils.verificaLinkFile(request.getQueryString());
	    if (verificaMac == false) {
		response.setStatus(404);
		return;
	    }
	    Oggetti obj = this.oggettiService.findById(new PkId(id));
	    if (null != obj) {
		byte[] data = obj.getOggetto();
		String fileName = obj.getNomefile();
		String mimeType = AttachmentsUtils.getMimeTypeForFileName(fileName);
		response.setContentType(mimeType);
		response.setContentLength(data.length);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		ServletOutputStream sos = response.getOutputStream();
		AttachmentsUtils.writeBytesToStream(data, sos);
		sos.flush();
		sos.close();
	    } else {
		response.setStatus(404);
	    }
	} else {
	    //file non trovato
	    response.setStatus(404);
	}
    }

    @RequestMapping
    public void downloadOggettoInFormato(@RequestParam(value = "idOggetto") Integer idOggetto,
	    @RequestParam(value = "formato", required = false) String formato,
	    @RequestParam(value = "fileRename", required = false) String fileRename, HttpServletResponse response) throws IOException {

	if (idOggetto != null) {
	    //response.setContentType("text/plain");
	    Oggetti obj = this.oggettiService.findById(new PkId(idOggetto));
	    if (null != obj) {
		byte[] data = obj.getOggetto();
		String fileName = obj.getNomefile();
		if (StringUtils.isNotBlank(fileRename)) {
		    fileName = fileRename;
		}
		String mimeType = AttachmentsUtils.getMimeTypeForFileName(fileName);
		String inputFormat = FileConverterWsClient.ContentType.HTML.name();
		if (mimeType.equals("text/rtf")) {
		    inputFormat = "RTF";
		}
		if (StringUtils.isNotBlank(formato) && !inputFormat.equalsIgnoreCase(formato)) {
		    FileConverterWsClient fileConverterWService = new FileConverterWsClient();
		    ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), data, mimeType, formato.toUpperCase());
		    ConvertBinaryResponse cResp = fileConverterWService.convertBinary(cbr);
		    /*
		    ConvertRequest cReq = new ConvertRequest(ORMHelper.getToken(), new String(data), inputFormat, formato.toUpperCase());
		    ConvertResponse cResp = fileConverterWService.convert(cReq);
		    */
		    data = cResp.getBinaryData();
		    //modifico l'estensione del file restituito
		    fileName = FilenameUtils.removeExtension(fileName).concat("." + formato.toLowerCase());
		}
		response.setContentType(mimeType);
		response.setContentLength(data.length);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		ServletOutputStream sos = response.getOutputStream();
		AttachmentsUtils.writeBytesToStream(data, sos);
		sos.flush();
		sos.close();
	    } else {
		response.setStatus(HttpStatus.SC_NOT_FOUND);
	    }
	} else {
	    //file non trovato
	    response.setStatus(HttpStatus.SC_NOT_FOUND);
	}
    }

    @RequestMapping
    public void calcolaCF(@RequestParam(value = "cognome", required = false) String cognome,
	    @RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "data", required = false) String data,
	    @RequestParam(value = "sesso", required = false) String sesso, @RequestParam(value = "comune", required = false) String codiceComune,
	    HttpServletResponse response) throws IOException {

	log.debug("calcolaCF(cognome={},nome={},sesso={},data={},comune={})", new Object[] { cognome, nome, sesso, data, codiceComune });
	String cf = "";
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	if (StringUtils.isNotBlank(cognome) && StringUtils.isNotBlank(nome) && StringUtils.isNotBlank(sesso) && StringUtils.isNotBlank(codiceComune)
		&& StringUtils.isNotBlank(data)) {
	    if (sesso.equalsIgnoreCase("f") || sesso.equalsIgnoreCase("m")) {
		if (codiceComune.length() == 4) {
		    try {
			sdf.parse(data);
			log.debug("calcolaCF: dati completi, eseguo il calcolo");
			cf = Utilities.calcolaCodiceFiscale(cognome, nome, data, sesso, codiceComune);
			log.debug("calcolaCF: {}", cf);
		    } catch (ParseException e) {
		    } catch (Exception e) {
			log.error("calcolaCF(cognome={},nome={},sesso={},data={},comune={})\n{}",
				new Object[] { cognome, nome, sesso, data, codiceComune, e });
		    }
		}
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(cf);
	return;
    }

    @RequestMapping
    public void report(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	try {
	    if (deployProperties.isLineacomuneWsReportEnable()) {
		String idEnte = ORMHelper.getIdcomuneAlias();
		//TODO falorni ha detto di metterli ad 1 per ora
		int idCanale = 1;
		int idServizio = 1;
		int idVersione = 1;
		//
		String url = req.getRequestURI();
		String referer = req.getHeader("referer");
		String host = req.getRemoteHost();
		String agent = req.getHeader("User-Agent");
		String sessionId = req.getSession().getId();
		String cf = ((LoggedUser) userSecurityService.getCurrentlyAuthenticatedUser()).getCf();
		FoArjSteps currentStep = (FoArjSteps) req.getSession().getAttribute("CURRENT_STEP");
		FoArjSteps previousStep = (FoArjSteps) req.getSession().getAttribute("PREVIOUS_STEP");
		FoArjSteps nextStep = (FoArjSteps) req.getSession().getAttribute("NEXT_STEP");
		int id_step = currentStep.getOrdine().intValue();
		NuovaIstanzaCommand command = (NuovaIstanzaCommand) req.getSession().getAttribute("nuovaIstanzaCommand");
		String step_tipo = "INTERMEDIO";
		if (previousStep == null) {
		    step_tipo = "INIZIO";
		}
		if (nextStep == null) {
		    step_tipo = "FINE";
		}
		String step_desc = currentStep.getTitolo();
		String step_time = "" + (new Date()).getTime();
		if (StringUtils.isEmpty(command.getLineaComunecodiceTransazioneReport())) {
		    String codTransazione = reportWSClient.createAccesso(idEnte, idCanale, idServizio, idVersione, url, referer, host, agent,
			    sessionId, cf);
		    command.setLineaComunecodiceTransazioneReport(codTransazione);
		}
		reportWSClient.createTransazione(command.getLineaComunecodiceTransazioneReport(), id_step, step_tipo, step_desc, step_time);
	    }
	} catch (Exception e) {
	    log.error("report", e);
	}
	resp.setContentType("text/plain");
	resp.getWriter().write("");
	return;
    }

    @RequestMapping
    public void sfogliaFileTemporanei(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	try {
	    String cf = ((Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getCodicefiscale();
	    log.debug("sfogliaFileTemporanei: {}", cf);
	    RichiestaRicerca richiestaRicerca = new RichiestaRicerca();
	    richiestaRicerca.setIdentificativoUtente(cf);
	    /////
	    /*
	    String s = "[{\"value\":\"1\",\"label\":\"file1\"}, {\"value\":\"2\",\"label\":\"file2\"}]";
	    log.debug("sfogliaFileTemporanei: {}", s);
	    resp.setContentType("text/plain");
	    resp.getWriter().write(s);
	    */
	    /////
	    RispostaRicerca rispostaRicerca = fileTemporaneiWsClient.ricercaFile(richiestaRicerca);
	    if (rispostaRicerca.getStatus() == 200) {
		StringBuffer buffer = new StringBuffer("[");
		Files files = rispostaRicerca.getFiles();
		if (files.getFile() != null && !files.getFile().isEmpty()) {
		    for (FileTrovato file : files.getFile()) {
			buffer.append("{\"label\":\"").append(file.getNomeOriginale()).append("\",\"value\":\"").append(file.getUri()).append("\"},");
		    }
		    buffer.deleteCharAt(buffer.length() - 1);
		}
		buffer.append("]");
		log.debug("sfogliaFileTemporanei: {}", buffer.toString());
		resp.setContentType("text/plain");
		resp.getWriter().write(buffer.toString());
	    } else {
		log.error("sfogliaFileTemporanei: status={}, statusDesc={}", rispostaRicerca.getStatus(), rispostaRicerca.getStatusDescription());
	    }
	} catch (Exception e) {
	    log.error("sfogliaFileTemporanei", e);
	}
    }
}
