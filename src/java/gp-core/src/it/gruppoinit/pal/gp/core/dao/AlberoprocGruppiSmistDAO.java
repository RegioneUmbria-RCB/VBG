package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocGruppiSmist;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GruppiSmistHelper;

import java.util.List;

public interface AlberoprocGruppiSmistDAO extends BaseDAO<AlberoprocGruppiSmist, PkId> {

    // public List<GruppiSmistHelper> getListaPerGruppi(List<Integer> codiciGruppo);

    public List<GruppiSmistHelper> getConfigurazioni();
}
