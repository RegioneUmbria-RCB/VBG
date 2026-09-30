/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Movimentimailallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MovimentimailCommand;
import it.gruppoinit.pal.gp.core.domain.web.SessionDetails;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.MovimentimailallegatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes({ "movimentimail", "movimentiallegatis", "mailtipi" })
public class MovimentimailController extends BaseController<Movimentimail> {

    private static final Logger log = LoggerFactory.getLogger(MovimentimailController.class);
    @Autowired
    private MovimentimailService movimentimailService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IstanzeprocedimentiService istanzeprocedimentiService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private MovimentimailallegatiService movimentimailallegatiService;
    @Autowired
    private MovimentiNoSecurityService movimentiNoSecurityService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    @Autowired
    private DocumentiHelperService documentiHelperService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private MovimentiZipLogicoService movimentiZipLogicoService;
    @Autowired
    private ResponsabiliService responsabiliService;

    @RequestMapping
    public String cancellaBozzaMovimenti(Model model, @RequestParam("entity.id.codice") Integer movimentiMailCod,
	    @RequestParam("entity.movimento.id.codice") Integer codiceMovimento, HttpServletRequest request, HttpServletResponse response) {

	Movimenti m = movimentiNoSecurityService.findById(new PkId(codiceMovimento));
	Integer codiceIstanza = m.getIstanza().getId().getCodice();
	PkId pkId = new PkId(movimentiMailCod);
	Movimentimail entity = movimentimailService.findById(pkId);
	movimentimailService.delete(entity);
	return "redirect:list.htm?codicemovimento=" + codiceMovimento + "&codiceistanza=" + codiceIstanza;
    }

    @RequestMapping
    public String salvaBozzaMovimenti(Model model, @RequestParam("entity.movimento.id.codice") Integer codiceMovimento, HttpServletRequest request,
	    HttpServletResponse response) {

	Movimenti m = movimentiNoSecurityService.findById(new PkId(codiceMovimento));
	Movimentimail movimentimail = createAndSaveMovimentiMail(request.getParameterMap());
	String salvaBozza = "OK";
	if (movimentimail == null) {
	    salvaBozza = "KO";
	}
	Integer codiceistanza = m.getIstanza().getId().getCodice();
	Integer codicemovimento = m.getId().getCodice();
	String statusMsg = salvaBozza.equals("OK") ? "01" : "03";
	return "redirect:createMail.htm?codicemovimento=" +
		codicemovimento +
		"&codiceistanza=" +
		codiceistanza +
		"&status_msg=" +
		statusMsg +
		"&idMov=" +
		movimentimail.getId().getCodice();
    }

