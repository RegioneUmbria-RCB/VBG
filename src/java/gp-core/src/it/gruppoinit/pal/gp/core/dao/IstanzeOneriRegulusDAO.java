/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.IstanzeOneriRegulus;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * @author francescop
 * 
 */
public interface IstanzeOneriRegulusDAO extends BaseDAO<IstanzeOneriRegulus, PkId> {

    void deleteByIdOnere(int istanzeOneriId);

    List<IstanzeOneriRegulus> findByIdIstanzeOneri(Integer idIstanzeOneri);
}
