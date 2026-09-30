package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author francescop
 */
public interface MovimentimailDAO extends BaseDAO<Movimentimail, PkId> {

    public List<Movimentimail> findByIstanza(Istanze istanza);

    public List<Movimentimail> findByMovimento(Movimenti movimento);
}
