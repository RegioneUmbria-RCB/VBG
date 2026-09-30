package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.RegolamentoComunaleHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ParteLocaleSchedaEndoTipo1Command;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.CartProxyService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.DocumentazioneLocale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoNormativeLocali;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoNormativeLocali.RegolamentoComunale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoNormativeRegionali;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoNormativeRegionali.NormativaRegionale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.PagamentoLocale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteLocaleSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteRegionaleSchedaEndoTipo1;

@Controller
@SessionAttributes(value = { "partelocaleschedaendotipo1" })
public class ParteLocaleSchedaEndoTipo1Controller extends BaseController<ParteLocaleSchedaEndoTipo1> {

    private static final Logger log = LoggerFactory.getLogger(ParteLocaleSchedaEndoTipo1Controller.class);
    @Autowired
    private StpEndoTipo1Service stpEndoTipo1Service;
    @Autowired
    private CartProxyService cartProxyService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public String create(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	ParteLocaleSchedaEndoTipo1Command parteLocaleSchedaEndoTipo1 = new ParteLocaleSchedaEndoTipo1Command();
	StpEndoTipo1 endo1 = stpEndoTipo1Service.findbyStpCodice(codice);
	gestSchedaLocalizzata(model, parteLocaleSchedaEndoTipo1, endo1);
	fixRenderParteLocaleSchedaEndoTipo1CommandProperty(parteLocaleSchedaEndoTipo1);
	fixRenderEntityProperty(parteLocaleSchedaEndoTipo1.getEntity());
	model.addAttribute("partelocaleschedaendotipo1", parteLocaleSchedaEndoTipo1);
	setPageAttributes(model);
	return "partelocaleschedaendotipo1/form";
    }

    @RequestMapping
    public String view(Model model, @ModelAttribute("partelocaleschedaendotipo1") ParteLocaleSchedaEndoTipo1Command parteLocaleSchedaEndoTipo1,
	    BindingResult result, SessionStatus status) {

	model.addAttribute("partelocaleschedaendotipo1", parteLocaleSchedaEndoTipo1);
	return "partelocaleschedaendotipo1/form";
    }

