package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
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
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.RegolamentoComunaleHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ParteLocaleSchedaEndoTipo2Command;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.CartInvioLocalizzazioneEndo2Service;
import it.gruppoinit.pal.gp.core.service.CartProxyService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo1Service;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.AllegatoRichiesto;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.DocumentazioneLocale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoAllegatiRichiesti;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoEndoLocali;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoEndoPrevisti;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoNormativeLocali;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoNormativeLocali.RegolamentoComunale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoNormativeRegionali;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoNormativeRegionali.NormativaRegionale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoQuadri;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoQuadri.Quadro;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.EndoLocale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.File;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Norma;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.PagamentoLocale;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteLocaleSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteRegionaleSchedaEndoTipo2;

@Controller
@SessionAttributes(value = { "partelocaleschedaendotipo2" })
public class ParteLocaleSchedaEndoTipo2Controller extends BaseController<ParteLocaleSchedaEndoTipo2> {

    private static final Logger log = LoggerFactory.getLogger(ParteLocaleSchedaEndoTipo2Controller.class);
    private static final String ELENCO_LOCALI_DOPO = "LOCALI_DOPO";
    private static final String ELENCO_LOCALI_PRIMA = "LOCALI_PRIMA";
    private static final String ELENCO_REGIONALI_DOPO = "REGIONALI_DOPO";
    private static final String ELENCO_REGIONALI_PRIMA = "REGIONALI_PRIMA";
    //    @Autowired
    //    private StpTipologieEndo1Service stpTipologieEndo1Service;
    @Autowired
    private StpEndoTipo1Service stpEndoTipo1Service;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private CartProxyService cartProxyService;
    @Autowired
    private CartInvioLocalizzazioneEndo2Service cartInvioLocalizzazioneEndo2Service;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private StpTipologieEndo1Service stpTipologieEndo1Service;

