package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzepeopled;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author francescop
 */
public interface IstanzepeopledDAO extends BaseDAO<Istanzepeopled, PkId> {

    public Istanzepeopled findByIstanza(Istanze istanze);
}
