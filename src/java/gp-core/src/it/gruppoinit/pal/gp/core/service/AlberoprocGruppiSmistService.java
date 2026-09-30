package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AlberoprocGruppiSmist;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GruppiSmistamentoClassiHelper;

import java.util.List;
import java.util.Set;

public interface AlberoprocGruppiSmistService extends BaseService<AlberoprocGruppiSmist, PkId> {

    public void insertConfigurazione(Set<Integer> codicigruppo, Integer codiceAlberoproc, Integer codiceProceduraScia,
	    Integer codiceProceduraOrdinario);

    public List<AlberoprocGruppiSmist> findByGruppiEndot(Integer codiceGruppo, Integer firstResult, Integer maxResults);

    public List<AlberoprocGruppiSmist> findByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResults);

    public List<AlberoprocGruppiSmist> findByprocedura(Integer codiceProcedura, Integer firstResult, Integer maxResults);

    public int countBySoftware();

    public GruppiSmistamentoClassiHelper getGruppiSmistamentoClassiHelper();
}
