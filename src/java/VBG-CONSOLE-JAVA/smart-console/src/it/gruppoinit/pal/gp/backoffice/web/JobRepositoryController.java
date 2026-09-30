package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.JobRepository;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.JobExecutionContextForJson;
import it.gruppoinit.pal.gp.core.service.JobRepositoryService;
import it.gruppoinit.pal.gp.core.service.JobSchedulerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
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
@SessionAttributes("jobrepository")
public class JobRepositoryController extends BaseController<JobRepository> {

    @Autowired
    private JobRepositoryService jobrepositoryService;
    @Autowired
    private JobSchedulerManager jobSchedulerManager;

    @RequestMapping
    public String schedule(Model model, HttpServletRequest request, HttpServletResponse response) {

	jobSchedulerManager.schedulePersistentJobs();
	return "redirect:list.htm";
    }

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

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

	JobRepository jobrepository = new JobRepository();
	fixRenderEntityProperty(jobrepository);
	model.addAttribute("jobrepository", jobrepository);
	setPageAttributes(model);
	return "jobrepository/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("jobrepository") JobRepository jobrepository, BindingResult result, SessionStatus status) {

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

	JobRepository jobrepository = jobrepositoryService.findById(codice);
	fixRenderEntityProperty(jobrepository);
	model.addAttribute("jobrepository", jobrepository);
	setPageAttributes(model);
	return "jobrepository/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("jobrepository") JobRepository jobrepository, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

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

    @Override
    protected void fixMergeEntityProperty(JobRepository entity) {

    }

    @Override
    protected void fixRenderEntityProperty(JobRepository entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
