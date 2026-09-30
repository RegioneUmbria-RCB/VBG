package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Taskbase;
import it.gruppoinit.pal.gp.core.domain.Taskparametribase;
import it.gruppoinit.pal.gp.core.domain.Taskscheduler;
import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;
import it.gruppoinit.pal.gp.core.domain.TaskschedulerparametriId;
import it.gruppoinit.pal.gp.core.domain.helper.TaskschedulerparametriHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.TaskschedulerCommand;
import it.gruppoinit.pal.gp.core.service.TaskbaseService;
import it.gruppoinit.pal.gp.core.service.TaskparametribaseService;
import it.gruppoinit.pal.gp.core.service.TaskschedulerService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
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

/**
 * 
 * @author Luca Proietti
 */
@Controller
@SessionAttributes("taskscheduler")
public class TaskschedulerController extends BaseController<Taskscheduler> {

    @Autowired
    private TaskschedulerService taskschedulerService;
    @Autowired
    private TaskbaseService taskbaseService;
    @Autowired
    private TaskparametribaseService taskparametribaseService;
    private static final Logger log = LoggerFactory.getLogger(TaskschedulerController.class);

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Taskscheduler> taskschedulerList = taskschedulerService.findAll(null, null);
	ModelMap model = new ModelMap(taskschedulerList);
	boolean export = createJMesaExport(request, response, taskschedulerList);
	if (export) {
	    return null;
	}
	model.addAttribute("taskschedulerList", taskschedulerList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	TaskschedulerCommand taskscheduler = new TaskschedulerCommand();
	taskscheduler.setDisplayMode(TaskschedulerCommand.NEW);
	fixRenderEntityProperty(taskscheduler.getEntity());
	setCommandAttributes(taskscheduler);
	model.addAttribute("taskscheduler", taskscheduler);
	setPageAttributes(model);
	return "taskscheduler/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("taskscheduler") TaskschedulerCommand taskscheduler, BindingResult result, SessionStatus status) {

	Taskscheduler entity = taskscheduler.getEntity();
	Integer intervallo = 0;
	if (taskscheduler.getGiorni() != null && taskscheduler.getGiorni() > 0) {
	    intervallo = 1440 * taskscheduler.getGiorni();
	    entity.setIntervallo(intervallo);
	}
	if (taskscheduler.getOre() != null && taskscheduler.getOre() > 0) {
	    intervallo = intervallo + 60 * taskscheduler.getOre();
	    entity.setIntervallo(intervallo);
	}
	if (taskscheduler.getMinuti() != null && taskscheduler.getMinuti() > 0) {
	    intervallo = intervallo + taskscheduler.getMinuti();
	    entity.setIntervallo(intervallo);
	}
	if (entity.getProssimaesecuzione() != null) {
	    Calendar dataesecuzione = new GregorianCalendar();
	    dataesecuzione.setTime(entity.getProssimaesecuzione());
	    Integer hh = 0;
	    Integer mm = 0;
	    Integer ss = 0;
	    if (StringUtils.isNotBlank(taskscheduler.getHhmmss())) {
		String[] hhmmss = taskscheduler.getHhmmss().split(":");
		hh = Integer.parseInt(hhmmss[0]);
		mm = Integer.parseInt(hhmmss[1]);
		ss = Integer.parseInt(hhmmss[2]);
	    }
	    dataesecuzione.set(Calendar.HOUR_OF_DAY, hh);
	    dataesecuzione.set(Calendar.MINUTE, mm);
	    dataesecuzione.set(Calendar.SECOND, ss);
	    entity.setProssimaesecuzione(dataesecuzione.getTime());
	}
	// Vengono inseriti automaticamente i parametri prelevandoli da parametribase
	List<Taskparametribase> taskparametribases = taskparametribaseService.findAll(null, null);
	Set<Taskschedulerparametri> taskschedulerparametris = new LinkedHashSet<Taskschedulerparametri>();
	for (Taskparametribase taskparametribase : taskparametribases) {
	    Taskschedulerparametri taskschedulerparametri = new Taskschedulerparametri();
	    TaskschedulerparametriId id = new TaskschedulerparametriId(ORMHelper.getIdcomune(), entity.getId().getCodice(), taskparametribase.getId()
		    .getParametro());
	    taskschedulerparametri.setTaskscheduler(entity);
	    taskschedulerparametri.setId(id);
	    taskschedulerparametris.add(taskschedulerparametri);
	}
	entity.setTaskschedulerparametris(taskschedulerparametris);
	try {
	    taskschedulerService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, taskscheduler.getEntity(), true, e);
	    fixRenderEntityProperty(taskscheduler.getEntity());
	    setCommandAttributes(taskscheduler);
	    return "taskscheduler/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + taskscheduler.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	TaskschedulerCommand taskscheduler = new TaskschedulerCommand();
	taskscheduler.setDisplayMode(TaskschedulerCommand.VIEW);
	PkId id = new PkId(codice);
	Taskscheduler entity = taskschedulerService.findById(id);
	fixRenderEntityProperty(taskscheduler.getEntity());
	taskscheduler.setEntity(entity);
	setCommandAttributes(taskscheduler);
	model.addAttribute("taskscheduler", taskscheduler);
	setPageAttributes(model);
	return "taskscheduler/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("taskscheduler") TaskschedulerCommand taskscheduler, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Taskscheduler entity = taskscheduler.getEntity();
	Integer intervallo = 0;
	if (taskscheduler.getGiorni() != null && taskscheduler.getGiorni() > 0) {
	    intervallo = 1440 * taskscheduler.getGiorni();
	    entity.setIntervallo(intervallo);
	}
	if (taskscheduler.getOre() != null && taskscheduler.getOre() > 0) {
	    intervallo = intervallo + 60 * taskscheduler.getOre();
	    entity.setIntervallo(intervallo);
	}
	if (taskscheduler.getMinuti() != null && taskscheduler.getMinuti() > 0) {
	    intervallo = intervallo + taskscheduler.getMinuti();
	    entity.setIntervallo(intervallo);
	}
	if (entity.getProssimaesecuzione() != null) {
	    Calendar dataesecuzione = new GregorianCalendar();
	    dataesecuzione.setTime(entity.getProssimaesecuzione());
	    Integer hh = 0;
	    Integer mm = 0;
	    Integer ss = 0;
	    if (StringUtils.isNotBlank(taskscheduler.getHhmmss())) {
		String[] hhmmss = taskscheduler.getHhmmss().split(":");
		hh = Integer.parseInt(hhmmss[0]);
		mm = Integer.parseInt(hhmmss[1]);
		ss = Integer.parseInt(hhmmss[2]);
	    }
	    dataesecuzione.set(Calendar.HOUR_OF_DAY, hh);
	    dataesecuzione.set(Calendar.MINUTE, mm);
	    dataesecuzione.set(Calendar.SECOND, ss);
	    entity.setProssimaesecuzione(dataesecuzione.getTime());
	}
	try {
	    taskschedulerService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, taskscheduler.getEntity(), true, e);
	    fixRenderEntityProperty(taskscheduler.getEntity());
	    setCommandAttributes(taskscheduler);
	    return "taskscheduler/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + taskscheduler.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("taskscheduler") TaskschedulerCommand taskscheduler, BindingResult result, SessionStatus status) {

	Taskscheduler objToDelete = taskschedulerService.findById(taskscheduler.getEntity().getId());
	try {
	    taskschedulerService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, taskscheduler.getEntity(), true, e);
	    fixRenderEntityProperty(taskscheduler.getEntity());
	    setCommandAttributes(taskscheduler);
	    return "taskscheduler/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String createParametri(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	TaskschedulerCommand taskscheduler = new TaskschedulerCommand();
	PkId id = new PkId(codice);
	Taskscheduler entity = taskschedulerService.findById(id);
	fixRenderEntityProperty(taskscheduler.getEntity());
	taskscheduler.setEntity(entity);
	setCommandAttributes(taskscheduler);
	List<TaskschedulerparametriHelper> taskschedulerparametriHelpers = new ArrayList<TaskschedulerparametriHelper>();
	List<Taskparametribase> taskparametribases = taskparametribaseService.findAll(null, null);
	for (Taskparametribase taskparametribase : taskparametribases) {
	    TaskschedulerparametriHelper taskschedulerparametriHelper = new TaskschedulerparametriHelper();
	    taskschedulerparametriHelper.setOrdine(taskparametribase.getOrdine());
	    taskschedulerparametriHelper.setDescrizione(taskparametribase.getDescrizione());
	    for (Taskschedulerparametri taskschedulerparametri : entity.getTaskschedulerparametris()) {
		if (taskparametribase.getId().getParametro().equals(taskschedulerparametri.getId().getParametro())) {
		    taskschedulerparametriHelper.setTaskschedulerparametri(taskschedulerparametri);
		}
	    }
	    taskschedulerparametriHelpers.add(taskschedulerparametriHelper);
	}
	taskscheduler.setTaskschedulerparametriHelpers(taskschedulerparametriHelpers);
	model.addAttribute("taskscheduler", taskscheduler);
	setPageAttributes(model);
	return "taskscheduler/formParametri";
    }

