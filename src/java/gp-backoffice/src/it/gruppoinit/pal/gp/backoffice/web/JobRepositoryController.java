package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.JobRepository;
import it.gruppoinit.pal.gp.core.domain.JobRepositoryParam;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.JobExecutionContextForJson;
import it.gruppoinit.pal.gp.core.service.JobRepositoryParamService;
import it.gruppoinit.pal.gp.core.service.JobRepositoryService;
import it.gruppoinit.pal.gp.core.service.JobSchedulerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author fabrizioc
 */
@Controller
@SessionAttributes(value = { "jobrepository", "jobrepositoryparam" })
public class JobRepositoryController extends BaseController<JobRepository> {

    private static Logger logger = LoggerFactory.getLogger(JobRepositoryController.class);
    @Autowired
    private JobRepositoryService jobrepositoryService;
    @Autowired
    private JobSchedulerManager jobSchedulerManager;
    @Autowired
    private JobRepositoryParamService jobRepositoryParamService;

    @RequestMapping
    public String schedule(Model model, HttpServletRequest request, HttpServletResponse response) {

	verificaAccessoAmministratore();
	jobSchedulerManager.schedulePersistentJobs();
	return "redirect:list.htm";
    }

    private void verificaAccessoAmministratore() {

	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.defaultIfEmpty(r.getAmministratore(), "0").equalsIgnoreCase("0")) {
	    logger.error("L'utente {} ha tentato di accedere alla funzionalità di gestione JobSchedulati", r);
	    throw new SecurityException("Utente non abilitato alla funzionalità! L'operazione è stata riportata nei log.");
	}
    }

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	verificaAccessoAmministratore();
	List<JobRepository> jobrepositoryList = jobrepositoryService.findAll(null, null);
	ModelMap model = new ModelMap(jobrepositoryList);
	boolean export = createJMesaExport(request, response, jobrepositoryList);
	if (export) {
	    return null;
	}
	model.addAttribute("jobrepositoryList", jobrepositoryList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	verificaAccessoAmministratore();
	JobRepository jobrepository = new JobRepository();
	fixRenderEntityProperty(jobrepository);
	model.addAttribute("jobrepository", jobrepository);
	setPageAttributes(model);
	return "jobrepository/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("jobrepository") JobRepository jobrepository, BindingResult result, SessionStatus status) {

	verificaAccessoAmministratore();
	fixMergeEntityProperty(jobrepository);
	try {
	    jobrepositoryService.insert(jobrepository);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, jobrepository, e);
	    fixRenderEntityProperty(jobrepository);
	    return "jobrepository/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + jobrepository.getId() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	verificaAccessoAmministratore();
	JobRepository jobrepository = jobrepositoryService.findById(codice);
	fixRenderEntityProperty(jobrepository);
	model.addAttribute("jobrepository", jobrepository);
	setPageAttributes(model);
	return "jobrepository/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("jobrepository") JobRepository jobrepository, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	verificaAccessoAmministratore();
	fixMergeEntityProperty(jobrepository);
	try {
	    jobrepositoryService.update(jobrepository);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, jobrepository, e);
	    fixRenderEntityProperty(jobrepository);
	    return "jobrepository/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + jobrepository.getId() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("jobrepository") JobRepository jobrepository, BindingResult result, SessionStatus status) {

	verificaAccessoAmministratore();
	JobRepository objToDelete = jobrepositoryService.findById(jobrepository.getId());
	try {
	    jobrepositoryService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, jobrepository, e);
	    fixRenderEntityProperty(jobrepository);
	    return "jobrepository/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public ModelAndView ajaxGetCurrentlyExecutingJobs(HttpServletResponse response) {

	try {
	    List<JobExecutionContext> _jobs = jobSchedulerManager.getCurrentlyExecutingJobs();
	    List<ChiaveValoreBean<String, JobExecutionContextForJson>> jobs = new ArrayList<ChiaveValoreBean<String, JobExecutionContextForJson>>();
	    for (JobExecutionContext job : _jobs) {
		JobExecutionContextForJson jobExec = new JobExecutionContextForJson(job);
		ChiaveValoreBean<String, JobExecutionContextForJson> cvb = new ChiaveValoreBean<String, JobExecutionContextForJson>();
		cvb.setChiave(job.getJobDetail().getName());
		cvb.setValore(jobExec);
		jobs.add(cvb);
	    }
	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("jobs", jobs);
	    return new ModelAndView("jsonView", model);
	} catch (SchedulerException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e.getMessage());
	}
    }

    @RequestMapping
    public String createParametri(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	verificaAccessoAmministratore();
	JobRepository jobrepository = jobrepositoryService.findById(codice);
	fixRenderEntityProperty(jobrepository);
	setPageAttributes(model);
	setPageAttributes(model, jobrepository, true);
	model.addAttribute("jobrepository", jobrepository);
	model.addAttribute("isView", 0);
	return "jobrepository/formParametri";
    }

    @RequestMapping
    public String insertParametri(@ModelAttribute("jobrepository") JobRepository jobrepository, BindingResult result, SessionStatus status) {

	verificaAccessoAmministratore();
	//fixMergeEntityProperty(jobrepository);
	try {
	    jobRepositoryParamService.insertListParametri(jobrepository.getJobRepositoryParams());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, jobrepository, e);
	    fixRenderEntityProperty(jobrepository);
	    // model.addAttribute("isView", 0);
	    return "jobrepository/form";
	}
	status.setComplete();
	return "redirect:viewParametri.htm?codice=" + jobrepository.getId() + "&status_msg=01";
    }

    @RequestMapping
    public String viewParametri(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	verificaAccessoAmministratore();
	JobRepository jobrepository = jobrepositoryService.findById(codice);
	fixRenderEntityProperty(jobrepository);
	setPageAttributes(model);
	setPageAttributes(model, jobrepository, false);
	model.addAttribute("jobrepository", jobrepository);
	model.addAttribute("isView", 1);
	return "jobrepository/formParametri";
    }

    @RequestMapping
    public String updateParametri(@ModelAttribute("jobrepository") JobRepository jobrepository, BindingResult result, SessionStatus status) {

	verificaAccessoAmministratore();
	try {
	    // jobRepositoryParamService.insertListParametri(jobrepository.getJobRepositoryParams());
	    jobRepositoryParamService.insertOrUpdateListParametri(jobrepository.getJobRepositoryParams());
	    jobSchedulerManager.schedulePersistentJobs();
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, jobrepository, e);
	    fixRenderEntityProperty(jobrepository);
	    // model.addAttribute("isView", 0);
	    return "jobrepository/form";
	}
	status.setComplete();
	return "redirect:viewParametri.htm?codice=" + jobrepository.getId() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteParametri(@ModelAttribute("jobrepository") JobRepository jobrepository, BindingResult result, SessionStatus status) {

	verificaAccessoAmministratore();
	//fixMergeEntityProperty(jobrepository);
	try {
	    jobRepositoryParamService.deleteListParametri(jobrepository.getJobRepositoryParams());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, jobrepository, e);
	    fixRenderEntityProperty(jobrepository);
	    // model.addAttribute("isView", 0);
	    return "jobrepository/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + jobrepository.getId() + "&status_msg=02";
    }

    @RequestMapping
    public String run(@ModelAttribute("jobrepository") JobRepository jobrepository, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	verificaAccessoAmministratore();
	try {
	    jobSchedulerManager.triggerJob(jobrepository.getIdentificativoJob());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, jobrepository, e);
	    fixRenderEntityProperty(jobrepository);
	    return "jobrepository/form";
	}
	return "redirect:view.htm?codice=" + jobrepository.getId() + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(JobRepository entity) {

    }

    @Override
    protected void fixRenderEntityProperty(JobRepository entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    //    private Set<JobRepositoryParam> recuperaParametriJob(JobRepository jobrepository) {
    //
    //	Set<JobRepositoryParam> jobRepositoryParams = new HashSet<JobRepositoryParam>();
    //	JobRepositoryParam jobRepositoryParam = null;
    //	Class<?> c;
    //	if (jobrepository != null && StringUtils.isNotBlank(jobrepository.getJobClassName())) {
    //	    try {
    //		c = Class.forName(jobrepository.getJobClassName());
    //		Object obj = c.newInstance();
    //		Class noparams[] = {};
    //		Method method = c.getDeclaredMethod("getParam", noparams);
    //		Map<String, String> m = (Map<String, String>) method.invoke(obj, null);
    //		for (Map.Entry<String, String> entry : m.entrySet()) {
    //		    jobRepositoryParam = new JobRepositoryParam();
    //		    jobRepositoryParam.setEtichetta(entry.getKey());
    //		    jobRepositoryParam.setDescrizione(entry.getValue());
    //		    jobRepositoryParam.setJobRepository(jobrepository);
    //		    jobRepositoryParams.add(jobRepositoryParam);
    //		}
    //	    } catch (Exception e) {
    //		e.printStackTrace();
    //	    }
    //	}
    //	return jobRepositoryParams;
    //    }
    private void setPageAttributes(Model model, JobRepository jobrepository, boolean isInsert) {

	if (isInsert) {
	    Set<JobRepositoryParam> jobRepositoryParams = jobRepositoryParamService.recuperaParametriJob(jobrepository);
	    jobrepository.setJobRepositoryParams(jobRepositoryParams);
	} else {
	    Set<JobRepositoryParam> jobRepositoryParamsDB = jobrepository.getJobRepositoryParams();
	    Set<JobRepositoryParam> jobRepositoryParams = jobRepositoryParamService.recuperaParametriJob(jobrepository);
	    Set<JobRepositoryParam> jobRepositoryParamsNew = new HashSet<JobRepositoryParam>();
	    for (JobRepositoryParam jobRepositoryParam : jobRepositoryParams) {
		Boolean trovato = false;
		for (JobRepositoryParam jobRepositoryParamDB : jobRepositoryParamsDB) {
		    if (jobRepositoryParam.getEtichetta().equals(jobRepositoryParamDB.getEtichetta())) {
			trovato = true;
			break;
		    }
		}
		if (!trovato) {
		    jobRepositoryParamsNew.add(jobRepositoryParam);
		}
	    }
	    jobRepositoryParamsDB.addAll(jobRepositoryParamsNew);
	    jobrepository.setJobRepositoryParams(jobRepositoryParamsDB);
	}
    }
}
