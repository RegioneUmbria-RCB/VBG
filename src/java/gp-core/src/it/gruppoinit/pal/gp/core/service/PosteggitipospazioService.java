package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.PosteggitipospazioDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;

import java.util.List;

public interface PosteggitipospazioService extends BaseService<Posteggitipospazio, PkId> {

    /**
     * @see PosteggitipospazioDAO#findByTipoSpazio(Posteggitipospazio posteggitipospazio)
     */
    public List<Posteggitipospazio> findByTipoSpazio(Posteggitipospazio posteggitipospazio);
}