    @RequestMapping
    public String saveParametri(@ModelAttribute("taskscheduler") TaskschedulerCommand taskscheduler, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request) {

	Taskscheduler entity = taskscheduler.getEntity();
	Set<Taskschedulerparametri> taskschedulerparametris = new LinkedHashSet<Taskschedulerparametri>();
	List<TaskschedulerparametriHelper> taskschedulerparametriHelpers = taskscheduler.getTaskschedulerparametriHelpers();
	for (TaskschedulerparametriHelper taskschedulerparametriHelper : taskschedulerparametriHelpers) {
	    taskschedulerparametris.add(taskschedulerparametriHelper.getTaskschedulerparametri());
	}
	entity.setTaskschedulerparametris(taskschedulerparametris);
	try {
	    taskschedulerService.saveParametri(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, taskscheduler.getEntity(), true, e);
	    fixRenderEntityProperty(taskscheduler.getEntity());
	    setCommandAttributes(taskscheduler);
	    return "taskscheduler/formParametri";
	}
	status.setComplete();
	return "redirect:createParametri.htm?codice=" + taskscheduler.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String eliminaOperazione(Model model, @RequestParam("codiceoperazione") Integer codiceoperazione, HttpServletRequest request) {

	Taskscheduler entity = taskschedulerService.findById(new PkId(codiceoperazione));
	try {
	    taskschedulerService.delete(entity);
	} catch (Exception e) {
	    log.error("Possibile anomalia nella cancellazione dei dati sulla tabella TASKSCHEDULER. \nTaskschedulerServiceImpl.delete: Errore :"
		    + e.getMessage());
	    throw new RuntimeException(
		    "Possibile anomalia nella cancellazione dei dati sulla tabella TASKSCHEDULER. \nTaskschedulerServiceImpl.delete: Errore :"
			    + e.getMessage());
	}
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Taskscheduler entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Taskscheduler entity) {

	if (entity.getTaskbase() == null) {
	    entity.setTaskbase(new Taskbase());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private void setCommandAttributes(TaskschedulerCommand command) {

	// Recupero ora
	Integer hh = 0;
	Integer mm = 0;
	Integer ss = 0;
	if (EntityUtils.getNestedProperty(command.getEntity(), "prossimaesecuzione") != null) {
	    Calendar dataesecuzione = new GregorianCalendar();
	    dataesecuzione.setTime(command.getEntity().getProssimaesecuzione());
	    hh = dataesecuzione.get(Calendar.HOUR_OF_DAY);
	    mm = dataesecuzione.get(Calendar.MINUTE);
	    ss = dataesecuzione.get(Calendar.SECOND);
	}
	DecimalFormat df = new DecimalFormat("00");
	command.setHhmmss(df.format(hh) + ":" + df.format(mm) + ":" + df.format(ss));
	// Recupero intervallo
	if (EntityUtils.getNestedProperty(command.getEntity(), "intervallo") != null) {
	    Integer resto = command.getEntity().getIntervallo();
	    command.setGiorni(resto / 1440);
	    resto = resto - command.getGiorni() * 1440;
	    command.setOre(resto / 60);
	    resto = resto - command.getOre() * 60;
	    command.setMinuti(resto);
	}
	List<Taskbase> operazioneList = taskbaseService.findAll(null, null);
	command.setOperazioneList(operazioneList);
    }
}
