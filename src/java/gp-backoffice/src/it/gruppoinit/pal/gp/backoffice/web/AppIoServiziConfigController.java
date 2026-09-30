package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AppIoCoda;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaId;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimenti;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaStati;
import it.gruppoinit.pal.gp.core.domain.AppIoParam;
import it.gruppoinit.pal.gp.core.domain.AppIoParamId;
import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfig;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigId;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParam;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParamId;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziId;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServizi;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServiziId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.AppIoCodaRestBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.AppIoParamRestResponse;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.AppIoServiziAmbitiEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.AppIoServiziConfigParamRestResponse;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.AppIoServiziConfigRestResponse;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.AppIoServiziConfigRestWrapperResponse;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.AppIoServiziRestResponse;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMovimentiService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaStatiService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoParamService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoServiziConfigParamService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoServiziConfigService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoServiziService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.ITipimovimentoAppIoserviziService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@Controller
@SessionAttributes(value = { "appioservizi", "tmovioservizi" })
public class AppIoServiziConfigController extends BaseJsonController<Object> {

    @Autowired
    private IAppIoServiziService appIoServiziService;
    @Autowired
    private IAppIoServiziConfigService appIoServiziConfigService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private IAppIoParamService appIoParamService;
    @Autowired
    private IAppIoServiziConfigParamService appIoServiziCongParamService;
    @Autowired
    private ITipimovimentoAppIoserviziService tipimovimentoAppIoserviziService;
    @Autowired
    private TipiMovimentoService tipimovimentoService;
    @Autowired
    private IAppIoCodaMovimentiService appIoCodaMovimentiService;
    @Autowired
    private IAppIoCodaService appIoCodaService;
    @Autowired
    private IAppIoCodaStatiService appIoCodaStatiService;

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

