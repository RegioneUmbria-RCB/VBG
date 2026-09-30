package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ConfigurazioneEmail;
import it.gruppoinit.pal.gp.core.domain.PkId;
import java.util.List;

/**
 * 
 * @author 
 */
public interface ConfigurazioneEmailDAO extends
		BaseDAO<ConfigurazioneEmail, PkId> {

	/**
	 * TODO inserire il commento
	 * 
	 */
	public List<ConfigurazioneEmail> findAll(Integer firstResult,
			Integer maxResult);

}
