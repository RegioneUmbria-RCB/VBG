/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface TipiunitamisuraService extends BaseService<Tipiunitamisura, PkId> {

    public List<Tipiunitamisura> findByFilter(Tipiunitamisura entity);
}
