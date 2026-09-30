/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;

/**
 * @author lucap
 * 
 */
public interface ContiDAO extends BaseDAO<Conti, PkId> {

    public List<Conti> findByDescrizione(String descrizione);

    public List<Conti> findAllActive();

    public List<Conti> findContiAttivi(Date dataRiferimento);

    public Conti findContoAttivoByIdCausaleOnere(Integer idCausaleOnere) throws InvalidConfigurationException;
}
