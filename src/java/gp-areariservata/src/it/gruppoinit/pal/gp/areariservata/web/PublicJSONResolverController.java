package it.gruppoinit.pal.gp.areariservata.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.kahadb.util.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.TipoSchedaEndo;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.Infosuap;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Modelli;
import it.gruppoinit.pal.gp.core.domain.News;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Tipimodelli;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DownloadBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FaqBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FaqBeanComparator;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FaqPerCategoriaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FaqPerCategoriaBeanComparator;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FaqPerModuloBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FaqPerModuloBeanComparator;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InfoSportelloBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InterventoSimpleBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ModulisticaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ModulisticaBeanComparator;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.NewsBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.NewsBeanComparator;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.NormegeneraliBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.OrariEContattiBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoSimpleBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.SoftwareBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.TipoModulisticaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.TipoModulisticaBeanComparator;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocFoTopService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.FaqService;
import it.gruppoinit.pal.gp.core.service.FaqclassiService;
import it.gruppoinit.pal.gp.core.service.InfosuapService;
import it.gruppoinit.pal.gp.core.service.InventarioprocFoTopService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.ModelliService;
import it.gruppoinit.pal.gp.core.service.NewsService;
import it.gruppoinit.pal.gp.core.service.NormegeneraliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.TipimodelliService;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.LoginRequest;
import it.gruppoinit.sigeprosecurity.schema.LoginResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.sojo.interchange.json.JsonParser;

/**
 * <pre>
 * PATH_BASE/faq/:idcomune/:software PATH_BASE/faq/:idcomune/:software/top PATH_BASE/faqpermodulo/:idcomune/:software
 * PATH_BASE/faqpercategoria/:idcomune/:software PATH_BASE/infosportello/:idcomune/:software
 * PATH_BASE/interventi/:idcomune/:software/top PATH_BASE/interventi/:idcomune/:software/sottonodi/:idNodoPadre
 * PATH_BASE/interventi/:idcomune/:software/gerarchia/:idNodo
 * PATH_BASE/interventi/:idcomune/:software/gerarchiadettaglio/:idNodo PATH_BASE/interventi/:idcomune/:software/:idNodo
 * PATH_BASE/news/:idcomune/:software PATH_BASE/news/:idcomune/:software/top
 * PATH_BASE/news/:idcomune/:software/primopiano PATH_BASE/news/:idcomune/:software/:id
 * PATH_BASE/normativa/:idcomune/:software PATH_BASE/normativa/:idcomune/:software/top
 * PATH_BASE/orariecontatti/:idcomune/:software PATH_BASE/procedimenti/:idcomune/:software/top
 * PATH_BASE/procedimenti/:idcomune/:software/sottonodi/:idNodoPadre
 * PATH_BASE/procedimenti/:idcomune/:software/gerarchia/:idNodo
 * PATH_BASE/procedimenti/:idcomune/:software/gerarchiadettaglio/:idNodo PATH_BASE/procedimenti/:idcomune/:software/:id
 * PATH_BASE/modulistica/:idcomune/:software/ PATH_BASE/procedimenti/:idcomune/:software/categoria/:idcategoria </pre
 * 
 * @author riccardob
 *
 */
@RequestMapping("/public_json/")
@Controller
public class PublicJSONResolverController extends BaseResolverController {

    private static final String COND = "_cond_";
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private static final int MAX_RESULT_TOP = 3;
    private static final int MAX_RESULT_TOP_FAQ = 2;
    private static final int MAX_RESULT_TOP_PRIMOPIANO = 9999;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AlberoprocFoTopService alberoprocFoTopService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private FaqService faqService;
    @Autowired
    private FaqclassiService faqclassiService;
    @Autowired
    private InfosuapService infosuapService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private InventarioprocFoTopService inventarioprocFoTopService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private NormegeneraliService normegeneraliService;
    @Autowired
    private ModelliService modelliService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    private StpEndoTipo1Service stpEndoTipo1Service;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private SecurityWSClient securityWSClient;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private TipimodelliService tipimodelliService;
    @Autowired
    private IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService;

    //    final BeanGenerator beanGenerator = new BeanGenerator();
    //    private static Class interventiClass = null;
    //
    //    private Class<?> createInterventiBean() {
    //
    //	if (interventiClass != null) {
    //	    return interventiClass;
    //	}
    //	final Map<String, Class<?>> properties = new HashMap<String, Class<?>>();
    //	beanGenerator.setNamingPolicy(new NamingPolicy() {
    //
    //	    @Override
    //	    private String getClassName(final String prefix, final String source, final Object key, final Predicate names) {
    //
    //		return "it.gruppoinit.dynamic.json.InterventoTop";
    //	    }
    //	});
    //	properties.put("id", Integer.class);
    //	properties.put("descrizione", String.class);
    //	BeanGenerator.addProperties(beanGenerator, properties);
    //	interventiClass = (Class<?>) beanGenerator.createClass();
    //	return interventiClass;
    //    }
    @RequestMapping
    public ModelAndView resolveServizio(HttpServletRequest request, HttpServletResponse response) {

	ModelAndView m = resolveChiamata(request, response, uriMapping);
	setHeaders(response);
	ORMHelper.destroyORMHelper();
	return m;
    }

    private ModelAndView resolveChiamata(HttpServletRequest request, HttpServletResponse response, String uriMapping2) {

	String[] params = getParams(request);
	if (params == null) {
	    throw new RuntimeException("URL non valida " + request.getRequestURL());
	}
	String azione = params[0];
	if (StringUtils.isBlank(azione)) {
	    throw new RuntimeException("URL non valida " + request.getRequestURL());
	}
	PATH_BASE az = fromString(azione);
	setORMHelper(params[1], params[2]);
	switch (az) {
	case download:
	    return operazioniDownload(params, request, response);
	case faq:
	    return operazioniSuFAQ(params, request, response);
	case faqpermodulo:
	    return operazioniSuFAQPerModulo(params, request, response);
	case faqpercategoria:
	    return operazioniSuFAQPerCategoria(params, request, response);
	case infosportello:
	    return operazioniSuInfosportello(params);
	case interventi:
	    return operazioniSuInterventi(params, request);
	case news:
	    return operazioniSuNews(params, request);
	case normativa:
	    return operazioniSuNormativa(params);
	case orariecontatti:
	    return operazioniSuOrarieContatti(params);
	case procedimenti:
	    return operazioniSuProcedimenti(params, request);
	case modulistica:
	    return operazioniSuModulistica(params, request);
	default:
	    break;
	}
	return null;
    }

