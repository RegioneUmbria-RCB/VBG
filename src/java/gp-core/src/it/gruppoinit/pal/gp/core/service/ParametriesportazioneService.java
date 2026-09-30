package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ParametriesportazioneDAO;
import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;

/**
 * 
 * @author 
 */
public interface ParametriesportazioneService extends
		BaseService<Parametriesportazione, PkId> {

	/**
	 * @see ParametriesportazioneDAO#findAll(Integer, Integer)
	 */
	public List<Parametriesportazione> findAll(Integer firstResult,
			Integer maxResult);

}
