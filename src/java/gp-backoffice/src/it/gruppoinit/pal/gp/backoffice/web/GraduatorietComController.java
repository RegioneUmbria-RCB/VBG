package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedComDTO;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatorietComHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciCampoHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.GraduatorietComCommand;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaRigheFilter;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.jmesa.GraduatoriedComTable;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.ProprietaCampi;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.GraduatoriedComManagerService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedComService;
import it.gruppoinit.pal.gp.core.service.GraduatorietComService;
import it.gruppoinit.pal.gp.core.service.GraduatorietService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.helper.SchedeDinamicheTL;
import it.gruppoinit.pal.gp.core.utils.CustomHtmlBuilder;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("graduatorietcom")
public class GraduatorietComController extends BaseController<GraduatorietCom> {

    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private GraduatoriedComService graduatoriedComService;
    @Autowired
    private GraduatoriedComManagerService graduatoriedComManagerService;
    @Autowired
    private GraduatorietService graduatorietService;
    @Autowired
    private GraduatorietComService graduatorietcomService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private DocumentiDaFirmareService documentiDaFirmareService;
    private static final Logger log = LoggerFactory.getLogger(GraduatorietComController.class);

    @RequestMapping
    public ModelMap list(@RequestParam("codiceGraduatoria") Integer codiceGraduatoria, HttpServletRequest request, HttpServletResponse response) {

	Graduatoriet graduatoriet = graduatorietService.findById(new PkId(codiceGraduatoria));
	//List<GraduatorietCom> graduatorietcomList = graduatorietcomService.findByGraduatoriT(codiceGraduatoria, null, null);
	List<GraduatorietComHelper> graduatorietcomList = graduatorietcomService.findGraduatorietComHelperByGraduatoriT(codiceGraduatoria, null,
		null);
	ModelMap model = new ModelMap(graduatorietcomList);
	boolean export = createJMesaExport(request, response, graduatorietcomList);
	if (export) {
	    return null;
	}
	model.addAttribute("graduatorietcomList", graduatorietcomList);
	model.addAttribute("graduatoriet", graduatoriet);
	model.addAttribute("graduatorietcom", new GraduatorietComCommand());
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codicegraduatoriat") Integer codicegraduatoriat, Model model) {

	Graduatoriet graduatoriet = graduatorietService.findById(new PkId(codicegraduatoriat));
	GraduatorietComCommand graduatorietcom = new GraduatorietComCommand();
	GraduatorietCom entity = new GraduatorietCom();
	// Recupero se c'è l'amministrazione di default impostato sulla verticalizzazione PROTOCOLLO_ATTIVO
	Verticalizzazioniparametri amministrazioneprotocollo = verticalizzazioniService.getVerticalizzazioniparametri(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT);
	if (amministrazioneprotocollo != null && amministrazioneprotocollo.getValore() != null) {
	    log.debug("create# Amminstrazione protocollo configuarta");
	    Amministrazioni amministrazione = amministrazioniService.findById(new PkId(Integer.parseInt(amministrazioneprotocollo.getValore())));
	    log.debug("create# Inserisco amministrazione {} ......",
		    new Object[] { amministrazione.getAmministrazione(), amministrazione.getId().getCodice() });
	    entity.setAmministrazioni(amministrazione);
	} else {
	    log.debug("create# Nessuna amministrazione di default impostata tra i parametri del protocollo");
	}
	entity.setGraduatoriet(graduatoriet);
	graduatorietcom.setEntity(entity);
	fixRenderEntityProperty(graduatorietcom.getEntity());
	model.addAttribute("graduatorietcom", graduatorietcom);
	model.addAttribute("graduatoriet", graduatoriet);
	setPageAttributes(model);
	return "graduatorietcom/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(graduatorietcom.getEntity());
	GraduatorietCom entity = graduatorietcom.getEntity();
	try {
	    graduatoriedComManagerService.insertComunicazioni(entity, graduatorietcom.getSchedaDinamicaFilter());
	} catch (Exception e) {
	    // Evita che quando arrivata l'eccezione "UnexpectedRollbackException" non da errore sul form,
	    // ma riporta "aggiornamento eseguito". L'eccezione è stata gestita nel service
	    if (e instanceof UnexpectedRollbackException) {
		return "redirect:view.htm?codice=" + graduatorietcom.getEntity().getId().getCodice() + "&status_msg=01";
	    }
	    copyErrorsToBindingResult(result, graduatorietcom.getEntity(), true, e);
	    fixRenderEntityProperty(graduatorietcom.getEntity());
	    return "graduatorietcom/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + graduatorietcom.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	GraduatorietComCommand graduatorietcom = new GraduatorietComCommand();
	PkId id = new PkId(codice);
	GraduatorietCom entity = graduatorietcomService.findById(id);
	//List<GraduatoriedComDTO> graduatoriedComDTOs = graduatoriedComService.findGraduatoriedComDTOByGraduatoriatCom(entity.getId().getCodice());
	//	List<GraduatoriedComDTO> graduatoriedComDTOs = graduatoriedComService.findGraduatoriedComDTOByGraduatoriatComWithEvent(entity.getId()
	//		.getCodice(), null, null);
	fixRenderEntityProperty(entity);
	graduatorietcom.setEntity(entity);
	// Controllo se il protocollo è attivo
	graduatorietcom.setMessageProtocolloNonAttivo(chekProtocolloIsAttivo().toString());
	model.addAttribute("graduatorietcom", graduatorietcom);
	//	model.addAttribute("graduatoriedComDTOs", graduatoriedComDTOs);
	////////////////////// GENERAZIONE TABLE /////////////////////////////////////////
	boolean isMettiallafirma = BooleanUtils.isTrue(entity.getFlagMettiallafirma());
	GenerateTable<GraduatoriedComDTO> GraduatoriedComDTOTable = new GraduatoriedComTable(entity.getId().getCodice(), isMettiallafirma,
		graduatoriedComService, documentiDaFirmareService);
	String htmlTable = GraduatoriedComDTOTable.createJMesaList(request, response, "label.lista_graduatoriedcom.title", "graduatoriedcom_id",
		false);
	if (htmlTable == null) {
	    return null;
	}
	model.addAttribute("htmltable", htmlTable);
	String listaFirmatariContent = graduatorietcomService.findListaFirmatariToHtml(codice);
	model.addAttribute("listaFirmatariContent", listaFirmatariContent);
	setPageAttributes(model);
	return "graduatorietcom/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(graduatorietcom.getEntity());
	try {
	    graduatorietcomService.update(graduatorietcom.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, graduatorietcom.getEntity(), true, e);
	    fixRenderEntityProperty(graduatorietcom.getEntity());
	    return "graduatorietcom/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + graduatorietcom.getEntity().getId().getCodice() + "&status_msg=02";
    }

    // 
    @RequestMapping
    public String delete(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	GraduatorietCom objToDelete = graduatorietcomService.findById(new PkId(codice));
	try {
	    graduatorietcomService.delete(objToDelete);
	} catch (Exception e) {
	    e.printStackTrace();
	    copyErrorsToFlashMessages(graduatorietcom.getEntity(), true, "", e);
	    return "redirect:view.htm?codice=" + objToDelete.getId().getCodice();
	}
	//	status.setComplete();
	return "redirect:list.htm?codiceGraduatoria=" + objToDelete.getGraduatoriet().getId().getCodice();
    }

    @RequestMapping
    public String deleteGraduatoriadcom(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	GraduatoriedCom objToDelete = graduatoriedComService.findById(new PkId(codice));
	try {
	    graduatoriedComService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(graduatorietcom.getEntity(), true, "", e);
	    return "redirect:view.htm?codice=" + objToDelete.getGraduatorietCom().getId().getCodice();
	}
	//	status.setComplete();
	return "redirect:view.htm?codice=" + objToDelete.getGraduatorietCom().getId().getCodice();
    }

    @RequestMapping
    public String elaboraGraduatoriadcom(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	GraduatoriedCom graduatoriedCom = graduatoriedComService.findById(new PkId(codice));
	try {
	    graduatoriedComManagerService.elaboroGraduatoriedCom(graduatoriedCom);
	} catch (Exception e) {
	    // Evita che quando arrivata l'eccezione "UnexpectedRollbackException" non da errore sul form,
	    // ma riporta "aggiornamento eseguito". L'eccezione è stata gestita nel service
	    if (e instanceof UnexpectedRollbackException) {
		return "redirect:view.htm?codice=" + graduatoriedCom.getGraduatorietCom().getId().getCodice() + "&status_msg=02";
	    }
	    copyErrorsToBindingResult(result, graduatorietcom.getEntity(), true, e);
	    fixRenderEntityProperty(graduatorietcom.getEntity());
	    return "graduatorietcom/form";
	}
	//	status.setComplete();
	return "redirect:view.htm?codice=" + graduatoriedCom.getGraduatorietCom().getId().getCodice();
    }

    @RequestMapping
    public String elaboraTutteGraduatoriadcom(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	GraduatorietCom graduatorietCom = graduatorietcomService.findById(new PkId(codice));
	Set<GraduatoriedCom> list = graduatorietCom.getGraduatoriedComs();
	try {
	    for (GraduatoriedCom graduatoriedCom : list) {
		graduatoriedComManagerService.elaboroGraduatoriedCom(graduatoriedCom);
	    }
	} catch (Exception e) {
	    // Evita che quando arrivata l'eccezione "UnexpectedRollbackException" non da errore sul form,
	    // ma riporta "aggiornamento eseguito". L'eccezione è stata gestita nel service
	    if (e instanceof UnexpectedRollbackException) {
		return "redirect:view.htm?codice=" + graduatorietCom.getId().getCodice() + "&status_msg=02";
	    }
	    copyErrorsToBindingResult(result, graduatorietcom.getEntity(), true, e);
	    fixRenderEntityProperty(graduatorietcom.getEntity());
	    return "graduatorietcom/form";
	}
	//	status.setComplete();
	return "redirect:view.htm?codice=" + graduatorietCom.getId().getCodice();
    }

    @RequestMapping
    public String changeScheda(@ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom, HttpServletRequest request) {

	SchedaDinamicaFilter sf = graduatorietcom.getSchedaDinamicaFilter();
	boolean azzeraRighe = false;
	if (sf.getScheda() != null) {
	    if (sf.getScheda().getId() != null) {
		if (sf.getScheda().getId().getCodice() != null) {
		    Dyn2Modellit scheda = dyn2ModellitService.findById(new PkId(sf.getScheda().getId().getCodice()));
		    List<Dyn2Campi> d2cs = dyn2CampiService.findByDescrizioneAndSoftwareAndModello("", sf.getScheda().getId().getCodice(), null);
		    sf.setListaCampiModello(d2cs);
		    sf.setScheda(scheda);
		} else {
		    sf.setScheda(new Dyn2Modellit());
		    azzeraRighe = true;
		}
	    } else {
		sf.getScheda().setId(new PkId());
	    }
	} else {
	    sf.setScheda(new Dyn2Modellit());
	    azzeraRighe = true;
	}
	List<SchedaDinamicaRigheFilter> righe = new ArrayList<SchedaDinamicaRigheFilter>();
	if (!azzeraRighe) {
	    SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
	    righe.add(0, riga);
	}
	sf.setRighe(righe);
	clearAndPopulateModel(graduatorietcom);
	return "graduatorietcom/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxRenderCampo(@ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom,
	    @RequestParam(value = "codiceCampo") Integer codiceCampo, @RequestParam(value = "idx") Integer idx,
	    @RequestParam(value = "nomeElementoValore") String nomeElementoValore, @RequestParam(value = "valore") String valore,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	String campo = createCampoValore(codiceCampo, valore, valore, nomeElementoValore);
	// campo = campo.replaceAll("name=\"[A-Z_a-z_0-9]*\"", "name=\"" + nomeElementoValore + "\"");
	// campo = campo.replaceAll("id=\"[A-Z_a-z_0-9]*\"", "id=\"" + nomeElementoValore + "_id\"");
	byte[] out = campo.getBytes("UTF-8");
	response.getOutputStream().write(out);
    }

    @RequestMapping
    public String addCampoAScheda(Model model, @ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	SchedaDinamicaFilter sf = graduatorietcom.getSchedaDinamicaFilter();
	if (sf.getScheda() != null) {
	    if (sf.getScheda().getId() != null) {
		if (sf.getScheda().getId().getCodice() != null) {
		    SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
		    sf.getRighe().add(riga);
		}
	    }
	}
	clearAndPopulateModel(graduatorietcom);
	return "graduatorietcom/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String removeCampoScheda(@RequestParam(value = "idx", required = true) Integer idx, Model model,
	    @ModelAttribute("graduatorietcom") GraduatorietComCommand graduatorietcom, HttpServletRequest request) {

	// §§§BEGIN§§§
	SchedaDinamicaFilter sf = graduatorietcom.getSchedaDinamicaFilter();
	if (sf != null) {
	    List<SchedaDinamicaRigheFilter> righe = sf.getRighe();
	    if (righe != null) {
		for (int i = 0; i < righe.size(); i++) {
		    if (i == idx.intValue()) {
			righe.remove(i);
			break;
		    }
		}
	    }
	    List<SchedaDinamicaRigheFilter> ricalcoloIndici = new ArrayList<SchedaDinamicaRigheFilter>(righe.size());
	    int i = 0;
	    for (SchedaDinamicaRigheFilter schedaDinamicaRigheFilter : righe) {
		ricalcoloIndici.add(i, schedaDinamicaRigheFilter);
		i++;
	    }
	    sf.setRighe(ricalcoloIndici);
	}
	clearAndPopulateModel(graduatorietcom);
	return "graduatorietcom/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxReportDocDaFirmare(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	GraduatoriedCom rec = graduatoriedComService.findById(new PkId(codice));
	if (rec == null) {
	    response.getWriter().write("Non è stato trovato nessun record. Controllare il dato " + codice);
	    return;
	}
	Integer codiceOggetto = null;
	if (rec.getOggetti() != null) {
	    if (rec.getOggetti().getId() != null) {
		if (rec.getOggetti().getId().getCodice() != null) {
		    codiceOggetto = rec.getOggetti().getId().getCodice();
		}
	    }
	}
	if (codiceOggetto == null) {
	    response.getWriter().write("Non è stato trovato nessun allegato da firmare. Codice oggetto è nullo");
	    return;
	}
	String result = documentiDaFirmareService.findReportHTMLOggettoDaFirmare(codiceOggetto);
	response.getWriter().write(result);
	return;
    }

    @RequestMapping
    public void ajaxCheckProtocolloAttivo(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	response.setContentType("text/plain");
	response.getWriter().write(chekProtocolloIsAttivo().toString());
    }

    private StringBuffer chekProtocolloIsAttivo() {

	Verticalizzazioniparametri tipoProtocollo = verticalizzazioniService.getVerticalizzazioniparametri(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO);
	StringBuffer sbuf = new StringBuffer("");
	if (tipoProtocollo != null && tipoProtocollo.getValore() != null) {
	    log.debug("ajaxCheckProtocolloAttivo# Protocollo configuarto");
	} else {
	    String warn = "Attenzione non sarà possibile effettuare la protocollazione automatica dei movimenti, in quanto non è configutato nessun tipo di protocollazione";
	    log.debug("ajaxCheckProtocolloAttivo# Protocollo non configuarto, non è possibile getsire la protocllazione automatica dei movimenti");
	    sbuf.append("<div id=\"warning_msg\" class=\"warning_header\" ><div>&nbsp;</div>");
	    sbuf.append("<div>").append(warn).append("</div>");
	}
	return sbuf;
    }

    private void clearAndPopulateModel(GraduatorietComCommand graduatorietcom) {

	if (EntityUtils.getNestedProperty(graduatorietcom.getEntity().getMailtipo(), "id.codice") != null) {
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(graduatorietcom.getEntity().getMailtipo().getId().getCodice()));
	    graduatorietcom.getEntity().setMailtipo(mailtipo);
	} else {
	    graduatorietcom.getEntity().setMailtipo(new Mailtipo());
	}
	if (EntityUtils.getNestedProperty(graduatorietcom.getEntity().getLetteretipo(), "id.codice") != null) {
	    Letteretipo letteretipo = letteretipoService.findById(new PkId(graduatorietcom.getEntity().getLetteretipo().getId().getCodice()));
	    graduatorietcom.getEntity().setLetteretipo(letteretipo);
	} else {
	    graduatorietcom.getEntity().setLetteretipo(new Letteretipo());
	}
	if (EntityUtils.getNestedProperty(graduatorietcom.getEntity().getAmministrazioni(), "id.codice") != null) {
	    Amministrazioni amministrazioni = amministrazioniService
		    .findById(new PkId(graduatorietcom.getEntity().getAmministrazioni().getId().getCodice()));
	    graduatorietcom.getEntity().setAmministrazioni(amministrazioni);
	} else {
	    graduatorietcom.getEntity().setAmministrazioni(new Amministrazioni());
	}
	if (EntityUtils.getNestedProperty(graduatorietcom.getEntity().getTipimovimento(), "id.tipomovimento") != null) {
	    Tipimovimento tipimovimento = tipiMovimentoService
		    .findById(new TipimovimentoId(graduatorietcom.getEntity().getTipimovimento().getId().getTipomovimento()));
	    graduatorietcom.getEntity().setTipimovimento(tipimovimento);
	} else {
	    graduatorietcom.getEntity().setTipimovimento(new Tipimovimento());
	}
    }

    private String createCampoValore(Integer codiceCampo, String valore, String valoreDecodificato, String nomeElementoValore) {

	StringBuffer buffer = new StringBuffer();
	// faccio il render del campo dinamico
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(codiceCampo));
	ModellidinamiciCampoHelper campo = new ModellidinamiciCampoHelper();
	campo.setDyn2Campi(dyn2Campi);
	campo.setValore(valore);
	campo.setValoreDecodificato(valoreDecodificato);
	String campoHtml = this.renderCampo(TipoControlloEnum.valueOf(dyn2Campi.getTipodato()), dyn2Campi, campo, nomeElementoValore);
	buffer.append(campoHtml);
	return buffer.toString();
    }

