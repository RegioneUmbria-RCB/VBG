package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzepeopled;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author francescop
 */
public interface IstanzepeopledService extends BaseService<Istanzepeopled, PkId> {

    public Istanzepeopled findByIstanza(Istanze istanze);
}
