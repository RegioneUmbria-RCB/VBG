package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface TipiunitamisuraDAO extends BaseDAO<Tipiunitamisura, PkId> {

    List<Tipiunitamisura> findByFilter(Tipiunitamisura entity);
}