	List<AppIoServizi> ioServizi = appIoServiziService.findAll(null, null);
	model.addAttribute("ioServizi", ioServizi);
	return "appio/list";
    }

    @RequestMapping
    public String listservizitipimovimento(Model model, @RequestParam(required = false, value = "idservizio") String idservizio,
	    HttpServletRequest request, HttpServletResponse response) {

	List<TipimovimentoAppIoServizi> tipimovimentoAppIoServizi = tipimovimentoAppIoserviziService.findByIdservizio(idservizio);
	model.addAttribute("tipimovimentoAppIoServizi", tipimovimentoAppIoServizi);
	model.addAttribute("idservizio", idservizio);
	return "appio/list_form_servizi_tipimovimento";
    }

    @RequestMapping
    public String serviziente(Model model, @RequestParam(required = false) String idservizio, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	AppIoServizi appIoServizi = new AppIoServizi();
	AppIoServiziId id = new AppIoServiziId();
	id.setIdcomune(ORMHelper.getIdcomune());
	appIoServizi.setId(id);
	List<Comuniassociati> comuni = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	model.addAttribute("ambiti", AppIoServiziAmbitiEnum.values());
	model.addAttribute("comuni", comuni);
	model.addAttribute("appioservizi", appIoServizi);
	if (StringUtils.isNotEmpty(idservizio)) {
	    AppIoServiziId id1 = new AppIoServiziId();
	    id1.setIdcomune(ORMHelper.getIdcomune());
	    id1.setIdentificativoServizio(idservizio);
	    AppIoServizi appioServizi1 = appIoServiziService.findById(id1);
	    List<AppIoServiziConfig> appIoServiziConfig = appIoServiziConfigService.findByIdServizio(idservizio);
	    List<AppIoServiziConfigParam> params = appIoServiziCongParamService.findByIdServizio(idservizio);
	    model.addAttribute("appioservizi", appioServizi1);
	    model.addAttribute("appIoServiziConfig", appIoServiziConfig);
	    model.addAttribute("appIoServiziConfigParam", params);
	}
	return "appio/form_servizi_config";
    }

    @RequestMapping
    public String createservizitipimovimento(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	TipimovimentoAppIoServizi tmovioservizi = new TipimovimentoAppIoServizi();
	TipimovimentoAppIoServiziId id = new TipimovimentoAppIoServiziId(ORMHelper.getIdcomune(), null, idservizio);
	tmovioservizi.setId(id);
	AppIoServiziId idServizio = new AppIoServiziId();
	idServizio.setIdentificativoServizio(idservizio);
	tmovioservizi.setAppIoServizi(appIoServiziService.findById(idServizio));
	setTmovPagesAttribute(tmovioservizi, model, request, Boolean.TRUE);
	return "appio/form_servizi_tipimovimento";
    }

    private void setTmovPagesAttribute(TipimovimentoAppIoServizi tmovioservizi, Model model, HttpServletRequest request, Boolean isCreate) {

	model.addAttribute("tmovioservizi", tmovioservizi);
	model.addAttribute("create", isCreate);
	List<AppIoServizi> ioServizi = appIoServiziService.findAll(null, null);
	model.addAttribute("ioServizi", ioServizi);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String insertservizitipimovimento(Model model, @ModelAttribute("tmovioservizi") TipimovimentoAppIoServizi tmovioservizi,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	AppIoServiziId idServizio = new AppIoServiziId();
	idServizio.setIdentificativoServizio(tmovioservizi.getId().getIdentificativoServizio());
	tmovioservizi.setAppIoServizi(appIoServiziService.findById(idServizio));
	Tipimovimento tmov = tipimovimentoService.findById(new TipimovimentoId(tmovioservizi.getTipimovimento().getId().getTipomovimento()));
	tmovioservizi.getId().setTipomovimento(tmovioservizi.getTipimovimento().getId().getTipomovimento());
	tmovioservizi.setTipimovimento(tmov);
	try {
	    tipimovimentoAppIoserviziService.insert(tmovioservizi);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(tmovioservizi, false, "", e);
	    setTmovPagesAttribute(tmovioservizi, model, request, Boolean.TRUE);
	    return "appio/form_servizi_tipimovimento";
	}
	return "redirect:viewservizitipimovimento.htm?id.identificativoServizio=" +
		tmovioservizi.getId().getIdentificativoServizio() +
		"&id.tipomovimento=" +
		tmovioservizi.getId().getTipomovimento() +
		"&" +
		WebConstants.STATUS_MSG_PARAM_NAME +
		"=01";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateservizitipimovimento(Model model, @ModelAttribute("tmovioservizi") TipimovimentoAppIoServizi tmovioservizi,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	AppIoServiziId idServizio = new AppIoServiziId();
	idServizio.setIdentificativoServizio(tmovioservizi.getId().getIdentificativoServizio());
	tmovioservizi.setAppIoServizi(appIoServiziService.findById(idServizio));
	Tipimovimento tmov = tipimovimentoService.findById(new TipimovimentoId(tmovioservizi.getId().getTipomovimento()));
	tmovioservizi.setTipimovimento(tmov);
	try {
	    tipimovimentoAppIoserviziService.update(tmovioservizi);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(tmovioservizi, false, "", e);
	    setTmovPagesAttribute(tmovioservizi, model, request, Boolean.FALSE);
	    return "appio/form_servizi_tipimovimento";
	}
	return "redirect:viewservizitipimovimento.htm?id.identificativoServizio=" +
		tmovioservizi.getId().getIdentificativoServizio() +
		"&id.tipomovimento=" +
		tmovioservizi.getId().getTipomovimento() +
		"&" +
		WebConstants.STATUS_MSG_PARAM_NAME +
		"=01";
    }

    @RequestMapping
    public String viewservizitipimovimento(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    @RequestParam(value = "id.tipomovimento", required = true) String tipomovimento, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	TipimovimentoAppIoServiziId id = new TipimovimentoAppIoServiziId(ORMHelper.getIdcomune(), tipomovimento, idservizio);
	TipimovimentoAppIoServizi tmovioservizi = tipimovimentoAppIoserviziService.findById(id);
	model.addAttribute("tmovioservizi", tmovioservizi);
	List<AppIoServizi> ioServizi = appIoServiziService.findAll(null, null);
	model.addAttribute("ioServizi", ioServizi);
	return "appio/form_servizi_tipimovimento";
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonEliminaTipimovimentoServizio(Model model, @RequestParam("idservizio") String idServizio,
	    @RequestParam("tipomovimento") String tipoMovimento, HttpServletRequest request, HttpServletResponse response)
	    throws JAXBException, IOException {

	TipimovimentoAppIoServiziId id = new TipimovimentoAppIoServiziId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setIdentificativoServizio(idServizio);
	id.setTipomovimento(tipoMovimento);
	TipimovimentoAppIoServizi tipimovimentoAppIoServizi = new TipimovimentoAppIoServizi();
	tipimovimentoAppIoServizi.setId(id);
	tipimovimentoAppIoserviziService.delete(tipimovimentoAppIoServizi);
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonSalvaServizio(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	AppIoServiziRestResponse res = fromJson(request.getInputStream(), AppIoServiziRestResponse.class);
	AppIoServiziId id = new AppIoServiziId();
	id.setIdentificativoServizio(res.getIdentificativoServizio());
	id.setIdcomune(ORMHelper.getIdcomune());
	AppIoServizi appIoServizi = new AppIoServizi();
	appIoServizi.setId(id);
	appIoServizi.setDescrizione(res.getDescrizioneServizio());
	AppIoServizi appSer = appIoServiziService.findById(id);
	if (appSer != null && appSer.getId() != null && appSer.getId().getIdentificativoServizio() != null) {
	    response.setStatus(400);
	} else {
	    appIoServiziService.insert(appIoServizi);
	}
	model.addAttribute("appioservizi", appIoServizi);
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonSalvaServizioConfig(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	try {
	    AppIoServiziConfigRestWrapperResponse res = fromJson(request.getInputStream(), AppIoServiziConfigRestWrapperResponse.class);
	    for (AppIoServiziConfigRestResponse appIoServiziConfigRestResponse : res.getEntiServizi()) {
		AppIoServiziConfigId id = new AppIoServiziConfigId();
		id.setIdentificativoServizio(appIoServiziConfigRestResponse.getIdentificativoServizio());
		id.setIdcomune(ORMHelper.getIdcomune());
		id.setSoftware(ORMHelper.getSoftware());
		id.setCodiceComune(appIoServiziConfigRestResponse.getCodiceComune());
		AppIoServiziConfig appIoServiziConfig = new AppIoServiziConfig();
		appIoServiziConfig.setId(id);
		appIoServiziConfig.setAmbito(appIoServiziConfigRestResponse.getAmbito());
		appIoServiziConfig.setAttivo(appIoServiziConfigRestResponse.isAttivo());
		appIoServiziConfig.setMaxMessaggiGiorno(BigDecimal.valueOf(Integer.parseInt(appIoServiziConfigRestResponse.getMaxNumMessaggio())));
		appIoServiziConfig.setTemplateOggetto(appIoServiziConfigRestResponse.getOggettoMesaggio());
		appIoServiziConfig.setTemplateMessaggio(appIoServiziConfigRestResponse.getMessaggio());
		appIoServiziConfigService.insert(appIoServiziConfig, id, true);
		for (AppIoParamRestResponse param : appIoServiziConfigRestResponse.getParametri()) {
		    AppIoServiziConfigParamId id1 = new AppIoServiziConfigParamId();
		    id1.setCodiceComune(appIoServiziConfigRestResponse.getCodiceComune());
		    id1.setIdcomune(ORMHelper.getIdcomune());
		    id1.setIdentificativoServizio(appIoServiziConfigRestResponse.getIdentificativoServizio());
		    id1.setParametro(param.getParametro());
		    id1.setSoftware(ORMHelper.getSoftware());
		    AppIoServiziConfigParam par = new AppIoServiziConfigParam();
		    par.setId(id1);
		    par.setDescrizione(param.getDescrizione());
		    appIoServiziCongParamService.insert(par);
		}
	    }
	} catch (Exception e) {
	    response.setContentType("application/json");
	    super.writeJsonError(response, e.getMessage());
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonSalvaServizioParams(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	AppIoParamRestResponse res = fromJson(request.getInputStream(), AppIoParamRestResponse.class);
	AppIoParamId id = new AppIoParamId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setIdentificativoServizio(res.getIdentificativoServizio());
	id.setParametro(res.getParametro());
	AppIoParam appIoParam = new AppIoParam();
	appIoParam.setId(id);
	appIoParam.setDescrizione(res.getDescrizione());
	appIoParamService.insert(appIoParam);
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonEliminaServizioParams(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	AppIoServiziConfigParamRestResponse res = fromJson(request.getInputStream(), AppIoServiziConfigParamRestResponse.class);
	AppIoServiziConfigParamId id1 = new AppIoServiziConfigParamId();
	id1.setCodiceComune(res.getCodiceComune());
	id1.setIdcomune(ORMHelper.getIdcomune());
	id1.setIdentificativoServizio(res.getIdentificativoServizio());
	id1.setParametro(res.getParametro());
	id1.setSoftware(ORMHelper.getSoftware());
	AppIoServiziConfigParam par = new AppIoServiziConfigParam();
	par.setId(id1);
	appIoServiziCongParamService.delete(par);
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonModificaServizio(Model model, HttpServletRequest request, HttpServletResponse response) throws JAXBException, IOException {

	AppIoServiziRestResponse res = fromJson(request.getInputStream(), AppIoServiziRestResponse.class);
	AppIoServiziId id = new AppIoServiziId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setIdentificativoServizio(res.getPrecIdentificativoServizio());
	AppIoServizi entity = new AppIoServizi();
	entity.setId(id);
	entity.setDescrizione(res.getDescrizioneServizio());
	AppIoServizi appSer = appIoServiziService.findById(id);
	appSer.getId().setIdentificativoServizio(res.getIdentificativoServizio());
	appSer.setDescrizione(res.getDescrizioneServizio());
	AppIoServiziId id1 = new AppIoServiziId();
	id1.setIdcomune(ORMHelper.getIdcomune());
	id1.setIdentificativoServizio(res.getPrecIdentificativoServizio());
	appIoServiziService.updateServizio(res.getDescrizioneServizio(), res.getPrecIdentificativoServizio(), res.getIdentificativoServizio());
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsongetServizioParams(Model model, @RequestParam("idServizio") String idServizio, HttpServletRequest request,
	    HttpServletResponse response) throws JAXBException, IOException {

	List<AppIoServiziConfigParamRestResponse> pars = new ArrayList<AppIoServiziConfigParamRestResponse>();
	List<AppIoServiziConfigParam> params = appIoServiziCongParamService.findByIdServizio(idServizio);
	for (AppIoServiziConfigParam appIoServiziConfigParam : params) {
	    AppIoServiziConfigParamRestResponse res = new AppIoServiziConfigParamRestResponse();
	    res.setCodiceComune(appIoServiziConfigParam.getId().getCodiceComune());
	    res.setIdentificativoServizio(appIoServiziConfigParam.getId().getIdentificativoServizio());
	    res.setParametro(appIoServiziConfigParam.getId().getParametro());
	    res.setValore(appIoServiziConfigParam.getDescrizione());
	    pars.add(res);
	}
	response.setContentType("application/json");
	response.getOutputStream().write(this.listToJsonBytes(pars, AppIoServiziConfigParamRestResponse.class, false));
	response.getOutputStream().flush();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsongetStatoComunicazione(Model model, @RequestParam("codicemovimento") Integer codiceMovimento, HttpServletRequest request,
	    HttpServletResponse response) throws JAXBException, IOException {

	List<AppIoCodaMovimenti> codaMovimenti = appIoCodaMovimentiService.findByMovimento(codiceMovimento);
	//AppIoCodaMovimenti appIoCodaMovimenti = codaMovimenti.get(0);
	List<AppIoCodaRestBean> resBean = new ArrayList<AppIoCodaRestBean>();
	for (AppIoCodaMovimenti appIoCodaMovimenti : codaMovimenti) {
	    AppIoCodaId id = new AppIoCodaId();
	    id.setGuid(appIoCodaMovimenti.getId().getGuidCoda());
	    id.setIdcomune(ORMHelper.getIdcomune());
	    AppIoCoda appIoCoda = appIoCodaService.findById(id);
	    List<AppIoCodaStati> statiCoda = appIoCodaStatiService.findByGuid(appIoCodaMovimenti.getId().getGuidCoda());
	    AppIoCodaRestBean ioCodaRestBean = new AppIoCodaRestBean();
	    ioCodaRestBean.setCodiceFiscale(appIoCoda.getCodiceFiscale());
	    ioCodaRestBean.setGuid(appIoCoda.getId().getGuid());
	    ioCodaRestBean.setIdServizio(appIoCoda.getIdentificativoServizio());
	    ioCodaRestBean.setMessaggio(appIoCoda.getMessaggio());
	    ioCodaRestBean.setOggetto(appIoCoda.getOggetto());
	    ioCodaRestBean.setStatoMessaggio(appIoCoda.getStatoMessaggio());
	    ioCodaRestBean.setUltimoStato(statiCoda.get(0).getId().getStato());
	    ioCodaRestBean.setUltimoDataStato(statiCoda.get(0).getId().getData());
	    resBean.add(ioCodaRestBean);
	}
	response.setContentType("application/json");
	byte[] b = this.listToJsonBytes(resBean, AppIoCodaRestBean.class, false);
	response.getOutputStream().write(b);
	response.getOutputStream().flush();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsongetSingoloStatoComunicazione(Model model, @RequestParam("guid") String guidCoda, HttpServletRequest request,
	    HttpServletResponse response) throws JAXBException, IOException {

	AppIoCodaId id = new AppIoCodaId();
	id.setGuid(guidCoda);
	id.setIdcomune(ORMHelper.getIdcomune());
	AppIoCoda appIoCoda = appIoCodaService.findById(id);
	List<AppIoCodaStati> statiCoda = appIoCodaStatiService.findByGuid(guidCoda);
	AppIoCodaRestBean ioCodaRestBean = new AppIoCodaRestBean();
	ioCodaRestBean.setCodiceFiscale(appIoCoda.getCodiceFiscale());
	ioCodaRestBean.setGuid(appIoCoda.getId().getGuid());
	ioCodaRestBean.setIdServizio(appIoCoda.getIdentificativoServizio());
	ioCodaRestBean.setMessaggio(appIoCoda.getMessaggio());
	ioCodaRestBean.setOggetto(appIoCoda.getOggetto());
	ioCodaRestBean.setStatoMessaggio(appIoCoda.getStatoMessaggio());
	ioCodaRestBean.setUltimoStato(statiCoda.get(0).getId().getStato());
	ioCodaRestBean.setUltimoDataStato(statiCoda.get(0).getId().getData());
	response.setContentType("application/json");
	byte[] b = this.toJsonBytes(ioCodaRestBean);
	response.getOutputStream().write(b);
	response.getOutputStream().flush();
    }

    @XmlRootElement(name = "request")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class JsonDettaglioComuneRequest {

	@XmlElement(name = "comune")
	public String comune;
	@XmlElement(name = "idServizio")
	public String idServizio;

	public JsonDettaglioComuneRequest() {

	}

	public JsonDettaglioComuneRequest(String comune, String idServizio) {

	    this.comune = comune;
	    this.idServizio = idServizio;
	}
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonFindConfigurazioneComune(Model model, HttpServletRequest request, HttpServletResponse response)
	    throws JAXBException, IOException {

	try {
	    JsonDettaglioComuneRequest jsonRequest = fromJson(request.getInputStream(), JsonDettaglioComuneRequest.class);
	    AppIoServiziConfigRestResponse jsonResponse = this.appIoServiziConfigService.findByIdServizioEComune(jsonRequest.idServizio,
		    jsonRequest.comune);
	    response.setContentType("application/json");
	    String test = toJson(jsonResponse, true);
	    response.getOutputStream().write(test.getBytes("utf-8"));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    @RequestMapping(method = RequestMethod.POST)
    public String ajaxCaricaTabellaEndo(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    @RequestParam(value = "id.tipomovimento", required = true) String tipomovimento, HttpServletRequest request,
	    HttpServletResponse response) {

	model.addAttribute("endoList", tipimovimentoAppIoserviziService.findConfigurazioniEndoProcedimenti(idservizio, tipomovimento));
	return "appio/ajaxDettaglioEndo";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void ajaxInserisciEndo(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    @RequestParam(value = "id.tipomovimento", required = true) String tipomovimento,
	    @RequestParam(value = "codiceInventario", required = true) Integer codiceInventario,
	    @RequestParam(value = "templateOggetto", required = false) String templateOggetto,
	    @RequestParam(value = "templateMessaggio", required = false) String templateMessaggio, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String messaggio = "Dato salvato correttamente";
	try {
	    tipimovimentoAppIoserviziService.insertConfigurazioneEndo(idservizio, tipomovimento, codiceInventario, templateOggetto,
		    templateMessaggio);
	} catch (Exception e) {
	    messaggio = e.getMessage();
	}
	response.setContentType("text/plain");
	response.getWriter().write(messaggio);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void ajaxModificaEndo(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    @RequestParam(value = "id.tipomovimento", required = true) String tipomovimento,
	    @RequestParam(value = "codiceInventario", required = true) Integer codiceInventario,
	    @RequestParam(value = "templateOggetto", required = false) String templateOggetto,
	    @RequestParam(value = "templateMessaggio", required = false) String templateMessaggio, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String messaggio = "Dato salvato correttamente";
	try {
	    tipimovimentoAppIoserviziService.updateConfigurazioneEndo(idservizio, tipomovimento, codiceInventario, templateOggetto,
		    templateMessaggio);
	} catch (Exception e) {
	    messaggio = e.getMessage();
	}
	response.setContentType("text/plain");
	response.getWriter().write(messaggio);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void ajaxEliminaEndo(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    @RequestParam(value = "id.tipomovimento", required = true) String tipomovimento,
	    @RequestParam(value = "codiceInventario", required = true) Integer codiceInventario, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String messaggio = "Dato eliminato correttamente";
	try {
	    tipimovimentoAppIoserviziService.deleteConfigurazioneEndo(idservizio, tipomovimento, codiceInventario);
	} catch (Exception e) {
	    messaggio = e.getMessage();
	}
	response.setContentType("text/plain");
	response.getWriter().write(messaggio);
    }
    // INTERVENTI

    @RequestMapping(method = RequestMethod.POST)
    public String ajaxCaricaTabellaIntervento(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    @RequestParam(value = "id.tipomovimento", required = true) String tipomovimento, HttpServletRequest request,
	    HttpServletResponse response) {

	model.addAttribute("interventiList", tipimovimentoAppIoserviziService.findConfigurazioniInterventoRaggruppati(idservizio, tipomovimento));
	return "appio/ajaxDettaglioIntervento";
    }

    @RequestMapping(method = RequestMethod.POST)
    public void ajaxInserisciIntervento(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    @RequestParam(value = "id.tipomovimento", required = true) String tipomovimento,
	    @RequestParam(value = "codiceIntervento", required = true) Integer codiceIntervento,
	    @RequestParam(value = "templateOggetto", required = false) String templateOggetto,
	    @RequestParam(value = "templateMessaggio", required = false) String templateMessaggio, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String messaggio = "Dato salvato correttamente";
	try {
	    tipimovimentoAppIoserviziService.insertConfigurazioneIntervento(idservizio, tipomovimento, codiceIntervento, templateOggetto,
		    templateMessaggio);
	} catch (BusinessValidationException e) {
	    messaggio = e.getMessage();
	} catch (Exception e) {
	    messaggio = e.getMessage();
	}
	response.setContentType("text/plain");
	response.getWriter().write(messaggio);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void ajaxModificaIntervento(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    @RequestParam(value = "id.tipomovimento", required = true) String tipomovimento,
	    @RequestParam(value = "codiceIntervento", required = true) Integer codiceIntervento,
	    @RequestParam(value = "templateOggetto", required = false) String templateOggetto,
	    @RequestParam(value = "templateMessaggio", required = false) String templateMessaggio, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String messaggio = "Dato salvato correttamente";
	try {
	    tipimovimentoAppIoserviziService.updateConfigurazioneIntervento(idservizio, tipomovimento, codiceIntervento, templateOggetto,
		    templateMessaggio);
	} catch (Exception e) {
	    messaggio = e.getMessage();
	}
	response.setContentType("text/plain");
	response.getWriter().write(messaggio);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void ajaxEliminaIntervento(Model model, @RequestParam(value = "id.identificativoServizio", required = true) String idservizio,
	    @RequestParam(value = "id.tipomovimento", required = true) String tipomovimento,
	    @RequestParam(value = "codiceIntervento", required = true) Integer codiceIntervento, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String messaggio = "Dato eliminato correttamente";
	try {
	    tipimovimentoAppIoserviziService.deleteConfigurazioneIntervento(idservizio, tipomovimento, codiceIntervento);
	} catch (Exception e) {
	    messaggio = e.getMessage();
	}
	response.setContentType("text/plain");
	response.getWriter().write(messaggio);
    }

    @Override
    protected void setPageAttributes(Model model) {

	//  Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	//  Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	//  Auto-generated method stub
    }
}
