/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StarConfigOneri;


/**
 * @author francol
 *
 */
public interface StarConfigOneriService extends BaseService<StarConfigOneri, PkId> {
    
    public List<StarConfigOneri> findConfigOneriLocali();
    
    public StarConfigOneri findConfigOneriBase();
    
    public StarConfigOneri findConfigOneriByCodiceComune(String codCOmune);
}
