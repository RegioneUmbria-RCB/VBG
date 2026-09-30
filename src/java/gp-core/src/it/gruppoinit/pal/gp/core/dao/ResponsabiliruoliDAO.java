/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliruoliId;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface ResponsabiliruoliDAO extends BaseDAO<Responsabiliruoli, ResponsabiliruoliId> {

    public List<Integer> findCodiciRuoloByResponsabile(Integer codiceResponsabile);
}