    private ModelAndView operazioniDownload(String[] params, HttpServletRequest request, HttpServletResponse response) {

	int l = params.length;
	String uid = params[l - 1];
	if (uid.indexOf("___") > 0) {
	    String[] codDesc = uid.split("___");
	    String alias = codDesc[0];
	    String realUid = codDesc[1];
	    if (!alias.equalsIgnoreCase(ORMHelper.getIdcomuneAlias())) {
		return downloadExternal(alias, realUid, request, response);
	    }
	    uid = uid.substring((uid.indexOf("___") + 3));
	}
	Integer codiceOggetto = oggettiMetadatiService.findByChiaveEValore(WebConstants.OGGETTI_FILE_UID, uid);
	if (codiceOggetto == null) {
	    throw new RuntimeException("Nessun oggetto trovato");
	}
	Oggetti o = oggettiService.findByIdLazy(new PkId(codiceOggetto));
	if (o == null) {
	    throw new RuntimeException("Nessun oggetto trovato");
	}
	String fmt = StringUtils.defaultString(request.getParameter("fmt"), "");
	boolean performConversion = StringUtils.isNotBlank(fmt);
	if (performConversion) {
	    if ("pdf".equalsIgnoreCase(fmt) || "pdfc".equalsIgnoreCase(fmt)) {
		if (StringUtils.defaultString(o.getNomefile()).toUpperCase().endsWith(".PDF")) {
		    // 2016-04-27 BOCCI-GARGAGLI la conversione non va fatta se il file di origine è pdf ed è specificato di convertire in PDF, PDFC
		    performConversion = false;
		}
	    }
	}
	if (performConversion) {
	    // formatta in 
	    // passa a fileconverter	    
	    InputStream is = oggettiService.getOggettoAsInputStream(codiceOggetto);
	    String tipoFormattazione = request.getParameter("fmt");
	    FileConverterWsClient client = new FileConverterWsClient();
	    ConvertBinaryRequest req = new ConvertBinaryRequest();
	    try {
		String guestUserId = deployProperties.getServiziUserid();
		String token = getGuestSecurityToken(params[1], guestUserId, request.getRemoteAddr());
		req.setToken(token);
		// String cType = contenttypesService.findMimeTypeByFileName(o.getNomefile());
		String cType = Utilities.getFileExtension(o.getNomefile());
		if (StringUtils.isBlank(cType)) {
		    cType = "html";
		}
		req.setContentType(cType);
		req.setConversionType(tipoFormattazione);
		req.setBinaryData(IOUtils.toByteArray(is));
		IOUtils.closeQuietly(is);
		ConvertBinaryResponse res = client.convertBinary(req);
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		if ("true".equalsIgnoreCase((String) request.getParameter("inline"))) {
		    response.setHeader("Content-Disposition", " inline; filename=\"" + o.getNomefile() + "." + tipoFormattazione + "\"");
		} else {
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + o.getNomefile() + "." + tipoFormattazione + "\"");
		}
		response.setHeader("Content-transfer-encoding", "binary");
		response.setContentType(cType);
		// response.setContentLength(b.length);
		try {
		    ServletOutputStream out = response.getOutputStream();
		    out.flush();
		    InputStream is2 = new ByteArrayInputStream(res.getBinaryData());
		    IOUtils.copy(is2, out);
		    IOUtils.closeQuietly(is2);
		    out.flush();
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
	    } catch (Exception e) {
		throw new RuntimeException(e);
	    }
	} else {
	    InputStream is = oggettiService.getOggettoAsInputStream(codiceOggetto);
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    if ("true".equalsIgnoreCase((String) request.getParameter("inline"))) {
		response.setHeader("Content-Disposition", " inline; filename=\"" + o.getNomefile() + "\"");
	    } else {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + o.getNomefile() + "\"");
	    }
	    response.setHeader("Content-transfer-encoding", "binary");
	    String cType = contenttypesService.findMimeTypeByFileName(o.getNomefile());
	    response.setContentType(cType);
	    // response.setContentLength(b.length);
	    try {
		ServletOutputStream out = response.getOutputStream();
		IOUtils.copy(is, out);
		out.flush();
		IOUtils.closeQuietly(is);
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	}
	return null;
    }

    private ModelAndView downloadExternal(String alias, String realUid, HttpServletRequest request, HttpServletResponse response) {

	String url = checkUrlServiziCondivisi();
	String[] aliasUrl = url.split("#");
	url = aliasUrl[1];
	HttpClient cli = new HttpClient();
	String path = request.getPathInfo();
	path = path.replace(ORMHelper.getIdcomuneAlias(), alias).replace(ORMHelper.getIdcomuneAlias() + "___", "");
	url += path;
	HttpMethod method = new GetMethod(url);
	method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
	try {
	    log.debug("prima di eseguire la chiamata all'url {}", url);
	    int status = cli.executeMethod(method);
	    log.debug("la chiamata al 'url {} ha tornato status {}", url, status);
	    if (status != 200) {
		log.error("errore nella chiamata all'url {}, stato http {} ", url, status);
		return null;
	    }
	    InputStream is = method.getResponseBodyAsStream();
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-Disposition", method.getResponseHeader("Content-Disposition").getValue());
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType(method.getResponseHeader("Content-Type").getValue());
	    try {
		ServletOutputStream out = response.getOutputStream();
		IOUtils.copy(is, out);
		out.flush();
	    } catch (IOException e) {
		throw new RuntimeException(e);
	    }
	} catch (Exception e) {
	    log.error("errore nella chiamata all'url {}", url, e);
	}
	return null;
    }

    private ModelAndView operazioniSuInterventi(String[] params, HttpServletRequest request) {

	int l = params.length;
	String azione = params[l - 1];
	if (azione.equals("top")) {
	    return interventitop();
	} else {
	    for (String p : params) {
		if (p.equals("gerarchia")) {
		    Integer codiceIntervento = Integer.valueOf(azione);
		    // gerarchia
		    return gerarchianodiinterventi(codiceIntervento);
		} else if (p.equals("gerarchiadettaglio")) {
		    Integer codiceIntervento = Integer.valueOf(azione);
		    // gerarchia
		    return gerarchianodiinterventidettaglio(codiceIntervento);
		} else if (p.equals("sottonodi")) {
		    Integer codiceIntervento = Integer.valueOf(azione);
		    // sottonodi
		    return listasottonodiintervento(codiceIntervento);
		} else if (p.equals("cerca")) {
		    // sottonodi
		    return ricercaintervento(azione, request);
		}
	    }
	    Integer codiceIntervento = Integer.valueOf(azione);
	    return dettagliointervento(codiceIntervento, request);
	}
    }

    private String getTipoRicerca(HttpServletRequest request) {

	return StringUtils.defaultIfEmpty(request.getParameter("tipo"), "tutteParole");
    }

    private String getCampiRicerca(HttpServletRequest request) {

	return StringUtils.defaultIfEmpty(request.getParameter("campi"), "titoli");
    }

