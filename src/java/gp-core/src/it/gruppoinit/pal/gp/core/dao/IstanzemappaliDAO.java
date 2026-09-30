package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author francescop
 */
public interface IstanzemappaliDAO extends BaseDAO<Istanzemappali, PkId> {

    public Istanzemappali findByPrimarioIstanza(Istanze istanza);
}