    private Movimentimail createAndSaveMovimentiMail(Map<?, ?> parameterMap) {

	Movimentimail entity = null;
	try {
	    String codiceMovimento = ((String[]) parameterMap.get("entity.movimento.id.codice"))[0].trim().isEmpty() ? null
		    : ((String[]) parameterMap.get("entity.movimento.id.codice"))[0];
	    if (codiceMovimento != null) {
		String codicemovimentimail = ((String[]) parameterMap.get("entity.id.codice"))[0].trim().isEmpty() ? null
			: ((String[]) parameterMap.get("entity.id.codice"))[0];
		if (!StringUtils.isEmpty(codicemovimentimail)) {
		    Integer codMov = Integer.parseInt(codicemovimentimail);
		    entity = movimentimailService.findById(new PkId(codMov));
		} else {
		    entity = new Movimentimail();
		}
		Movimenti movimento = movimentiService.findById(new PkId(Integer.parseInt(codiceMovimento)));
		//		List<Movimentimail> movimentiMailList = movimentimailService.findByMovimento(movimento);
		entity.setCorpo(
			((String[]) parameterMap.get("entity.corpo"))[0].trim().isEmpty() ? null : ((String[]) parameterMap.get("entity.corpo"))[0]);
		entity.setOggetto(((String[]) parameterMap.get("entity.oggetto"))[0]);
		entity.setDestinatariobcc(((String[]) parameterMap.get("entity.destinatariobcc"))[0].trim().isEmpty() ? null
			: ((String[]) parameterMap.get("entity.destinatariobcc"))[0]);
		entity.setDestinatariocc(((String[]) parameterMap.get("entity.destinatariocc"))[0].trim().isEmpty() ? null
			: ((String[]) parameterMap.get("entity.destinatariocc"))[0]);
		entity.setDestinatario(((String[]) parameterMap.get("entity.destinatario"))[0].trim().isEmpty() ? null
			: ((String[]) parameterMap.get("entity.destinatario"))[0]);
		movimento = movimentiNoSecurityService
			.findById(new PkId(Integer.parseInt(((String[]) parameterMap.get("entity.movimento.id.codice"))[0])));
		entity.setMovimento(movimento);
		entity.setBozza(true);
		MovimentimailCommand movimentimail = new MovimentimailCommand();
		movimentimail.setFlgInvialinkallmail(movimento.getTipomovimento().getFlgInvialinkallmail());
		movimentimail.setLetteraTipoAllegati(movimento.getTipomovimento().getLetteraTipoAllegati());
		entity.setMittente(((String[]) parameterMap.get("entity.mittente"))[0].trim().isEmpty() ? null
			: ((String[]) parameterMap.get("entity.mittente"))[0]);
		//        	String[] codiceComuni = this.popolaArrayCodiciComune();
		List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
			ORMHelper.getIdcomuneAlias(), false);
		if (listMailConfig != null) {
		    movimentimail.setMailConfig(listMailConfig.get(0));
		}
		//		         	List<MailConfig> listMailConfig2 = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		//		         		codiceComuni);
		entity.setMovimento(movimento);
		movimentimailService.insert(entity);
		//		}
	    }
	} catch (Exception e) {
	    log.error("Errore " + e.getMessage(), e);
	    return null;
	}
	return entity;
    }

    @RequestMapping
    public ModelMap list(@RequestParam("codicemovimento") String codicemovimento, @RequestParam("codiceistanza") String codiceistanza,
	    HttpServletRequest request, HttpServletResponse response) {

	List<Movimentimail> movimentimailList = new ArrayList<Movimentimail>();
	ModelMap model = new ModelMap(movimentimailList);
	if (codicemovimento != null && !codicemovimento.equals("")) {
	    //Movimenti movimento = movimentiService.findById(new PkId(Integer.parseInt(codicemovimento)));
	    Movimenti movimento = movimentiNoSecurityService.findById(new PkId(Integer.parseInt(codicemovimento)));
	    movimentimailList = movimentimailService.findByMovimento(movimento);
	    model.addAttribute("movimento", movimento);
	} else if (codiceistanza != null && !codiceistanza.equals("")) {
	    Istanze istanze = istanzeService.findById(new PkId(Integer.parseInt(codiceistanza)));
	    movimentimailList = movimentimailService.findByIstanza(istanze);
	    model.addAttribute("istanza", istanze);
	}
	boolean export = createJMesaExport(request, response, movimentimailList);
	if (export)
	    return null;
	model.addAttribute("movimentimailList", movimentimailList);
	model.addAttribute("codicemovimento", codicemovimento);
	model.addAttribute("codiceistanza", codiceistanza);
	return model;
    }

    @RequestMapping
    public String createMail(Model model, @RequestParam("codicemovimento") Integer codicemovimento,
	    @RequestParam("codiceistanza") Integer codiceistanza, HttpServletRequest request, HttpServletResponse response) {

	// ATTENZIONE!!! QUANDO SI MODIFICA QUESTO METODO LA STESSA MODIFICA VA PORTATA IN 
	// 		 PROTOCOLLAZIONECONTROLLER.CREATEMAIL
	String codiceIdMov = request.getParameter("idMov");
	MovimentimailCommand movimentimail = new MovimentimailCommand();
	Movimenti movimento = movimentiService.findById(new PkId(codicemovimento));
	movimentimail.setFlgInvialinkallmail(movimento.getTipomovimento().getFlgInvialinkallmail());
	movimentimail.setLetteraTipoAllegati(movimento.getTipomovimento().getLetteraTipoAllegati());
	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	MailConfig mailConfigDefault = mailConfigService.findPrepopolaInvioEmailBySoftwareAndCodiceComune(ORMHelper.getSoftware(),
		istanza.getComune().getCodicecomune(), false);
	movimentimail.setMailConfig(mailConfigDefault);
	String[] codiceComuni = this.popolaArrayCodiciComune();
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);
	Movimentimail entity = new Movimentimail();
	List<Movimentimail> movimentiMailList = movimentimailService.findByMovimento(movimento);
	if (movimentiMailList != null && !movimentiMailList.isEmpty()) {
	    for (Movimentimail candidate : movimentiMailList) {
		if (candidate.getBozza()
			&& (!StringUtils.isEmpty(codiceIdMov) && candidate.getId().getCodice().equals(Integer.parseInt(codiceIdMov)))) {
		    entity = candidate;
		    break;
		}
	    }
	}
	boolean isDpr160 = false;
	Mailtipo mailtipo = null;
	Tipimovimento tipimovimento = movimento.getTipomovimento();
	// seleziona la mail tipo configurata
	if (EntityUtils.getNestedProperty(tipimovimento.getMailtipoByFkTipimovricTelMailtipo(), "id.codice") != null) {
	    isDpr160 = true;
	    mailtipo = tipimovimento.getMailtipoByFkTipimovricTelMailtipo();
	} else if (EntityUtils.getNestedProperty(tipimovimento.getMailtipoByFkTipimovcomTelMailtipo(), "id.codice") != null) {
	    isDpr160 = true;
	    mailtipo = tipimovimento.getMailtipoByFkTipimovcomTelMailtipo();
	}
	if (entity.getId().getCodice() == null) {
	    PkId pkId = new PkId();
	    entity.setId(pkId);
	    entity.setMovimento(movimento);
	    if (EntityUtils.getNestedProperty(mailConfigDefault, "id.codice") != null) {
		entity.setMittente(mailConfigDefault.getSenderaddress());
	    }
	    entity.setMailConfig(mailConfigDefault);
	    if (isDpr160) {
		// prepopola destinatario con PEC dell'impresa richiedente e PEC del richiedente
		// TODO alert se non trovo PEC configurata
		String pecDestinatari = "";
		if (istanza.getTitolarelegale() != null && StringUtils.isNotBlank(istanza.getTitolarelegale().getPec())) {
		    String pecImpresa = istanza.getTitolarelegale().getPec();
		    pecDestinatari += pecImpresa + ";";
		}
		if (istanza.getRichiedente() != null && StringUtils.isNotBlank(istanza.getRichiedente().getPec())) {
		    String pecRichiedente = istanza.getRichiedente().getPec();
		    pecDestinatari += pecRichiedente;
		}
		entity.setDestinatario(pecDestinatari);
		// prepopola il form della mail con oggetto e corpo della mailtipo
		model.addAttribute("setmailtipo", true);
	    }
	}
	List<Mailtipo> mailtipi = new ArrayList<Mailtipo>();
	if (isDpr160) {
	    mailtipi.add(mailtipo);
	} else {
	    mailtipi = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.MAIL);
	}
	model.addAttribute("mailtipi", mailtipi);
	movimentimail.setEntity(entity);
	model.addAttribute("istanza", istanza);
	model.addAttribute("listMailConfig", listMailConfig);
	//DocumentiHelper documentiHelper = documentiHelperService.findDocumentiInvioMailDaMovimento(movimento.getId().getCodice());
	DocumentiHelper documentiHelper = null;
	documentiHelper = documentiHelperService.findDocumentiInvioMailDaMovimento(movimento.getId().getCodice());
	movimentimail.setDocumentiHelper(documentiHelper);
	fixRenderMovimentiMailCommandProperty(movimentimail);
	model.addAttribute("movimentimail", movimentimail);
	setPageAttributes(model);
	setPageAttributes(model, movimentimail);
	/////////////////// verifica se la verticalizzazione è VERTICALIZZAZIONE_ALLEGATI_PEC /////////////////////////////////
	/////////////////// serve per visualizzare o no i campi necessari per l'utilizzo della funzionalità ////////////////////
	boolean isAttiva = isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC, request);
	if (!isAttiva) {
	    movimentimail.setFlgInvialinkallmail(isAttiva);
	}
	// ATTENZIONE!!! QUANDO SI MODIFICA QUESTO METODO LA STESSA MODIFICA VA PORTATA IN 
	// 		 PROTOCOLLAZIONECONTROLLER.CREATEMAIL
	return "movimentimail/inviomail";
    }

    private List<ChiaveValoreBean<String, List<Movimentiallegati>>> findListaMovimentiAllegati(Istanze istanza, Movimenti movimento) {

	List<ChiaveValoreBean<String, List<Movimentiallegati>>> list = new ArrayList<ChiaveValoreBean<String, List<Movimentiallegati>>>();
	List<Movimenti> movimentis = movimentiService.findEseguitiByIstanza(istanza);
	for (Movimenti movimenti : movimentis) {
	    List<Movimentiallegati> movalls = movimentiallegatiService.findByIstanzaOggetto(istanza.getId().getCodice(),
		    movimenti.getId().getCodice());
	    if (movalls.size() > 0) {
		ChiaveValoreBean<String, List<Movimentiallegati>> bean = new ChiaveValoreBean<String, List<Movimentiallegati>>();
		List<Movimentiallegati> movalls2 = new ArrayList<Movimentiallegati>();
		for (Movimentiallegati movimentiallegati : movalls) {
		    if (movimento.getId().getCodice().equals(movimentiallegati.getMovimento().getId().getCodice())) {
			movimentiallegati.setTransientSegnaPerInvio(true);
		    }
		    movalls2.add(movimentiallegati);
		}
		//		String descrizioneMovimento = "<b>" + movimenti.getTipomovimento().getDescrizioneEstesa() + "</b>";
		//		if (movimenti.getAmministrazioni() != null) {
		//		    descrizioneMovimento += " - " + movimenti.getAmministrazioni().getAmministrazione();
		//		}
		//		if (movimenti.getEndoprocedimento() != null) {
		//		    descrizioneMovimento += " [" + movimenti.getEndoprocedimento().getProcedimento() + "]";
		//		}
		String descrizioneMovimento = movimenti.getDescrizioneMovimento();
		bean.setChiave(descrizioneMovimento);
		bean.setValore(movalls2);
		list.add(bean);
	    }
	}
	return list;
    }

    private List<ChiaveValoreBean<String, List<Istanzeallegati>>> findListaIstanzeallegati(Istanze istanza) {

	List<ChiaveValoreBean<String, List<Istanzeallegati>>> list = new ArrayList<ChiaveValoreBean<String, List<Istanzeallegati>>>();
	List<Istanzeprocedimenti> ips = istanzeprocedimentiService.findByIstanze(istanza);
	for (Istanzeprocedimenti procedimento : ips) {
	    List<Istanzeallegati> ialls = istanzeallegatiService.findByIstanzaAndEndo(istanza.getId().getCodice(),
		    procedimento.getId().getCodiceinventario());
	    if (ialls.size() > 0) {
		boolean almenoUno = false;
		List<Istanzeallegati> ialls2 = new ArrayList<Istanzeallegati>();
		for (Istanzeallegati istanzeallegati : ialls) {
		    if (EntityUtils.getNestedProperty(istanzeallegati.getOggetto(), "id.codice") != null) {
			ialls2.add(istanzeallegati);
			almenoUno = true;
		    }
		}
		if (almenoUno) {
		    ChiaveValoreBean<String, List<Istanzeallegati>> bean = new ChiaveValoreBean<String, List<Istanzeallegati>>();
		    bean.setChiave(procedimento.getDescrizioneAndAmministrazione());
		    bean.setValore(ialls2);
		    list.add(bean);
		}
	    }
	}
	return list;
    }

    @RequestMapping
    public String inviaMail(Model model, @ModelAttribute("movimentimail") MovimentimailCommand movimentimail, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	String esito = null;
	Movimentimail entity = movimentimail.getEntity();
	entity.setDatainvio(new Date());
	Set<Movimentimailallegati> movimentimailallegatis = new HashSet<Movimentimailallegati>();
	movimentimailallegatis = movimentimailallegatiService.findAllegatiMovimentiMailDaInviare(movimentimail.getDocumentiHelper(), entity);
	Integer codiceLettera = null;
	Boolean flgInvialinkallmail = movimentimail.getFlgInvialinkallmail();
	Boolean flgZipLogicolink = movimentimail.getFlgInvioMailZipLogico();
	try {
	    movimentimail.getEntity().setMovimentimailallegatis(movimentimailallegatis);
	    SessionDetails sessionDetails = getSessionDetails(request);
	    ORMHelper.setToken(sessionDetails.getToken());
	    //	    if (EntityUtils.getNestedProperty(entity.getMovimento().getTipomovimento().getLetteraTipoAllegati(), "id.codice") != null) {
	    //		codiceLettera = movimentimail.getLetteraTipoAllegati().getId().getCodice();
	    //	    }
	    if (EntityUtils.getNestedProperty(movimentimail.getLetteraTipoAllegati(), "id.codice") != null) {
		codiceLettera = movimentimail.getLetteraTipoAllegati().getId().getCodice();
	    }
	    if (EntityUtils.getNestedProperty(movimentimail.getMailConfig(), "id.codice") != null) {
		MailConfig mailConfigSelezionata = mailConfigService.findById(new PkId(movimentimail.getMailConfig().getId().getCodice()));
		movimentimail.setMailConfig(mailConfigSelezionata);
		movimentimail.getEntity().setMailConfig(mailConfigSelezionata);
	    }
	    //movimentimailService.sendMail(movimentimail.getEntity(), flgInvialinkallmail, codiceLettera);
	    Movimenti m = movimentiNoSecurityService.findById(new PkId(movimentimail.getEntity().getMovimento().getId().getCodice()));
	    esito = movimentimailService.sendMail2(movimentimail.getEntity(), flgInvialinkallmail, flgZipLogicolink, codiceLettera,
		    movimentimail.getEntity().getMailConfig().getId().getCodice(), m.getIstanza().getComune().getCodicecomune());
	    if (esito != null && esito.equalsIgnoreCase("OK")) {
		entity.setMovimentimailallegatis(new HashSet<Movimentimailallegati>(0));
		movimentimailService.delete(entity);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, movimentimail.getEntity(), true, e);
	    fixRenderEntityProperty(movimentimail.getEntity());
	    setPageAttributes(model);
	    Integer codiceistanza = movimentimail.getEntity().getMovimento().getIstanza().getId().getCodice();
	    Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	    Movimenti movimento = movimentiService.findById(new PkId(movimentimail.getEntity().getMovimento().getId().getCodice()));
	    //riporto i valori settati dell'utente, per il campo 
	    // movimentimail.setFlgInvialinkallmail(movimento.getTipomovimento().getFlgInvialinkallmail());
	    movimentimail.setLetteraTipoAllegati(movimento.getTipomovimento().getLetteraTipoAllegati());
	    //	    movimentimail.setFlgInvialinkallmail(flgInvialinkallmail);
	    if (codiceLettera != null) {
		Letteretipo letteretipoAllegati = letteretipoService.findById(new PkId(codiceLettera));
		movimentimail.setLetteraTipoAllegati(letteretipoAllegati);
	    } else {
		movimentimail.setLetteraTipoAllegati(new Letteretipo());
	    }
	    // TODO: da revisionare il comportamento della variabile isAttiva
	    // movimentimail.setFlgInvialinkallmail(isAttiva);
	    movimentimail.getEntity().setMovimento(movimento);
	    List<Documentiistanza> documentiistanzas = documentiistanzaService.findByIstanzaOggetto(codiceistanza);
	    List<ChiaveValoreBean<String, List<Movimentiallegati>>> movalls = findListaMovimentiAllegati(istanza, movimento);
	    model.addAttribute("movimentiallegatis", movalls);
	    List<ChiaveValoreBean<String, List<Istanzeallegati>>> ialls = findListaIstanzeallegati(istanza);
	    DocumentiHelper documentiHelper = documentiHelperService.findDocumentiInvioMailDaMovimento(movimento.getId().getCodice());
	    movimentimail.setDocumentiHelper(documentiHelper);
	    model.addAttribute("istanzeallegatis", ialls);
	    model.addAttribute("documentiistanzas", documentiistanzas);
	    model.addAttribute("movimentimail", movimentimail);
	    model.addAttribute("invio", "KO");
	    // model.addAttribute("mailtipi", mailtipi);
	    model.addAttribute("ifZipLogicoExist", ifZipLogicoExist(movimento.getId().getCodice()));
	    String[] codiceComuni = this.popolaArrayCodiciComune();
	    //	    List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
	    //		    istanza.getComune().getCodicecomune(), false);
	    List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		    codiceComuni);
	    model.addAttribute("listMailConfig", listMailConfig);
	    return "movimentimail/inviomail";
	} finally {
	    ORMHelper.setToken(null);
	}
	status.setComplete();
	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	LoggerCancellazioni.log("####INVIO MAIL==>In data " +
		Utilities.getToday(true) +
		" l'utente " +
		r.getResponsabile() +
		" (" +
		r.getId() +
		") ha inviato la mail per il movimento " +
		movimentimail.getEntity().getMovimento().getId() +
		" dell'istanza " +
		movimentimail.getEntity().getMovimento().getIstanza().getId() +
		" con oggetto: " +
		movimentimail.getEntity().getOggetto());
	//dato che la insert invia solamente la mail, non ho il codice del record di movimentimail(lo inserisce la webapp MailService), 
	//quindi vado direttamente all'archivio mail. L'inserimento viene fatto tramite un thred separato, questo rallenta l'inserimento.
	// Mettiamo il processo in pausa 2 sec in modo che quando si torna alla lista ci sia il record inserito
	long threadSleepLastAttemp = 2000;
	try {
	    Thread.sleep(threadSleepLastAttemp);
	} catch (InterruptedException e) {
	    log.warn("inviaMail# {}", e);
	}
	return "redirect:list.htm?codicemovimento=" +
		movimentimail.getEntity().getMovimento().getId().getCodice() +
		"&codiceistanza=" +
		movimentimail.getEntity().getMovimento().getIstanza().getId().getCodice() +
		"&status_msg=01";
    }

    private String[] popolaArrayCodiciComune() {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomuni = responsabiliService.findListResponsabilicomuni(responsabili);
	int i = 0;
	String[] codiceComuni = new String[responsabilicomuni.size()];
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	return codiceComuni;
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	PkId id = new PkId(codice);
	Movimentimail movimentimail = movimentimailService.findById(id);
	boolean export = createJMesaExport(request, response, movimentimail.getMovimentimailallegatis());
	if (export)
	    return null;
	fixRenderEntityProperty(movimentimail);
	model.addAttribute("movimentimail", movimentimail);
	model.addAttribute("codicemovimento", movimentimail.getMovimento().getId().getCodice());
	model.addAttribute("codiceistanza", movimentimail.getMovimento().getIstanza().getId().getCodice());
	setPageAttributes(model);
	return "movimentimail/form";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("movimentimail") Movimentimail movimentimail, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// String codiceistanza = request.getParameter("codiceistanza");
	// String codicemovimento = request.getParameter("codicemovimento");
	Movimentimail objToDelete = movimentimailService.findById(movimentimail.getId());
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	boolean isCancellaMail = userlogged.getFlagCancelladocumentistc() == null ? false : userlogged.getFlagCancelladocumentistc().booleanValue();
	if (!isCancellaMail) {
	    String messaggioErrore = getMessageFromBundle("javascript.alert.operatore_non_puo_cancellare_documento_stc", null);
	    throw new SecurityException(messaggioErrore);
	}
	String descrizioneMail = objToDelete.toString();
	String descrizioneIstanza = ((Istanze) EntityUtils.getNestedProperty(objToDelete.getMovimento(), "istanza")).toString();
	String responsabile = userlogged.toString();
	try {
	    movimentimailService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(movimentimail);
	    setPageAttributes(model);
	    return "movimentimail/form";
	}
	LoggerCancellazioni.logCancellazioneMail(responsabile, descrizioneMail, descrizioneIstanza);
	status.setComplete();
	return getHistoryBack();
    }

    @RequestMapping
    public String ajaxMostraEmail(Model model, @RequestParam("codicemovimento") Integer codicemovimento,
	    @RequestParam("codiceistanza") String codiceistanza, HttpServletRequest request, HttpServletResponse response) {

	List<Movimentimail> movimentimailList = new ArrayList<Movimentimail>();
	if (codicemovimento != null && !codicemovimento.equals("")) {
	    movimentimailList = movimentimailService.findByCodiceMovimento(codicemovimento);
	} else if (codiceistanza != null && !codiceistanza.equals("")) {
	    movimentimailList = movimentimailService.findByCodiceIstanza(Integer.valueOf(codiceistanza));
	}
	boolean export = createJMesaExport(request, response, movimentimailList);
	if (export)
	    return null;
	model.addAttribute("movimentimailList", movimentimailList);
	model.addAttribute("codicemovimento", codicemovimento);
	model.addAttribute("codiceistanza", codiceistanza);
	return "movimentimail/ajaxMostraEmail";
    }

    @Override
    protected void fixMergeEntityProperty(Movimentimail entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Movimentimail entity) {

    }

    private void fixRenderMovimentiMailCommandProperty(MovimentimailCommand movimentimailCommand) {

	if (movimentimailCommand.getLetteraTipoAllegati() == null) {
	    movimentimailCommand.setLetteraTipoAllegati(new Letteretipo());
	}
	if (movimentimailCommand.getMailConfig() == null) {
	    movimentimailCommand.setMailConfig(new MailConfig());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	Responsabili resp = getCurrentlyAuthenticatedUserDetails();
	model.addAttribute("userlogged", resp);
    }

    private void setPageAttributes(Model model, MovimentimailCommand command) {

	Integer codicemovimento = command.getEntity().getMovimento().getId().getCodice();
	model.addAttribute("ifZipLogicoExist", ifZipLogicoExist(codicemovimento));
    }

    private Boolean ifZipLogicoExist(Integer codicemovimento) {

	return this.movimentiZipLogicoService.isZipLogicoExistInMovimento(codicemovimento);
    }
}