    private String renderCampo(TipoControlloEnum tipodato, Dyn2Campi d2c, ModellidinamiciCampoHelper campo, String nomeElementoValore) {

	Set<Dyn2Campiproprieta> proprietaCampo = d2c.getDyn2Campiproprietas();
	switch (tipodato) {
	    case Lista:
	    case MultiLista:
		return renderLista(d2c, proprietaCampo, false, campo, nomeElementoValore);
	    case Checkbox:
		return renderCheckBox(d2c, proprietaCampo, campo, nomeElementoValore);
	    case Data:
		return renderData(d2c, proprietaCampo, campo, nomeElementoValore);
	    case NumericoDouble:
		return renderNumerico(d2c, proprietaCampo, true, campo, nomeElementoValore);
	    case NumericoIntero:
		return renderNumerico(d2c, proprietaCampo, false, campo, nomeElementoValore);
	    default:
		return renderTesto(d2c, proprietaCampo, campo, nomeElementoValore);
	}
    }

    private String renderLista(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, boolean isMultiSelect, ModellidinamiciCampoHelper campo,
	    String nomeElementoValore) {

	String valoriLista = getProprietaCampo(proprietaCampo, ProprietaCampi.ElementiLista, "");
	// String obbligatorio = getProprietaCampo(proprietaCampo, ProprietaCampi.Obbligatorio, "");
	String[] valori = null;
	if (StringUtils.isNotBlank(valoriLista)) {
	    valori = valoriLista.split(";");
	}
	CustomHtmlBuilder result = new CustomHtmlBuilder();
	String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	result.select().name(elementName).id(elementId).append(validationFX(elementId, false));
	List<String> valoriSelezionati = new ArrayList<String>();
	String valoreSelezionato = StringUtils.defaultIfEmpty(campo.getValore(), "");
	if (isMultiSelect) {
	    result.append(" multiple=\"multiple\"");
	    String[] valoriSelezionatiAr = null;
	    if (StringUtils.isNotBlank(valoriLista)) {
		valoriSelezionatiAr = valoreSelezionato.split(";");
	    }
	    if (valoriSelezionatiAr != null && valoriSelezionatiAr.length > 0) {
		for (String val : valoriSelezionatiAr) {
		    if (StringUtils.isNotBlank(val)) {
			valoriSelezionati.add(val);
		    }
		}
	    }
	    if (valori != null) {
		result.size(String.valueOf(valori.length));
	    }
	} else {
	    valoriSelezionati.add(valoreSelezionato);
	}
	String errMessage = "";//validaLista(d2c, proprietaCampo, isMultiSelect, campo.getValore());
	result.close();
	if (!isMultiSelect) {
	    result.option().value("").close().append("").optionEnd();
	}
	if (valori != null) {
	    for (String val : valori) {
		if (StringUtils.isNotBlank(val)) {
		    result.newline();
		    result.option().value(val);
		    if (valoriSelezionati != null) {
			if (valoriSelezionati.contains(StringUtils.defaultIfEmpty(val, "").trim())) {
			    result.selected();
			}
		    }
		    result.close().append(val).optionEnd();
		}
	    }
	}
	result.selectEnd().append(spanErrors(elementId, elementName, errMessage));
	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    StringBuffer resultBuffer = new StringBuffer();
	    for (String val : valoriSelezionati) {
		resultBuffer.append(val);
		resultBuffer.append(", ");
	    }
	    return getPrintValue(resultBuffer.toString());
	} else {
	    return result.toString();
	}
    }

    private String renderTesto(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, ModellidinamiciCampoHelper campo, String nomeElementoValore) {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    return getPrintValue(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String maxLength = getProprietaCampo(proprietaCampo, ProprietaCampi.MaxLength, "");
	    String readonly = getProprietaCampo(proprietaCampo, ProprietaCampi.ReadOnly, "");
	    String size = getProprietaCampo(proprietaCampo, ProprietaCampi.Columns, "40");
	    String textarea = getProprietaCampo(proprietaCampo, ProprietaCampi.MultiLine, "");
	    String rows = getProprietaCampo(proprietaCampo, ProprietaCampi.Rows, "");
	    if (textarea.equalsIgnoreCase("true")) {
		result.textarea();
		if (StringUtils.isNotBlank(size)) {
		    result.cols(size);
		}
		if (StringUtils.isNotBlank(rows)) {
		    result.rows(rows);
		}
	    } else {
		result.input().type("text");
		if (StringUtils.isNotBlank(size)) {
		    result.size(size);
		}
	    }
	    String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	    String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	    result.id(elementId).name(elementName).append(validationFX(elementId, false));
	    if (StringUtils.isNotBlank(maxLength)) {
		result.maxlength(maxLength);
	    }
	    if (StringUtils.defaultIfEmpty(readonly, "false").equalsIgnoreCase("true")) {
		result.readonly();
	    }
	    if (textarea.equalsIgnoreCase("true")) {
		result.close();
		result.append(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
		result.textareaEnd();
	    } else {
		result.value(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
		result.end();
	    }
	    String errMessage = "";//validaTesto(d2c, proprietaCampo, campo.getValoreDecodificato());
	    result.append(spanErrors(elementId, elementName, errMessage));
	    return result.toString();
	}
    }

    private String renderNumerico(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, boolean isDouble, ModellidinamiciCampoHelper campo,
	    String nomeElementoValore) {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    return getPrintValue(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String maxLength = getProprietaCampo(proprietaCampo, ProprietaCampi.MaxLength, "");
	    String readonly = getProprietaCampo(proprietaCampo, ProprietaCampi.ReadOnly, "");
	    String size = getProprietaCampo(proprietaCampo, ProprietaCampi.Columns, "10");
	    String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	    String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	    result.input().style("text-align: right;").type("text").id(elementId).name(elementName).append(validationFX(elementId, false));
	    if (StringUtils.isNotBlank(size)) {
		result.size(size);
	    }
	    if (StringUtils.isNotBlank(maxLength)) {
		result.maxlength(maxLength);
	    }
	    if (StringUtils.defaultIfEmpty(readonly, "false").equalsIgnoreCase("true")) {
		result.readonly();
	    }
	    result.value(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
	    String errMessg = "";//validaNumerico(d2c, proprietaCampo, isDouble, campo.getValoreDecodificato());
	    result.end().append(spanErrors(elementId, elementName, errMessg));
	    return result.toString();
	}
    }

    protected String renderData(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, ModellidinamiciCampoHelper campo, String nomeElementoValore) {

	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    String result = getPrintValue(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), ""));
	    return result;
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	    String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	    String readonly = getProprietaCampo(proprietaCampo, ProprietaCampi.ReadOnly, "");
	    result.input().type("text").id(elementId).onblur("isValidDate(this,true);").name(elementName).size("10");
	    if (readonly.equalsIgnoreCase("true")) {
		result.readonly();
	    }
	    result.value(StringUtils.defaultIfEmpty(campo.getValoreDecodificato(), "")).end();
	    // String calName = "CAL_" + elementId;
	    // result.a().append(" href=\"\" ").id(calName).title("Calendario").close();
	    // result.img().alt("Calendario").src("../images/cal.gif").close().aEnd();
	    String errMessg = "";// validaData(d2c, proprietaCampo, campo.getValoreDecodificato());
	    //result.script().type("text/javascript").close().append(calendarString(elementId, calName)).scriptEnd()
	    result.append(spanErrors(elementId, elementName, errMessg));
	    return result.toString();
	}
    }

    private String renderCheckBox(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, ModellidinamiciCampoHelper campo,
	    String nomeElementoValore) {

	String valoreTrue = getProprietaCampo(proprietaCampo, ProprietaCampi.ValoreTrue, "1");
	String valoreFalse = getProprietaCampo(proprietaCampo, ProprietaCampi.ValoreFalse, "0");
	if (BooleanUtils.isTrue(SchedeDinamicheTL.getRenderForPrint())) {
	    String result = getPrintValue("[ ]");
	    if (campo != null) {
		if (StringUtils.defaultIfEmpty(campo.getValore(), "").equalsIgnoreCase(valoreTrue)) {
		    result = getPrintValue("[x]");
		}
	    }
	    return result;
	} else {
	    CustomHtmlBuilder result = new CustomHtmlBuilder();
	    String elementId = getNameOrIdFromCampo(nomeElementoValore, true);
	    String elementName = getNameOrIdFromCampo(nomeElementoValore, false);
	    String errMessage = "";
	    result.input().type("checkbox").value(valoreTrue).name("TMP_" + elementName).id("TMP_" + elementId);
	    String valoreDefault = valoreFalse;
	    if (campo != null) {
		if (StringUtils.defaultIfEmpty(campo.getValore(), "").equalsIgnoreCase(valoreTrue)) {
		    result.checked();
		    valoreDefault = valoreTrue;
		}
		errMessage = validaCheckBox(d2c, proprietaCampo, campo.getValore());
	    }
	    result.onclick(
		    "if(this.checked){$('" + elementId + "').value='" + valoreTrue + "';}else{$('" + elementId + "').value='" + valoreFalse + "';}")
		    .end().append(spanErrors(elementId, elementName, errMessage));
	    result.input().type("hidden").name(elementName).id(elementId).value(valoreDefault).end();
	    return result.toString();
	}
    }

    private String validationFX(String elementId, boolean isCheckbox) {

	return "";//isCheckbox ? " onclick=\"validaCampo(this);\" " : " onchange=\"validaCampo(this);\" ";
    }

    protected String calendarString(String id, String name) {

	return "jQuery(document).ready(function(){Calendar.setup({inputField     :    \"" + id + "\",    button         :    \"" + name + "\" });});";
    }

    protected String getProprietaCampo(Set<Dyn2Campiproprieta> proprietaCampo, ProprietaCampi proprieta, String defaultValue) {

	String result = StringUtils.defaultIfEmpty(defaultValue, "");
	for (Dyn2Campiproprieta dyn2Campiproprieta : proprietaCampo) {
	    if (dyn2Campiproprieta.getId().getProprieta().equalsIgnoreCase(proprieta.name())) {
		result = dyn2Campiproprieta.getValore();
	    }
	}
	return result;
    }

    protected String getPrintValue(String value) {

	return "<b>" + value + "</b>";
    }

    private String validaCheckBox(Dyn2Campi d2c, Set<Dyn2Campiproprieta> proprietaCampo, String valore) {

	String obbligatorio = getProprietaCampo(proprietaCampo, ProprietaCampi.Obbligatorio, "false");
	String errMessg = "";
	if (StringUtils.isNotBlank(obbligatorio)) {
	    if (obbligatorio.equalsIgnoreCase("true")) {
		if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("0")) {
		    errMessg = "È obbligatorio spuntare la checkbox." /*TODO METTERE LABEL*/;
		}
	    }
	}
	return errMessg;
    }

    public String getNameOrIdFromCampo(String nomeCampo, boolean isId) {

	String result = nomeCampo;
	if (isId) {
	    result = result.replaceAll("\\.", "_").replaceAll("\\[", "").replaceAll("\\]", "") + "_id";
	} else {
	    return nomeCampo;
	}
	return result;
    }

    protected Object spanErrors(String elementId, String elementName, String errMessage) {

	String display = "display: none;";
	if (StringUtils.isNotBlank(errMessage)) {
	    display = "";
	}
	return "<span id=\"" +
		elementId +
		"_ERRORS\" style=\"clear: left;" +
		display +
		"\" class=\"error\">" +
		StringUtils.defaultIfEmpty(errMessage, "") +
		"</span>";
    }

    @Override
    protected void fixMergeEntityProperty(GraduatorietCom entity) {

    }

    @Override
    protected void fixRenderEntityProperty(GraduatorietCom entity) {

	if (EntityUtils.getNestedProperty(entity, "graduatoriet") == null) {
	    entity.setGraduatoriet(new Graduatoriet());
	}
	if (EntityUtils.getNestedProperty(entity, "tipimovimento") == null) {
	    entity.setTipimovimento(new Tipimovimento());
	}
	if (EntityUtils.getNestedProperty(entity, "amministrazioni") == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (EntityUtils.getNestedProperty(entity, "mailtipo") == null) {
	    entity.setMailtipo(new Mailtipo());
	}
	if (EntityUtils.getNestedProperty(entity, "tipimovimento") == null) {
	    entity.setTipimovimento(new Tipimovimento());
	}
	if (EntityUtils.getNestedProperty(entity, "letteretipo") == null) {
	    entity.setLetteretipo(new Letteretipo());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
