/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.VwOneriregulus;
import it.gruppoinit.pal.gp.core.domain.VwOneriregulusId;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface VwOneriregulusDAO extends BaseDAO<VwOneriregulus, VwOneriregulusId> {

    public List<VwOneriregulus> getDebtSituationOneriRegulus(String codiceFiscale);
}
