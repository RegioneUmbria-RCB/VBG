package it.gruppoinit.pal.gp.areariservata.web;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.areariservata.service.AlberoProcARJService;
import it.gruppoinit.pal.gp.areariservata.web.util.DeployProperties;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBean;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneServiziService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.ElenchiprofessionalibaseService;
import it.gruppoinit.pal.gp.core.service.InventarioprocTipititoloService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.TitoliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VelocityRendererService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private AlberoprocService alberoprocService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private InventarioprocTipititoloService inventarioprocTipititoloService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private DeployProperties deployProperties;
    @Autowired
    private TitoliService titoliService;
    @Autowired
    private ElenchiprofessionalibaseService elenchiprofessionalibaseService;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private VelocityRendererService velocityRendererService;
    @Autowired
    private ConfigurazioneServiziService configurazioneServiziService;

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
	List<Comuni> list = comuniService.findByDescrizione(term, 0);
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
			.append("\",\"label\":\"").append(loc.getDescrizioneCompleta()).append("\",\"value\":\"")
			.append(loc.getDescrizioneCompleta()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void ricercaInterventoPerDescrizione(@RequestParam("testo") String testo,
	    @RequestParam(value = "tipoRicerca", required = false) String tipoRicerca,
	    @RequestParam(value = "modoRicerca", required = false) String modoRicerca,
	    @RequestParam(value = "codiceComune", required = false) String codiceComune, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	boolean filtraSoloComunica = checkAccessoComunica(request);
	boolean soloModulisticaNazionale = configurazioneServiziService.checkModulisticaNazionale(ORMHelper.getIdente());
	List<InterventoSimpleBean> list = alberoprocService.findInterventiByDescrizioneNew(ORMHelper.getIdcomunebase(), testo, tipoRicerca,
		modoRicerca, 0, 50, filtraSoloComunica, soloModulisticaNazionale, false, false, codiceComune);
	//	List<InterventoSimpleBean> list = alberoprocService.findInterventiByDescrizione(ORMHelper.getIdcomunebase(), testo, tipoRicerca,
	//		modoRicerca, 0, 50, filtraSoloComunica);
	response.setContentType("application/json");
	StringBuffer buffer = new StringBuffer("[");
	if (!list.isEmpty()) {
	    if (list != null && !list.isEmpty()) {
		for (InterventoSimpleBean isb : list) {
		    buffer.append("{\"id\":\"").append(String.valueOf(isb.getId())).append("\",\"descrizione\":\"").append(isb.getText())
			    .append("\",\"codice\":\"").append(isb.getScCodice()).append("\"},");
		}
		buffer.deleteCharAt(buffer.length() - 1);
	    }
	}
	buffer.append("]");
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
	boolean soloModulisticaNazionale = configurazioneServiziService.checkModulisticaNazionale(ORMHelper.getIdente());
	List<Alberoproc> list = alberoProcARJService.findSubTree(ORMHelper.getIdcomunebase(), scCodice, false, soloModulisticaNazionale);
	buffer.append("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
	for (Alberoproc alberoproc : list) {
	    String current = alberoproc.getScCodice();
	    if (alberoProcARJService.hasSubTree(ORMHelper.getIdcomunebase(), current)) {
		buffer.append("<li class=\"directory collapsed\"><a href=\"#\" id=\"" + alberoproc.getId().getCodice() + "\" rel=\"" + current
			+ "/\">" + alberoproc.getScDescrizione() + "</a></li>");
	    } else {
		buffer.append("<li class=\"file ext_" + ext + "\"><a href=\"#\" id=\"" + alberoproc.getId().getCodice() + "\" rel=\""
			+ alberoproc.getVwAlberoproc().getScDescrizione() + "\">" + alberoproc.getScDescrizione() + "</a></li>");
	    }
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getAlberoProc2(@RequestParam("id") String scCodice, HttpServletResponse response) throws IOException {

	StringBuffer buffer = new StringBuffer("[");
	boolean soloModulisticaNazionale = configurazioneServiziService.checkModulisticaNazionale(ORMHelper.getIdente());
	List<Alberoproc> list = alberoProcARJService.findSubTree(ORMHelper.getIdcomunebase(), scCodice, false, soloModulisticaNazionale);
	for (Alberoproc alberoproc : list) {
	    String childScCodice = alberoproc.getScCodice();
	    if (alberoProcARJService.hasSubTree(ORMHelper.getIdcomunebase(), childScCodice)) {
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
    public void getAlberoProcCart(@RequestParam("id") String scCodice, HttpServletRequest request, HttpServletResponse response) throws IOException {

	StringBuffer buffer = new StringBuffer("[");
	if (StringUtils.defaultString(scCodice).equalsIgnoreCase("MODULISTICA_PRELIMINARE")) {
	    InputStream in = null;
	    String jsonModulistica = "";
	    try {
		in = this.getClass().getClassLoader().getResource(WebConstants.CONFIG_FILES_FOLDER + "modulistica_preliminare.json").openStream();
		jsonModulistica = IOUtils.toString(in, "UTF-8");
		buffer.append(jsonModulistica);
	    } catch (Exception e) {
		buffer.append("{\"data\" : { \"title\" : \"Modello Procura Speciale")
			.append("\", \"icon\": \"../images/file.png\"}, \"attr\" : {\"id\": \"procura_speciale")
			.append("\", \"rel\":\"DOC:procura_speciale").append("\"},\"state\" : \"opened\"},");
		buffer.append("{\"data\" : { \"title\" : \"Modello Requisiti Morali")
			.append("\", \"icon\": \"../images/file.png\"}, \"attr\" : {\"id\": \"requisiti_morali")
			.append("\", \"rel\":\"DOC:requisiti_morali").append("\"},\"state\" : \"opened\"},");
		buffer.append("{\"data\" : { \"title\" : \"Modello Requisiti Professionali")
			.append("\", \"icon\": \"../images/file.png\"}, \"attr\" : {\"id\": \"requisiti_professionali")
			.append("\", \"rel\":\"DOC:requisiti_professionali").append("\"},\"state\" : \"opened\"},");
		buffer.append("{\"data\" : { \"title\" : \"Modello Allegati Pesanti")
			.append("\", \"icon\": \"../images/file.png\"}, \"attr\" : {\"id\": \"allegati_pesanti")
			.append("\", \"rel\":\"DOC:allegati_pesanti").append("\"},\"state\" : \"opened\"},");
	    } finally {
		if (in != null) {
		    try {
			in.close();
		    } catch (IOException e) {
		    }
		}
	    }
	} else {
	    boolean isComunica = checkAccessoComunica(request);
	    boolean soloModulisticaNazionale = configurazioneServiziService.checkModulisticaNazionale(ORMHelper.getIdente());
	    List<Alberoproc> list = alberoProcARJService.findSubTree(ORMHelper.getIdcomunebase(), scCodice, isComunica, soloModulisticaNazionale);
	    boolean isFirstCall = false;
	    if (scCodice == null) {
		isFirstCall = true;
	    }
	    for (Alberoproc alberoproc : list) {
		String childScCodice = alberoproc.getScCodice();
		if (alberoProcARJService.hasSubTree(ORMHelper.getIdcomunebase(), childScCodice)) {
		    //folder
		    buffer.append("{\"data\" : { \"title\" : \"").append(alberoproc.getScDescrizione()).append("\"}, \"attr\" : {\"id\": \"")
			    .append(childScCodice).append("\", \"rel\":\"").append("FOLDER").append("\"},\"state\" : \"closed\"},");
		} else {
		    //file
		    StpEndoTipo2 s = stpEndoTipo2Service.findbyAlberoproc(ORMHelper.getIdcomunebase(), alberoproc.getId().getCodice());
		    boolean show = true;
		    if (s == null) {
			show = false;
		    } else if (s.getTipo().equalsIgnoreCase(StpEndoTipo2Service.TIPO_CATEGORIA)) {
			show = false;
		    }
		    if (show) {
			buffer.append("{\"data\" : { \"title\" : \"" + alberoproc.getScDescrizione())
				.append("\", \"icon\": \"../images/file.png\"}, \"attr\" : {\"id\": \"").append(alberoproc.getId().getCodice())
				.append("\", \"rel\":\"").append(alberoproc.getVwAlberoproc().getScDescrizione())
				.append("\"},\"state\" : \"opened\"},");
		    } else {
			buffer.append("{\"data\" : { \"title\" : \"" + alberoproc.getScDescrizione())
				.append("\", \"icon\": \"../images/error.gif\"}, \"attr\" : {\"id\": \"").append(alberoproc.getId().getCodice())
				.append("\", \"non_selezionabile\": \"true\", \"rel\":\"").append(alberoproc.getVwAlberoproc().getScDescrizione())
				.append("\"},\"state\" : \"opened\"},");
		    }
		}
	    }
	    if (isFirstCall) {
		buffer.append("{\"data\" : { \"title\" : \"Modulistica preliminare").append("\"}, \"attr\" : {\"id\": \"MODULISTICA_PRELIMINARE")
			.append("\", \"rel\":\"").append("FOLDER").append("\"},\"state\" : \"closed\"},");
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
    public void findRicercaDyncampi(@RequestParam("term") String term, @RequestParam("codiceCampo") String codiceCampo, HttpServletResponse response)
	    throws IOException {

	log.debug("findRicercaDyncampi({})", term);
	term = StringUtils.defaultString(term);
	String[] valori = codiceCampo.split("_");
	List<ChiaveValoreBean<String, String>> list = dyn2CampiService.findValoriPerCampo(term, valori[0], Integer.parseInt(valori[1]),
		WebConstants.NUM_MAX_RESULTS);
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
    public void download(@RequestParam(value = "id") Integer id, @RequestParam(value = "idComuneOggetto") String idComuneOggetto,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (id != null) {
	    if (log.isDebugEnabled()) {
		log.debug("download# verifico la querystring {}", request.getQueryString());
	    }
	    boolean verificaMac = Utilities.verificaLinkFile(request.getQueryString());
	    if (verificaMac == false) {
		response.setStatus(404);
		return;
	    }
	    Oggetti obj = this.oggettiService.findById(new PkId(idComuneOggetto, id));
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
	    @RequestParam(value = "idcomuneOggetto", required = true) String idcomuneOggetto,
	    @RequestParam(value = "formato", required = false) String formato,
	    @RequestParam(value = "fileRename", required = false) String fileRename, HttpServletResponse response) throws IOException {

	if (idOggetto != null) {
	    String idcomune = ORMHelper.getIdcomune();
	    if (StringUtils.isNotBlank(idcomuneOggetto)) {
		idcomune = idcomuneOggetto;
	    }
	    //response.setContentType("text/plain");
	    Oggetti obj = this.oggettiService.findById(new PkId(idcomune, idOggetto));
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
			log.error("calcolaCF(cognome={},nome={},sesso={},data={},comune={})\n{}", new Object[] { cognome, nome, sesso, data,
				codiceComune, e });
		    }
		}
	    }
	}
	response.setContentType("text/plain");
	response.getWriter().write(cf);
	return;
    }

    @RequestMapping
    public void ajaxGetUtente(HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (checkAccessoComunica(request)) {
	    response.getOutputStream().print("<!-- UTENTE ANONIMO -->");
	    return;
	}
	Anagrafe delegante = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	Map<Object, Object> contextData = new HashMap<Object, Object>();
	contextData.put("delegante", delegante);
	String delegatoCf = (String) request.getSession().getAttribute(WebConstants.ARPA_AUTHENTICATED_USER_CF);
	Anagrafe delegato = anagrafeService.findByCF(delegatoCf);
	contextData.put("delegato", delegato);
	String delega = (String) request.getSession().getAttribute(WebConstants.ARPA_DELEGATING_USER_CF);
	String tpl = "/general/accettatore_auth_user.vm";
	if (StringUtils.isBlank(delega)) {
	    tpl = "/general/accettatore_auth_user_no_delega.vm";
	}
	contextData.put("Utilities", new Utilities());
	String htmlModulo = velocityRendererService.renderTemplate(contextData, tpl);
	response.getOutputStream().print(htmlModulo);
    }
}