    private ModelAndView ricercaintervento(String testoDaCercare, HttpServletRequest request) {

	String tipoRicerca = getTipoRicerca(request);
	String campiRicerca = getCampiRicerca(request);
	Map model = new HashMap();
	List<InterventoSimpleBean> b = alberoprocService.findInterventiByDescrizione(testoDaCercare, tipoRicerca, campiRicerca, 0, 100);
	List<InterventoSimpleBean> res = new ArrayList<InterventoSimpleBean>();
	try {
	    testoDaCercare = URLDecoder.decode(testoDaCercare, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	}
	for (InterventoSimpleBean isb : b) {
	    // FILTRA
	    if (filtra(isb, tipoRicerca, campiRicerca, testoDaCercare)) {
		res.add(isb);
	    }
	}
	model.put("items", res);
	return new ModelAndView("jsonView", model);
    }

    private boolean filtra(InterventoSimpleBean isb, String tipoRicerca, String campiRicerca, String testoDaCercare) {

	if (tipoRicerca.equalsIgnoreCase("parolaEsatta")) {
	    String[] aTestoDaCercare = testoDaCercare.split(" ");
	    for (String t : aTestoDaCercare) {
		Pattern patt = Pattern.compile("(^|\\W)" + t.trim() + "(\\W|$)", Pattern.CASE_INSENSITIVE);
		String text = StringUtils.defaultString(isb.getText());
		String note = StringUtils.defaultString(isb.getNote());
		if (campiRicerca.equals("titoliDescrizioni")) {
		    if (patt.matcher(text).find() || patt.matcher(note).find()) {
			return true;
		    }
		} else {
		    if (patt.matcher(text).find()) {
			return true;
		    }
		}
	    }
	    return false;
	}
	return true;
    }

    private ModelAndView operazioniSuProcedimenti(String[] params, HttpServletRequest request) {

	int l = params.length;
	String azione = params[l - 1];
	if (azione.equals("top")) {
	    return procedimentitop(); // OK MODIFICATA LA RICERCA
	} else {
	    for (String p : params) {
		if (p.equals("gerarchia")) {
		    // gerarchia
		    return gerarchianodiprocedimento(azione); // OK MODIFICATA LA RICERCA
		} else if (p.equals("gerarchiadettaglio")) {
		    // gerarchiadettaglio
		    return gerarchianodiprocedimentodettaglio(azione); // OK MODIFICATA LA RICERCA
		} else if (p.equals("sottonodi")) {
		    // sottonodi
		    return listasottonodiprocedimento(azione); // OK MODIFICATA RICERCA
		} else if (p.equals("cerca")) {
		    // sottonodi
		    return ricercaprocedimento(azione, request);// OK MODIFICATA RICERCA
		}
	    }
	    Integer codiceProcedimento = Integer.valueOf(azione);
	    return dettagliprocedimento(codiceProcedimento);
	}
    }

    private ModelAndView operazioniSuModulistica(String[] params, HttpServletRequest request) {

	//PATH_BASE/modulistica/:idcomune/:software/
	//PATH_BASE/procedimenti/:idcomune/:software/categoria/:idcategoria
	// Verifico se è presente nell'url  il parametro categoria, se si il valore
	// successivo sarà il codice della categoria
	Integer i = ArrayUtils.indexOf(params, "categoria");
	String codicecategoria = "";
	if (i != -1 && params[i + 1] != null) {
	    codicecategoria = params[i + 1];
	}
	return dettagliModulistica(params[2], codicecategoria, request);
    }

    /**
     * codiceTipoMod : sarà un parametro non obbligatorio, se presente cercherò la modlustica di una specifica
     * categoria, altrimenti tutte
     * 
     * @param codiceSoftware_
     * @param codiceTipoMod
     * @return
     */
    private ModelAndView dettagliModulistica(@RequestParam("codiceS") String codiceSoftware_,
	    @RequestParam(required = false, value = "codiceTipoMod") String codiceTipoMod, HttpServletRequest request) {

	Software software = softwareService.findById(codiceSoftware_);
	SoftwareBean b = new SoftwareBean();
	b.setCodice(software.getCodice());
	b.setTipo(software.getDescrizione());
	Map model = new HashMap();
	// Popolo il sofware scelto
	String codiceTipo = "";
	if (codiceTipoMod.indexOf("___") > 0) {
	    codiceTipo = codiceTipoMod.substring((codiceTipoMod.indexOf("___") + 3));
	}
	List<TipoModulisticaBean> categorie = new ArrayList<TipoModulisticaBean>();
	if (StringUtils.defaultIfEmpty(codiceTipoMod, ORMHelper.getIdcomuneAlias()).startsWith(ORMHelper.getIdcomuneAlias())) {
	    categorie = findCategorieModulistica(codiceSoftware_, codiceTipo);
	}
	try {
	    List<TipoModulisticaBean> categorie2 = findModulisticaCondivisa(codiceSoftware_, codiceTipoMod, request);
	    if (categorie2 != null && categorie2.size() > 0) {
		categorie.addAll(categorie2);
	    }
	} catch (Exception e) {
	    log.error("{}", e);
	}
	if (!categorie.isEmpty()) {
	    for (TipoModulisticaBean tmb : categorie) {
		List<ModulisticaBean> l = tmb.getModulistica();
		if (l != null && l.size() > 0) {
		    Collections.sort(tmb.getModulistica(), new ModulisticaBeanComparator());
		}
	    }
	    Collections.sort(categorie, new TipoModulisticaBeanComparator());
	    b.setCategorie(categorie);
	    model.put("items", b);
	}
	return new ModelAndView("jsonView", model);
    }

    private List<TipoModulisticaBean> findCategorieModulistica(String codiceSoftware_, String codiceTipoMod) {

	// List<TipoModulisticaBean> categorie = new ArrayList<TipoModulisticaBean>();
	// Verifico se ho passato una categoria specidica
	List<Tipimodelli> tipimodellis = new ArrayList<Tipimodelli>();
	if (StringUtils.isBlank(codiceTipoMod)) {
	    log.debug("dettagliModulistica# Categoria non psecificata, cerco la modulistica su tutte");
	    // Recupero i tipi di modelli associati ai modelli per il software passato
	    tipimodellis = tipimodelliService.findBySoftwareAndModulo(codiceSoftware_);
	    // Questo mi permette di fare la ricerca per tipo modello uguale a null
	    // ritorna tutti i modelli che non hanno settata una tipologia
	    tipimodellis.add(new Tipimodelli());
	} else {
	    log.debug("dettagliModulistica# Specificata la categoria con codice {}", codiceTipoMod);
	    Tipimodelli tipimodelli = null;
	    try {
		tipimodelli = tipimodelliService.findById(new PkId(Integer.parseInt(codiceTipoMod)));
	    } catch (NumberFormatException nfe) {
		log.error("dettagliModulistica# Il codice della categora non è numerico : {}", nfe.getMessage());
		new RuntimeException("Il codice della categora non è numerico :" + nfe.getMessage());
	    }
	    tipimodellis.add(tipimodelli);
	}
	List<TipoModulisticaBean> categorie = new ArrayList<TipoModulisticaBean>();
	TipoModulisticaBean cat = null;
	// Per ogni tipo modello recuper tutti i modelli
	for (Tipimodelli tipimodelli : tipimodellis) {
	    // popolo l'oggetto TipoModulisticaBean con i valori del tipo modello
	    if (tipimodelli != null) {
		cat = new TipoModulisticaBean();
		cat.setCodice(
			ORMHelper.getIdcomuneAlias() + "___" + (tipimodelli.getId().getCodice() == null ? "" : tipimodelli.getId().getCodice()));
		cat.setNome(tipimodelli.getDescrizione());
		ModulisticaBean modulisticaBean = null;
		List<ModulisticaBean> modulisticaBeans = new ArrayList<ModulisticaBean>();
		List<Modelli> listModelli = new ArrayList<Modelli>();
		// Recupero i modelli per tipo modello
		if (EntityUtils.getNestedProperty(tipimodelli, "id.codice") != null) {
		    listModelli = modelliService.findBySoftwareAndTipoModello(codiceSoftware_, tipimodelli.getId().getCodice());
		} else {
		    listModelli = modelliService.findBySoftwareAndTipoModello(codiceSoftware_, null);
		}
		// popolo l'oggetto ModulisticaBean con i valori di Modelli
		for (Modelli modelli : listModelli) {
		    if (BooleanUtils.isTrue(modelli.getFlagPubblica())) {
			modulisticaBean = new ModulisticaBean();
			modulisticaBean.setCodice(ORMHelper.getIdcomuneAlias() + "___" + modelli.getId().getCodice());
			modulisticaBean.setDescrizione(modelli.getDescrizione());
			modulisticaBean.setTitolo(modelli.getTitolo());
			if (EntityUtils.getNestedProperty(modelli.getOggetti(), "id.codice") != null) {
			    String UID = oggettiService.insertOrGetUID(modelli.getOggetti().getId().getCodice());
			    modulisticaBean.setFileId(ORMHelper.getIdcomuneAlias() + "___" + UID);
			}
			modulisticaBean.setLink(modelli.getIndirizzoweb());
			modulisticaBean.setOrdine((modelli.getOrdine() == null ? 0 : modelli.getOrdine().intValue()));
			modulisticaBeans.add(modulisticaBean);
		    }
		}
		cat.setModulistica(modulisticaBeans);
		if (cat.getModulistica() != null && cat.getModulistica().size() > 0) {
		    categorie.add(cat);
		}
	    }
	}
	return categorie;
    }

    private ModelAndView ricercaprocedimento(String testoDaCercare, HttpServletRequest request) {

	String tipoRicerca = getTipoRicerca(request);
	String campiRicerca = getCampiRicerca(request);
	Map model = new HashMap();
	List<ProcedimentoSimpleBean> b = inventarioprocedimentiService.findProcedimentiByDescrizione(testoDaCercare, tipoRicerca, campiRicerca,
		FlagPubblicaEnum.DA_PUBBLICARE, 0, 100);
	model.put("items", b);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView operazioniSuNormativa(String[] params) {

	int l = params.length;
	String azione = params[l - 1];
	if (azione.equals("top")) {
	    return normativatop();
	} else {
	    return normativaall();
	}
    }

    private ModelAndView operazioniSuNews(String[] params, HttpServletRequest request) {

	int l = params.length;
	String azione = params[l - 1];
	if (azione.equals("top")) {
	    return newstop(request);
	} else if (azione.equals("primopiano")) {
	    return newsprimopiano(request);
	} else {
	    if (params.length < 4) {
		// 	all o quella con id
		return newsall(request);
	    } else {
		return news(azione, request);
	    }
	}
    }

    private ModelAndView operazioniSuOrarieContatti(String[] params) {

	return orariecontatti();
    }

    private ModelAndView operazioniSuInfosportello(String[] params) {

	return infosportello();
    }

    private ModelAndView operazioniSuFAQ(String[] params, HttpServletRequest request, HttpServletResponse response) {

	int l = params.length;
	String azione = params[l - 1];
	if (azione.equals("top")) {
	    return faqtop(request, response);
	} else {
	    return faqall(request, response);
	}
    }

    private ModelAndView operazioniSuFAQPerModulo(String[] params, HttpServletRequest request, HttpServletResponse response) {

	return faqPerModulo(request, response);
    }

    private ModelAndView operazioniSuFAQPerCategoria(String[] params, HttpServletRequest request, HttpServletResponse response) {

	return faqPerCategoria(request, response);
    }

    protected String[] getParams(HttpServletRequest request) {

	String uri = request.getRequestURI();
	String context = request.getContextPath();
	String action = uriMapping;
	String params = uri.replaceFirst(context + action, "");
	return params.split("/");
    }

    private static enum PATH_BASE {
	download,
	faq,
	faqpermodulo,
	faqpercategoria,
	infosportello,
	interventi,
	news,
	normativa,
	orariecontatti,
	procedimenti,
	modulistica
    };

    protected void setHeaders(HttpServletResponse response) {

	response.addHeader("Access-Control-Allow-Origin", "*");
	response.addHeader("Access-Control-Allow-Headers", "Content-Type, authorization");
    }

    private PATH_BASE fromString(String azione) {

	return PATH_BASE.faq.valueOf(azione);
    }

    private final String uriMapping = "/public_json/";

    private FaqBean fromFaq(Faq faq) {

	FaqBean nb = new FaqBean();
	nb.setId(faq.getId().getCodice());
	nb.setTitolo(StringUtils.defaultString(faq.getDomanda(), ""));
	nb.setDescrizione(StringUtils.defaultString(faq.getRisposta(), ""));
	if (faq.getFaqclassi() != null) {
	    Faqclassi f = faqclassiService.findById(new PkId(faq.getFaqclassi().getId().getCodice()));
	    nb.setDescrizioneCategoria(f.getFaqclasse());
	}
	nb.setOrdine(faq.getOrdine());
	return nb;
    }

    // FAQ
    private ModelAndView faqall(HttpServletRequest request, HttpServletResponse response) {

	Map model = new HashMap();
	List<Faq> r = faqService.findAllPubblicate(null, null);
	List<FaqBean> list = new ArrayList<FaqBean>();
	for (Faq n : r) {
	    list.add(fromFaq(n));
	}
	List<FaqBean> list2 = findFaqCondivise(request);
	if (list2 != null & !list2.isEmpty()) {
	    list.addAll(list2);
	}
	Collections.sort(list, new FaqBeanComparator());
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView faqtop(HttpServletRequest request, HttpServletResponse response) {

	Map model = new HashMap();
	List<Faq> r = faqService.findAllPubblicate(0, MAX_RESULT_TOP_FAQ);
	List<FaqBean> list = new ArrayList<FaqBean>();
	for (Faq n : r) {
	    list.add(fromFaq(n));
	}
	List<FaqBean> list2 = findFaqCondivise(request);
	if (list2 != null & !list2.isEmpty()) {
	    list.addAll(list2);
	}
	Collections.sort(list, new FaqBeanComparator());
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    //FAQPERMODULO
    private ModelAndView faqPerModulo(HttpServletRequest request, HttpServletResponse response) {

	Map model = new HashMap();
	// Recupero la lista dei software attivi escluso TT
	List<Software> listSoftware = softwareService.findAttiviAndExcludeTT(false);
	// Recupero il software TT 
	Software softwareTT = softwareService.findById(WebConstants.SOFTWARE_TT);
	// aggiungo il software TT alla lista
	listSoftware.add(0, softwareTT);
	List<FaqPerModuloBean> list = new ArrayList<FaqPerModuloBean>();
	// Ciclo la lista dei software
	for (Software software : listSoftware) {
	    List<String> codicisoftwares = new ArrayList<String>();
	    codicisoftwares.add(software.getCodice());
	    // recupero le Faq per il software passato.
	    List<Faq> r = faqService.findByFilter(codicisoftwares, null, null, Boolean.TRUE);
	    FaqPerModuloBean nb = null;
	    // Se per il software esistono faq popolo l'elemento FaqPerModuloBean
	    if (!r.isEmpty()) {
		nb = new FaqPerModuloBean();
		nb.setId(software.getCodice());
		nb.setDescrizione(software.getDescrizione());
		for (Faq n : r) {
		    nb.getFaq().add(fromFaq(n));
		}
		list.add(nb);
	    }
	}
	List<FaqPerModuloBean> list2 = (List<FaqPerModuloBean>) findFaqCondivise(request, FaqPerModuloBean.class);
	if (list2 != null & !list2.isEmpty()) {
	    list.addAll(list2);
	}
	List<FaqPerModuloBean> result = unisciEordina(list);
	model.put("items", result);
	return new ModelAndView("jsonView", model);
    }

    private List<FaqPerModuloBean> unisciEordina(List<FaqPerModuloBean> list) {

	if (list.isEmpty()) {
	    return list;
	}
	List<FaqPerModuloBean> result = new ArrayList<FaqPerModuloBean>();
	Map<String, FaqPerModuloBean> m = new HashMap<String, FaqPerModuloBean>();
	for (FaqPerModuloBean fs : list) {
	    String key = fs.getId();
	    if (m.get(key) == null) {
		m.put(key, fs);
	    } else {
		FaqPerModuloBean fm = m.get(key);
		fm.getFaq().addAll(fs.getFaq());
		m.put(key, fm);
	    }
	}
	for (Map.Entry<String, FaqPerModuloBean> me : m.entrySet()) {
	    FaqPerModuloBean v = me.getValue();
	    List<FaqBean> fqs = v.getFaq();
	    if (fqs != null && !fqs.isEmpty()) {
		Collections.sort(fqs, new FaqBeanComparator());
		v.setFaq(fqs);
	    }
	    result.add(v);
	}
	Collections.sort(result, new FaqPerModuloBeanComparator());
	return result;
    }

    private ModelAndView faqPerCategoria(HttpServletRequest request, HttpServletResponse response) {

	Map model = new HashMap();
	// Recupero tutte le categorie (FaqClassi) presenti per il software passato e TT
	List<Faqclassi> faqclassis = faqclassiService.findBySoftwareAndFaq(ORMHelper.getSoftware(), true);
	List<FaqPerCategoriaBean> list = new ArrayList<FaqPerCategoriaBean>();
	FaqPerCategoriaBean categoriaBean = null;
	for (Faqclassi faqclassi : faqclassis) {
	    categoriaBean = new FaqPerCategoriaBean();
	    categoriaBean.setId(faqclassi.getId().getCodice().toString());
	    categoriaBean.setDescrizione(faqclassi.getFaqclasse());
	    List<Faq> listfaq = faqService.findByFaqClassi(faqclassi.getId().getCodice(), true, ORMHelper.getSoftware(), true);
	    List<FaqBean> faqBeans = new ArrayList<FaqBean>();
	    for (Faq faq : listfaq) {
		faqBeans.add(fromFaq(faq));
	    }
	    categoriaBean.setFaq(faqBeans);
	    if (!listfaq.isEmpty()) {
		list.add(categoriaBean);
	    }
	}
	// Controllo se esistono Faq che non sono caretterizzate (Faqclassi==null)
	List<Faq> listfaqWithoutFaqClassi = faqService.findWithoutFaqClassi(true, ORMHelper.getSoftware(), true);
	FaqPerCategoriaBean categoriaBeanGenerale = null;
	if (!listfaqWithoutFaqClassi.isEmpty()) {
	    categoriaBeanGenerale = new FaqPerCategoriaBean();
	    categoriaBeanGenerale.setId("0");
	    categoriaBeanGenerale.setDescrizione("CATEGORIA GENERALE");
	    List<FaqBean> faqBeanswithoutClass = new ArrayList<FaqBean>();
	    FaqBean faqBean = null;
	    for (Faq faq : listfaqWithoutFaqClassi) {
		faqBean = new FaqBean();
		faqBean.setId(faq.getId().getCodice());
		faqBean.setTitolo(StringUtils.defaultString(faq.getDomanda(), ""));
		faqBean.setDescrizione(StringUtils.defaultString(faq.getRisposta(), ""));
		faqBean.setOrdine(faq.getOrdine());
		faqBeanswithoutClass.add(faqBean);
		categoriaBeanGenerale.setFaq(faqBeanswithoutClass);
	    }
	    list.add(0, categoriaBeanGenerale);
	}
	List<FaqPerCategoriaBean> list2 = (List<FaqPerCategoriaBean>) findFaqCondivise(request, FaqPerCategoriaBean.class);
	if (list2 != null & !list2.isEmpty()) {
	    list.addAll(list2);
	}
	List<FaqPerCategoriaBean> result = unisciEordinaFPQ(list);
	model.put("items", result);
	return new ModelAndView("jsonView", model);
    }

    private List<FaqPerCategoriaBean> unisciEordinaFPQ(List<FaqPerCategoriaBean> list) {

	if (list.isEmpty()) {
	    return list;
	}
	List<FaqPerCategoriaBean> result = new ArrayList<FaqPerCategoriaBean>();
	Map<String, FaqPerCategoriaBean> m = new HashMap<String, FaqPerCategoriaBean>();
	for (FaqPerCategoriaBean fs : list) {
	    String key = fs.getId();
	    if (m.get(key) == null) {
		m.put(key, fs);
	    } else {
		FaqPerCategoriaBean fm = m.get(key);
		fm.getFaq().addAll(fs.getFaq());
		m.put(key, fm);
	    }
	}
	for (Map.Entry<String, FaqPerCategoriaBean> me : m.entrySet()) {
	    FaqPerCategoriaBean v = me.getValue();
	    List<FaqBean> fqs = v.getFaq();
	    if (fqs != null && !fqs.isEmpty()) {
		Collections.sort(fqs, new FaqBeanComparator());
		v.setFaq(fqs);
	    }
	    result.add(v);
	}
	Collections.sort(result, new FaqPerCategoriaBeanComparator());
	return result;
    }

    // INFO SPORTELLO
    private ModelAndView infosportello() {

	Map model = new HashMap();
	List<Infosuap> r = infosuapService.findAll(null, null);
	List<InfoSportelloBean> list = new ArrayList<InfoSportelloBean>();
	for (Infosuap n : r) {
	    InfoSportelloBean nb = new InfoSportelloBean();
	    nb.setId(n.getId().getCodice());
	    nb.setTitolo(n.getTitolo());
	    nb.setDescrizione(n.getDescrizione());
	    list.add(nb);
	}
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    // NEWS
    private ModelAndView newstop(HttpServletRequest request) {

	Map model = new HashMap();
	List<News> r = newsService.findLatest(MAX_RESULT_TOP);
	List<NewsBean> list = new ArrayList<NewsBean>();
	for (News n : r) {
	    NewsBean nb = new NewsBean();
	    nb.setId(String.valueOf(n.getId().getCodice()));
	    nb.setTitolo(n.getTitolo());
	    nb.setSottotitolo(n.getSommario());
	    nb.setCorpo(n.getNews());
	    if (n.getData() != null) {
		String data = Utilities.formatDate(n.getData(), false);
		nb.setData(data);
	    }
	    if (n.getOggettiByFkNews1Oggetti() != null) {
		Integer codiceOggetto = n.getOggettiByFkNews1Oggetti().getId().getCodice();
		if (codiceOggetto != null) {
		    String uid = oggettiService.insertOrGetUID(codiceOggetto);
		    nb.setCodiceoggetto(uid);
		}
	    }
	    list.add(nb);
	}
	List<NewsBean> nb2s = newsCondivise(request, NewsBean.class);
	if (nb2s != null && nb2s.size() > 0) {
	    list.addAll(nb2s);
	    unisciEordinaNews(list);
	}
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    // NEWS
    private ModelAndView newsprimopiano(HttpServletRequest request) {

	Map model = new HashMap();
	List<News> r = newsService.findLatestPrimoPiano(MAX_RESULT_TOP_PRIMOPIANO);
	List<NewsBean> list = new ArrayList<NewsBean>();
	for (News n : r) {
	    NewsBean nb = new NewsBean();
	    nb.setId(String.valueOf(n.getId().getCodice()));
	    nb.setTitolo(n.getTitolo());
	    nb.setSottotitolo(n.getSommario());
	    nb.setCorpo(n.getNews());
	    if (n.getData() != null) {
		String data = Utilities.formatDate(n.getData(), false);
		nb.setData(data);
	    }
	    if (n.getOggettiByFkNews1Oggetti() != null) {
		Integer codiceOggetto = n.getOggettiByFkNews1Oggetti().getId().getCodice();
		if (codiceOggetto != null) {
		    String uid = oggettiService.insertOrGetUID(codiceOggetto);
		    nb.setCodiceoggetto(uid);
		}
	    }
	    list.add(nb);
	}
	List<NewsBean> nb2s = newsCondivise(request, NewsBean.class);
	if (nb2s != null && nb2s.size() > 0) {
	    list.addAll(nb2s);
	    unisciEordinaNews(list);
	}
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    private List<NewsBean> unisciEordinaNews(List<NewsBean> list) {

	if (list.isEmpty()) {
	    return list;
	}
	Collections.sort(list, new NewsBeanComparator());
	return list;
    }

    private ModelAndView newsall(HttpServletRequest request) {

	Map model = new HashMap();
	List<News> r = newsService.findLatest(100);
	List<NewsBean> list = new ArrayList<NewsBean>();
	for (News n : r) {
	    NewsBean nb = new NewsBean();
	    nb.setId(String.valueOf(n.getId().getCodice()));
	    nb.setTitolo(n.getTitolo());
	    nb.setSottotitolo(n.getSommario());
	    nb.setCorpo(n.getNews());
	    if (n.getData() != null) {
		String data = Utilities.formatDate(n.getData(), false);
		nb.setData(data);
	    }
	    if (n.getOggettiByFkNews1Oggetti() != null) {
		Integer codiceOggetto = n.getOggettiByFkNews1Oggetti().getId().getCodice();
		if (codiceOggetto != null) {
		    String uid = oggettiService.insertOrGetUID(codiceOggetto);
		    nb.setCodiceoggetto(uid);
		}
	    }
	    list.add(nb);
	}
	List<NewsBean> nb2s = newsCondivise(request, NewsBean.class);
	if (nb2s != null && nb2s.size() > 0) {
	    list.addAll(nb2s);
	    unisciEordinaNews(list);
	}
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView news(String codiceNews, HttpServletRequest request) {

	Map model = new HashMap();
	NewsBean nb = null;
	if (codiceNews.startsWith(COND)) {
	    nb = dettaglioNews(codiceNews, request);
	} else {
	    News r = newsService.findById(new PkId(Integer.valueOf(codiceNews)));
	    nb = new NewsBean();
	    nb.setId(String.valueOf(r.getId().getCodice()));
	    nb.setTitolo(r.getTitolo());
	    nb.setSottotitolo(r.getSommario());
	    nb.setCorpo(r.getNews());
	    if (r.getData() != null) {
		String data = Utilities.formatDate(r.getData(), false);
		nb.setData(data);
	    }
	    if (r.getOggettiByFkNews1Oggetti() != null) {
		Integer codiceOggetto = r.getOggettiByFkNews1Oggetti().getId().getCodice();
		if (codiceOggetto != null) {
		    String uid = oggettiService.insertOrGetUID(codiceOggetto);
		    nb.setCodiceoggetto(uid);
		}
	    }
	}
	model.put("items", nb);
	return new ModelAndView("jsonView", model);
    }

    private NewsBean dettaglioNews(String codiceNews, HttpServletRequest request) {

	String pathinfo = request.getPathInfo();
	// "/news/E256/SS"
	String url = checkUrlServiziCondivisi();
	if (StringUtils.isNotBlank(url)) {
	    String[] aliasUrl = url.split("#");
	    String alias = aliasUrl[0];
	    String urlss = aliasUrl[1];
	    String urloToCall = urlss + pathinfo.replaceAll(ORMHelper.getIdcomuneAlias(), alias).replaceAll(COND, "");
	    try {
		JsonParser p = new JsonParser();
		String json = call(urloToCall);
		Map jsonObjectM = (Map) p.parse(json);
		JSONObject jsonObject = JSONObject.fromObject(jsonObjectM);
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("items", NewsBean.class);
		NewsBean tmb = (NewsBean) JSONObject.toBean(jsonObject.getJSONObject("items"), NewsBean.class, classMap);
		if (tmb != null) {
		    tmb.setId(COND + tmb.getId());
		}
		return tmb;
	    } catch (Exception e) {
		log.error("Errore nel recupero delle fa da servizio esterno {}: {}", urloToCall, e);
	    }
	}
	return new NewsBean();
    }

    // NORMATIVA
    private ModelAndView normativatop() {

	Map model = new HashMap();
	List<NormegeneraliBean> list = normegeneraliService.findNormegeneraliBySoftware(0, MAX_RESULT_TOP, true);
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView normativaall() {

	Map model = new HashMap();
	List<NormegeneraliBean> list = normegeneraliService.findNormegeneraliBySoftware(null, null, true);
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    // ORARI E CONTATTI
    private ModelAndView orariecontatti() {

	Map model = new HashMap();
	OrariEContattiBean list = configurazioneService.getOrariEContatti();
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    // INTERVENTI
    private ModelAndView dettagliointervento(@RequestParam("codiceIntervento") Integer codiceIntervento, HttpServletRequest request) {

	Map model = new HashMap();
	InterventoBean list = alberoprocService.findInterventoBean(codiceIntervento);
	// Controllo se l'intervento è del CART, popolo il campo "schedaRegionale"
	StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.isIntervetoCART(codiceIntervento);
	if (stpEndoTipo2 != null) {
	    String html = getSchedaRegionale(stpEndoTipo2.getCodiceStp(), TipoSchedaEndo.SCHEDA_TIPO_ENDO2);
	    list.setSchedaRegionale(html);
	}
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    private String getSchedaRegionale(Integer codice, TipoSchedaEndo tipoSchedaEndo) {

	GetMethod method = null;
	String html = "";
	try {
	    HttpClient client = new HttpClient();
	    String urlSchedaSpiegazioneEndo = "";
	    // Scelgo se la richiesta è fatta per la scheda di spiegazione 1 o 2
	    switch (tipoSchedaEndo) {
	    case SCHEDA_TIPO_ENDO1:
		log.debug("Recupero la scheda di spiegazione per endo di tipo 1 (Intervento)");
		urlSchedaSpiegazioneEndo = "stp/ajaxSchedaSpiegazioneEndo1.htm";
		break;
	    case SCHEDA_TIPO_ENDO2:
		log.debug("Recupero la scheda di spiegazione per endo di tipo 2 (Procedimento)");
		urlSchedaSpiegazioneEndo = "stp/ajaxSchedaSpiegazioneEndo2.htm";
		break;
	    default:
		throw new RuntimeException("Tipo scheda endo non scelta");
	    }
	    // Recupera un token applicativo per l'alias presente sull' ORMHelper
	    String token = getToken();
	    // Costruisco la stringa per la chiamata con HttpClient
	    String httpClientMethodCall = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_JAVA) +
		    "/" +
		    urlSchedaSpiegazioneEndo +
		    "?codice=" +
		    codice +
		    "&" +
		    WebConstants.TOKEN +
		    "=" +
		    token +
		    "&" +
		    WebConstants.SOFTWARE +
		    "=" +
		    ORMHelper.getSoftware();
	    log.debug("URL : {}", httpClientMethodCall);
	    method = new GetMethod(httpClientMethodCall);
	    // method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
	    // Execute the method.
	    int statusCode = client.executeMethod(method);
	    if (statusCode != HttpStatus.SC_OK) {
		log.error("Method failed: {}", method.getStatusLine());
	    }
	    // Read the response body.
	    InputStream responseBody = method.getResponseBodyAsStream();
	    html = convertStreamToString(responseBody);
	} catch (HttpException e) {
	    log.error("Fatal protocol violation: {}", e.getMessage());
	    throw new RuntimeException(e.getMessage());
	} catch (IOException e) {
	    log.error("Fatal transport error: {}", e.getMessage());
	    throw new RuntimeException(e.getMessage());
	} finally {
	    // Release the connection.
	    method.releaseConnection();
	}
	return html;
    }

    private String convertStreamToString(InputStream is) throws IOException {

	/*
	 * To convert the InputStream to String we use the BufferedReader.readLine() method. We iterate until the
	 * BufferedReader return null which means there's no more data to read. Each line will appended to a
	 * StringBuilder and returned as String.
	 */
	if (is != null) {
	    StringBuilder sb = new StringBuilder();
	    String line;
	    try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		while ((line = reader.readLine()) != null) {
		    sb.append(line).append("\n");
		}
	    } finally {
		is.close();
	    }
	    return sb.toString();
	} else {
	    return "";
	}
    }

    private String getToken() {

	LoginResponse loginResponse = null;
	String token = "";
	try {
	    LoginRequest loginRequest = new LoginRequest();
	    loginRequest.setAlias(ORMHelper.getIdcomuneAlias());
	    loginRequest.setContesto(ContestoType.APP);
	    loginRequest.setIpAddress("");
	    loginRequest.setPassword("");
	    loginRequest.setUsername("");
	    loginResponse = securityWSClient.getWsPort().login(loginRequest);
	    token = loginResponse.getToken();
	} catch (Exception e) {
	    log.error("Non è stato possibile recuperare un token : {}", e.getMessage());
	}
	return token;
    }

    private ModelAndView gerarchianodiinterventi(@RequestParam("codiceIntervento") Integer codiceIntervento) {

	Map model = new HashMap();
	List<Integer> b = alberoprocService.findGerarchiaNodiPadre(codiceIntervento, false);
	model.put("items", b);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView gerarchianodiinterventidettaglio(Integer codiceIntervento) {

	Map model = new HashMap();
	List<InterventoSimpleBean> b = alberoprocService.findGerarchiaDettaglioNodiPadre(codiceIntervento, false);
	model.put("items", b);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView listasottonodiintervento(@RequestParam("codiceElemento") Integer codiceIntervento) {

	Map model = new HashMap();
	List<InterventoSimpleBean> b = alberoprocService.findListaInterventiSottonodiDi(codiceIntervento);
	model.put("items", b);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView interventitop() {

	Map model = new HashMap();
	List<IdentificativoDescrizioneBean> list = alberoprocFoTopService.findInterventi(null, null);
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    // PROCEDIMENTI
    private ModelAndView procedimentitop() {

	Map model = new HashMap();
	List<IdentificativoDescrizioneBean> list = inventarioprocFoTopService.findProcedimenti(null, null);
	model.put("items", list);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView dettagliprocedimento(@RequestParam("codiceProcedimento") Integer codiceProcedimento) {

	Map model = new HashMap();
	Inventarioprocedimenti invProc = inventarioprocedimentiService.findById(new PkId(codiceProcedimento));
	ProcedimentoBean b = new ProcedimentoBean();
	if (BooleanUtils.toBoolean(invProc.getFlagPubblica())) {
	    b = inventarioprocedimentiService.findProcedimentoBean(codiceProcedimento);
	    // Controllo se il procedimento è del CART, popolo il campo "schedaRegionale"
	    StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.isProcedimentoCART(codiceProcedimento);
	    if (stpEndoTipo1 != null) {
		String html = getSchedaRegionale(stpEndoTipo1.getCodiceStp(), TipoSchedaEndo.SCHEDA_TIPO_ENDO1);
		b.setSchedaRegionale(html);
	    }
	}
	model.put("items", b);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView gerarchianodiprocedimento(@RequestParam("codiceElemento") String codiceElemento) {

	Map model = new HashMap();
	List<String> b = inventarioprocedimentiService.findGerarchiaNodiPadre(codiceElemento);
	model.put("items", b);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView gerarchianodiprocedimentodettaglio(@RequestParam("codiceElemento") String codiceElemento) {

	Map model = new HashMap();
	List<ProcedimentoSimpleBean> b = inventarioprocedimentiService.findGerarchiaNodiPadreDettaglio(codiceElemento);
	model.put("items", b);
	return new ModelAndView("jsonView", model);
    }

    private ModelAndView listasottonodiprocedimento(@RequestParam("codiceElemento") String codiceElemento) {

	Map model = new HashMap();
	List<ProcedimentoSimpleBean> b = inventarioprocedimentiService.findListaSottonodiDi(codiceElemento, FlagPubblicaEnum.DA_PUBBLICARE);
	model.put("items", b);
	return new ModelAndView("jsonView", model);
    }

    private String checkUrlServiziCondivisi() {

	if (!this.verticalizzazioneAreaRiservataService.isAttiva()) {
	    return null;
	}
	String retval = this.verticalizzazioneAreaRiservataService.getUrlJsonServiziCondivisi();
	if (StringUtils.isBlank(retval)) {
	    return null;
	}
	return retval.trim();
    }

    private List<FaqBean> findFaqCondivise(HttpServletRequest request) {

	List<FaqBean> categorie = new ArrayList<FaqBean>();
	String pathinfo = request.getPathInfo();
	// "/modulistica/E256/SS"
	String url = checkUrlServiziCondivisi();
	if (StringUtils.isNotBlank(url)) {
	    String[] aliasUrl = url.split("#");
	    String alias = aliasUrl[0];
	    String urlss = aliasUrl[1];
	    JsonParser p = new JsonParser();
	    String urloToCall = urlss + pathinfo.replaceAll(ORMHelper.getIdcomuneAlias(), alias);
	    String json = call(urloToCall);
	    Map jsonObjectM = (Map) p.parse(json);
	    JSONObject jsonObject = JSONObject.fromObject(jsonObjectM);
	    if (null != jsonObject && !jsonObject.isNullObject()) {
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("items", FaqBean.class);
		categorie = (List<FaqBean>) JSONArray.toList(jsonObject.getJSONArray("items"), FaqBean.class, classMap);
	    }
	}
	return categorie;
    }

    private List<?> findFaqCondivise(HttpServletRequest request, Class<?> clazz) {

	List categorie = new ArrayList();
	String pathinfo = request.getPathInfo();
	// "/modulistica/E256/SS"
	String url = checkUrlServiziCondivisi();
	if (StringUtils.isNotBlank(url)) {
	    String[] aliasUrl = url.split("#");
	    String alias = aliasUrl[0];
	    String urlss = aliasUrl[1];
	    String urloToCall = urlss + pathinfo.replaceAll(ORMHelper.getIdcomuneAlias(), alias);
	    try {
		JsonParser p = new JsonParser();
		String json = call(urloToCall);
		Map jsonObjectM = (Map) p.parse(json);
		JSONObject jsonObject = JSONObject.fromObject(jsonObjectM);
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("items", clazz);
		classMap.put("faq", FaqBean.class);
		categorie = (List) JSONArray.toList(jsonObject.getJSONArray("items"), clazz, classMap);
	    } catch (Exception e) {
		log.error("Errore nel recupero delle fa da servizio esterno {}: {}", urloToCall, e);
	    }
	}
	return categorie;
    }

    private List<TipoModulisticaBean> findModulisticaCondivisa(String codiceSoftware_, String codiceTipoMod, HttpServletRequest req) {

	List<TipoModulisticaBean> categorie = new ArrayList<TipoModulisticaBean>();
	String pathinfo = req.getPathInfo();
	// "/modulistica/E256/SS"
	String url = checkUrlServiziCondivisi();
	if (StringUtils.isNotBlank(url)) {
	    String[] aliasUrl = url.split("#");
	    String alias = aliasUrl[0];
	    String urlss = aliasUrl[1];
	    JsonParser p = new JsonParser();
	    String urloToCall = urlss + pathinfo.replaceAll(ORMHelper.getIdcomuneAlias(), alias);
	    String json = call(urloToCall);
	    Map jsonObjectM = (Map) p.parse(json);
	    JSONObject jsonObject = JSONObject.fromObject(jsonObjectM);
	    Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
	    classMap.put("items", SoftwareBean.class);
	    classMap.put("categorie", TipoModulisticaBean.class);
	    classMap.put("modulistica", ModulisticaBean.class);
	    classMap.put("downloads", DownloadBean.class);
	    SoftwareBean tmb = (SoftwareBean) JSONObject.toBean(jsonObject.getJSONObject("items"), SoftwareBean.class, classMap);
	    if (tmb != null) {
		if (tmb.getCategorie() != null && tmb.getCategorie().size() > 0) {
		    categorie.addAll(tmb.getCategorie());
		}
	    }
	}
	return categorie;
    }

    private List<NewsBean> newsCondivise(HttpServletRequest request, Class<?> clazz) {

	List<NewsBean> categorie = new ArrayList<NewsBean>();
	String pathinfo = request.getPathInfo();
	// "/news/E256/SS"
	String url = checkUrlServiziCondivisi();
	if (StringUtils.isNotBlank(url)) {
	    String[] aliasUrl = url.split("#");
	    String alias = aliasUrl[0];
	    String urlss = aliasUrl[1];
	    String urloToCall = urlss + pathinfo.replaceAll(ORMHelper.getIdcomuneAlias(), alias);
	    try {
		JsonParser p = new JsonParser();
		String json = call(urloToCall);
		if (json != null) {
		    Map jsonObjectM = (Map) p.parse(json);
		    JSONObject jsonObject = JSONObject.fromObject(jsonObjectM);
		    Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		    classMap.put("items", NewsBean.class);
		    categorie = (List<NewsBean>) JSONArray.toList(jsonObject.getJSONArray("items"), NewsBean.class, classMap);
		    for (NewsBean nb : categorie) {
			nb.setId(COND + nb.getId());
		    }
		}
	    } catch (Exception e) {
		log.error("Errore nel recupero delle fa da servizio esterno {}: {}", urloToCall, e);
	    }
	}
	return categorie;
    }

    private String call(String urlTo) {

	HttpClient cli = new HttpClient();
	HttpMethod method = new GetMethod(urlTo);
	method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
	try {
	    log.debug("prima di eseguire la chiamata all'url {}", urlTo);
	    int status = cli.executeMethod(method);
	    log.debug("la chiamata al 'url {} ha tornato status {}", urlTo, status);
	    if (status != 200) {
		log.error("errore nella chiamata all'url {}, stato http {} ", urlTo, status);
		return null;
	    }
	    String codice = new String(method.getResponseBody());
	    return codice;
	} catch (Exception e) {
	    log.error("errore nella chiamata all'url {}", urlTo, e);
	}
	return null;
    }
}