    @RequestMapping
    public String create(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) throws Exception {

	ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2 = new ParteLocaleSchedaEndoTipo2Command();
	StpEndoTipo2 endo2 = stpEndoTipo2Service.findbyStpCodice(codice, StpEndoTipo2Service.TIPO_ENDO);
	parteLocaleSchedaEndoTipo2.setAlberoproc(endo2.getAlberoproc());
	gestSchedaLocalizzata(model, parteLocaleSchedaEndoTipo2, endo2, true);
	fixRenderEntityProperty(parteLocaleSchedaEndoTipo2.getEntity());
	setPageAttributes(model);
	String descrizioneEndo = endo2.getAlberoproc().getVwAlberoproc().getScDescrizione();
	model.addAttribute("descrizioneEndo", descrizioneEndo);
	model.addAttribute("partelocaleschedaendotipo2", parteLocaleSchedaEndoTipo2);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String view(Model model, @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2,
	    BindingResult result, SessionStatus status) throws Exception {

	model.addAttribute("partelocaleschedaendotipo2", parteLocaleSchedaEndoTipo2);
	StpEndoTipo2 endo2 = stpEndoTipo2Service.findbyStpCodice(parteLocaleSchedaEndoTipo2.getEntity().getParteRegionale().intValue(),
		StpEndoTipo2Service.TIPO_ENDO);
	gestSchedaLocalizzata(model, parteLocaleSchedaEndoTipo2, endo2, true);
	String descrizioneEndo = endo2.getAlberoproc().getVwAlberoproc().getScDescrizione();
	model.addAttribute("descrizioneEndo", descrizioneEndo);
	return "partelocaleschedaendotipo2/form";
    }

    @SuppressWarnings("unchecked")
    private void gestSchedaLocalizzata(Model model, ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, StpEndoTipo2 endo2,
	    boolean isCreate) {

	try {
	    // esiste la scheda regionale?
	    if (EntityUtils.getNestedProperty(endo2, "oggetti.id") != null) {
		// la scheda regionale esiste
		Oggetti schedaEndo = oggettiService.findById(new PkId(endo2.getOggetti().getId().getCodice()));
		JAXBContext jc = JAXBContext.newInstance(InvioSchedaEndoTipo2.class);
		Unmarshaller u = jc.createUnmarshaller();
		InvioSchedaEndoTipo2 invioSchedaEndoTipo2 = (InvioSchedaEndoTipo2) u.unmarshal(new ByteArrayInputStream(schedaEndo.getOggetto()));
		ParteLocaleSchedaEndoTipo2 parteLocale = invioSchedaEndoTipo2.getParteLocaleSchedaEndoTipo2();
		if (parteLocale == null) {
		    parteLocale = new ParteLocaleSchedaEndoTipo2();
		    fixRenderEntityProperty(parteLocale);
		    invioSchedaEndoTipo2.setParteLocaleSchedaEndoTipo2(parteLocale);
		}
		// Devo aggiungere u
		//if (StringUtils.isBlank(parteLocale.getSUAP())) {
		Verticalizzazioniparametri verticalizzazioniparametriSUAP_ID = verticalizzazioniService
			.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SUAP_ID);
		if (verticalizzazioniparametriSUAP_ID == null) {
		    log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SUAP_ID");
		    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO VERTICALIZZAZIONE_CART_SUAP_ID");
		}
		parteLocale.setSUAP(verticalizzazioniparametriSUAP_ID.getValore());
		// }
		ParteRegionaleSchedaEndoTipo2 parteRegionaleSchedaEndoTipo2 = invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2();
		parteLocale.setDataInizioValidita(parteRegionaleSchedaEndoTipo2.getDataInizioValidita());
		parteLocale.setDataFineValidita(parteRegionaleSchedaEndoTipo2.getDataFineValidita());
		parteLocale.setParteRegionale(parteRegionaleSchedaEndoTipo2.getEndoprocedimento());
		if (parteLocaleSchedaEndoTipo2.getEntity() != null) {
		    parteLocale.getElencoEndoPrevistiPrima()
			    .setElencoEndoLocali(parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiPrima().getElencoEndoLocali());
		}
		if (parteLocaleSchedaEndoTipo2.getEntity() != null) {
		    parteLocale.getElencoEndoPrevistiDopo()
			    .setElencoEndoLocali(parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiDopo().getElencoEndoLocali());
		}
		Map<String, String> adempimentiMap = StpController.getAdempimentiNormativeMap();
		//ElencoEndoPrevisti elencoEndoPrevistiPrima =  parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiPrima();
		//		elencoEndoPrevistiPrima.getElencoEndoLocali().getEndoLocale();
		//		parteLocale.getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale();
		//		List<EndoLocale> endoLocalesPrima = ListUtils.union(elencoEndoPrevistiPrima.getElencoEndoLocali().getEndoLocale(), parteLocale
		//			.getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale());
		//		parteLocale.getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale().addAll(endoLocalesPrima);
		//		if (EntityUtils.getNestedProperty(parteLocale, "elencoEndoPrevistiPrima.elencoEndoRegionali") != null) {
		//		    List<BigInteger> listPrima = parteLocale.getElencoEndoPrevistiPrima().getElencoEndoRegionali().getEndoTipo1();
		//		    List<StpEndoTipo1> invsPrima = parteLocaleSchedaEndoTipo2.getInventarioprocedimentisPrima();
		//		    for (BigInteger stpCodice : listPrima) {
		//			StpEndoTipo1 endoPrima = stpEndoTipo1Service.findbyStpCodice(stpCodice.intValue());
		//			invsPrima.add(endoPrima);
		//		    }
		//		    parteLocaleSchedaEndoTipo2.setInventarioprocedimentisPrima(invsPrima);
		//		}
		//		if (EntityUtils.getNestedProperty(parteLocale, "elencoEndoPrevistiDopo.elencoEndoRegionali") != null) {
		//		    List<BigInteger> listDopo = parteLocale.getElencoEndoPrevistiDopo().getElencoEndoRegionali().getEndoTipo1();
		//		    List<StpEndoTipo1> invsDopo = parteLocaleSchedaEndoTipo2.getInventarioprocedimentisDopo();
		//		    for (BigInteger stpCodice : listDopo) {
		//			StpEndoTipo1 endoDopo = stpEndoTipo1Service.findbyStpCodice(stpCodice.intValue());
		//			invsDopo.add(endoDopo);
		//		    }
		//		    parteLocaleSchedaEndoTipo2.setInventarioprocedimentisDopo(invsDopo);
		//		}
		// Recupero normative endo tipo 1 da mostrare sulla scheda di localizzaizone (Scheda D1)
		List<ElencoNormativeRegionali.NormativaRegionale> normativaRegionale1List = parteRegionaleSchedaEndoTipo2
			.getElencoNormativeRegionaliEndoTipo1().getNormativaRegionale();
		//List<ElencoNormativeLocali.RegolamentoComunale> regolamentoComunale1List = null;
		// Lista di appoggio per memorizzare le informazioni riguardanti le normative locali di tipo 1
		List<RegolamentoComunaleHelper> regolamentoComunaleHelpers1 = new ArrayList<RegolamentoComunaleHelper>();
		if (null != parteRegionaleSchedaEndoTipo2.getElencoNormativeRegionaliEndoTipo1()) {
		    RegolamentoComunaleHelper regolamentoComunaleHelper = null;
		    // Dalla parte della schede regionale recupero le informazione sulle normative regionali/nazionali Scheda D (parte regionale)
		    for (NormativaRegionale normativaRegionale : normativaRegionale1List) {
			regolamentoComunaleHelper = new RegolamentoComunaleHelper();
			regolamentoComunaleHelper.setNormativaRegionale(normativaRegionale);
			// Recupero la descrizionde dell'adempimento
			String idAdempimento = StringUtils.defaultString(normativaRegionale.getAdempimentoNormativa()).trim();
			if (StringUtils.isNotBlank(idAdempimento)) {
			    String descrizioneAdempimento = adempimentiMap.get(idAdempimento);
			    if (StringUtils.isNotBlank(descrizioneAdempimento)) {
				regolamentoComunaleHelper.setDescrizioneAdempimento(descrizioneAdempimento);
			    }
			}
			// Popolo la parte locale della normativa se è già presente
			if (parteLocale.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale() != null) {
			    List<RegolamentoComunale> regolamentoComunales = parteLocale.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
			    // Uso la variabile "id" che lega la normativa regionale (passata dal sistema cart) con la normativa locale
			    //ch inseriamo noi. 
			    for (RegolamentoComunale regolamentoComunale : regolamentoComunales) {
				// Quando troviamo la corrispondenza vediamo se sull' xml è già salvata la normativa locale e in particolare
				// i campi value e url dell' oggetto norma. Se i campi sono presenti sull'xml riporto quelli salvati, altrimentii
				//metto stringa vuota.
				if (regolamentoComunale.getId().equals(normativaRegionale.getId())) {
				    String value = (StringUtils.isNotBlank(regolamentoComunale.getRegolamentoComunale().getValue())
					    ? regolamentoComunale.getRegolamentoComunale().getValue()
					    : "");
				    String url = (StringUtils.isNotBlank(regolamentoComunale.getRegolamentoComunale().getURL())
					    ? regolamentoComunale.getRegolamentoComunale().getURL()
					    : "");
				    regolamentoComunaleHelper.setUrl(url);
				    regolamentoComunaleHelper.setValue(value);
				    break;
				}
			    }
			    regolamentoComunaleHelpers1.add(regolamentoComunaleHelper);
			    //mapAdempimentiRegionali.put(normativaRegionale.getId(), normativaRegionale);
			}
			parteLocaleSchedaEndoTipo2.setRegolamentoComunaleHelpers(regolamentoComunaleHelpers1);
		    }
		}
		//		if (null != parteLocale.getElencoNormativeLocaliEndoTipo1()) {
		//		    regolamentoComunale1List = parteLocale.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
		//		    for (RegolamentoComunale regolamentoComunale : regolamentoComunale1List) {
		//			RegolamentoComunaleHelper helper = new RegolamentoComunaleHelper();
		//			//			ElencoNormativeRegionali.NormativaRegionale normaRegionale = mapAdempimentiRegionali.get(regolamentoComunale
		//			//				.getNormativaRegionale());
		//			// 			helper.setNormativaRegionale(normaRegionale);
		//			helper.setValue(regolamentoComunale.getRegolamentoComunale().getValue());
		//			helper.setUrl(regolamentoComunale.getRegolamentoComunale().getURL());
		//			//			if (null != normaRegionale) {
		//			//			    StpTipologieEndo1 adempimento = stpTipologieEndo1Service.findbyStpCodice(new Integer(normaRegionale
		//			//				    .getAdempimentoNormativa()));
		//			//			    helper.setDescrizioneAdempimento(adempimento.getTipifamiglieendo().getTipo());
		//			//			}
		//			regolamentoComunaleHelpers1.add(helper);
		//		    }
		//		    parteLocaleSchedaEndoTipo2.setRegolamentoComunaleHelpers(regolamentoComunaleHelpers1);
		//		}
		// end normative 1
		// normative endo tipo 2
		//		List<ElencoNormativeRegionali.NormativaRegionale> normativaRegionale2List = null;
		//		List<ElencoNormativeLocali.RegolamentoComunale> regolamentoComunale2List = null;
		//		
		//		List<RegolamentoComunaleHelper> regolamentoComunaleHelpers2 = new ArrayList<RegolamentoComunaleHelper>();
		//		if (null != parteRegionaleSchedaEndoTipo2.getElencoNormativeRegionaliEndoTipo2()) {
		//		    normativaRegionale2List = parteRegionaleSchedaEndoTipo2.getElencoNormativeRegionaliEndoTipo2().getNormativaRegionale();
		//		    for (NormativaRegionale normativaRegionale : normativaRegionale2List) {
		//			mapAdempimentiRegionali2.put(normativaRegionale.getId(), normativaRegionale);
		//		    }
		//		}
		//		if (null != parteLocale.getElencoNormativeLocaliEndoTipo2()) {
		//		    regolamentoComunale2List = parteLocale.getElencoNormativeLocaliEndoTipo2().getRegolamentoComunale();
		//		    for (RegolamentoComunale regolamentoComunale : regolamentoComunale2List) {
		//			RegolamentoComunaleHelper helper = new RegolamentoComunaleHelper();
		//			//			ElencoNormativeRegionali.NormativaRegionale normaRegionale = mapAdempimentiRegionali2.get(regolamentoComunale
		//			//				.getNormativaRegionale());
		//			//			helper.setNormativaRegionale(normaRegionale);
		//			helper.setValue(regolamentoComunale.getRegolamentoComunale().getValue());
		//			helper.setUrl(regolamentoComunale.getRegolamentoComunale().getURL());
		//			//			if (null != normaRegionale) {
		//			//			    StpTipologieEndo1 adempimento = stpTipologieEndo1Service.findbyStpCodice(new Integer(normaRegionale
		//			//				    .getAdempimentoNormativa()));
		//			//			    helper.setDescrizioneAdempimento(adempimento.getTipifamiglieendo().getTipo());
		//			//			}
		//			regolamentoComunaleHelpers2.add(helper);
		//		    }
		//		    parteLocaleSchedaEndoTipo2.setRegolamentoComunaleTipo2Helpers(regolamentoComunaleHelpers2);
		//		}
		List<ElencoNormativeRegionali.NormativaRegionale> normativaRegionale2List = parteRegionaleSchedaEndoTipo2
			.getElencoNormativeRegionaliEndoTipo2().getNormativaRegionale();
		//List<ElencoNormativeLocali.RegolamentoComunale> regolamentoComunale1List = null;
		// Lista di appoggio per memorizzare le informazioni riguardanti le normative locali di tipo 1
		List<RegolamentoComunaleHelper> regolamentoComunaleHelpers2 = new ArrayList<RegolamentoComunaleHelper>();
		if (null != parteRegionaleSchedaEndoTipo2.getElencoNormativeRegionaliEndoTipo2()) {
		    RegolamentoComunaleHelper regolamentoComunaleHelper = null;
		    // Dalla parte della schede regionale recupero le informazione sulle normative regionali/nazionali Scheda D (parte regionale)
		    for (NormativaRegionale normativaRegionale : normativaRegionale2List) {
			regolamentoComunaleHelper = new RegolamentoComunaleHelper();
			regolamentoComunaleHelper.setNormativaRegionale(normativaRegionale);
			// Recupero la descrizionde dell'adempimento
			String idAdempimento = StringUtils.defaultString(normativaRegionale.getAdempimentoNormativa()).trim();
			if (StringUtils.isNotBlank(idAdempimento)) {
			    String descrizioneAdempimento = adempimentiMap.get(idAdempimento);
			    if (StringUtils.isNotBlank(descrizioneAdempimento)) {
				regolamentoComunaleHelper.setDescrizioneAdempimento(descrizioneAdempimento);
			    }
			}
			// Popolo la parte locale della normativa se è già presente
			if (parteLocale.getElencoNormativeLocaliEndoTipo2().getRegolamentoComunale() != null) {
			    List<RegolamentoComunale> regolamentoComunales = parteLocale.getElencoNormativeLocaliEndoTipo2().getRegolamentoComunale();
			    // Uso la variabile "id" che lega la normativa regionale (passata dal sistema cart) con la normativa locale
			    //che inseriamo noi. 
			    for (RegolamentoComunale regolamentoComunale : regolamentoComunales) {
				// Quando troviamo la corrispondenza vediamo se sull' xml è già salvata la normativa locale e in particolare
				// i campi value e url dell' oggetto norma. Se i campi sono presenti sull'xml riporto quelli salvati, altrimentii
				//metto stringa vuota.
				if (regolamentoComunale.getId().equals(normativaRegionale.getId())) {
				    String value = (StringUtils.isNotBlank(regolamentoComunale.getRegolamentoComunale().getValue())
					    ? regolamentoComunale.getRegolamentoComunale().getValue()
					    : "");
				    String url = (StringUtils.isNotBlank(regolamentoComunale.getRegolamentoComunale().getURL())
					    ? regolamentoComunale.getRegolamentoComunale().getURL()
					    : "");
				    regolamentoComunaleHelper.setUrl(url);
				    regolamentoComunaleHelper.setValue(value);
				    break;
				}
			    }
			    regolamentoComunaleHelpers2.add(regolamentoComunaleHelper);
			    //mapAdempimentiRegionali.put(normativaRegionale.getId(), normativaRegionale);
			}
			parteLocaleSchedaEndoTipo2.setRegolamentoComunaleTipo2Helpers(regolamentoComunaleHelpers2);
		    }
		}
		// end normative 2
		parteLocaleSchedaEndoTipo2.setEntity(parteLocale);
	    } else {
		// la scheda regionale non esiste lancio l'eccezione
		throw new RuntimeException("La scheda dell'endo procedimento di tipo 2 regionale non è stata scaricata.");
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage());
	}
    }

