package it.gruppoinit.pal.gp.core.features.stradario.allineamento.upgr;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.domain.JobRepository;
import it.gruppoinit.pal.gp.core.service.JobRepositoryService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrUpgrAllineamentoStradarioJobTask")
public class UpgrAllineamentoStradarioJobTask extends BaseJavaTask {

    private JobRepositoryService jobRepositoryService;

    @Autowired
    public void setJobRepositoryService(JobRepositoryService jobRepositoryService) {

	this.jobRepositoryService = jobRepositoryService;
    }

    @Override
    public void initialize() throws SetupRunException {

	// non serve nessuna inizializzazione
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	List<JobRepository> jobs = this.jobRepositoryService.findByClassName("it.gruppoinit.pal.gp.core.jobs.AllineamentoStradarioJob");
	for (JobRepository job : jobs) {
	    job.setJobClassName("it.gruppoinit.pal.gp.core.features.stradario.allineamento.v1.AllineamentoStradarioV1Job");
	    this.jobRepositoryService.update(job);
	}
	return 0;
    }
}
