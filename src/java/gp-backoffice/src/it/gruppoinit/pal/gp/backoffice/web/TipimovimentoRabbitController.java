package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

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

import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbit;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbitId;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit.ITipimovimentoRabbitService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit.TipimovimentoRabbitCategoriaEnum;
import it.gruppoinit.pal.gp.core.features.rabbitmq.TopicType;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;

@Controller
@SessionAttributes(value = { "rabbitmq", "tipimovimentoRabbit" })
public class TipimovimentoRabbitController extends BaseController<TipimovimentoRabbit> {

    private static final String TIPOMOVIMENTO = "tipomovimento";
    @Autowired
    private ITipimovimentoRabbitService tipimovimentoRabbitService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private MailtipoService mailtipoService;

    @RequestMapping
    public ModelMap list(@RequestParam(TIPOMOVIMENTO) String codice, HttpServletRequest request, HttpServletResponse response) {

	List<TipimovimentoRabbit> listamovimenti = tipimovimentoRabbitService.findTipimovRabbitByTipomov(codice);
	ModelMap model = new ModelMap();
	model.addAttribute("listamovimenti", listamovimenti);
	model.addAttribute(TIPOMOVIMENTO, codice);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam(TIPOMOVIMENTO) String tipomovimento, Model model) {

	TipimovimentoRabbit tipimovimentoRabbit = new TipimovimentoRabbit();
	if (tipomovimento == null) {
	    throw new RuntimeException("Impossibile creare un nuovo movimento rabbit senza passare il codicemovimento");
	}
	TipimovimentoId id = new TipimovimentoId(tipomovimento);
	Tipimovimento tm = tipiMovimentoService.findById(id);
	List<Mailtipo> mailstipo = mailtipoService.findAll(null, null);
	tipimovimentoRabbit.setTipimovimento(tm);
	List<TopicType> topicList = new ArrayList<TopicType>();
	try {
	    topicList = tipimovimentoRabbitService.findListaTopic();
	} catch (JAXBException e) {
	    e.printStackTrace();
	}
	model.addAttribute("topicList", topicList);
	fixMergeEntityProperty(tipimovimentoRabbit);
	model.addAttribute("isInsert", true);
	model.addAttribute("tipimovimentoRabbit", tipimovimentoRabbit);
	model.addAttribute(TIPOMOVIMENTO, tipomovimento);
	model.addAttribute("mailstipo", mailstipo);
	model.addAttribute("categoriaList", TipimovimentoRabbitCategoriaEnum.values());
	setPageAttributes(model);
	return "tipimovimentorabbit/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("tipimovimentoRabbit") TipimovimentoRabbit tipimovimentoRabbit, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(tipimovimentoRabbit);
	try {
	    this.tipimovimentoRabbitService.insert(tipimovimentoRabbit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovimentoRabbit, e);
	    List<TopicType> topicList = new ArrayList<TopicType>();
	    try {
		topicList = tipimovimentoRabbitService.findListaTopic();
	    } catch (JAXBException ex) {
		e.printStackTrace();
	    }
	    model.addAttribute("topicList", topicList);
	    model.addAttribute(TIPOMOVIMENTO, tipimovimentoRabbit.getId().getFkTipimovimento());
	    model.addAttribute("isInsert", true);
	    setPageAttributes(model);
	    return "tipimovimentorabbit/form";
	}
	return "redirect:list.htm?tipomovimento=" + tipimovimentoRabbit.getTipimovimento().getId().getTipomovimento();
    }

    @RequestMapping
    public String view(@RequestParam(TIPOMOVIMENTO) String tipomovimento, @RequestParam("topic") String topic, Model model,
	    HttpServletRequest request) {

	TipimovimentoRabbitId id = new TipimovimentoRabbitId(tipomovimento, topic);
	TipimovimentoRabbit tipimovimentoRabbit = this.tipimovimentoRabbitService.findById(id);
	List<TopicType> topicList = new ArrayList<TopicType>();
	try {
	    topicList = tipimovimentoRabbitService.findListaTopic();
	} catch (JAXBException e) {
	    e.printStackTrace();
	}
	fixRenderEntityProperty(tipimovimentoRabbit);
	model.addAttribute("tipimovimentoRabbit", tipimovimentoRabbit);
	model.addAttribute(TIPOMOVIMENTO, tipomovimento);
	model.addAttribute("isView", true);
	model.addAttribute("topicList", topicList);
	model.addAttribute("categoriaList", TipimovimentoRabbitCategoriaEnum.values());
	setPageAttributes(model);
	return "tipimovimentorabbit/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipimovimentoRabbit") TipimovimentoRabbit tipimovimentoRabbit, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	try {
	    this.tipimovimentoRabbitService.update(tipimovimentoRabbit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimovimentoRabbit, e);
	    setPageAttributes(model);
	    return "tipimovimentorabbit/form";
	}
	status.setComplete();
	return "redirect:list.htm?tipomovimento=" + tipimovimentoRabbit.getTipimovimento().getId().getTipomovimento();
    }

    @RequestMapping
    public void ajaxDelete(@RequestParam(TIPOMOVIMENTO) String tipomovimento, @RequestParam("topic") String topic, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	String result = "Ok";
	TipimovimentoRabbitId id = new TipimovimentoRabbitId(tipomovimento, topic);
	TipimovimentoRabbit tmr = this.tipimovimentoRabbitService.findById(id);
	try {
	    this.tipimovimentoRabbitService.delete(tmr);
	} catch (Exception e) {
	    result = "Si e' verificato un errore durante la cancellazione del documento autorizzazione. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @Override
    protected void setPageAttributes(Model model) {

	model.addAttribute("RABBIT_TOPIC_ENUM", RabbitTopicEnum.values());
    }

    @Override
    protected void fixMergeEntityProperty(TipimovimentoRabbit entity) {

	if (entity.getTipimovimento() != null) {
	    entity.getId().setFkTipimovimento(entity.getTipimovimento().getId().getTipomovimento());
	}
    }

    @Override
    protected void fixRenderEntityProperty(TipimovimentoRabbit entity) {

	if (entity.getMailtipo() == null) {
	    entity.setMailtipo(new Mailtipo());
	}
    }
}
