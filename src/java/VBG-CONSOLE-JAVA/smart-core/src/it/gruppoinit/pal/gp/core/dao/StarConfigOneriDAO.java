/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StarConfigOneri;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;

import java.util.List;

/**
 * @author francescop
 * 
 */
public interface StarConfigOneriDAO extends BaseDAO<StarConfigOneri, PkId> {

    public StarConfigOneri findConfigOneriByCodiceComune(String codCOmune);
    
    public StarConfigOneri findConfigOneriBase();
    
}
