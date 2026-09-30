package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDCritass;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciCampoHelper;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.MercatiDCritassService;
import it.gruppoinit.pal.gp.core.service.MercatiDService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author
 */
//DAELIMINARE @Controller
@SessionAttributes("mercatidcritass")
public class MercatiDCritassController extends BaseController<MercatiDCritass> {

    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiDCritassService mercatidcritassService;
    private final static String VALORE = "valore";
    private final static String VALOREDECODIFICATO = "valoredecodificato";

    @RequestMapping
    public ModelMap list(@RequestParam("codicePosteggio") Integer codicePosteggio, HttpServletRequest request, HttpServletResponse response) {

	List<MercatiDCritass> mercatidcritassList = mercatidcritassService.findByPosteggio(codicePosteggio);
	MercatiD mercatiD = mercatiDService.findById(new PkId(codicePosteggio));
	ModelMap model = new ModelMap(mercatidcritassList);
	boolean export = createJMesaExport(request, response, mercatidcritassList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatidcritassList", mercatidcritassList);
	model.addAttribute("mercatiD", mercatiD);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codicePosteggio") Integer codicePosteggio, @RequestParam("codiceMercato") Integer codiceMercato, Model model) {

	MercatiDCritass mercatidcritass = new MercatiDCritass();
	MercatiD mercatiD = mercatiDService.findById(new PkId(codicePosteggio));
	mercatidcritass.setMercatiD(mercatiD);
	fixRenderEntityProperty(mercatidcritass);
	model.addAttribute("mercatidcritass", mercatidcritass);
	model.addAttribute("codiceMercato", codiceMercato);
	setPageAttributes(model);
	return "mercatidcritass/form";
    }

    @RequestMapping
    public String scegliCampoDinamico(Model model, @ModelAttribute("mercatidcritass") MercatiDCritass mercatidcritass, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(mercatidcritass);
	try {
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatidcritass, e);
	    fixRenderEntityProperty(mercatidcritass);
	    return "mercatidcritass/form";
	}
	String campoHtml = createCampoValore(mercatidcritass);
	fixRenderEntityProperty(mercatidcritass);
	model.addAttribute("mercatidcritass", mercatidcritass);
	model.addAttribute("campoHtml", campoHtml);
	return "mercatidcritass/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mercatidcritass") MercatiDCritass mercatidcritass, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Map<String, String> valoriCampoJsp = recuperaValoreCampo(mercatidcritass, request);
	mercatidcritass.setValore(valoriCampoJsp.get(VALORE));
	mercatidcritass.setValoredecodificato(valoriCampoJsp.get(VALOREDECODIFICATO));
	fixMergeEntityProperty(mercatidcritass);
	try {
	    mercatidcritassService.insert(mercatidcritass);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatidcritass, e);
	    String campoHtml = createCampoValore(mercatidcritass);
	    fixRenderEntityProperty(mercatidcritass);
	    model.addAttribute("campoHtml", campoHtml);
	    return "mercatidcritass/form";
	}
	String campoHtml = createCampoValore(mercatidcritass);
	fixRenderEntityProperty(mercatidcritass);
	model.addAttribute("mercatidcritass", mercatidcritass);
	model.addAttribute("campoHtml", campoHtml);
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatidcritass.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiDCritass mercatidcritass = mercatidcritassService.findById(id);
	String campoHtml = createCampoValore(mercatidcritass);
	fixRenderEntityProperty(mercatidcritass);
	model.addAttribute("mercatidcritass", mercatidcritass);
	model.addAttribute("campoHtml", campoHtml);
	setPageAttributes(model);
	return "mercatidcritass/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("mercatidcritass") MercatiDCritass mercatidcritass, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Map<String, String> valoriCampoJsp = recuperaValoreCampo(mercatidcritass, request);
	mercatidcritass.setValore(valoriCampoJsp.get(VALORE));
	mercatidcritass.setValoredecodificato(valoriCampoJsp.get(VALOREDECODIFICATO));
	fixMergeEntityProperty(mercatidcritass);
	try {
	    mercatidcritassService.update(mercatidcritass);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatidcritass, e);
	    String campoHtml = createCampoValore(mercatidcritass);
	    fixRenderEntityProperty(mercatidcritass);
	    model.addAttribute("campoHtml", campoHtml);
	    return "mercatidcritass/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatidcritass.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("mercatidcritass") MercatiDCritass mercatidcritass, BindingResult result, SessionStatus status) {

	MercatiDCritass objToDelete = mercatidcritassService.findById(mercatidcritass.getId());
	try {
	    mercatidcritassService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatidcritass, e);
	    fixRenderEntityProperty(mercatidcritass);
	    return "mercatidcritass/form";
	}
	status.setComplete();
	return "redirect:list.htm?codicePosteggio=" + objToDelete.getMercatiD().getId().getCodice();
    }

    /**
     * 
     * @param mercatidcritass
     * @return
     */
    private String createCampoValore(MercatiDCritass mercatidcritass) {

	StringBuffer buffer = new StringBuffer("<td>Valore</td><td>");
	// faccio il render del campo dinamico
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(mercatidcritass.getDyn2Campi().getId().getCodice()));
	ModellidinamiciCampoHelper campo = new ModellidinamiciCampoHelper();
	campo.setApplicationContext(getContext());
	campo.setDyn2Campi(dyn2Campi);
	campo.setValore(mercatidcritass.getValore());
	campo.setValoreDecodificato(mercatidcritass.getValoredecodificato());
	String campoHtml = dyn2ModellitService.renderCampo(null, TipoControlloEnum.valueOf(dyn2Campi.getTipodato()), dyn2Campi, campo);
	buffer.append(campoHtml);
	return buffer.toString();
    }

    /**
     * 
     * @param mercatidcritass
     * @return
     */
    private Map<String, String> recuperaValoreCampo(MercatiDCritass mercatidcritass, HttpServletRequest request) {

	Map<String, String> map = new HashMap<String, String>();
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(mercatidcritass.getDyn2Campi().getId().getCodice()));
	String nomecampoJsp = "";
	String nomecampoDecodificatoJsp = "";
	boolean isCheckbox = false;
	switch (TipoControlloEnum.valueOf(dyn2Campi.getTipodato())) {
	case Checkbox:
	    nomecampoJsp = "TMP_FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0";
	    nomecampoDecodificatoJsp = "TMP_FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0";
	    isCheckbox = true;
	    break;
	case ListaSIGePro:
	    nomecampoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0_ID";
	    nomecampoDecodificatoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0_DESC";
	    break;
	case Data:
	    nomecampoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0";
	    nomecampoDecodificatoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0";
	    break;
	case MultiLista:
	    nomecampoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0";
	    nomecampoDecodificatoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0";
	    break;
	case Ricerca:
	    nomecampoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0_ID";
	    nomecampoDecodificatoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0_DESC";
	    break;
	default:
	    nomecampoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0";
	    nomecampoDecodificatoJsp = "FLD_" + mercatidcritass.getDyn2Campi().getId().getCodice() + "_0_0";
	    break;
	}
	String valoreCampoJsp = "";
	String valoreDecodificatoCampoJsp = "";
	String[] oggettoMappa = request.getParameterValues(nomecampoJsp);
	String[] oggettoMappaDecodificato = request.getParameterValues(nomecampoDecodificatoJsp);
	// Recupero e decodifico il campo valore
	if (oggettoMappa != null) {
	    if (oggettoMappa.length > 0) {
		if (oggettoMappa.length > 1) {
		    valoreCampoJsp = StringUtils.join(oggettoMappa, ";");
		} else {
		    valoreCampoJsp = oggettoMappa[0];
		}
	    }
	}
	// Recupero e decodifico il campo valore decodificato
	if (oggettoMappaDecodificato != null) {
	    if (oggettoMappaDecodificato.length > 0) {
		if (oggettoMappaDecodificato.length > 1) {
		    valoreDecodificatoCampoJsp = StringUtils.join(oggettoMappaDecodificato, ";");
		} else {
		    valoreDecodificatoCampoJsp = oggettoMappaDecodificato[0];
		}
	    }
	}
	//	String valoreCampoJsp = (String) request.getParameter(nomecampoJsp);
	//	String valoreDecodificatoCampoJsp = (String) request.getParameter(nomecampoDecodificatoJsp);
	// Nel caso di check box se non viene spuntato ritorna valore null, devo settare il valore e valore decodificato a 0
	// in quanto sono due valore obb.
	if (isCheckbox) {
	    valoreCampoJsp = (StringUtils.isBlank(valoreCampoJsp) ? "0" : valoreCampoJsp);
	    valoreDecodificatoCampoJsp = (StringUtils.isBlank(valoreDecodificatoCampoJsp) ? "0" : valoreDecodificatoCampoJsp);
	}
	map.put(VALORE, valoreCampoJsp);
	map.put(VALOREDECODIFICATO, valoreDecodificatoCampoJsp);
	return map;
    }

    @Override
    protected void fixMergeEntityProperty(MercatiDCritass entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MercatiDCritass entity) {

	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
	if (entity.getMercatiD() == null) {
	    entity.setMercatiD(new MercatiD());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