    @RequestMapping
    public String addElencoEndoRegionali(Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status) {

	addElencoEndo(parteLocaleSchedaEndoTipo2, ELENCO_REGIONALI_PRIMA);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String deleteElencoEndoRegionali(@RequestParam("elemento") Integer elemento, Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status) {

	deleteElementoEndo(elemento, parteLocaleSchedaEndoTipo2, ELENCO_REGIONALI_PRIMA);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String addElencoEndoLocali(Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status) {

	addElencoEndo(parteLocaleSchedaEndoTipo2, ELENCO_LOCALI_PRIMA);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String deleteElencoEndoLocali(@RequestParam("elemento") Integer elemento, Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status) {

	deleteElementoEndo(elemento, parteLocaleSchedaEndoTipo2, ELENCO_LOCALI_PRIMA);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String addElencoEndoRegionaliDopo(Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status) {

	addElencoEndo(parteLocaleSchedaEndoTipo2, ELENCO_REGIONALI_DOPO);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String deleteElencoEndoRegionaliDopo(@RequestParam("elemento") Integer elemento, Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status) {

	deleteElementoEndo(elemento, parteLocaleSchedaEndoTipo2, ELENCO_REGIONALI_DOPO);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String addAllegatoElencoEndoLocaliPrima(@RequestParam("endoLocale") String endoLocale, Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	addAllegatoEndoLocale(endoLocale, parteLocaleSchedaEndoTipo2, ELENCO_LOCALI_PRIMA, request);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String addAllegatoElencoEndoLocaliDopo(@RequestParam("endoLocale") String endoLocale, Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	addAllegatoEndoLocale(endoLocale, parteLocaleSchedaEndoTipo2, ELENCO_LOCALI_DOPO, request);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String deleteAllegatoElencoEndoLocaliPrima(@RequestParam("endoLocale") String endoLocale, @RequestParam("idx") Integer idx, Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	deleteAllegatoEndoLocale(endoLocale, idx, parteLocaleSchedaEndoTipo2, ELENCO_LOCALI_PRIMA, request);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String deleteAllegatoElencoEndoLocaliDopo(@RequestParam("endoLocale") String endoLocale, @RequestParam("idx") Integer idx, Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	deleteAllegatoEndoLocale(endoLocale, idx, parteLocaleSchedaEndoTipo2, ELENCO_LOCALI_DOPO, request);
	return "redirect:view.htm";
    }

    private void deleteAllegatoEndoLocale(String endoLocaleStr, Integer idx, ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2,
	    String tipo, HttpServletRequest request) {

	if (idx != null) {
	    List<EndoLocale> list = null;
	    if (tipo.equalsIgnoreCase(ELENCO_LOCALI_PRIMA)) {
		list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale();
	    } else {
		list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale();
	    }
	    if (list.size() > 0) {
		for (EndoLocale el2 : list) {
		    if (el2.getCodice().equalsIgnoreCase(endoLocaleStr)) {
			ElencoQuadri eq = el2.getElencoQuadriStandard5();
			List<Quadro> quadris = eq.getQuadro();
			Quadro q = quadris.get(0);
			ElencoAllegatiRichiesti ear = q.getElencoAllegatiRichiestiQuadro();
			AllegatoRichiesto ar = ear.getAllegatoRichiesto().remove(idx.intValue());
			ar.toString();
		    }
		}
	    }
	}
    }

    private void addAllegatoEndoLocale(String endoLocaleStr, ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, String tipo,
	    HttpServletRequest request) {

	String prefissoRequest = "PRIMA";
	if (!tipo.equalsIgnoreCase(ELENCO_LOCALI_PRIMA)) {
	    prefissoRequest = "DOPO";
	}
	String valoreAllegato = request.getParameter("allegatoendodescrizione_" + prefissoRequest + "_" + endoLocaleStr);
	if (StringUtils.isNotBlank(valoreAllegato)) {
	    List<EndoLocale> list = null;
	    if (tipo.equalsIgnoreCase(ELENCO_LOCALI_PRIMA)) {
		list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale();
	    } else {
		list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale();
	    }
	    if (list.size() > 0) {
		for (EndoLocale el2 : list) {
		    if (el2.getCodice().equalsIgnoreCase(endoLocaleStr)) {
			ElencoQuadri eq = el2.getElencoQuadriStandard5();
			if (eq == null) {
			    eq = new ElencoQuadri();
			    el2.setElencoQuadriStandard5(eq);
			}
			List<Quadro> quadris = eq.getQuadro();
			Quadro q = null;
			if (quadris.size() == 0) {
			    q = new Quadro();
			    File f = new File();
			    f.setNomeFile("");
			    f.setDatiFile(" ".getBytes());
			    q.setTestoQuadro(f);
			    quadris.add(q);
			} else {
			    q = quadris.get(0);
			}
			ElencoAllegatiRichiesti ear = q.getElencoAllegatiRichiestiQuadro();
			if (ear == null) {
			    ear = new ElencoAllegatiRichiesti();
			    q.setElencoAllegatiRichiestiQuadro(ear);
			}
			if (ear != null) {
			    AllegatoRichiesto ar = new AllegatoRichiesto();
			    ar.setSpiegazioniAllegato(valoreAllegato);
			    ear.getAllegatoRichiesto().add(ar);
			}
		    }
		}
	    }
	}
    }

    @RequestMapping
    public String addElencoEndoLocaliDopo(Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status) {

	addElencoEndo(parteLocaleSchedaEndoTipo2, ELENCO_LOCALI_DOPO);
	return "redirect:view.htm";
    }

    @RequestMapping
    public String deleteElencoEndoLocaliDopo(@RequestParam("elemento") Integer elemento, Model model,
	    @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, BindingResult result,
	    SessionStatus status) {

	deleteElementoEndo(elemento, parteLocaleSchedaEndoTipo2, ELENCO_LOCALI_DOPO);
	return "redirect:view.htm";
    }

    private void addElencoEndo(ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, String tipo) {

	if (tipo.equalsIgnoreCase(ELENCO_REGIONALI_PRIMA)) {
	    String elencoEndoRegionaliPrima = parteLocaleSchedaEndoTipo2.getElencoEndoRegionaliPrima();
	    Integer codiceinventario = new Integer(elencoEndoRegionaliPrima);
	    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceinventario));
	    StpEndoTipo1 endo = stpEndoTipo1Service.findByInventarioProcedimenti(inventarioprocedimenti);
	    // BigInteger codiceStp = BigInteger.valueOf(endo.getCodiceStp());
	    //	    List<BigInteger> list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiPrima().getElencoEndoRegionali().getEndoTipo1();
	    //	    if (!list.isEmpty()) {
	    //		list.add(list.size(), codiceStp);
	    //	    } else {
	    //		list.add(codiceStp);
	    //	    }
	    List<StpEndoTipo1> invs = parteLocaleSchedaEndoTipo2.getInventarioprocedimentisPrima();
	    invs.add(endo);
	} else if (tipo.equalsIgnoreCase(ELENCO_REGIONALI_DOPO)) {
	    String elencoEndoRegionaliPrima = parteLocaleSchedaEndoTipo2.getElencoEndoRegionaliDopo();
	    Integer codiceinventario = new Integer(elencoEndoRegionaliPrima);
	    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceinventario));
	    StpEndoTipo1 endo = stpEndoTipo1Service.findByInventarioProcedimenti(inventarioprocedimenti);
	    // BigInteger codiceStp = BigInteger.valueOf(endo.getCodiceStp());
	    //	    List<BigInteger> list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiDopo().getElencoEndoRegionali().getEndoTipo1();
	    //	    if (!list.isEmpty()) {
	    //		list.add(list.size(), codiceStp);
	    //	    } else {
	    //		list.add(codiceStp);
	    //	    }
	    List<StpEndoTipo1> invs = parteLocaleSchedaEndoTipo2.getInventarioprocedimentisDopo();
	    invs.add(endo);
	} else if (tipo.equalsIgnoreCase(ELENCO_LOCALI_PRIMA)) {
	    EndoLocale endoLocale = parteLocaleSchedaEndoTipo2.getEndoLocalePrima();
	    if (endoLocale != null && endoLocale.getCodice() != null) {
		List<EndoLocale> list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale();
		if (!list.isEmpty()) {
		    list.add(list.size(), endoLocale);
		} else {
		    list.add(endoLocale);
		}
	    }
	    endoLocale = new EndoLocale();
	    parteLocaleSchedaEndoTipo2.setEndoLocalePrima(endoLocale);
	} else if (tipo.equalsIgnoreCase(ELENCO_LOCALI_DOPO)) {
	    EndoLocale endoLocale = parteLocaleSchedaEndoTipo2.getEndoLocaleDopo();
	    if (endoLocale != null && endoLocale.getCodice() != null) {
		List<EndoLocale> list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale();
		if (!list.isEmpty()) {
		    list.add(list.size(), endoLocale);
		} else {
		    list.add(endoLocale);
		}
	    }
	    endoLocale = new EndoLocale();
	    parteLocaleSchedaEndoTipo2.setEndoLocaleDopo(endoLocale);
	}
    }

    private void deleteElementoEndo(Integer elemento, ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2, String tipo) {

	if (tipo.equalsIgnoreCase(ELENCO_REGIONALI_PRIMA)) {
	    //  Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(elemento));
	    //StpEndoTipo1 endo = stpEndoTipo1Service.findByInventarioProcedimenti(inventarioprocedimenti);
	    // BigInteger codiceStp = BigInteger.valueOf(endo.getCodiceStp());
	    //	    List<BigInteger> list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiPrima().getElencoEndoRegionali().getEndoTipo1();
	    //	    list.remove(codiceStp);
	    List<StpEndoTipo1> invs = parteLocaleSchedaEndoTipo2.getInventarioprocedimentisPrima();
	    StpEndoTipo1[] listArray = new StpEndoTipo1[invs.size()];
	    listArray = invs.toArray(listArray);
	    for (int i = 0; i < listArray.length; i++) {
		StpEndoTipo1 stpEndoTipo1 = listArray[i];
		if (stpEndoTipo1.getInventarioprocedimenti().getId().getCodice().equals(elemento)) {
		    invs.remove(stpEndoTipo1);
		}
	    }
	} else if (tipo.equalsIgnoreCase(ELENCO_REGIONALI_DOPO)) {
	    // Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(elemento));
	    // StpEndoTipo1 endo = stpEndoTipo1Service.findByInventarioProcedimenti(inventarioprocedimenti);
	    // BigInteger codiceStp = BigInteger.valueOf(endo.getCodiceStp());
	    //	    List<BigInteger> list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiDopo().getElencoEndoRegionali().getEndoTipo1();
	    //	    list.remove(codiceStp);
	    List<StpEndoTipo1> invs = parteLocaleSchedaEndoTipo2.getInventarioprocedimentisDopo();
	    StpEndoTipo1[] listArray = new StpEndoTipo1[invs.size()];
	    listArray = invs.toArray(listArray);
	    for (int i = 0; i < listArray.length; i++) {
		StpEndoTipo1 stpEndoTipo1 = listArray[i];
		if (stpEndoTipo1.getInventarioprocedimenti().getId().getCodice().equals(elemento)) {
		    invs.remove(stpEndoTipo1);
		}
	    }
	} else if (tipo.equalsIgnoreCase(ELENCO_LOCALI_PRIMA)) {
	    List<EndoLocale> list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale();
	    for (int i = 0; i < list.size(); i++) {
		String codice = "";
		if (StringUtils.isNotBlank(list.get(i).getCodice())) {
		    codice = list.get(i).getCodice();
		} else {
		    list.remove(i);
		}
		if (codice.equalsIgnoreCase(String.valueOf(elemento))) {
		    list.remove(i);
		}
	    }
	} else if (tipo.equalsIgnoreCase(ELENCO_LOCALI_DOPO)) {
	    List<EndoLocale> list = parteLocaleSchedaEndoTipo2.getEntity().getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale();
	    for (int i = 0; i < list.size(); i++) {
		String codice = "";
		if (StringUtils.isNotBlank(list.get(i).getCodice())) {
		    codice = list.get(i).getCodice();
		} else {
		    list.remove(i);
		}
		if (codice.equalsIgnoreCase(String.valueOf(elemento))) {
		    list.remove(i);
		}
	    }
	}
    }

    @RequestMapping
    public String invia(Model model, @ModelAttribute("partelocaleschedaendotipo2") ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2,
	    BindingResult result, SessionStatus status) {

	try {
	    // Variabili da inizializzare per fare il Marshaller e l'Unmarshaller del file xml
	    JAXBContext jc = JAXBContext.newInstance(InvioSchedaEndoTipo2.class);
	    Marshaller m = jc.createMarshaller();
	    // è una proprietà dell'operazione di Marshaller che evita sia generato l'xml con la proprietà 
	    // " standalone = "yes" " non compatibile.
	    m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
	    Unmarshaller u = jc.createUnmarshaller();
	    // Recupero l'oggetto contenete il file xml salvato e da questo ricavo l'oggetto  "InvioSchedaEndoTipo2" tramite Unmarshaller
	    StpEndoTipo2 endo2 = stpEndoTipo2Service.findbyStpCodice(parteLocaleSchedaEndoTipo2.getEntity().getParteRegionale().intValue(),
		    StpEndoTipo2Service.TIPO_ENDO);
	    Oggetti oggetti = oggettiService.findById((endo2.getOggetti().getId()));
	    // Creo l'oggetto InvioSchedaEndoTipo2 tramite Unmarshaller
	    InvioSchedaEndoTipo2 invioSchedaEndoTipo2 = (InvioSchedaEndoTipo2) u.unmarshal(new ByteArrayInputStream(oggetti.getOggetto()));
	    populateInvioSchedaEndoTipo2(parteLocaleSchedaEndoTipo2, invioSchedaEndoTipo2);
	    // Faccio il marshaller dell'oggetto InvioSchedaEndoTipo2 per creare nuovamente xml 
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    m.marshal(invioSchedaEndoTipo2, byteArrayOutputStream);
	    //System.out.println(byteArrayOutputStream.toString());
	    // All'oggetto recuperato dal db contente tuute le info vado a inserire solo le modiiche fatte alla parte locale
	    // setto l'array di byte che rappresenta l'xml creato all' Oggetto e faccio l'update per salvare le modifiche
	    // applicate sulla parte locale.
	    oggetti.setOggetto(byteArrayOutputStream.toByteArray());
	    oggettiService.update(oggetti);
	    cartInvioLocalizzazioneEndo2Service.inviaLocalizzazioneSchedaEndo(parteLocaleSchedaEndoTipo2.getAlberoproc().getId().getCodice());
	    //  StpEndoTipo2 endo2 = stpEndoTipo2Service.findbyStpCodice(codice, StpEndoTipo2Service.TIPO_ENDO);
	    // List<RegolamentoComunaleHelper> normeTipo1 = parteLocaleSchedaEndoTipo2.getRegolamentoComunaleHelpers();
	    // List<RegolamentoComunale> norme1 = entity.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
	    //	    for (RegolamentoComunale regolamentoComunale : norme1) {
	    //		BigInteger normaregionale = regolamentoComunale.getNormativaRegionale();
	    //		for (RegolamentoComunaleHelper regolamentoComunaleHelper : normeTipo1) {
	    //		    if (regolamentoComunaleHelper.getNormativaRegionale().getId().intValue() == normaregionale.intValue()) {
	    //			regolamentoComunale.setValue(regolamentoComunaleHelper.getValue());
	    //			regolamentoComunale.setURL(regolamentoComunaleHelper.getUrl());
	    //			break;
	    //		    }
	    //		}
	    //	    }
	    // List<RegolamentoComunaleHelper> normeTipo2 = parteLocaleSchedaEndoTipo2.getRegolamentoComunaleTipo2Helpers();
	    // List<RegolamentoComunale> norme2 = entity.getElencoNormativeLocaliEndoTipo2().getRegolamentoComunale();
	    //	    for (RegolamentoComunale regolamentoComunale : norme2) {
	    //		BigInteger normaregionale = regolamentoComunale.getNormativaRegionale();
	    //		for (RegolamentoComunaleHelper regolamentoComunaleHelper : normeTipo2) {
	    //		    if (regolamentoComunaleHelper.getNormativaRegionale().getId().intValue() == normaregionale.intValue()) {
	    //			regolamentoComunale.getRegolamentoComunale().setValue(regolamentoComunaleHelper.getValue());
	    //			regolamentoComunale.getRegolamentoComunale().setURL(regolamentoComunaleHelper.getUrl());
	    //			break;
	    //		    }
	    //		}
	    //	    }
	} catch (Exception e) {
	    log.error("invia: {}", e.getMessage());
	    return "redirect:view.htm?status_msg=03&msg=" + e.getMessage();
	}
	model.addAttribute("partelocaleschedaendotipo2", parteLocaleSchedaEndoTipo2);
	return "redirect:view.htm?status_msg=01";
    }

    private void populateInvioSchedaEndoTipo2(ParteLocaleSchedaEndoTipo2Command parteLocaleSchedaEndoTipo2,
	    InvioSchedaEndoTipo2 invioSchedaEndoTipo2) {

	// Dal model recupero le informazione aggiunte o modificate sul form per la parte delle schede locali.
	ParteLocaleSchedaEndoTipo2 entity = parteLocaleSchedaEndoTipo2.getEntity();
	//  DALL'OGGETTO CREATO CON UMMARSHALLER DEL FILE XML MODIFICO SOLA LA PARTE DELLE SCHEDE LOCALI 
	//1. POPOLA I CAMPI : "descrizioneLocale", "altreInfoLocali","adempimentiSuccessiviLocali", "noteLocali",
	//"documentazioneLocale.destinatarioDocumentazione","documentazioneLocale.noteDocumentazione",
	//"pagamentoLocale.contributiOneri","pagamentoLocale.dirittiSegreteria","pagamentoLocale.dirittiIstruttoriaSUAP",
	//"pagamentoLocale.notePagamento","elencoEndoPrevistiPrima","elencoEndoPrevistiDopo"
	log.debug("INIZIO POPULATE CAMPI BASE:\"descrizioneLocale\", \"altreInfoLocali\",\"adempimentiSuccessiviLocali\", \"noteLocali\"," +
		"\"documentazioneLocale.destinatarioDocumentazione\",\"documentazioneLocale.noteDocumentazione\"," +
		"\"pagamentoLocale.contributiOneri\",\"pagamentoLocale.dirittiSegreteria\",\"pagamentoLocale.dirittiIstruttoriaSUAP\"," +
		"\"pagamentoLocale.notePagamento\",\"elencoEndoPrevistiPrima\",\"elencoEndoPrevistiDopo\"");
	invioSchedaEndoTipo2.setParteLocaleSchedaEndoTipo2(entity);
	entity.setDataInizioValidita(Utilities.getToday());
	// PREMERGE ACTIONS - INSERISCO I VALORI RICHIESTI PER LA VALIDAZIONE DELL'XSD
	if (entity.getAdempimentiSuccessiviLocali() == null) {
	    entity.setAdempimentiSuccessiviLocali("");
	}
	if (entity.getAltreInfoLocali() == null) {
	    entity.setAltreInfoLocali("");
	}
	if (entity.getDescrizioneLocale() == null) {
	    entity.setDescrizioneLocale("");
	}
	if (entity.getNoteLocali() == null) {
	    entity.setNoteLocali("");
	}
	if (entity.getDocumentazioneLocale() != null) {
	    if (entity.getDocumentazioneLocale().getDestinatarioDocumentazione() == null) {
		entity.getDocumentazioneLocale().setDestinatarioDocumentazione("");
	    }
	    if (entity.getDocumentazioneLocale().getNoteDocumentazione() == null) {
		entity.getDocumentazioneLocale().setNoteDocumentazione("");
	    }
	}
	if (entity.getPagamentoLocale() != null) {
	    if (entity.getPagamentoLocale().getContributiOneri() == null) {
		entity.getPagamentoLocale().setContributiOneri("");
	    }
	    if (entity.getPagamentoLocale().getContributiOneriValore() == null) {
		entity.getPagamentoLocale().setContributiOneriValore("");
	    }
	    if (entity.getPagamentoLocale().getDirittiIstruttoriaSUAP() == null) {
		entity.getPagamentoLocale().setDirittiIstruttoriaSUAP("");
	    }
	    if (entity.getPagamentoLocale().getDirittiIstruttoriaSUAPValore() == null) {
		entity.getPagamentoLocale().setDirittiIstruttoriaSUAPValore("");
	    }
	    if (entity.getPagamentoLocale().getDirittiSegreteria() == null) {
		entity.getPagamentoLocale().setDirittiSegreteria("");
	    }
	    if (entity.getPagamentoLocale().getDirittiSegreteriaValore() == null) {
		entity.getPagamentoLocale().setDirittiSegreteriaValore("");
	    }
	    if (entity.getPagamentoLocale().getNotePagamento() == null) {
		entity.getPagamentoLocale().setNotePagamento("");
	    }
	}
	if (entity.getElencoEndoPrevistiPrima() != null) {
	    if (entity.getElencoEndoPrevistiPrima().getElencoEndoLocali() != null) {
		if (entity.getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale().size() > 0) {
		    preparaDefaultEndoLocali(entity.getElencoEndoPrevistiPrima().getElencoEndoLocali().getEndoLocale());
		}
	    }
	}
	if (entity.getElencoEndoPrevistiDopo() != null) {
	    if (entity.getElencoEndoPrevistiDopo().getElencoEndoLocali() != null) {
		if (entity.getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale().size() > 0) {
		    preparaDefaultEndoLocali(entity.getElencoEndoPrevistiDopo().getElencoEndoLocali().getEndoLocale());
		}
	    }
	}
	// PREMERGE ACTIONS - INSERISCO I VALORI RICHIESTI PER LA VALIDAZIONE DELL'XSD
	log.debug("FINE POPULATE CAMPI BASE");
	//2. POPULATE ELENCO NORMATIVE SCHEDA D1
	log.debug("INIZIO POPULATE ELENCO NORMATIVE SCHEDA D1");
	List<RegolamentoComunaleHelper> regolamentoComunaleHelpers = parteLocaleSchedaEndoTipo2.getRegolamentoComunaleHelpers();
	// Recupero la lista dei regolamenti comunali che andrò a popolare con le info che ritornano dal model presenti sul 
	// Oggetto RegolamentoComunaleHelper
	List<RegolamentoComunale> regolamentoComunales = entity.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
	// Verifica se già abbiamo salvato sull'xml i dati sulle normative locali D1 (norme Endo tipo 1)
	boolean isRegolamentoComunaleListEmpty = (entity.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale().isEmpty() ? true : false);
	log.debug("La lista delle normative di tipo 1 è popolata: {} ", isRegolamentoComunaleListEmpty);
	RegolamentoComunale regolamentoComunale = null;
	Norma norma = null;
	int i = 0;
	// Dalle informazioni che recuperiamo dal model andiamo a popolare un oggetto RegolamentoComunale che andremo ad
	//inserire nella lista delle Norme locali.
	for (RegolamentoComunaleHelper regolamentoComunaleHelper : regolamentoComunaleHelpers) {
	    log.debug("Popolo l'oggetto RegolamentoComunale");
	    regolamentoComunale = new RegolamentoComunale();
	    norma = new Norma();
	    // Configura l'adempimento della norma
	    regolamentoComunale.setAdempimentoNormativa(regolamentoComunaleHelper.getDescrizioneAdempimento());
	    // Ci da il riferimento numerico (codice stp della norma a livello regionale)
	    regolamentoComunale.setId(regolamentoComunaleHelper.getNormativaRegionale().getId());
	    // Valori relativi alla norma comunale 
	    norma.setValue(regolamentoComunaleHelper.getValue());
	    norma.setURL(regolamentoComunaleHelper.getUrl());
	    regolamentoComunale.setRegolamentoComunale(norma);
	    if (isRegolamentoComunaleListEmpty) {
		regolamentoComunales.add(regolamentoComunale);
	    } else {
		regolamentoComunales.set(i, regolamentoComunale);
	    }
	    i++;
	    log.debug("Oggetto Regolamento Comunale associato alle lista");
	}
	log.debug("FINE POPULATE ELENCO NORMATIVE SCHEDA D1");
	//3. POPULATE ELENCO NORMATIVE SCHEDA E1
	log.debug("POPULATE ELENCO NORMATIVE SCHEDA E1");
	List<RegolamentoComunaleHelper> regolamentoComunale2Helpers = parteLocaleSchedaEndoTipo2.getRegolamentoComunaleTipo2Helpers();
	// Recupero la lista dei regolamenti comunali che andrò a popolare con le info che ritornano dal model presenti sul 
	// Oggetto RegolamentoComunaleHelper
	List<RegolamentoComunale> regolamento2Comunales = entity.getElencoNormativeLocaliEndoTipo2().getRegolamentoComunale();
	// Verifica se già abbiamo salvato sull'xml i dati sulle normative locali scheda E1(Endo tipo 2)
	boolean isRegolamentoComunaleList2Empty = (entity.getElencoNormativeLocaliEndoTipo2().getRegolamentoComunale().isEmpty() ? true : false);
	log.debug("La lista delle normative di tipo 2 è popolata: {} ", isRegolamentoComunaleList2Empty);
	RegolamentoComunale regolamentoComunale2 = null;
	Norma norma2 = null;
	int j = 0;
	// Dalle informazioni che recuperiamo dal model andiamo a popolare un oggetto RegolamentoComunale E2 che andremo ad
	//inserire nella lista delle Norme locali E2.
	for (RegolamentoComunaleHelper regolamentoComunaleHelper : regolamentoComunale2Helpers) {
	    log.debug("Popolo l'oggetto RegolamentoComunale");
	    regolamentoComunale2 = new RegolamentoComunale();
	    norma2 = new Norma();
	    // Configura l'adempimento della norma
	    regolamentoComunale2.setAdempimentoNormativa(regolamentoComunaleHelper.getDescrizioneAdempimento());
	    // Ci da il riferimento numerico (codice stp della norma a livello regionale)
	    regolamentoComunale2.setId(regolamentoComunaleHelper.getNormativaRegionale().getId());
	    // Valori relativi alla norma comunale 
	    norma2.setValue(regolamentoComunaleHelper.getValue());
	    norma2.setURL(regolamentoComunaleHelper.getUrl());
	    regolamentoComunale2.setRegolamentoComunale(norma2);
	    if (isRegolamentoComunaleList2Empty) {
		regolamento2Comunales.add(regolamentoComunale2);
	    } else {
		regolamento2Comunales.set(j, regolamentoComunale2);
	    }
	    j++;
	    log.debug("Oggetto Regolamento Comunale associato alle lista");
	}
	log.debug("FINE POPULATE ELENCO NORMATIVE SCHEDA E1");
    }

    private void preparaDefaultEndoLocali(List<EndoLocale> endoLocali) {

	for (EndoLocale endo : endoLocali) {
	    if (endo.getElencoQuadriStandard5() == null) {
		endo.setElencoQuadriStandard5(new ElencoQuadri());
	    } else {
		List<Quadro> qs = endo.getElencoQuadriStandard5().getQuadro();
		if (qs.size() > 0) {
		    for (Quadro quadro : qs) {
			if (quadro.getTestoQuadro() == null) {
			    File f = new File();
			    f.setNomeFile("");
			    f.setDatiFile(" ".getBytes());
			    quadro.setTestoQuadro(f);
			} else {
			    if (quadro.getTestoQuadro().getContentType() != null) {
				quadro.getTestoQuadro().setContentType(null);
			    }
			    if (quadro.getTestoQuadro().getNomeFile() == null) {
				quadro.getTestoQuadro().setNomeFile("");
			    }
			    if (quadro.getTestoQuadro().getDatiFile() == null) {
				quadro.getTestoQuadro().setDatiFile(" ".getBytes());
			    }
			}
			ElencoAllegatiRichiesti ear = quadro.getElencoAllegatiRichiestiQuadro();
			List<AllegatoRichiesto> ars = ear.getAllegatoRichiesto();
			for (AllegatoRichiesto allegatoRichiesto : ars) {
			    if (allegatoRichiesto.getAdempimentoAllegato() == null) {
				allegatoRichiesto.setAdempimentoAllegato("");
			    }
			    if (allegatoRichiesto.getCodiceAllegato() == null) {
				allegatoRichiesto.setCodiceAllegato("");
			    }
			    if (allegatoRichiesto.getTipologiaAllegato() == null) {
				allegatoRichiesto.setTipologiaAllegato("");
			    }
			}
		    }
		}
	    }
	    if (endo.getElencoQuadriStandard6() == null) {
		endo.setElencoQuadriStandard6(new ElencoQuadri());
	    }
	}
    }

    @Override
    protected void fixMergeEntityProperty(ParteLocaleSchedaEndoTipo2 entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixRenderEntityProperty(ParteLocaleSchedaEndoTipo2 entity) {

	// inizializza l'oggetto entity (non ha il costruttore)
	// inizializza la lista di endo tipo 1 nell'elenco regionale
	if (EntityUtils.getNestedProperty(entity, "elencoEndoPrevistiPrima") == null) {
	    ElencoEndoPrevisti elencoEndoPrevistiprima = new ElencoEndoPrevisti();
	    elencoEndoPrevistiprima.setElencoEndoLocali(new ElencoEndoLocali());
	    // elencoEndoPrevistiprima.setElencoEndoRegionali(new ElencoEndoRegionali());
	    entity.setElencoEndoPrevistiPrima(elencoEndoPrevistiprima);
	}
	if (EntityUtils.getNestedProperty(entity, "elencoEndoPrevistiPrima.elencoEndoLocali") == null) {
	    ElencoEndoPrevisti elencoEndoPrevistiprima = (ElencoEndoPrevisti) EntityUtils.getNestedProperty(entity, "elencoEndoPrevistiPrima");
	    elencoEndoPrevistiprima.setElencoEndoLocali(new ElencoEndoLocali());
	}
	if (EntityUtils.getNestedProperty(entity, "elencoEndoPrevistiDopo") == null) {
	    ElencoEndoPrevisti elencoEndoPrevistidopo = new ElencoEndoPrevisti();
	    elencoEndoPrevistidopo.setElencoEndoLocali(new ElencoEndoLocali());
	    // elencoEndoPrevistidopo.setElencoEndoRegionali(new ElencoEndoRegionali());
	    entity.setElencoEndoPrevistiDopo(elencoEndoPrevistidopo);
	}
	if (EntityUtils.getNestedProperty(entity, "elencoEndoPrevistiDopo.elencoEndoLocali") == null) {
	    ElencoEndoPrevisti elencoEndoPrevistidopo = (ElencoEndoPrevisti) EntityUtils.getNestedProperty(entity, "elencoEndoPrevistiDopo");
	    elencoEndoPrevistidopo.setElencoEndoLocali(new ElencoEndoLocali());
	}
	if (entity.getElencoNormativeLocaliEndoTipo1() == null) {
	    entity.setElencoNormativeLocaliEndoTipo1(new ElencoNormativeLocali());
	}
	// inizializza la lista di l'elenco delle normative locali tipo 1
	if (entity.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale() == null) {
	    entity.getElencoNormativeLocaliEndoTipo1().getRegolamentoComunale();
	}
	if (entity.getElencoNormativeLocaliEndoTipo2() == null) {
	    entity.setElencoNormativeLocaliEndoTipo2(new ElencoNormativeLocali());
	}
	// inizializza la lista di l'elenco delle normative locali tipo 2
	if (entity.getElencoNormativeLocaliEndoTipo2().getRegolamentoComunale() == null) {
	    // entity.getElencoNormativeLocaliEndoTipo2().setRegolamentoComunale(new ArrayList<RegolamentoComunale>());
	    entity.getElencoNormativeLocaliEndoTipo2().getRegolamentoComunale();
	}
	if (entity.getDocumentazioneLocale() == null) {
	    entity.setDocumentazioneLocale(new DocumentazioneLocale());
	}
	if (entity.getPagamentoLocale() == null) {
	    entity.setPagamentoLocale(new PagamentoLocale());
	}
    }
}
