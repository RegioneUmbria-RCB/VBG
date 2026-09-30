package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ConfigurazioneEmailDAO;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneEmail;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ConfigurazioneEmailDAOImpl extends BaseDAOImpl<ConfigurazioneEmail, PkId> implements ConfigurazioneEmailDAO {

    @Override
    public Class<ConfigurazioneEmail> getEntityClass() {

	return ConfigurazioneEmail.class;
    }
}
