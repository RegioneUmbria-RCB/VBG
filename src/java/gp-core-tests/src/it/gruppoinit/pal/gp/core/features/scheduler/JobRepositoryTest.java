package it.gruppoinit.pal.gp.core.features.scheduler;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.JobRepository;

public class JobRepositoryTest {

    @Test
    public void getIdDaIdentificativoJobReturnInteger() {

	JobRepository repo = new JobRepository();
	repo.setId(100);
	repo.setJobName(" il mio job");
	Assert.assertTrue(repo.getIdDaItentificativo(repo.getIdentificativoJob()).intValue() == 100);
    }
}