    private void gestSchedaLocalizzata(Model model, ParteLocaleSchedaEndoTipo1Command parteLocaleSchedaEndoTipo1, StpEndoTipo1 endo1) {

	try {
	    // esiste la scheda regionale?
	    if (EntityUtils.getNestedProperty(endo1, "oggetti.id") != null) {
		// la scheda regionale esiste
		Oggetti schedaEndo = oggettiService.findById(endo1.getOggetti().getId());
		JAXBContext jc = JAXBContext.newInstance(InvioSchedaEndoTipo1.class);
		Unmarshaller u = jc.createUnmarshaller();
		InvioSchedaEndoTipo1 invioSchedaEndoTipo1 = (InvioSchedaEndoTipo1) u.unmarshal(new ByteArrayInputStream(schedaEndo.getOggetto()));
		ParteLocaleSchedaEndoTipo1 parteLocale = invioSchedaEndoTipo1.getParteLocaleSchedaEndoTipo1();
		if (parteLocale != null) {
		    if (StringUtils.isBlank(parteLocale.getSUAP())) {
			Verticalizzazioniparametri verticalizzazioniparametriSUAP_ID = verticalizzazioniService
				.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SUAP_ID);
			if (verticalizzazioniparametriSUAP_ID == null) {
			    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SUAP_ID");
			    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO VERTICALIZZAZIONE_CART_SUAP_ID");
			}
			parteLocale.setSUAP(verticalizzazioniparametriSUAP_ID.getValore());
		    }
		    // normative endo tipo 1
		    List<ElencoNormativeLocali.RegolamentoComunale> regolamentoComunale1List = null;
		    List<RegolamentoComunaleHelper> regolamentoComunaleHelpers1 = new ArrayList<RegolamentoComunaleHelper>();
		    if (null != parteLocale.getElencoNormativeLocaliEndoTipo1()) {
			regolamentoComunale1List = parteLocale.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
			for (RegolamentoComunale regolamentoComunale : regolamentoComunale1List) {
			    RegolamentoComunaleHelper helper = new RegolamentoComunaleHelper();
			    //			ElencoNormativeRegionali.NormativaRegionale normaRegionale = mapAdempimentiRegionali.get(regolamentoComunale
			    //				.getNormativaRegionale());
			    // helper.setNormativaRegionaleEndo1(normaRegionale);
			    helper.setValue(regolamentoComunale.getRegolamentoComunale().getValue());
			    helper.setUrl(regolamentoComunale.getRegolamentoComunale().getURL());
			    //			if (null != normaRegionale) {
			    //			    StpTipologieEndo1 adempimento = stpTipologieEndo1Service.findbyStpCodice(new Integer(normaRegionale
			    //				    .getAdempimentoNormativa()));
			    //			    helper.setDescrizioneAdempimento(adempimento.getTipifamiglieendo().getTipo());
			    //			}
			    regolamentoComunaleHelpers1.add(helper);
			}
			parteLocaleSchedaEndoTipo1.setRegolamentoComunaleHelpers(regolamentoComunaleHelpers1);
		    }
		} else {
		    parteLocale = new ParteLocaleSchedaEndoTipo1();
		}
		ParteRegionaleSchedaEndoTipo1 parteRegionaleSchedaEndoTipo1 = invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1();
		if (parteRegionaleSchedaEndoTipo1 != null) {
		    parteLocale.setDataInizioValidita(parteRegionaleSchedaEndoTipo1.getDataInizioValidita());
		    parteLocale.setDataFineValidita(parteRegionaleSchedaEndoTipo1.getDataFineValidita());
		    parteLocale.setParteRegionale(parteRegionaleSchedaEndoTipo1.getEndoprocedimento());
		    List<ElencoNormativeRegionali.NormativaRegionale> normativaRegionale1List = null;
		    Map<BigInteger, ElencoNormativeRegionali.NormativaRegionale> mapAdempimentiRegionali = new HashMap<BigInteger, ElencoNormativeRegionali.NormativaRegionale>();
		    if (null != parteRegionaleSchedaEndoTipo1.getElencoNormativeRegionaliEndoTipo1()) {
			normativaRegionale1List = parteRegionaleSchedaEndoTipo1.getElencoNormativeRegionaliEndoTipo1().getNormativaRegionale();
			for (NormativaRegionale normativaRegionale : normativaRegionale1List) {
			    mapAdempimentiRegionali.put(normativaRegionale.getId(), normativaRegionale);
			}
		    }
		}
		// end normative 1
		parteLocaleSchedaEndoTipo1.setEntity(parteLocale);
	    } else {
		// la scheda regionale non esiste lancio l'eccezione
		throw new RuntimeException("La scheda dell'endo procedimento di tipo 2 regionale non è stata scaricata.");
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage());
	}
    }

    @RequestMapping
    public String addElencoNormativeLocaliTipo1(Model model,
	    @ModelAttribute("partelocaleschedaendotipo1") ParteLocaleSchedaEndoTipo1Command parteLocaleSchedaEndoTipo1, BindingResult result,
	    SessionStatus status) {

	// try {
	// RegolamentoComunale regolamentoComunale = new RegolamentoComunale();
	// BigInteger normativaRegionaleBigIntegr = new
	// BigInteger(parteLocaleSchedaEndoTipo1.getRegolamentoComunaleTipo1().getNormativaRegionale());
	// regolamentoComunale.setNormativaRegionale(normativaRegionaleBigIntegr);
	// regolamentoComunale.setURL(parteLocaleSchedaEndoTipo1.getRegolamentoComunaleTipo1().getUrl());
	// regolamentoComunale.setValue(parteLocaleSchedaEndoTipo1.getRegolamentoComunaleTipo1().getValue());
	// List<RegolamentoComunale> list =
	// parteLocaleSchedaEndoTipo1.getEntity().getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
	// List<RegolamentoComunaleHelper> listHelper = parteLocaleSchedaEndoTipo1.getRegolamentoComunaleHelpers();
	// if (!list.isEmpty()) {
	// list.add(list.size(), regolamentoComunale);
	// listHelper.add(listHelper.size(), parteLocaleSchedaEndoTipo1.getRegolamentoComunaleTipo1());
	// } else {
	// list.add(regolamentoComunale);
	// listHelper.add(parteLocaleSchedaEndoTipo1.getRegolamentoComunaleTipo1());
	// }
	// // parteLocaleSchedaEndoTipo1.getEntity().getElencoNormativeLocaliEndoTipo1().setRegolamentoComunale(list);
	// parteLocaleSchedaEndoTipo1.setRegolamentoComunaleHelpers(listHelper);
	// RegolamentoComunaleHelper regolamentoComunaleHelper1 = new RegolamentoComunaleHelper();
	// parteLocaleSchedaEndoTipo1.setRegolamentoComunaleTipo1(regolamentoComunaleHelper1);
	// } catch (Exception e) {
	// }
	model.addAttribute("partelocaleschedaendotipo1", parteLocaleSchedaEndoTipo1);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String deleteElencoNormativeLocaliTipo1(@RequestParam("elemento") Integer elemento, Model model,
	    @ModelAttribute("partelocaleschedaendotipo1") ParteLocaleSchedaEndoTipo1Command parteLocaleSchedaEndoTipo1, BindingResult result,
	    SessionStatus status) {

	try {
	    List<RegolamentoComunale> list = parteLocaleSchedaEndoTipo1.getEntity().getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
	    List<RegolamentoComunaleHelper> listHelper = parteLocaleSchedaEndoTipo1.getRegolamentoComunaleHelpers();
	    RegolamentoComunale object = list.get(elemento);
	    RegolamentoComunaleHelper objectHelper = listHelper.get(elemento);
	    list.remove(object);
	    listHelper.remove(objectHelper);
	    // parteLocaleSchedaEndoTipo1.getEntity().getElencoNormativeLocaliEndoTipo1().setRegolamentoComunale(list);
	    parteLocaleSchedaEndoTipo1.setRegolamentoComunaleHelpers(listHelper);
	} catch (Exception e) {
	    // FIXME gestire eccezione!
	    log.error(e.getMessage());
	}
	model.addAttribute("partelocaleschedaendotipo1", parteLocaleSchedaEndoTipo1);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String invia(Model model, @ModelAttribute("partelocaleschedaendotipo1") ParteLocaleSchedaEndoTipo1Command parteLocaleSchedaEndoTipo1,
	    BindingResult result, SessionStatus status) {

	try {
	    ParteLocaleSchedaEndoTipo1 entity = parteLocaleSchedaEndoTipo1.getEntity();
	    // List<RegolamentoComunaleHelper> normeTipo1 = parteLocaleSchedaEndoTipo1.getRegolamentoComunaleHelpers();
	    // List<RegolamentoComunale> norme1 = entity.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
	    //	    for (RegolamentoComunale regolamentoComunale : norme1) {
	    //		BigInteger normaregionale = regolamentoComunale.getNormativaRegionale();
	    //		for (RegolamentoComunaleHelper regolamentoComunaleHelper : normeTipo1) {
	    //		    if (regolamentoComunaleHelper.getNormativaRegionaleEndo1().getId().intValue() == normaregionale.intValue()) {
	    //			regolamentoComunale.getRegolamentoComunale().setValue(regolamentoComunaleHelper.getValue());
	    //			regolamentoComunale.getRegolamentoComunale().setURL(regolamentoComunaleHelper.getUrl());
	    //			break;
	    //		    }
	    //		}
	    //	    }
	    cartProxyService.inviaLocalizzazioneEndo1(entity, entity.getParteRegionale().intValue());
	} catch (Exception e) {
	    log.error("invia: {}", e.getMessage());
	    return "redirect:view.htm?status_msg=03&msg=" + e.getMessage();
	}
	model.addAttribute("partelocaleschedaendotipo1", parteLocaleSchedaEndoTipo1);
	return "redirect:view.htm?status_msg=01";
    }

    @Override
    protected void fixMergeEntityProperty(ParteLocaleSchedaEndoTipo1 entity) {

    }

    // @Override
    protected void fixRenderParteLocaleSchedaEndoTipo1CommandProperty(ParteLocaleSchedaEndoTipo1Command parteLocaleSchedaEndoTipo1Command) {

	// necessario in quanto non esiste il costruttore dell'oggetto ParteLocaleSchedaEndoTipo2
	if (parteLocaleSchedaEndoTipo1Command.getEntity() == null) {
	    ParteLocaleSchedaEndoTipo1 parteLocaleSchedaEndoTipo1 = new ParteLocaleSchedaEndoTipo1();
	    parteLocaleSchedaEndoTipo1.setElencoNormativeLocaliEndoTipo1(new ElencoNormativeLocali());
	    parteLocaleSchedaEndoTipo1.setDocumentazioneLocale(new DocumentazioneLocale());
	    parteLocaleSchedaEndoTipo1.setPagamentoLocale(new PagamentoLocale());
	    parteLocaleSchedaEndoTipo1Command.setEntity(parteLocaleSchedaEndoTipo1);
	}
    }

    //
    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixRenderEntityProperty(ParteLocaleSchedaEndoTipo1 entity) {

	// // inizializza la lista di l'elenco delle normative locali tipo 1
	if (entity.getElencoNormativeLocaliEndoTipo1() != null) {
	    if (entity.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale() == null) {
		entity.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
	    }
	} else {
	    entity.setElencoNormativeLocaliEndoTipo1(new ElencoNormativeLocali());
	    entity.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
	}
	if (entity.getDocumentazioneLocale() == null) {
	    entity.setDocumentazioneLocale(new DocumentazioneLocale());
	}
	if (entity.getPagamentoLocale() == null) {
	    entity.setPagamentoLocale(new PagamentoLocale());
	}
    }
}
