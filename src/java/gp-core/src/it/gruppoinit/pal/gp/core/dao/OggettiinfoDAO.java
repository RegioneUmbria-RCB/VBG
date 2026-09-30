/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Oggettiinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface OggettiinfoDAO extends BaseDAO<Oggettiinfo, PkId> {

    public List<Oggettiinfo> findByDescrizioneAndTipologia(String descrizione, Integer tipologia);
}
